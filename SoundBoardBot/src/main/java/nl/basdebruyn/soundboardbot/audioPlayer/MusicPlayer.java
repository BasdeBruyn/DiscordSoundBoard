package nl.basdebruyn.soundboardbot.audioPlayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;
import nl.basdebruyn.soundboardbot.util.CommandHelper;

import java.util.HashMap;
import java.util.Map;

public class MusicPlayer {
    private static final String CURRENTLY_NOT_PLAYING = "Currently not playing";
    private static final String SKIPPED_TO_NEXT_TRACK = "Skipped to next track";
    private static final String QUEUE_FINISHED = "Queue finished";
    private static final String BOT_NOT_IN_VOICE_CHANNEL = "I haven't joined a voice-channel jet";
    private static final String STOPPED_PLAYING = "Stopped playing";

    private static MusicPlayer mInstance;

    private final AudioPlayerManager playerManager;
    private final Map<Long, GuildMusicManager> musicManagers;

    private MusicPlayer() {
        this.musicManagers = new HashMap<>();

        this.playerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerRemoteSources(playerManager);
        AudioSourceManagers.registerLocalSource(playerManager);
    }

    public static MusicPlayer getInstance() {
        if (mInstance == null) {
            mInstance = new MusicPlayer();
        }
        return mInstance;
    }

    public void loadAndPlay(String trackName, String trackUrl, TextChannel textChannel, VoiceChannel voiceChannel) {
        GuildMusicManager musicManager = getGuildAudioPlayer(textChannel.getGuild());

        playerManager.loadItemOrdered(musicManager, trackUrl,
                new LoadResultHandler(trackName, trackUrl, textChannel, voiceChannel));
    }

    public void play(VoiceChannel voiceChannel, AudioTrack track) {
        Guild guild = voiceChannel.getGuild();
        connectToVoiceChannel(voiceChannel, guild.getAudioManager());

        getGuildAudioPlayer(guild).queueTrack(track);
    }

    public void skipTrack(TextChannel textChannel, Message message) {
        GuildMusicManager musicManager = getGuildAudioPlayer(textChannel.getGuild());

        if (!musicManager.isPlaying()) {
            CommandHelper.sendWarningMessage(textChannel, CURRENTLY_NOT_PLAYING);
        } else if (musicManager.nextTrack()) {
            CommandHelper.sendInfoMessage(textChannel, SKIPPED_TO_NEXT_TRACK);
            message.addReaction("U+23ED").queue();
        } else {
            message.addReaction("U+23ED").queue();
            CommandHelper.sendInfoMessage(textChannel, QUEUE_FINISHED);
            leaveChannel(textChannel);
        }
    }

    public void stopPlaying(TextChannel textChannel, Message message) {
        GuildMusicManager musicManager = getGuildAudioPlayer(textChannel.getGuild());

        if (!musicManager.isPlaying()) {
            CommandHelper.sendWarningMessage(textChannel, CURRENTLY_NOT_PLAYING);
        } else {
            musicManager.stopPlaying();
            leaveChannel(textChannel);
            message.addReaction("U+23F9").queue();
        }
    }

    public void leaveChannel(TextChannel textChannel) {
        Guild guild = textChannel.getGuild();
        if (guild.getAudioManager().getConnectedChannel() == null) {
            CommandHelper.sendWarningMessage(textChannel, BOT_NOT_IN_VOICE_CHANNEL);
        } else {
            guild.getAudioManager().closeAudioConnection();
        }
    }

    public boolean isPlaying(Guild guild) {
        return getGuildAudioPlayer(guild).isPlaying();
    }

    private synchronized GuildMusicManager getGuildAudioPlayer(Guild guild) {
        long guildId = Long.parseLong(guild.getId());
        GuildMusicManager musicManager = musicManagers.get(guildId);

        if (musicManager == null) {
            musicManager = new GuildMusicManager(playerManager, guild);
            musicManagers.put(guildId, musicManager);
        }

        guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());

        return musicManager;
    }

    private static void connectToVoiceChannel(VoiceChannel voiceChannel, AudioManager audioManager) {
        if (!audioManager.isConnected()) {
            audioManager.openAudioConnection(voiceChannel);
        }
    }
}
