package hexlet.code;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public final class DiffCalculator {
    private DiffCalculator() {
    }

    public static DiffResult getDiffResult(Map<String, Object> data1, Map<String, Object> data2) {
        List<DiffEntry> entries = new ArrayList<>();
        Set<String> keys = new TreeSet<>(data1.keySet());
        keys.addAll(data2.keySet());
        for (String key : keys) {
            boolean inFirst = data1.containsKey(key);
            boolean inSecond = data2.containsKey(key);
            Object oldValue = data1.get(key);
            Object newValue = data2.get(key);
            if (inFirst && !inSecond) {
                entries.add(new DiffEntry(key, oldValue, null, DiffAction.DELETED));
            } else if (!inFirst && inSecond) {
                entries.add(new DiffEntry(key, null, newValue, DiffAction.ADDED));
            } else if (newValue == null || !newValue.equals(oldValue)) {
                entries.add(new DiffEntry(key, oldValue, newValue, DiffAction.CHANGED));
            } else {
                entries.add(new DiffEntry(key, oldValue, newValue, DiffAction.UNCHANGED));
            }
        }
        return new DiffResult(entries);
    }
}
