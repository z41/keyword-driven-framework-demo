package com.demo.hybrid.keyword;

import com.demo.hybrid.core.entities.KeywordItem;
import com.demo.hybrid.core.entities.Scenario;
import com.demo.hybrid.core.exceptions.SuiteParseException;
import com.demo.hybrid.core.ifaces.CommandHandler;
import com.demo.hybrid.core.ifaces.KeywordHandler;
import com.demo.hybrid.core.utils.ExcelReader;
import com.demo.hybrid.web.UiCommandItem;
import com.demo.hybrid.web.handlers.commands.UICommandHandler;
import com.demo.hybrid.web.handlers.quieries.UIQueryHandler;
import com.demo.hybrid.web.utils.LocatorHandler;
import com.demo.hybrid.web.utils.WebAutomationContext;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;
import org.apache.commons.lang3.NotImplementedException;
import org.openqa.selenium.By;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.stream.StreamSupport;

public class TestGenerator {

    private final ExcelReader testReader;
    private final Injector injector;
    private LocatorHandler locatorHandler;
    private WebAutomationContext context;


    @Inject
    public TestGenerator(ExcelReader testReader, Injector injector, LocatorHandler locatorHandler, WebAutomationContext context){
        this.testReader = testReader;
        this.injector = injector;
        this.locatorHandler = locatorHandler;
        this.context = context;
    }

    public Iterator<Object> iterator(InputStream resource, String suiteName) throws IOException, SuiteParseException {
        testReader.loadFromResource(resource, suiteName);
        return StreamSupport.stream(testReader.spliterator(), false).map(this::generateSteps).iterator();
    }

    // Intentionally using Object - for testNG
    private Object generateSteps(Scenario scenario) {

        return new Runnable() {
            @Override
            public void run() {
                for (KeywordItem item: scenario.getKeywordItems()) {

                    // A bit of mess here. To be refactored, left as it is for now.
                    KeywordHandler command = injector.getInstance(Key.get(CommandHandler.class, Names.named(item.getKeyword())));
                    if (command instanceof UICommandHandler)
                    {
                        ((UICommandHandler)command).accept(ConvertToUiCommandItem(item));
                    }
                    else if (command instanceof UIQueryHandler)
                    {
                        // ((UIQueryHandler)command)
                    }
                    else
                    {
                        throw new NotImplementedException("Cannot find a handler for the command: " + item.getKeyword());
                    }
                }
            }

            public String toString(){
                return scenario.toString();
            }
        };
    }

    // A bit of mess. To be refactored
    private UiCommandItem ConvertToUiCommandItem(KeywordItem item) {
        String[] parentAndValue = item.getObjectName().split("\\.");
        String propertyName = parentAndValue.length > 1 ? parentAndValue[1] : parentAndValue[0];
        String parentKey = parentAndValue.length > 1 ? parentAndValue[0] : null;

        String propertyValue = context.propertiesReader.GetKeyOrNull("", propertyName, null);

        By locator = null;
        String handlerId = null;
        if (propertyValue != null)
        {
            String[] locatorAndHandler = propertyValue.split("\\|\\|");
            locator = context.locatorHandler.by(locatorAndHandler[0]);
            handlerId = locatorAndHandler.length > 1 ? locatorAndHandler[1] : null;
        }
        return new UiCommandItem(item.getKeyword(), item.getValue(), handlerId, locator, parentKey, item);
    }

}
