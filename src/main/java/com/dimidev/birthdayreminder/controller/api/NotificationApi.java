package com.dimidev.birthdayreminder.controller.api;

import com.dimidev.birthdayreminder.dto.notification.NotificationSendResultDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Notification", description = "Формирование сообщения для рассылки")
public interface NotificationApi {

    @Operation(summary = "Сформировать сообщение для рассылки")
    NotificationSendResultDto sendNow();
}
