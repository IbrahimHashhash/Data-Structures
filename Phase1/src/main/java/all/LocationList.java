package all;
/*
This class has a similar commenting to DistrictList class
 */
class LocationNode {
    private Object element; // Declares an Object
    private LocationNode next; // Declares a Node
    private final MartyrList head; // declares a MartyrList object

    public LocationNode(Object element) {
        this(element,null);
    } // no arg-constructor
    public LocationNode(Object element, LocationNode next) { // arg-constructor
        this.element = element;
        this.next = next;
        this.head = new MartyrList();
    }
    // getter methods
    public Object getElement() { // returns element
        return this.element;
    }

    public LocationNode getNext() { // returns next
        return this.next;
    }

    // setter methods
    public void setElement(Object element) {
        this.element = element;
    }
    public MartyrList getHead(){ // returns the head
        return this.head;
    }

    public void addListHead(Object element) {
        Martyr martyr = (Martyr) element;
        if(!head.contains(martyr)) {
            if (this.head.isEmpty() || ((Martyr) this.head.getFront().getElement()).getAge() >= martyr.getAge()) {
                // If the list is empty or the martyr's age is less than or equal to the age of the first martyr in the list,
                // add the martyr to the beginning of the list
                this.head.addFirst(martyr);
            } else {
                // Iterate through the list to find the correct position to insert the martyr
                MartyrList martyrList = this.head;
                MartyrNode current = martyrList.getFront();
                int index = 0;
                while (current != null && ((Martyr) current.getElement()).getAge() < martyr.getAge()) {
                    current = current.getNext();
                    index++;
                }
                // Insert the martyr at the correct index
                martyrList.add(martyr, index);
            }
        }else{
            System.out.println("Martyr already exists" +"Martyr name: " + martyr.getName());
        }
    }
    public void setNext(LocationNode next) {
        this.next = next;
    }
}


public class LocationList { // a location list class to store locations
    private LocationNode front,back;
    private int size;

    public LocationList(){
        front=back=null;
        size=0;
    }

    public void addFirst(Object element){
        LocationNode newNode = new LocationNode(element);
        if(size==0){
            front = back = newNode;
        }else{
            newNode.setNext(front);
            front=newNode;
        }
        size++;

    }
    public void addLast(Object element){ // adds the element to the last position
        LocationNode newNode = new LocationNode(element);
        if(size==0){
            front=back=newNode;
        }else{
            back.setNext(newNode);
            back = newNode;
        }
        size++;
    }

    public void add(Object element, int index) { // adds element to a specified position sorted
        if (index < 0 || index > size) {
            return;
        }

        Location newLocation = (Location) element;
        LocationNode newNode = new LocationNode(newLocation);

        if (size == 0 || newLocation.getLocationName().compareToIgnoreCase(((Location)front.getElement()).getLocationName()) < 0) {
            addFirst(newLocation);
        } else {
            LocationNode current = front;
            LocationNode prev = null;

            while (current != null && newLocation.getLocationName().compareToIgnoreCase(((Location)current.getElement()).getLocationName()) > 0) {
                prev = current;
                current = current.getNext();
            }

            if (current == null) {
                addLast(newLocation);
            } else {
                newNode.setNext(current);
                prev.setNext(newNode);
                size++;
            }
        }
    }
    public void addNode(LocationNode newNode, int index) { // adds node sorted
        if (index < 0 || index > size) {
            return;
        }

        LocationNode current = front;
        LocationNode prev = null;

        Location newLocation = (Location) newNode.getElement();

        while (current != null && newLocation.getLocationName().compareToIgnoreCase(((Location) current.getElement()).getLocationName()) > 0) {
            prev = current;
            current = current.getNext();
        }

        if (prev == null) { // insert at the beginning
            newNode.setNext(front);
            front = newNode;
        } else { // insert in the middle or at the end
            prev.setNext(newNode);
            newNode.setNext(current);
        }

        if (current == null) { // If the newNode is added at the end, update the last pointer
            back = newNode;
        }

        size++;
    }

    public LocationNode getNode(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        LocationNode current = front;
        for (int i = 0; i < index && current.getNext()!=null; i++) {
            current = current.getNext();
        }
        return current;
    }

    public boolean removeFirst(){ // removes the first element
        if(size == 0)
            return false;
        else
            front = front.getNext();
        size--;
        return true;
    }
    public boolean removeLast() { // remvoes the last element
        if (size == 0)
            return false;
        else if (size == 1) {
            front = back = null;
        } else {
            LocationNode current = front;
            for (int i = 0; i < size - 2; i++) { // Iterate until the second-to-last node
                current = current.getNext();
            }
            current.setNext(null); // Update the next reference of the second-to-last node
            back = current;
        }
        size--;
        return true;
    }
    public boolean remove(int index){ // removes an element by index
        if(size==0)
            return false;
        if(size == 1) {
            return removeFirst();
        }
        else if(index == size -1) {


            return removeLast();
        }
        else {
            LocationNode current = front;
            for (int i = 0; i < index -1; i++)
                current=current.getNext();

            current.setNext(current.getNext().getNext());
            back = current;
            size--;
            return true;
        }

    }
    public int lastIndexOf(Object o) { // a method used to get the index of specified object
        if (isEmpty()) {
            return -1;
        } else if (size == 1) {
            if (front.getElement().equals(o))
                return 0;
            else
                return -1;
        } else {

            LocationNode current = front;
            for (int i = 0; i < size; i++) {
                if (current.getElement().equals(o)) {
                    return i;
                }
                current = current.getNext();
            }
            return -1;
        }
    }
    @Override
    public String toString() { // toString method
        StringBuilder sb = new StringBuilder();
        LocationNode current = front;
        while (current != null) {
            sb.append(current.getElement().toString()).append(" ");
            current = current.getNext();
        }
        return sb.toString();
    }
    public void add(Object element){
        add(element,size);
    } // adds an element by object
    public int size(){
        return size;
    } // returns size
    public boolean isEmpty(){
        return size==0;
    } // checks if list is emoty

    public LocationNode getFront() {
        return front;
    } // returns the front node

    public LocationNode getBack() {
        return back;
    }
}