package nl.basdebruyn.soundboardbot.bot.commands;

import com.jagrosh.jdautilities.command.CommandEvent;
import nl.basdebruyn.soundboardbot.bot.audioPlayer.MusicPlayer;
import nl.basdebruyn.soundboardbot.web.models.SoundEffect;
import nl.basdebruyn.soundboardbot.bot.util.CommandReplyUtil;
import nl.basdebruyn.soundboardbot.bot.util.MessageFactory;
import nl.basdebruyn.soundboardbot.bot.util.MessageType;
import nl.basdebruyn.soundboardbot.web.services.SoundEffectService;
import org.springframework.stereotype.Component;

@Component
public class PlayCommand extends CommandWrapper {
    private final MusicPlayer musicPlayer;
    private final SoundEffectService soundEffectService;

    public PlayCommand(MusicPlayer musicPlayer, SoundEffectService soundEffectService) {
        this.musicPlayer = musicPlayer;
        this.soundEffectService = soundEffectService;
        this.name = "play";
        this.aliases = new String[]{"p"};
        this.help = "play the given sound effect";
        this.arguments = "<sound effect>";
        this.numberOfArguments = 1;
    }

    @Override
    protected void executeCommand(CommandEvent event) {
        if (!CommandReplyUtil.messageAuthorIsInVoiceChannel(event)) {
            replyNotInVoiceChannel(event);
            return;
        }

        if (!soundEffectService.exists(event.getArgs())) {
            replySoundEffectNotOnSoundboard(event);
            return;
        }

        SoundEffect soundEffect = soundEffectService.getByName(event.getArgs());
        musicPlayer.loadAndPlay(
                soundEffect.name,
                soundEffect.url,
                event.getTextChannel(),
                event.getMember().getVoiceState().getChannel()
        );
    }

    private void replySoundEffectNotOnSoundboard(CommandEvent event) {
        String message = String.format("**%s** is not on the soundboard", event.getArgs());
        event.reply(MessageFactory.createMessageEmbed(MessageType.WARNING, message));
    }

    private void replyNotInVoiceChannel(CommandEvent event) {
        event.reply(MessageFactory.createMessageEmbed(MessageType.WARNING, "You're not in a voice channel"));
    }
}
