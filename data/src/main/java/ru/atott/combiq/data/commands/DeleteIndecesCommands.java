package ru.atott.combiq.data.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;
import ru.atott.combiq.data.service.DeleteIndecesService;

@Component
public class DeleteIndecesCommands implements CommandMarker {
    @Autowired
    private DeleteIndecesService deleteIndecesService;

    @CliCommand("delete index")
    public void deleteIndex(
            @CliOption(key = "domain", mandatory = true) String domain,
            @CliOption(key = "version", mandatory = true) Long version) {
        deleteIndecesService.deleteIndex(domain, version);
    }
}
