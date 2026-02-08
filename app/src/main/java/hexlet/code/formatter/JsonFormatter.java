package hexlet.code.formatter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.differ.DiffResult;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JsonFormatter {

    private JsonFormatter() {
    }

    public static String getFormattedData(DiffResult diffResult) {
        List<Map<String, Object>> formattedList = new ArrayList<>();

        for (Map.Entry<String, Object> entry : diffResult.addedKeys()) {
            Map<String, Object> node = new LinkedHashMap<>();
            node.put("key", entry.getKey());
            node.put("type", "added");
            node.put("value", entry.getValue());
            formattedList.add(node);
        }

        for (Map.Entry<String, Object> entry : diffResult.deletedKeys()) {
            Map<String, Object> node = new LinkedHashMap<>();
            node.put("key", entry.getKey());
            node.put("type", "deleted");
            node.put("value", entry.getValue());
            formattedList.add(node);
        }

        for (Map.Entry<String, Object> entry : diffResult.commonKeys()) {
            Map<String, Object> node = new LinkedHashMap<>();
            node.put("key", entry.getKey());
            node.put("type", "unchanged");
            node.put("value", entry.getValue());
            formattedList.add(node);
        }

        List<Map.Entry<String, Object>> changed = diffResult.changedKeys();
        for (int i = 0; i < changed.size(); i += 2) {
            Map.Entry<String, Object> oldEntry = changed.get(i);
            Map.Entry<String, Object> newEntry = changed.get(i + 1);

            Map<String, Object> node = new LinkedHashMap<>();
            node.put("key", oldEntry.getKey());
            node.put("type", "changed");
            node.put("oldValue", oldEntry.getValue());
            node.put("newValue", newEntry.getValue());
            formattedList.add(node);
        }

        formattedList.sort(Comparator.comparing(m -> (String) m.get("key")));

        try {
            return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(formattedList);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
