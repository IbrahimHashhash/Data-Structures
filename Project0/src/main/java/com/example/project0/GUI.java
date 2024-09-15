package com.example.project0;
/*
importing the required javafx libraries for GUI class
 */
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import javafx.scene.control.cell.PropertyValueFactory;

public class GUI extends Application { // a GUI class
    /*
    Declaring Private Objects for proper encapsulation
     */
    private TextField tf;
    private TextArea ta;
    private Button bt;
    private VBox vBox;
    private HBox hb3;
    private TableView tv;
    private Tab tvTab;
    private Button[] buttons;
    private final TabPane root = new TabPane(); // declaring and creating a tabpane object
    private Tab tab = new Tab("Insert Martyr"); // declaring and creating a tab object
    private BorderPane bp = new BorderPane();

    @Override
    public void start(Stage stage)  { // the start method that is overridden
        Tab t = new Tab("Display Screen");
        t.setContent(bp); // sets the content of the tap to borderpane
        root.getTabs().add(t); // adds tab "t" to the TabPane
        root.setStyle("-fx-border-color: skyBlue; -fx-background-color: lightSkyBlue;");
        t.setStyle("-fx-border-color: skyBlue; -fx-background-color: lightSkyBlue;");
        t.setClosable(false);
        Scene scene = new Scene(root, 860, 700);
        bp.setCenter(createInteractionPane(stage));
        bp.setTop(ta);
        SupportGuidePane sg = new SupportGuidePane();
        styleButton(sg.getHelp()); // calls the styleButton method and passes a button in the sg class
        bp.setBottom(sg.getHelp()); // setting the buttom at the bottom of the borderpane
        vBox.getChildren().add(showTable());
        stage.setTitle("Btselem");
        bp.setPadding(new Insets(30,10,10,10)); // sets padding
        stage.setScene(scene);
        stage.show();
    }
    private Button showTable(){ // a method that handels the "Display Martyrs" button
        Button table = new Button("Display Martyrs");
        styleButton(table);
        table.setOnAction(e -> {
            tvTab = new Tab("Table View");
            tvTab.setStyle("-fx-border-color: skyBlue; -fx-background-color: lightSkyBlue;");
            tvTab.setContent(createTableView());
            root.getTabs().add(tvTab);
            root.getSelectionModel().select(tvTab); // this is used to move to the tab right away when it is added
        });
        return table;
    }
    private TableView createTableView() { // a method to create the table view
        ObservableList<Martyr> data = FXCollections.observableArrayList();
        for (int i = 0; i < Manager.getMartyrCounter(); i++) {
            Martyr martyr = Manager.getMartyrs()[i];
            data.add(new Martyr(
                    martyr.getName(),
                    martyr.getAge(),
                    martyr.getEventLocation(),
                    martyr.getDateOfDeath(),
                    martyr.getGender()
            ));
        }
        TableView<Martyr> tableView = new TableView<>(data);  // creating the TableView
        TableColumn<Martyr,String> nameCol = new TableColumn<>("Name");// creating TableColumn for Name
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Martyr,Byte> ageCol = new TableColumn<>("Age");// creating TableColumn for Age
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));

        TableColumn<Martyr,Character> genderCol = new TableColumn<>("Gender");// creating TableColumn for Gender
        genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));

        TableColumn<Martyr,String> eventCol = new TableColumn<>("Event Location");// creating TableColumn for Event Location
        eventCol.setCellValueFactory(new PropertyValueFactory<>("eventLocation"));

        TableColumn<Martyr,String> dateCol = new TableColumn<>("Date of Death");// creating TableColumn for Date of Death
        dateCol.setCellValueFactory(new PropertyValueFactory<>("dateOfDeath"));

        // adding columns to the TableView
        tableView.getColumns().addAll(nameCol, ageCol, eventCol, dateCol, genderCol);
        return tableView;
    }
    private void styleButton(Button button) { // a method used to style Buttons
        button.setStyle("-fx-background-color: aliceBlue; -fx-border-color: DarkSlateGray; -fx-text-fill: DarkSlateGray;");
        button.setOnMouseMoved(e -> {
            button.setStyle("-fx-background-color: SkyBlue; -fx-border-color: DarkSlateGray; -fx-text-fill: DarkSlateGray;");
        });
        button.setOnMouseClicked(e -> {
            button.setStyle("-fx-background-color: deepSkyBlue; -fx-border-color: black; -fx-text-fill: black;");
        });

        button.setOnMouseExited(e -> {
            button.setStyle("-fx-background-color: aliceBlue; -fx-border-color: DarkSlateGray; -fx-text-fill: DarkSlateGray;");
        });
    }

    private Pane createInteractionPane(Stage stage) {// a method used to connect the Display Pane elements together
        vBox = new VBox(); // Creating vBox
        tf = createTextField(); // Creating TextField
        bt = createButton(); // Creating the "Enter" Buttpn
        ta = createTextArea(); // Creating TextArea

        HBox hb = new HBox(); // Declaring and creating the HBox used to contain 7 buttons
        hb.getChildren().addAll(tf, bt);
        hb.setAlignment(Pos.CENTER);
        hb.setSpacing(15);
        HBox hb2 = new HBox();
        buttons = new Button[5];
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new Button(String.valueOf(i + 1));
            styleButton(buttons[i]);
            buttons[i].setPrefSize(25, 20);

            hb2.getChildren().add(buttons[i]);
        }
        hb2.setAlignment(Pos.CENTER);
        hb2.setSpacing(10);


        vBox.getChildren().addAll(hb, hb2);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);
        ta.setText("      COMPUTER: \n         click on the buttons bellow numbered from 1 to 5 to do an operation\n" +
                "          1- Insert a martyr into the list \n          2- Delete martyr by name\n          3- Search martyr by name\n          4- pick a file to read\n          5- Number of Martyrs by (age,gender,date)\n \n         **Note: Press Cancel to stop the operation\n \n         Consider clicking on the \"?\" Button, if you need any help");

        bt.setOnAction(e -> { // the Enter button action event used to get the text in the TextField and send it to getRespone method
            String usr = tf.getText().trim();
            tf.clear();
            if (!usr.isEmpty()) {
                String response = getResponse(usr);
                ta.appendText("\n\n      USER: \n        " + usr);
                ta.appendText("\n      COMPUTER: \n        " + response);
            }

        });
        hb2.getChildren().add(cancelButton());
        handleButtons(stage);

        return vBox;
    }

    private Button cancelButton() { // a method to create the cancel button, which is used to cancel an operation
        Button cancel = new Button("Cancel");
        cancel.setPrefSize(60, 25);
        styleButton(cancel); // calling the styleButton to style the cancel button
        cancel.setOnAction(e -> {
            vBox.getChildren().remove(hb3);
            ta.appendText("\n\n     COMPUTER: \n        " + "Operation has been cancelled");
            bt.setOnAction(ee -> {
                String usr = tf.getText().trim();
                tf.clear();
                if (!usr.isEmpty()) {
                    String response = getResponse(usr);
                    ta.appendText("\n\n      USER: \n        " + usr);
                    ta.appendText("\n      COMPUTER: \n        " + response);
                }
            });
        });
        return cancel; // returns button "cancel"
    }

    private void handleButtons(Stage stage) { // a method to handle the buttons 1,2,3,4 and 5
        buttons[0].setOnAction(e -> handleInsertButton());
        buttons[1].setOnAction(e -> handleDeleteButton());
        buttons[2].setOnAction(e -> handleSearchButton());
        buttons[3].setOnAction(e -> handleFileButton(stage));
        buttons[4].setOnAction(e -> handleDisplayButton());
    }

    private void handleInsertButton() { // a method to handel the insert button
        vBox.getChildren().remove(hb3);
        String respone = getResponse("1");
        ta.appendText("\n\n     COMPUTER: \n        " + respone);
        tab = new Tab("Insert Martyr");
        tab.setStyle("-fx-background-color: lightSkyBlue;-fx-border-color: skyBlue;");
        InsertPane insertPane = new InsertPane();
        tab.setContent(insertPane);
        insertPane.getCloseButton().setOnAction( e -> {
            root.getTabs().remove(tab); // removes the tab when user clicks on close button
        });
        root.getTabs().add(tab); // adds the tab to the TabPane
        root.getSelectionModel().select(tab); // moves to the Tab when it is added by calling the select method
    }
    private void handleDeleteButton() {// a method to handel the delete button
        vBox.getChildren().remove(hb3); // removes the HBox when the user clicks on the delete button
        ta.appendText("\n\n     COMPUTER: \n        " + getResponse("2"));
        bt.setOnAction(ee -> {
            if(Manager.exists(tf.getText().trim())) { // calls the exists method from the manager class to check if the name exists or not
                if(!tf.getText().trim().equalsIgnoreCase("Name unknown to B'Tselem")) {
                    Manager.deleteMartyrByName(tf.getText().trim()); // if true then it calls the delete martyr method from the manager class
                    ta.appendText("\n\n      USER: \n        " + tf.getText().trim());
                    ta.appendText("\n\n     COMPUTER: \n        " + "Martyr has been deleted");
                }else{
                    ta.appendText("\n\n      USER: \n        " + tf.getText().trim());
                    ta.appendText("\n\n     COMPUTER: \n        " + Manager.handleUnknownMartyrs(tf.getText().trim()));
                    ta.appendText("\n\n     COMPUTER: \n        " + "Enter the Number of the Unknown martyr you want to delete");
                    bt.setOnAction( eee -> {
                        ta.appendText("\n\n     COMPUTER: \n        " + Manager.deleteByIndex(tf.getText().trim()));
                        tf.clear();

                    });
                }
            } else {
                ta.appendText("\n\n      USER: \n        " + tf.getText().trim());
                ta.appendText("\n\n     COMPUTER: \n        " + "Martyr is not found"); // if false, then displays martyr is not found
            }
            tf.clear();
        });
    }

    private void handleSearchButton() {// a method to handel the search button
        vBox.getChildren().remove(hb3); // removes the hb3 when method is called
        tf.clear(); // clears text field
        String response = getResponse("3"); // calls the getRespone method
        ta.appendText("\n\n     COMPUTER: \n        " + response);
        bt.setOnAction(ee -> {
            if(Manager.exists(tf.getText().trim())) { // checks if the user exists
                ta.appendText("\n\n      USER: \n        " + tf.getText().trim());
                ta.appendText("\n\n     COMPUTER: \n        " + Manager.searchMartyr(tf.getText()));
            } else {
                ta.appendText("\n\n      USER: \n        " + tf.getText().trim());
                ta.appendText("\n\n     COMPUTER: \n        " + "Martyr is not found");
            }
            tf.clear(); // clears text field
        });
    }

    private void handleFileButton(Stage stage) {// a method to handel the fileChooser button
        vBox.getChildren().remove(hb3);
        tf.clear();
        String response = getResponse("4");
        ta.appendText("\n\n     COMPUTER: \n        " + response);
        openFileChooser(stage);
    }

    private void handleDisplayButton() {// a method to handel the display button, it creates temporary buttons, to provide options for the user
        vBox.getChildren().remove(hb3);
        tf.clear();
        String response = getResponse("5");
        ta.appendText("\n\n     COMPUTER: \n        " + response);
        Button bt1 = new Button("Age");
        Button bt2 = new Button("Gender");
        Button bt3 = new Button("Date");
        hb3 = new HBox();
        hb3.setAlignment(Pos.CENTER);
        hb3.setSpacing(10);
        hb3.getChildren().addAll(bt1, bt2, bt3);
        vBox.getChildren().add(hb3);

        bt1.setOnAction(e1 -> handleAgeButton());
        bt2.setOnAction(e1 -> handleGenderButton());
        bt3.setOnAction(e1 -> handleDateButton());
    }

    private void handleAgeButton() {// a method to handel the age button
        ta.appendText("\n\n     COMPUTER: \n        " + "Enter the age of the Martyr");
        bt.setOnAction(ee -> {
            String s = tf.getText().trim();
            ta.appendText("\n\n      USER: \n        " + s);
            tf.clear();
            if(Manager.isValidAge(s) || s.equals("-1")) {
                byte age = Byte.parseByte(s);
                int counter = 0;
                for (int i = 0; i < Manager.getMartyrCounter(); i++) {
                    if (age == Manager.getMartyrs()[i].getAge()) {
                        counter++;
                    }
                }
                ta.appendText("\n\n     COMPUTER: \n        " + "Number of Martyrs of the age " + age +
                        " is " + counter);
            } else {
                ta.appendText("\n\n     COMPUTER: \n        " + "Invalid Age, try again..");
            }
        });
        vBox.getChildren().remove(hb3);
    }


    private void handleGenderButton() {// a method to handel the gender button
        ta.appendText("\n\n     COMPUTER: \n        " + "Enter the gender of the Martyr");
        bt.setOnAction(ee -> {
            String s = tf.getText().trim().toUpperCase();
            if(!s.isEmpty()) {
                ta.appendText("\n\n      USER: \n        " + s);
                tf.clear();
                char gender = s.charAt(0);
                int counter = 0;
                if ((gender == 'M' || gender == 'F' || gender == 'N')) { // checks if the gender is valid
                    for (int i = 0; i < Manager.getMartyrCounter(); i++) {
                        if (gender == Manager.getMartyrs()[i].getGender()) { // checks if the chender is equal to the gender in the list
                            counter++; // if true then it incerements the counter for it
                        }
                    }
                    ta.appendText("\n\n     COMPUTER: \n        " + "Number of Martyrs of the Gender " + gender +
                            " is " + counter); // displays the number of martyrs of that gender in the text area
                } else {
                    ta.appendText("\n\n     COMPUTER: \n        " + "Invalid Gender, try again..");
                }
            }
        });
        vBox.getChildren().remove(hb3);
    }

    private void handleDateButton() {// a method to handel the date button
        ta.appendText("\n\n     COMPUTER: \n        " + "Enter the date of Martyrdom");
        bt.setOnAction(ee -> {
            String s = tf.getText().trim().toUpperCase();
            tf.clear();
            if(Manager.isValidDate(s) && !s.isEmpty()) { // checks if the date is valid
                ta.appendText("\n\n      USER: \n        " + s);
                int counter = 0;
                for (int i = 0; i < Manager.getMartyrCounter(); i++) {
                    if (s.equals(Manager.getMartyrs()[i].getDateOfDeath())) { // checks if the date is equal to the date in the list
                        counter++; // if true it increments the counter
                    }
                }
                ta.appendText("\n\n     COMPUTER: \n        " + "Number of Martyrs of the date " + s +
                        " is " + counter);
            } else {
                ta.appendText("\n\n     COMPUTER: \n        " + "Invalid Date, please try again");
            }
        });
        vBox.getChildren().remove(hb3); // after the user clicks on the button, the pane for that button is deleted so it could be resued
    }

    private TextField createTextField() {// a method to create the text field
        TextField textField = new TextField();
        textField.setPrefSize(500, 50);
        textField.setStyle("-fx-control-inner-background: aliceBlue; -fx-border-color: black; -fx-text-fill: DarkSlateGray;-fx-font-size: 19px;");
        return textField; // returns the textfield
    }
    private Button createButton() {// a method to handel the styles of the buttons
        Button button = new Button("Enter");
        button.setPrefSize(100, 50);
        button.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 14));
        styleButton(button);
        return button;
    }
    private TextArea createTextArea() { // a method to create the text area
        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setPrefSize(500, 400);
        textArea.setStyle("-fx-control-inner-background: aliceBlue;-fx-text-fill: DarkSlateGray;-fx-border-color: black;-fx-font-size: 15px; ");
        return textArea; // returns the text area
    }
    private void openFileChooser(Stage stage) { // an openFileChooser method that handlse FileChooser class
        FileChooser fileChooser = new FileChooser(); // Declaring and creating FileChooser class
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home"))); // sets the Initial directory to home
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV FILES", "*.csv"); // Extension Filter to find the file easily
        fileChooser.getExtensionFilters().add(extFilter);
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) { // if the selected file is not equal to null
            ta.appendText("\n\n     COMPUTER: \n        " + "Path of the file Picked: " + selectedFile.getAbsolutePath());
            Manager.readFromFile(selectedFile.getAbsolutePath()); // calls the readFromFile method from manager class and passes the filePath to the method
            ta.appendText("\n\n     COMPUTER: \n        " + Manager.getMartyrCounter() + " Martyrs have been added successfully"); // displays the number of martyrs

        } else {
            ta.appendText("\n\n     COMPUTER: \n        " + "No file selected.");
        }
    }
    private String getResponse(String userInput) { // a method used to get the proper response based on userInput
        tf.clear();
        String input = userInput.trim().toLowerCase();
        switch(input) { // switch case statement
            case "1":
                return "Please provide me the following attributes to insert a Martyr...";
            case "2":
                return "Type the name of Martyr you want to delete";
            case "3":
                return "Sure, please provide the name of the martyr...";
            case "4":
                return "Sure, opening file chooser...";
            case "5":
                return "Display the number of martyrs based on (age/gender/date)";
            case "clear":
                ta.clear();
                return "Text Area has been cleared";
            case "number of martyrs":
            case "number":
            case "what is":
            case "martyrs":
                return "Number of martyrs is " + Manager.getMartyrCounter();
            default:
                return "Sorry, I don't understand";
        }
    }
    public static void main(String[] args) {
        launch();
    }
}