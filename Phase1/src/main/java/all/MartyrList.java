package all;
 class MartyrNode {
    private Object element; // a private Object named element
    private MartyrNode next; // an Object Node that will be used as a refrence variable

    public MartyrNode(Object element) {
        this(element,null);
    } // a no-arg constructor
    public MartyrNode(Object element, MartyrNode next) { // an argument constructor
        this.element=element;
        this.next=next;
    }


    // Getter methods
    public Object getElement() {
        return this.element;
    }

    public MartyrNode getNext() {
        return this.next;
    }

    // Setter methods
    public void setElement(Object element) {
        this.element = element;
    }
    public void setNext(MartyrNode next) {
        this.next = next;
    }
}
public class MartyrList {

    private MartyrNode front; // front Node that references to the first node
    private MartyrNode back; // back Node that references to the last node of the list
    private int mCounter,fCounter,totalAge; // male counter, female counter, total age count
    private int size;

    public MartyrList(){ // a no-arg construct that initializes the following attributes
        front=back=null;
        size=0;
        mCounter = 0;
        fCounter = 0;
    }

    public int calculateTotalFemales(){ // calculates the total females in the list
        MartyrNode current = front; // creates a current node equals to front
        while(current!=null){ // while current is not null
            if(((Martyr)current.getElement()).getGender()=='F') // if martyr's gender is equal to F increment the counter
                fCounter++;
            current=current.getNext(); // moves to the next node
        }
        return fCounter;
    }
    public int calculateTotalMales(){
        MartyrNode current = front;
        while(current!=null){
            if(((Martyr)current.getElement()).getGender()=='M') // if martyr's gender is equal to M increment the counter
                mCounter++;
            current=current.getNext();
        }
        return mCounter;
    }
    public double calculateAverage(){ // a method to calculate the average age of the list
        MartyrNode current = front;
        while(current!=null){
            totalAge +=((Martyr)current.getElement()).getAge(); // increments the total age
            current=current.getNext(); // moves to the next node
        }
        return (double) totalAge /size; // divides the total age by the number of martyrs
    }
    // getter methods
    public int getfCounter() {
        return fCounter;
    }

    public int getmCounter() {
        return mCounter;
    }

    public int getTotalAge() {
        return totalAge;
    }
    public double getAverageAge(){

        return (double) totalAge /size;
    }

    public String findMostCommonDeathDate() { // a method used to find the most common date in the list
        if (front == null) { // if front equal to null then return null
            return null;
        }
        MartyrNode current = front; // assigns the current node to front
        String mostCommonDate = ((Martyr)front.getElement()).getDate(); // assigns mostCommonDate to the first date in the list
        int maxCount = 1;

        while (current != null) { // while current is not equal to null
            String currentDate = ((Martyr)current.getElement()).getDate(); // create a currentDate variable and assigns it to the current node date
            int count = 1; // assigns count to 1

            // Count occurrences of currentDate in the rest of the list
            MartyrNode runner = current.getNext(); // creates a runner node to and assigns to the next node of current node
            while (runner != null) { // while runner is not equal to null
                if (currentDate.equals(((Martyr)runner.getElement()).getDate())) { // if current date is equal to the runner's date then increment the counter
                    count++; // increments the counter
                }
                runner = runner.getNext(); // moves the runner to the next node
            }
            if (count > maxCount) { // if count is greater than maxCount
                maxCount = count; // assigns the max count to count
                mostCommonDate = currentDate; // assigns most commonDate to current Date
            }

            current = current.getNext(); // moves the current to the next node
        }

        return mostCommonDate; // returns most common date
    }

