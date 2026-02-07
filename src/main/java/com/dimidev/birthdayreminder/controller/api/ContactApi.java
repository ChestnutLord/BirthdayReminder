package com.dimidev.birthdayreminder.controller.api;

import com.dimidev.birthdayreminder.dto.contact.ContactCreateUpdateDto;
import com.dimidev.birthdayreminder.dto.contact.ContactDto;
import com.dimidev.birthdayreminder.dto.contact.ContactFullDetailDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "Contacts", description = "Управление контактами")
public interface ContactApi {

    @Operation(summary = "Получить список контактов")
    List<ContactDto> getAll(List<Long> statusId);

    @Operation(summary = "Получить контакт по id")
    ContactFullDetailDto getById(Long id);

    @Operation(summary = "Получить список контактов чьи дни рождения выпадают на текущую дату")
    List<ContactDto> getBirthdaysToday(List<Long> statusId);

    @Operation(summary = "Получить список контактов чьи дни рождения выпадают на ближайшие дни")
    List<ContactDto> getBirthdaysUpcoming(List<Long> statusId);

    @Operation(summary = "Создать контакт")
    ContactFullDetailDto create(ContactCreateUpdateDto contactCreateUpdateDto, MultipartFile photo);

    @Operation(summary = "Изменить контакт")
    ContactFullDetailDto update(ContactCreateUpdateDto contactCreateUpdateDto, Long id, MultipartFile photo);

    @Operation(summary = "Удалить контакт по id")
    void deleteById(Long id);
}
