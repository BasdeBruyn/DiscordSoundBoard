package nl.basdebruyn.soundboardapi.auth.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import nl.basdebruyn.soundboardapi.auth.models.AuthUser;
import nl.basdebruyn.soundboardapi.auth.properties.AuthenticationProperties;
import nl.basdebruyn.soundboardapi.discord.models.DiscordOath2;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Map;

@Component
public class JwtService {
    public static final String DISCORD_OAUTH_KEY = "discordOauth2";

    private final Key secretKey;

    public JwtService(AuthenticationProperties authenticationProperties) {
        String secret = authenticationProperties.getJwt().getSecret();
        secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public AuthUser parseToken(String token) {
        try {
            Claims body = Jwts.parserBuilder()
                    .setSigningKey(secretKey.getEncoded())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            Map<String, Object> discordOauth2Map = (Map<String, Object>) body.get(DISCORD_OAUTH_KEY);
            DiscordOath2 discordOath2 = getDiscordOauth2FromMap(discordOauth2Map);

            return new AuthUser(body.getSubject(), discordOath2);
        } catch (JwtException | ClassCastException e) {
            return null;
        }
    }

    public String generateToken(String discordUserId, DiscordOath2 discordOath2) {
        Claims claims = Jwts.claims();
        claims.setSubject(discordUserId);
        claims.put(DISCORD_OAUTH_KEY, discordOath2);

        return Jwts
                .builder()
                .setClaims(claims)
                .signWith(secretKey)
                .compact();
    }

    private DiscordOath2 getDiscordOauth2FromMap(Map<String, Object> map) {
        return new DiscordOath2(
                (String) map.get("access_token"),
                (int) map.get("expires_in"),
                (String) map.get("refresh_token")
        );
    }
}
