-- liquibase formatted sql
-- changeset eve:1

CREATE TABLE users
(
    id           BIGSERIAL PRIMARY KEY,
    name         VARCHAR(100) NOT NULL,
    email        VARCHAR(100) NOT NULL UNIQUE,
    password     VARCHAR(100) NOT NULL,
    role         VARCHAR(32)  NOT NULL,
    phone_number VARCHAR(32)  NOT NULL,
    is_active    BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at   TIMESTAMP             DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE tickets
(
    id            BIGSERIAL PRIMARY KEY,
    user_id       BIGINT REFERENCES users (id),
    process_id    VARCHAR(36),
    user_name     VARCHAR(100)  NOT NULL,
    description   VARCHAR(1000) NOT NULL,
    pc_name_or_ip VARCHAR(64),
    status        VARCHAR(32)   NOT NULL DEFAULT 'NEW',
    is_closed     BOOLEAN       NOT NULL DEFAULT FALSE,
    is_overdue    BOOLEAN       NOT NULL DEFAULT FALSE,
    created_at    TIMESTAMP              DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP              DEFAULT CURRENT_TIMESTAMP,
    closed_at     TIMESTAMP
);

CREATE TABLE tickets_work
(
    id            BIGSERIAL PRIMARY KEY,
    ticket_id     BIGINT NOT NULL REFERENCES tickets (id) ON DELETE CASCADE,
    specialist_id BIGINT REFERENCES users (id),
    status        VARCHAR(32),
    assigned_at   TIMESTAMP,
    unassigned_at TIMESTAMP
);

CREATE TABLE tickets_history
(
    id              BIGSERIAL PRIMARY KEY,
    ticket_id       BIGINT      NOT NULL REFERENCES tickets (id) ON DELETE CASCADE,
    specialist_id   BIGINT REFERENCES users (id),
    previous_status VARCHAR(50),
    new_status      VARCHAR(50) NOT NULL,
    action_type     VARCHAR(50),
    action_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_ticket_status ON tickets (status);
CREATE INDEX idx_ticket_specialist_ticket_id ON tickets_work (ticket_id);
CREATE INDEX idx_ticket_specialist_specialist_id ON tickets_work (specialist_id);
CREATE INDEX idx_history_ticket_id ON tickets_history (ticket_id);
CREATE INDEX idx_history_action_by_specialist_id ON tickets_history (specialist_id);
