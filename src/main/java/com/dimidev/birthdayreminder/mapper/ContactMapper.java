package com.dimidev.birthdayreminder.mapper;

import com.dimidev.birthdayreminder.dto.contact.ContactCreateUpdateDto;
import com.dimidev.birthdayreminder.dto.contact.ContactDto;
import com.dimidev.birthdayreminder.dto.contact.ContactFullDetailDto;
import com.dimidev.birthdayreminder.model.Contact;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = StatusMapper.class)
public interface ContactMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "hasPhoto", ignore = true)
    @Mapping(target = "photoUrl", ignore = true)
    Contact toModel(ContactCreateUpdateDto contactCreateUpdateDto);

    ContactFullDetailDto toDto(Contact contact);

    List<ContactDto> toListDto(List<Contact> contactList);
}
