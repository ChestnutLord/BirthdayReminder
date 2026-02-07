package com.dimidev.birthdayreminder.repository;

import com.dimidev.birthdayreminder.model.Contact;
import com.dimidev.birthdayreminder.model.Status;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;

public final class ContactSpecification {

    private ContactSpecification() {
    }

    public static Specification<Contact> nameContainsIgnoreCase(String search) {
        return (root, query, cb) -> {
            if (search == null || search.isBlank()) {
                return cb.conjunction();
            }
            String pattern = "%" + search.trim().toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("firstName")), pattern),
                    cb.like(cb.lower(root.get("lastName")), pattern)
            );
        };
    }

    public static Specification<Contact> hasStatusIdIn(Collection<Long> statusIds) {
        return (root, query, cb) -> {
            if (statusIds == null || statusIds.isEmpty()) {
                return cb.conjunction();
            }
            Join<Contact, Status> statusJoin = root.join("status");
            return statusJoin.get("id").in(statusIds);
        };
    }
}
