package nl.basdebruyn.soundboardbot.audioPlayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import nl.basdebruyn.soundboardbot.util.MessageFactory;
import nl.basdebruyn.soundboardbot.util.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TrackScheduler extends AudioEventAdapter {
    private static final String LAVAPLAYER_OUTDATED_MSG = "Unable to play video";
    public static final String LAVAPLAYER_OUTDATED_ERROR = "Lavaplayer can't play this track, this version is probably outdated!";
    private final AudioPlayer player;
    private final Guild guild;
    private final BlockingQueue<AudioTrack> queue;

    private static final Logger logger = LoggerFactory.getLogger(TrackScheduler.class);

    public TrackScheduler(AudioPlayer player, Guild guild) {
        this.player = player;
        this.guild = guild;
        this.queue = new LinkedBlockingQueue<>();
    }

    public void queue(AudioTrack track) {
        if (!player.startTrack(track, true)) {
            queue.offer(track);
        }
    }

    public boolean nextTrack() {
        return player.startTrack(queue.poll(), false);
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        if (endReason.mayStartNext && !nextTrack())
                guild.getAudioManager().closeAudioConnection();
    }

    @Override
    public void onTrackException(AudioPlayer player, AudioTrack track, FriendlyException exception) {
        logger.error(LAVAPLAYER_OUTDATED_ERROR);

        TextChannel textChannel = (TextChannel) track.getUserData();
        MessageEmbed messageEmbed = MessageFactory.createMessageEmbed(MessageType.ERROR, LAVAPLAYER_OUTDATED_MSG);
        textChannel
                .sendMessage(messageEmbed)
                .queue(message -> {
                    guild.getAudioManager().closeAudioConnection();
                });
    }

    public void clearQueue() {
        queue.clear();
    }
}
