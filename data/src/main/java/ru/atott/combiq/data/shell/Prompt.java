package ru.atott.combiq.data.shell;

import org.springframework.core.annotation.Order;
import org.springframework.shell.plugin.PromptProvider;
import org.springframework.stereotype.Component;

@Order(0)
@Component
public class Prompt implements PromptProvider {
    @Override
    public String getPrompt() {
        return "combiq>";
    }

    @Override
    public String getProviderName() {
        return "combiq";
    }
}
