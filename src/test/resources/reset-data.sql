DELETE
FROM users;
INSERT INTO users (id, first_name, family_name, email, identity, address, city, province, postal_code, active)
VALUES ('1', 'Oscar', 'Fernandez', 'oscar@example.com', '12345678A', 'Calle Mayor 1', 'Madrid', 'Madrid', '28001',
        true),
       ('2', 'Ana', 'Blanco', 'ana@example.com', '87654321B', 'Gran Via 5', 'Valencia', 'Valencia', '46001', true),
       ('3', 'Oscar', 'Ruiz', null, null, null, null, null, null, false),
       ('4', 'Paula', 'Torres', 'paula@example.com', '11223344C', 'Plaza Sol 2', 'Sevilla', 'Sevilla', '   ', false),
       ('5', 'Antonio', 'Delgado', null, null, null, null, null, null, false),
       ('6', 'Paula', 'Villa', null, null, null, null, null, null, false);