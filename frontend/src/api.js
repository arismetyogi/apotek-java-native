// API functions for Pharmacy POS
class PharmacyAPI {
    constructor(baseURL = 'http://localhost:8080') {
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

    // Prescription-related API calls
    async getPrescriptions() {
        try {
            const response = await fetch(`${this.baseURL}/prescriptions`);
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return await response.json();
        } catch (error) {
            console.error('Error fetching prescriptions:', error);
            throw error;
        }
    }

    async getPrescriptionById(id) {
        try {
            const response = await fetch(`${this.baseURL}/prescriptions/${id}`);
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return await response.json();
        } catch (error) {
            console.error(`Error fetching prescription with id ${id}:`, error);
            throw error;
        }
    }

    async addPrescription(prescriptionData) {
        try {
            const response = await fetch(`${this.baseURL}/prescriptions`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(prescriptionData),
            });
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return await response.json();
        } catch (error) {
            console.error('Error adding prescription:', error);
            throw error;
        }
    }

    async updatePrescription(id, prescriptionData) {
        try {
            const response = await fetch(`${this.baseURL}/prescriptions/${id}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(prescriptionData),
            });
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return await response.json();
        } catch (error) {
            console.error(`Error updating prescription with id ${id}:`, error);
            throw error;
        }
    }

    async deletePrescription(id) {
        try {
            const response = await fetch(`${this.baseURL}/prescriptions/${id}`, {
                method: 'DELETE',
            });
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return await response.json();
        } catch (error) {
            console.error(`Error deleting prescription with id ${id}:`, error);
            throw error;
        }
    }

    // POS transaction API calls
    async createTransaction(transactionData) {
        try {
            const response = await fetch(`${this.baseURL}/transactions`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(transactionData),
            });
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return await response.json();
        } catch (error) {
            console.error('Error creating transaction:', error);
            throw error;
        }
    }

    async getTransactions() {
        try {
            const response = await fetch(`${this.baseURL}/transactions`);
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return await response.json();
        } catch (error) {
            console.error('Error fetching transactions:', error);
            throw error;
        }
    }

    async getTransactionById(id) {
        try {
            const response = await fetch(`${this.baseURL}/transactions/${id}`);
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return await response.json();
        } catch (error) {
            console.error(`Error fetching transaction with id ${id}:`, error);
            throw error;
        }
    }
}

// Export the API class for use in other modules
const pharmacyAPI = new PharmacyAPI();
export default pharmacyAPI;