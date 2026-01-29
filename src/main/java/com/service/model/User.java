package com.service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "\"user\"", schema = "grimoire")
public class User {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Size(max = 100)
    @NotNull
    @Column(name = "username", nullable = false, length = 100)
    private String username;

    @Size(max = 255)
    @Column(name = "password")
    private String password;

    @Size(max = 255)
    @Column(name = "email")
    private String email;

    @Size(max = 255)
    @Column(name = "display_name")
    private String displayName;

    @Size(max = 255)
    @Column(name = "auth_provider")
    private String authProvider;

    @Size(max = 255)
    @Column(name = "full_name")
    private String fullName;

    @ColumnDefault("0")
    @Column(name = "gold")
    private Long gold;

    @ColumnDefault("1")
    @Column(name = "level")
    private Integer level;

    @ColumnDefault("0")
    @Column(name = "current_exp")
    private Long currentExp;

    @ColumnDefault("100")
    @Column(name = "next_exp")
    private Long nextExp;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    public void setCurrentExp(Long currentExp) {
        this.currentExp = currentExp;
        if (this.currentExp < this.nextExp) {
            return;
        }
        this.setNextExp(currentExp);
        this.setLevel(this.getLevel() + 1);
    }

    private void setNextExp(Long nextExp) {
        this.nextExp = nextExp;
    }

    private void level(Integer level) {
        this.level = level;
    }


}