package com.dimidev.birthdayreminder.repository;

import com.dimidev.birthdayreminder.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Long> {
}
