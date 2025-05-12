package com.speaktext.backend.book.infra.core.impl;

import com.speaktext.backend.book.script.domain.PendingBookChunk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PendingBookChunkJpaRepository extends JpaRepository<PendingBookChunk, Long> {

    List<PendingBookChunk> findByIdentificationNumberOrderByIndexAsc(String identificationNumber);

}
