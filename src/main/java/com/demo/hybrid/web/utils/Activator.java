package com.demo.hybrid.web.utils;

import com.demo.hybrid.web.handlers.commands.ifaces.HasOptions;
import org.apache.commons.lang3.NotImplementedException;
import org.openqa.selenium.WebElement;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class Activator {

    public static <T> T createInstance(Class<?> toCreate, Class<? extends T> shouldImplement, Class<?>[] parameterTypes, Object... params) {

        if (!shouldImplement.isAssignableFrom(toCreate))
            throw new NotImplementedException("Handler doesn't implement necessary interfaces: " + toCreate + " Interface: " + shouldImplement);
        try {
            return (T)toCreate.getConstructor(parameterTypes).newInstance(params);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        throw new NotImplementedException("Something wrong happened during initialization of " + toCreate);
    }
}
