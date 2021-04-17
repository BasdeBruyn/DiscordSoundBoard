package nl.basdebruyn.soundboardbot.util;

public class DiscordBotConfiguration {
    public final String ownerId;
    public final String prefix;
    public final String botToken;
    public final String apiUrl;

    public DiscordBotConfiguration(String ownerId, String prefix, String botToken, String apiUrl) {
        this.ownerId = ownerId;
        this.prefix = prefix;
        this.botToken = botToken;
        this.apiUrl = apiUrl;
    }

    public static DiscordBotConfiguration fromEnvironment() {
        return new DiscordBotConfiguration(
                System.getenv("OWNER_ID"),
                System.getenv("PREFIX"),
                System.getenv("BOT_TOKEN"),
                System.getenv("API_URL")
        );
    }
}
