package com.speaktext.backend.book.infra.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    // ===== 스크립트 청크 =====
    public static final String SCRIPT_EXCHANGE = "script.chunk.exchange";
    public static final String SCRIPT_ROUTING_KEY = "script.chunk.routingKey";
    public static final String SCRIPT_QUEUE = "script.chunk.queue";

    // ===== 등장인물 음성 =====
    public static final String CHARACTER_EXCHANGE = "character.voice.exchange";
    public static final String CHARACTER_ROUTING_KEY = "character.voice.routingKey";
    public static final String CHARACTER_QUEUE = "character.voice.queue";

    // ===== 나레이션 음성 =====
    public static final String NARRATION_EXCHANGE = "narration.voice.exchange";
    public static final String NARRATION_ROUTING_KEY = "narration.voice.routingKey";
    public static final String NARRATION_QUEUE = "narration.voice.queue";

    // === RabbitTemplate ===
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

    @Bean
    public RabbitTemplate jsonRabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(new Jackson2JsonMessageConverter());
        return template;
    }

    // === 스크립트 청크 바인딩 ===
    @Bean
    public DirectExchange scriptExchange() {
        return new DirectExchange(SCRIPT_EXCHANGE);
    }

    @Bean
    public Queue scriptQueue() {
        return QueueBuilder.durable(SCRIPT_QUEUE).build();
    }

    @Bean
    public Binding scriptBinding(Queue scriptQueue, DirectExchange scriptExchange) {
        return BindingBuilder.bind(scriptQueue).to(scriptExchange).with(SCRIPT_ROUTING_KEY);
    }

    // === 등장인물 음성 바인딩 ===
    @Bean
    public DirectExchange characterExchange() {
        return new DirectExchange(CHARACTER_EXCHANGE);
    }

    @Bean
    public Queue characterQueue() {
        return QueueBuilder.durable(CHARACTER_QUEUE).build();
    }

    @Bean
    public Binding characterBinding(Queue characterQueue, DirectExchange characterExchange) {
        return BindingBuilder.bind(characterQueue).to(characterExchange).with(CHARACTER_ROUTING_KEY);
    }

    // === 나레이션 음성 바인딩 ===
    @Bean
    public DirectExchange narrationExchange() {
        return new DirectExchange(NARRATION_EXCHANGE);
    }

    @Bean
    public Queue narrationQueue() {
        return QueueBuilder.durable(NARRATION_QUEUE).build();
    }

    @Bean
    public Binding narrationBinding(Queue narrationQueue, DirectExchange narrationExchange) {
        return BindingBuilder.bind(narrationQueue).to(narrationExchange).with(NARRATION_ROUTING_KEY);
    }

}