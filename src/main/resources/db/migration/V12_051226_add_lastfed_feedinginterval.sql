ALTER TABLE creatures
    ADD COLUMN last_fed_at TIMESTAMP,
    ADD COLUMN feeding_interval_hours INT DEFAULT 24;