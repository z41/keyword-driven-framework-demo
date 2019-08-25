package com.demo.hybrid.web.providers;

import com.demo.hybrid.core.ifaces.RequiresCleanUp;
import com.google.inject.Provider;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static io.github.bonigarcia.wdm.DriverManagerType.CHROME;

public class WebDriverProvider implements Provider<WebDriver>, RequiresCleanUp {

    private WebDriver driver;

    public WebDriver get() {
        WebDriverManager.getInstance(CHROME).setup();
        return driver = new ChromeDriver();
    }

    @Override
    public void CleanUp() {
        driver.quit();
    }
}
