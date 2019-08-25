package com.demo.hybrid.web.handlers.commands;

import com.demo.hybrid.web.UiCommandItem;
import com.demo.hybrid.web.elements.MultiSelect;
import com.demo.hybrid.web.elements.Select2;
import com.demo.hybrid.web.handlers.commands.ifaces.HasOptions;
import com.demo.hybrid.web.utils.Activator;
import com.demo.hybrid.web.utils.WebAutomationContext;
import org.apache.commons.lang3.NotImplementedException;
import org.openqa.selenium.WebElement;

import javax.inject.Inject;
import java.util.AbstractMap;
import java.util.Map;

public class Select extends UICommandHandler {

    private Map<String, Class<?>> handlers = Map.ofEntries(
            new AbstractMap.SimpleEntry<>("Select2", Select2.class),
            new AbstractMap.SimpleEntry<>("MultiSelect", MultiSelect.class)
    );

    @Inject
    public Select(WebAutomationContext context) {
        super(context);
    }

    @Override
    public void accept(UiCommandItem uiCommandItem) {
        if (uiCommandItem.getHandlerId() == null)
        {
            // TODO: use actual select
            throw new NotImplementedException("I'm too lazy");
        }

        WebElement toWrap = context.findElement(uiCommandItem);
        Class<?> handler = handlers.getOrDefault(uiCommandItem.getHandlerId(), null);

        Activator.createInstance(handler, HasOptions.class,
                // just because WebElement is interface. Have to think about better solution
                new Class[] {WebAutomationContext.class, WebElement.class},
                context, toWrap).select(uiCommandItem.getValue());
    }
}
