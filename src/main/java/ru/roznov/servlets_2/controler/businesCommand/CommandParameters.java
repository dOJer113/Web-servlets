package ru.roznov.servlets_2.controler.businesCommand;

import java.util.HashMap;
import java.util.Map;

public class CommandParameters {
    private final Map<String, Object> parameters = new HashMap<>();

    public void addParameter(String key, Object value) {
        parameters.put(key, value);
    }

    public Object getParameter(String key) {
        return parameters.get(key);
    }

    public <T> T getParameter(String key, Class<T> clazz) {
        return clazz.cast(parameters.get(key));
    }
}