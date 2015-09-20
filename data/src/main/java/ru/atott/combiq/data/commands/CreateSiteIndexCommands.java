package ru.atott.combiq.data.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.stereotype.Component;
import ru.atott.combiq.data.service.CreateSiteIndexService;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Component
public class CreateSiteIndexCommands implements CommandMarker {
    @Autowired
    private CreateSiteIndexService createSiteIndexService;

    @CliCommand(value = "create index site")
    public String create() throws InterruptedException, ExecutionException, IOException {
        return createSiteIndexService.create(CommandsContext.env);
    }

    @CliCommand(value = "update index site")
    public String update() throws InterruptedException, ExecutionException, IOException {
        return createSiteIndexService.update(CommandsContext.env);
    }
}
