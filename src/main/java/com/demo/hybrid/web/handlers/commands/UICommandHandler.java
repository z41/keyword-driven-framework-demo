package com.demo.hybrid.web.handlers.commands;

import com.demo.hybrid.core.ifaces.CommandHandler;
import com.demo.hybrid.web.UiCommandItem;
import com.demo.hybrid.web.utils.WebAutomationContext;
import lombok.AllArgsConstructor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.inject.Inject;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

@AllArgsConstructor(onConstructor = @__({@Inject}))
public abstract class UICommandHandler implements Consumer<UiCommandItem>, CommandHandler {

    protected WebAutomationContext context;

    public <T> T waitUntil(Function<? super WebDriver, T> func, long timeOutInSeconds){
        WebDriverWait wait = new WebDriverWait(context.driver, timeOutInSeconds);
        return wait.until(func);
    }

}
