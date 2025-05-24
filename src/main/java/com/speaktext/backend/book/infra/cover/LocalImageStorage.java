package com.speaktext.backend.book.infra.cover;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Component
public class LocalImageStorage implements ImageStorage {

    private static final String OUTPUT_DIR = "coverImages";

    @Override
    public Path saveImage(MultipartFile image) {
        try {
            Path outputDir = Paths.get(OUTPUT_DIR);
            if (Files.notExists(outputDir)) {
                Files.createDirectories(outputDir);
            }

            String originalFilename = image.getOriginalFilename();
            String extension = "";

            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }

            String filename = UUID.randomUUID() + extension;
            Path targetPath = outputDir.resolve(filename);

            Files.copy(image.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            return targetPath;
        } catch (IOException e) {
            throw new RuntimeException("이미지 저장 실패", e);
        }
    }

}
