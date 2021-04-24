package nl.basdebruyn.soundboardbot.bot.commands;

import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import nl.basdebruyn.soundboardbot.web.models.SoundEffect;
import nl.basdebruyn.soundboardbot.bot.util.MessageFactory;
import nl.basdebruyn.soundboardbot.bot.util.MessageType;
import nl.basdebruyn.soundboardbot.web.services.SoundEffectService;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class AddCommand extends CommandWrapper {
    private final SoundEffectService soundEffectService;

    public AddCommand(SoundEffectService soundEffectService) {
        this.name = "add";
        this.aliases = new String[]{"save"};
        this.help = "add a new sound effect";
        this.arguments = "<YT url> <name>";
        this.numberOfArguments = 2;
        this.argumentSeparator = " ";
        this.soundEffectService = soundEffectService;
    }

    @Override
    protected void executeCommand(CommandEvent event) {
        String[] args = getArgumentsFromEvent(event);

        String name = args[1].trim();
        if (soundEffectService.exists(name)) {
            replySoundAlreadyOnSoundboard(event, name);
            return;
        }

        String url = args[0].trim();
        Optional<SoundEffect> soundEffect = soundEffectService.save(new SoundEffect(name, url));
        if (soundEffect.isPresent()) {
            replySoundAddedToSoundboard(event, soundEffect.get());
        } else {
            replyInvalidSoundEffect(event);
        }
    }

    private void replyInvalidSoundEffect(CommandEvent event) {
        EmbedBuilder warningMessageBuilder = MessageFactory
                .createMessageEmbedBuilder(MessageType.WARNING, "Invalid sound effect")
                .setFooter("Info: name max 20 characters, url max 50 characters");
        event.reply(warningMessageBuilder.build());
    }

    private void replySoundAlreadyOnSoundboard(CommandEvent event, String name) {
        String message = String.format("**%s** is already on the soundboard", name);
        event.reply(MessageFactory.createMessageEmbed(MessageType.WARNING, message));
    }

    private void replySoundAddedToSoundboard(CommandEvent event, SoundEffect soundEffect) {
        String message = String.format("Added **%s** to the soundboard (*[source](%s)*)", soundEffect.name, soundEffect.url);
        event.reply(MessageFactory.createMessageEmbed(MessageType.SUCCESS, message));
    }
}
