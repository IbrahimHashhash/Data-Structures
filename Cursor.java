
class Node{
    Object element;
    int next;
    public Node(Object element){
        this(element,0);
    }
    public Node(Object element,int next){
        this.element=element;
        this.next=next;
    }
}
public class Cursor {
    final int MAX_SIZE= 10;
    Node[] arr;
    public Cursor(){
        initialize();
    }
    public void initialize(){
        for(int i=0;i<MAX_SIZE;i++){
            arr[i] = new Node();
        }
        arr[MAX_SIZE-1].next=0;
    }

    public int cursorAlloc(){
        int p = arr[0].next;
        if(p!=0){
            arr[0].next=arr[p].next;
            return p;
        }else{
            return 0;
        }
    }
    public void createList(){
        int l = cursorAlloc();
        if(l!=0){
            arr[l] = new Node("-",0);
        }
    }
    public void cursorFree(int p){
        arr[p].element=null;
        arr[p].next=arr[0].next;
        arr[0].next=p;

    }
    public void insert(){

    }


}
