package com.dimidev.birthdayreminder;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
        "notification.schedule=0 0 8 * * *",
        "notification.recipients=test@test.local"
})
class BirthdayReminderApplicationTests {

    @Test
    void contextLoads() {
    }
}
