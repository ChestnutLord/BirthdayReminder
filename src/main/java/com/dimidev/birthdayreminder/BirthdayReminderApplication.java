package com.dimidev.birthdayreminder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BirthdayReminderApplication {

    public static void main(String[] args) {
        SpringApplication.run(BirthdayReminderApplication.class, args);
    }

}
