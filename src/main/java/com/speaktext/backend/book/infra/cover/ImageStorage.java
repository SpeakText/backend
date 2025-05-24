package com.speaktext.backend.book.infra.cover;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface ImageStorage {

    Path saveImage(MultipartFile image);
}
