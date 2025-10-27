-- V2__adjust_readings_schema.sql (idempotente)

DO $$
BEGIN
  -- 1) sensor_type -> metric
  IF EXISTS (SELECT 1 FROM information_schema.columns
             WHERE table_schema='public' AND table_name='readings' AND column_name='sensor_type') THEN
    EXECUTE 'ALTER TABLE public.readings RENAME COLUMN sensor_type TO metric';
  END IF;

  -- 2) metric NOT NULL (se já existir, apenas aplica o NOT NULL)
  IF EXISTS (SELECT 1 FROM information_schema.columns
             WHERE table_schema='public' AND table_name='readings' AND column_name='metric') THEN
    -- preenche caso haja nulos
    EXECUTE 'UPDATE public.readings SET metric = COALESCE(metric, ''UNKNOWN'') WHERE metric IS NULL';
    EXECUTE 'ALTER TABLE public.readings ALTER COLUMN metric SET NOT NULL';
  END IF;

  -- 3) sensor_value -> value
  IF EXISTS (SELECT 1 FROM information_schema.columns
             WHERE table_schema='public' AND table_name='readings' AND column_name='sensor_value') THEN
    EXECUTE 'ALTER TABLE public.readings RENAME COLUMN sensor_value TO value';
  END IF;

  -- 4) timestamp -> timestamp_utc
  IF EXISTS (SELECT 1 FROM information_schema.columns
             WHERE table_schema='public' AND table_name='readings' AND column_name='timestamp') THEN
    EXECUTE 'ALTER TABLE public.readings RENAME COLUMN timestamp TO timestamp_utc';
  END IF;

  -- 5) índice em (sensor_id, timestamp_utc) se ainda não existir
  IF NOT EXISTS (
      SELECT 1 FROM pg_indexes WHERE schemaname='public' AND indexname='idx_readings_sensorid_ts'
  ) THEN
    IF EXISTS (SELECT 1 FROM information_schema.columns
               WHERE table_schema='public' AND table_name='readings' AND column_name='timestamp_utc') THEN
      EXECUTE 'CREATE INDEX idx_readings_sensorid_ts ON public.readings (sensor_id, timestamp_utc)';
    END IF;
  END IF;
END $$;
