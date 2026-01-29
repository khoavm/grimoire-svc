package com.service.model;

import com.service.constant.QuestActionType;
import com.service.dto.entity.QuizContentDTO;
import com.service.dto.entity.RewardDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
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

    @Column(name = "usr_id")
    private UUID usrId;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "quiz_content")
    private QuizContent quizContent;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Reward {
        private Integer gold;
        private Integer exp;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QuizContent implements Serializable {
        private List<String> options;
        private String correctAnswer;
        private String explanation;
    }
}
