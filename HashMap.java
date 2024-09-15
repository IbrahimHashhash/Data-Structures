public class HashMap {
    private HashEntry[] hashArray;
    private final int maxSize;

    public HashMap(int maxSize) {
        this.maxSize = maxSize;
        this.hashArray = new HashEntry[maxSize];
    }

    private int hashFunction(int key) {
        return key % maxSize;
    }

    public void insert(int object) {
        int value = hashFunction(object);
        int i = 0;
        int index;
        while (i < maxSize) {
            index = (value + i) % maxSize;
            if (hashArray[index] == null || hashArray[index].getStatus() == 'E' || hashArray[index].getStatus() == 'D') {
                hashArray[index] = new HashEntry(object);
                return;
            }
            i++;
        }
        System.out.println("Hash table is full");
    }

    public Integer find(int object) {
        int value = hashFunction(object);
        int i = 0;
        int index;
        while (i < maxSize) {
            index = (value + i) % maxSize;
            if (hashArray[index] == null) {
                return null;
            }
            if (hashArray[index].getStatus() == 'F' && hashArray[index].getObject() == object) {
                return hashArray[index].getObject();
            }
            i++;
        }
        return null;
    }

    public void remove(int object) {
        int value = hashFunction(object);
        int i = 0;
        int index;

        while (i < maxSize) {
            index = (value + i) % maxSize;
            if (hashArray[index] == null) {
                System.out.println("Object not found");
                return;
            }
            if (hashArray[index].getStatus() == 'F' && hashArray[index].getObject() == object) {
                hashArray[index].setStatus('D');
                hashArray[index].setObject(null);
                System.out.println("Object has been deleted successfully");
                return;
            }
            i++;
        }
        System.out.println("Object not found.");
    }

    public void traverse() {
        for (int i = 0; i < maxSize; i++) {
            if (hashArray[i] != null) {
                System.out.println(i + ":" + hashArray[i].getObject() + " ");
            } else {
                System.out.println( i + ":" + "null ");
            }
        }
        System.out.println();
    }

}
