package com.dimidev.birthdayreminder.service;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;

public record LoadedPhoto(Resource resource, MediaType mediaType) {
}
