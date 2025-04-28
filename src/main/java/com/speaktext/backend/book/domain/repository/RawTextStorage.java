package com.speaktext.backend.book.domain.repository;

public interface RawTextStorage {

    void save(String rawText, String identificationNumber);
    String load(String identificationNumber);

}