package nl.basdebruyn.soundboardbot;

import nl.basdebruyn.soundboardbot.managers.DiscordBotManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.security.auth.login.LoginException;

@SpringBootApplication
public class Main {
    public static void main(String[] args) throws LoginException, InterruptedException {
        DiscordBotManager.connect();
        SpringApplication.run(Main.class, args);
    }
}
