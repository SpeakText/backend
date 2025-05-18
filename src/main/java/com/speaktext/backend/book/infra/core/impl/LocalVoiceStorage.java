package com.speaktext.backend.book.infra.core.impl;

import com.speaktext.backend.book.voice.domain.repository.VoiceStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
@RequiredArgsConstructor
public class LocalVoiceStorage implements VoiceStorage {

    private static final String OUTPUT_DIR = "outputs";

    @Override
    public Path save(String filename, InputStream content) {
        try {
            Path outputDir = Path.of(OUTPUT_DIR);
            if (!Files.exists(outputDir)) {
                Files.createDirectories(outputDir);
            }

            Path outputPath = outputDir.resolve(filename + ".mp3");

            try (OutputStream outputStream = Files.newOutputStream(outputPath)) {
                content.transferTo(outputStream);
            }

            return outputPath;
        } catch (Exception e) {
            throw new RuntimeException("Failed to save file", e);
        }
    }

    @Override
    public File getVoiceFile(String filename) {
        return Path.of(OUTPUT_DIR, filename + ".mp3").toFile();
    }

    @Override
    public File getVoiceFileWithFilePath(String filePath) {
        return Path.of(filePath).toFile();
    }
}
