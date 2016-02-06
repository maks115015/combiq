package ru.atott.combiq.web.view;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.atott.combiq.web.security.AuthService;

import java.util.concurrent.TimeUnit;

@Component
public class InstantMessageHolder {

    @Autowired
    private AuthService authService;

    private Cache<String, Message> messageMap =
            CacheBuilder.newBuilder()
                    .expireAfterWrite(1, TimeUnit.MINUTES)
                    .expireAfterAccess(20, TimeUnit.SECONDS)
                    .build();

    public void push(String text) {
        Message message = new Message();
        message.setText(text);
        messageMap.put(authService.getSessionId(), message);
    }

    public boolean pick() {
        return messageMap.getIfPresent(authService.getSessionId()) != null;
    }

    public Message get() {
        String sessionId = authService.getSessionId();
        Message message = messageMap.getIfPresent(sessionId);
        messageMap.invalidate(sessionId);
        return message;
    }

    public static class Message {
        private String text;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
