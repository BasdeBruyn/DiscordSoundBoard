package nl.basdebruyn.soundboardapi.auth.controllers;

import nl.basdebruyn.soundboardapi.auth.Roles;
import nl.basdebruyn.soundboardapi.auth.models.AuthUser;
import nl.basdebruyn.soundboardapi.auth.services.JwtService;
import nl.basdebruyn.soundboardapi.auth.util.AuthUtil;
import nl.basdebruyn.soundboardapi.discord.models.DiscordOath2;
import nl.basdebruyn.soundboardapi.discord.models.DiscordUser;
import nl.basdebruyn.soundboardapi.discord.services.DiscordService;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final JwtService jwtService;
    private final DiscordService discordService;

    public AuthController(JwtService jwtService, DiscordService discordService) {
        this.jwtService = jwtService;
        this.discordService = discordService;
    }

    @GetMapping
    @Secured(Roles.WEB_USER)
    public AuthUser getUser() {
        return AuthUtil.getAuthUserFromSecurityContext();
    }

    @PostMapping
    @Secured(Roles.WEB_USER)
    public DiscordUser getDiscordUser() {
        return discordService.getUser(AuthUtil.getAuthUserFromSecurityContext().discordOauth2.getAccessToken());
    }

    @GetMapping(value = "/token", produces = MediaType.TEXT_PLAIN_VALUE)
    public String getToken(@RequestParam String code) {
        DiscordOath2 discordOath2 = discordService.getTokenFromCode(code);
        DiscordUser discordUser = discordService.getUser(discordOath2.getAccessToken());
        return jwtService.generateToken(discordUser.getId(), discordOath2);
    }
}
