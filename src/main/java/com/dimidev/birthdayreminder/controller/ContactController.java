package com.dimidev.birthdayreminder.controller;

import com.dimidev.birthdayreminder.controller.api.ContactApi;
import com.dimidev.birthdayreminder.dto.contact.ContactCreateUpdateDto;
import com.dimidev.birthdayreminder.dto.contact.ContactDto;
import com.dimidev.birthdayreminder.dto.contact.ContactFullDetailDto;
import com.dimidev.birthdayreminder.mapper.ContactMapper;
import com.dimidev.birthdayreminder.service.ContactService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
@RequiredArgsConstructor
public class ContactController implements ContactApi {

    private final ContactService contactService;
    private final ContactMapper contactMapper;

    @Override
    @GetMapping
    public List<ContactDto> getAll(@RequestParam(required = false) List<Long> statusId) {
        return contactMapper.toListDto(contactService.findAll(statusId));
    }

    @Override
    @GetMapping("/{id}")
    public ContactFullDetailDto getById(@PathVariable Long id) {
        return contactService.findById(id)
                .map(contactMapper::toDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Контакт с id " + id + " не найден"));
    }

    @Override
    @GetMapping("/birthdays/today")
    public List<ContactDto> getBirthdaysToday(@RequestParam(required = false) List<Long> statusId) {
        return contactMapper.toListDto(contactService.findBirthdaysToday(statusId));
    }

    @Override
    @GetMapping("/birthdays/upcoming")
    public List<ContactDto> getBirthdaysUpcoming(@RequestParam(required = false) List<Long> statusId) {
        return contactMapper.toListDto(contactService.findBirthdaysUpcoming(statusId));
    }

    @Override
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ContactFullDetailDto create(@Valid
                                       @RequestPart("contact")
                                       @Parameter(
                                               description = "Данные контакта в формате JSON",
                                               content = @Content(
                                                       mediaType = "application/json",
                                                       schema = @Schema(implementation = ContactCreateUpdateDto.class)
                                               )
                                       )
                                       ContactCreateUpdateDto dto,
                                       @RequestPart(value = "photo", required = false)
                                       @Parameter(
                                               description = "Фото (jpg/png)",
                                               content = @Content(mediaType = "image/jpeg, image/png")
                                       )
                                       MultipartFile photo) {
        return contactMapper.toDto(contactService.create(dto, photo));
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ContactFullDetailDto update(@Valid
                                       @RequestPart("contact")
                                       @Parameter(
                                               description = "Данные контакта в формате JSON",
                                               content = @Content(
                                                       mediaType = "application/json",
                                                       schema = @Schema(implementation = ContactCreateUpdateDto.class)
                                               )
                                       )
                                       ContactCreateUpdateDto dto,
                                       @PathVariable Long id,
                                       @Parameter(
                                               description = "Фото (jpg/png)",
                                               content = @Content(mediaType = "image/jpeg, image/png")
                                       )
                                       @RequestPart(value = "photo", required = false) MultipartFile photo) {
        return contactMapper.toDto(contactService.update(id, dto, photo));
    }

    @Override
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        contactService.deleteById(id);
    }
}