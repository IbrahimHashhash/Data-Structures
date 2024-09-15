package com.example.finalprojectever;

import java.time.LocalDate;

class HashEntry { // a hash entry class that has status and value and each hashEntry object references to an AVL tree
    private LocalDate value;
    private char status; // 'E' for Empty, 'F' for Full, 'D' for Deleted
    private final AVLTree tree;

    public HashEntry(Martyr martyr) { // constructor that takes Martyr
        this.value = Manager.localDate(martyr.getDate());
        this.status = 'E';
        this.tree = new AVLTree();
    }
    public HashEntry(LocalDate localDate) { // constructor that takes date
        this.value = localDate;
        this.status = 'E';
        this.tree = new AVLTree();
    }

    public AVLTree getTree() {
        return tree;
    }

    public void insertMartyr(Martyr martyr) {
        if(!this.tree.contains(martyr.getName())) {
            this.tree.insert(martyr);
        }
    }
    // getters and setters
    public void setStatus(char status) {
        this.status = status;
    }

    public char getStatus() {
        return status;
    }

    public LocalDate getValue() {
        return value;
    }

    public void setValue(LocalDate value) {
        this.value = value;
    }
}

