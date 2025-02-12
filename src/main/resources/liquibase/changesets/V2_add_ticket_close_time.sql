-- liquibase formatted sql
-- changeset eve:2

ALTER TABLE tickets
    ADD COLUMN closed_at  TIMESTAMP,
    ADD COLUMN is_overdue BOOLEAN NOT NULL DEFAULT FALSE;
