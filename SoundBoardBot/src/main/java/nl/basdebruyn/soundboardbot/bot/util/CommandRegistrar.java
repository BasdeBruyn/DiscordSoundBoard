package nl.basdebruyn.soundboardbot.bot.util;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CommandRegistrar {
    private final ApplicationContext context;

    public CommandRegistrar(ApplicationContext context) {
        this.context = context;
    }

    public void RegisterCommands(CommandClientBuilder commandClientBuilder) {
        Map<String, Command> commandsMap = context.getBeansOfType(Command.class);
        commandsMap.values().forEach(commandClientBuilder::addCommand);
    }
}
