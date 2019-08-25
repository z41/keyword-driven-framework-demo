package com.demo.hybrid.web.handlers.commands;

import com.demo.hybrid.web.UiCommandItem;
import com.demo.hybrid.web.utils.WebAutomationContext;

import javax.inject.Inject;

public class GoTo extends UICommandHandler {

    @Inject
    public GoTo(WebAutomationContext context) {
        super(context);
    }

    @Override
    public void accept(UiCommandItem uiCommandItem) {
        context.driver.get(uiCommandItem.getValue());
    }
}
