package com.dimidev.birthdayreminder.controller;

import com.dimidev.birthdayreminder.controller.api.PhotoApi;
import com.dimidev.birthdayreminder.service.LoadedPhoto;
import com.dimidev.birthdayreminder.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
@RequestMapping("/api/photos")
@RequiredArgsConstructor
public class PhotoController implements PhotoApi {

    private final PhotoService photoService;

    @Override
    @GetMapping("/{contactId}")
    public ResponseEntity<Resource> getById(@PathVariable Long contactId) {
        LoadedPhoto loaded;
        try {
            loaded = photoService.loadByContactId(contactId);
        } catch (FileNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok()
                .contentType(loaded.mediaType())
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"photo\"")
                .body(loaded.resource());
    }
}
