package ru.atott.combiq.data.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.stereotype.Component;
import ru.atott.combiq.data.service.CreatePersonalIndexService;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Component
public class CreatePerosnalIndexCommands implements CommandMarker {
    @Autowired
    private CreatePersonalIndexService createPersonalIndexService;

    @CliCommand(value = "create index personal")
    public String create() throws InterruptedException, ExecutionException, IOException {
        return createPersonalIndexService.create(CommandsContext.env);
    }
}
