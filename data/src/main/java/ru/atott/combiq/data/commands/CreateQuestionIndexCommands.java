package ru.atott.combiq.data.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.stereotype.Component;
import ru.atott.combiq.data.service.CreateQuestionIndexService;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Component
public class CreateQuestionIndexCommands implements CommandMarker {
    @Autowired
    private CreateQuestionIndexService createQuestionIndexService;

    @CliCommand(value = "create index question")
    public String create() throws InterruptedException, ExecutionException, IOException {
        return createQuestionIndexService.create(CommandsContext.env);
    }

    @CliCommand(value = "update index question")
    public String update() throws InterruptedException, ExecutionException, IOException {
        return createQuestionIndexService.update(CommandsContext.env);
    }
}
