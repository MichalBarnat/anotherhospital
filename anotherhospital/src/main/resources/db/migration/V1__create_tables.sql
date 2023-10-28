CREATE TABLE doctor (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    speciality VARCHAR(255),
    email VARCHAR(255) UNIQUE NOT NULL,
    rate INTEGER,
    pesel VARCHAR(11) UNIQUE NOT NULL
);

CREATE TABLE patient (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    pesel VARCHAR(11) UNIQUE NOT NULL,
    valid_insurance BOOLEAN NOT NULL
);

CREATE TABLE appointment (
    id SERIAL PRIMARY KEY,
    doctor_id INTEGER REFERENCES doctor(id),
    patient_id INTEGER REFERENCES patient(id),
    date_time TIMESTAMP NOT NULL,
    price DOUBLE PRECISION
);