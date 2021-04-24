package nl.basdebruyn.soundboardbot.web.services;

import nl.basdebruyn.soundboardbot.web.models.SoundEffect;
import nl.basdebruyn.soundboardbot.web.models.SoundEffectRename;
import nl.basdebruyn.soundboardbot.web.properties.ApiProperties;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class SoundEffectService {
    private final ApiProperties apiProperties;

    public SoundEffectService(ApiProperties apiProperties) {
        this.apiProperties = apiProperties;
    }

    public SoundEffect[] getAll() {
        RestTemplate restTemplate = getRestTemplate();
        return restTemplate.getForEntity(apiProperties.getUrl(), SoundEffect[].class).getBody();
    }

    public SoundEffect getByName(String name) {
        RestTemplate restTemplate = getRestTemplate();
        return restTemplate.getForObject(apiProperties.getUrl() + name, SoundEffect.class);
    }

    public Boolean exists(String name) {
        RestTemplate restTemplate = getRestTemplate();
        return restTemplate.getForObject(apiProperties.getUrl() + "exists/" + name, Boolean.class);
    }

    public Optional<SoundEffect> save(SoundEffect soundEffect) {
        RestTemplate restTemplate = getRestTemplate();

        try {
            SoundEffect response = restTemplate.postForObject(apiProperties.getUrl(), soundEffect, SoundEffect.class);
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
        RestTemplate restTemplate = getRestTemplate();

        try {
            restTemplate.put(apiProperties.getUrl(), soundEffect);
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
        RestTemplate restTemplate = getRestTemplate();

        try {
            SoundEffect soundEffect = restTemplate.postForObject(apiProperties.getUrl() + "rename", renameModel, SoundEffect.class);
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
        RestTemplate restTemplate = getRestTemplate();
        restTemplate.delete(apiProperties.getUrl() + name);
    }

    @NotNull
    private RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().set("Password", apiProperties.getPassword());
            return execution.execute(request, body);
        });
        return restTemplate;
    }
}
