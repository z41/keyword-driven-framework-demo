package com.demo.hybrid.web.handlers.commands;

import com.demo.hybrid.web.UiCommandItem;
import com.demo.hybrid.web.utils.WebAutomationContext;
import org.apache.commons.lang3.NotImplementedException;
import org.jibx.schema.codegen.extend.DefaultNameConverter;
import org.jibx.schema.codegen.extend.NameConverter;
import org.openqa.selenium.WebElement;

import javax.inject.Inject;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Predicate;


public class Find extends UICommandHandler {
    @Inject
    public Find(WebAutomationContext context) {
        super(context);
    }

    private Map<String, BiFunction<List<WebElement>, Predicate<WebElement>, WebElement>> handlers = Map.ofEntries(
            Map.entry("find first in", this::first),
            Map.entry("find last in", this::last)
    );

    private static NameConverter nameTools = new DefaultNameConverter();


    @Override
    public void accept(UiCommandItem uiCommandItem) {



        if (!handlers.containsKey(uiCommandItem.getKeyword()))
            throw new NotImplementedException("Find cannot find a handler for: " + uiCommandItem.getKeyword());


        WebElement foundItem = handlers
                .get(uiCommandItem.getKeyword())
                .apply(context.findElements(uiCommandItem), GetPredicateFor(uiCommandItem.getValue()));
        String parentKey = nameTools.depluralize(uiCommandItem.getKeywordItem().getObjectName());
        context.addToParentElements(parentKey, foundItem, uiCommandItem.getHandlerId());
    }

    // To be refactored: move to some predicate library
    public Predicate<WebElement> GetPredicateFor(String value) {
        if (value.contains("text=")){
            String valueToSearch = value.replace("text=", "");
            return el -> el.getText().contains(valueToSearch);
        }
        throw new NotImplementedException("Cannot find a predicate for filter: " + value);
    }

    public WebElement first(List<WebElement> all, Predicate<WebElement> predicate){
        return predicate == null ?
                all.stream().findFirst().orElse(null) :
                all.stream().filter(predicate).findFirst().orElse(null);
    }

    private WebElement last(List<WebElement> all, Predicate<WebElement> predicate){
        if (all.size() ==0)
            return null;

        return predicate == null ?
                all.get(all.size() - 1) :
                // not the best way...
                all.stream().filter(predicate).reduce((first, second) -> second).orElse(null);
    }
}
