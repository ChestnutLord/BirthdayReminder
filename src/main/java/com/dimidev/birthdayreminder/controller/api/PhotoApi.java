package com.dimidev.birthdayreminder.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

@Tag(name = "Photos", description = "Работа с фотографиями контактов")
public interface PhotoApi {

    @Operation(summary = "Получить фото контакта по id контакта")
    ResponseEntity<Resource> getById(Long id);
}

