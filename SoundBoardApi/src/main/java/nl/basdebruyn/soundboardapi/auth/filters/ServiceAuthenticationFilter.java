package nl.basdebruyn.soundboardapi.auth.filters;

import nl.basdebruyn.soundboardapi.auth.authentications.ServiceAuthentication;
import nl.basdebruyn.soundboardapi.auth.properties.AuthenticationProperties;
import nl.basdebruyn.soundboardapi.web.util.HttpServletUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ServiceAuthenticationFilter extends OncePerRequestFilter {
    public static final String INVALID_PASSWORD_MESSAGE = "Invalid password";

    private final AuthenticationProperties.Service serviceAuthentication;

    public ServiceAuthenticationFilter(AuthenticationProperties authenticationProperties) {
        serviceAuthentication = authenticationProperties.getService();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String password = request.getHeader("Password");
        if (password != null) {
            Authentication authentication = authenticateWithPassword(response, password);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            if (!authentication.isAuthenticated()) return;
        }

        filterChain.doFilter(request, response);
    }

    private Authentication authenticateWithPassword(HttpServletResponse response, String password) throws IOException {
        if (isCorrectPassword(password)) {
            return new ServiceAuthentication(true);
        }

        HttpServletUtil.setResponseAsUnauthorized(response, INVALID_PASSWORD_MESSAGE);
        return ServiceAuthentication.asUnauthenticated();
    }

    private boolean isCorrectPassword(String password) {
        return password.equals(serviceAuthentication.getPassword());
    }
}
