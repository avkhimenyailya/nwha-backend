package io.grayproject.nwha.api.util;

import io.grayproject.nwha.api.domain.AnswerValue;
import io.grayproject.nwha.api.repository.AnswerValueRepository;
import io.grayproject.nwha.api.repository.OptionRepository;
import io.grayproject.nwha.api.repository.TraitRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class InitAnswerValues {
    private final TraitRepository traitRepository;
    private final OptionRepository optionRepository;
    private final AnswerValueRepository answerValueRepository;

    public void initAnswerValues() {
        Map<String, List<String>> data = taskParse();
        long i = 1;

        List<AnswerValue> answerValues = new ArrayList<>();
        for (String key : data.keySet()) {
            optionRepository.findById(i++).ifPresent(option -> {
                        if(option.getId() == 56) {
                            System.out.println();
                        }
                        List<String> attrNames = data.get(key);
                        attrNames.forEach(attrName -> {
                            traitRepository.findAttributeByName(attrName).ifPresent(attr ->
                                    answerValues.add(AnswerValue
                                                    .builder()
                                                    .option(option)
                                                    .attribute(attr)
                                                    .value(1)
                                                    .build())
                            );
                        });
                    }
            );
        }

        answerValueRepository.saveAll(answerValues);
    }

    @SneakyThrows
    private static Map<String, List<String>> taskParse() {
        FileInputStream file = new FileInputStream("src/main/resources/primitives/Tasks.xlsx");
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0);

        List<String> attributesNames = new ArrayList<>();
        Map<String, List<String>> data = new LinkedHashMap<>();

        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                row.forEach(cell -> attributesNames.add(cell.getStringCellValue()));
            } else {
                String key = null;
                List<String> values = new ArrayList<>();
                for (Cell cell : row) {
                    if (cell.getColumnIndex() == 3) {
                        key = cell.getStringCellValue();
                    }
                    if (cell.getColumnIndex() == 2) {
                        continue;
                    }
                    if (cell.getCellType().equals(CellType.NUMERIC)) {
                        if (cell.getNumericCellValue() == 1d) {
                            String attrName = attributesNames.get(cell.getColumnIndex());
                            values.add(attrName.trim());
                        }
                    }
                }
                if (key != null && !key.isBlank()) {
                    String[] split = key.split("/");
                    key = split[1].trim();
                    data.put(key, values);
                }
            }
        }
        return data;
    }
}
