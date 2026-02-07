package com.dimidev.birthdayreminder.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "contacts")
@Getter
@Setter
@ToString(exclude = "status")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "status")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 128)
    private String firstName;

    @NotBlank
    @Column(nullable = false, length = 128)
    private String lastName;

    @NotNull
    @PastOrPresent(message = "Дата рождения не может быть в будущем")
    @Column(nullable = false)
    private LocalDate birthDate;

    @Email
    private String email;

    @Column(nullable = false)
    private boolean hasPhoto = false;

    private String photoUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    private Status status;
}
