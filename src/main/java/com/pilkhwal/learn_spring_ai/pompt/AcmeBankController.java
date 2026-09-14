package com.pilkhwal.learn_spring_ai.pompt;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/acme")
public class AcmeBankController {
    private final ChatClient chatClient;
    private final String systemPrompt = """
            You are a customer service assistant for AcmeBank.
            You can ONLY discuss:
            - Account balances and transactions
            - Branch locations and hours
            - General banking services
            If asked about anything else, respond: "I can only help with banking-related questions."
            """;

    public AcmeBankController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/chat")
    public String chat(@RequestParam String message) {
        return chatClient.prompt()
                .system(systemPrompt)
                .user(message)
                .call()
                .content();
    }

}
