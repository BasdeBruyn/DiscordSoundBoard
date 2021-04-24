package nl.basdebruyn.soundboardbot.bot.commands;

import com.jagrosh.jdautilities.command.CommandEvent;
import nl.basdebruyn.soundboardbot.bot.audioPlayer.MusicPlayer;
import org.springframework.stereotype.Component;

@Component
public class StopCommand extends CommandWrapper {
    private final MusicPlayer musicPlayer;

    public StopCommand(MusicPlayer musicPlayer) {
        this.musicPlayer = musicPlayer;
        this.name = "stop";
        this.help = "stop playing";
    }

    @Override
    protected void executeCommand(CommandEvent event) {
        musicPlayer.stopPlaying(event.getTextChannel(), event.getMessage());
    }
}
