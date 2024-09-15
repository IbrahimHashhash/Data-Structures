package com.example.phase2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

class DNode {
    private DNode left, right;
    private final BSLocationTree head;
    private String element;

    public DNode(String element) {
        this(element, null, null);
    }

    public DNode(String element, DNode left, DNode right) {
        this.element = element;
        this.left = left;
        this.right = right;
        this.head = new BSLocationTree();
    }

    public BSLocationTree getLocation() {
        return head;
    }

    public void insertLocation(String element) {
        if (!this.head.contains(element)) {
            this.head.insert(element);
        }
    }


    public DNode getLeft() {
        return left;
    }

    public void setLeft(DNode left) {
        this.left = left;
    }

    public DNode getRight() {
        return right;
    }

    public void setRight(DNode right) {
        this.right = right;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }
    public int getMartyrCounter(DNode district) {
        if (district != null) {
            // Start by resetting the counter
            int martyrCounter = 0;
            // Traverse through each location in the district
            martyrCounter = countMartyrsInLocations(district.getLocation().getRoot(), martyrCounter);
            return martyrCounter;
        }
        return 0; // Return 0 if district is null
    }

    private int countMartyrsInLocations(LNode location, int martyrCounter) {
        if (location != null) {
            // For each location, traverse through each date
            martyrCounter = countMartyrsInDates(location.getDate().getRoot(), martyrCounter);
            // Recursively traverse to the next location
            martyrCounter = countMartyrsInLocations(location.getRight(), martyrCounter);
            martyrCounter = countMartyrsInLocations(location.getLeft(), martyrCounter);
        }
        return martyrCounter;
    }

    private int countMartyrsInDates(BSDateNode date, int martyrCounter) {
        if (date != null) {
            // For each date, traverse through each martyr
            martyrCounter = countMartyrsInMartyrs(date.getHead().getFront(), martyrCounter);
            // Recursively traverse to the next date
            martyrCounter = countMartyrsInDates(date.getRight(), martyrCounter);
            martyrCounter = countMartyrsInDates(date.getLeft(), martyrCounter);
        }
        return martyrCounter;
    }

    private int countMartyrsInMartyrs(Node martyr, int martyrCounter) {
        while (martyr != null) {
            // Increment the counter for each martyr encountered
            martyrCounter++;
            // Move to the next martyr
            martyr = martyr.getNext();
        }
        return martyrCounter;
    }

}

public class BSDistrictTree {
    private DNode root;
    private int martyrCounter =0;

    public BSDistrictTree() {
        this.root = null;
    }
    private final List districts = new ArrayList();


    public void insert(String element) {
        root = insert(element, root);
    }

   private DNode insert(String element, DNode current) {
        DNode dNode = new DNode(element);
        if (current == null) {
            Style.getDistrictList().add(dNode.getElement());
            return dNode; // Create a new node
        }
        else {
            // Perform case-insensitive comparison
            int cmp = element.compareToIgnoreCase(current.getElement());
            if (cmp < 0) {
                current.setLeft(insert(element, current.getLeft()));
            }
            else if (cmp > 0) {
                current.setRight(insert(element, current.getRight()));
            }
        }
        return current;
    }
    public DNode find(String e) {
        if(e!=null) {
            return find(e, root);
        }
        return null;
    }

    private DNode find(String e, DNode current) {

        if (current == null)
            return null; // Not found, empty tree.

        int cmp = e.compareToIgnoreCase(current.getElement());
        if (cmp < 0)
            return find(e, current.getLeft()); // Search left subtree
        else if (cmp > 0)
            return find(e, current.getRight()); // Search right subtree
        return current; // Found.
    }


    public boolean contains(String e) {
        return contains(e, root);
    }

    private boolean contains(String e, DNode current) {
        if (current == null)
            return false; // Not found, empty tree.
        else {
            // Perform case-insensitive comparison
            int cmp = e.compareToIgnoreCase(current.getElement());
            if (cmp < 0)
                return contains(e, current.getLeft()); // Search left subtree
            else if (cmp > 0)
                return contains(e, current.getRight()); // Search right subtree
            return true; // Found.
        }
    }

    public void remove(String e) {
        root = remove(e, root);
        districts.remove(e);
        Style.getDistrictList().remove(e);
    }

    private DNode remove(String e, DNode current) {
        if (current == null)
            return null; // Item not found, empty tree.

        int cmp = e.compareToIgnoreCase(current.getElement());
        if (cmp < 0)
            current.setLeft(remove(e, current.getLeft()));
        else if (cmp > 0)
            current.setRight(remove(e, current.getRight()));
        else { // Found element to be deleted
            if (current.getLeft() != null && current.getRight() != null) { // Two children
                // Replace with smallest in right subtree
                current.setElement(findMin(current.getRight()).getElement());
                current.setRight(remove(current.getElement(), current.getRight()));
            } else { // One or zero child
                current = (current.getLeft() != null) ? current.getLeft() : current.getRight();
            }
        }
        return current;
    }
    public List<LNode> loadLocations(DNode district) {
        List<LNode> locations = new ArrayList<>();
        if (district != null) {
            loadLocationsLevelOrder(district.getLocation().getRoot(), locations);
        }
        return locations;
    }

