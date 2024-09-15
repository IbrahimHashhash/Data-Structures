package com.example.phase2;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class FileMenu extends Menu {
    private static String filePath;
    public FileMenu(){
        this.setText("File");
        initializeItems();
    }
    /*
    initialize the menu items of the file menu, it consists of load and save all and save as
     */
    public void initializeItems(){
        MenuItem[] items = new MenuItem[3];
        items[0] = new MenuItem();
        items[1] = new MenuItem();
        items[2] = new MenuItem();
        items[0].setText("Load");
        items[1].setText("Save all");
        items[2].setText("Save as");
        this.getItems().addAll(items[0],items[1],items[2]);

        items[0].setOnAction(e-> handleLoad());
        items[1].setOnAction(e-> handleSave());
        items[2].setOnAction(e-> handleSaveAs());

    }

    /*
    this method handles the load logic, it creates a file chooser and you pick the file you want
     */
    public static void handleLoad(){
        FileChooser fc = new FileChooser();
        File selectedFile = fc.showOpenDialog(null);
        if (selectedFile != null) {
            filePath = selectedFile.getAbsolutePath();
            readFromFile();
        } else {
            System.out.println("no file selected"); // if file not found then you print an error statement
        }
    }

    // a static void method that reads from file
    public static void readFromFile() { // a method that reads the file
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
                        if(!Manager.getDistrictTree().contains(district)) { // if district is not already in the tree
                            Manager.getDistrictTree().insert(district); // insert it
                        }

                        LocalDate dateObj = Manager.localDate(date) ; // creates and assigns a local date object that takes a local date
                        DNode districtNode =  Manager.getDistrictTree().find(district); // finds the district node
                        districtNode.insertLocation(location); // insert the location into the district node
                        LNode locationNode = districtNode.getLocation().find(location);
                        locationNode.insertDate(dateObj); // insert the date into the location node
                        BSDateNode dateNode = locationNode.getDate().find(dateObj); // finds the date node
                        dateNode.addMartyr(martyr);
                    }
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex + " ");
        }

    }
    public void handleSave(){
        if (filePath != null) {
            try {
                File f = new File(filePath);
                PrintWriter pw = new PrintWriter(f); // creates a printWriter object to set the text of the file
              //  pw.print("Name," + "Date," + "Age," + "Location," + "District," + "Gender\n");
                List<Martyr> martyrs = Manager.getDistrictTree().collectAllMartyrs();
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

    public void handleSaveAs(){

        // Create a Button for the "Save As" functionality

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
                    //  pw.print("Name," + "Date," + "Age," + "Location," + "District," + "Gender\n");
                    List<Martyr> martyrs = Manager.getDistrictTree().collectAllMartyrs();
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
}
