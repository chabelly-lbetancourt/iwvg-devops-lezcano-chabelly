CREATE TABLE IF NOT EXISTS users (
    id VARCHAR(10) PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    family_name VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    identity VARCHAR(20),
    address VARCHAR(255),
    city VARCHAR(100),
    province VARCHAR(100),
    postal_code VARCHAR(10),
    active BOOLEAN DEFAULT true
);

INSERT INTO users (id, first_name, family_name, email, identity, address, city, province, postal_code, active) VALUES
('1', 'Oscar', 'Fernandez', 'oscar@example.com', '12345678A', 'Calle Mayor 1', 'Madrid', 'Madrid', '28001', true),
('2', 'Ana', 'Blanco', 'ana@example.com', '87654321B', 'Gran Via 5', 'Valencia', 'Valencia', '46001', true),
('3', 'Oscar', 'Ruiz', null, null, null, null, null, null, false),
('4', 'Paula', 'Torres', 'paula@example.com', '11223344C', 'Plaza Sol 2', 'Sevilla', 'Sevilla', '   ', false),
('5', 'Antonio', 'Delgado', null, null, null, null, null, null, false),
('6', 'Paula', 'Villa', null, null, null, null, null, null, false);
