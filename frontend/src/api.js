// API functions for Pharmacy POS
class PharmacyAPI {
    constructor(baseURL = 'http://localhost:8080/api') {
        this.baseURL = baseURL;
    }

    // Medicine-related API calls
    async getMedicines() {
        try {
            const response = await fetch(`${this.baseURL}/medicines`);
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return await response.json();
        } catch (error) {
            console.error('Error fetching medicines:', error);
            throw error;
        }
    }

    async getMedicineById(id) {
        try {
            const response = await fetch(`${this.baseURL}/medicines/${id}`);
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return await response.json();
        } catch (error) {
            console.error(`Error fetching medicine with id ${id}:`, error);
            throw error;
        }
    }

    async addMedicine(medicineData) {
        try {
            const response = await fetch(`${this.baseURL}/medicines`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(medicineData),
            });
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return await response.json();
        } catch (error) {
            console.error('Error adding medicine:', error);
            throw error;
        }
    }

    async updateMedicine(id, medicineData) {
        try {
            const response = await fetch(`${this.baseURL}/medicines/${id}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(medicineData),
            });
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return await response.json();
        } catch (error) {
            console.error(`Error updating medicine with id ${id}:`, error);
            throw error;
        }
    }

    async deleteMedicine(id) {
        try {
            const response = await fetch(`${this.baseURL}/medicines/${id}`, {
                method: 'DELETE',
            });
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return await response.json();
        } catch (error) {
            console.error(`Error deleting medicine with id ${id}:`, error);
            throw error;
        }
    }

    // Patient-related API calls
    async getPatients() {
        try {
            const response = await fetch(`${this.baseURL}/patients`);
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return await response.json();
        } catch (error) {
            console.error('Error fetching patients:', error);
            throw error;
        }
    }

    async getPatientById(id) {
        try {
            const response = await fetch(`${this.baseURL}/patients/${id}`);
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return await response.json();
        } catch (error) {
            console.error(`Error fetching patient with id ${id}:`, error);
            throw error;
        }
    }

    async addPatient(patientData) {
        try {
            const response = await fetch(`${this.baseURL}/patients`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(patientData),
            });
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return await response.json();
        } catch (error) {
            console.error('Error adding patient:', error);
            throw error;
        }
    }

    async updatePatient(id, patientData) {
        try {
            const response = await fetch(`${this.baseURL}/patients/${id}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(patientData),
            });
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return await response.json();
        } catch (error) {
            console.error(`Error updating patient with id ${id}:`, error);
            throw error;
        }
    }

    async deletePatient(id) {
        try {
            const response = await fetch(`${this.baseURL}/patients/${id}`, {
                method: 'DELETE',
            });
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return await response.json();
        } catch (error) {
            console.error(`Error deleting patient with id ${id}:`, error);
            throw error;
        }
    }

}

// Initialize the API instance and make it available globally
const pharmacyAPI = new PharmacyAPI();
window.pharmacyAPI = pharmacyAPI; // Make it accessible globally for browser usage