package com.demo.hybrid.web.elements;

import com.demo.hybrid.web.handlers.commands.ifaces.HasOptions;
import com.demo.hybrid.web.utils.LocatorHandler;
import com.demo.hybrid.web.utils.WebAutomationContext;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import java.util.List;

public class Select2 extends Element implements HasOptions {


    public Select2(WebAutomationContext context, WebElement toWrap) {
        super(context, toWrap);
    }

    @Override
    public void select(String items) {
        // Quick hack
        ((JavascriptExecutor) context.driver)
                .executeScript("let item = document.querySelector('.cookie-disclaimer__content'); if (typeof(item)==='undefined') return; item.style.display='none';");

        LocatorHandler handler = context.locatorHandler;
        // the simplest case is coded
        // normally I use different approach for element wrappers
        // actually this class is full of dirty hacks. Shame on me...

        wrappedElement.findElement(handler.by(".select2-selection__rendered")).click();

        By itemLocator = handler.by(".select2-results__options.open li");
        // Normally I would prefer to use Xpath for this
        waitUntil(dr -> context.driver.findElements(itemLocator).stream().anyMatch(WebElement::isDisplayed), 10);

        wrappedElement.findElement(handler.by("./..//input")).sendKeys(items);
        waitUntil(dr -> context.driver.findElements(itemLocator).stream().filter(e -> e.getText().equals(items)).findFirst().orElse(null), 10).click();
        //List<WebElement> allElements = context.driver.findElements(itemLocator);
        // ((JavascriptExecutor)context.driver).executeScript("arguments[0].find(n => n.innerText == arguments[1]).click();", allElements, items);

    }
}
