package com.sicradi.votacao.interfaces.rest.dto;

public class VotingSessionRequest {
    private Long topicId;
    private Integer duration;

    public Long getTopicId() { return topicId; }
    public void setTopicId(Long topicId) { this.topicId = topicId; }
    public Integer getDuration() { return duration; }
    public void setDuration(Integer duration) { this.duration = duration; }
}
