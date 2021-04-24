package nl.basdebruyn.soundboardapi.auth.models;

import nl.basdebruyn.soundboardapi.discord.models.DiscordOath2;

public class AuthUser {
    public final String discordUserId;
    public final DiscordOath2 discordOauth2;

    public AuthUser(String discordUserId, DiscordOath2 discordOath2) {
        this.discordUserId = discordUserId;
        this.discordOauth2 = discordOath2;
    }
}
