package all;

import javafx.beans.property.SimpleStringProperty;

import java.util.Objects;
public class Martyr {
    // Declaring Attribute
    private final SimpleStringProperty  name; // Declaring a SimpleStringProperty variable
    private final SimpleStringProperty location;// Declaring a SimpleStringProperty variable
    private final SimpleStringProperty district;// Declaring a SimpleStringProperty variable
    private final SimpleStringProperty date;// Declaring a SimpleStringProperty variable
    private byte age;// Declaring a byte variable
    private char gender;// Declaring a character variable
    public Martyr() { // no-arg constructor
        this("", "", (byte) 0, "", "",' '); // calls the arg constructor
    }

    public Martyr(String name, String date, byte age, String location, String district, char gender) { // argument constructor
        this.name=new SimpleStringProperty(name);// creates new SimpleStringProperty
        this.date = new SimpleStringProperty(date); // creates new SimpleStringProperty
        this.age = age; // assigns age
        this.location = new SimpleStringProperty(location);// creates new SimpleStringProperty
        this.district = new SimpleStringProperty(district);// creates new SimpleStringProperty
        this.gender = gender; // assigns gender
    }

    // getters
    public String getName() {
        return name.get();
    }

    public byte getAge() {
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

    public void setAge(byte age) {
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

}

class Location { // location class
    private final SimpleStringProperty locationName;// Declaring a SimpleStringProperty variable
    public Location(String locationName) {
        this.locationName = new SimpleStringProperty(locationName);
    }

    // getters and setters for Location attributes

    public String getLocationName() {
        return locationName.get();
    }
    public void setLocationName(String locationName) {

        this.locationName.set(locationName);
    }


    @Override
    public String toString() {
        return locationName + " ";

    }
    @Override
    public boolean equals(Object obj) { // equals method
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Location locationObj = (Location) obj;
        return locationName.get().equalsIgnoreCase(locationObj.getLocationName());
    }
}

class District { // district class
    private final SimpleStringProperty district;// Declaring a SimpleStringProperty variable
    public District(String district) {

        this.district = new SimpleStringProperty(district);
    } // constructor
    //getters and setters
    public String getDistrict() {
        return district.get();
    }
    public void setDistrict(String district) {
        this.district.set(district);
    }
    @Override
    public String toString() {
        return district.get();
    }
    @Override
    public boolean equals(Object obj) { // equals method
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        District districtObj = (District) obj;
        return district.get().equalsIgnoreCase(districtObj.district.get());
    }
}
