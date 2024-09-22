package ru.roznov.servlets_2.controler.global;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.HashMap;
import java.util.Map;

public class ModifiableHttpServletRequest extends HttpServletRequestWrapper {

    private final Map<String, String> customParams;

    public ModifiableHttpServletRequest(HttpServletRequest request) {
        super(request);
        this.customParams = new HashMap<>();
    }

    public void addParameter(String name, String value) {
        customParams.put(name, value);
    }

    public void removeParameter(String name) {
        customParams.remove(name);
    }

    @Override
    public String getParameter(String name) {
        if (customParams.containsKey(name)) {
            return customParams.get(name);
        }
        return super.getParameter(name);
    }
}
