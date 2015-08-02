package ru.atott.combiq.data.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;
import ru.atott.combiq.data.service.ImportService;

@Component
public class ImportCommands implements CommandMarker {
    @Autowired
    private ImportService importService;

    @CliCommand(
            value = "import questionnare ods",
            help = "import ODS questionnaire file with columns: question, level, tags, tip")
    public String importQuestionnareOds(
            @CliOption(key = "file")
            String file,
            @CliOption(key = "name")
            String name) throws Exception {
        try {
            return importService.importQuestionnareOds(file, name);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
