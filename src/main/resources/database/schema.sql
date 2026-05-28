-- Tabla doctors
CREATE TABLE doctors
(
    id               INT AUTO_INCREMENT PRIMARY KEY,
    dni              VARCHAR(20) UNIQUE NOT NULL,
    firstName        VARCHAR(50)        NOT NULL,
    secondName       VARCHAR(50),
    lastName         VARCHAR(50)        NOT NULL,
    secondLastName   VARCHAR(50),
    email            VARCHAR(100)       NOT NULL,
    phone            VARCHAR(15),
    licenseNumber    VARCHAR(20)        NOT NULL,
    specialty        VARCHAR(100)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla patients
CREATE TABLE patients
(
    id               INT AUTO_INCREMENT PRIMARY KEY,
    dni              VARCHAR(20) UNIQUE NOT NULL,
    firstName        VARCHAR(50)        NOT NULL,
    secondName       VARCHAR(50),
    lastName         VARCHAR(50)        NOT NULL,
    secondLastName   VARCHAR(50),
    email            VARCHAR(100)       NOT NULL,
    phone            VARCHAR(15),
    birthDate        DATETIME           NULL,
    address          VARCHAR(200)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla appointments
CREATE TABLE appointments
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    dateTime   DATETIME    NOT NULL,
    patient    INT         NOT NULL,
    doctor     INT         NOT NULL,
    reason     VARCHAR(500),
    status     VARCHAR(50) NOT NULL,
    CONSTRAINT fk_patient FOREIGN KEY (patient) REFERENCES patients (id) ON DELETE CASCADE,
    CONSTRAINT fk_doctor  FOREIGN KEY (doctor)  REFERENCES doctors  (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla appointment_details
CREATE TABLE appointment_details
(
    id            INT AUTO_INCREMENT PRIMARY KEY,
    appointment   INT          NOT NULL,
    diagnosis     VARCHAR(1000),
    prescription  VARCHAR(1000),
    notes         VARCHAR(2000),
    treatment     VARCHAR(500),
    followUp      VARCHAR(500),
    CONSTRAINT fk_appointment FOREIGN KEY (appointment) REFERENCES appointments (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tablas de autenticacion
CREATE TABLE roles
(
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE users
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255)        NOT NULL,
    enabled  BOOLEAN             NOT NULL DEFAULT TRUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE user_roles
(
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user_roles_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_user_roles_role FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE refresh_tokens
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    token      VARCHAR(255) UNIQUE NOT NULL,
    user_id    BIGINT             NOT NULL,
    expires_at DATETIME           NOT NULL,
    revoked    BOOLEAN            NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_refresh_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla contact_messages para mensajes de contacto desde la app móvil
CREATE TABLE contact_messages
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre           VARCHAR(100)  NOT NULL,
    apellido         VARCHAR(100)  NOT NULL,
    email            VARCHAR(150)  NOT NULL,
    telefono         VARCHAR(20),
    mensaje          TEXT          NOT NULL,
    revisado         BOOLEAN       NOT NULL DEFAULT FALSE,
    fecha_creacion   DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_email (email),
    INDEX idx_revisado (revisado)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Datos de prueba
INSERT INTO doctors (dni, firstName, secondName, lastName, secondLastName, email, phone, licenseNumber, specialty)
VALUES
    ('12345678A', 'Luis', 'Andrés', 'Gómez', 'Martínez', 'l.gomez@clinic.com', '555-1010', 'LIC001', 'Cardiología'),
    ('87654321B', 'María', NULL, 'Pérez', 'López', 'm.perez@clinic.com', '555-2020', 'LIC002', 'Pediatría'),
    ('11223344C', 'Jorge', 'Luis', 'Fernández', 'Ruiz', 'j.fernandez@clinic.com', '555-3030', 'LIC003', 'Dermatología');

INSERT INTO patients (dni, firstName, secondName, lastName, secondLastName, email, phone, birthDate, address)
VALUES
    ('99911122A', 'Carlos', NULL, 'Santos', 'García', 'c.santos@mail.com', '555-4000', '1990-03-12 00:00:00', 'Av. Central 101'),
    ('88822233B', 'Laura', 'Beatriz', 'Ruiz', 'Mendoza', 'l.ruiz@mail.com', '555-5000', '1985-11-30 00:00:00', 'Calle Norte 55'),
    ('77733344C', 'Ana', 'María', 'Castro', 'Ramos', 'a.castro@mail.com', '555-6000', '2000-07-22 00:00:00', 'Boulevard Sur 23');

INSERT INTO appointments (dateTime, patient, doctor, reason, status)
VALUES
    ('2025-11-10 10:00:00', 1, 1, 'Chequeo general', 'SCHEDULED'),
    ('2025-10-15 14:30:00', 2, 2, 'Dolor de garganta', 'COMPLETED'),
    ('2025-10-16 09:00:00', 3, 3, 'Erupción cutánea', 'COMPLETED');

INSERT INTO appointment_details (appointment, diagnosis, prescription, notes, treatment, followUp)
VALUES
    (1, 'Hipertensión', 'Lisinopril 10mg', 'Controlar presión arterial', 'Cambios en el estilo de vida', '2025-11-18'),
    (2, 'Faringitis', 'Amoxicilina 500mg', 'Evitar irritantes', 'Reposo y líquidos', '2025-10-25'),
    (3, 'Dermatitis', 'Crema hidrocortisona', 'Evitar rascarse', 'Mantener la piel hidratada', '2025-10-26');

INSERT INTO roles (name)
VALUES ('ROLE_ADMIN'), ('ROLE_DOCTOR'), ('ROLE_PATIENT');

-- Password para todos: ChangeMe123!
INSERT INTO users (username, password, enabled)
VALUES
    ('admin', '$2a$10$BA0eOc/tUJPP0.4qXg5MoeD41DzIt2Fpl2eMk6rOp9OSnurH3I9yq', TRUE),
    ('doctor', '$2a$10$BA0eOc/tUJPP0.4qXg5MoeD41DzIt2Fpl2eMk6rOp9OSnurH3I9yq', TRUE),
    ('patient', '$2a$10$BA0eOc/tUJPP0.4qXg5MoeD41DzIt2Fpl2eMk6rOp9OSnurH3I9yq', TRUE);

INSERT INTO user_roles (user_id, role_id)
VALUES
    (1, 1),
    (2, 2),
    (3, 3);

UPDATE users SET password = '$2a$10$BA0eOc/tUJPP0.4qXg5MoeD41DzIt2Fpl2eMk6rOp9OSnurH3I9yq' WHERE username IN ('admin', 'doctor', 'patient');
SELECT id, username, password FROM users;
