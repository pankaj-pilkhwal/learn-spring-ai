package com.pilkhwal.learn_spring_ai.multimodal;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageDetection {
    private final ChatClient chatClient;
    @Value("classpath:/images/10hotairballoongallery.jpg")
    Resource image;


    public ImageDetection(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/image-to-text")
    public String imageToText() {
        return chatClient.prompt()
                .user(u -> {
                    u.text("can you please describe what you see in the image?");
                    u.media(MimeTypeUtils.IMAGE_JPEG, image);
                })
                .call()
                .content();
    }
}
