package nl.basdebruyn.soundboardbot.bot.commands;

import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.entities.MessageEmbed;
import nl.basdebruyn.soundboardbot.web.models.SoundEffect;
import nl.basdebruyn.soundboardbot.bot.util.MessageFactory;
import nl.basdebruyn.soundboardbot.bot.util.MessageType;
import nl.basdebruyn.soundboardbot.web.services.SoundEffectService;
import org.springframework.stereotype.Component;

@Component
public class UpdateCommand extends CommandWrapper {
    private final SoundEffectService soundEffectService;

    public UpdateCommand(SoundEffectService soundEffectService) {
        this.soundEffectService = soundEffectService;
        this.name = "update";
        this.arguments = "<name>,<YT url>";
        this.help = "update the Youtube url of a sound effect";
        this.numberOfArguments = 2;
        this.argumentSeparator = ",";
        this.argumentsHelpText = "<name> and <YT url> are separated by a comma";
    }

    @Override
    protected void executeCommand(CommandEvent event) {
        String[] args = getArgumentsFromEvent(event);

        String name = args[0].trim();
        if (!soundEffectService.exists(name)) {
            replySoundEffectNotOnSoundboard(event, name);
            return;
        }

        String url = args[1].trim();
        if (soundEffectService.update(new SoundEffect(name, url))) {
            replySoundEffectUpdated(event, name, url);
        } else {
            replyInvalidUrl(event);
        }
    }

    private void replyInvalidUrl(CommandEvent event) {
        MessageEmbed messageEmbed = MessageFactory
                .createMessageEmbedBuilder(MessageType.WARNING, "Invalid YouTube url")
                .setFooter("Info: YouTube url can't be longer than 100 characters")
                .build();
        event.reply(messageEmbed);
    }

    private void replySoundEffectUpdated(CommandEvent event, String name, String url) {
        String message = String.format("Updated **%s** (*[new source](%s)*)", name, url);
        event.reply(MessageFactory.createMessageEmbed(MessageType.SUCCESS, message));
    }

    private void replySoundEffectNotOnSoundboard(CommandEvent event, String oldName) {
        String message = String.format("**%s** is not on the soundboard", oldName);
        event.reply(MessageFactory.createMessageEmbed(MessageType.WARNING, message));
    }
}
