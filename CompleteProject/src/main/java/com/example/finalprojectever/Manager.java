package com.example.finalprojectever;

import javafx.collections.ObservableList;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Scanner;

public class Manager {
    /* a Manager class to handle a few operations,
     including converting local date into a string ,
     and converting string into a location
    * */
    static HashMap hashMap = new HashMap();
    private static int counter; // a counter to check the number of Martyrs added
    private static String path;
    public static LocalDate localDate(String s){ // a method to convert String into a local date
        String[] dateArr = s.split("/");
        int year = Integer.parseInt(dateArr[2]);
        int month = Integer.parseInt(dateArr[0]);
        int day = Integer.parseInt(dateArr[1]);
        return LocalDate.of(year, month, day);
    }
    public static String martyrDate(String s){ // a method to convert local date into a different format
        System.out.println("date" + s);
        String[] dateArr = s.split("-");
        int year = Integer.parseInt(dateArr[0]);
        int month = Integer.parseInt(dateArr[1]);
        int day = Integer.parseInt(dateArr[2]);
        String date = month + "/" + day + "/" + year;
        return date;
    }

    public static short isValidAge(String age) { // checks if age is valid
        switch (age.toLowerCase()) {
            case "":
            case "?":
                return -1;
            default:
                try {
                    short num = Short.parseShort(age);
                    if (num >= 0 && num <= 130) {
                        return num;
                    } else {
                        return -2;
                    }
                } catch (NumberFormatException ex) {
                    return -2;
                }
        }
    }
    public static boolean checkInput(String s) { // checks input
        if (s.isEmpty() || s.length() <= 2) {
            return false; // Empty or too short strings are invalid
        }

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                return false; // If any character is a digit, the string is invalid
            }
        }

        return true; // If all characters are non-digits and the string is of sufficient length, it's valid
    }

    public static void readFromFile(String filePath) { // a method that reads the file
        path = filePath;
        try {
            Scanner sc = new Scanner(new File(filePath)); // takes the file path from the picked file
            String name, date, ageString, location, district; // declares string variables
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if(line.startsWith("Name")){ // skips the line that starts with name
                    continue;
                }
                String[] info = line.split(",");
                if (info.length >= 6) { // if the length is greater or equal then do
                    name = info[0]; // takes name at index 0
                    date = info[1]; // takes date at index 1
                    ageString = info[2]; // takes age at index 2
                    location = info[3]; // takes location at index 3
                    district = info[4]; // takes district at index 4
                    char gender = info[5].charAt(0);
                    Martyr martyr = new Martyr(name,date,Manager.isValidAge(ageString),location,district,gender);
                    hashMap.insert(martyr);
                    counter++;

                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex + " ");
        }
    }
    public static void saveAll(){ // a method to save all the information into the same file with the same foramt
        if (path != null) {
            try {
                File f = new File(path);
                PrintWriter pw = new PrintWriter(f); // creates a printWriter object to set the text of the file
                ObservableList<Martyr> martyrs = hashMap.collectAll();
                for (Martyr martyr : martyrs) {
                    pw.println(martyr.getName() + "," + martyr.getDate() + "," + martyr.getAge() + "," + martyr.getLocation() + "," + martyr.getDistrict() + "," + martyr.getGender());
                }
                pw.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }else{
            Style.showWarning("You haven't loaded a file yet!");
        }
    }
    public static void handleSaveAs(){ // a method to save the file into a new file with the same format as before

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save As");

        // Set extension filters
        FileChooser.ExtensionFilter txtFilter = new FileChooser.ExtensionFilter("Text Files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(txtFilter);

        // Show the save file dialog
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try {
                PrintWriter pw = new PrintWriter(file); // creates a printWriter object to set the text of the file
                ObservableList<Martyr> martyrs = hashMap.collectAll();
                for (Martyr martyr : martyrs) {
                    pw.println(martyr.getName() + "," + martyr.getDate() + "," + martyr.getAge() + "," + martyr.getLocation() + "," + martyr.getDistrict() + "," + martyr.getGender());
                }
                pw.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }else {
            System.out.println("no file selected");
        }

    }



    public static int getCounter(){
        return counter;
    }
}
