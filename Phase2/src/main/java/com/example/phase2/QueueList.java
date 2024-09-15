package com.example.phase2;
/*
A Custom Queue LinkedList class
 */
public class QueueList {
    private Node front, rear; // front, rear nodes

    private int size; // size

    public QueueList() { // constructor
        front = rear = null;
        size = 0;
    }

    public void enQueue(Object element) { // enQueue method, inserts an element and sets the front to it, if it was the first node
        Node newNode = new Node(element);
        if (isEmpty()) {
            front = rear = newNode;
        } else {
            rear.setNext(newNode);
            rear = newNode;
        }
        size++;
    }

    public Object front() { // gets the front of the queue
        if (isEmpty()) {
            System.out.println("Error: Queue is empty");
            return null;
        }
        return front.getElement();
    }

    public Object deQueue() { // removes an element and returns its value
        if (isEmpty()) {
            System.out.println("Error: Queue is empty");
            return null;
        }
        Object removed = front.getElement();
        if (front == rear) {
            front = rear = null;
        } else {
            front = front.getNext();
        }
        size--;
        return removed;
    }
    public void clear(){
        this.front=this.rear=null;
    } // clears the queue



    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}
