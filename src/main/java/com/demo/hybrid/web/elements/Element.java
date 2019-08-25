package com.demo.hybrid.web.elements;

import com.demo.hybrid.web.utils.WebAutomationContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.function.Function;

public abstract class Element {
    protected WebAutomationContext context;
    protected WebElement wrappedElement;

    public Element(WebAutomationContext context, WebElement toWrap){

        this.context = context;
        this.wrappedElement = toWrap;
    }

    public <T> T waitUntil(Function<? super WebDriver, T> func, long timeOutInSeconds){
        WebDriverWait wait = new WebDriverWait(context.driver, timeOutInSeconds);
        return wait.until(func);
    }
}
