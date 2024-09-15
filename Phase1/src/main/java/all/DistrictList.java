
package all;
/*
a custom doublyLinkedList class that contains various methods to handle different operations
 */
public class DistrictList  {
    public class Node { // an inner class so it has access to the method in the DistrictList class
        private Node prev, next;
        private final LocationList head; // a locationList object that is used as a reference variable
        private Object element; // an object

        public Node(Object element) { // public constructor with 1 argument
            this(element, null, null);
        }

        public Node(Object element, Node prev, Node next) { // public constructor with 3 arguments
            this.element = element;
            this.prev = prev;
            this.next = next;
            this.head = new LocationList(); // creates a new Object of LocationList everytime a node is created
        }
        public int totalMartyrs(int index){ //calculates the total martyrs of the list
            int counter = 0; // assigns the counter to 0
            LocationNode current = getDistrictNode(index).getHead().getFront();
            while (current!=null) {
                counter += current.getHead().size();
                current = current.getNext();
            }
            return counter;
        }
        public int totalMales(int index){ // method to calculate the total males
            int counter = 0;
            LocationNode current = getDistrictNode(index).getHead().getFront();
            while (current!=null) {
                counter += current.getHead().getmCounter();
                current = current.getNext();
            }
            return counter;
        }
        public int totalFemales(int index){ // method to calculate the total females
            int counter = 0;
            LocationNode current = getDistrictNode(index).getHead().getFront();
            while (current!=null) {
                counter += current.getHead().getfCounter();
                current = current.getNext();
            }
            return counter;
        }
        public String findMostCommonDeathDate(int districtIndex) {  // a method to find the most common date in a district
            // the time complexity is O(n^2)
            // Get the district node at the specified index
            Node districtNode = getDistrictNode(districtIndex);
            SingleLinkedList dates = new SingleLinkedList();
            if (districtNode == null) {
                return null; // District node not found
            }

            // initialize variables to track the most common death date
            String mostCommonDate = null;
            // traverse through the martyrs within the specified district node
            LocationNode locationNode = districtNode.getHead().getFront();
            while (locationNode != null) { // if location Node is not equal to null
                MartyrNode martyrNode = locationNode.getHead().getFront();
                while (martyrNode != null) { // if martyr Node is not equal to null
                    String currentDate = ((Martyr) martyrNode.getElement()).getDate();
                    dates.add(currentDate); // stores all the possible dates in the linkedlist
                    martyrNode = martyrNode.getNext(); // moves to the next node
                }
                locationNode = locationNode.getNext(); // moves to the next node
            }

            int maxCounter = 0; // initialize the maxCounter to keep track of the maximum occurrence
            ListNode dateNode = dates.getFront(); // get the front node of the linked list

            // Iterate through the linked list of dates
            while (dateNode != null) {
                String currentDate = (String) dateNode.getElement(); // Get the date from the current node
                int counter = getCounter(dates, currentDate);

                // Update maxCounter if the current counter is greater
                if (maxCounter < counter) {
                    maxCounter = counter;
                    mostCommonDate = currentDate;
                }

                dateNode = dateNode.getNext(); // Move to the next date node
            }



            return mostCommonDate;
        }

        private int getCounter(SingleLinkedList dates, String currentDate) {
            int counter = 0; // initialize a counter for the current date's occurrences

            // iterate through the linked list again to count occurrences of the current date
            ListNode current2 = dates.getFront(); // Start from the front again
            while (current2 != null) {
                String currentDate2 = (String) current2.getElement(); // Get the date from the runner node
                if (currentDate.equals(currentDate2)) {
                    counter++; // Increment counter if the dates match
                }
                current2 = current2.getNext(); // Move to the next node
            }
            return counter;
        }


        public double getAverage(int index){ // a method to calculate the average age for each district
            int counter1 = 0; // counter for the total age
            int counter2 = 0; // counter for the tatal size
            LocationNode current = getDistrictNode(index).getHead().getFront();
            while (current!=null) {
                counter1 += current.getHead().getTotalAge();
                counter2 += current.getHead().size();

                current = current.getNext(); // moves to the next location
            }
            return  (double) counter1 /counter2;
        }

        // Getter methods
        public Node getPrev() {
            return this.prev;
        }

        public Node getNext() {
            return this.next;
        }

        public LocationList getHead() {
            return this.head;
        }

        public Object getElement() {
            return this.element;
        }

        // Setter methods
        public void setPrev(Node prev) {
            this.prev = prev;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public void addListNode(Object element) {
           this.head.add(element);
        }

        public void setElement(Object element) {
            this.element = element;
        }
    }

