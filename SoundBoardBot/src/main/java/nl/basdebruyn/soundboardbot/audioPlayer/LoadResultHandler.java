package nl.basdebruyn.soundboardbot.audioPlayer;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import nl.basdebruyn.soundboardbot.util.CommandHelper;

public class LoadResultHandler implements AudioLoadResultHandler {
    private static final String ADDING_TO_QUEUE = "Adding to queue **%s** (%s)";
    private static final String PLAYING = "Playing **%s** (%s)";
    private static final String NOTHING_FOUND_BY = "Nothing found by %s (**%s**)";
    private static final String COULD_NOT_PLAY = "Could not play: %s";

    private final String trackName;
    private final String trackUrl;
    private final TextChannel textChannel;
    private final VoiceChannel voiceChannel;
    private final MusicPlayer musicPlayer;

    public LoadResultHandler(String trackName, String trackUrl, TextChannel textChannel, VoiceChannel voiceChannel) {
        this.trackName = trackName;
        this.trackUrl = trackUrl;
        this.textChannel = textChannel;
        this.voiceChannel = voiceChannel;
        this.musicPlayer = MusicPlayer.getInstance();
    }

    @Override
    public void trackLoaded(AudioTrack track) {
        track.setUserData(this.textChannel);

        sendPlayingTrackMessage(track);

        musicPlayer.play(this.voiceChannel, track);
    }

    @Override
    public void playlistLoaded(AudioPlaylist playlist) {
        AudioTrack audioTrack = playlist.getTracks().get(0);
        audioTrack.setUserData(this.textChannel);

        sendPlayingTrackMessage(audioTrack);

        musicPlayer.play(voiceChannel, audioTrack);
    }

    @Override
    public void noMatches() {
        String messageContent = String.format(NOTHING_FOUND_BY, trackUrl, trackName);
        CommandHelper.sendErrorMessage(textChannel, messageContent);
    }

    @Override
    public void loadFailed(FriendlyException exception) {
        String messageContent = String.format(COULD_NOT_PLAY, exception.getMessage());
        CommandHelper.sendErrorMessage(textChannel, messageContent);
    }

    private void sendPlayingTrackMessage(AudioTrack track) {
        String messageContent = String.format(
                musicPlayer.isPlaying(textChannel.getGuild()) ? ADDING_TO_QUEUE : PLAYING,
                trackName,
                track.getInfo().title
        );
        CommandHelper.sendInfoMessage(this.textChannel, messageContent);
    }
}