    public void addFirst(Object element){ // add first method to add the martyr to the first node
        MartyrNode newNode = new MartyrNode(element);
        if(size==0){
            front = back = newNode; // reference the first and back node to the first node
        }else{
            newNode.setNext(front); // make the new Node references to the first node
            front=newNode;  // make front references to the new Node
        }
        Manager.getMartyrs().add((Martyr) element); // adds
        size++;
        char gender =((Martyr)front.getElement()).getGender();
        if(((Martyr)front.getElement()).getAge()!=-1) {
            totalAge += ((Martyr) front.getElement()).getAge();
        }
        updateCounters(gender);
    }
    public void addList(Object element){ // an add method for creating temp lists
        MartyrNode newNode = new MartyrNode(element);
        if(size==0){
            front=back=newNode;
        }else{
            back.setNext(newNode);
            back = newNode;
        }
        size++;
    }
    public void addLast(Object element){ // a method to add node to the last
        MartyrNode newNode = new MartyrNode(element);
        if(size==0){
            front=back=newNode;
        }else{
            back.setNext(newNode);
            back = newNode;
        }
        Manager.getMartyrs().add((Martyr) element);
        size++;
        char gender =((Martyr)back.getElement()).getGender();
        if(((Martyr)back.getElement()).getAge()!=-1) {
            totalAge += ((Martyr) back.getElement()).getAge();
        }
        updateCounters(gender);
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

            MartyrNode newNode = new MartyrNode(element);
            MartyrNode current = front;
            for(int i = 0; i < index - 1; i++) {
                current = current.getNext();
            }
            char gender =((Martyr)current.getElement()).getGender();
            updateCounters(gender);
            if(((Martyr)current.getElement()).getAge()!=-1) {
                totalAge += ((Martyr) current.getElement()).getAge();
            }
            newNode.setNext(current.getNext());
            current.setNext(newNode);
            size++;
            Manager.getMartyrs().add((Martyr) element);
        }
    }
    private void updateCounters(char gender) { // increments the counter when called
        if (gender == 'M' || gender == 'm') {
            mCounter++;
        } else if (gender == 'F' || gender == 'f') {
            fCounter++;
        }
    }
    public boolean removeFirst() { // removes the first node
        if (size == 0)
            return false;
        else {
            if (size == 1) {
                if(((Martyr)front.getElement()).getGender()=='F'){
                    fCounter--;
                }else{
                    mCounter--;
                }
                front = back = null; // If there's only one node, set front to null
            } else {
                if(((Martyr)front.getElement()).getGender()=='F'){
                    fCounter--;
                }else{
                    mCounter--;
                }
                front = front.getNext();
            }
            size--;
            return true;
        }
    }
    public void removeMartyrFromManager(Martyr martyr) {
        if (Manager.getMartyrs().contains(martyr)) {
            int index = Manager.getMartyrs().lastIndexOf(martyr);
            Manager.getMartyrs().remove(index);
        }
    }

    public boolean removeLast(){ // removes the last node of the list
        if (size == 0)
            return false;
        else if (size == 1) {
            if(((Martyr)back.getElement()).getGender()=='F'){
                fCounter--;
            }else{
                mCounter--;
            }
            front = back = null;
        }
        else {
            if(((Martyr)back.getElement()).getGender()=='F'){
                fCounter--;
            }else{
                mCounter--;
            }
            MartyrNode current = front;
            for (int i = 0; i < size - 2; i++) { // Iterate until the second-to-last node
                current = current.getNext();
            }
            removeMartyrFromManager((Martyr) current.getElement());
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
            removeMartyrFromManager((Martyr) front.getElement());
            return removeFirst();
        } else if (index == size - 1) {
            removeMartyrFromManager((Martyr) back.getElement());
            return removeLast();
        } else if (index >0 && index<size-1) {
            MartyrNode current = front;
            for (int i=0;i<index-1;i++)
                current=current.getNext();
            // Remove the martyr from the manager list
            removeMartyrFromManager((Martyr) current.getNext().getElement());

            // Update counters if needed
            char gender = ((Martyr) current.getNext().getElement()).getGender();
            if (gender == 'F') {
                fCounter--;
            } else {
                mCounter--;
            }

            // Remove the node by updating references
            current.setNext(current.getNext().getNext());
            size--;

            // If the last node is removed, update the back reference
            if (current.getNext() == null) {
                back = current;
            }

            return true;
        }
        return false;
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
    public Object get(int index) {
        if (size == 0 || index < 0 || index >= size)
            return null; // Invalid index or empty list

        if (index == 0)
            return getFirst(); // Return the first element if index is 0

        if (index == size - 1)
            return getLast(); // Return the last element if index is size - 1

        MartyrNode current = front;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        return current.getElement();
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
            MartyrNode current = front;
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

            MartyrNode current = front;
            for (int i = 0; i < size; i++) {
                if (current.getElement().equals(o)) {
                    return i;
                }
                current = current.getNext();
            }
            return -1;
        }
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

    public MartyrNode getFront() {
        return front;
    } // returns front

    public MartyrNode getBack() {
        return back;
    } // returns back
}