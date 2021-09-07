package org.spoopy.commands;

import org.javacord.api.event.message.MessageCreateEvent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.spoopy.api.SpoopApi.print;

public class Findghost {
    static Reader reader;
    static JSONParser parser;
    static JSONObject obj;

    static
    {
        try
        {
            reader = new FileReader("src/main/resources/ghosts.json");
            parser = new JSONParser();
            obj = (JSONObject) parser.parse(reader);
        } catch (IOException | ParseException e)
        {
            e.printStackTrace();
        }
    }

    public static void run(MessageCreateEvent event, Object... args) throws IOException, ParseException
    {
        ArrayList<Object> userGuess = new ArrayList<>(List.of(args));
        userGuess.remove(0);

        HashMap<Object, Object> results = new HashMap<>();

        obj.forEach((key, value) -> {
            JSONObject ghosts = (JSONObject) value;
            ghosts.forEach((ghost, evidences) -> {
                JSONObject evi = (JSONObject) evidences;
                evi.forEach((a, b) -> {
                    JSONArray eviRay = (JSONArray) b;
                    if (eviRay.containsAll(userGuess)) {
                        JSONArray container = new JSONArray();
                        container.addAll(eviRay);
                        container.removeAll(userGuess);
                        results.put(ghost,container);
                    }
                });
            });
        });

        event.getChannel().sendMessage("Possible ghosts: " + results);
        results.clear();
    }
}
