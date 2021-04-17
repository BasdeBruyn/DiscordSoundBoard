package nl.basdebruyn.soundboardbot.managers;

import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.JDABuilder;
import nl.basdebruyn.soundboardbot.commands.*;
import nl.basdebruyn.soundboardbot.util.DiscordBotConfiguration;

import javax.security.auth.login.LoginException;

public class DiscordBotManager {
    public static void connect() throws LoginException, InterruptedException {
        EventWaiter waiter = new EventWaiter();
        CommandClientBuilder client = new CommandClientBuilder();
        DiscordBotConfiguration configuration = DiscordBotConfiguration.fromEnvironment();

        client.useDefaultGame();
        client.setOwnerId(configuration.ownerId);
        client.setPrefix(configuration.prefix);
        client.addCommands(
                new PlayCommand(),
                new StopCommand(),
                new SkipCommand(),
                new AddCommand(),
                new RemoveCommand(),
                new RenameCommand(),
                new UpdateCommand(),
                new ListCommand(),
                new HelpCommand()
        );
        client.useHelpBuilder(false);

        JDABuilder
                .createDefault(configuration.botToken)
                .addEventListeners(waiter, client.build())
                .build()
                .awaitReady();
    }
}
