// Pharmacy POS Application
document.addEventListener('DOMContentLoaded', function() {
    // Navigation functionality
    const navLinks = document.querySelectorAll('.nav-link');
    const sections = document.querySelectorAll('section');

    // Set default active section
    document.getElementById('medicines').classList.add('active-section');

    navLinks.forEach(link => {
        link.addEventListener('click', function(e) {
            e.preventDefault();

            // Remove active class from all links and sections
            navLinks.forEach(l => l.classList.remove('active'));
            sections.forEach(s => s.classList.remove('active-section'));

            // Add active class to clicked link
            this.classList.add('active');

            // Show corresponding section
            const targetSection = document.getElementById(this.getAttribute('href').substring(1));
            if (targetSection) {
                targetSection.classList.add('active-section');
            }
        });
    });

    // Add to cart functionality
    const addToCartButtons = document.querySelectorAll('.add-to-cart-btn');
    addToCartButtons.forEach(button => {
        button.addEventListener('click', function() {
            const row = this.closest('tr');
            const medicineName = row.cells[0].textContent;
            const price = row.cells[2].textContent;

            // Create cart item element
            const cartItems = document.querySelector('.cart-items');
            const cartItem = document.createElement('div');
            cartItem.className = 'cart-item';
            cartItem.innerHTML = `
                <div class="item-info">
                    <h4>${medicineName}</h4>
                    <p>x1</p>
                </div>
                <div class="item-price">
                    ${price}
                </div>
                <button class="btn remove-btn">Remove</button>
            `;

            cartItems.appendChild(cartItem);

            // Add event listener to the new remove button
            cartItem.querySelector('.remove-btn').addEventListener('click', function() {
                this.closest('.cart-item').remove();
            });
        });
    });

    // Remove from cart functionality (for existing buttons)
    const removeButtons = document.querySelectorAll('.remove-btn');
    removeButtons.forEach(button => {
        button.addEventListener('click', function() {
            this.closest('.cart-item').remove();
        });
    });

    // Search functionality
    const medSearch = document.getElementById('med-search');
    if (medSearch) {
        medSearch.addEventListener('input', function() {
            const searchTerm = this.value.toLowerCase();
            const rows = document.querySelectorAll('#medicines tbody tr');

            rows.forEach(row => {
                const text = row.textContent.toLowerCase();
                if (text.includes(searchTerm)) {
                    row.style.display = '';
                } else {
                    row.style.display = 'none';
                }
            });
        });
    }

    const patientSearch = document.getElementById('patient-search');
    if (patientSearch) {
        patientSearch.addEventListener('input', function() {
            const searchTerm = this.value.toLowerCase();
            const rows = document.querySelectorAll('#patients tbody tr');

            rows.forEach(row => {
                const text = row.textContent.toLowerCase();
                if (text.includes(searchTerm)) {
                    row.style.display = '';
                } else {
                    row.style.display = 'none';
                }
            });
        });
    }

    // Checkout button functionality
    const checkoutBtn = document.querySelector('.checkout-btn');
    if (checkoutBtn) {
        checkoutBtn.addEventListener('click', function() {
            alert('Transaction completed successfully!');
            // In a real app, you would process the payment and clear the cart
        });
    }

    // Cancel transaction button
    const cancelBtn = document.querySelector('.cancel-btn');
    if (cancelBtn) {
        cancelBtn.addEventListener('click', function() {
            if (confirm('Are you sure you want to cancel this transaction?')) {
                // Clear cart items
                document.querySelectorAll('.cart-item').forEach(item => item.remove());
                // Reset form fields
                document.getElementById('patient-id').value = '';
                document.getElementById('patient-name').value = '';
                document.getElementById('prescription-id').value = '';
            }
        });
    }

    // Patient ID input functionality
    const patientIdInput = document.getElementById('patient-id');
    if (patientIdInput) {
        patientIdInput.addEventListener('input', function() {
            // In a real app, you would fetch patient details from an API
            // For now, we'll simulate by filling in a name
            const patientNameInput = document.getElementById('patient-name');
            if (this.value.trim() !== '') {
                patientNameInput.value = this.value.includes('1') ? 'John Doe' :
                                        this.value.includes('2') ? 'Jane Smith' :
                                        this.value.includes('3') ? 'Robert Johnson' : '';
            } else {
                patientNameInput.value = '';
            }
        });
    }
});