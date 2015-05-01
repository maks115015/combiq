package ru.atott.combiq.web.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Component;

@Component
public class ViewUtils {
    public String toJson(Object object) {
        return serializeToJson(object);
    }

    public static String serializeToJson(Object object) {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(object);
    }

    public static JsonObject parseJson(String json) {
        JsonParser parser = new JsonParser();
        return (JsonObject) parser.parse(json);
    }
}
