package com.dimidev.birthdayreminder.repository;

import com.dimidev.birthdayreminder.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long>, JpaSpecificationExecutor<Contact> {

    @Query(value = """
            SELECT * FROM contacts c
            WHERE EXTRACT(MONTH FROM c.birth_date) = EXTRACT(MONTH FROM CURRENT_DATE)
              AND EXTRACT(DAY FROM c.birth_date) = EXTRACT(DAY FROM CURRENT_DATE)
            ORDER BY c.last_name, c.first_name
            """, nativeQuery = true)
    List<Contact> findBirthdaysToday();

    @Query(value = """
            SELECT * FROM contacts c
            WHERE (EXTRACT(MONTH FROM c.birth_date), EXTRACT(DAY FROM c.birth_date)) IN (
                SELECT EXTRACT(MONTH FROM d::date), EXTRACT(DAY FROM d::date)
                FROM generate_series(CURRENT_DATE + 1, CURRENT_DATE + 6, '1 day'::interval) AS d
            )
            ORDER BY EXTRACT(MONTH FROM c.birth_date), EXTRACT(DAY FROM c.birth_date), c.last_name, c.first_name
            """, nativeQuery = true)
    List<Contact> findBirthdaysUpcoming();
}
