package com.example.project0;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Manager {
    private static int martyrCounter = 0; // a counter to count the number of martyrs
    private static Martyr[] martyrs = new Martyr[16]; // an array of Martyr objects with the size of 16
    public static boolean isValidDate(String date) {
        if(date!=null) {
            String[] dates = date.split("/"); // splits the string by "/"
            if (dates.length == 3) {
                // parses the strings to integers
                int day = Integer.parseInt(dates[1]);
                int month = Integer.parseInt(dates[0]);
                int year = Integer.parseInt(dates[2]);
                return (year <= 2025 && year >= 2000) && (month <= 12 && month >= 1) && (day <= 31 && day >= 1); // returns the boolean value of the logical statement
            }
        }
        return false;
    }

    public static boolean exists(String name) { // a method to check if the name already exits in the array of martyrs
        for (int i = 0; i < martyrCounter; i++) {
            if(martyrs[i]!=null) {
                if (martyrs[i].getName().equalsIgnoreCase(name.trim())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static int getMartyrCounter() { // a getter method that returns the martyrCounter

        return martyrCounter;
    }

    public static int incrementCounter() { // getter method to increment the counter
        return martyrCounter++;
    }


    public static Martyr[] getMartyrs() {
        return martyrs;
    } /// a getter method that returns the array of martyrs

    public static Martyr[] doubleArrayLength(Martyr[] array, int martyrCounter) { // a method that returns a double sized array of martyrs
        if (martyrCounter == array.length) {
            Martyr[] newArray = new Martyr[array.length * 2]; // creates a new array with a double size
            System.arraycopy(array, 0, newArray, 0, array.length); // copies the original array from index 0 to the length of array
            return newArray; // returns the array if martyrcounter was equal to array length
        }
        return array; // returns the original array if not
    }

    public static boolean isValidAge(String age) { // a method to check if the age is valid or not
        try {
            byte a = Byte.parseByte(age);
            return (a >= 0 && a <= 150);

        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public static void deleteMartyrByName(String name) { // a method that deletes a martyr by name
        for (int i = 0; i < martyrCounter; i++) {
            if (martyrs[i].getName().equalsIgnoreCase(name)) {
                for (int j = i + 1; j < martyrCounter - 1; j++) {
                    martyrs[j - 1] = martyrs[j]; // shifts elements in the martyrs array to fill the gap left by the deleted martyr
                }
                martyrCounter--;
                break;
            }
        }
    }

    public static boolean isNumerical(String num) { // checks if a string is a number or string
        try {
            int number = Integer.parseInt(num);
            return true;

        } catch (NumberFormatException ex) {
            return false;
        }
    }
    public static String handleUnknownMartyrs(String name) { // a method to handle unknown martyrs
        StringBuilder result = new StringBuilder(); // declaring and creating a string builder class
        if (name != null) {
            for (int i = 0; i < martyrCounter; i++) {
                if (martyrs[i].getName().equalsIgnoreCase("Name unknown to B'Tselem") || martyrs[i].getName().equalsIgnoreCase("??????")) {
                    result.append(martyrs[i].toString()).append("\n        ----------At Index: ").append(i).append("\n");
                }
            }
        }
        return result.toString();
    }

    public static String deleteByIndex(String in) {
        if (isNumerical(in)) { // checks if the string is a number or not
            int index = Integer.parseInt(in);
            if (index >= 0 && index < martyrCounter) { // checks if the index is valid
                for (int i = index + 1; i < martyrCounter; i++) {
                    martyrs[i - 1] = martyrs[i]; // shifts elements in the martyrs array to fill the gap left by the deleted martyr

                }
                martyrCounter--;
                return "Martyr has been deleted Successfully";
            } else {
                return "Index out of range";
            }
        }
        return "Invalid index: " + in;
    }

    public static String searchMartyr(String name) { // a method that searches for martyr by name, and displays its info
        StringBuilder result = new StringBuilder();
        if (name.equalsIgnoreCase("Name unknown to B'Tselem")) {
            for (int i = 0; i < martyrCounter; i++) {
                if (martyrs[i].getName().equalsIgnoreCase(name) || martyrs[i].getName().equalsIgnoreCase("??????")) {
                    result.append(martyrs[i].toString()).append("\n        ----------At Index: ").append(i).append("\n");
                }
            }
            return result.toString();
        }

        for (int i = 0; i < martyrCounter; i++) {
            if (martyrs[i].getName().equalsIgnoreCase(name)) {
                return "Martyr is found:" + martyrs[i].toString();
            }
        }
        return "Martyr doesn't exit";
    }
    public static void readFromFile(String filePath) { // a method to read from file that takes the Path of the file
        String name;
        String ageS;
        String event;
        String date;
        String g;

        try {
            File f = new File(filePath);
            Scanner sc = new Scanner(f);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] info = line.split(",");
                if (info.length < 5) {
                    continue;
                }
                if(info[0].equalsIgnoreCase("name")){
                    continue;
                }
                name = info[0].trim();
                ageS = info[1].trim();
                event = info[2].trim();
                date = info[3].trim();
                g = info[4].trim();
                char gender = g.charAt(0);
                try { // a try and catch inside the while loop, to check if there is issue with number format, if there is then, it handels it
                     martyrs = doubleArrayLength(martyrs,martyrCounter);

                    if (!exists(name) && isValidAge(ageS)) { // checking if the martyr isn't already in the list and has a valid age
                        byte age = Byte.parseByte(ageS);
                        martyrs[martyrCounter] = new Martyr(name, age, event, date, gender); // adds the martyr to the list
                        martyrCounter++; // increments the counter
                    } else if (exists(name) && isValidAge(ageS)) { // else if the martyr exits, then it assigns its name to unknown
                        byte age = Byte.parseByte(ageS);
                        martyrs[martyrCounter] = new Martyr("UnKnown Name", age, event, date, gender); // adds the martyr to the list
                        martyrCounter++;
                    }else {
                        byte age = Byte.parseByte(ageS);
                        martyrs[martyrCounter] = new Martyr(name, age, event, date, 'N');
                        martyrCounter++;
                    }

                }catch (NumberFormatException ex){
                    byte age = -1; // if age is unknown, it assigns age to -1
                    martyrs[martyrCounter] = new Martyr(name, age, event, date, gender);
                    martyrCounter++;
                }catch (ArrayIndexOutOfBoundsException ex){
                    System.out.println(ex.toString());

                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex.toString());
        }
    }
}
