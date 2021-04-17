package nl.basdebruyn.soundboardbot.commands;

import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import nl.basdebruyn.soundboardbot.models.SoundEffect;
import nl.basdebruyn.soundboardbot.util.MessageFactory;
import nl.basdebruyn.soundboardbot.util.MessageType;
import nl.basdebruyn.soundboardbot.util.SoundEffectService;
import nl.basdebruyn.soundboardbot.util.TableUtil;

public class ListCommand extends CommandWrapper {
    private final SoundEffectService soundEffectService;

    public ListCommand() {
        this.name = "list";
        this.aliases = new String[]{"l", "all"};
        this.help = "show all sound effects";
        soundEffectService = SoundEffectService.getInstance();
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
