package nl.basdebruyn.soundboardapi.discord.services;

import nl.basdebruyn.soundboardapi.auth.util.AuthUtil;
import nl.basdebruyn.soundboardapi.discord.models.DiscordOath2;
import nl.basdebruyn.soundboardapi.discord.models.DiscordUser;
import nl.basdebruyn.soundboardapi.discord.properties.DiscordProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class DiscordService {
    private final DiscordProperties.Oauth2 discordOauth2;
    private final String discordApiUri;

    public DiscordService(DiscordProperties discordProperties) {
        discordOauth2 = discordProperties.getOauth2();
        discordApiUri = discordProperties.getApiUri();
    }

    public DiscordOath2 getTokenFromCode(String code) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set(HttpHeaders.USER_AGENT, discordOauth2.getUserAgent());

        MultiValueMap<String, String> requestBody = createRequestBody(code);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(requestBody, headers);

        return restTemplate.postForEntity(discordApiUri + "/oauth2/token", entity, DiscordOath2.class).getBody();
    }

    public DiscordUser getUser(String token) {
        RestTemplate restTemplate = AuthUtil.getRestTemplateWithAuthHeader(token, discordOauth2);
        return restTemplate.getForObject(discordApiUri + "/users/@me", DiscordUser.class);
    }

    private MultiValueMap<String, String> createRequestBody(String code) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", discordOauth2.getClientId());
        params.add("client_secret", discordOauth2.getClientSecret());
        params.add("grant_type", "authorization_code");
        params.add("redirect_uri", discordOauth2.getRedirectUri());
        params.add("scope", discordOauth2.getJoinedScope());
        params.add("code", code);
        return params;
    }
}
