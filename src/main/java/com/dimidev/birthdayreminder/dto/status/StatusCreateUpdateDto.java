package com.dimidev.birthdayreminder.dto.status;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusCreateUpdateDto {

    @NotBlank(message = "Нужно указать статус")
    private String status;
}
