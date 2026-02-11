package hexlet.code.formatter;

import hexlet.code.DiffAction;
import hexlet.code.DiffEntry;
import hexlet.code.DiffResult;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public final class PlainFormatter {
    private PlainFormatter() {
    }

    public static String getFormattedData(DiffResult diffResult) {
        Map<String, String> formattedChanges = new TreeMap<>();

        for (DiffEntry entry : diffResult.entries()) {
            if (entry.action() == DiffAction.DELETED) {
                formattedChanges.put(entry.key(), "Property '" + entry.key() + "' was removed");
            } else if (entry.action() == DiffAction.ADDED) {
                formattedChanges.put(entry.key(), "Property '" + entry.key() + "' was added with value: "
                        + formatValue(entry.newValue()));
            } else if (entry.action() == DiffAction.CHANGED) {
                formattedChanges.put(entry.key(), "Property '" + entry.key() + "' was updated. From "
                        + formatValue(entry.oldValue()) + " to " + formatValue(entry.newValue()));
            }
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
