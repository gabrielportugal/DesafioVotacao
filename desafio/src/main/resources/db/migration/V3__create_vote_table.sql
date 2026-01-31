CREATE TABLE vote (
    vote_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    topic_id BIGINT NOT NULL,
    associate_id VARCHAR(20) NOT NULL,
    choice INT NOT NULL CHECK (choice IN (0, 1)),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT uq_vote_topic_associate UNIQUE (topic_id, associate_id),
    CONSTRAINT fk_vote_topic FOREIGN KEY (topic_id) REFERENCES topic(id)
);
