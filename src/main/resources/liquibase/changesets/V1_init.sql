-- liquibase formatted sql
-- changeset eve:1

--Table: tickets
CREATE TABLE tickets
(
    id            BIGSERIAL PRIMARY KEY,
    process_id    VARCHAR(36),
    user_name     VARCHAR(100)  NOT NULL,
    description   VARCHAR(1000) NOT NULL,
    pc_name_or_ip VARCHAR(64),
    status        VARCHAR(32)   NOT NULL DEFAULT 'NEW',
    is_closed     BOOLEAN       NOT NULL DEFAULT FALSE,
    created_at    TIMESTAMP              DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP              DEFAULT CURRENT_TIMESTAMP
);

--Table: specialists
CREATE TABLE specialists
(
    id           BIGSERIAL PRIMARY KEY,
    name         VARCHAR(100) NOT NULL,
    email        VARCHAR(100) NOT NULL UNIQUE,
    phone_number VARCHAR(32)  NOT NULL,
    is_active    BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at   TIMESTAMP             DEFAULT CURRENT_TIMESTAMP
);

--Table: tickets_specialists
CREATE TABLE tickets_work
(
    id            BIGSERIAL PRIMARY KEY,
    ticket_id     BIGINT NOT NULL REFERENCES tickets (id) ON DELETE CASCADE,
    specialist_id BIGINT REFERENCES specialists (id),
    status        VARCHAR(32),
    assigned_at   TIMESTAMP,
    unassigned_at TIMESTAMP
);

--Table: tickets_history
CREATE TABLE tickets_history
(
    id              BIGSERIAL PRIMARY KEY,
    ticket_id       BIGINT      NOT NULL REFERENCES tickets (id) ON DELETE CASCADE,
    specialist_id   BIGINT REFERENCES specialists (id),
    previous_status VARCHAR(50),
    new_status      VARCHAR(50) NOT NULL,
    action_type     VARCHAR(50), -- ASSIGN, UNASSIGN, STATUS_UPDATE
    action_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_ticket_status ON tickets (status);
CREATE INDEX idx_ticket_specialist_ticket_id ON tickets_work (ticket_id);
CREATE INDEX idx_ticket_specialist_specialist_id ON tickets_work (specialist_id);
CREATE INDEX idx_history_ticket_id ON tickets_history (ticket_id);
CREATE INDEX idx_history_action_by_specialist_id ON tickets_history (specialist_id);
