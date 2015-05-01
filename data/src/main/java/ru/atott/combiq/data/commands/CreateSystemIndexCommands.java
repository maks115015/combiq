package ru.atott.combiq.data.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.stereotype.Component;
import ru.atott.combiq.data.service.CreateSystemIndexService;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Component
public class CreateSystemIndexCommands implements CommandMarker {
    @Autowired
    private CreateSystemIndexService createSystemIndexService;

    @CliCommand(value = "create index system")
    public String create() throws InterruptedException, ExecutionException, IOException {
        return createSystemIndexService.create(CommandsContext.env);
    }
}
