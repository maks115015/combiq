package ru.atott.combiq.data.service;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public interface CreatePersonalIndexService {
    String create(String env) throws IOException, ExecutionException, InterruptedException;
    String update(String env) throws IOException, ExecutionException, InterruptedException;
}
