package all;

class ListNode {
    private Object element; // a private Object named element
    private ListNode next; // an Object Node that will be used as a refrence variable

    public ListNode(Object element) {
        this(element,null);
    } // a no-arg constructor
    public ListNode(Object element, ListNode next) { // an argument constructor
        this.element=element;
        this.next=next;
    }


    // Getter methods
    public Object getElement() {
        return this.element;
    }

    public ListNode getNext() {
        return this.next;
    }

    // Setter methods
    public void setElement(Object element) {
        this.element = element;
    }
    public void setNext(ListNode next) {
        this.next = next;
    }
}
public class SingleLinkedList {

    private ListNode front; // front Node that references to the first node
    private ListNode back; // back Node that references to the last node of the list
    private int size;

    public SingleLinkedList(){ // a no-arg construct that initializes the following attributes
        front=back=null;
        size=0;
    }


    public void addFirst(Object element){ // add first method to add the martyr to the first node
        ListNode newNode = new ListNode(element);
        if(size==0){
            front = back = newNode; // reference the first and back node to the first node
        }else{
            newNode.setNext(front); // make the new Node references to the first node
            front=newNode;  // make front references to the new Node
        }
        size++;
    }
    public void addList(Object element){ // an add method for creating temp lists
        ListNode newNode = new ListNode(element);
        if(size==0){
            front=back=newNode;
        }else{
            back.setNext(newNode);
            back = newNode;
        }
        size++;
    }
    public void addLast(Object element){ // a method to add node to the last
        ListNode newNode = new ListNode(element);
        if(size==0){
            front=back=newNode;
        }else{
            back.setNext(newNode);
            back = newNode;
        }
        size++;

    }
    public void add(Object element, int index){ // a method to add the node to a specified index
        if(index < 0 || index > size) {
            return;
        }

        if(index == 0) {
            addFirst(element);
        } else if(index == size) {
            addLast(element);
        } else {

            ListNode newNode = new ListNode(element);
            ListNode current = front;
            for(int i = 0; i < index - 1; i++) {
                current = current.getNext();
            }
            newNode.setNext(current.getNext());
            current.setNext(newNode);
            size++;
        }
    }
    public boolean removeFirst() { // removes the first node
        if (size == 0)
            return false;
        else {
            if (size == 1) {
                front = back = null; // If there's only one node, set front to null
            } else {
                front = front.getNext();
            }
            size--;
            return true;
        }
    }
    public boolean removeLast(){ // removes the last node of the list
        if (size == 0)
            return false;
        else if (size == 1) {
            front = back = null;
        }
        else {
            ListNode current = front;
            for (int i = 0; i < size - 2; i++) { // Iterate until the second-to-last node
                current = current.getNext();
            }
            current.setNext(null); // Update the next reference of the second-to-last node
            back = current;
        }
        size--;
        return true;
    }
    public boolean remove(int index) { // removes a node by index
        if (size == 0 || index < 0 || index >= size)
            return false; // index out of bounds or empty list

        if (size == 1) {
            return removeFirst();
        } else if (index == size - 1) {
            return removeLast();
        } else {
            ListNode current = front;
            for (int i = 0; i < index - 1; i++)
                current = current.getNext();

            current.setNext(current.getNext().getNext());
            size--;

            return true;
        }
    }
    public Object getFirst(){ // gets the first element
        if(size == 0){
            return null;
        }
        return front.getElement();

    }
    public Object getLast(){ // gets the last element

        if(size == 0){
            return null;
        }
        return back.getElement();
    }
    public Object get(int index){ // gets an element by index
        if(size==0)
            return null;
        else if(size==1)
            return getFirst();
        else if(index == size -1)
            return getLast();
        else if(index > 0 && index<size-1){
            ListNode current = front;
            for(int i=0;i<size-1;i++)
                current=current.getNext();
            return current.getElement();
        }else{
            return null;
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
            ListNode current = front;
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
    public int lastIndexOf(Object o) { // returns the index of a specific object in the list
        if (isEmpty()) {
            return -1;
        } else if (size == 1) {
            if (front.getElement().equals(o))
                return 0;
            else
                return -1;
        } else {

            ListNode current = front;
            for (int i = 0; i < size; i++) {
                if (current.getElement().equals(o)) {
                    return i;
                }
                current = current.getNext();
            }
            return -1;
        }
    }
    public boolean remove(Object element) { // remove index by object
        if (size == 0)
            return false;
        else {
            ListNode current = front;
            for (int i = 0; i < size; i++) {
                if (current.getElement().equals(element))
                    return remove(i);
                current = current.getNext();
            }
        }
        return false;
    }




    public void add(Object element){
        add(element,size);
    } // adds element to the list
    public int size(){
        return size;
    } // returns size
    public boolean isEmpty(){
        return size==0;
    } // checks if size is empty

    public ListNode getFront() {
        return front;
    } // returns front

    public ListNode getBack() {
        return back;
    } // returns back
}