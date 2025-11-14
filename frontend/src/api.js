// src/api.js
const BASE_URL = "http://localhost:8081"; // sesuaikan jika berbeda

async function http(method, path, body) {
  const res = await fetch(`${BASE_URL}${path}`, {
    method,
    headers: { "Content-Type": "application/json" },
    body: body ? JSON.stringify(body) : undefined,
  });

  // error handling rapi
  if (!res.ok) {
    let detail;
    try {
      detail = await res.json();
    } catch {
      detail = await res.text();
    }
    const err = new Error(`HTTP ${res.status}`);
    err.status = res.status;
    err.detail = detail;
    throw err;
  }

  // tidak semua response ada body
  const txt = await res.text();
  return txt ? JSON.parse(txt) : null;
}

export async function listMedicines({ q = "", page = 0, size = 10 } = {}) {
  const qp = new URLSearchParams({ page, size });
  if (q) qp.set("q", q);
  return http("GET", `/medicines?${qp.toString()}`);
}

export async function getMedicine(id) {
  return http("GET", `/medicines/${id}`);
}

export async function createMedicine(payload) {
  return http("POST", "/medicines", payload);
}

export async function updateMedicine(id, payload) {
  return http("PUT", `/medicines/${id}`, payload);
}

export async function deleteMedicine(id) {
  return http("DELETE", `/medicines/${id}`);
}

export async function adjustStock(id, delta) {
  return http("POST", `/medicines/${id}/adjust`, { delta });
}

export async function getLowStockMedicines({ page = 0, size = 10 } = {}) {
  const qp = new URLSearchParams({ page, size });
  return http("GET", `/medicines/low-stock?${qp.toString()}`);
}

export async function getNearExpiryMedicines({ page = 0, size = 10 } = {}) {
  const qp = new URLSearchParams({ page, size });
  return http("GET", `/medicines/near-expiry?${qp.toString()}`);
}
