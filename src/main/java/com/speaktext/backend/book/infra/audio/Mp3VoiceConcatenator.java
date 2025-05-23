package com.speaktext.backend.book.infra.audio;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Mp3VoiceConcatenator implements VoiceConcatenator {

    private static final String OUTPUT_DIR = "mergedVoices";

    @Override
    public Path concatenate(List<File> voiceFiles, String identificationNumber) {
        try {
            Path outputDir = ensureOutputDirExists();
            File listFile = createConcatListFile(voiceFiles);
            File outputFile = outputDir.resolve(identificationNumber + "_merged.mp3").toFile();

            mergeWithFFmpeg(listFile, outputFile);

            if (!listFile.delete()) {
                System.err.println("listFile 삭제 실패: " + listFile.getAbsolutePath());
            }

            return outputFile.toPath();
        } catch (Exception e) {
            throw new RuntimeException("MP3 병합 중 오류 발생", e);
        }
    }

    private Path ensureOutputDirExists() throws IOException {
        Path outputDir = Path.of(OUTPUT_DIR);
        if (!Files.exists(outputDir)) {
            Files.createDirectories(outputDir);
        }
        return outputDir;
    }

    private File createConcatListFile(List<File> voiceFiles) throws IOException {
        File listFile = File.createTempFile("concat_list", ".txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(listFile))) {
            for (File voiceFile : voiceFiles) {
                writer.write("file '" + voiceFile.getAbsolutePath().replace("'", "'\\''") + "'");
                writer.newLine();
            }
        }
        return listFile;
    }

    private void mergeWithFFmpeg(File listFile, File outputFile) throws Exception {
        ProcessBuilder builder = new ProcessBuilder(
                "ffmpeg", "-f", "concat", "-safe", "0",
                "-i", listFile.getAbsolutePath(),
                "-c", "copy", outputFile.getAbsolutePath()
        );
        builder.redirectErrorStream(true);
        Process process = builder.start();

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("FFmpeg 실행 실패, exitCode=" + exitCode);
        }
    }
}
