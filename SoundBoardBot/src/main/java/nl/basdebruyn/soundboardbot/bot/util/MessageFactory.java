package nl.basdebruyn.soundboardbot.bot.util;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
//import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.awt.*;

public class MessageFactory {
    private final static EmbedBuilder defaultBuilder = new EmbedBuilder();

    public static EmbedBuilder createMessageEmbedBuilder(MessageType messageType) {
        return new EmbedBuilder(defaultBuilder)
                .setColor(getColorForMessageType(messageType));
    }
    public static EmbedBuilder createMessageEmbedBuilder(MessageType messageType, String message) {
        return createMessageEmbedBuilder(messageType)
                .setDescription(message);
    }

    public static MessageEmbed createMessageEmbed(MessageType messageType, String message) {
        return createMessageEmbedBuilder(messageType, message).build();
    }
    
    private static Color getColorForMessageType(MessageType messageType) {
        switch (messageType) {
            case INFO:
                return new Color(125, 43, 161);
            case SUCCESS:
                return Color.GREEN;
            case WARNING:
                return Color.YELLOW;
            case ERROR:
                return Color.RED;
        }

        return null;
    }
}
