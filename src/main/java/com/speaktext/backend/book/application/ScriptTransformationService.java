package com.speaktext.backend.book.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScriptTransformationService {

    private final ScriptInvoker scriptInvoker;
    private final ScriptPartitioner scriptPartitioner;

    public void generateScript(Long pendingBookId) {
        scriptInvoker.announce(pendingBookId);
    }

    public void splitPendingBookChunk(Long pendingBookId) {
        scriptPartitioner.split(pendingBookId);
    }

}
