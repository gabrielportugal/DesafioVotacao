package com.sicradi.votacao.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "topic")
public class Topic {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "top_id")
  public Long id;

  @Column(name = "top_title", nullable = false)
  public String title;

  @Column(name = "top_description", columnDefinition = "text")
  public String description;

  @Column(name = "top_status", nullable = false)
  public String status;

  @Column(name = "top_created_at", nullable = true, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  public java.time.LocalDateTime createdAt;

  @Column(name = "top_updated_at", nullable = true, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  public java.time.LocalDateTime updatedAt;
}
