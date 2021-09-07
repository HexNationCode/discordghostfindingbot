package org.spoopy.commands;

import org.javacord.api.event.message.MessageCreateEvent;

public class Start {
    public static void run(MessageCreateEvent event)
    {
        event.getChannel().sendMessage("Whoo, the interface works!");
    }
}
