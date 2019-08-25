package com.demo.hybrid.core.entities;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Scenario {

    private List<KeywordItem> keywordItems = new ArrayList<>();
    private String name;
    private String suiteName;

    public Scenario(String name, String suiteName) {
        this.name = name;
        this.suiteName = suiteName;
    }
}
