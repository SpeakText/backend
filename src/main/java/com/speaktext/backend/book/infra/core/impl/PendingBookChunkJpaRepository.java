package com.speaktext.backend.book.infra.core.impl;

import com.speaktext.backend.book.domain.PendingBookChunk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PendingBookChunkJpaRepository extends JpaRepository<PendingBookChunk, Long> {
}
