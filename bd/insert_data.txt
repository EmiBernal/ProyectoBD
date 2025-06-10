SET search_path TO ciudad_de_los_ninos;


-- 1. Programas base

INSERT INTO Programa (nomProg, descripcion) VALUES
('Apoyo Escolar', 'Asistencia a niños con bajo rendimiento académico.'),
('Conectar Igualdad', 'Entrega de notebooks a estudiantes de nivel medio.'),
('Tarjeta Alimentar', 'Subsidio para la compra de alimentos.'),
('AUH', 'Asignación Universal por Hijo para protección social.'),
('Progresar', 'Becas para estudiantes secundarios y universitarios.'),
('Manos a la Obra', 'Apoyo a microemprendedores y proyectos comunitarios.'),
('Argentina Trabaja', 'Capacitación y empleo para sectores vulnerables.'),
('Programa Primeros Años', 'Acompañamiento a familias con niños pequeños.'),
('Plan Sumar', 'Acceso a la salud pública sin obra social.'),
('Red de Apoyo Escolar', 'Tutorías y acompañamiento escolar para chicos.');

-- 2. Padrinos (y donantes al mismo tiempo)
INSERT INTO Padrino (dni, nombre_apellido, direccion, email, facebook_user, cod_postal, fecha_nac)
VALUES 
('10000001', 'Ana Torres', 'Mitre 100', 'ana@gmail.com', 'ana_fb', 5800, '1985-01-01'),
('10000002', 'Bruno Díaz', 'Belgrano 45', 'bruno@gmail.com', 'bruno_fb', 5800, '1979-03-12'),
('10000003', 'Carla Méndez', 'Sarmiento 300', 'carla@gmail.com', 'carla_fb', 5800, '1990-07-23'),
('10000004', 'Daniel Sosa', 'Urquiza 12', 'daniel@gmail.com', 'daniel_fb', 5800, '1982-10-05'),
('10000005', 'Elena Ruiz', 'Rivadavia 210', 'elena@gmail.com', 'elena_fb', 5800, '1975-09-17'),
('10000006', 'Fernando Álvarez', 'San Martín 60', 'fer@gmail.com', 'fer_fb', 5800, '1991-06-14'),
('10000007', 'Graciela Gómez', 'España 90', 'graci@gmail.com', 'graci_fb', 5800, '1988-12-30'),
('10000008', 'Héctor Silva', 'Italia 150', 'hector@gmail.com', 'hector_fb', 5800, '1983-11-25'),
('10000009', 'Isabel Pereyra', 'Patria 200', 'isa@gmail.com', 'isa_fb', 5800, '1977-05-06'),
('10000010', 'Julián Castro', 'Alvear 333', 'julian@gmail.com', 'julian_fb', 5800, '1993-08-19');

-- 3. Donantes
INSERT INTO Donante (dni, cuit_cuil, ocupacion)
VALUES 
('10000001', '27100000011', 'DOCENTE'),
('10000002', '27100000022', 'JUBILADO'),
('10000003', '27100000033', 'AUTONOMO'),
('10000004', '27100000044', 'ESTUDIANTE'),
('10000005', '27100000055', 'COMERCIANTE'),
('10000006', '27100000066', 'INDEPENDIENTE'),
('10000007', '27100000077', 'JUBILADO'),
('10000008', '27100000088', 'AUTONOMO'),
('10000009', '27100000099', 'DOCENTE'),
('10000010', '27100000100', 'OTRO');

-- 4. Contacto (solo 5 personas)
INSERT INTO Contacto (dni, fecha_alta, estado)
VALUES 
('10000001', '2023-01-01', 'ADHERIDO'),
('10000003', '2023-02-01', 'VOLUNTARIO'),
('10000005', '2023-03-01', 'NO ACEPTA'),
('10000007', '2023-04-01', 'EN GESTION'),
('10000009', '2023-05-01', 'BAJA');