    private void loadLocationsLevelOrder(LNode root, List<LNode> locations) {
        if (root == null)
            return;

        QueueList queue = new QueueList();
        queue.enQueue(root);

        while (!queue.isEmpty()) {
            LNode current = (LNode) queue.deQueue();
            if (current == null)
                continue; // Skip processing null nodes

            locations.add(current);

            if (current.getLeft() != null)

            queue.enQueue(current.getLeft());

            if (current.getRight() != null)

            queue.enQueue(current.getRight());
        }
    }

    public ObservableList<String> collectLocations(String districtName) {
        DNode districtNode = find(districtName);
        ObservableList<String> locations = FXCollections.observableArrayList();
        if (districtNode != null) {
            collectLocationsHelper(districtNode.getLocation().getRoot(), locations);
        }

        return locations;
    }

    // Helper method to recursively collect locations
    private void collectLocationsHelper(LNode current, ObservableList<String> locations) {
        if (current != null) {
            collectLocationsHelper(current.getLeft(), locations);
            locations.add(current.getElement());
            collectLocationsHelper(current.getRight(), locations);
        }
    }
    public List<Martyr> collectAllMartyrs(){
        ObservableList<Martyr> martyrs = FXCollections.observableArrayList();
        if(root!=null){
            collectAllMartyrs(root, martyrs);
        }
        return martyrs;
    }

    public List<Martyr> collectMartyrsByDistrict(DNode districtNode) {
        ObservableList<Martyr> martyrs = FXCollections.observableArrayList();
        if (districtNode != null) {
            collectAllMartyrs(districtNode.getLocation().getRoot(), martyrs); // Traverse the location tree and collect martyrs
        }
        return martyrs;
    }



    private void collectAllMartyrs(DNode current, ObservableList<Martyr> martyrs){
        if(current!=null){
            collectAllMartyrs(current.getRight(), martyrs);
            collectAllMartyrs(current.getLocation().getRoot(), martyrs);
            collectAllMartyrs(current.getLeft(), martyrs);
        }
    }

    private void collectAllMartyrs(LNode current, ObservableList<Martyr> martyrs){
        if(current!=null){
            collectAllMartyrs(current.getRight(), martyrs);
            collectAllMartyrs(current.getDate().getRoot(), martyrs);
            collectAllMartyrs(current.getLeft(), martyrs);
        }
    }

    private void collectAllMartyrs(BSDateNode current, ObservableList<Martyr> martyrs){
        if(current!=null){
            collectAllMartyrs(current.getRight(), martyrs);
            collectAllMartyrs(current.getHead().getFront(), martyrs);
            collectAllMartyrs(current.getLeft(), martyrs);
        }
    }
    private void collectAllMartyrs(Node current, ObservableList<Martyr> martyrs){
        while (current!=null){
            martyrCounter++;
            martyrs.add((Martyr)current.getElement());
            current = current.getNext();
        }
    }


    // Helper method to recursively collect locations
    private DNode findMin(DNode current) {
        if (current == null)
            return null;
        else if (current.getLeft() == null)
            return current;
        else
            return findMin(current.getLeft()); // Keep going to the left
    }

    private DNode findMax(DNode current) {
        if (current == null)
            return null;
        else if (current.getRight() == null)
            return current;
        else
            return findMax(current.getRight()); // Keep going to the right
    }
    public StackList inOrder() {
        StackList stack = new StackList();
        inOrder(root,stack);
        return reverseStack(stack);
    }
    public StackList reverseStack(StackList result){
        StackList stackList = new StackList();
        while(!result.isEmpty()){
            stackList.push(result.pop());
        }
        return stackList;
    }

    private void inOrder(DNode r,StackList stack) {
        if (r != null) {
            inOrder(r.getLeft(),stack);
            stack.push(r);
            inOrder(r.getRight(),stack);
        }
    }



    public void clear() {
        root = null; // Set the root to null to clear the tree
        Style.getMartyrObList().clear();
        Style.getDistrictList().clear();
    }
    public boolean isEmpty() {
        return root == null;
    }
    //--------------------------------------------------Searching
    public List<Martyr> searchMartyr(String partName){
        ObservableList<Martyr> martyrs = FXCollections.observableArrayList();
        if(root!=null){
            searchMartyr(root, martyrs,partName);
        }
        return martyrs;
    }

