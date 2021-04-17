package nl.basdebruyn.soundboardbot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import nl.basdebruyn.soundboardbot.util.MessageFactory;
import nl.basdebruyn.soundboardbot.util.MessageType;

import java.util.Objects;

public class HelpCommand extends CommandWrapper {
    public HelpCommand() {
        this.name = "help";
        this.aliases = new String[]{"h", "info", "i", "commands", "c"};
        this.help = "show all commands";
    }

    @Override
    public void executeCommand(CommandEvent event) {
        CommandClient client = event.getClient();

        StringBuilder stringBuilder = new StringBuilder();
        for (Command command : client.getCommands()) {
            stringBuilder.append(String.format(
                    "`%s%s %s%s` %s\n",
                    client.getPrefix(),
                    command.getName(),
                    getAliasesString(command),
                    getArgumentsString(command),
                    command.getHelp()
            ));
        }

        EmbedBuilder messageEmbedBuilder = MessageFactory
                .createMessageEmbedBuilder(MessageType.INFO, stringBuilder.toString())
                .setTitle("Commands")
                .setFooter("Between {} are aliases for commands i.e. -p = -play\n<> indicates an argument i.e. -p <sound effect> => -p fart");

        event.reply(messageEmbedBuilder.build());
    }

    private String getAliasesString(Command command) {
        if (command.getAliases().length <= 0) return "";

        return String.format("{%s} ", String.join(", ", command.getAliases()));
    }

    private String getArgumentsString(Command command) {
        return Objects.toString(command.getArguments(), "");
    }
}
