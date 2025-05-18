package com.speaktext.backend.book.infra.analyzer;

import com.speaktext.backend.book.voice.application.VoiceLengthAnalyzer;
import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;
import org.springframework.stereotype.Component;

import javax.sound.sampled.AudioFileFormat;
import java.io.File;
import java.util.Map;

@Component
public class Mp3VoiceLengthAnalyzer implements VoiceLengthAnalyzer {

    @Override
    public Long getVoiceLength(File file) {
        try {
            AudioFileFormat fileFormat = new MpegAudioFileReader().getAudioFileFormat(file);
            Map<String, Object> properties = fileFormat.properties();
            Long durationMicros = (Long) properties.get("duration");
            return durationMicros != null ? durationMicros / 1_000 : 0L;
        } catch (Exception e) {
            throw new RuntimeException("MP3 재생 시간 읽기 실패", e);
        }
    }
}
