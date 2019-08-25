package com.demo.hybrid.core.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

public class ScopePropertiesReader {

    private HashMap<String, Properties> properties = new HashMap<>();

    public String GetKeyOrNull(String scope, String key, String defaultValue) {

        // hardcoded at the moment
        scope = "careers.properties";
        if (!properties.containsKey(scope))
        {
            InputStream stream = getClass().getClassLoader().getResourceAsStream("scopes/" + scope);
            Properties scopeProperties = new Properties();
            assert stream != null;
            try {
                scopeProperties.load(stream);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            properties.put(scope, scopeProperties);
        }
        return properties.get(scope).getProperty(key, defaultValue);
    }
}
