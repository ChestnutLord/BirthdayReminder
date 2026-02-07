package com.dimidev.birthdayreminder.service;

import com.dimidev.birthdayreminder.dto.contact.ContactCreateUpdateDto;
import com.dimidev.birthdayreminder.mapper.ContactMapper;
import com.dimidev.birthdayreminder.model.Contact;
import com.dimidev.birthdayreminder.repository.ContactRepository;
import com.dimidev.birthdayreminder.repository.ContactSpecification;
import com.dimidev.birthdayreminder.repository.StatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository repository;
    private final ContactMapper contactMapper;
    private final StatusRepository statusRepository;
    private final PhotoService photoService;

    public List<Contact> findAll(List<Long> statusIds) {
        Specification<Contact> spec = ContactSpecification.hasStatusIdIn(statusIds);
        return repository.findAll(spec);
    }


    public Optional<Contact> findById(Long id) {
        return repository.findById(id);
    }

    public List<Contact> findBirthdaysToday(List<Long> statusIds) {
        if (statusIds == null || statusIds.isEmpty()) {
            return repository.findBirthdaysToday();
        }
        return repository.findBirthdaysToday().stream()
                .filter(c -> c.getStatus() != null && statusIds.contains(c.getStatus().getId()))
                .toList();
    }

    public List<Contact> findBirthdaysUpcoming(List<Long> statusIds) {
        if (statusIds == null || statusIds.isEmpty()) {
            return repository.findBirthdaysUpcoming();
        }
        return repository.findBirthdaysUpcoming().stream()
                .filter(c -> c.getStatus() != null && statusIds.contains(c.getStatus().getId()))
                .toList();
    }

    public Contact create(ContactCreateUpdateDto dto, MultipartFile photo) {
        Contact contact = contactMapper.toModel(dto);
        setStatusFromId(contact, dto.getStatusId());
        contact = repository.save(contact);
        savePhotoIfPresent(contact, photo);
        return repository.save(contact);
    }

    public Contact update(Long id, ContactCreateUpdateDto dto, MultipartFile photo) {
        Contact contact = contactMapper.toModel(dto);
        contact.setId(id);
        setStatusFromId(contact, dto.getStatusId());
        contact = repository.save(contact);
        handlePhotoUpdate(contact, photo, dto.getRemovePhoto());
        return repository.save(contact);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
        try {
            photoService.delete(id);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка удаления фото для контакта id=" + id, e);
        }
    }

    private void setStatusFromId(Contact contact, Long statusId) {
        if (statusId == null) {
            contact.setStatus(null);
            return;
        }
        statusRepository.findById(statusId).ifPresent(contact::setStatus);
    }

    private void savePhotoIfPresent(Contact contact, MultipartFile photo) {
        if (photo == null || photo.isEmpty()) {
            return;
        }

        try {
            String filename = photoService.save(photo, contact.getId());
            contact.setHasPhoto(true);
            contact.setPhotoUrl("/api/photos/" + filename);
        } catch (IOException e) {
            throw new RuntimeException(
                    "Ошибка сохранения фото для контакта id=" + contact.getId(), e
            );
        }
    }

    private void handlePhotoUpdate(Contact contact,
                                   MultipartFile photo,
                                   Boolean removePhoto) {
        try {
            if (removePhoto) {
                photoService.delete(contact.getId());
                contact.setHasPhoto(false);
                contact.setPhotoUrl(null);
                return;
            }

            if (photo != null && !photo.isEmpty()) {
                String filename = photoService.save(photo, contact.getId());
                contact.setHasPhoto(true);
                contact.setPhotoUrl("/api/photos/" + filename);
            }

        } catch (IOException e) {
            throw new RuntimeException("Ошибка обработки фото контакта", e);
        }
    }
}

