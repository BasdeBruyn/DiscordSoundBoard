package nl.basdebruyn.soundboardbot.bot.managers;

import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import nl.basdebruyn.soundboardbot.bot.util.CommandRegistrar;
import nl.basdebruyn.soundboardbot.bot.properties.BotProperties;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class DiscordBotManager {
    private final BotProperties botProperties;
    private final CommandRegistrar commandRegistrar;

    public JDA discordApi;

    @Bean
    public JDA getDiscordApi(){
        return discordApi;
    }

    public DiscordBotManager(BotProperties botProperties, CommandRegistrar commandRegistrar) {
        this.botProperties = botProperties;
        this.commandRegistrar = commandRegistrar;
    }

    public JDA connect() throws LoginException {
        EventWaiter waiter = new EventWaiter();
        CommandClientBuilder client = new CommandClientBuilder();

        client.useDefaultGame();
        client.setOwnerId(botProperties.getOwnerId());
        client.setPrefix(botProperties.getPrefix());
        client.useHelpBuilder(false);

        commandRegistrar.RegisterCommands(client);

        discordApi = JDABuilder
                .createDefault(botProperties.getBotToken())
                .addEventListeners(waiter, client.build())
                .build();

        return discordApi;
    }
}
