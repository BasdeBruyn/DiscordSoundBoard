package nl.basdebruyn.soundboardbot.bot.util;

import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;

public class CommandReplyUtil {
    public static boolean messageAuthorIsInVoiceChannel(CommandEvent event) {
        return event.getMember().getVoiceState() != null && event.getMember().getVoiceState().inVoiceChannel();
    }

    public static void sendInfoMessage(TextChannel textChannel, String messageContent) {
        sendMessageForType(MessageType.INFO, textChannel, messageContent);
    }

    public static void sendSuccessMessage(TextChannel textChannel, String messageContent) {
        sendMessageForType(MessageType.SUCCESS, textChannel, messageContent);
    }

    public static void sendWarningMessage(TextChannel textChannel, String messageContent) {
        sendMessageForType(MessageType.WARNING, textChannel, messageContent);
    }

    public static void sendErrorMessage(TextChannel textChannel, String messageContent) {
        sendMessageForType(MessageType.ERROR, textChannel, messageContent);
    }

    private static void sendMessageForType(MessageType messageType, TextChannel textChannel, String messageContent) {
        MessageEmbed message = MessageFactory.createMessageEmbed(messageType, messageContent);
        textChannel.sendMessage(message).queue();
    }
}
