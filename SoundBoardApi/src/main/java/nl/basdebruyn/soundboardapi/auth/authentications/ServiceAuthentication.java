package nl.basdebruyn.soundboardapi.auth.authentications;

import nl.basdebruyn.soundboardapi.auth.Roles;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Collection;

public class ServiceAuthentication implements Authentication {
    private boolean authenticated;

    public ServiceAuthentication(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public static ServiceAuthentication asUnauthenticated(){
        return new ServiceAuthentication(false);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList(Roles.SERVICE);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return null;
    }
}
