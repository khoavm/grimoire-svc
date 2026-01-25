package com.service.repository;

import com.service.model.Quest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.UUID;
import java.util.List;

@Repository
public interface QuestRepository extends JpaRepository<Quest, UUID>, JpaSpecificationExecutor<Quest> {
    // Tự động sinh query: SELECT * FROM quests WHERE type = ?
    List<Quest> findByType(String type);
}
