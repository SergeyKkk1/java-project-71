package hexlet.code;

import hexlet.code.formatter.Formatter;
import hexlet.code.parse.Parser;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class Differ {
    private Differ() {
    }

    public static String generate(String filePath1, String filePath2, String outputFormat) throws Exception {
        Map<String, Object> data1 = getData(filePath1);
        Map<String, Object> data2 = getData(filePath2);

        DiffResult diffResult = getDiffResult(data1, data2);
        return Formatter.format(diffResult, outputFormat);
    }

    public static String generate(String filePath1, String filePath2) throws Exception {
        Map<String, Object> data1 = getData(filePath1);
        Map<String, Object> data2 = getData(filePath2);

        DiffResult diffResult = getDiffResult(data1, data2);
        return Formatter.format(diffResult, "stylish");
    }

    private static Map<String, Object> getData(String filePath) throws Exception {
        Path path = Paths.get(filePath).toAbsolutePath().normalize();
        if (!Files.exists(path)) {
            throw new IllegalArgumentException("File not found: " + filePath);
        }
        String content = Files.readString(path);
        return Parser.parse(content, filePath);
    }

    private static DiffResult getDiffResult(Map<String, Object> data1, Map<String, Object> data2) {
        List<Map.Entry<String, Object>> deletedKeys = new ArrayList<>();
        List<Map.Entry<String, Object>> addedKeys = new ArrayList<>();
        List<Map.Entry<String, Object>> changedKeys = new ArrayList<>();
        List<Map.Entry<String, Object>> commonKeys = new ArrayList<>();
        for (Map.Entry<String, Object> entry : data1.entrySet()) {
            if (!data2.containsKey(entry.getKey())) {
                deletedKeys.add(entry);
            } else if (data2.get(entry.getKey()) == null || entry.getValue() == null
                    || !data2.get(entry.getKey()).equals(entry.getValue())) {
                changedKeys.add(entry);
                Object secondDataValue = data2.get(entry.getKey());
                changedKeys.add(new AbstractMap.SimpleEntry<>(entry.getKey(), secondDataValue));
            } else if (data1.containsKey(entry.getKey())) {
                commonKeys.add(entry);
            }
        }
        for (Map.Entry<String, Object> entry : data2.entrySet()) {
            if (!data1.containsKey(entry.getKey())) {
                addedKeys.add(entry);
            }
        }
        return new DiffResult(deletedKeys, addedKeys, changedKeys, commonKeys);
    }
}
