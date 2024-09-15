package com.example.finalprojectever;
public class DLMaxHeap {
    private Entry[] heap;
    private int heapSize;
    private static final int DEFAULT_CAPACITY = 10;

    static class Entry {
        String object;
        int counter;

        Entry(String object) {
            this.object = object;
            this.counter = 1;
        }
    }

    public DLMaxHeap() {
        heap = new Entry[DEFAULT_CAPACITY];
        heapSize = 0;
    }

    public void insert(String object) {
        int index = findIndex(object);
        if (index != -1) {
            heap[index].counter++;
            heapifyUp(index);
        } else {
            if (heapSize == heap.length - 1) {
                resize();
            }
            heapSize++;
            int i = heapSize;
            heap[i] = new Entry(object);
            heapifyUp(i);
        }
    }

    public void decrementEntry(String object) {
        int index = findIndex(object);
        if (index != -1) {
            if (heap[index].counter > 1) {
                heap[index].counter--;
                heapifyDown(index);
            } else {
                delete(index);
            }
        }
    }

    private int findIndex(String object) {
        for (int i = 1; i <= heapSize; i++) {
            if (heap[i].object.equals(object)) {
                return i;
            }
        }
        return -1;
    }

    private void heapifyUp(int index) {
        while (index > 1 && heap[index].counter > heap[parent(index)].counter) {
            swap(index, parent(index));
            index = parent(index);
        }
    }

    private void heapifyDown(int index) {
        int left = leftChild(index);
        int right = rightChild(index);
        int largest = index;

        if (left <= heapSize && heap[left].counter > heap[largest].counter) {
            largest = left;
        }

        if (right <= heapSize && heap[right].counter > heap[largest].counter) {
            largest = right;
        }

        if (largest != index) {
            swap(index, largest);
            heapifyDown(largest);
        }
    }

    private void delete(int index) {
        heap[index] = heap[heapSize];
        heapSize--;
        heapifyDown(index);
    }

    private void resize() {
        Entry[] newHeap = new Entry[heap.length * 2];
        System.arraycopy(heap, 1, newHeap, 1, heapSize);
        heap = newHeap;
    }

    private int parent(int i) {
        return i / 2;
    }

    private int leftChild(int i) {
        return 2 * i;
    }

    private int rightChild(int i) {
        return 2 * i + 1;
    }

    private void swap(int i, int j) {
        Entry temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }
    public String getRoot(){
        return heap[1].object;
    }
}
