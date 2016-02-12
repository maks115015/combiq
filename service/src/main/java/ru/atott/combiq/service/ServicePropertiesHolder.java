package ru.atott.combiq.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class ServicePropertiesHolder {

    public static String getFilesPath() {
        return holdedFilesPath;
    }

    private static String holdedFilesPath;

    @Value("${service.files.path}")
    private String filesPath;

    @PostConstruct
    public void postConstruct() {
        holdedFilesPath = filesPath;
    }
}
