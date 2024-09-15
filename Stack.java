public class Stack {

    private int top;
    private int maxSize;
    private Object[] stackArray;


    public Stack(int maxSize){
        this.maxSize=maxSize;
        stackArray = new Object[maxSize];
        top = -1;
    }
    public void push(Object element){
        stackArray[++top] = element;
    }
    public Object peek(){
        if(isEmpty())
            return null;

        return stackArray[top];
    }
    public Object pop(){
        if(isEmpty())
            return null;
        Object temp = stackArray[top--];
        return temp;
    }
    public boolean isEmpty(){
        return top==-1;
    }
    public boolean isFull(){
        return top == maxSize;
    }
    public int size(){
        return top+1;
    }
}
