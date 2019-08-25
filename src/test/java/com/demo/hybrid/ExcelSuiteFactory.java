package com.demo.hybrid;

import com.demo.hybrid.core.exceptions.SuiteParseException;
import com.demo.hybrid.keyword.KeywordDrivenWebModule;
import com.demo.hybrid.keyword.TestGenerator;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import java.io.IOException;
import java.io.InputStream;

public class ExcelSuiteFactory {

    @Factory(dataProvider = "excel")
    public Object[] generateTests(String fileName, String suiteName) throws IOException, SuiteParseException {

        InputStream resource = getClass().getClassLoader().getResourceAsStream(fileName);
        Injector injector = Guice.createInjector(new KeywordDrivenWebModule());

        return new Object[] {new ExcelSuite(injector.getInstance(TestGenerator.class).iterator(resource, suiteName))};
    }

    @DataProvider(name = "excel")
    public Object[][] excel() {
        return new Object[][]{
                {"suites/01. Smoke suite.xlsx", "01. Smoke suite"}
        };
    }
}
