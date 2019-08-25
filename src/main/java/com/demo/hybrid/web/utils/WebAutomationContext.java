package com.demo.hybrid.web.utils;

import com.demo.hybrid.core.utils.ScopePropertiesReader;
import com.demo.hybrid.web.UiCommandItem;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

public class WebAutomationContext {

    public final WebDriver driver;
    public final LocatorHandler locatorHandler;
    public final ScopePropertiesReader propertiesReader;
    private final HashMap<String, WebElement> parentElements;
    // not smart, but a quick solution
    private final HashMap<String, String> parentTypes;

    @Inject
    public WebAutomationContext(WebDriver driver, LocatorHandler locatorHandler, ScopePropertiesReader propertiesReader) {
        this.driver = driver;
        this.locatorHandler = locatorHandler;
        this.propertiesReader = propertiesReader;
        this.parentElements = new HashMap<>();
        this.parentTypes = new HashMap<>();
    }

    public void addToParentElements(String key, WebElement parent, String type) {
        parentElements.put(key, parent);
        parentTypes.put(key, type);
    }

    public SearchContext getSearchContext(String key) {
        if (key == null)
            return driver;
        if (!parentElements.containsKey(key))
            // not the best exception to use here
            throw new NotFoundException("Cannot find parent to search in... check your excel file! parent name: " + key);
        return parentElements.get(key);
    }

    // think of a better place for the following methods
    // what about waits?
    public List<WebElement> findElements(UiCommandItem uiCommandItem){
        return getSearchContext(uiCommandItem.getParentKey()).findElements(uiCommandItem.getBy());
    }

    public WebElement findElement(UiCommandItem uiCommandItem){
        return getSearchContext(uiCommandItem.getParentKey()).findElement(uiCommandItem.getBy());
    }

    public WebElement findElement(UiCommandItem uiCommandItem, Predicate<WebElement> waitFor){
        // some hardcoded value for now
        WebDriverWait wait = new WebDriverWait(driver, 10);
        return wait.until(dr -> findElements(uiCommandItem).stream().filter(waitFor).findFirst().orElse(null));
    }
}
