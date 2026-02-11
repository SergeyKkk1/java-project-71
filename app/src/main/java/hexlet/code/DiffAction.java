package hexlet.code;

public enum DiffAction {
    DELETED("deleted"),
    ADDED("added"),
    CHANGED("changed"),
    UNCHANGED("unchanged");

    private final String label;

    DiffAction(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
