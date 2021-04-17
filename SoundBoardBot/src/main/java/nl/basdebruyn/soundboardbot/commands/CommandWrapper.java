package nl.basdebruyn.soundboardbot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import nl.basdebruyn.soundboardbot.util.MessageFactory;
import nl.basdebruyn.soundboardbot.util.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class CommandWrapper extends Command {
    private final Logger logger;

    protected int numberOfArguments;
    protected String argumentSeparator = "";
    protected String argumentsHelpText = "";

    protected CommandWrapper() {
        logger = LoggerFactory.getLogger(CommandWrapper.class);
    }

    @Override
    protected void execute(CommandEvent event) {
        if (!checkArguments(event)) return;

        try {
            executeCommand(event);
        } catch (Exception exception) {
            event.reply(MessageFactory.createMessageEmbed(MessageType.ERROR, "Something went wrong"));
            logger.error("Exception thrown in command execution", exception);
        }
    }

    protected abstract void executeCommand(CommandEvent event);

    public boolean checkArguments(CommandEvent event) {
        if (this.numberOfArguments == 0) return true;

        if (!event.getArgs().isEmpty()) {
            int argumentsCount = getArgumentsFromEvent(event).length;
            if (argumentsCount >= this.numberOfArguments) return true;
        }

        replyMissingArgumentsMessage(event);
        return false;
    }

    public String[] getArgumentsFromEvent(CommandEvent event) {
        return event.getArgs().split(this.argumentSeparator, this.numberOfArguments);
    }

    private void replyMissingArgumentsMessage(CommandEvent event) {
        String message = String.format(
                "This command requires **%d** argument%s: `%s`",
                this.numberOfArguments,
                this.numberOfArguments == 1 ? "" : "s",
                this.arguments
        );
        EmbedBuilder warningBuilder = MessageFactory.createMessageEmbedBuilder(MessageType.WARNING, message);

        if (!this.argumentsHelpText.isEmpty()) {
            warningBuilder.setFooter("Info: " + this.argumentsHelpText);
        }

        event.reply(warningBuilder.build());
    }
}
