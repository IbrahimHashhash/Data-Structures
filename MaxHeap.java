public class MaxHeap {
    private int[] array;
    private int heapSize;
    private int maxsize;

    public MaxHeap() {
        heapSize = 0;
        maxsize = 10;
        array = new int[maxsize];
    }

    public MaxHeap(int[] array) {
        buildHeap(array);
    }

    public void buildHeap(int[] array) {
        heapSize = array.length;
        maxsize = heapSize + 1;
        this.array = new int[maxsize];
        System.arraycopy(array, 0, this.array, 1, heapSize);
        for (int i = heapSize / 2; i >= 1; i--) {
            if (!isLeaf(i)) {
                maxHeapify(i);
            }
        }
    }

    public void Insert(int value) {
        if (heapSize >= maxsize - 1) {
            doubleSize();
        }
        heapSize++;
        int i = heapSize;
        array[i] = value;
        // Traverse up and fix violated property
        while (i > 1 && array[i] > array[parent(i)]) {
            swap(i, parent(i));
            i = parent(i);
        }
    }

    private void maxHeapify(int pos) {
        int largest = pos;
        int left = left(pos);
        int right = right(pos);

        if (left <= heapSize && array[left] < array[largest]) {
            largest = left;
        }

        if (right <= heapSize && array[right] < array[largest]) {
            largest = right;
        }

        if (largest != pos) {
            swap(pos, largest);
            maxHeapify(largest);
        }
    }

    private boolean isLeaf(int pos) {
        return pos > heapSize / 2 && pos <= heapSize;
    }

    private void doubleSize() {
        maxsize *= 2;
        int[] newHeap = new int[maxsize];
        System.arraycopy(array, 1, newHeap, 1, heapSize);
        array = newHeap;
    }

    private void swap(int fpos, int spos) {
        int tmp = array[fpos];
        array[fpos] = array[spos];
        array[spos] = tmp;
    }

    private int parent(int i) {
        return i / 2;
    }

    private int left(int i) {
        return 2 * i;
    }

    private int right(int i) {
        return 2 * i + 1;
    }

    public int extractMax() {
        if (heapSize == 0) {
            System.out.println("Heap is Empty");
            return -1;
        }
        int max = array[1];
        array[1] = array[heapSize];
        array[heapSize] = 0;
        heapSize--;
        maxHeapify(1);
        return max;
    }

    public void heapSort(int [] array) {
        buildHeap(array);
        int n = heapSize;
        for (int i = n; i >= 2; i--) {
            swap(1, i);
            heapSize--;
            maxHeapify(1);
        }
        heapSize = n;
    }

    public void print() {
        for (int i = 1; i <= heapSize; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        MaxHeap maxHeap = new MaxHeap();

        int[] array = {7, 6, 4, 5, 3, 1, 9};

        System.out.println("Heap Sort:");
        maxHeap.heapSort(array);
        maxHeap.print();
        System.out.println("----------------");
        maxHeap.buildHeap(array);
        System.out.println("Max Heap:");
        maxHeap.print();

        System.out.println("----------------");
        System.out.println("Insert : " + 30 );
        maxHeap.Insert(30);
        maxHeap.print();
        System.out.println("----------------");
        System.out.println("Insert : " + 20 );
        maxHeap.Insert(20);
        maxHeap.print();
        System.out.println("----------------");
        System.out.println("Insert : " + 10 );
        maxHeap.Insert(10);
        maxHeap.print();


        System.out.println("----------------");

        System.out.println("Extracted Max: " + maxHeap.extractMax());
        maxHeap.print();

        System.out.println("----------------");
        System.out.println("Extracted Max: " + maxHeap.extractMax());
        maxHeap.print();

        System.out.println("----------------");
        System.out.println("Extracted Max: " + maxHeap.extractMax());
        maxHeap.print();

        System.out.println("----------------");
        System.out.println("Extracted Max: " + maxHeap.extractMax());
        maxHeap.print();

        System.out.println("----------------");
        System.out.println("Extracted Max: " + maxHeap.extractMax());
        maxHeap.print();

        System.out.println("----------------");
        System.out.println("Extracted Max: " + maxHeap.extractMax());
        maxHeap.print();
        System.out.println("----------------");
        System.out.println("Extracted Max: " + maxHeap.extractMax());
        maxHeap.print();
        System.out.println("----------------");
        System.out.println("Extracted Max: " + maxHeap.extractMax());
        maxHeap.print();
        System.out.println("----------------");
        System.out.println("Extracted Max: " + maxHeap.extractMax());
        maxHeap.print();
    }
}
