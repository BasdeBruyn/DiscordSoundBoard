package nl.basdebruyn.soundboardbot.commands;

import com.jagrosh.jdautilities.command.CommandEvent;
import nl.basdebruyn.soundboardbot.audioPlayer.MusicPlayer;

public class SkipCommand extends CommandWrapper {
    private final MusicPlayer musicPlayer;

    public SkipCommand() {
        this.name = "skip";
        this.help = "skip current sound effect";
        musicPlayer = MusicPlayer.getInstance();
    }

    @Override
    protected void executeCommand(CommandEvent event) {
        musicPlayer.skipTrack(event.getTextChannel(), event.getMessage());
    }
}
