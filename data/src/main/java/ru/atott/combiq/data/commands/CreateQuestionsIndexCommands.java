package ru.atott.combiq.data.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.stereotype.Component;
import ru.atott.combiq.data.service.CreateQuestionIndexService;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Component
public class CreateQuestionsIndexCommands implements CommandMarker {
    @Autowired
    private CreateQuestionIndexService createQuestionIndexService;

    @CliCommand(value = "create index question")
    public String create() throws InterruptedException, ExecutionException, IOException {
        return createQuestionIndexService.create(CommandsContext.env);
    }
}
