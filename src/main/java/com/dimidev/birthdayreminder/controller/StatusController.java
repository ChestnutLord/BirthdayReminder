package com.dimidev.birthdayreminder.controller;

import com.dimidev.birthdayreminder.controller.api.StatusApi;
import com.dimidev.birthdayreminder.dto.status.StatusCreateUpdateDto;
import com.dimidev.birthdayreminder.dto.status.StatusDto;
import com.dimidev.birthdayreminder.mapper.StatusMapper;
import com.dimidev.birthdayreminder.model.Status;
import com.dimidev.birthdayreminder.service.StatusService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/statuses")
@RequiredArgsConstructor
public class StatusController implements StatusApi {

    private final StatusService statusService;
    private final StatusMapper statusMapper;

    @Override
    @GetMapping
    public List<StatusDto> findAll() {
        return statusMapper.toListDto(statusService.findAll());
    }

    @Override
    @GetMapping("/{id}")
    public StatusDto getById(@PathVariable Long id) {
        return statusService.findById(id)
                .map(statusMapper::toDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Статус с id " + id + " не найден"));
    }

    @Override
    @PostMapping
    public StatusDto create(@Valid @RequestBody StatusCreateUpdateDto statusCreateUpdateDto) {
        Status status = statusMapper.toModel(statusCreateUpdateDto);
        return statusMapper.toDto(statusService.create(status));
    }

    @Override
    @PutMapping("/{id}")
    public StatusDto update(@Valid @RequestBody StatusCreateUpdateDto statusCreateUpdateDto, @PathVariable Long id) {
        Status status = statusMapper.toModel(statusCreateUpdateDto);
        status.setId(id);
        return statusMapper.toDto(statusService.update(status));
    }

    @Override
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        statusService.deleteById(id);
    }
}
