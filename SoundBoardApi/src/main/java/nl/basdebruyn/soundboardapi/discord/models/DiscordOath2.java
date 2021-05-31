package nl.basdebruyn.soundboardapi.discord.models;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DiscordOath2 {
    private String accessToken;
    private long expiresIn;
    private String refreshToken;

    public DiscordOath2() {
    }

    public DiscordOath2(String accessToken, long expiresIn, String refreshToken) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
