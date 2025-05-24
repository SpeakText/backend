package com.speaktext.backend.book.infra.analyzer;

import com.speaktext.backend.book.voice.application.VoiceLengthAnalyzer;
import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;
import org.springframework.stereotype.Component;

import javax.sound.sampled.AudioFileFormat;
import java.io.File;
import java.util.Map;

@Component
public class Mp3VoiceLengthAnalyzer implements VoiceLengthAnalyzer {

    private static final Long MICROSECONDS_PER_MILLISECOND = 1000L;

    @Override
    public Long getVoiceLength(File file) {
        try {
            if (!file.exists() || file.length() == 0) {
                throw new IllegalArgumentException("MP3 파일이 존재하지 않거나 비어 있습니다.");
            }

            AudioFileFormat fileFormat = new MpegAudioFileReader().getAudioFileFormat(file);
            Map<String, Object> properties = fileFormat.properties();
            Long durationMicros = (Long) properties.get("duration");

            if (durationMicros == null) {
                throw new IllegalArgumentException("MP3 파일에서 재생 시간을 읽을 수 없습니다.");
            }

            return durationMicros / MICROSECONDS_PER_MILLISECOND;
        } catch (Exception e) {
            throw new RuntimeException("MP3 재생 시간 읽기 실패", e);
        }
    }

}
