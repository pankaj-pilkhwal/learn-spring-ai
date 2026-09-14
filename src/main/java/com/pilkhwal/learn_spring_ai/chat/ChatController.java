package com.pilkhwal.learn_spring_ai.chat;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v1/chat")
public class ChatController {
    private final ChatClient chatClient;

    public ChatController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @PostMapping
    public String chat(@RequestBody ChatRequest request) {
        return chatClient.prompt()
                .user(request.message())
                .call()
                .content();
    }

    @PostMapping("/stream")
    public Flux<String> chatStream(@RequestBody ChatRequest request) {
        return chatClient.prompt()
                .system("Always answer like a professional customer support agent.")
                .user(request.message())
                .stream()
                .content();
    }


    @PostMapping("/with-memory")
    public Flux<String> chatWithMemory(@RequestBody ChatRequest request) {
        return chatClient.prompt()
                .system("Always answer like a professional customer support agent.")
                .user(request.message())
                .advisors(advisorSpec -> advisorSpec
                        .param(ChatMemory.CONVERSATION_ID, conversationId(request))
                )
                .stream()
                .content();
    }


    private String conversationId(ChatRequest request) {
        return request.conversationId() != null && !request.conversationId().isBlank()
                ? request.conversationId()
                : "default-session";
    }

    public record ChatRequest(String message, String conversationId) {
    }
}
