package nl.basdebruyn.soundboardbot.bot.audioPlayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Guild;

public class GuildMusicManager {
    private final AudioPlayer player;
    private final TrackScheduler scheduler;

    public GuildMusicManager(AudioPlayerManager manager, Guild guild) {
        player = manager.createPlayer();
        scheduler = new TrackScheduler(player, guild);
        player.addListener(scheduler);
    }

    public AudioPlayerSendHandler getSendHandler() {
        return new AudioPlayerSendHandler(player);
    }

    public void queueTrack(AudioTrack track) {
        scheduler.queue(track);
    }

    public boolean nextTrack() {
        return scheduler.nextTrack();
    }

    public void stopPlaying() {
        scheduler.clearQueue();
        player.stopTrack();
        player.setPaused(false);
    }

    public boolean isPlaying() {
        return player.getPlayingTrack() != null;
    }
}
