package hexlet.code.formatter;

import hexlet.code.DiffAction;
import hexlet.code.DiffEntry;
import hexlet.code.DiffResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class StylishFormatter {
    private StylishFormatter() {
    }

    public static String getFormattedData(DiffResult diffResult) {
        List<Map.Entry<String, String>> formattedEntries = new ArrayList<>();
        for (DiffEntry entry : diffResult.entries()) {
            if (entry.action() == DiffAction.DELETED) {
                formattedEntries.add(Map.entry(entry.key(),
                        String.format("- %s: %s", entry.key(), entry.oldValue())));
            } else if (entry.action() == DiffAction.ADDED) {
                formattedEntries.add(Map.entry(entry.key(),
                        String.format("+ %s: %s", entry.key(), entry.newValue())));
            } else if (entry.action() == DiffAction.CHANGED) {
                formattedEntries.add(Map.entry(entry.key(),
                        String.format("- %s: %s", entry.key(), entry.oldValue())));
                formattedEntries.add(Map.entry(entry.key(),
                        String.format("+ %s: %s", entry.key(), entry.newValue())));
            } else if (entry.action() == DiffAction.UNCHANGED) {
                formattedEntries.add(Map.entry(entry.key(),
                        String.format("  %s: %s", entry.key(), entry.oldValue())));
            }
        }
        StringBuilder diffResultString = new StringBuilder();
        diffResultString.append("{\n");
        formattedEntries.sort(Map.Entry.comparingByKey());
        for (Map.Entry<String, String> entry : formattedEntries) {
            diffResultString.append("  ").append(entry.getValue()).append("\n");
        }
        diffResultString.append("}");
        return diffResultString.toString().trim();
    }
}
