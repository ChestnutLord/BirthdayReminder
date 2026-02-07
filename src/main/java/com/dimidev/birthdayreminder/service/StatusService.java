package com.dimidev.birthdayreminder.service;

import com.dimidev.birthdayreminder.model.Status;
import com.dimidev.birthdayreminder.repository.StatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StatusService {

    private final StatusRepository repository;

    public List<Status> findAll() {
        return repository.findAll();
    }

    public Optional<Status> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Status create(Status status) {
        return repository.save(status);
    }

    @Transactional
    public Status update(Status status) {
        return repository.save(status);
    }

    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
