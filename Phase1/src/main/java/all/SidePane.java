package all;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

class SidePane extends BorderPane { // a class that extends borderpane
    public SidePane() {
        this.setPadding(new Insets(40, 40, 40, 40)); // sets the padding to 40
        this.setPrefWidth(200);
        this.setStyle("-fx-background-color: skyBlue;"); // changes the background color
        Button bt1 = new Button("?");
        Style.styleButton(bt1);
        Button bt2 = Style.createLeftTriangleButton();
        Button bt3 = Style.createRightTriangleButton();
        bt3.setOnAction(e -> this.setCenter(statistics()));
        HBox hb = new HBox();
        hb.setSpacing(10);
        hb.setAlignment(Pos.TOP_LEFT);
        bt1.setOnAction(e -> support());
        bt2.setOnAction(e -> this.setCenter(mainMenu()));
        hb.getChildren().addAll(bt1, bt2, bt3);
        this.setTop(hb);
        Label label = new Label(""" 
                           “And never think of those who have been killed in
                the cause of Allah as dead. Rather, they are alive  with their Lord,\s
                                                 \s\s  receiving provision‶""");
        label.setFont(Font.font("Arial", FontPosture.ITALIC, 11));
        setAlignment(label, Pos.CENTER);
        this.setBottom(label);
        this.setPrefWidth(450);
        this.setCenter(mainMenu());
    }

    public void support() { // a method used to explain how the program works
        VBox vBox = new VBox();
        TextArea ta = new TextArea();
        ta.setEditable(false);
        ta.setStyle("-fx-text-fill:red;-fx-text-size:14px;");
        String instructions = """
                 User Guide for Martyrs Data Management System
                 
                 1. Introduction
                 
                 Welcome to the Martyrs Data Management System (MDMS)! This guide will walk you through the functionalities and usage of the system designed to manage data related to martyrs.
                 
                 2. Getting Started
                 
                 To begin using MDMS, follow these steps:
                 
                 Run the program.
                 Load the martyrs file using the file chooser provided.
                 The program will read the file line-by-line and populate the districts-locations-dates-martyrs data structure accordingly.
                 3. District Screen
                 
                 In this screen, you can perform various operations related to districts. Here are the available options:
                 
                 Insert New District: Add a new district record to the district tree.
                 Update District Record: Modify an existing district record.
                 Delete District Record: Remove a district record from the data structure.
                 Navigate Through Districts: Traverse the districts in an in-order manner, displaying total number of martyrs in each district. You can navigate to the next or previous district.
                 4. Location Screen
                 
                 This screen allows you to manage location records within districts. Here are the available options:
                 
                 Insert New Location Record: Add a new location record to the location tree.
                 Update Location Record: Modify an existing location record.
                 Delete Location Record: Remove a location record from the data structure.
                 Navigate Through Locations: Traverse through locations level-by-level and from left to right manner. Display information such as earliest and latest dates with martyrs, and the date with the maximum number of martyrs. You can navigate to the next or previous location.
                 5. Martyr Screen
                 
                 In this screen, you can view and manage martyr records. Here are the available options:
                 
                 Navigate Through Dates: Traverse through dates in an in-order manner. Display average martyrs ages, youngest and oldest martyrs, and a list of martyrs sorted by name. You can navigate to the next or previous date.
                 Insert New Martyr Record: Add a new martyr record to the martyrs linked list.
                 Update/Delete Martyr Record: Modify or delete an existing martyr record.
                 Search for Martyrs: Search for martyr records by part of their names.
                 6. Additional Features
                 
                 Use DatePicker to enter dates.
                 Choose districts or locations from combo boxes.
                 Operations consider data from the created districts-locations-dates-martyrs data structure.
                 Option to save the updated data structure to a new file in the same format as the input file. 
                               """;
        ta.setText(instructions);
        Button bt1 = new Button("Understood");
        ta.setPrefHeight(600);
        bt1.setOnAction(e -> this.setCenter(mainMenu()));
        vBox.getChildren().addAll(ta, bt1);
        this.setCenter(vBox);
    }

    public VBox statistics() { // a method that shows the statistics of the whole district list
        MartyrList list = Manager.getDistricts().getAllMartyrs();
        VBox vBox = Style.createVBox();
        Label label = new Label("Total Statistics");
        label.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        Label totalNumber = Style.createLabel("Total Number: " + list.size()); // total martyrs
        totalNumber.setTextFill(Color.RED);
        Label totalFemales = Style.createLabel("Total Females: " + list.calculateTotalFemales()); // total females
        totalFemales.setTextFill(Color.RED);
        Label totalMales = Style.createLabel("Total Males: " + list.calculateTotalMales()); // total males
        totalMales.setTextFill(Color.RED);
        Label averageAge = Style.createLabel("Average Age: " + String.format("%.1f", list.calculateAverage())); // most common date
        averageAge.setTextFill(Color.RED);
        Label mostCommon = Style.createLabel("Most Common Date: " + list.findMostCommonDeathDate()); // most common date
        mostCommon.setTextFill(Color.RED);
        vBox.getChildren().addAll(label, totalNumber, totalFemales, totalMales, averageAge, mostCommon); // adds the labels to a vBox
        return vBox;
    }

    public VBox mainMenu() { // a method that has a Vbox that contains 3 buttons
        Button bt1 = new Button("Load File"); // button to load file
        Button bt2 = new Button("Total Statistics"); // button for total statistics
        Button bt3 = new Button("Clear All"); // button to clear all the list
        Style.styleButton(bt1);
        Style.styleButton(bt2);
        Style.styleButton(bt3);
        Text text = new Text("Select Operation");
        text.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 19));
        bt1.setPrefSize(100, 50);
        bt2.setPrefSize(100, 50);
        bt3.setPrefSize(100, 50);
        bt1.setOnAction(e -> MainScreen.handleLoadAction()); // opens file chooser and loads the file into the system
        bt2.setOnAction(e -> this.setCenter(statistics())); // shows the statistics pane
        bt3.setOnAction(e -> { // clears the entire list when clicked
            Stage stage = new Stage();
            VBox vBox = Style.createVBox();
            vBox.setStyle("-fx-background-color:lightSkyBlue;");
            HBox hb = Style.createHBox();
            Button yes = new Button("yes");
            Button no = new Button("no");
            yes.setOnAction(e1 -> {
                Manager.getDistricts().clear();
                Manager.getMartyrs().clear();
                stage.close();
            });
            no.setOnAction(e1 -> stage.close());
            hb.getChildren().addAll(yes, no);
            vBox.getChildren().addAll(new Text("Are you Sure you want to remove everything?"), hb);
            Scene s = new Scene(vBox, 300, 150);
            stage.setScene(s);
            stage.setResizable(false);
            stage.show();
        });
        VBox vBox = new VBox();
        vBox.getChildren().addAll(text, bt1, bt2, bt3);
        vBox.setSpacing(20);
        vBox.setAlignment(Pos.CENTER);
        return vBox;
    }
}
