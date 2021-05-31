package nl.basdebruyn.soundboardbot.web.controllers;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;
import nl.basdebruyn.soundboardbot.bot.audioPlayer.MusicPlayer;
import nl.basdebruyn.soundboardbot.web.models.SoundEffect;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/musicplayer")
public class CommandController {
    private final JDA jda;
    private final MusicPlayer musicPlayer;

    public CommandController(JDA jda, MusicPlayer musicPlayer) {
        this.jda = jda;
        this.musicPlayer = musicPlayer;
    }

    @PostMapping("/play")
    public void playSound(@RequestBody SoundEffect soundEffect, @RequestParam String userId) {
        Optional<VoiceChannel> voiceChannel = getVoiceChannel(userId);

        if (!voiceChannel.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not connected to available voice channel");
        }

        musicPlayer.loadAndPlay(
                soundEffect.name,
                soundEffect.url,
                voiceChannel.get().getGuild().getDefaultChannel(),
                voiceChannel.get()
        );
    }

    private Optional<VoiceChannel> getVoiceChannel(String userId) {
        for (Guild jdaGuild : jda.getGuilds()) {
            Optional<VoiceChannel> channel = getVoiceChannelIfConnected(userId, jdaGuild);
            if (channel.isPresent()) return channel;
        }
        return Optional.empty();
    }

    private Optional<VoiceChannel> getVoiceChannelIfConnected(String userId, Guild guild) {
        Member memberById = guild.getMemberById(userId);
        if (memberById == null) return Optional.empty();

        GuildVoiceState voiceState = memberById.getVoiceState();
        if (voiceState == null) return Optional.empty();

        return Optional.ofNullable(voiceState.getChannel());
    }
}
