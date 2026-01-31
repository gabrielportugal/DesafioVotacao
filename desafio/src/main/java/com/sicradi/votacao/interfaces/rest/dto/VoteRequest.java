package com.sicradi.votacao.interfaces.rest.dto;

public class VoteRequest {
    private Long topicId;
    private String associateId;
    private String choice;

    public Long getTopicId() { return topicId; }
    public void setTopicId(Long topicId) { this.topicId = topicId; }
    public String getAssociateId() { return associateId; }
    public void setAssociateId(String associateId) { this.associateId = associateId; }
    public String getChoice() { return choice; }
    public void setChoice(String choice) { this.choice = choice; }
}
