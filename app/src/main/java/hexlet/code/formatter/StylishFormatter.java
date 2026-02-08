package hexlet.code.formatter;

import hexlet.code.differ.DiffResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class StylishFormatter {
    private StylishFormatter() {
    }

    public static String getFormattedData(DiffResult diffResult) {
        List<Map.Entry<String, String>> deletedKeysToString = diffResult.deletedKeys().stream()
                .map(entry -> Map.entry(entry.getKey(), String.format("- %s: %s", entry.getKey(), entry.getValue())))
                .toList();
        List<Map.Entry<String, String>> addedKeysToString = diffResult.addedKeys().stream()
                .map(entry -> Map.entry(entry.getKey(), String.format("+ %s: %s", entry.getKey(), entry.getValue())))
                .toList();
        List<Map.Entry<String, String>> changedKeysToString = new ArrayList<>();
        for (int i = 0; i < diffResult.changedKeys().size(); i += 2) {
            Map.Entry<String, Object> entryFromFirstFile = diffResult.changedKeys().get(i);
            changedKeysToString.add(Map.entry(entryFromFirstFile.getKey(),
                    String.format("- %s: %s", entryFromFirstFile.getKey(), entryFromFirstFile.getValue())));
            Map.Entry<String, Object> entryFromSecondFile = diffResult.changedKeys().get(i + 1);
            changedKeysToString.add(Map.entry(entryFromSecondFile.getKey(),
                    String.format("+ %s: %s", entryFromSecondFile.getKey(), entryFromSecondFile.getValue())));
        }
        List<Map.Entry<String, String>> commonKeysToString = diffResult.commonKeys().stream()
                .map(entry -> Map.entry(entry.getKey(), String.format("  %s: %s", entry.getKey(), entry.getValue())))
                .toList();
        StringBuilder diffResultString = new StringBuilder();
        diffResultString.append("{\n");
        Stream.of(deletedKeysToString, addedKeysToString, changedKeysToString, commonKeysToString)
                .flatMap(Collection::stream)
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> diffResultString.append("\s\s").append(entry.getValue()).append("\n"));
        diffResultString.append("}\n");
        return diffResultString.toString();
    }
}

