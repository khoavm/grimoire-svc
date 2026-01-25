package com.service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Table(name = "quest")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Quest {
    @Id
    private UUID id;
    private String title;
    private String description;
    private String type;
    private String actionType;

    @Size(max = 200)
    @Column(name = "answer_hint", length = 200)
    private String answerHint;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "reward")
    private Reward reward;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Reward {
        private Integer gold;
        private Integer exp;
    }
}
