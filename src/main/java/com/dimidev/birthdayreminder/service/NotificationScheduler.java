package com.dimidev.birthdayreminder.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationScheduler {

    private final NotificationService service;
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationScheduler.class);

    @Scheduled(cron = "${notification.schedule}")
    public void sendEmailNotification() {
        LOGGER.info("Отправка сообщения о днях рождениях");
        service.sendEmailNotification();
        LOGGER.info("Сообщение отправлено");
    }
}
