package com.example.finalprojectever;

class AVLTreeNode {
    private Martyr element; //store data
    private AVLTreeNode left; // left child
    private AVLTreeNode right; //right child
    private int height; //Height

    public AVLTreeNode(Martyr element){
        this(element, null, null);
    }
    public AVLTreeNode(Martyr element, AVLTreeNode left, AVLTreeNode right)
    {
        this.element=element;
        this.left=left;
        this.right=right;
        this.height=0;
    }

    public AVLTreeNode getLeft() {
        return left;
    }

    public AVLTreeNode getRight() {
        return right;
    }

    public void setMartyr(Martyr element) {
        this.element = element;
    }

    public Martyr getMartyr() {
        return element;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setLeft(AVLTreeNode left) {
        this.left = left;
    }

    public void setRight(AVLTreeNode right) {
        this.right = right;
    }
}
