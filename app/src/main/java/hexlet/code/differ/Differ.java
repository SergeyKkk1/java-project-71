package hexlet.code.differ;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Differ {
    public static String generate(Map<String, Object> data1, Map<String, Object> data2) {
        List<Map.Entry<String, Object>> deletedKeys = new ArrayList<>();
        List<Map.Entry<String, Object>> addedKeys = new ArrayList<>();
        List<Map.Entry<String, Object>> changedKeys = new ArrayList<>();
        List<Map.Entry<String, Object>> commonKeys = new ArrayList<>();
        for (Map.Entry<String, Object> entry : data1.entrySet()) {
            if (!data2.containsKey(entry.getKey())) {
                deletedKeys.add(entry);
            } else if (!data2.get(entry.getKey()).equals(entry.getValue())) {
                changedKeys.add(entry);
                Object secondDataValue = data2.get(entry.getKey());
                changedKeys.add(Map.entry(entry.getKey(), secondDataValue));
            } else if (data1.containsKey(entry.getKey())) {
                commonKeys.add(entry);
            }
        }
        for (Map.Entry<String, Object> entry : data2.entrySet()) {
            if (!data1.containsKey(entry.getKey())) {
                addedKeys.add(entry);
            }
        }
        return generateString(deletedKeys, addedKeys, changedKeys, commonKeys);
    }

    private static String generateString(List<Map.Entry<String, Object>> deletedKeys, List<Map.Entry<String, Object>> addedKeys,
                                         List<Map.Entry<String, Object>> changedKeys, List<Map.Entry<String, Object>> commonKeys) {
        List<Map.Entry<String, String>> deletedKeysToString = deletedKeys.stream()
                .map(entry -> Map.entry(entry.getKey(), String.format("- %s: %s", entry.getKey(), entry.getValue())))
                .toList();
        List<Map.Entry<String, String>> addedKeysToString = addedKeys.stream()
                .map(entry -> Map.entry(entry.getKey(), String.format("+ %s: %s", entry.getKey(), entry.getValue())))
                .toList();
        List<Map.Entry<String, String>> changedKeysToString = new ArrayList<>();
        for (int i = 0; i < changedKeys.size(); i+=2) {
            Map.Entry<String, Object> entryFromFirstFile = changedKeys.get(i);
            changedKeysToString.add(Map.entry(entryFromFirstFile.getKey(),
                    String.format("- %s: %s", entryFromFirstFile.getKey(), entryFromFirstFile.getValue())));
            Map.Entry<String, Object> entryFromSecondFile = changedKeys.get(i + 1);
            changedKeysToString.add(Map.entry(entryFromSecondFile.getKey(),
                    String.format("+ %s: %s", entryFromSecondFile.getKey(), entryFromSecondFile.getValue())));
        }
        List<Map.Entry<String, String>> commonKeysToString = commonKeys.stream()
                .map(entry -> Map.entry(entry.getKey(), String.format("%s: %s", entry.getKey(), entry.getValue())))
                .toList();
        StringBuilder diffResult = new StringBuilder();
        diffResult.append("{\n");
        Stream.of(deletedKeysToString, addedKeysToString, changedKeysToString, commonKeysToString)
                .flatMap(Collection::stream)
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> diffResult.append("\s\s").append(entry.getValue()).append("\n"));
        diffResult.append("}\n");
        return diffResult.toString();
    }
}
