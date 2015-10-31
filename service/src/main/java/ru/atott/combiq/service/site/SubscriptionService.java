package ru.atott.combiq.service.site;

public interface SubscriptionService {

    void subscribe(String email, SubscriptionType type);

    boolean hasSubscription(String email, SubscriptionType type);
}
