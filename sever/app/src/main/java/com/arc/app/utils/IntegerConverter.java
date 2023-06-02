package com.arc.app.utils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

@Converter
public class IntegerConverter implements AttributeConverter<List<Integer>, String> {
    private static final String SPLIT_CHAR = ";";
    @Override
    public String convertToDatabaseColumn(List<Integer> integers) {
        return integers != null ? integers.stream().map(String::valueOf)
                .collect(Collectors.joining(SPLIT_CHAR)) : "";
    }

    @Override
    public List<Integer> convertToEntityAttribute(String s) {
        if (s == null || s.isEmpty()) {
            return emptyList();
        }
        List<Integer> rs = new ArrayList<>();
        for (String value : s.split(SPLIT_CHAR)) {
            rs.add(Integer.valueOf(value));
        }
        return rs;
    }
}
