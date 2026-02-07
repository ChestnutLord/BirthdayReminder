package com.dimidev.birthdayreminder.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationSendResultDto {
    private String message;
    private List<String> recipients;
}
