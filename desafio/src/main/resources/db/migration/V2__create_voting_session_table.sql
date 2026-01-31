CREATE TABLE voting_session (
    vse_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    vse_topic_id BIGINT NOT NULL,
    vse_created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    vse_duration INT NOT NULL,
    vse_closed_at TIMESTAMP NULL,
    vse_status VARCHAR(20) NOT NULL,
    vse_updated_at TIMESTAMP NULL,
    CONSTRAINT fk_voting_session_topic FOREIGN KEY (vse_topic_id) REFERENCES topic(id)
);
