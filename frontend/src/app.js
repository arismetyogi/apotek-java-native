// src/app.js
import PharmacyAPI from "./api.js";

const {getMedicines} = new PharmacyAPI();

const state = { q: "", page: 0, size: 10, totalPages: 0, editingId: null, adjustingId: null };

const els = {
    searchInput: document.getElementById("searchInput"),
    btnSearch: document.getElementById("btnSearch"),
    btnNew: document.getElementById("btnNew"),
    tbody: document.getElementById("tbody"),
    prev: document.getElementById("prevPage"),
    next: document.getElementById("nextPage"),
    pageInfo: document.getElementById("pageInfo"),
    sizeSel: document.getElementById("sizeSel"),
    dlgItem: document.getElementById("dlgItem"),
    formItem: document.getElementById("formItem"),
    dlgTitle: document.getElementById("dlgTitle"),
    dlgAdjust: document.getElementById("dlgAdjust"),
    formAdjust: document.getElementById("formAdjust"),
};

function money(n) {
    const v = Number(n);
    if (Number.isNaN(v)) return n;
    return new Intl.NumberFormat("id-ID", { style: "currency", currency: "IDR", maximumFractionDigits: 0 }).format(v);
}

function errMsg(e) {
    if (e?.detail?.message) return `${e.message}: ${e.detail.message}`;
    if (typeof e?.detail === "string") return `${e.message}: ${e.detail}`;
    return e?.message || "Unknown error";
}

function setForm(form, obj) {
    Object.entries(obj).forEach(([k, v]) => {
        const input = form.elements.namedItem(k);
        if (input) input.value = v;
    });
}
function getForm(form) {
    const data = {};
    new FormData(form).forEach((v, k) => (data[k] = v));
    return data;
}

async function load() {
    try {
        const data = await getMedicines({ q: state.q, page: state.page, size: state.size });
        renderTable(data.content);
        state.totalPages = data.totalPages;
        els.pageInfo.textContent = `Page ${state.page + 1} / ${Math.max(1, state.totalPages)}`;
        els.prev.disabled = state.page <= 0;
        els.next.disabled = state.page >= state.totalPages - 1;
    } catch (e) {
        alert(errMsg(e));
    }
}

function renderTable(rows) {
    els.tbody.innerHTML = "";
    if (!rows || rows.length === 0) {
        const tr = document.createElement("tr");
        const td = document.createElement("td");
        td.colSpan = 7;
        td.textContent = "Tidak ada data";
        tr.append(td);
        els.tbody.append(tr);
        return;
    }
    for (const it of rows) {
        const tr = document.createElement("tr");
        tr.innerHTML = `
      <td>${it.id}</td>
      <td><span class="badge">${escapeHtml(it.sku)}</span></td>
      <td>${escapeHtml(it.name)}</td>
      <td>${escapeHtml(it.category)}</td>
      <td class="num">${it.quantity}</td>
      <td class="num">${money(it.unitPrice)}</td>
      <td>
        <div class="actions">
          <button class="btn" data-act="edit">Edit</button>
          <button class="btn ok" data-act="adjust">Adjust</button>
          <button class="btn danger" data-act="del">Delete</button>
        </div>
      </td>
    `;
        tr.querySelector('[data-act="edit"]').addEventListener("click", () => openEdit(it));
        tr.querySelector('[data-act="adjust"]').addEventListener("click", () => openAdjust(it));
        tr.querySelector('[data-act="del"]').addEventListener("click", () => doDelete(it));
        els.tbody.append(tr);
    }
}

const escapeHtml = (s) => String(s).replace(/[&<>"']/g, m => ({ "&": "&amp;", "<": "&lt;", ">": "&gt;", '"': "&quot;", "'": "&#39;" }[m]));

// Search & pagination
els.btnSearch.addEventListener("click", () => { state.q = els.searchInput.value.trim(); state.page = 0; load(); });
els.searchInput.addEventListener("keydown", (e) => { if (e.key === "Enter") { state.q = els.searchInput.value.trim(); state.page = 0; load(); } });
els.prev.addEventListener("click", () => { if (state.page > 0) { state.page--; load(); } });
els.next.addEventListener("click", () => { if (state.page < state.totalPages - 1) { state.page++; load(); } });
els.sizeSel.addEventListener("change", () => { state.size = Number(els.sizeSel.value); state.page = 0; load(); });

// Create & Edit
els.btnNew.addEventListener("click", openCreate);
function openCreate() {
    state.editingId = null;
    els.dlgTitle.textContent = "Barang Baru";
    els.formItem.reset();
    els.dlgItem.showModal();
}
function openEdit(it) {
    state.editingId = it.id;
    els.dlgTitle.textContent = `Edit Barang #${it.id}`;
    els.formItem.reset();
    setForm(els.formItem, { sku: it.sku, name: it.name, category: it.category, quantity: it.quantity, unitPrice: it.unitPrice });
    els.dlgItem.showModal();
}
els.formItem.addEventListener("submit", async (e) => {
    e.preventDefault();
    const payload = getForm(els.formItem);
    payload.quantity = Number(payload.quantity);
    payload.unitPrice = String(payload.unitPrice);
    try {
        if (state.editingId == null) await createItem(payload);
        else await updateItem(state.editingId, payload);
        els.dlgItem.close();
        await load();
    } catch (e) {
        alert(errMsg(e));
    }
});

// Adjust stok
function openAdjust(it) {
    state.adjustingId = it.id;
    els.formAdjust.reset();
    els.dlgAdjust.showModal();
}
els.formAdjust.addEventListener("submit", async (e) => {
    e.preventDefault();
    const { delta } = getForm(els.formAdjust);
    try {
        await adjustStock(state.adjustingId, Number(delta));
        els.dlgAdjust.close();
        await load();
    } catch (e) {
        alert(errMsg(e));
    }
});

// Delete
async function doDelete(it) {
    if (!confirm(`Hapus item #${it.id} (${it.name}) ?`)) return;
    try {
        await deleteItem(it.id);
        await load();
    } catch (e) {
        alert(errMsg(e));
    }
}

// init
(function init() {
    els.sizeSel.value = String(state.size);
    load();
})();
