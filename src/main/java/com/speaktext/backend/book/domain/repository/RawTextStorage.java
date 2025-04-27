package com.speaktext.backend.book.domain.repository;

public interface RawTextStorage {

    void save(String identificationNumber, String rawText);

}
