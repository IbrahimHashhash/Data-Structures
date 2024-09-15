package com.example.finalprojectever;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Martyr implements Comparable<Martyr> {
    // Static counter to count instances of Martyr
    private static int counter = 0;

    // Properties for Martyr attributes
    private SimpleStringProperty name;
    private SimpleStringProperty location;
    private SimpleStringProperty district;
    private SimpleStringProperty date;
    private SimpleIntegerProperty age;
    private SimpleObjectProperty<Character> gender;

    // Default constructor
    public Martyr() {
        this("", "", (short) 0, "", "", ' ');
    }

    // Parameterized constructor
    public Martyr(String name, String date, short age, String location, String district, char gender) {
        this.name = new SimpleStringProperty(name);
        this.date = new SimpleStringProperty(date);
        this.age = new SimpleIntegerProperty(age);
        this.location = new SimpleStringProperty(location);
        this.district = new SimpleStringProperty(district);
        this.gender = new SimpleObjectProperty<>(gender);
        counter++; // Increment the counter each time a new Martyr is created
    }

    // Getters and setters for properties

    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        Martyr.counter = counter;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public String getLocation() {
        return location.get();
    }

    public void setLocation(String location) {
        this.location.set(location);
    }

    public SimpleStringProperty locationProperty() {
        return location;
    }

    public String getDistrict() {
        return district.get();
    }

    public void setDistrict(String district) {
        this.district.set(district);
    }

    public SimpleStringProperty districtProperty() {
        return district;
    }

    public String getDate() {
        return date.get();
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public int getAge() {
        return age.get();
    }

    public void setAge(short age) {
        this.age.set(age);
    }

    public SimpleIntegerProperty ageProperty() {
        return age;
    }

    public char getGender() {
        return gender.get();
    }

    public void setGender(char gender) {
        this.gender.set(gender);
    }

    public SimpleObjectProperty<Character> genderProperty() {
        return gender;
    }

    // toString method to represent Martyr object as a String

    @Override
    public String toString() {
        return "Martyr{" +
                "name='" + name.get() + '\'' +
                ", location='" + location.get() + '\'' +
                ", district='" + district.get() + '\'' +
                ", date='" + date.get() + '\'' +
                ", age=" + age.get() +
                ", gender=" + gender.get() +
                '}';
    }

    // Equals method to compare Martyr objects for equality

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Martyr martyr = (Martyr) o;
        return age.get() == martyr.age.get() &&
                gender.get() == martyr.gender.get() &&
                name.get().equalsIgnoreCase(martyr.name.get()) &&
                location.get().equalsIgnoreCase(martyr.location.get()) &&
                district.get().equalsIgnoreCase(martyr.district.get()) &&
                date.get().equals(martyr.date.get());
    }

    // CompareTo method to compare Martyr objects for sorting

    @Override
    public int compareTo(Martyr martyr) {
        int compareDistrict = this.district.get().compareToIgnoreCase(martyr.district.get());
        if (compareDistrict == 0) {
            return this.name.get().compareTo(martyr.name.get());
        }
        return compareDistrict;
    }
}