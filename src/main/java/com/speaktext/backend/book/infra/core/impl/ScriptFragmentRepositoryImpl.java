package com.speaktext.backend.book.infra.core.impl;

import com.speaktext.backend.book.script.domain.ScriptFragment;
import com.speaktext.backend.book.script.domain.repository.ScriptFragmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class ScriptFragmentRepositoryImpl implements ScriptFragmentRepository {

    private final ScriptFragmentMongoRepository scriptFragmentMongoRepository;
    private final MongoTemplate mongoTemplate;

    public Optional<ScriptFragment> findLastScriptFragment(String identificationNumber) {
        return scriptFragmentMongoRepository.findFirstByIdentificationNumberOrderByIndexDesc(identificationNumber);
    }

    @Override
    public Page<ScriptFragment> findByIdentificationNumberOrderByIndexPage(String identificationNumber, Pageable pageable) {
        return scriptFragmentMongoRepository.findAllByIdentificationNumberOrderByIndexAsc(identificationNumber, pageable);
    }

    @Override
    public List<ScriptFragment> findByIdentificationNumberOrderByIndex(String identificationNumber) {
        return scriptFragmentMongoRepository.findAllByIdentificationNumberOrderByIndex(identificationNumber);
    }

    @Override
    public List<ScriptFragment> updateAll(List<ScriptFragment> scriptFragments) {
        for (ScriptFragment fragment : scriptFragments) {
            Query query = Query.query(
                    Criteria.where("identificationNumber").is(fragment.getIdentificationNumber())
                            .and("index").is(fragment.getIndex())
            );

            Update update = new Update()
                    .set("speaker", fragment.getSpeaker())
                    .set("narration", fragment.isNarration())
                    .set("utterance", fragment.getUtterance());

            mongoTemplate.updateFirst(query, update, ScriptFragment.class);
        }
        return scriptFragments;
    }

    @Override
    public void saveAll(List<ScriptFragment> scriptFragments) {
        scriptFragmentMongoRepository.saveAll(scriptFragments);
    }

    @Override
    public Optional<ScriptFragment> findByIdentificationNumberAndIndex(String identificationNumber, Long index) {
        return scriptFragmentMongoRepository.findByIdentificationNumberAndIndex(identificationNumber, index);
    }

    @Override
    public void saveVoicePathAndLength(String identificationNumber, Long index, Long voiceLength, String voicePath) {
        Query query = Query.query(
                Criteria.where("identificationNumber").is(identificationNumber)
                        .and("index").is(index)
        );

        Update update = new Update()
                .set("voicePath", voicePath)
                .set("voiceLength", voiceLength);

        mongoTemplate.updateFirst(query, update, ScriptFragment.class);
    }

    @Override
    public List<ScriptFragment> findByIdentificationNumber(String identificationNumber) {
        return scriptFragmentMongoRepository.findAllByIdentificationNumber(identificationNumber);
    }

    @Override
    public List<ScriptFragment> findChunkByIdentificationNumberAndIndex(String identificationNumber, Long readingIndex) {
        Query query = new Query();
        query.addCriteria(Criteria.where("identificationNumber").is(identificationNumber));
        query.addCriteria(Criteria.where("index").gte(readingIndex));
        query.with(Sort.by(Sort.Direction.ASC, "index"));
        query.limit(100);
        return mongoTemplate.find(query, ScriptFragment.class);
    }

}