-- 5. Teléfonos
INSERT INTO MTel (dni, tipo_tel, num)
VALUES 
('10000001', 'CELULAR', '3511111111'),
('10000002', 'FIJO', '0358123456'),
('10000003', 'CELULAR', '3512222222'),
('10000004', 'FIJO', '0358133333'),
('10000005', 'CELULAR', '3514444444'),
('10000006', 'FIJO', '0358155555'),
('10000007', 'CELULAR', '3516666666'),
('10000008', 'CELULAR', '3517777777'),
('10000009', 'FIJO', '0358188888'),
('10000010', 'CELULAR', '3519999999');

-- 6. Medios de pago
-- Los primeros 5 con Tarjeta
INSERT INTO MedioPago (nombre_titular, dni, nomProg) VALUES 
('Ana Torres', '10000001', 'Apoyo Escolar'),
('Bruno Díaz', '10000002', 'Conectar Igualdad'),
('Carla Méndez', '10000003', 'AUH'),
('Daniel Sosa', '10000004', 'Conectar Igualdad'),
('Elena Ruiz', '10000005', 'Apoyo Escolar');

-- Los siguientes 5 con Transferencia
INSERT INTO MedioPago (nombre_titular, dni, nomProg) VALUES 
('Fernando Álvarez', '10000006', 'Apoyo Escolar'),
('Graciela Gómez', '10000007', 'Argentina Trabaja'),
('Héctor Silva', '10000008', 'Conectar Igualdad'),
('Isabel Pereyra', '10000009', 'Apoyo Escolar'),
('Julián Castro', '10000010', 'Apoyo Escolar');

-- 7. Tarjetas de crédito (id_medioPago = 1 al 5)
INSERT INTO TarjetaCredito (id_medioPago, nombre_tarjeta, nro_tarjeta, fecha_vto)
VALUES 
(1, 'VISA', '4111111111111111', '2025-12-31'),
(2, 'MASTERCARD', '5500000000000004', '2026-06-30'),
(3, 'NARANJA', '6011000000000004', '2027-03-15'),
(4, 'VISA', '4111111111111234', '2025-11-10'),
(5, 'MASTERCARD', '5500000000001234', '2026-01-20');

-- 8. Débito/Transferencia (id_medioPago = 6 al 10)
INSERT INTO DebitoTransferencia (id_medioPago, cbu, nro_cuenta, tipo_cuenta, nom_banco, sucursal)
VALUES 
(6, '123000000000000001', '11100001', 'DEBITO', 'Banco Nación', 'Río Cuarto'),
(7, '123000000000000002', '11100002', 'TRANSFERENCIA', 'Banco Provincia', 'La Plata'),
(8, '123000000000000003', '11100003', 'TRANSFERENCIA', 'Banco Córdoba', 'Centro'),
(9, '123000000000000004', '11100004', 'DEBITO', 'Banco Galicia', 'Capital'),
(10, '123000000000000005', '11100005', 'DEBITO', 'Banco Santander', 'Sur');

-- 9. Aportes
INSERT INTO Aporte (dni, nomProg, monto, frecuencia, id_medioPago)
VALUES 
('10000001', 'Apoyo Escolar', 1000, 'DIARIO', 1),
('10000002', 'Conectar Igualdad', 2000, 'SEMANAL', 2),
('10000003', 'AUH', 1500, 'BIMESTRAL', 3),
('10000004', 'Conectar Igualdad', 800, 'MENSUAL', 4),
('10000005', 'Apoyo Escolar', 1200, 'ANUAL', 5),
('10000006', 'Apoyo Escolar', 1000, 'MENSUAL', 6),
('10000007', 'Argentina Trabaja', 1300, 'QUINCENAL', 7),
('10000008', 'Conectar Igualdad', 1100, 'MENSUAL', 8),
('10000009', 'Apoyo Escolar', 950, 'TRIMESTRAL', 9),
('10000010', 'Apoyo Escolar', 1700, 'MENSUAL', 10),

-- Agregados extra para que Ana Torres tenga más de 2 programas
('10000001', 'Conectar Igualdad', 800, 'MENSUAL', 2),
('10000001', 'AUH', 500, 'TRIMESTRAL', 3);