    private void searchMartyr(DNode current, ObservableList<Martyr> martyrs,String partName){ // takes a district node, list and string partName to iterate through all districts in order
        if(current!=null){ // if current is not equal to null
            searchMartyr(current.getLeft(), martyrs,partName ); // recursive call
            searchMartyr(current.getLocation().getRoot(), martyrs,partName); // goes to the location tree from the current district node
            searchMartyr(current.getRight(), martyrs,partName); // recursive call
        }
    }
    public ObservableList<Martyr> searchMartyr(String districtName, String partName) {
        ObservableList<Martyr> martyrs = FXCollections.observableArrayList();
        DNode districtNode = find(districtName); // Find the district node
        if (districtNode != null) {
            searchMartyr(districtNode.getLocation().getRoot(), martyrs,partName); // Traverse the location tree and collect martyrs
        }
        return martyrs;
    }

    private void searchMartyr(LNode current, ObservableList<Martyr> martyrs,String partName){
        if(current!=null){ // if current is not equal to null
            searchMartyr(current.getLeft(), martyrs,partName); // recursive call left
            searchMartyr(current.getDate().getRoot(), martyrs,partName); // goes to date tree through the current location node
            searchMartyr(current.getRight(), martyrs,partName); // recursive call right
        }
    }
    private void searchMartyr(BSDateNode current, ObservableList<Martyr> martyrs,String partName){
        if(current!=null){
            searchMartyr(current.getLeft(), martyrs,partName); // recursive call date
            searchMartyr(current.getHead().getFront(), martyrs,partName); // goes to the martyr list through the date node
            searchMartyr(current.getRight(), martyrs,partName); // g
        }
    }
    private void searchMartyr(Node current, ObservableList<Martyr> martyrs,String partName){
        while (current != null) {
            Martyr martyr = (Martyr) current.getElement(); // martyr object
            String fullName = martyr.getName().toLowerCase(); // the full name of the martyr
            if (fullName.startsWith(partName) || fullName.contains(partName) || fullName.equalsIgnoreCase(partName)) { // checks if full is starts with or contains part name
                martyrs.add(martyr); // if yes, then it adds it to the list
            }
            current = current.getNext(); // moves to the next martyr
        }
    }
//------------------------------------------------------------------------Fixin
public LNode findLocationNode(String lNode) {
    return findLocationNode(lNode, root);
}

    private LNode findLocationNode(String lNode, DNode dNode) {
        if (dNode != null) {
            // Search for the location node recursively in the current DNode
            LNode result = findLocationNode(lNode, dNode.getLocation().getRoot());
            if (result != null) {
                return result; // Return if found
            }

            // If not found, search in the left subtree
            result = findLocationNode(lNode, dNode.getLeft());
            if (result != null) {
                return result; // Return if found
            }

            // If not found in the left subtree, search in the right subtree
            return findLocationNode(lNode, dNode.getRight());
        }
        return null; // Return null if the DNode is null
    }

    private LNode findLocationNode(String lString, LNode lNode) {
        if (lNode != null) {
            if (lNode.getElement().equals(lString)) {
                return lNode; // Return the node if found
            }

            // Search in the left subtree
            LNode leftResult = findLocationNode(lString, lNode.getLeft());
            if (leftResult != null) {
                return leftResult; // Return if found in the left subtree
            }

            // If not found in the left subtree, search in the right subtree
            return findLocationNode(lString, lNode.getRight());
        }
        return null; // Return null if the LNode is null
    }

    public ObservableList<Martyr> collectMartyrsByDate(LocalDate date, String partName) {
        ObservableList<Martyr> martyrs = FXCollections.observableArrayList();
        if (date != null) {
            collectMartyrsByDate(date, root, partName, martyrs);
        }
        return martyrs;
    }

    private void collectMartyrsByDate(LocalDate date, DNode dNode, String partName, ObservableList<Martyr> martyrs) {
        if (dNode != null) {
            collectMartyrsByDate(date, dNode.getRight(), partName, martyrs);
            collectMartyrsByDate(date, dNode.getLocation().getRoot(), partName, martyrs);
            collectMartyrsByDate(date, dNode.getLeft(), partName, martyrs);
        }
    }

    private void collectMartyrsByDate(LocalDate date, LNode lNode, String partName, ObservableList<Martyr> martyrs) {
        if (lNode != null) {
            collectMartyrsByDate(date, lNode.getRight(), partName, martyrs);
            collectMartyrsByDate(date, lNode.getDate().getRoot(), partName, martyrs);
            collectMartyrsByDate(date, lNode.getLeft(), partName, martyrs);
        }
    }

    private void collectMartyrsByDate(LocalDate date, BSDateNode dateNode, String partName, ObservableList<Martyr> martyrs) {
        if (dateNode != null) {
            if (dateNode.getElement().equals(date)) {
                searchMartyr(dateNode.getHead().getFront(), martyrs, partName);
                return;
            }
            collectMartyrsByDate(date, dateNode.getLeft(), partName, martyrs);
            collectMartyrsByDate(date, dateNode.getRight(), partName, martyrs);
        }
    }

    public int totalNumberOfMartyrs(DNode node){
        martyrCounter =0;
        collectMartyrsByDistrict(node);
        return martyrCounter;
    }
    public DNode getRoot(){
        return root;
    }
}
