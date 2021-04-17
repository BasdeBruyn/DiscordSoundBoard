package nl.basdebruyn.soundboardbot.commands;

import com.jagrosh.jdautilities.command.CommandEvent;
import nl.basdebruyn.soundboardbot.audioPlayer.MusicPlayer;
import nl.basdebruyn.soundboardbot.models.SoundEffect;
import nl.basdebruyn.soundboardbot.util.CommandHelper;
import nl.basdebruyn.soundboardbot.util.MessageFactory;
import nl.basdebruyn.soundboardbot.util.MessageType;
import nl.basdebruyn.soundboardbot.util.SoundEffectService;

public class PlayCommand extends CommandWrapper {
    private final MusicPlayer musicPlayer;
    private final SoundEffectService soundEffectService;

    public PlayCommand() {
        this.name = "play";
        this.aliases = new String[]{"p"};
        this.help = "play the given sound effect";
        this.arguments = "<sound effect>";
        this.numberOfArguments = 1;
        musicPlayer = MusicPlayer.getInstance();
        soundEffectService = SoundEffectService.getInstance();
    }

    @Override
    protected void executeCommand(CommandEvent event) {
        if (!CommandHelper.messageAuthorIsInVoiceChannel(event)) {
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
