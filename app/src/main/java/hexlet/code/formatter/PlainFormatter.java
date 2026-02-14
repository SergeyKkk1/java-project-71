package hexlet.code.formatter;

import hexlet.code.DiffAction;
import hexlet.code.DiffEntry;
import hexlet.code.DiffResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public final class PlainFormatter {
    private PlainFormatter() {
    }

    public static String getFormattedData(DiffResult diffResult) {
        List<String> formattedChanges = new ArrayList<>();

        for (DiffEntry entry : diffResult.entries()) {
            if (entry.action() == DiffAction.DELETED) {
                formattedChanges.add("Property '" + entry.key() + "' was removed");
            } else if (entry.action() == DiffAction.ADDED) {
                formattedChanges.add("Property '" + entry.key() + "' was added with value: "
                        + formatValue(entry.newValue()));
            } else if (entry.action() == DiffAction.CHANGED) {
                formattedChanges.add("Property '" + entry.key() + "' was updated. From "
                        + formatValue(entry.oldValue()) + " to " + formatValue(entry.newValue()));
            }
        }

        return String.join("\n", formattedChanges);
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
