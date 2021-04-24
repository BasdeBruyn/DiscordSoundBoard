package nl.basdebruyn.soundboardapi.discord.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Validated
@Configuration
@ConfigurationProperties(prefix = "discord")
public class DiscordProperties {
    private Oauth2 oauth2;
    private String apiUri;

    public static class Oauth2 {
        @NotNull
        private String clientId;
        @NotNull
        private String clientSecret;
        @NotNull
        private String redirectUri;
        @NotEmpty
        private String[] scope;
        @NotEmpty
        private String userAgent;

        public String getJoinedScope() {
            return String.join(" ", scope);
        }

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public String getClientSecret() {
            return clientSecret;
        }

        public void setClientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
        }

        public String getRedirectUri() {
            return redirectUri;
        }

        public void setRedirectUri(String redirectUri) {
            this.redirectUri = redirectUri;
        }

        public String[] getScope() {
            return scope;
        }

        public void setScope(String[] scope) {
            this.scope = scope;
        }

        public String getUserAgent() {
            return userAgent;
        }

        public void setUserAgent(String userAgent) {
            this.userAgent = userAgent;
        }
    }

    public Oauth2 getOauth2() {
        return oauth2;
    }

    public void setOauth2(Oauth2 oauth2) {
        this.oauth2 = oauth2;
    }

    public String getApiUri() {
        return apiUri;
    }

    public void setApiUri(String apiUri) {
        this.apiUri = apiUri;
    }
}
