package com.speaktext.backend.book.infra.core.impl;

import com.speaktext.backend.book.voice.domain.repository.VoiceStorage;
import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;
import org.springframework.stereotype.Component;

import javax.sound.sampled.AudioFileFormat;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

@Component
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
    public Long getVoiceLength(String filename) {
        try {
            File mp3File = Path.of(OUTPUT_DIR, filename + ".mp3").toFile();
            AudioFileFormat fileFormat = new MpegAudioFileReader().getAudioFileFormat(mp3File);
            Map<String, Object> properties = fileFormat.properties();

            Long durationMicros = (Long) properties.get("duration");
            return durationMicros != null ? durationMicros / 1_000 : 0L;
        } catch (Exception e) {
            throw new RuntimeException("MP3 재생 시간 읽기 실패", e);
        }
    }

}
