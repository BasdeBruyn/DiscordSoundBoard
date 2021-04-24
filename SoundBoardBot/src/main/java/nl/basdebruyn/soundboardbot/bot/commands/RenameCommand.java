package nl.basdebruyn.soundboardbot.bot.commands;

import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.entities.MessageEmbed;
import nl.basdebruyn.soundboardbot.web.models.SoundEffect;
import nl.basdebruyn.soundboardbot.bot.util.MessageFactory;
import nl.basdebruyn.soundboardbot.bot.util.MessageType;
import nl.basdebruyn.soundboardbot.web.services.SoundEffectService;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RenameCommand extends CommandWrapper {
    private final SoundEffectService soundEffectService;

    public RenameCommand(SoundEffectService soundEffectService) {
        this.soundEffectService = soundEffectService;
        this.name = "rename";
        this.arguments = "<old name>,<new name>";
        this.help = "rename a sound effect";
        this.numberOfArguments = 2;
        this.argumentSeparator = ",";
        this.argumentsHelpText = "<old name> and <new name> are separated by a comma";
    }

    @Override
    protected void executeCommand(CommandEvent event) {
        String[] args = getArgumentsFromEvent(event);

        String oldName = args[0].trim();
        if (!soundEffectService.exists(oldName)) {
            replySoundEffectNotOnSoundboard(event, oldName);
            return;
        }

        String newName = args[1].trim();
        Optional<SoundEffect> soundEffect = soundEffectService.rename(oldName, newName);
        if (soundEffect.isPresent()) {
            replySoundEffectRenamed(event, oldName, soundEffect);
        } else {
            replyInvalidNewName(event);
        }
    }

    private void replyInvalidNewName(CommandEvent event) {
        MessageEmbed messageEmbed = MessageFactory
                .createMessageEmbedBuilder(MessageType.WARNING, "Invalid new name")
                .setFooter("Info: name can't be longer than 20 characters")
                .build();
        event.reply(messageEmbed);
    }

    private void replySoundEffectRenamed(CommandEvent event, String oldName, Optional<SoundEffect> soundEffect) {
        String message = String.format("Renamed **%s** to **%s**", oldName, soundEffect.get().name);
        event.reply(MessageFactory.createMessageEmbed(MessageType.SUCCESS, message));
    }

    private void replySoundEffectNotOnSoundboard(CommandEvent event, String oldName) {
        String message = String.format("**%s** is not on the soundboard", oldName);
        event.reply(MessageFactory.createMessageEmbed(MessageType.WARNING, message));
    }
}
