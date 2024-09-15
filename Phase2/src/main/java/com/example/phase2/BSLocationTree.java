package com.example.phase2;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

class LNode {
    private LNode left, right;
    private final BSDateTree head;
    private String element;

    public LNode(String element) {
        this(element, null, null);
    }

    public LNode(String element, LNode left, LNode right) {
        this.element = element;
        this.left = left;
        this.right = right;
        this.head = new BSDateTree();
    }
    public ObservableList<Martyr> searchMartyr(LNode lNode,String partName){
        ObservableList<Martyr> martyrs = FXCollections.observableArrayList();
        if(lNode!=null) {
            searchMartyr(lNode.getDate().getRoot(), martyrs, partName);
        }
        return martyrs;

    }

    private void searchMartyr(BSDateNode current, ObservableList<Martyr> martyrs,String partName){
        if(current!=null){
            searchMartyr(current.getRight(), martyrs,partName);
            searchMartyr(current.getHead().getFront(), martyrs,partName);
            searchMartyr(current.getLeft(), martyrs,partName);
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



    public BSDateTree getDate() {
        return this.head;
    }

    public void insertDate(Object element) {
        if (!this.head.contains(element)) {
            this.head.insert(element);
        }
    }

    public LNode getLeft() {
        return left;
    }

    public void setLeft(LNode left) {
        this.left = left;
    }

    public LNode getRight() {
        return right;
    }

    public void setRight(LNode right) {
        this.right = right;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

}

public class BSLocationTree {
    private LNode root;

    public BSLocationTree() {
        this.root = null;
    }

    public void insert(String element) {
        root = insert(element, root);
    }

    private LNode insert(String element, LNode current) {
        if (current == null)
            current = new LNode(element);
        else {
            int compare = element.compareToIgnoreCase(current.getElement());
            if (compare < 0)
                current.setLeft(insert(element, current.getLeft()));
            else if (compare > 0)
                current.setRight(insert(element, current.getRight()));
        }
        return current;
    }

    public boolean contains(String e) {
        return contains(e, root);
    }

    private boolean contains(String e, LNode current) {
        if (current == null)
            return false; // Not found, empty tree.
        else {
            int cmp = e.compareToIgnoreCase(current.getElement());
            if (cmp < 0)
                return contains(e, current.getLeft()); // Search left subtree
            else if (cmp > 0)
                return contains(e, current.getRight()); // Search right subtree
            return true; // Found.
        }
    }

    public LNode find(String e) {
        return find(e, root);
    }

    private LNode find(String e, LNode current) {
        if (current == null)
            return null; // Not found, empty tree.
        else {
            // Perform case-insensitive comparison
            int cmp = e.compareToIgnoreCase(current.getElement());
            if (cmp < 0)
                return find(e, current.getLeft()); // Search left subtree
            else if (cmp > 0)
                return find(e, current.getRight()); // Search right subtree
            return current; // Found.
        }
    }
        public void remove(String e) {
        root = remove(e, root);
        Style.getDistrictList().remove(e);
    }
    private LNode findMin(LNode current) {
        if (current == null)
            return null;
        else if (current.getLeft() == null)
            return current;
        else
            return findMin(current.getLeft()); // Keep going to the left
    }

    private LNode remove(String e, LNode current) {
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


    public void preOrder() {
        // root -> left -> right
        preOrder(root);
    }

    private void preOrder(LNode root) {
        if (root != null) {
            System.out.print(root.getElement() + " ");
            preOrder(root.getLeft());
            preOrder(root.getRight());
        }
    }

    public void inOrder() {
        inOrder(root);
    }

    private void inOrder(LNode r) {
        if (r != null) {
            inOrder(r.getLeft());
            System.out.print(r.getElement() + " ");
            inOrder(r.getRight());
        }
    }

    public void postOrder() {
        postOrder(root);
    }

    private void postOrder(LNode root) {
        if (root != null) {
            postOrder(root.getLeft());
            postOrder(root.getRight());
            System.out.print(root.getElement() + " ");
        }
    }
    public ObservableList<Martyr> searchMartyr(LNode lNode,String partName){
        ObservableList<Martyr> martyrs = FXCollections.observableArrayList();
        if(lNode!=null) {
            searchMartyr(lNode.getDate().getRoot(), martyrs, partName);
        }
        return martyrs;

    }

    private void searchMartyr(BSDateNode current, ObservableList<Martyr> martyrs,String partName){
        if(current!=null){
            searchMartyr(current.getRight(), martyrs,partName);
            searchMartyr(current.getHead().getFront(), martyrs,partName);
            searchMartyr(current.getLeft(), martyrs,partName);
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
    // a method that returns a stack list of locations that are ordered by level
    public StackList levelOrder(){
       StackList stack =  levelOrder(root); // creates a stack list and assigns it to helper method that also returns a stacklist
       return stack;
    }
    private StackList levelOrder(LNode root) {
        StackList resultStack = new StackList(); // a stack that stores the dequeued elements
        if (root == null)
            return resultStack; // Return empty stack if root is null

        QueueList queue = new QueueList(); // Queue qeueing and dequeuing the elements into the result stack for level by level
        queue.enQueue(root); // Insert the root into the queue

        while (!queue.isEmpty()) { // while queue is not empty
            int sizeOfLevel = queue.size(); // gets the size of the queue, so it iterates by queue size

            for (int i = 0; i < sizeOfLevel; i++) { // i < queueSize
                LNode current = (LNode) queue.deQueue(); //
                resultStack.push(current); // Directly push nodes onto the result stack
                if (current.getRight() != null) // if the current's right child is not null, then enQeue
                    queue.enQueue(current.getRight());

                if (current.getLeft() != null) // if the current's left child is not null, then enQueue
                    queue.enQueue(current.getLeft());
            } // now the size depends on the enqueued elements
        }

        return reverseStack(resultStack); // Reverse the stack to get level-order
    }

    private StackList reverseStack(StackList stack) { // reevrses the stack
        StackList reversedStack = new StackList();
        while (!stack.isEmpty()) {
            reversedStack.push(stack.pop());
        }
        return reversedStack; // returns the reversed stack
    }

    public LNode getRoot() {
        return root;
    }

    public boolean isEmpty() {
        return root == null;
    }
}
