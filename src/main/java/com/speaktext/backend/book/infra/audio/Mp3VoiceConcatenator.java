package com.speaktext.backend.book.infra.audio;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Mp3VoiceConcatenator implements VoiceConcatenator {

    private static final String OUTPUT_DIR = "mergedVoices";
    private static final String SILENCE_FILE = "silenceFile/silence.mp3";

    @Override
    public Path concatenate(List<File> voiceFiles, String identificationNumber) {
        try {
            Path outputDir = ensureOutputDirExists();
            File outputFile = outputDir.resolve(identificationNumber + "_merged.mp3").toFile();

            List<File> mergedWithSilence = insertSilenceBetween(voiceFiles);
            mergeMp3sWithConcatFilter(mergedWithSilence, outputFile);

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

    /**
     * 각 MP3 사이에 silence.mp3 자동 삽입
     */
    private List<File> insertSilenceBetween(List<File> voiceFiles) {
        File silence = new File(SILENCE_FILE);
        if (!silence.exists()) {
            throw new RuntimeException("silence.mp3 파일이 존재하지 않습니다: " + silence.getAbsolutePath());
        }

        List<File> result = new ArrayList<>();
        for (int i = 0; i < voiceFiles.size(); i++) {
            result.add(voiceFiles.get(i));
            if (i < voiceFiles.size() - 1) {
                result.add(silence);  // 중간에만 삽입
            }
        }
        return result;
    }

    /**
     * ffmpeg concat 필터 방식으로 MP3 병합
     */
    private void mergeMp3sWithConcatFilter(List<File> voiceFiles, File outputFile) throws IOException, InterruptedException {
        List<String> command = new ArrayList<>();
        command.add("ffmpeg");
        command.add("-y");

        // 입력 파일 등록
        for (File file : voiceFiles) {
            command.add("-i");
            command.add(file.getAbsolutePath());
        }

        // 필터 복합 생성
        StringBuilder filter = new StringBuilder();
        for (int i = 0; i < voiceFiles.size(); i++) {
            filter.append("[").append(i).append(":a]");
        }
        filter.append("concat=n=").append(voiceFiles.size()).append(":v=0:a=1[out]");

        // 출력 명령 설정
        command.add("-filter_complex");
        command.add(filter.toString());
        command.add("-map");
        command.add("[out]");
        command.add("-acodec");
        command.add("libmp3lame");
        command.add("-b:a");
        command.add("192k");
        command.add(outputFile.getAbsolutePath());

        // 실행
        ProcessBuilder pb = new ProcessBuilder(command);
        pb.redirectErrorStream(true);
        Process process = pb.start();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("[ffmpeg concat-filter] " + line);
            }
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("FFmpeg concat 필터 실패, exitCode=" + exitCode);
        }
    }

}