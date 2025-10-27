-- V1__init_reading_and_user.sql

-- TABELA READINGS (compatível com a Entity atual)
CREATE TABLE IF NOT EXISTS readings (
    id          BIGSERIAL PRIMARY KEY,
    sensor_id   VARCHAR(100) NOT NULL,
    sensor_type VARCHAR(50)  NOT NULL,
    sensor_value DOUBLE PRECISION NOT NULL,
    unit        VARCHAR(20)  NOT NULL,
    timestamp   TIMESTAMP    NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_readings_sensorid_ts
    ON readings (sensor_id, timestamp);

-- TABELA DE USUÁRIO (para autenticação)
CREATE TABLE IF NOT EXISTS app_user (
    id       BIGSERIAL PRIMARY KEY,
    email    VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role     VARCHAR(30)  NOT NULL DEFAULT 'USER',
    enabled  BOOLEAN      NOT NULL DEFAULT TRUE
);
