package com.example.phase2;

import javafx.beans.property.SimpleStringProperty;

import java.util.Comparator;
import java.util.Objects;
public class Martyr implements Comparable<Martyr> {
    // Declaring Attribute
    private static int counter;
    private final SimpleStringProperty name; // Declaring a SimpleStringProperty variable
    private final SimpleStringProperty location;// Declaring a SimpleStringProperty variable
    private final SimpleStringProperty district;// Declaring a SimpleStringProperty variable
    private final SimpleStringProperty date;// Declaring a SimpleStringProperty variable
    private short age;// Declaring a byte variable
    private char gender;// Declaring a character variable
    public Martyr() { // no-arg constructor
        this("", "", (short) 0, "", "",' '); // calls the arg constructor
    }

    public Martyr(String name, String date, short age, String location, String district, char gender) { // argument constructor
        this.name=new SimpleStringProperty(name);// creates new SimpleStringProperty
        this.date = new SimpleStringProperty(date); // creates new SimpleStringProperty
        this.age = age; // assigns age
        this.location = new SimpleStringProperty(location);// creates new SimpleStringProperty
        this.district = new SimpleStringProperty(district);// creates new SimpleStringProperty
        this.gender = gender; // assigns gender
        counter++;
    }

    // getters
    public String getName() {
        return name.get();
    }

    public short getAge() {
        return age;
    }

    public String getLocation() {
        return location.get();
    }

    public String getDistrict() {
        return district.get();
    }

    public String getDate() {
        return date.get();
    }

    public char getGender() {
        return gender;
    }

    // setters
    public void setName(String name) {
        this.name.set(name);
    }

    public void setAge(short age) {
        this.age=age;
    }

    public void setLocation(String location) {
        this.location.set(location);
    }

    public void setDistrict(String district) {
        this.district.set(district);
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    @Override
    public String toString() { // toString method that returns martyr info
        return "Martyr{" +
                "name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", district='" + district + '\'' +
                ", date='" + date + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                '}';
    }
    @Override
    public boolean equals(Object o) { // Override equals method so i can compare Martyrs
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Martyr martyr = (Martyr) o;
        return age == martyr.age && gender == martyr.gender &&
                Objects.equals(name.get().toLowerCase(), martyr.name.get().toLowerCase()) &&
                Objects.equals(location.get().toLowerCase(), martyr.location.get().toLowerCase()) &&
                Objects.equals(district.get().toLowerCase(), martyr.district.get().toLowerCase()) &&
                Objects.equals(date, martyr.date);
    }

    @Override
    public int compareTo(Martyr o) {
        return this.getName().compareTo(o.getName());
    }
}