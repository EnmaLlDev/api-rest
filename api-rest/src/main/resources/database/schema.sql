
-- Tabla doctors
CREATE TABLE doctors
(
    id               SERIAL PRIMARY KEY,
    dni              VARCHAR(20) UNIQUE NOT NULL,
    firstName       VARCHAR(50)        NOT NULL,
    secondName      VARCHAR(50),
    lastName        VARCHAR(50)        NOT NULL,
    secondLastName VARCHAR(50),
    email            VARCHAR(100)       NOT NULL,
    phone            VARCHAR(15),
    licenseNumber   VARCHAR(20)        NOT NULL,
    specialty        VARCHAR(100)
);

-- Tabla patients
CREATE TABLE patients
(
    id               SERIAL PRIMARY KEY,
    dni              VARCHAR(20) UNIQUE NOT NULL,
    firstName       VARCHAR(50)        NOT NULL,
    secondName      VARCHAR(50),
    lastName        VARCHAR(50)        NOT NULL,
    secondLastName VARCHAR(50),
    email            VARCHAR(100)       NOT NULL,
    phone            VARCHAR(15),
    birthDate       TIMESTAMP          NOT NULL,
    address          VARCHAR(200)
);

-- Tabla appointments
CREATE TABLE appointments
(
    id         SERIAL PRIMARY KEY,
    dateTime  TIMESTAMP   NOT NULL,
    patientId INT         NOT NULL REFERENCES patients (id),
    doctorId  INT         NOT NULL REFERENCES doctors (id),
    reason     VARCHAR(500),
    status     VARCHAR(50) NOT NULL
);

-- Tabla treatments
CREATE TABLE treatments
(
    id          SERIAL PRIMARY KEY,
    patientId  INT            NOT NULL REFERENCES patients (id),
    doctorId   INT            NOT NULL REFERENCES doctors (id),
    description TEXT           NOT NULL,
    cost        DECIMAL(10, 2) NOT NULL,
    startDate  TIMESTAMP      NOT NULL,
    endDate    TIMESTAMP,
    status      VARCHAR(50)    NOT NULL
);

-- Tabla invoices
CREATE TABLE invoices(
    id           SERIAL PRIMARY KEY,
    description  TEXT           NOT NULL,
    treatmentId INT            NOT NULL REFERENCES treatments (id),
    totalAmount DECIMAL(10, 2) NOT NULL,
    issueDate   TIMESTAMP      NOT NULL,
    status       INT            NOT NULL DEFAULT 0
);

-- Tabla diagnostics
CREATE TABLE diagnostics
(
    id          SERIAL PRIMARY KEY,
    description TEXT      NOT NULL,
    date        TIMESTAMP NOT NULL,
    doctorId   INT       NOT NULL REFERENCES doctors (id),
    patientId  INT       NOT NULL REFERENCES patients (id)
);

-- Datos de prueba
INSERT INTO doctors (dni, firstName, secondName, lastName, secondLastName, email, phone, licenseNumber, specialty)
VALUES ('12345678A', 'Luis', 'Andrés', 'Gómez', 'Martínez', 'l.gomez@clinic.com', '555-1010', 'LIC001', 'Cardiología'),
       ('87654321B', 'María', NULL, 'Pérez', 'López', 'm.perez@clinic.com', '555-2020', 'LIC002', 'Pediatría'),
       ('11223344C', 'Jorge', 'Luis', 'Fernández', 'Ruiz', 'j.fernandez@clinic.com', '555-3030', 'LIC003',
        'Dermatología');

INSERT INTO patients (dni, firstName, secondName, lastName, secondLastName, email, phone, birthDate, address)
VALUES ('99911122A', 'Carlos', NULL, 'Santos', 'García', 'c.santos@mail.com', '555-4000', '1990-03-12',
        'Av. Central 101'),
       ('88822233B', 'Laura', 'Beatriz', 'Ruiz', 'Mendoza', 'l.ruiz@mail.com', '555-5000', '1985-11-30',
        'Calle Norte 55'),
       ('77733344C', 'Ana', 'María', 'Castro', 'Ramos', 'a.castro@mail.com', '555-6000', '2000-07-22',
        'Boulevard Sur 23');

INSERT INTO appointments (dateTime, patientId, doctorId, reason, status)
VALUES ('2025-10-18 10:00:00', 1, 1, 'Chequeo general', 'SCHEDULED'),
       ('2025-10-18 11:30:00', 2, 2, 'Dolor de garganta', 'SCHEDULED'),
       ('2025-10-19 09:00:00', 3, 3, 'Revisión de piel', 'SCHEDULED');

INSERT INTO treatments (patientId, doctorId, description, cost, startDate, endDate, status)
VALUES (1, 1, 'Tratamiento de presión arterial', 150.00, '2025-10-01', '2025-10-15', 'ACTIVE'),
       (2, 2, 'Antibióticos para infección respiratoria', 80.00, '2025-10-10', NULL, 'ACTIVE'),
       (3, 3, 'Crema dermatológica para irritación', 60.00, '2025-09-25', '2025-10-05', 'ACTIVE');

INSERT INTO invoices (treatmentId, description, totalAmount, issueDate, status)
VALUES (1, 'Consulta ordinaria',150.00, '2025-10-16', 1),
       (2, 'Revision', 80.00, '2025-10-17', 0),
       (3, 'Analiticas',60.00, '2025-10-06', 1);

INSERT INTO diagnostics (description, date, doctorId, patientId)
VALUES ('Hipertensión controlada', '2025-10-01', 1, 1),
       ('Amigdalitis leve', '2025-10-12', 2, 2),
       ('Dermatitis tratada', '2025-09-26', 3, 3);


