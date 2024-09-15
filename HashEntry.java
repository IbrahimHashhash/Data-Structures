public class HashEntry {
    private Object key;
    private Object value;
    private char status;
    private Object reference;

    // Constants for status
    public static final char OCCUPIED = 'O';
    public static final char EMPTY = 'E';
    public static final char DELETED = 'D';

    // Constructor for occupied entry
    public HashEntry(Object key, Object value) {
        this(key, value, OCCUPIED, null);
    }

    // General constructor
    public HashEntry(Object key, Object value, char status, Object reference) {
        this.key = key;
        this.value = value;
        this.status = status;
        this.reference = reference;
    }

    // Getters and setters
    public Object getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    public Object getReference() {
        return reference;
    }

    public void setReference(Object reference) {
        this.reference = reference;
    }

    @Override
    public String toString() {
        return "HashEntry{key=" + key + ", value=" + value + ", status=" + status + ", reference=" + reference + "}";
    }
}
