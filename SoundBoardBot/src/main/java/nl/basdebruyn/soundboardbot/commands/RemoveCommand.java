package nl.basdebruyn.soundboardbot.commands;

import com.jagrosh.jdautilities.command.CommandEvent;
import nl.basdebruyn.soundboardbot.util.MessageFactory;
import nl.basdebruyn.soundboardbot.util.MessageType;
import nl.basdebruyn.soundboardbot.util.SoundEffectService;

public class RemoveCommand extends CommandWrapper {
    private final SoundEffectService soundEffectService;

    public RemoveCommand() {
        this.name = "remove";
        this.aliases = new String[]{"delete", "d"};
        this.help = "remove sound effect";
        this.arguments = "<name>";
        this.numberOfArguments = 1;
        soundEffectService = SoundEffectService.getInstance();
    }

    @Override
    protected void executeCommand(CommandEvent event) {
        if (soundEffectService.exists(event.getArgs())) {
            soundEffectService.delete(event.getArgs());
            replyAddedSoundEffectToSoundboard(event);
        } else {
            replySoundEffectNotOnSoundboard(event);
        }
    }

    private void replySoundEffectNotOnSoundboard(CommandEvent event) {
        String message = String.format("**%s** is not on the soundboard", event.getArgs());
        event.reply(MessageFactory.createMessageEmbed(MessageType.WARNING, message));
    }

    private void replyAddedSoundEffectToSoundboard(CommandEvent event) {
        String message = String.format("Removed **%s** from the soundboard", event.getArgs());
        event.reply(MessageFactory.createMessageEmbed(MessageType.SUCCESS, message));
    }
}
