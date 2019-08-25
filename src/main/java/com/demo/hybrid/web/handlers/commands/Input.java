package com.demo.hybrid.web.handlers.commands;

import com.demo.hybrid.web.UiCommandItem;
import com.demo.hybrid.web.utils.WebAutomationContext;
import org.openqa.selenium.WebElement;

import javax.inject.Inject;

public class Input extends UICommandHandler{

    @Inject
    public Input(WebAutomationContext context) {
        super(context);
    }

    @Override
    public void accept(UiCommandItem uiCommandItem) {
        WebElement el = context.findElement(uiCommandItem, WebElement::isDisplayed);
        el.clear();
        el.sendKeys(uiCommandItem.getValue());
    }
}
