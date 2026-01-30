-- V1__create_topic_table.sql
CREATE TABLE topic (
    top_id BIGSERIAL PRIMARY KEY,
    top_title VARCHAR(255) NOT NULL,
    top_description TEXT,
    top_status VARCHAR(50) NOT NULL,
    top_created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    top_updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);