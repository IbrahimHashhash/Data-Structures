package com.example.phase2;

public class Node {
    private Object element; // a private Object named element
    private Node next; // an Object Node that will be used as a refrence variable

    public Node(Object element) {
        this(element,null);
    } // a no-arg constructor
    public Node(Object element, Node next) { // an argument constructor
        this.element=element;
        this.next=next;
    }

    // Getter methods
    public Object getElement() {
        return this.element;
    }

    public Node getNext() {
        return this.next;
    }

    // Setter methods
    public void setElement(Object element) {
        this.element = element;
    }
    public void setNext(Node next) {
        this.next = next;
    }

}
