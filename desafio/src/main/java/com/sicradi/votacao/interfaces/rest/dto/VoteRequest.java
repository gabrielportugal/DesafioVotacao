package com.sicradi.votacao.interfaces.rest.dto;

public class VoteRequest {
    private Long topicId;
    private String associateId;
    private Integer choice;

    public Long getTopicId() { return topicId; }
    public void setTopicId(Long topicId) { this.topicId = topicId; }
    public String getAssociateId() { return associateId; }
    public void setAssociateId(String associateId) { this.associateId = associateId; }
    public Integer getChoice() { return choice; }
    public void setChoice(Integer choice) { this.choice = choice; }
}
