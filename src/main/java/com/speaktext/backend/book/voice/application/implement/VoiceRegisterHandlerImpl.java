package com.speaktext.backend.book.voice.application.implement;

import com.speaktext.backend.book.script.domain.repository.ScriptFragmentRepository;
import com.speaktext.backend.book.voice.application.VoiceLengthAnalyzer;
import com.speaktext.backend.book.voice.application.VoiceRegisterHandler;
import com.speaktext.backend.book.voice.domain.VoiceData;
import com.speaktext.backend.book.voice.domain.repository.VoiceStorage;
import feign.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;

@RequiredArgsConstructor
@Component
public class VoiceRegisterHandlerImpl implements VoiceRegisterHandler {

    private final VoiceStorage voiceStorage;
    private final ScriptFragmentRepository scriptFragmentRepository;
    private final VoiceLengthAnalyzer voiceLengthAnalyzer;

    @Override
    public void registerVoice(String identificationNumber, Long index, VoiceData voiceData) {
        try (InputStream inputStream = voiceData.content()) {
            Path outputPath = voiceStorage.save(voiceData.fileName(), inputStream);
            File voiceFile = voiceStorage.getVoiceFile(voiceData.fileName());
            Long voiceLength = voiceLengthAnalyzer.getVoiceLength(voiceFile);
            scriptFragmentRepository.saveVoicePathAndLength(identificationNumber, index, voiceLength, outputPath.toString());
        } catch (Exception e) {
            throw new RuntimeException("Failed to register voice", e);
        }
    }

}
