package nl.basdebruyn.soundboardbot.util;

import nl.basdebruyn.soundboardbot.models.SoundEffect;
import nl.basdebruyn.soundboardbot.models.SoundEffectRename;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

public class SoundEffectService {
    private final String apiUrl;
    private static SoundEffectService instance;

    public SoundEffectService(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public static SoundEffectService getInstance() {
        if (instance == null) {
            instance = new SoundEffectService(DiscordBotConfiguration.fromEnvironment().apiUrl);
        }
        return instance;
    }

    public SoundEffect[] getAll() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity(apiUrl, SoundEffect[].class).getBody();
    }

    public SoundEffect getByName(String name) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(apiUrl + name, SoundEffect.class);
    }

    public Boolean exists(String name) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(apiUrl + "exists/" + name, Boolean.class);
    }

    public Optional<SoundEffect> save(SoundEffect soundEffect) {
        RestTemplate restTemplate = new RestTemplate();

        try {
            SoundEffect response = restTemplate.postForObject(apiUrl, soundEffect, SoundEffect.class);
            return Optional.ofNullable(response);
        } catch (HttpClientErrorException exception) {
            if (exception.getStatusCode() == HttpStatus.BAD_REQUEST) {
                return Optional.empty();
            } else {
                throw exception;
            }
        }
    }

    public boolean update(SoundEffect soundEffect) {
        RestTemplate restTemplate = new RestTemplate();

        try {
            restTemplate.put(apiUrl, soundEffect);
            return true;
        } catch (HttpClientErrorException exception) {
            if (exception.getStatusCode() == HttpStatus.BAD_REQUEST) {
                return false;
            } else {
                throw exception;
            }
        }
    }

    public Optional<SoundEffect> rename(String oldName, String newName) {
        SoundEffectRename renameModel = new SoundEffectRename(oldName, newName);
        RestTemplate restTemplate = new RestTemplate();

        try {
            SoundEffect soundEffect = restTemplate.postForObject(apiUrl + "rename", renameModel, SoundEffect.class);
            return Optional.ofNullable(soundEffect);
        } catch (HttpClientErrorException exception) {
            if (exception.getStatusCode() == HttpStatus.BAD_REQUEST) {
                return Optional.empty();
            } else {
                throw exception;
            }
        }
    }

    public void delete(String name) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(apiUrl + name);
    }
}
