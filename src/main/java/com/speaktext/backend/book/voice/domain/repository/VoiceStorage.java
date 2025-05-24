package com.speaktext.backend.book.voice.domain.repository;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;

public interface VoiceStorage {

    Path save(String filename, InputStream content);
    File getVoiceFile(String filename);
    File getVoiceFileWithFilePath(String filePath);

}
