package com.dimidev.birthdayreminder.repository;

import com.dimidev.birthdayreminder.model.Contact;
import com.dimidev.birthdayreminder.model.Status;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;

public final class ContactSpecification {

    private ContactSpecification() {
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
