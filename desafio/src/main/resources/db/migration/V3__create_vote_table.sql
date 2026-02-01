CREATE TABLE vote (
    vot_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    vot_topic_id BIGINT NOT NULL,
    vot_associate_id VARCHAR(20) NOT NULL,
    vot_choice INT NOT NULL CHECK (vot_choice IN (0, 1)),
    vot_created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    vot_updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT uq_vote_topic_associate UNIQUE (vot_topic_id, vot_associate_id),
    CONSTRAINT fk_vote_topic FOREIGN KEY (vot_topic_id) REFERENCES topic(id)
);
