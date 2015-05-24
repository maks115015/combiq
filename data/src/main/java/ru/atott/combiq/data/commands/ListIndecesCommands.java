package ru.atott.combiq.data.commands;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;
import ru.atott.combiq.data.service.ListIndecesService;

@Component
public class ListIndecesCommands implements CommandMarker {
    @Autowired
    private ListIndecesService listIndecesService;

    @CliCommand("list indeces")
    public String list(@CliOption(key = "domain", mandatory = true) String domain) {
        return StringUtils.join(listIndecesService.list(domain), ",");
    }
}

