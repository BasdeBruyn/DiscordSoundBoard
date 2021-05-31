package nl.basdebruyn.soundboardapi.auth.authentications;

import nl.basdebruyn.soundboardapi.auth.Roles;
import nl.basdebruyn.soundboardapi.discord.models.DiscordOath2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Collection;

public class JwtAuthentication implements Authentication {
    private boolean authenticated;
    private final DiscordOath2 discordOath2;
    private final String discordUserId;

    public JwtAuthentication(boolean authenticated, DiscordOath2 discordOath2, String discordUserId) {
        this.authenticated = authenticated;
        this.discordOath2 = discordOath2;
        this.discordUserId = discordUserId;
    }

    public static JwtAuthentication asUnauthenticated() {
        return new JwtAuthentication(false, null, null);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList(Roles.WEB_USER);
    }

    @Override
    public Object getCredentials() {
        return discordOath2;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return discordUserId;
    }

    @Override
    public boolean isAuthenticated() {
        return this.authenticated;
    }

    @Override
    public void setAuthenticated(boolean authenticated) throws IllegalArgumentException {
        this.authenticated = authenticated;
    }

    @Override
    public String getName() {
        return discordUserId;
    }
}
