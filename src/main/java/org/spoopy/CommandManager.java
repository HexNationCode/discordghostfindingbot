package org.spoopy;

import org.apache.commons.lang3.StringUtils;
import org.javacord.api.event.message.MessageCreateEvent;

import java.util.concurrent.Callable;


public class CommandManager {
    public static void run(MessageCreateEvent event)
    {
        String command = event.getMessageContent();
        String[] args = StringUtils.split(command);


        Callable<Boolean> check = () -> !event.getMessageAuthor().isBotUser() &&
                !event.getMessageContent().contains("@") &&
                StringUtils.contains(command, '%') &&
                event.getChannel().getIdAsString().equals("884715936207212555");

        try {
            if (check.call())
            {
                if (args.length > 1) {
                    Class<?> c = Class.forName("org.spoopy.commands" + "." + StringUtils.capitalize(StringUtils.remove(args[0], "%")));
                    c.getDeclaredMethod("run", MessageCreateEvent.class, Object[].class).invoke(null, event, args);
                } else {
                    Class<?> c = Class.forName("org.spoopy.commands" + "." + StringUtils.capitalize(StringUtils.remove(command, "%")));
                    c.getDeclaredMethod("run", MessageCreateEvent.class).invoke(null, event);
                }
            }
        } catch(Exception e) {
            if (e.getClass().equals(ClassNotFoundException.class) || e.getClass().equals(NoSuchMethodException.class))
               event.getChannel().sendMessage("Are you fucking stupid? Invalid command!");
            else
                System.out.println(e);

        }
    }
}
