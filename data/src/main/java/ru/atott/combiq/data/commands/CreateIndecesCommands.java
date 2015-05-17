package ru.atott.combiq.data.commands;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.stereotype.Component;
import ru.atott.combiq.data.service.CreatePersonalIndexService;
import ru.atott.combiq.data.service.CreateQuestionIndexService;
import ru.atott.combiq.data.service.CreateSystemIndexService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

@Component
public class CreateIndecesCommands implements CommandMarker {
    @Autowired
    private CreatePersonalIndexService createPersonalIndexService;
    @Autowired
    private CreateQuestionIndexService createQuestionIndexService;
    @Autowired
    private CreateSystemIndexService createSystemIndexService;

    @CliCommand(value = "create indeces")
    public String create() throws InterruptedException, ExecutionException, IOException {
        ArrayList<String> indeces = Lists.newArrayList(
                createQuestionIndexService.create(CommandsContext.env),
                createPersonalIndexService.create(CommandsContext.env),
                createSystemIndexService.create(CommandsContext.env));
        return StringUtils.join(indeces.toArray(), ",");
    }

    @CliCommand(value = "update indeces")
    public String update() throws InterruptedException, ExecutionException, IOException {
        ArrayList<String> indeces = Lists.newArrayList(
                createQuestionIndexService.update(CommandsContext.env),
                createPersonalIndexService.update(CommandsContext.env));
        return StringUtils.join(indeces.toArray(), ",");
    }
}
