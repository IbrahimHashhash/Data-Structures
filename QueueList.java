
class Node{
    private Object element;
    private Node next;
    public Node(Object element){
        this(element,null);
    }
    public Node(Object element,Node next){
        this.element=element;
        this.next=next;
    }

    public Object getElement() {
        return element;
    }

    public Node getNext() {
        return next;
    }

    public void setElement(Object element) {
        this.element = element;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}

public class QueueList {
    private Node front,rear;

    public QueueList(){
        front=rear=null;
    }
    public void enQueue(Object element){
        Node newNode = new Node(element);
        if(isEmpty()){
            System.out.println("Entered the isEmpty: " + element);
            front = rear =  newNode;
        }else{
            rear.setNext(newNode);
            rear=newNode;
        }
    }
    public Object front(){
        if(isEmpty()){
            System.out.println("Error: List is null");
            return null;
        }
        return front.getElement();
    }
    public Object deQueue(){
        Object first = null;
        if(isEmpty()){
            System.out.println("Error: List is Empty");
            return first;
        }else if(front == rear){
            first = front.getElement();
            front = rear = null;
            return first;
        }else {
            first = front.getElement();
            front = front.getNext();
            return first;
        }
    }
    public boolean isEmpty(){
        return (front == null && rear == null);
    }
}