    private Node front, back; // node for front, back
    private int size; // size

    public DistrictList() { // public constructor
        front = back = null;


        size = 0;
    }


    public void addFirst(Object element) { // adds distirct to front
        Node newNode = new Node(element);
        if (size == 0) {
            front = back = newNode;
        } else {
            newNode.setNext(front);
            front.setPrev(newNode);
            front = newNode;
        }
        Manager.getCboDistrict().add((District) element);
        size++;
    }

    public void addLast(Object element) { // adds district to the last
        Node newNode = new Node(element);
        if (size == 0) {
            front = back = newNode;
        } else {
            newNode.setPrev(back);
            back.setNext(newNode);
            back = newNode;
        }
        Manager.getCboDistrict().add((District) element);
        size++;
    }
    public void add(Object element, int index) { // adds the districts sorted to the list
        District district = (District) element;

        if (index < 0 || index > size) {
            return;
        }

        if (isEmpty()) {
            addFirst(district);
            return;
        }
        if (size == 1 && (((District) front.getElement()).getDistrict().toLowerCase().compareTo(district.getDistrict().toLowerCase()) > 0)) {
            addFirst(district);
            return;
        }

        Node newNode = new Node(district);
        Node current = front;

        // traverse the list to find the correct position to insert
        while (current != null && ((District) current.getElement()).getDistrict().toLowerCase().compareTo(district.getDistrict().toLowerCase()) <= 0) {
            current = current.getNext();
        }

        // if the element should be inserted at the beginning
        if (current == front) {
            addFirst(district);
            return;
        }

        // if the element should be inserted at the end
        if (current == null) {
            addLast(district);
            return;
        }

        // insert the new node before the current node
        newNode.setPrev(current.getPrev());
        newNode.setNext(current);
        current.getPrev().setNext(newNode);
        current.setPrev(newNode);
        size++;
        Manager.getCboDistrict().add(district);
    }
    public boolean removeFirst() { // removes the first district
        if (size == 0)
            return false;
        else if (size == 1) {
            front = back = null;
            size--;
            return true;
        } else {
            front = front.getNext();
            front.setPrev(null);
            size--;
            return true;
        }
    }

    public boolean removeLast() { //removes the last district
        if (size == 0)
            return false;
        else if (size == 1) {
            front = back = null;
            size--;
            return true;
        } else {
            back = back.getPrev();
            back.setNext(null);
            size--;
            return true;
        }
    }

    public boolean remove(int index) { // removes district by index
        if (size == 0 || index < 0 || index >= size) {
            return false;
        }
        else if (size == 1) {
            Manager.getCboDistrict().remove(front.getElement());
            return removeFirst();
        } else if (index == 0) {
            Manager.getCboDistrict().remove(front.getElement());
            return removeFirst();
        } else if (index == size - 1) {
            Manager.getCboDistrict().remove(back.getElement());
            return removeLast();
        } else {
            // If removing from the middle
            Node current = front;
            for (int i = 0; i < index; i++) {
                current = current.getNext();
            }
            Manager.getCboDistrict().remove(current.getElement());
            current.getPrev().setNext(current.getNext());
            current.getNext().setPrev(current.getPrev());
            size--;
            return true;
        }
    }

    public Node getDistrictNode(int index) { //returns the district node by index
        if(index==0){
            return front;
        }else if(index == size-1){
            return back;

        }
         else if (index > 0 && index < size - 1) {
            Node current = front;
            for (int i = 0; i < index; i++)
                current = current.getNext();
            return current;
        } else
            return null;
    }

    public boolean remove (Object element) { // removes node by object
        if (size==0)
            return false;
        else
        {
            Node current=front;
            for (int i=0;i<size;i++)
            {
                if (current.element.equals(element))
                    return remove(i);
                current=current.next;
            }
        }
        return false; //there is no object inside the list
    }

    public boolean exists(Object element) { // checks if a district exists or not
        if (size != 0) {
            Node current = front;
            while (current != null) {
                Object currentElement = current.getElement();
                if (currentElement != null && currentElement.toString().equalsIgnoreCase(element.toString())) {
                    return false;
                }
                current = current.getNext();
            }
        }
        return true;
    }



    public LocationNode getLocationNode(District district, Location location) { // returns the locationNode
        Node districtNode = getDistrictNode(lastIndexOf(district));
        if (districtNode != null) {
            LocationNode current = districtNode.getHead().getFront();
            while (current != null) {
                if (current.getElement().equals(location)) {
                    return current;
                }
                current = current.getNext();
            }
        }
        return null;
    }

