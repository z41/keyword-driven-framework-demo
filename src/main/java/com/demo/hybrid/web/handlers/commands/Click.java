package com.demo.hybrid.web.handlers.commands;

import com.demo.hybrid.web.UiCommandItem;
import com.demo.hybrid.web.utils.WebAutomationContext;
import org.openqa.selenium.WebElement;

import javax.inject.Inject;

public class Click extends UICommandHandler {

    @Inject
    public Click(WebAutomationContext context) {
        super(context);
    }

    @Override
    public void accept(UiCommandItem uiCommandItem) {
        context.findElement(uiCommandItem, WebElement::isDisplayed).click();
    }
}
