package com.demo.hybrid.core.utils;

import com.demo.hybrid.core.entities.KeywordItem;
import com.demo.hybrid.core.entities.Scenario;
import com.demo.hybrid.core.exceptions.SuiteParseException;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class ExcelReader implements Iterable<Scenario> {

    private List<Scenario> scenarios;

    public void loadFromResource(InputStream resource, String suiteName) throws IOException, SuiteParseException {

        XSSFWorkbook book = new XSSFWorkbook(resource);
        book.setMissingCellPolicy(Row.CREATE_NULL_AS_BLANK);

        Iterator<Row> rowIterator = book.getSheetAt(0).rowIterator();
        if (!rowIterator.hasNext())
            return;

        // skipping the first column
        rowIterator.next();
        scenarios = new ArrayList<>();
        Scenario currentScenario = null;

        while (rowIterator.hasNext()) {
            Row currentRow = rowIterator.next();

            if (!StringUtils.isEmpty(currentRow.getCell(0).getStringCellValue())) {
                currentScenario = new Scenario(currentRow.getCell(0).getStringCellValue(), suiteName);
                scenarios.add(currentScenario);
            } else if (currentScenario == null) {
                throw new SuiteParseException("Scenario was not started");
            } else {
                currentScenario.getKeywordItems().add(
                        new KeywordItem(
                                currentRow.getCell(1).getStringCellValue(),
                                currentRow.getCell(2).getStringCellValue(),
                                currentRow.getCell(3).getStringCellValue()
                        ));
            }
        }
    }

    @NotNull
    public Iterator<Scenario> iterator() {
        return scenarios.iterator();
    }
}
