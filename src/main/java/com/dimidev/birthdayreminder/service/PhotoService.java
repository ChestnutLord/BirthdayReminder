package com.dimidev.birthdayreminder.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Stream;

@Service
public class PhotoService {

    private final Path photosDirectory;

    public PhotoService(@Value("${spring.storage.photos-dir}") String photosDir) {
        this.photosDirectory = Paths.get(photosDir).toAbsolutePath().normalize();
    }

    @PostConstruct
    public void init() throws IOException {
        Files.createDirectories(photosDirectory);
    }

    public String save(MultipartFile file, Long contactId) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Файл не передан или пустой");
        }

        String extension = getFileExtension(file.getOriginalFilename());
        if (!List.of(".jpg", ".jpeg", ".png").contains(extension.toLowerCase())) {
            throw new IllegalArgumentException("Разрешены только jpg/jpeg/png");
        }

        String filename = contactId.toString() + extension;
        Path target = photosDirectory.resolve(filename);

        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

        return filename;
    }

    public LoadedPhoto loadByContactId(Long contactId) throws IOException {
        Path file = resolvePhotoPath(contactId);
        Resource resource = new UrlResource(file.toUri());

        if (!resource.exists() || !resource.isReadable()) {
            throw new FileNotFoundException("Фото для контакта " + contactId + " не найдено или недоступно");
        }

        MediaType mediaType = getMediaTypeFromPath(file);
        return new LoadedPhoto(resource, mediaType);
    }

    private Path resolvePhotoPath(Long contactId) throws IOException {
        try (Stream<Path> files = Files.list(photosDirectory)) {
            return files
                    .filter(p -> p.getFileName().toString().startsWith(contactId.toString() + "."))
                    .findFirst()
                    .orElseThrow(() -> new FileNotFoundException("Фото для контакта " + contactId + " не найдено"));
        }
    }

    private static MediaType getMediaTypeFromPath(Path path) {
        String name = path.getFileName().toString().toLowerCase();
        if (name.endsWith(".png")) {
            return MediaType.IMAGE_PNG;
        }
        if (name.endsWith(".jpg") || name.endsWith(".jpeg")) {
            return MediaType.IMAGE_JPEG;
        }
        return MediaType.APPLICATION_OCTET_STREAM;
    }


    public void delete(Long contactId) throws IOException {
        try (Stream<Path> files = Files.list(photosDirectory)) {
            files
                    .filter(p -> p.getFileName().toString().startsWith(contactId.toString()))
                    .forEach(p -> {
                        try {
                            Files.deleteIfExists(p);
                        } catch (IOException e) {
                            throw new UncheckedIOException(e);
                        }
                    });
        }
    }

    private String getFileExtension(String originalName) {
        if (originalName == null || !originalName.contains(".")) {
            return ".jpg";
        }
        return originalName.substring(originalName.lastIndexOf("."));
    }
}
