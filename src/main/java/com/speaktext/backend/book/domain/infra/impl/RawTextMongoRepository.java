package com.speaktext.backend.book.domain.infra.impl;

import com.speaktext.backend.book.domain.RawText;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RawTextMongoRepository extends MongoRepository<RawText, String> {
}
