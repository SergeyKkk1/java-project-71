package hexlet.code;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class DiffCalculator {
    private DiffCalculator() {
    }

    public static DiffResult getDiffResult(Map<String, Object> data1, Map<String, Object> data2) {
        List<DiffEntry> entries = new ArrayList<>();
        for (Map.Entry<String, Object> entry : data1.entrySet()) {
            String key = entry.getKey();
            Object oldValue = entry.getValue();
            if (!data2.containsKey(key)) {
                entries.add(new DiffEntry(key, oldValue, null, DiffAction.DELETED));
            } else if (data2.get(key) == null || oldValue == null || !data2.get(key).equals(oldValue)) {
                entries.add(new DiffEntry(key, oldValue, data2.get(key), DiffAction.CHANGED));
            } else if (data1.containsKey(key)) {
                entries.add(new DiffEntry(key, oldValue, data2.get(key), DiffAction.UNCHANGED));
            }
        }
        for (Map.Entry<String, Object> entry : data2.entrySet()) {
            if (!data1.containsKey(entry.getKey())) {
                entries.add(new DiffEntry(entry.getKey(), null, entry.getValue(), DiffAction.ADDED));
            }
        }
        return new DiffResult(entries);
    }
}
