-- V3__make_readings_adjustments_idempotent.sql
-- Torna os ajustes idempotentes e conclui a transição para o schema final:
-- columns: id, sensor_id, metric, value NUMERIC(12,4), unit, timestamp_utc

DO $$
BEGIN
  ---------------------------------------------------------------------------
  -- 1) metric: renomeia sensor_type -> metric SE metric não existir.
  --    Se ambas existirem, copia dados que estiverem faltando e remove sensor_type.
  ---------------------------------------------------------------------------
  IF EXISTS (
    SELECT 1 FROM information_schema.columns
    WHERE table_schema='public' AND table_name='readings' AND column_name='sensor_type'
  ) THEN
    IF NOT EXISTS (
      SELECT 1 FROM information_schema.columns
      WHERE table_schema='public' AND table_name='readings' AND column_name='metric'
    ) THEN
      EXECUTE 'ALTER TABLE public.readings RENAME COLUMN sensor_type TO metric';
    ELSE
      -- ambas existem: garante valores e remove a antiga
      EXECUTE 'UPDATE public.readings SET metric = COALESCE(metric, sensor_type)';
      EXECUTE 'ALTER TABLE public.readings DROP COLUMN sensor_type';
    END IF;
  END IF;

  ---------------------------------------------------------------------------
  -- 2) value: renomeia sensor_value -> value; se ambas existirem, migra dados e remove sensor_value
  ---------------------------------------------------------------------------
  IF EXISTS (
    SELECT 1 FROM information_schema.columns
    WHERE table_schema='public' AND table_name='readings' AND column_name='sensor_value'
  ) THEN
    IF NOT EXISTS (
      SELECT 1 FROM information_schema.columns
      WHERE table_schema='public' AND table_name='readings' AND column_name='value'
    ) THEN
      EXECUTE 'ALTER TABLE public.readings RENAME COLUMN sensor_value TO value';
    ELSE
      EXECUTE 'UPDATE public.readings SET value = COALESCE(value, sensor_value::numeric(12,4))';
      EXECUTE 'ALTER TABLE public.readings DROP COLUMN sensor_value';
    END IF;
  END IF;

  ---------------------------------------------------------------------------
  -- 3) timestamp_utc: renomeia "timestamp" -> timestamp_utc; se ambas existirem, migra dados e remove "timestamp"
  ---------------------------------------------------------------------------
  IF EXISTS (
    SELECT 1 FROM information_schema.columns
    WHERE table_schema='public' AND table_name='readings' AND column_name='timestamp'
  ) THEN
    IF NOT EXISTS (
      SELECT 1 FROM information_schema.columns
      WHERE table_schema='public' AND table_name='readings' AND column_name='timestamp_utc'
    ) THEN
      EXECUTE 'ALTER TABLE public.readings RENAME COLUMN "timestamp" TO timestamp_utc';
    ELSE
      EXECUTE 'UPDATE public.readings SET timestamp_utc = COALESCE(timestamp_utc, "timestamp")';
      EXECUTE 'ALTER TABLE public.readings DROP COLUMN "timestamp"';
    END IF;
  END IF;
END;
$$;

-- 4) Ajusta tipo/precisão da coluna value
ALTER TABLE public.readings
  ALTER COLUMN value TYPE NUMERIC(12,4) USING value::numeric(12,4);

-- 5) Garante NOT NULL onde aplicável (ajuste se sua Entity permitir nulos)
ALTER TABLE public.readings
  ALTER COLUMN metric SET NOT NULL,
  ALTER COLUMN unit SET NOT NULL,
  ALTER COLUMN timestamp_utc SET NOT NULL;

-- 6) Índice correto (remove o antigo se existir e cria o novo se faltar)
DO $$
BEGIN
  IF EXISTS (
    SELECT 1
    FROM pg_class c
    JOIN pg_namespace n ON n.oid = c.relnamespace
    WHERE n.nspname = 'public' AND c.relname = 'idx_readings_sensorid_ts'
  ) THEN
    EXECUTE 'DROP INDEX public.idx_readings_sensorid_ts';
  END IF;
END;
$$;

CREATE INDEX IF NOT EXISTS idx_readings_sensorid_ts_utc
  ON public.readings (sensor_id, timestamp_utc);
