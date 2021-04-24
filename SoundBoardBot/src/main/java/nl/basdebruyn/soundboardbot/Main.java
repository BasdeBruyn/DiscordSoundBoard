package nl.basdebruyn.soundboardbot;

import nl.basdebruyn.soundboardbot.bot.managers.DiscordBotManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.security.auth.login.LoginException;

@SpringBootApplication
public class Main {
    public Main(DiscordBotManager discordBotManager) throws LoginException, InterruptedException {
        discordBotManager.connect();
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
