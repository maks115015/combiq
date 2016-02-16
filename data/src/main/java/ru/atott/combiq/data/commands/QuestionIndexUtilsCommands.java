package ru.atott.combiq.data.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.stereotype.Component;
import ru.atott.combiq.data.service.CreateQuestionIndexService;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Component
public class QuestionIndexUtilsCommands implements CommandMarker {

    @Autowired
    private CreateQuestionIndexService createQuestionIndexService;

    @CliCommand(value = "update question timestamps", help = "Set missing timestamp values for questions.")
    public String updateTimestamps() throws InterruptedException, ExecutionException, IOException {
        return createQuestionIndexService.updateQuestionTimestamps();
    }

    @CliCommand(value = "migrate question stringIdsToNumbers")
    public String migrateIdsToNumbers() {
        createQuestionIndexService.migrateQuestionIdsToNumbers();
        createQuestionIndexService.migrateQuestionnaireIdsToNumbers();
        return "Done";
    }
}
