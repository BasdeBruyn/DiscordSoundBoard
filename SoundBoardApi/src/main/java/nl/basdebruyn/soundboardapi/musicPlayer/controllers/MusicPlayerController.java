package nl.basdebruyn.soundboardapi.musicPlayer.controllers;

import nl.basdebruyn.soundboardapi.auth.Roles;
import nl.basdebruyn.soundboardapi.auth.util.AuthUtil;
import nl.basdebruyn.soundboardapi.musicPlayer.models.SoundEffect;
import nl.basdebruyn.soundboardapi.musicPlayer.properties.MusicPlayerProperties;
import nl.basdebruyn.soundboardapi.musicPlayer.repositories.SoundEffectRepository;
import nl.basdebruyn.soundboardapi.web.statusErrors.NotFoundException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@RestController
@RequestMapping("/soundboard")
@Secured(Roles.WEB_USER)
public class MusicPlayerController {
    public static final String SOUND_EFFECT_NOT_FOUND_MESSAGE = "No sound effect with name '%s' can be found";

    private final SoundEffectRepository soundEffectRepository;
    private final MusicPlayerProperties musicPlayerProperties;

    public MusicPlayerController(
            SoundEffectRepository soundEffectRepository,
            MusicPlayerProperties musicPlayerProperties
    ) {
        this.soundEffectRepository = soundEffectRepository;
        this.musicPlayerProperties = musicPlayerProperties;
    }

    @PostMapping("/play/{name}")
    public void playSoundEffect(@PathVariable String name) {
        Optional<SoundEffect> soundEffect = soundEffectRepository.findById(name);
        if (!soundEffect.isPresent()) {
            throw new NotFoundException(SOUND_EFFECT_NOT_FOUND_MESSAGE, name);
        }

        RestTemplate restTemplate = new RestTemplate();
        String discordUserId = AuthUtil.getAuthUserFromSecurityContext().discordUserId;
        String uri = createUriWithUserId(discordUserId);

        try {
            restTemplate.postForObject(uri, soundEffect.get(), Void.class);
        } catch (HttpClientErrorException exception) {
            throw new ResponseStatusException(exception.getStatusCode(), exception.getResponseBodyAsString());
        }
    }

    private String createUriWithUserId(String discordUserId) {
        return UriComponentsBuilder
                .fromHttpUrl(musicPlayerProperties.getUrl() + "/musicplayer/play")
                .queryParam("userId", discordUserId)
                .toUriString();
    }
}
