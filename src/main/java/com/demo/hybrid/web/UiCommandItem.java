package com.demo.hybrid.web;

import com.demo.hybrid.core.entities.KeywordItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.openqa.selenium.By;

@Data
@AllArgsConstructor
public class UiCommandItem {
    private String keyword, value, handlerId;
    private By by;
    private String parentKey;
    private KeywordItem keywordItem;
}
