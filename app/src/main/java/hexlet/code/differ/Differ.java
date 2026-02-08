package hexlet.code.differ;

import hexlet.code.formatter.Formatter;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Differ {
    private Differ() {
    }

    public static String calculateDiffResult(Map<String, Object> data1, Map<String, Object> data2, String outputFormat) {
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
        return Formatter.format(new DiffResult(deletedKeys, addedKeys, changedKeys, commonKeys), outputFormat);
    }
}
