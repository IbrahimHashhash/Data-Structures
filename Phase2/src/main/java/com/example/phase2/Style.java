package com.example.phase2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Style {

    private static final ObservableList<String> districtList = FXCollections.observableArrayList();
    private static final ObservableList<Martyr>  martyrObList = FXCollections.observableArrayList();

    public static ComboBox<String> getDistrictCbo() {
        ComboBox<String> districtCbo = new ComboBox<>(districtList);
        style(districtCbo);
        districtCbo.setPromptText("Select District");
        return districtCbo;
    }


    public static ComboBox<String> getLocationCbo() {
        ComboBox<String> locationCbo = new ComboBox<>();
        style(locationCbo);
        locationCbo.setPromptText("Select Location");
        return locationCbo;
    }
    public static Text createText(String s){
        Text text = new Text(s);
        text.setStyle("-fx-text-fill: darkred; -fx-font-family: 'Segoe Print';-fx-font-size:15;-fx-font-weight:bold;");
        text.setFill(Color.DARKRED);
        return text;
    }

    public static ComboBox<Character> getGenderCbo() {
        ComboBox<Character> genderCbo = new ComboBox<>();
        style(genderCbo);
        genderCbo.getItems().addAll('M','F','N');
        genderCbo.setPromptText("Select Gender");
        return genderCbo;
    }

    public static ObservableList<Martyr> getMartyrObList() {
        return martyrObList;
    }

    public static GridPane martyrInfo(TextField tf1,TextField tf2, ComboBox<Character> gender, ComboBox<String> lBox, ComboBox<String> dBox, DatePicker datePicker) {
        GridPane gp = new GridPane();
        Style.style(tf1);
        Style.style(tf2);
        gp.setAlignment(Pos.CENTER);
        gp.setHgap(10);
        gp.setVgap(30);
        tf1.setPromptText("Enter Name");
        tf2.setPromptText("000");
        tf2.setMaxWidth(45);
        String[] labels = {"Name", "Age", "Gender", "Date", "District", "Location"};
        datePicker.setPromptText("MM - DD - YYYY");
        for (int i = 0; i < labels.length; i++) {
            gp.add(createText(labels[i]), 0, i);
        }
        gp.add(tf1, 1, 0); // Name TextField
        gp.add(tf2, 1, 1); // Age TextField
        gp.add(gender, 1, 2); // Gender ComboBox
        gp.add(datePicker, 1, 3); // Date DatePicker
        gp.add(dBox, 1, 4); // District ComboBox
        gp.add(lBox, 1, 5); // Location ComboBox
        return gp;
    }
    public static GridPane createGridPane(){
        GridPane gp = new GridPane();
        gp.setAlignment(Pos.CENTER);
        gp.setVgap(20);
        gp.setHgap(20);
        return gp;
    }
    public VBox navigate(){
        return new VBox();
    }



    public static DatePicker datePicker(){
        DatePicker datePicker = new DatePicker();
        datePicker.setPromptText("DD " + " MM " + " YYYY ");
        style(datePicker);
        return datePicker;
    }

    public static HBox createHBox(){
        HBox hb = new HBox();
        hb.setSpacing(20);
        hb.setAlignment(Pos.CENTER);
        return hb;
    }
    public static VBox createVBox(){
        VBox vBox = new VBox();
        vBox.setSpacing(40);
        vBox.setAlignment(Pos.CENTER);
        //vBox.setStyle("-fx-background-color: rgba(255, 255, 255, 0.58);");

        return vBox;
    }
    public static Button createRightTriangleButton() { // method that returns a triangular shaped button
        // Create an inverted triangle pointing to the right
        Polygon invertedTriangle = new Polygon();
        invertedTriangle.getPoints().addAll(
                0.0, 0.0,
                20.0, 10.0,
                0.0, 20.0
        );

        // Create a button with the inverted triangle shape
        Button button = new Button();
        button.setShape(invertedTriangle);
        style(button);

        return button;
    }
    public static Button createLeftTriangleButton() {// method that returns a triangular shaped button but inverted
        // Create an inverted triangle pointing to the left
        Polygon invertedTriangle = new Polygon();
        invertedTriangle.getPoints().addAll(
                20.0, 0.0,
                0.0, 10.0,
                20.0, 20.0
        );

        // Create a button with the inverted triangle shape
        Button button = new Button();
        button.setShape(invertedTriangle);
        button.setStyle(" -fx-background-color: orange; -fx-border-width: 1.5px;");

        // Set action events for the button
        style(button);
        return button;
    }
    public static HBox palestineColors(){
            Circle c1 = new Circle();
            c1.setFill(Color.GREEN);
            c1.setRadius(6);

            Circle c2 = new Circle();
            c2.setRadius(6);
            c2.setFill(Color.RED);
            Circle c3 = new Circle();
            c3.setRadius(6);
            c3.setFill(Color.BLACK);
            Circle c4 = new Circle();
            c4.setFill(Color.WHITE);
            c4.setRadius(6);
            HBox hb = new HBox();
            hb.setAlignment(Pos.BASELINE_LEFT);
            hb.getChildren().addAll(c1,c2,c3,c4);
            hb.setSpacing(4);
            return hb;
        }
        public static VBox help(){
            VBox vBox = new VBox();
            TextArea ta = new TextArea();
            ta.setEditable(false);
            String instructions = """
                           Introduction:-
                           Welcome to B'Tselem! This guide helps you navigate the system for managing martyr data.
                            ---------------------------------------------------------------------                           
                          
                           
                           Getting Started: Run the program, load martyrs file.
                           
                           ---------------------------------------------------------------------                           
                           1- District Menu:
                           
                           -Insert, Update, Delete: Manage district records.
                           -Navigate: Move through districts, seeing martyr totals.
                           
                           ---------------------------------------------------------------------                           
                           2- Location Menu:
                           
                           -Insert, Update, Delete: Handle location records.
                           -Navigate: Explore locations, noting dates and martyrs.
                           
                           ---------------------------------------------------------------------                        
                           3-Martyr Menu:
                           
                           - Navigate: Browse dates, view stats, and martyrs' details.
                           - Insert, Update/Delete: Manage martyr records.
                           - Search: Find martyrs by name.
                           - Additional Features:
                           - DatePicker: Input dates.
                           - Combo Boxes: Select districts or locations.
                           - Save Data: Keep updated data in the same format.
                           
                           
                           ---------------------------------------------------------------------
                           4-File Menu:
                           
                           - Save: Save current data.
                           - Save As: Save data as a new file:
                           - Load: Load martyrs data.
                           ---------------------------------------------------------------------
                           
                           5-Additional Features (Buttons):
                           
                           - Load File: Import martyrs data.
                           - Total Statistics: View overall data insights.
                           - Clear All: Reset all data and screens.
                           - Background Image: Personalize your interface.
                           - Remove Background: Restore default interface.
                                                                 """;
            ta.setText(instructions);
            ta.setStyle("-fx-text-fill:black;-fx-font-size:12px;-fx-font-family: 'Segoe Print';-fx-font-weight:bold;-fx-control-inner-background:darkred;-fx-text-fill:white;");
            vBox.getChildren().add(ta);
            vBox.setAlignment(Pos.CENTER);
            VBox.setVgrow(ta, Priority.ALWAYS);
        return vBox;
    }
    public static FlowPane checkBoxes(CheckBox[] cb,String[] label){
        FlowPane fp = new FlowPane();
        for(int i = 0; i < cb.length; i++){
            cb[i] = new CheckBox(label[i]);
            style(cb[i]);
            cb[i].setMinWidth(60); // Set a fixed width for the checkboxes
            fp.getChildren().add(cb[i]);
        }

        fp.setHgap(10); // Adjust spacing as needed
        fp.setAlignment(Pos.CENTER);
        fp.setVgap(10);
        return fp;
    }
    public static GridPane checkMartyr(ComboBox<String> districtCbo, ComboBox<String> locationCbo, DatePicker datePicker, TextField tf){
        String[] label = {"Name", "Distirct","Location","Date"};
        tf.setPromptText("Enter Name..");
        tf.setMaxWidth(150);
        GridPane gp = new GridPane();
        gp.setAlignment(Pos.CENTER);
        gp.setHgap(20);
        gp.setVgap(20);
        for(int i=0;i<4;i++){
            gp.add(createText(label[i]),0,i);
        }
        gp.add(tf,1,0);
        gp.add(districtCbo,1,1);
        gp.add(locationCbo,1,2);
        gp.add(datePicker,1,3);

        return gp;
    }



    public static boolean showConfirmation(String message) {
        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No");

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Adding custom buttons
        alert.getButtonTypes().setAll(yesButton, noButton);

        // Show and wait for user response
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.setAlwaysOnTop(true);
        stage.showAndWait();
        // Return true if user clicks Yes, false otherwise
        return alert.getResult() == yesButton;
    }
    public static GridPane locationGrid(ComboBox<?> districtCbo, ComboBox<?> locationCbo){
        GridPane gp = Style.createGridPane();
        gp.add(createText("Current Distirct"),0,0);
        gp.add(districtCbo,1,0);
        gp.add(createText("Current Location"),0,1);
        gp.add(locationCbo,1,1);
        return gp;
    }
    public static GridPane locationGrid(ComboBox<?> districtCbo, ComboBox<?> locationCbo,TextField tf){
        GridPane gp = Style.createGridPane();
        gp.add(createText("Current Distirct"),0,0);
        gp.add(districtCbo,1,0);
        gp.add(createText("Current Location"),0,1);
        gp.add(locationCbo,1,1);
        gp.add(createText("New Location"),0,2);
        gp.add(tf,1,2);

        return gp;
    }

    public static void handleNavigate(Button bt1, Button bt2, TableView tableView){
        bt1.setOnAction(e->{
            int currentIndex = tableView.getSelectionModel().getSelectedIndex();
            if (currentIndex > 0) {
                tableView.getSelectionModel().select(currentIndex - 1);
                tableView.scrollTo(currentIndex - 1);
            }
        });

        bt2.setOnAction(e->{
            int currentIndex = tableView.getSelectionModel().getSelectedIndex();
            if (currentIndex < tableView.getItems().size() - 1) {
                tableView.getSelectionModel().select(currentIndex + 1);
                tableView.scrollTo(currentIndex + 1);
            }
        });
        tableView.setStyle("-fx-control-background: red;");
    }

    public static void style(Node x) {
        if (x instanceof TextField) {
            x.setStyle("-fx-background-color: rgba(128, 0, 0, 1); -fx-border-color: rgba(139, 0, 0, 0); -fx-text-fill: rgba(255, 255, 255, 1); -fx-font-family: 'Segoe Print'; -fx-background-radius: 10;");
            x.setOnMouseClicked(e -> {
                x.setStyle("-fx-background-color: rgba(139, 0, 0, 1); -fx-border-color: rgba(255, 0, 0, 0); -fx-text-fill: rgba(0, 0, 0, 1); -fx-font-family: 'Segoe Print'; -fx-background-radius: 10;");
            });
            x.setOnMouseExited(e -> {
                x.setStyle("-fx-background-color: rgba(128, 0, 0, 1); -fx-border-color: rgba(139, 0, 0, 0); -fx-text-fill: rgba(255, 255, 255, 1); -fx-font-family: 'Segoe Print'; -fx-background-radius: 10;");
            });

        } else if(x instanceof  DatePicker){
            x.setStyle("-fx-background-color: rgba(128, 0, 0, 1); -fx-border-color: rgba(139, 0, 0, 0); -fx-text-fill: rgba(255, 255, 255, 1); -fx-font-family: 'Segoe Print';");
            x.setOnMouseClicked(e -> {
                x.setStyle("-fx-background-color: rgba(139, 0, 0, 1); -fx-border-color: rgba(255, 0, 0, 0); -fx-text-fill: rgba(0, 0, 0, 1); -fx-font-family: 'Segoe Print';");
            });
            x.setOnMouseExited(e -> {
                x.setStyle("-fx-background-color: rgba(128, 0, 0, 1); -fx-border-color: rgba(139, 0, 0, 0); -fx-text-fill: rgba(255, 255, 255, 1); -fx-font-family: 'Segoe Print';");
            });

        }else {
            x.setStyle("-fx-background-color: rgba(128, 0, 0, 1); -fx-border-color: rgba(139, 0, 0, 0); -fx-text-fill: rgba(255, 255, 255, 1); -fx-font-family: 'Segoe Print'; -fx-background-radius: 10;");
            x.setOnMouseMoved(e -> {
                x.setStyle("-fx-background-color: rgba(178, 34, 34, 1); -fx-border-color: rgba(139, 0, 0, 0); -fx-text-fill: rgba(255, 255, 255, 1); -fx-font-family: 'Segoe Print'; -fx-background-radius: 10;");
            });
            x.setOnMouseClicked(e -> {
                x.setStyle("-fx-background-color: rgba(205, 92, 92, 1); -fx-border-color: rgba(0, 0, 0, 0); -fx-text-fill: rgba(0, 0, 0, 1); -fx-font-family: 'Segoe Print'; -fx-background-radius: 10;");
            });
            x.setOnMouseExited(e -> {
                x.setStyle("-fx-background-color: rgba(128, 0, 0, 1); -fx-border-color: rgba(139, 0, 0, 0); -fx-text-fill: rgba(255, 255, 255, 1); -fx-font-family: 'Segoe Print';-fx-text-fill: rgba(255, 255, 255, 1); -fx-font-family: 'Segoe Print'; -fx-background-radius: 10;");
            });

        }
    }
    public static void styleMenu(MenuBar menuBar){
        menuBar.setStyle("-fx-background-color: rgba(139, 0, 0, 1); " +
                "-fx-font-size: 14; " +
                "-fx-font-family: 'Segoe Print';");
    }

    public static VBox checkMartyrInfo(TextField name,ComboBox<String> districtCbo, ComboBox<String> locationCbo){
        VBox vBox = createVBox();
        vBox.getChildren().addAll(name,districtCbo,locationCbo);
        return vBox;
    }
    public static Label styleLabel(String text){
        Label label = new Label(text);
        label.setStyle("-fx-font-family: 'Segoe Print';-fx-font-size:15;-fx-font-weight:bold;");
        label.setTextFill(Color.DARKRED);
        return label;

    }
    public static HBox genders(RadioButton[] radioButtons){
        String[] label = {"Male","Female","Other"};
        for(int i=0;i<radioButtons.length;i++){
            radioButtons[i] = new RadioButton();
            radioButtons[i].setText(label[i]);
        }
        ToggleGroup toggleGroup = new ToggleGroup();
        radioButtons[0].setToggleGroup(toggleGroup);
        radioButtons[1].setToggleGroup(toggleGroup);
        radioButtons[2].setToggleGroup(toggleGroup);
        HBox hb = Style.createHBox();
        hb.getChildren().addAll(radioButtons[0],radioButtons[1],radioButtons[2]);
        return hb;

    }

    public  static void showWarning(String text){
        Stage stage = new Stage();
        Button button = new Button("Ok");
        VBox vBox = Style.createVBox();
        vBox.setSpacing(20);
        button.setMaxWidth(80);
        Text warning = new Text(text);
        warning.setFill(Color.WHITE);
        warning.setFont(Font.font("Arial",12.5));
        vBox.getChildren().addAll(warning,button);
        vBox.setStyle("-fx-background-color:darkred;");
        Scene scene = new Scene(vBox,500,110);
        button.setOnAction(e->{
            stage.close();
        });
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("Warning!");
        stage.show();
    }

    public static ObservableList<String> getDistrictList() {
        return districtList;
    }

}
