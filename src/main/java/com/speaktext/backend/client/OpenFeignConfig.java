package com.speaktext.backend.client;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients("com.speaktext.backend.book")
class OpenFeignConfig {
}
