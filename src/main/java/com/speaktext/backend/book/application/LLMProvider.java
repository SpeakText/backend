package com.speaktext.backend.book.application;

import com.speaktext.backend.book.application.dto.CharacterInfoDto;

import java.util.Map;

/**
 * 새로운 LLM 추가 시, client 디렉토리에 새로운 LLM 디렉토리 추가 후 어댑터를 구현합니다.
 * 해당 구현체에는 OpenFeign등을 사용하여 요청를 추상화하고, llm.api.url 을 포함하도록 해야합니다.
 * 프롬프트는 주어진 태스크에 적합한 프롬프트를 사용합니다.(ScriptPromptBuilder)
 * 설정 파일에는 새로운 LLM의 종류(llm.provider)를 추가해주고 client/config/LLMProviderConfig에 추가해줍니다.
 */
public interface LLMProvider {

    String generateScript(String chunkText, Map<String, CharacterInfoDto> mainCharacters);

}
