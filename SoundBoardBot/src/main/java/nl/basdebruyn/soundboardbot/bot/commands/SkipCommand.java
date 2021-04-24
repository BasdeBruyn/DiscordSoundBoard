package nl.basdebruyn.soundboardbot.bot.commands;

import com.jagrosh.jdautilities.command.CommandEvent;
import nl.basdebruyn.soundboardbot.bot.audioPlayer.MusicPlayer;
import org.springframework.stereotype.Component;

@Component
public class SkipCommand extends CommandWrapper {
    private final MusicPlayer musicPlayer;

    public SkipCommand(MusicPlayer musicPlayer) {
        this.musicPlayer = musicPlayer;
        this.name = "skip";
        this.help = "skip current sound effect";
    }

    @Override
    protected void executeCommand(CommandEvent event) {
        musicPlayer.skipTrack(event.getTextChannel(), event.getMessage());
    }
}
