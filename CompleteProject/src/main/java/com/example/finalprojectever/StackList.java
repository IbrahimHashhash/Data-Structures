package com.example.finalprojectever;

public class StackList {
    private Node front; // sentinel node
    private int size;

    public StackList() {
        front = new Node(null); // Sentinel node
        size = 0;
    }

    public void push(Object element) { // pushes the element to the linkedlist
        Node newNode = new Node(element);
        newNode.setNext(front);
        front=newNode;
        size++;
    }

    public Object pop() { // removing the element and returns it
        if (!isEmpty()) {
            Object top = front.getElement();
            front = front.getNext();
            size--;
            return top;
        } else {
            return null;
        }
    }

    public Object peek() {  // peek method that returns the front element in the list
        if (!isEmpty()) {
            return front.getNext().getElement();
        } else {
            return null;
        }
    }

    public boolean isEmpty() {
        return size == 0;
    } // checks if stack is empty

    public int size() {
        return size;
    }
}
