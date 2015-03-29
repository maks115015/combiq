package ru.atott.combiq.data.service;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public interface CreateQuestionIndexService {
    String create(String env) throws IOException, ExecutionException, InterruptedException;
}
