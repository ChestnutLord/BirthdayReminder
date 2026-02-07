package com.dimidev.birthdayreminder.controller.api;

import com.dimidev.birthdayreminder.dto.status.StatusCreateUpdateDto;
import com.dimidev.birthdayreminder.dto.status.StatusDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Statuses", description = "Управление статусами")
public interface StatusApi {

    @Operation(summary = "Получить список статусов")
    List<StatusDto> findAll();

    @Operation(summary = "Получить статус по id")
    StatusDto getById(Long id);

    @Operation(summary = "Создать статус")
    StatusDto create(StatusCreateUpdateDto statusCreateUpdateDto);

    @Operation(summary = "Изменить статус")
    StatusDto update(StatusCreateUpdateDto statusCreateUpdateDto, Long id);

    @Operation(summary = "Удалить статус по id")
    void deleteById(Long id);
}
