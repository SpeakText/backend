package com.speaktext.backend.book.infra.audio;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

public interface VoiceConcatenator {

    Path concatenate(List<File> voiceFiles, String identificationNumber);
}
