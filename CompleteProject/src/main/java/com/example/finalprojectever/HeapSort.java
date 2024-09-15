package com.example.finalprojectever;

public class HeapSort {
    private Martyr[] Heap;
    private int heapSize;
    private int maxsize;

    public HeapSort() {
        heapSize = 0;
        maxsize = 10; // initial size of the heap
        Heap = new Martyr[maxsize];
    }

    public void insert(Martyr value) {
        if (heapSize >= maxsize - 1) {
            doubleSize(); // resize the array if it's full
        }
        heapSize++;
        int i = heapSize;
        Heap[i] = value;
        // traverse up and fix violated property
        while (i > 1 && Heap[i].getAge() > Heap[parent(i)].getAge()) {
            swap(i, parent(i));
            i = parent(i);
        }
    }

    private void heapify(int i) {
        int largest = i;
        int left = left(i);
        int right = right(i);

        if (left <= heapSize && Heap[left].getAge() > Heap[largest].getAge()) {
            largest = left;
        }

        if (right <= heapSize && Heap[right].getAge() > Heap[largest].getAge()) {
            largest = right;
        }

        if (largest != i) {
            swap(i, largest);
            heapify(largest);
        }
    }

    private void doubleSize() { // a method to double the size of the array
        maxsize *= 2;
        Martyr[] newHeap = new Martyr[maxsize];
        System.arraycopy(Heap, 1, newHeap, 1, heapSize);
        Heap = newHeap;
    }

    private void swap(int i, int j) { // a method to swap martyrs
        Martyr tmp = Heap[i];
        Heap[i] = Heap[j];
        Heap[j] = tmp;
    }

    private int parent(int i) {
        return i / 2;
    } // gets parent

    private int left(int i) {
        return 2 * i;
    } // gets left

    private int right(int i) {
        return 2 * i + 1;
    } // gets right

    public void heapSort() { // heap sort method
        int n = heapSize;
        for (int i = n; i >= 2; i--) {
            swap(1, i);
            heapSize--;
            heapify(1);
        }
        heapSize = n; // restore the original size
    }

    public void print(MartyrTableView martyrTableView) { // prints the heap array to the tableview
        heapSort();
        for (int i = 1; i <= heapSize; i++) {
            martyrTableView.getItems().add(Heap[i]);
        }
        System.out.println();
    }
}
