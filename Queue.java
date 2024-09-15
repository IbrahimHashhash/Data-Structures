public class Queue {
    private int front;
    private int rear;
    private int maxSize;
    private Object [] queueArray;

    public Queue(int maxSize){
        front=rear=-1;
        this.maxSize=maxSize;
        queueArray= new Object[maxSize];
    }

    public void enQueue(Object element) {
        if(isFull()){
            System.out.println("Queue is full");
        }
        else if(isEmpty()){
            front++;
            rear++;
            queueArray[rear] = element;
        }else{
            rear = (rear+1) % maxSize;
            queueArray[rear] = element;
        }
        System.out.println(front);
        System.out.println(rear);
        System.out.println("------");
    }

    public Object deQueue(){
        Object element = null;
        if(isEmpty2()){
            System.out.println("Queue is empty");
        }else if(front == rear){
            element = queueArray[front];
            front = rear = -1;
        }else{
            element = queueArray[front];
            front=(front+1)%maxSize;
        }
        return element;
    }

    public Object front(){//returns front
        if (isEmpty()) {
            System.out.println("Error: cannot return front from empty queue");
            return null;
        }
        return queueArray[front];
    }
    public boolean isEmpty(){
        return (front==-1 && rear==-1);
    }
    public boolean isEmpty2(){
        return (front==-1 && rear==-1);
    }


    public void clear(){
        front = rear = -1;
    }
    public int size() {
        if (isEmpty()) {
            return 0;
        } else if (isFull()) {
            return maxSize;
        } else {
            return (maxSize - front + rear) % maxSize + 1;
        }
    }


    public boolean isFull(){
        return ((rear+1)%maxSize==front);
    }

}
