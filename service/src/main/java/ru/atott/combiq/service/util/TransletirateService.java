package ru.atott.combiq.service.util;

public interface TransletirateService {

    String lowercaseAndTransletirate(String text);

    String lowercaseAndTransletirate(String text, int maxLength);
}
