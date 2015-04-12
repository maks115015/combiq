package ru.atott.combiq.web.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
}
