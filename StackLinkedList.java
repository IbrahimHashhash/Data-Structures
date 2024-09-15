import java.util.LinkedList;

public class StackLinkedList {
    private Node front;
    private int size;
    public StackLinkedList(){
        front=null;
        size=0;
    }

    public void push(Object element){
        Node newNode = new Node(element);
        newNode.setNext(front.getNext());
        front=newNode;
        size++;

    }
    public Object pop(){
        if(!isEmpty()) {
            Object top = front.getNext();
            front = front.getNext();
            size--;
            return top;
        }
        else
        return null;
    }
    public Object peek(){
        if(!isEmpty())
        return front.getElement();
        else
        return null;
    }

    public boolean isEmpty(){
        return size==0;
    }
    public int size(){
        return size;
    }
}
