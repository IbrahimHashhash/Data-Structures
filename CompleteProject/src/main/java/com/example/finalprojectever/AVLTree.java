package com.example.finalprojectever;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AVLTree {
    private AVLTreeNode root;
    private int averageAge,female,male,total,size;
    private final DLMaxHeap districtH = new DLMaxHeap();
    private final DLMaxHeap locationH = new DLMaxHeap();


    public AVLTree() {
        root = null;
    }

    public void insert(Martyr martyr) {
        root = insert(martyr, root);
    }

    private AVLTreeNode insert(Martyr martyr, AVLTreeNode current) {
        if (current == null) {
            if (martyr.getGender() == 'M') {
                male++;
            } else if(martyr.getGender()=='F'){
                female++;
            }
            if (martyr.getAge() != -1)
                total++;

            averageAge += martyr.getAge();
            if (!Style.getDistrictList().contains(martyr.getDistrict())) {
                Style.getDistrictList().add(martyr.getDistrict());
            }

            Style.getMartyrObList().add(martyr);
            size++;
            districtH.insert(martyr.getDistrict());
            locationH.insert(martyr.getLocation());


            return new AVLTreeNode(martyr);
        } else if (martyr.compareTo(current.getMartyr()) < 0)
            current.setLeft(insert(martyr, current.getLeft()));
        else if (martyr.compareTo(current.getMartyr()) > 0)
            current.setRight(insert(martyr, current.getRight()));

        return balancing(current);
    }

    public void delete(Martyr martyr) {
        root = delete(martyr, root);
    }

    private AVLTreeNode delete(Martyr martyr, AVLTreeNode current) {
        if (current == null)
            return null;

        if (martyr.getName().compareToIgnoreCase(current.getMartyr().getName()) < 0)
            current.setLeft(delete(martyr, current.getLeft()));
        else if (martyr.getName().compareToIgnoreCase(current.getMartyr().getName()) > 0)
            current.setRight(delete(martyr, current.getRight()));
        else {
            if (current.getLeft() == null || current.getRight() == null) {
                Martyr martyrs = current.getMartyr();
                if (martyrs.getGender() == 'M') {
                    if(male!=0) {
                        male--;
                    }
                } else {
                    if(female!=0) {
                        female--;
                    }
                }
                size--;

                averageAge -= martyrs.getAge();
                if (martyrs.getAge() != -2 && total!=0) {
                    total--;
                }
                locationH.decrementEntry(martyr.getLocation());
                districtH.decrementEntry(martyr.getDistrict());
                current = (current.getLeft() != null) ? current.getLeft() : current.getRight();
            } else {
                AVLTreeNode successor = findMin(current.getRight());
                current.setMartyr(successor.getMartyr());
                current.setRight(delete(successor.getMartyr(), current.getRight()));
            }
        }

        return balancing(current);
    }

    private AVLTreeNode findMin(AVLTreeNode current) {
        if (current == null)
            return null;
        else if (current.getLeft() == null)
            return current;
        else
            return findMin(current.getLeft());
    }
    public int getHeight() {
        return getHeight(root);
    }

    // Recursive helper method to get the height of a subtree
    private int getHeight(AVLTreeNode node) {
        if (node == null) {
            return -1; // Base case: empty tree has height -1
        }
        int leftHeight = getHeight(node.getLeft());
        int rightHeight = getHeight(node.getRight());
        return Math.max(leftHeight, rightHeight) + 1; // Height of the current node is max of left and right subtrees + 1
    }

    // Method to get the size (number of nodes) of the AVL tree
    public int getSize() {

        return size;
    }

    // Recursive helper method to get the size of a subtree


    private int height(AVLTreeNode node) {
        return (node == null) ? -1 : node.getHeight();
    }

    private void updateHeight(AVLTreeNode node) {
        node.setHeight(Math.max(height(node.getLeft()), height(node.getRight())) + 1);
    }

    private AVLTreeNode balancing(AVLTreeNode node) {
        if (node == null)
            return null;

        updateHeight(node);

        int balanceFactor = height(node.getLeft()) - height(node.getRight());

        if (balanceFactor > 1) {
            if (height(node.getLeft().getLeft()) >= height(node.getLeft().getRight()))
                return rotateWithLeftChild(node);
            else
                return doubleWithLeftChild(node);
        } else if (balanceFactor < -1) {
            if (height(node.getRight().getRight()) >= height(node.getRight().getLeft()))
                return rotateWithRightChild(node);
            else
                return doubleWithRightChild(node);
        }

        return node;
    }

    private AVLTreeNode rotateWithLeftChild(AVLTreeNode k2) {
        AVLTreeNode k1 = k2.getLeft();
        k2.setLeft(k1.getRight());
        k1.setRight(k2);

        updateHeight(k2);
        updateHeight(k1);

        return k1;
    }

    private AVLTreeNode rotateWithRightChild(AVLTreeNode k1) {
        AVLTreeNode k2 = k1.getRight();
        k1.setRight(k2.getLeft());
        k2.setLeft(k1);

        updateHeight(k1);
        updateHeight(k2);

        return k2;
    }
    public boolean contains(String martyr) {
        return contains(martyr, root);
    }

    // Private helper method for contains
    private boolean contains(String martyr, AVLTreeNode current) {
        if (current == null) {
            return false; // Base case: not found
        }

        int comparison = martyr.compareToIgnoreCase(current.getMartyr().getName());

        if (comparison < 0) {
            return contains(martyr, current.getLeft()); // Search in left subtree
        } else if (comparison > 0) {
            return contains(martyr, current.getRight()); // Search in right subtree
        } else {
            return true; // Martyr found
        }
    }

    public Martyr find(String martyr) {
        return find(martyr, root);
    }

    // Private helper method for contains
    private Martyr find(String martyr, AVLTreeNode current) {
        if (current == null) {
            return null; // Base case: not found
        }

        int comparison = martyr.compareToIgnoreCase(current.getMartyr().getName());

        if (comparison < 0) {
            return find(martyr, current.getLeft()); // Search in left subtree
        } else if (comparison > 0) {
            return find(martyr, current.getRight()); // Search in right subtree
        } else {
            return current.getMartyr(); // Martyr found
        }
    }

    private AVLTreeNode doubleWithLeftChild(AVLTreeNode k3) {
        k3.setLeft(rotateWithRightChild(k3.getLeft()));
        return rotateWithLeftChild(k3);
    }

    private AVLTreeNode doubleWithRightChild(AVLTreeNode k3) {
        k3.setRight(rotateWithLeftChild(k3.getRight()));
        return rotateWithRightChild(k3);
    }

    public int getTotal() {
        return total;
    }

    public int getFemale() {
        return female;
    }

    public int getMale() {
        return male;
    }

    public int getAverageAge() {
        if (total != 0) {
            return averageAge / total;
        } else {
            return 0;
        }
    }

    public ObservableList<String> fillLocation(String district, ObservableList<String> locations) {
        fillLocation(root, locations, district);
        return locations;
    }

    private void fillLocation(AVLTreeNode root, ObservableList<String> list, String district) {
        if (root != null) {
            fillLocation(root.getLeft(), list, district);
            if (!list.contains(root.getMartyr().getLocation()) && root.getMartyr().getDistrict().equalsIgnoreCase(district)) {
                list.add(root.getMartyr().getLocation());
            }
            fillLocation(root.getRight(), list, district);
        }
    }
    public String findMaxDistrict(){
        return districtH.getRoot();
    }
    public String findMaxLocation() {
        return locationH.getRoot();
    }



    public ObservableList<Martyr> collectAllMartyrs(ObservableList<Martyr> martyrs){
        collectAllMartyrs(root,martyrs);
        return martyrs;
    }
    public void collectAllMartyrs(AVLTreeNode current,ObservableList<Martyr> martyrs){
        if(current!=null){
        collectAllMartyrs(current.getLeft(),martyrs);
        martyrs.add(current.getMartyr());
        collectAllMartyrs(current.getRight(),martyrs);
        }
    }


    public ObservableList<Martyr> levelOrder() {
        ObservableList<Martyr> martyrs = FXCollections.observableArrayList();
        levelOrder(root,martyrs); // creates a stack list and assigns it to helper method that also returns a stacklist
        return martyrs;
    }

    private void levelOrder(AVLTreeNode root,ObservableList<Martyr> martyrs) {
        if (root == null)
            return; // Return empty stack if root is null

        QueueList queue = new QueueList(); // Queue qeueing and dequeuing the elements into the result stack for level by level
        queue.enQueue(root); // Insert the root into the queue

        while (!queue.isEmpty()) { // while queue is not empty
            int sizeOfLevel = queue.size(); // gets the size of the queue, so it iterates by queue size

            for (int i = 0; i < sizeOfLevel; i++) { // i < queueSize
                AVLTreeNode current = (AVLTreeNode) queue.deQueue(); //
                martyrs.add(current.getMartyr()); // Directly push nodes onto the result stack
                if (current.getRight() != null) // if the current's right child is not null, then enQeue
                    queue.enQueue(current.getRight());

                if (current.getLeft() != null) // if the current's left child is not null, then enQueue
                    queue.enQueue(current.getLeft());
            } // now the size depends on the enqueued element

        }
    }

    public void minHeapSort(MartyrTableView martyrTableView){
        HeapSort heap = new HeapSort();
        traverseHeap(root,heap);
        heap.print(martyrTableView);
    }
    public void traverseHeap(AVLTreeNode current, HeapSort heapSort) {
        if (current != null) {
            traverseHeap(current.getLeft(), heapSort);
            Martyr martyr = current.getMartyr();
            if (martyr != null) { // Add null check
                heapSort.insert(martyr);
            }
            traverseHeap(current.getRight(), heapSort);
        }
    }
    public void searchAll(ObservableList<Martyr> martyrs,String partName){
        searchMartyr(root,martyrs,partName.trim());
    }
    public ObservableList<Martyr> searchSpecificHash(String partName){
        ObservableList<Martyr> martyrs = FXCollections.observableArrayList();
        searchMartyr(root,martyrs,partName.trim());
        return martyrs;
    }
    private void searchMartyr(AVLTreeNode current, ObservableList<Martyr> martyrs, String partName) {
        if (current != null) {
            searchMartyr(current.getLeft(), martyrs, partName);
            Martyr martyr = current.getMartyr();
            String fullName = martyr.getName().toLowerCase(); // Convert to lowercase for case-insensitive comparison
            partName = partName.toLowerCase(); // Convert to lowercase for case-insensitive comparison
            if (fullName.startsWith(partName) || fullName.contains(" " + partName) || fullName.endsWith(" " + partName) || fullName.contains(partName)) {
                martyrs.add(martyr);
            }
            searchMartyr(current.getRight(), martyrs, partName);
        }
    }
    public boolean isEmpty(){
        return root == null;
    }
    public void decrementF(){
        female--;
    }
    public void decrementM(){
        male--;
    }
    public void incrementF(){
        female++;
    }
    public void incrementM(){
        male++;
    }
    public void updateAge(int newValue, int oldValue){
        averageAge-=oldValue;
        averageAge+=newValue;
    }
    public void updateDate(String newDate){
        updateDate(root,newDate);
    }
    private void updateDate(AVLTreeNode current, String newDate){
       if(current!=null){
           updateDate(current.getLeft(),newDate);
           current.getMartyr().setDate(newDate);
           updateDate(current.getRight(),newDate);
       }
    }
    public String maxAge(){
        if(root == null){
            return "Unknown";
        }
        return maxAge(root).getMartyr().getName();
    }

    private AVLTreeNode maxAge(AVLTreeNode node){ // a method to find the oldest martyr
        if(node==null){
            return null;
        }
        AVLTreeNode max = node; // creates a date node
        if(node.getLeft()!=null){ // checks if the node left is not equal to null
            AVLTreeNode left = maxAge(node.getLeft()); // creates a left node then assigns to the recursive call of the left child of max node
            if(left.getMartyr().getAge()> max.getMartyr().getAge()){ // if the left.getHead().size > max.getHead().size();
                max = left; // max = left
            }
        }
        if(node.getRight()!=null){ // if node right is not equal to null
            AVLTreeNode right = maxAge(node.getRight()); // creates a right node and assigns to the right child of node
            if(right.getMartyr().getAge()> max.getMartyr().getAge()){ // compares it
                max = right;
            }
        }

        return max; // returns max node
    }
    public String minAge(){
        if(root == null){
            return "Unknown";
        }
        return minAge(root).getMartyr().getName();
    }

    private AVLTreeNode minAge(AVLTreeNode node){ // a method to find the youngest martyr
        if(node==null){
            return null;
        }
        AVLTreeNode min = node; // creates a date node
        if(node.getLeft()!=null){ // checks if the node left is not equal to null
            AVLTreeNode left = minAge(node.getLeft()); // creates a left node then assigns to the recursive call of the left child of max node
            if(left.getMartyr().getAge()< min.getMartyr().getAge()){ // if the left.getHead().size > max.getHead().size();
                min = left; // max = left
            }
        }
        if(node.getRight()!=null){ // if node right is not equal to null
            AVLTreeNode right = minAge(node.getRight()); // creates a right node and assigns to the right child of node
            if(right.getMartyr().getAge()< min.getMartyr().getAge()){ // compares it
                min = right;
            }
        }

        return min;
    }


    public DLMaxHeap getDistrictH() {
        return districtH;
    }

    public DLMaxHeap getLocationH() {
        return locationH;
    }
}