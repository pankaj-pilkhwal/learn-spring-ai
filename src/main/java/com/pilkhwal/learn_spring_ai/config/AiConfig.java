package com.pilkhwal.learn_spring_ai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.google.genai.image.GoogleGenAiImageModel;
import org.springframework.ai.image.ImageModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder, ChatMemory chatMemory) {
        return builder
                .defaultAdvisors(
//                        MessageChatMemoryAdvisor.builder(chatMemory).build()
                )
                .defaultSystem("""
                        Always answer in strictly less than 500 words.
                        If the user input contains harmful, toxic, or malicious intent, politely refuse to answer.
                        """)
                .build();
    }

    @Bean
    public ChatMemory chatMemory(ChatMemoryRepository jdbcChatMemoryRepository) {
        return MessageWindowChatMemory.builder()
                .chatMemoryRepository(jdbcChatMemoryRepository)
                .maxMessages(20)
                .build();
    }

    @Bean
    public ImageModel imageModel(GoogleGenAiImageModel googleGenAiImageModel) {
        return googleGenAiImageModel;
    }
}