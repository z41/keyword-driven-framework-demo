package com.demo.hybrid;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;

public class ExcelSuite {

    private final Iterator<Object> tests;
    ExcelSuite(Iterator<Object> tests) {
        this.tests = tests;
    }

    @DataProvider(name = "data")
    public Iterator<Object> data(){
        return tests;
    }

    @Test(dataProvider = "data")
    public void Run(Runnable actualTest) {
        actualTest.run();
    }
}