    public void add(Object element) {
        add(element, size);
    }

    public void clear() { // clears the district
        this.front = this.back = null;
        this.size=0;
        Manager.getCboDistrict().clear();
        Manager.getMartyrs().clear();
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public int lastIndexOf(Object o) { // returns the last index of the district
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
    public void addList(District district, Location location) {
        if (isEmpty()) {
            System.out.println("District list is empty.");
        } else {
            Node current = front;
            while (current != null) {
                if (current.getElement().equals(district)) {
                    // Check if the location already exists in the LocationList
                    LocationNode locationNode = current.getHead().getFront();
                    boolean locationExists = false; // boolean value to check if location exists or not
                    while (locationNode != null) {
                        if (locationNode.getElement().equals(location)) {
                            locationExists = true;
                            break;
                        }
                        locationNode = locationNode.getNext();
                    }
                    // If location doesn't exist, add it to the LocationList
                    if (!locationExists) {
                        current.addListNode(location);
                    }
                    return;
                }
                current = current.getNext();
            }
            System.out.println("District not found: " + district);
        }
    }
    public Node getDistrictByLocation(Location location ){ // method that returns the node of a specified location
        Node current = front;
        while (current!=null){
            LocationNode currentLocation = current.getHead().getFront(); // declares and assigns the currentLocation node to the front
            while(currentLocation!=null){ // while not equal null
                if(currentLocation.getElement().equals(location)){ // if currentLocation equals to location
                    return current; // returns the node of the district
                }
                currentLocation = currentLocation.getNext(); // moves to the next location
            }
            current= current.getNext(); // moves to the next district
        }
        return null;
    }
    public Martyr getMartyrByLocation(District district, Location location, String partName) { // gets the martyr by location
        int index = lastIndexOf(district);
        LocationNode locationNode = getDistrictNode(index).getHead().getNode(getDistrictNode(index).getHead().lastIndexOf(location));
        if (locationNode != null) {
            MartyrNode current = locationNode.getHead().getFront();
            while (current != null) {
                Martyr martyr = (Martyr) current.getElement();
                if (martyr.getName().equalsIgnoreCase(partName)) {
                    return martyr;
                }
                current = current.getNext(); // moves to the next martyr
            }
        }
        return null;
    }
    public LocationNode getLocations(String l) { // gets the location node by its name
        Location location = new Location(l);
        System.out.println(l);
        Node currentDistrict = front;
        while (currentDistrict != null) {
            LocationNode currentLocation = currentDistrict.getHead().getFront();

            while (currentLocation != null) {
                if (currentLocation.getElement().equals(location)) {
                    return currentLocation;
                }

                currentLocation = currentLocation.getNext();// moves to the next location
            }

            currentDistrict = currentDistrict.getNext();// moves to the next district
        }
        return null;
    }

    public MartyrList getMartyrs(String partName) { // a method used to get martyr by part of his name
        MartyrList matchingMartyrs = new MartyrList(); // creates a martyr list to store the martyrs that match
        partName = partName.trim().toLowerCase(); // trims the name and converts it to lower case
        Node currentDistrict = front; // creates currentDistrict node
        while (currentDistrict != null) { // while currentDistrict is not equal to null
            LocationNode currentLocation = currentDistrict.getHead().getFront(); // creates currentLocation node
            while (currentLocation != null) {
                MartyrNode currentMartyr = currentLocation.getHead().getFront(); // creates martyrNode
                while (currentMartyr != null) {
                    Martyr martyr = (Martyr) currentMartyr.getElement(); // martyr object
                    String fullName = martyr.getName().toLowerCase(); // the full name of the martyr
                    if (fullName.startsWith(partName) || fullName.contains(partName)) { // checks if full is starts with or contains part name
                        matchingMartyrs.addList(martyr); // if yes, then it adds it to the list
                    }
                    currentMartyr = currentMartyr.getNext(); // moves to the next martyr
                }
                currentLocation = currentLocation.getNext(); // moves to the next location
            }
            currentDistrict = currentDistrict.getNext(); // moves to the next District
        }
        return matchingMartyrs; // returns the list
    }

    public MartyrList getMartyrByLocation(String location, String partName) {
        MartyrList matchingMartyrs = new MartyrList();
        LocationNode locationNode = getLocations(location);
        if (locationNode != null) {
            MartyrNode current = locationNode.getHead().getFront();
            while (current != null) {
                Martyr martyr = (Martyr) current.getElement();
                String fullName = martyr.getName().toLowerCase();
                if (fullName.startsWith(partName) || fullName.contains(partName)) {
                    matchingMartyrs.addList(martyr);
                }
                current = current.getNext(); // moves the current to the next location
            }
        }
        return matchingMartyrs; // returns the list
    }

    public MartyrList getMartyrByDistrict(String district, String partName) {
        MartyrList matchingMartyrs = new MartyrList();
        Node districtNode = getDistrictNode(lastIndexOf(new District(district))); // gets the martyr by distirct
        if (districtNode != null) {
            LocationNode currentL = districtNode.getHead().getFront();
            if (currentL != null) {
                MartyrNode current = currentL.getHead().getFront();
                while (current != null) {
                    Martyr martyr = (Martyr) current.getElement();
                    String fullName = martyr.getName().toLowerCase();
                    if (fullName.startsWith(partName) || fullName.contains(partName)) {
                        matchingMartyrs.addList(martyr);
                    }
                    current = current.getNext(); // moves to the next location
                }
            }
        }
        return matchingMartyrs; // returns the list
    }
    public MartyrList getAllMartyrs() { // a method that returns all the martyrs in the districtList
        MartyrList allMartyrs = new MartyrList();
        Node currentDistrict = front;
        while (currentDistrict != null) {
            LocationNode currentLocation = currentDistrict.getHead().getFront();
            while (currentLocation != null) {
                MartyrNode currentMartyr = currentLocation.getHead().getFront();
                while (currentMartyr != null) {
                    Martyr martyr = (Martyr) currentMartyr.getElement();
                    allMartyrs.addList(martyr);
                    currentMartyr = currentMartyr.getNext(); // moves to the next martyr
                }
                currentLocation = currentLocation.getNext();// moves to the next location
            }
            currentDistrict = currentDistrict.getNext();// moves to the next district
        }

        return allMartyrs;
    }


    public MartyrList getMartyrsByLocationAndDistrict(String location, String district, String partName) {
        MartyrList matchingMartyrs = new MartyrList();
        int index = lastIndexOf(new District(district));
        LocationNode locationNode = getDistrictNode(index).getHead().getNode(getDistrictNode(index).getHead().lastIndexOf(new Location(location)));
        if (locationNode != null) {
            MartyrNode current = locationNode.getHead().getFront();
            while (current != null) {
                Martyr martyr = (Martyr) current.getElement();
                String fullName = martyr.getName().toLowerCase();
                if (fullName.startsWith(partName) || fullName.contains(partName)) { // if contains or starts adds to the list
                    matchingMartyrs.addList(martyr);
                }
                current = current.getNext(); // moves the current to the next martyr
            }
        }
        return matchingMartyrs; // returns list
    }
    public int getTotalMartyrsWithDate(String date) {
        int totalCount = 0; // counter
        Node currentDistrict = front; // current Node assigned to front
        while (currentDistrict != null) { // while not equal to null
            LocationNode currentLocation = currentDistrict.getHead().getFront(); // location node assigned to the front of location list
            while (currentLocation != null) { // while location node not equal to null
                MartyrNode currentMartyr = currentLocation.getHead().getFront(); // assigns martyr node to front of martyr list
                while (currentMartyr != null) { // while martyr node not equal to null
                    String martyrDate = ((Martyr) currentMartyr.getElement()).getDate(); // gets the date of the current martyr
                    if (date.equals(martyrDate)) { // if date is equal to martyr date increment counter
                        totalCount++; // counter increments
                    }
                    currentMartyr = currentMartyr.getNext(); // moves to the next martyr
                }
                currentLocation = currentLocation.getNext(); // moves to the next location
            }
            currentDistrict = currentDistrict.getNext(); // moves to the next district
        }

        return totalCount; // returns total count
    }
    public void updateMartyrDistrict(District district,String districtName){ // a method that goes through all the martyrs and updates the distincter
        int index = lastIndexOf(district);  // gets the index of the object
        Node current = getDistrictNode(index); // gets the index of the node
        LocationNode currentLocation = current.getHead().getFront();
        while(currentLocation!=null){
            MartyrNode currentMartyr = currentLocation.getHead().getFront();
            while(currentMartyr!=null){
                Martyr martyr = (Martyr) currentMartyr.getElement();
                martyr.setDistrict(districtName); // sets the new District
                currentMartyr = currentMartyr.getNext(); // moves to the next martyr until null
            }
            currentLocation=currentLocation.getNext(); // moves to the next location until null
        }
    }

}