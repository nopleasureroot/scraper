package com.service.scraper.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "target", schema = "public")
public class TargetEntity {
    @Id
    @Column(name = "uuid")
    String uuid;
    @Column(name = "url")

    private String url;
    @Column(name = "state")

    private String state;
    @Column(name = "created_at")

    private String created_at;
    @Column(name = "updated_at")

    private String updated_at;
    @Column(name = "rule_id")

    private String ruleId;
    @Column(name = "user_id")

    private String userId;


}
