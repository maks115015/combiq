package ru.atott.combiq.data.commands;

import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

@Component
public class SetEnvCommands implements CommandMarker {
    @CliCommand(value="set env", help="Set env variable")
    public void set(
            @CliOption(key = "env", help = "Environment's name")
            String env) {
        CommandsContext.env = env;
    }
}
