drop schema if exists ciudad_de_los_ninos CASCADE;
CREATE schema ciudad_de_los_ninos;

SET search_path TO "ciudad_de_los_ninos";

-- Eliminar dominio si existen
DROP DOMAIN IF EXISTS TTelefono CASCADE;
DROP DOMAIN IF EXISTS TEstado CASCADE;
DROP DOMAIN IF EXISTS TFrecuencia CASCADE;
DROP DOMAIN IF EXISTS TCuenta CASCADE;

-- Eliminar secuencia y tablas si existen
DROP SEQUENCE IF EXISTS secuencia_id_medioPago CASCADE;

DROP TABLE IF EXISTS DebitoTransferencia CASCADE;
DROP TABLE IF EXISTS TarjetaCredito CASCADE;
DROP TABLE IF EXISTS MedioPago CASCADE;
DROP TABLE IF EXISTS Aporte CASCADE;
DROP TABLE IF EXISTS Programa CASCADE;
DROP TABLE IF EXISTS Donante_auditoria CASCADE;
DROP TABLE IF EXISTS Donante CASCADE;
DROP TABLE IF EXISTS Contacto CASCADE;
DROP TABLE IF EXISTS MTel CASCADE;
DROP TABLE IF EXISTS Padrino CASCADE;

-- Crear dominios
CREATE DOMAIN TTelefono AS VARCHAR(10)
CHECK (VALUE IN ('FIJO', 'CELULAR'));

CREATE DOMAIN TEstado AS VARCHAR(20)
CHECK (VALUE IN (
		'SIN LLAMAR',
		'ERROR', 
		'EN GESTION', 
		'ADHERIDO', 
		'AMIGO', 
		'NO ACEPTA', 
		'BAJA', 
		'VOLUNTARIO'
		)
);

CREATE DOMAIN TFrecuencia AS VARCHAR (20)
CHECK (VALUE IN (
        'DIARIO',
        'SEMANAL',
        'QUINCENAL',
        'MENSUAL',
        'BIMESTRAL',
        'TRIMESTRAL',
        'ANUAL'
    )
);

CREATE DOMAIN TCuenta AS VARCHAR(20)
CHECK (VALUE IN ('DEBITO', 'TRANSFERENCIA'));

-- Crear tabla Padrino
CREATE TABLE Padrino (
  dni VARCHAR(20) NOT NULL,
  nombre_apellido VARCHAR(30) NOT NULL,
  direccion VARCHAR(30) NOT NULL,
  email VARCHAR(100) NOT NULL, 
  facebook_user VARCHAR(30),
  cod_postal INTEGER NOT NULL,
  fecha_nac DATE NOT NULL,
  CONSTRAINT pk_padrino PRIMARY KEY (dni),
  CONSTRAINT chk_fecha_nac CHECK (fecha_nac < CURRENT_DATE),	
  CONSTRAINT email_valido CHECK (email LIKE '%@%'),
  CONSTRAINT cod_postalValid CHECK (cod_postal > 0),
  CONSTRAINT unique_email UNIQUE (email)
);

-- Teléfonos del padrino
CREATE TABLE MTel (
  dni VARCHAR(20) NOT NULL,
  tipo_tel TTelefono NOT NULL,
  num VARCHAR(20) NOT NULL,
  CONSTRAINT pk_mtel PRIMARY KEY (dni, num),
  CONSTRAINT fk_mtel_padrino FOREIGN KEY (dni) REFERENCES Padrino(dni) ON UPDATE CASCADE
);

-- Contactos
CREATE TABLE Contacto (
  dni VARCHAR(20) NOT NULL,
  fecha_baja DATE,
  fecha_adhesion DATE,
  fecha_rechazo DATE,
  fecha_alta DATE,
  fecha_primer_contacto DATE,
  estado TEstado NOT NULL,
  CONSTRAINT pk_contacto PRIMARY KEY (dni),
  CONSTRAINT fk_contacto_padrino FOREIGN KEY (dni) REFERENCES Padrino(dni) ON DELETE CASCADE
);

