package nl.basdebruyn.soundboardapi.auth.util;

import nl.basdebruyn.soundboardapi.auth.models.AuthUser;
import nl.basdebruyn.soundboardapi.discord.models.DiscordOath2;
import nl.basdebruyn.soundboardapi.discord.properties.DiscordProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestTemplate;

public class AuthUtil {
    public static Authentication getAuthenticationFromSecurityContext() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static DiscordOath2 getDiscordOauth2FromSecurityContext() {
        return (DiscordOath2) getAuthenticationFromSecurityContext().getCredentials();
    }

    public static AuthUser getAuthUserFromSecurityContext() {
        Authentication authentication = getAuthenticationFromSecurityContext();
        return new AuthUser((String) authentication.getPrincipal(), (DiscordOath2) authentication.getCredentials());
    }

    public static RestTemplate getRestTemplateWithAuthHeader(String token, DiscordProperties.Oauth2 discordOauth2) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().set("Authorization", "Bearer " + token);
            request.getHeaders().set(HttpHeaders.USER_AGENT, discordOauth2.getUserAgent());
            return execution.execute(request, body);
        });
        return restTemplate;
    }
}
