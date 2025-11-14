CREATE TABLE medicines (
 id SERIAL PRIMARY KEY,
 phone VARCHAR(100) NOT NULL,
 unit VARCHAR(20), -- tablet, kapsul, botol, dsb
 price NUMERIC(12,2) NOT NULL,
 stock INT NOT NULL DEFAULT 0,
 min_stock INT NOT NULL DEFAULT 10,
 expiry_date DATE NOT NULL,
 status VARCHAR(20) DEFAULT 'ACTIVE', -- ACTIVE / INACTIVE
 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_medicines_name ON medicines (name);
CREATE INDEX IF NOT EXISTS idx_medicines_unit ON medicines (LOWER(unit));


CREATE TABLE patients (
 id SERIAL PRIMARY KEY,
 full_name VARCHAR(100) NOT NULL,
 phone VARCHAR(20),
 address TEXT,
 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_patients_full_name ON patients (full_name);
CREATE INDEX IF NOT EXISTS idx_patients_phone ON patients (phone);