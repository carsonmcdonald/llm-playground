package net.ioncannon.pipeline.activities;

import io.temporal.spring.boot.ActivityImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

@Component
@ActivityImpl(workers = "llm-query-worker")
public class LLMQueryActivityImpl implements LLMQueryActivity {
    private static final Log log = LogFactory.getLog(LLMQueryActivityImpl.class);

    private final ChatClient chatClient;

    public LLMQueryActivityImpl(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @Override
    public String startQuery(String message) {
        return chatClient.prompt()
                .user(message)
                .call()
                .content();
    }
}
