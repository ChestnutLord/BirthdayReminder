package com.dimidev.birthdayreminder.dto.contact;

import com.dimidev.birthdayreminder.dto.status.StatusDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactDto {

    private Long id;

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    private StatusDto status;
}
