
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
    birthDate       TIMESTAMP            NULL,
    address          VARCHAR(200)
);

-- Tabla appointments
CREATE TABLE appointments
(
    id         SERIAL PRIMARY KEY,
    dateTime  TIMESTAMP   NOT NULL,
    patientId INT         NOT NULL REFERENCES patients (id) ON DELETE CASCADE,
    doctorId  INT         NOT NULL REFERENCES doctors (id) ON DELETE CASCADE,
    reason     VARCHAR(500),
    status     VARCHAR(50) NOT NULL
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