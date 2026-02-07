package com.dimidev.birthdayreminder.dto.contact;

import com.dimidev.birthdayreminder.dto.status.StatusDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactFullDetailDto {

    private Long id;

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    private String email;

    private boolean hasPhoto;

    private String photoUrl;

    private StatusDto status;
}
