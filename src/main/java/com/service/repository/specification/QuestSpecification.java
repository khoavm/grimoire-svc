package com.service.repository.specification;

import com.service.model.Quest;
import org.springframework.data.jpa.domain.Specification;

public class QuestSpecification {

    public static Specification<Quest> conjunction() {
        return (root, query, cb) -> cb.conjunction();
    }

    public static Specification<Quest> titleOrDescriptionContains(String query) {
        return (root, q, cb) -> {
            String pattern = "%" + query.toLowerCase() + "%";
            return cb.or(
                cb.like(cb.lower(root.get("title")), pattern),
                cb.like(cb.lower(root.get("description")), pattern)
            );
        };
    }

    public static Specification<Quest> hasType(String type) {
        return (root, q, cb) -> cb.equal(root.get("type"), type);
    }
}
