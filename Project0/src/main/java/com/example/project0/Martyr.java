package com.example.project0;
/*
a public martyr class that has getters and setters and data controlling
 */
public class Martyr {
    private String eventLocation;
    private String dateOfDeath;
    private String name;
    private byte age;
    private char gender;

    public Martyr(){// a no-arg constructor to call for class martyr
    }
    /*
    an argument constructor for class Martyr
     */
    public Martyr(String name, Byte age,String eventLocation, String dateOfDeath,char gender) throws IllegalArgumentException{
        this.name = name;
        setAge(age);
        try {
            setGender(gender);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid gender: " + e.getMessage());
        }
        this.eventLocation=eventLocation;
        try {
            setDateOfDeath(dateOfDeath);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid date of death: " + e.getMessage());
        }
    }
    /*
    getters and setters
     */
    public String getEventLocation(){
        return eventLocation;
    }
    public void setEventLocation(String eventLocation){
        this.eventLocation=eventLocation;
    }
    public String getDateOfDeath(){
        return dateOfDeath;
    }
    public void setDateOfDeath(String dateOfDeath) throws IllegalArgumentException{
        if(Manager.isValidDate(dateOfDeath)){ // calls the isValidDate method from the manager class to check if the date is valid or not
            this.dateOfDeath=dateOfDeath;
        }else{
            throw new IllegalArgumentException("Invalid Date"); // throws an exception if the date was invalid
        }
    }
    public String getName() {
        return name;
    }

    public byte getAge() {
        return age;
    }

    public char getGender() {
        return gender;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Byte age) { // a setter method with filter for age
        String ageS = String.valueOf(age);
        if (Manager.isValidAge(ageS)) {
            this.age=age;
        } else {
            this.age=-1; // if age is unknown, it assigns the age to -1
        }
    }

    public void setGender(char gender) { // a setter with filter for gender
        if(gender == 'M' || gender == 'F' || gender == 'N') { // if statement to check if the gender is valid
            this.gender = gender;
        }else{
            throw new IllegalArgumentException("Invalid Gender Format: " + gender);
        }
    }

    @Override
    public String toString() { // a to string method
        return
                "\n        name: " + name +
                        "\n        age: "  + age +
                        "\n        gender: " + gender +
                        "\n        Event Location: "  + eventLocation +
                        "\n        Date of Martyrdom: "  + dateOfDeath;
    }
}

