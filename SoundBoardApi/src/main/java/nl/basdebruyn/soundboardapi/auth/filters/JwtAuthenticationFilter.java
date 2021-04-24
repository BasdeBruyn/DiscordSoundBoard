package nl.basdebruyn.soundboardapi.auth.filters;

import nl.basdebruyn.soundboardapi.auth.authentications.JwtAuthentication;
import nl.basdebruyn.soundboardapi.auth.models.AuthUser;
import nl.basdebruyn.soundboardapi.auth.services.JwtService;
import nl.basdebruyn.soundboardapi.web.util.HttpServletUtil;
import org.springframework.beans.factory.annotation.Value;
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
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    public static final String INVALID_JWT_TOKEN_MESSAGE = "Invalid JWT token";
    public static final String NO_JWT_TOKEN_MESSAGE = "Headers contain no JWT token";

    private final JwtService jwtService;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (isNotAuthenticated()) {
            Authentication authentication = authorizeWithJwt(request, response);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            if (!authentication.isAuthenticated()) return;
        }

        filterChain.doFilter(request, response);
    }

    private boolean isNotAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication() == null;
    }

    private Authentication authorizeWithJwt(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            HttpServletUtil.setResponseAsUnauthorized(response, NO_JWT_TOKEN_MESSAGE);
            return JwtAuthentication.asUnauthenticated();
        }

        String authToken = header.substring(7);
        AuthUser authUser = jwtService.parseToken(authToken);
        if (authUser == null) {
            HttpServletUtil.setResponseAsUnauthorized(response, INVALID_JWT_TOKEN_MESSAGE);
            return JwtAuthentication.asUnauthenticated();
        }

        return createTokenAuthentication(authUser);
    }

    private JwtAuthentication createTokenAuthentication(AuthUser authUser) {
        return new JwtAuthentication(true, authUser.discordOauth2, authUser.discordUserId);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.startsWith(contextPath + "/auth/token");
    }
}
