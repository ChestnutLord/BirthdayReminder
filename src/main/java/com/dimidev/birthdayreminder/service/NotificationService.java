package com.dimidev.birthdayreminder.service;

import com.dimidev.birthdayreminder.dto.notification.NotificationSendResultDto;
import com.dimidev.birthdayreminder.model.Contact;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM");
    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    private static final String RESULT_MESSAGE =
        "Сообщение для отправки по адресам электронной почты было сформировано, " +
                "однако сама отправка в настоящий момент не реализуется. Сообщение:\n ";

    private final ContactService contactService;

    @Value("${notification.recipients:}")
    private String recipientsConfig;

    public NotificationSendResultDto sendEmailNotification() {
        List<String> recipients = parseRecipients(recipientsConfig);
        if (recipients == null || recipients.isEmpty()) {
            log.warn("Рассылка: нет получателей (notification.recipients пуст)");
            return new NotificationSendResultDto("Нет получателей для рассылки", List.of());
        }

        List<Contact> today = contactService.findBirthdaysToday(null);
        List<Contact> upcoming = contactService.findBirthdaysUpcoming(null);
        String subject = "Поздравлятор: напоминание о днях рождения";
        String body = buildEmailBody(today, upcoming);

        log.info("Рассылка: получатели={}, тема=\"{}\", сегодня ДР: {}, ближайшие: {}",
            recipients, subject, today.size(), upcoming.size());
        log.debug("Рассылка: текст письма:\n{}", body);

        return new NotificationSendResultDto(RESULT_MESSAGE + body, recipients);
    }

    private static List<String> parseRecipients(String config) {
        if (config == null || config.isBlank()) {
            return List.of();
        }
        return Arrays.stream(config.split(","))
            .map(String::trim)
            .filter(s -> !s.isEmpty())
            .collect(Collectors.toList());
    }

    private String buildEmailBody(List<Contact> today, List<Contact> upcoming) {
        StringBuilder sb = new StringBuilder();
        sb.append("Здравствуйте!\n\n");

        sb.append("Дни рождения сегодня:\n");
        if (today.isEmpty()) {
            sb.append(" никто не празднует\n");
        } else {
            today.forEach(c -> sb.append(" --- ").append(formatContact(c)).append("\n"));
        }

        sb.append("\nДни рождения в скором времени:\n");
        if (upcoming.isEmpty()) {
            sb.append(" никто не празднует\n");
        } else {
            upcoming.forEach(c -> sb.append(" --- ").append(formatContact(c)).append("\n"));
        }

        sb.append("\n Поздравлятор");
        return sb.toString();
    }

    private String formatContact(Contact c) {
        String date = c.getBirthDate().format(DATE_FORMAT);
        return c.getFirstName() + " " + c.getLastName() + " -- " + date + " -- ";
    }
}
