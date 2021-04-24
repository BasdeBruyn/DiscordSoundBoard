package nl.basdebruyn.soundboardbot.bot.commands;

import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import nl.basdebruyn.soundboardbot.web.models.SoundEffect;
import nl.basdebruyn.soundboardbot.bot.util.MessageFactory;
import nl.basdebruyn.soundboardbot.bot.util.MessageType;
import nl.basdebruyn.soundboardbot.web.services.SoundEffectService;
import nl.basdebruyn.soundboardbot.bot.util.TableUtil;
import org.springframework.stereotype.Component;

@Component
public class ListCommand extends CommandWrapper {
    private final SoundEffectService soundEffectService;

    public ListCommand(SoundEffectService soundEffectService) {
        this.soundEffectService = soundEffectService;
        this.name = "list";
        this.aliases = new String[]{"l", "all"};
        this.help = "show all sound effects";
    }

    @Override
    protected void executeCommand(CommandEvent event) {
        SoundEffect[] soundEffects = soundEffectService.getAll();

        String[] messages = TableUtil.createTableMessages(soundEffects, 3, 8);
        for (String message : messages) {
            EmbedBuilder embedBuilder = MessageFactory.createMessageEmbedBuilder(MessageType.INFO);
            embedBuilder.setDescription(message);

            event.reply(embedBuilder.build());
        }
    }
}
