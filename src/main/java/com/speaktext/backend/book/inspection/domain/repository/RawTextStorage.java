package com.speaktext.backend.book.inspection.domain.repository;

public interface RawTextStorage {

    void save(String rawText, String identificationNumber);
    String load(String identificationNumber);
    void delete(String identificationNumber);

}