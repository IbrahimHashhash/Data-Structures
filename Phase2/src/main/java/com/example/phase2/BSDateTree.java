package com.example.phase2;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.*;

class BSDateNode {
    /*
    A date tree node, it has left , right just like a binary search tree
     */
    private BSDateNode left,right;
    private MartyrList head; // it contains a martyr list head
    private Object element; // it has an element object

    public BSDateNode(Object element){
        this(element,null,null);
    } // constructor that takes element
    public BSDateNode(Object element, BSDateNode left, BSDateNode right){ // contructor that takes 3 arguments
        this.element=element;
        this.left=left;
        this.right=right;
        this.head=new MartyrList();
    }

    public MartyrList getHead() {
        return head;
    } // a method to return the martyrLost

    public void addMartyr(Object element){ // a method to insert into the martyr list
        Martyr martyr = (Martyr) element;
        if(!head.contains(martyr)) {
            if (this.head.isEmpty() || ((Martyr) this.head.getFront().getElement()).getAge() >= martyr.getAge()) {
                // If the list is empty or the martyr's age is less than or equal to the age of the first martyr in the list,
                // add the martyr to the beginning of the list
                this.head.addFirst(martyr);
            } else {
                // Iterate through the list to find the correct position to insert the martyr
                MartyrList martyrList = this.head;
                Node current = martyrList.getFront();
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
    public BSDateNode getLeft() {
        return left;
    } // get left method

    public void setLeft(BSDateNode left) {
        this.left = left;
    } // setter method

    public BSDateNode getRight() {
        return right;
    } // a method to get the right method

    public void setRight(BSDateNode right) {
        this.right = right;
    } // setter method for right

    public Object getElement() {
        return element;
    } // get element
    public void setElement(Object element) {
        this.element = element; // set the element
    }
    public ObservableList<Martyr> sortedMartyrs(BSDateNode dateNode) { // sorting the martyrs by name
        ObservableList<Martyr> list = FXCollections.observableArrayList();
        searchMartyrs(dateNode.getHead().getFront(), list);
        Collections.sort(list); // collections sort for sorting the obseravableList
        return list;
    }

    public void searchMartyrs(Node martyr, ObservableList<Martyr> list) {
        while (martyr != null) {
            Martyr mar = (Martyr) martyr.getElement();
            list.add(mar); // Add martyr to the list
            martyr = martyr.getNext(); // Move to the next martyr
        }
    }

    public Martyr martyr(BSDateNode date,String name){

        return (Martyr) date.getHead().find(name);
    }

    public int averageAge(BSDateNode dateNode){
        int average = 0;
        average = searchMartyrs(dateNode.getHead().getFront(),average);
        return average;
    }
    public int searchMartyrs(Node martyr, int average) {
        int sum = 0;
        int count = 0; // Track the number of martyrs
        int noAge = 0;
        while (martyr != null) {
            Martyr mar = (Martyr) martyr.getElement();
            if(mar.getAge()==-1){
                noAge++;
            }
            sum += mar.getAge();
            count++; // Increment count for each martyr
            martyr = martyr.getNext(); // Move to the next martyr
        }
        if (count > 0) {
            average = sum / (count-noAge);
        }
        return average;
    }


    public String youngest(BSDateNode dateNode){
      return ((Martyr)dateNode.getHead().getFirst()).getName(); // returns the youngest martyr
    }
    public String oldest(BSDateNode dateNode){
        return ((Martyr)dateNode.getHead().getLast()).getName();
    } // retursn the oldest martyr



}
public class BSDateTree {
    /*
    A date tree class
     */
    private BSDateNode root;
    public BSDateTree() {
        this.root = null;
    }


    public void insert(Object element) {
        root = insert(element, root);
    }

    // Insert element function
    private BSDateNode insert(Object element, BSDateNode current) {
        if (current == null) {
            current = new BSDateNode(element); // create one node tree
        } else {
            Comparable<Object> comparableElement = (Comparable<Object>) element;
            Comparable<Object> currentElement = (Comparable<Object>) current.getElement();
            if (comparableElement.compareTo(currentElement) < 0)
                current.setLeft(insert(element, current.getLeft()));
            else
                current.setRight(insert(element, current.getRight()));
        }
        return current;
    }


    public boolean contains(Object e) {
        return contains(e, root);
    }

    public BSDateNode find(Object e) {
        return find(e, root);
    }
    private BSDateNode find(Object element, BSDateNode current) {
        if (current == null)
            return null;
        Comparable<Object> comparableElement = (Comparable<Object>) element;
        Comparable<Object> currentElement = (Comparable<Object>) current.getElement();
        if (comparableElement.compareTo(currentElement) < 0)
            return find(element, current.getLeft());
        else if (comparableElement.compareTo(currentElement) > 0)
            return find(element, current.getRight());
        else
            return current;
    }


    private boolean contains(Object e, BSDateNode current) {
        if (current == null)
            return false; // Not found, empty tree.
        else {
            Comparable<Object> comparableElement = (Comparable<Object>) e;
            Comparable<Object> currentElement = (Comparable<Object>) current.getElement();
            if (comparableElement.compareTo(currentElement) < 0)
                return contains(e, current.getLeft()); // Search left subtree
            else if (comparableElement.compareTo(currentElement) > 0)
                return contains(e, current.getRight()); // Search right subtree
            return true; // found.
        }
    }

    public BSDateNode findMin(){
        return findMin(root);
    }
    public BSDateNode findMax(){
        return findMax(root);
    }


    private BSDateNode findMin(BSDateNode current) {
        if (current == null)
            return null;
        else if (current.getLeft() == null)
            return current;
        else
            return findMin(current.getLeft()); // keep going to the left
    }
    private BSDateNode findMax(BSDateNode current) {
        if (current == null)
            return null;
        else if (current.getRight() == null)
            return current;
        else
            return findMax(current.getRight()); // keep going to the right
    }
    public void remove(Object element) {
        root = remove(element, root);
    }

    private BSDateNode remove(Object e, BSDateNode current) {
        if (current == null) return null; // Item not found, Empty tree
        Comparable<Object> comparableElement = (Comparable<Object>) e;
        Comparable<Object> currentElement = (Comparable<Object>) current.getElement();
        if (comparableElement.compareTo(currentElement) < 0)
            current.setLeft(remove(e, current.getLeft()));
        else if (comparableElement.compareTo(currentElement) > 0)
            current.setRight(remove(e, current.getRight()));
        else // found element to be deleted
            if (current.getLeft() != null && current.getRight() != null)// two children
            {
                /*Replace with smallest in right subtree */
                current.setElement(findMin(current.getRight()).getElement());
                current.setRight(remove(current.getElement(), current.getRight()));
            } else// one or zero child
                current = (current.getLeft() != null) ? current.getLeft() : current.getRight();
        return current;
    }
    public BSDateNode find(Comparable<Object> element){
        return find(element,root);
    }
    private BSDateNode find(Comparable<Object> element, BSDateNode current) {
        if (current == null)
            return null;
        int cmp = element.compareTo(current.getElement());
        if (cmp < 0)
            return find(element, current.getLeft());
        else if (cmp > 0)
            return find(element, current.getRight());
        else
            return current;
    }
    public boolean contains(Comparable<Object> e){
        return contains(e,root);

    }
    private boolean contains(Comparable<Object> e, BSDateNode current) {
        if (current == null)
            return false; // Not found, empty tree.
        int cmp = e.compareTo(current.getElement());
        if (cmp < 0)
            return contains(e, current.getLeft()); // Search left subtree
        else if (cmp > 0)
            return contains(e, current.getRight()); // Search right subtree
        return true; // found.
    }
    public void preOrder() {
        preOrder(root);
    }

    private void preOrder(BSDateNode root) {
        if (root != null) {
            System.out.print(root.getElement() + " ");
            preOrder(root.getLeft());
            preOrder(root.getRight());
        }
    }

    public void postOrder() {
        postOrder(root);
    }

    private void postOrder(BSDateNode root) {
        if (root != null) {
            postOrder(root.getLeft());
            postOrder(root.getRight());
            System.out.print(root.getElement() + " ");
        }
    }
    public BSDateNode getRoot() {
        return root;
    }

    public void searchMartyr(BSDateNode current,String partName){ // it takes a date node and part name
        ObservableList<Martyr> martyrs = FXCollections.observableArrayList();
        searchMartyr(current.getHead().getFront(),martyrs,partName);
    }
    public ObservableList<Martyr> collectMartyrsByDate(BSDateNode current){ // i takes a date nod e
        ObservableList<Martyr> martyrs = FXCollections.observableArrayList(); // creates a list
        collectAllMartyrs(current.getHead().getFront(),martyrs); // calls method to store the martyrs in the list
        return martyrs;
    }
    private void collectAllMartyrs(Node current, ObservableList<Martyr> martyrs){ // takes a linkedList node and an observablelist
        while (current!=null){ // while current is not equal to null
            martyrs.add((Martyr)current.getElement()); // adds martyrs to the list
            current = current.getNext();
        }
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

    private void inOrder(BSDateNode r,StackList stack) {
        if (r != null) {
            inOrder(r.getRight(),stack);
            stack.push(r);
            inOrder(r.getLeft(),stack);
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
    public BSDateNode maxMartyrsDate(){
        if(root == null){
            return null;
        }
        return maxMartyrsDate(root);
    }

    private BSDateNode maxMartyrsDate(BSDateNode node){ // a method to find the date with the maximum numbers of martyrs
        if(node==null){
            return null;
        }
        BSDateNode max = node; // creates a date node
        if(node.getLeft()!=null){ // checks if the node left is not equal to null
            BSDateNode left = maxMartyrsDate(node.getLeft()); // creates a left node then assigns to the recursive call of the left child of max node
            if(left.getHead().size()> max.getHead().size()){ // if the left.getHead().size > max.getHead().size();
                max = left; // max = left
            }
        }
        if(node.getRight()!=null){ // if node right is not equal to null
            BSDateNode right = maxMartyrsDate(node.getRight()); // creates a right node and assigns to the right child of node
            if(right.getHead().size()> max.getHead().size()){ // compares it
                max = right;
            }
        }

        return max; // returns max node
    }
    public boolean isEmpty() {
        return root == null;
    }
}
