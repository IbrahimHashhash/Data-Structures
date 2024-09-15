package com.example.phase2;

public class MartyrList {
    private Node front,back;
    private int size;

    public MartyrList(){
        front=back=null;
        size=0;
    }

    public void addFirst(Object element){
        Node newNode = new Node(element);
        if(size==0){
            front = back = newNode;
        }else{
            newNode.setNext(front);
            front=newNode;
        }
        Style.getMartyrObList().addAll((Martyr) element);
        size++;

    }
    public void addLast(Object element){
        Node newNode = new Node(element);
        if(size==0){
            front=back=newNode;
        }else{
            back.setNext(newNode);
            back = newNode;
        }
        Style.getMartyrObList().addAll((Martyr) element);
        size++;

    }
    public void add(Object element, int index){

        if(index < 0 || index > size)
            return;

        if(index == 0) {
            addFirst(element);
        } else if(index == size) {
            addLast(element);
        } else {
            Node newNode = new Node(element);
            Node current = front;
            for(int i = 0; i < index - 1; i++) {
                current = current.getNext();
            }
            Style.getMartyrObList().addAll((Martyr) element);
            newNode.setNext(current.getNext());
            current.setNext(newNode);
            size++;
        }
    }

    public boolean removeFirst(){
        if(size == 0)
            return false;
        else
            front.setNext(front.getNext());
        size--;
        return true;
    }
    public boolean removeLast(){
        if (size==0)
            return false;
        else if (size==1)
            front=back=null;
        else{
            Node current= front;
            for (int i=0;i<size-2;i++)
                current=current.getNext();
            current.setNext(null);
            back=current;
        }
        size--;
        return true;
    }
    public boolean remove(int index){
        if(size==0)
            return false;
        if(size == 1)
            return removeFirst();
        else if(index == size -1)
            return removeLast();
        else {
            Node current = front;
            for (int i = 0; i < index -2; i++)
                current=current.getNext();
            back.setNext(current.getNext());
            current.setNext(null);
            size--;
            return true;
        }

    }
    public Object getFirst(){
        if(size == 0){
            return null;
        }
        return front.getElement();

    }
    public Object getLast(){

        if(size == 0){
            return null;
        }
        return back.getElement();
    }
    public Object get(int index){
        if(size==0)
            return null;
        else if(size==1)
            return getFirst();
        else if(index == size -1)
            return getLast();
        else if(index > 0 && index<size-1){
            Node current = front;
            for(int i=0;i<size-1;i++)
                current=current.getNext();
            return current.getElement();
        }else{
            return null;
        }
    }
    public Object get(Object target){
        if(size == 0)
            return null;
        else if(size == 1)
            return target.equals(getFirst()) ? getFirst(): null;
        else {
            Node current = front;
            while (current != null) {
                if (target.equals(current.getElement()))
                    return current.getElement();
                current = current.getNext();
            }
            return null; // If the target is not found
        }
    }

    public boolean contains(Object element){ // checks if an element is in the list
        if (element == null || front == null) {
            return false;
        }

        Martyr martyr = (Martyr) element;

        if(size==0){
            return false;
        } else if(size == 1){
            Object frontElement = front.getElement();
            if (frontElement != null && frontElement instanceof Martyr) {
                return ((Martyr)frontElement).getName().equalsIgnoreCase(martyr.getName());
            } else {
                return false;
            }
        } else {
            Node current = front;
            for (int i=0;i<size;i++) {
                current = current.getNext();
                if (current != null && current.getElement() instanceof Martyr) {
                    Martyr currentMartyr = (Martyr) current.getElement();
                    if (currentMartyr.getName() != null && currentMartyr.getName().equalsIgnoreCase(martyr.getName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public Object find(String martyrName){
        if (martyrName == null || front == null) {
            return null;
        }

        if (size == 0) {
            return null;
        } else if (size == 1) {
            Object frontElement = front.getElement();
            if (frontElement != null && frontElement instanceof Martyr) {
                Martyr frontMartyr = (Martyr) frontElement;
                if (frontMartyr.getName() != null && frontMartyr.getName().equalsIgnoreCase(martyrName)) {
                    return frontMartyr;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else {
            Node current = front;
            for (int i = 0; i < size; i++) {
                current = current.getNext();
                if (current != null && current.getElement() instanceof Martyr) {
                    Martyr currentMartyr = (Martyr) current.getElement();
                    if (currentMartyr.getName() != null && currentMartyr.getName().equalsIgnoreCase(martyrName)) {
                        return currentMartyr;
                    }
                }
            }
        }
        return null;
    }


    public boolean remove(Object element) {
        if (element == null || isEmpty()) {
            return false;
        }

        // Special case for removing the first element
        if (((Martyr)front.getElement()).getName().equalsIgnoreCase((String)element )) {
            Style.getMartyrObList().remove((Martyr) front.getElement());
            front = front.getNext(); // removes the front and goes to the next
            size--;
            return true;
        }

        Node current = front;
        Node prev = null;

        // Traverse the list to find the node containing the element
        while (current != null && !((Martyr)current.getElement()).getName().equalsIgnoreCase((String)element )) {
            prev = current;
            current = current.getNext();
        }

        // If the element is found, remove the node
        if (current != null) {
            Style.getMartyrObList().remove((Martyr) current.getElement());
            prev.setNext(current.getNext());
            if (current == back) {
                back = prev; // Update back if the last node is removed
            }
            size--;
            return true;
        }

        // Element not found
        return false;
    }

    public int lastIndexOf(Object o) { // returns the index of a specific object in the list
        System.out.println("The object entered: " + o);
        if (isEmpty()) {
            return -1;
        } else if (size == 1) {
            if (front.getElement().equals(o))
                return 0;
            else
                return -1;
        } else {

            Node current = front;
            for (int i = 0; i < size; i++) {
                if (current.getElement().equals(o)) {
                    return i;
                }
                current = current.getNext();
            }
            return -1;
        }
    }

    public Node getFront() {
        return front;
    }

    public void add(Object element){
        add(element,size);
    }
    public int size(){
        return size;
    }
    public boolean isEmpty(){
        return size==0;
    }
}