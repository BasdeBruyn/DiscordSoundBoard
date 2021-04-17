package nl.basdebruyn.soundboardbot.commands;

import com.jagrosh.jdautilities.command.CommandEvent;
import nl.basdebruyn.soundboardbot.audioPlayer.MusicPlayer;

public class StopCommand extends CommandWrapper {
    private final MusicPlayer musicPlayer;

    public StopCommand() {
        this.name = "stop";
        this.help = "stop playing";
        musicPlayer = MusicPlayer.getInstance();
    }

    @Override
    protected void executeCommand(CommandEvent event) {
        musicPlayer.stopPlaying(event.getTextChannel(), event.getMessage());
    }
}
