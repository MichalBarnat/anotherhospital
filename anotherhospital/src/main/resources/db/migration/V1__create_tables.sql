CREATE TABLE doctor (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    surname VARCHAR(255),
    speciality VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    rate INTEGER,
    pesel VARCHAR(11) UNIQUE
);

CREATE TABLE patient (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    surname VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    pesel VARCHAR(11) UNIQUE,
    valid_insurance BOOLEAN
);

CREATE TABLE appointment (
    id SERIAL PRIMARY KEY,
    doctor_id INTEGER REFERENCES doctor(id),
    patient_id INTEGER REFERENCES patient(id),
    date_time TIMESTAMP,
    price DOUBLE PRECISION
);