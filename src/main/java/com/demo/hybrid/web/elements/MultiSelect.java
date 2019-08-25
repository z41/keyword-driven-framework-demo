package com.demo.hybrid.web.elements;

import com.demo.hybrid.web.handlers.commands.ifaces.HasOptions;
import com.demo.hybrid.web.utils.LocatorHandler;
import com.demo.hybrid.web.utils.WebAutomationContext;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MultiSelect extends Element implements HasOptions {

    public MultiSelect(WebAutomationContext context, WebElement toWrap) {
        super(context, toWrap);
    }

    @Override
    public void select(String items) {
        LocatorHandler handler = context.locatorHandler;

        List<String> split = Arrays.stream(items.split(",")).map(String::trim).collect(Collectors.toList());

        WebElement expander = wrappedElement.findElement(handler.by(".selected-params"));
        expander.click();
        By itemsBy = handler.by(".multi-select-dropdown .checkbox-custom-label");

        waitUntil(dr -> wrappedElement.findElements(itemsBy).stream().anyMatch(WebElement::isDisplayed), 10);
        wrappedElement.findElements(itemsBy).stream().filter(e -> split.contains(e.getText().trim())).forEach(WebElement::click);
        expander.click();
    }
}
