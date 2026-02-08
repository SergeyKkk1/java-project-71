package hexlet.code.formatter;

import hexlet.code.differ.DiffResult;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PlainFormatter {
    private PlainFormatter() {
    }

    public static String getFormattedData(DiffResult diffResult) {
        Map<String, String> formattedChanges = new TreeMap<>();

        for (Map.Entry<String, Object> entry : diffResult.deletedKeys()) {
            formattedChanges.put(entry.getKey(), "Property '" + entry.getKey() + "' was removed");
        }

        for (Map.Entry<String, Object> entry : diffResult.addedKeys()) {
            formattedChanges.put(entry.getKey(), "Property '" + entry.getKey() + "' was added with value: "
                    + formatValue(entry.getValue()));
        }

        List<Map.Entry<String, Object>> changedKeys = diffResult.changedKeys();
        for (int i = 0; i < changedKeys.size(); i += 2) {
            Map.Entry<String, Object> oldEntry = changedKeys.get(i);
            Map.Entry<String, Object> newEntry = changedKeys.get(i + 1);

            formattedChanges.put(oldEntry.getKey(), "Property '" + oldEntry.getKey() + "' was updated. From "
                    + formatValue(oldEntry.getValue()) + " to " + formatValue(newEntry.getValue()));
        }

        return String.join("\n", formattedChanges.values());
    }

    private static String formatValue(Object value) {
        if (value == null) {
            return "null";
        }
        if (value instanceof String) {
            return "'" + value + "'";
        }
        if (value instanceof Map || value instanceof Collection) {
            return "[complex value]";
        }
        return value.toString();
    }
}
