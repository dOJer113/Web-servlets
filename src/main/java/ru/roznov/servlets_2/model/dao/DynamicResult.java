package ru.roznov.servlets_2.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


public class DynamicResult<T> {
    private Map<String, List<T>> fields = new HashMap<>();

    public void setField(String fieldName, T value) {
        if (this.containsField(fieldName)) {
            this.fields.get(fieldName).add(value);
        } else {
            List<T> list = new ArrayList<>();
            list.add(value);
            fields.put(fieldName, list);
        }
    }

    public int getCountFields() {
        return this.fields.size();
    }

    public int getCountRows() {
        Iterator<String> iterator = fields.keySet().iterator();
        return this.fields.get(iterator.next()).size();
    }

    public List<T> getField(String fieldName) {
        return this.fields.get(fieldName);
    }

    public boolean containsField(String fieldName) {
        return this.fields.containsKey(fieldName);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("|");
        Iterator<String> mapIterator = this.fields.keySet().iterator();
        int size = this.fields.get(mapIterator.next()).size();
        stringBuilder.append(this.getColumnsNamesFormatted()).append('\n').append(this.getSeparate()).append('\n');
        for (int i = 0; i < size; i++) {
            stringBuilder.append('|');
            for (String s : this.fields.keySet()) {
                String format = "%-0s";
                int maxLength = Math.max(s.length(), this.getMaxLengthColumnValue(this.fields.get(s)));
                format = format.replaceFirst("\\d+", String.valueOf(maxLength));
                stringBuilder.append(String.format(format, this.fields.get(s).get(i))).append('|');
            }
            stringBuilder.append('\n').append(this.getSeparate()).append('\n');
        }
        return stringBuilder.append('\n').toString();
    }


    private int getMaxLengthColumnValue(List<T> list) {
        OptionalInt maxLength = list.stream().map(Object::toString).mapToInt(String::length).max();
        return maxLength.getAsInt();
    }

    private String getSeparate() {
        StringBuilder separateRow = new StringBuilder("|");
        for (String s : this.fields.keySet()) {
            StringBuilder separateRowBuilder = new StringBuilder();
            int maxLength = Math.max(s.length(), this.getMaxLengthColumnValue(this.fields.get(s)));
            separateRowBuilder.append(this.buildSeparatorRow(maxLength));
            separateRow.append(separateRowBuilder);
        }
        return separateRow.toString();
    }

    private String getColumnsNamesFormatted() {
        int maxLength;
        StringBuilder sb = new StringBuilder();
        String format = "%-0s";
        for (String s : this.fields.keySet()) {
            maxLength = Math.max(s.length(), this.getMaxLengthColumnValue(this.fields.get(s)));
            format = format.replaceFirst("\\d+", String.valueOf(maxLength));
            sb.append(String.format(format, s)).append("|");
        }
        return sb.toString();
    }

    private String buildSeparatorRow(int maxLength) {
        StringBuilder separateRowBuilder = new StringBuilder();
        int countSeparateSymbols = maxLength;
        for (int i = 0; i < countSeparateSymbols; i++) {
            separateRowBuilder.append("-");
        }
        return separateRowBuilder.append("|").toString();
    }
    public static DynamicResult containDynamicResult(ResultSet resultSet) throws SQLException {
        DynamicResult dynamicResult = new DynamicResult();
        int countColumns = resultSet.getMetaData().getColumnCount();
        while (resultSet.next()) {
            for (int i = 1; i <= countColumns; i++) {
                dynamicResult.setField(resultSet.getMetaData().getColumnName(i), resultSet.getObject(i));
            }
        }
        return dynamicResult;
    }

}