-- Donantes
CREATE TABLE Donante (
  dni VARCHAR(20) NOT NULL,
  cuit_cuil VARCHAR(15) NOT NULL,
  ocupacion VARCHAR(50) NOT NULL,
  CONSTRAINT pk_donante PRIMARY KEY (dni),
  CONSTRAINT fk_donante_padrino FOREIGN KEY (dni) REFERENCES Padrino(dni) ON DELETE CASCADE
);

-- Programas
CREATE TABLE Programa (
  nomProg VARCHAR(50) NOT NULL,
  descripcion VARCHAR(80),
  CONSTRAINT pk_programa PRIMARY KEY (nomProg)
);

-- Tabla de medio de pago
CREATE TABLE MedioPago (
  id_medioPago SERIAL PRIMARY KEY,
  nombre_titular VARCHAR(50) NOT NULL,
  dni VARCHAR(20) NOT NULL,
  nomProg VARCHAR(50) NOT NULL,
  CONSTRAINT fk_mediopago_donante FOREIGN KEY (dni) REFERENCES Donante(dni) ON DELETE CASCADE,
  CONSTRAINT fk_mediopago_programa FOREIGN KEY (nomProg) REFERENCES Programa(nomProg) ON UPDATE CASCADE
);

-- Tarjetas de crédito
CREATE TABLE TarjetaCredito (
  id_medioPago INTEGER NOT NULL,
  nombre_tarjeta VARCHAR(30) NOT NULL,
  nro_tarjeta VARCHAR(20) NOT NULL,
  fecha_vto DATE NOT NULL,
  CONSTRAINT pk_tarjetacredito PRIMARY KEY (id_medioPago,nro_tarjeta),
  CONSTRAINT fk_tarjetacredito_mediopago FOREIGN KEY (id_medioPago) REFERENCES MedioPago(id_medioPago) ON DELETE CASCADE
);

-- Débitos y transferencias bancarias
CREATE TABLE DebitoTransferencia (
  id_medioPago INTEGER NOT NULL,
  cbu VARCHAR(20) NOT NULL,
  nro_cuenta VARCHAR(20) NOT NULL,
  tipo_cuenta TCuenta NOT NULL,
  nom_banco VARCHAR(30) NOT NULL,
  sucursal VARCHAR(20) NOT NULL,
  CONSTRAINT pk_debitotransfer PRIMARY KEY (id_medioPago,cbu),
  CONSTRAINT fk_debitotransfer_mediopago FOREIGN KEY (id_medioPago) REFERENCES MedioPago(id_medioPago) ON DELETE CASCADE
);

-- Aportes
CREATE TABLE Aporte (
  dni VARCHAR(20) NOT NULL,
  nomProg VARCHAR(50) NOT NULL,
  monto DECIMAL NOT NULL,
  frecuencia TFrecuencia NOT NULL,
  id_medioPago SERIAL NOT NULL,
  CONSTRAINT pk_aporte PRIMARY KEY (dni, nomProg),
  CONSTRAINT fk_aporte_donante FOREIGN KEY (dni) REFERENCES Donante(dni) ON DELETE CASCADE,
  CONSTRAINT fk_aporte_programa FOREIGN KEY (nomProg) REFERENCES Programa(nomProg) ON UPDATE CASCADE,
  CONSTRAINT fk_aporte_medioPago FOREIGN KEY (id_medioPago) REFERENCES MedioPago(id_medioPago) ON UPDATE CASCADE
);

-- Auditoría de donantes eliminados
CREATE TABLE ciudad_de_los_ninos.Donante_auditoria (
  dni VARCHAR(20),
  cuit_cuil VARCHAR(15),
  ocupacion VARCHAR(50),
  eliminado_por VARCHAR(50),
  eliminado_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE OR REPLACE FUNCTION ciudad_de_los_ninos.auditar_eliminacion_donante()
RETURNS TRIGGER AS $$
BEGIN
  INSERT INTO ciudad_de_los_ninos.Donante_auditoria (dni, cuit_cuil, ocupacion, eliminado_por, eliminado_en)
  VALUES (OLD.dni, OLD.cuit_cuil, OLD.ocupacion, current_user, now());
  RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_auditoria_donante_eliminacion
BEFORE DELETE ON ciudad_de_los_ninos.Donante
FOR EACH ROW
EXECUTE FUNCTION ciudad_de_los_ninos.auditar_eliminacion_donante();