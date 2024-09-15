package com.example.finalprojectever;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDate;

public class HashMap { // a HashMap class that has size, and an array of HashEntry objects and methods to do the hash logic
    private int size; // size
    private HashEntry[] table; // array of hash entry objects
    private int count; // Number of elements in the hash table

    public HashMap() { // no-arg constructor
        this.size=11; // initial size is 11
        this.table = new HashEntry[size]; // creates the array and assigns the size into it
        this.count = 0; // counter is 0
    }

    private int hashFunction(LocalDate key) {
        // hash function
        return sumDate(key)% size;
    }

    private int quadraticFunction(int index, int place) { // quadraticFunction
        return (index + (int) Math.pow(place, 2)) % size;
    }

    public HashEntry findDate(LocalDate key) { // f
        int index = hashFunction(key);
        int place = 1;
        while (table[index] != null) {
            if (table[index].getValue().equals(key) && table[index].getStatus() != 'D') {
                return table[index];
            }
            index = quadraticFunction(index, place);
            place++;
        }
        return null;
    }

    private int nextPrimeNumber(int minNumber) {
        for (int i = minNumber; true; i++) {
            if (isPrime(i)) {
                return i;
            }
        }
    }

    static boolean isPrime(int num) {
        if(num<=1) {
            return false;
        }
        for(int i=2;i<=num/2;i++) {
            if((num%i)==0)
                return  false;
        }
        return true;
    }

    public int sumDate(LocalDate localDate){
        final int primeNum = 17;

        int year = localDate.getYear();
        int month = localDate.getMonthValue();
        int day = localDate.getDayOfMonth();

        int res = 1;
        res = primeNum * res + year;
        res = primeNum * res + month;
        res = primeNum * res + day;

        return res;
    }
    private void rehash() {
        int newSize = nextPrimeNumber(size * 2);
        HashEntry[] newTable = new HashEntry[newSize];
        for (HashEntry entry : table) {
            if (entry != null && entry.getStatus() == 'F') {
                int index = sumDate(entry.getValue()) % newSize;
                int place = 1;
                while (newTable[index] != null) {
                    index = (index + (int) Math.pow(place, 2)) % newSize;
                    place++;
                }
                newTable[index] = entry;
            }
        }
        size = newSize;
        table = newTable;

        System.out.println("New table size: " + size);
    }
    public void insert(LocalDate date) {
        int index = hashFunction(date);
        int place = 1;
        while (table[index] != null && table[index].getStatus() != 'E') {
            if(table[index].getValue().equals(date)){
                return;
            }
            index = quadraticFunction(index, place);
            place++;
        }
        table[index] = new HashEntry(date);
        table[index].setStatus('F'); // Set status to 'F' for Full

        count++;
        // Check if rehashing is required
        if (count > size / 2) {
            rehash();
        }
    }
    public void delete(LocalDate date) {
        int index = hashFunction(date);
        int place = 1;
        while (table[index] != null) {
            if (table[index].getValue().equals(date) && table[index].getStatus() != 'D') {
                table[index].setStatus('D'); // Mark the entry as deleted
                count--;
                return;
            }
            index = quadraticFunction(index, place);
            place++;
        }
        System.out.println("Date not found in the hash table.");
    }
    public void insert(Martyr martyr) {
        LocalDate key = Manager.localDate(martyr.getDate());
        int index = hashFunction(key);
        int place = 1;
        while (table[index] != null && table[index].getStatus() != 'E') {
            if(table[index].getValue().equals(key)){
                table[index].insertMartyr(martyr);
                return;
            }
            index = quadraticFunction(index, place);
            place++;
        }
        table[index] = new HashEntry(martyr);
        table[index].setStatus('F'); // Set status to 'F' for Full
        table[index].insertMartyr(martyr);
        count++;
        // Check if rehashing is required
        if (count > size / 2) {
            rehash();
        }
    }

    public StackList getList() {
        StackList list = new StackList();
        for (int i = size-1; i >=0; i--) {
            HashEntry entry = table[i];
            if (entry != null && entry.getStatus() == 'F') {
                list.push(table[i]);
            }
        }
        return list;
    }

    public void traverse(String district, ObservableList<String> list) {
        for (int i = 0; i < size; i++) {
            HashEntry entry = table[i];
            if (entry != null && entry.getStatus() == 'F') {
                entry.getTree().fillLocation(district,list);
            }
        }
    }
    public ObservableList<Martyr> collectAll() {
        ObservableList<Martyr> martyrs = FXCollections.observableArrayList();
        for (int i = 0; i < size; i++) {
            HashEntry entry = table[i];
            if (entry != null && entry.getStatus() == 'F') {
                table[i].getTree().collectAllMartyrs(martyrs);
            }
        }
        return martyrs;
    }
    public String print(boolean include) {
        StringBuilder stringBuilder = new StringBuilder();
        if(!include) {
            for (int i = 0; i < size; i++) {
                HashEntry entry = table[i];
                if (entry != null && entry.getStatus() == 'F') {
                    stringBuilder.append("\n\n      ").append(i).append(" -> ").append("[ ").append(entry.getValue()).append(" : ").append(entry.getStatus()).append(" : ").append(entry.getTree().getTotal()).append(" ]");
                    stringBuilder.append("\n\n   ---------------------------------");

                }
            }
        }else{
            for (int i = 0; i < size; i++) {
                HashEntry entry = table[i];
                if(entry!=null) {
                    stringBuilder.append("\n\n      " + i + " -> " + "[ " + entry.getValue() + " : " + entry.getStatus() + " : " + entry.getTree().getTotal() + " ]");
                    stringBuilder.append("\n\n   ---------------------------------");
                }else{
                    stringBuilder.append("\n\n      ").append(i).append(" -> ").append("[ Empty").append(" : ").append("E").append(" : ").append(0).append(" ]");
                    stringBuilder.append("\n\n   ---------------------------------");
                }
            }
        }
        return stringBuilder.toString();
    }
    public ObservableList<Martyr> searchAll(String name){
        ObservableList<Martyr> martyrs = FXCollections.observableArrayList();
        for(int i=0;i<size;i++){
            if(table[i]!=null){
                table[i].getTree().searchAll(martyrs,name);
            }
        }
        return martyrs;
    }
}