package com.demo.hybrid.web.utils;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;

public class LocatorHandler {
    public By by(String locator){

        if (locator.startsWith("xpath=") ||
                locator.startsWith("//") ||
                locator.startsWith("/html") ||
                locator.startsWith(".//") ||
                locator.startsWith("./") ||
                locator.startsWith("(//") ||
                locator.startsWith("(.//") ||
                locator.startsWith("(./")) {
            return By.xpath(LastAfterSplit(locator, "xpath="));
        }
        else if(locator.startsWith("name=")){
            return By.name(LastAfterSplit(locator, "name="));
        }
        else if(locator.startsWith("tag=")){
            return By.tagName(LastAfterSplit(locator, "tag="));
        }
        else if(locator.startsWith("id=")){
            return By.id(LastAfterSplit(locator, "id="));
        }
        else {
            return By.cssSelector(LastAfterSplit(locator, "css="));
        }
    }

    private String LastAfterSplit(String toSplit, String by){
        String [] afterSplit = toSplit.split(by);
        return afterSplit[afterSplit.length-1];
    }
}
