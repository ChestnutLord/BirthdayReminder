package com.dimidev.birthdayreminder.controller;

import com.dimidev.birthdayreminder.controller.api.NotificationApi;
import com.dimidev.birthdayreminder.dto.NotificationSendResultDto;
import com.dimidev.birthdayreminder.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notification")
@RequiredArgsConstructor
public class NotificationController implements NotificationApi {

    private final NotificationService service;

    @PostMapping("/send-now")
    public NotificationSendResultDto sendNow() {
        return service.sendEmailNotification();
    }
}
