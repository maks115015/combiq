package ru.atott.combiq.data.shell;

import org.springframework.core.annotation.Order;
import org.springframework.shell.plugin.BannerProvider;
import org.springframework.stereotype.Component;

@Order(0)
@Component
public class Banner implements BannerProvider {
    @Override
    public String getBanner() {
        return "";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    @Override
    public String getWelcomeMessage() {
        return "";
    }

    @Override
    public String getProviderName() {
        return "combiq";
    }
}
