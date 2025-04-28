package com.speaktext.backend.common.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FileReader {

    public static String readTxtFile(MultipartFile file) {
        try {
            return new String(file.getBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("파일 읽기에 실패했습니다.", e);
        }
    }

}
