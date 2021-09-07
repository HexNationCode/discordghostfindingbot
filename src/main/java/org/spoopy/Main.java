package org.spoopy;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

public class Main {

    final static char prefix = '!';

    public static void main(String[] args)
    {
        DiscordApi api = new DiscordApiBuilder().setToken(args[0]).login().join();

        api.addMessageCreateListener(CommandManager::run);
    }
}
