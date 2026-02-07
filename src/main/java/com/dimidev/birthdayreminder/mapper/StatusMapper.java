package com.dimidev.birthdayreminder.mapper;

import com.dimidev.birthdayreminder.dto.status.StatusCreateUpdateDto;
import com.dimidev.birthdayreminder.dto.status.StatusDto;
import com.dimidev.birthdayreminder.model.Status;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StatusMapper {

    Status toModel(StatusDto statusDto);

    @Mapping(target = "id", ignore = true)
    Status toModel(StatusCreateUpdateDto statusCreateUpdateDto);

    StatusDto toDto(Status status);

    List<StatusDto> toListDto(List<Status> statusList);
}
