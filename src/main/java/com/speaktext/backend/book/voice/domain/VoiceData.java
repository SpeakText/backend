package com.speaktext.backend.book.voice.domain;

import java.io.InputStream;

public record VoiceData(InputStream content, String fileName) {}
