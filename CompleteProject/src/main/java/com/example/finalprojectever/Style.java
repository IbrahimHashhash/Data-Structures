package com.example.finalprojectever;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Style {
    private static final ObservableList<String> districtList = FXCollections.observableArrayList();
    private static final ObservableList<Martyr>  martyrObList = FXCollections.observableArrayList();

    public static ComboBox<String> getDistrictCbo() { // creates a ComboBox for district
        ComboBox<String> districtCbo = new ComboBox<>(districtList);
        style(districtCbo);
        districtCbo.setPromptText("Select District");
        return districtCbo;
    }


    public static ComboBox<String> getLocationCbo() { // creates a ComboBox for location
        ComboBox<String> locationCbo = new ComboBox<>();
        style(locationCbo);
        locationCbo.setPromptText("Select Location");
        return locationCbo;
    }
    public static Text createText(String s){
        Text text = new Text(s);
        text.setStyle("-fx-text-fill: black; -fx-font-family: 'Segoe Print';-fx-font-size:15;-fx-font-weight:bold;");
        text.setFill(Color.BLACK);
        return text;
    }


    public static ObservableList<Martyr> getMartyrObList() {
        return martyrObList;
    }

    public static GridPane martyrInfo(TextField tf1,TextField tf2, HBox gender, ComboBox<String> lBox, ComboBox<String> dBox, DatePicker datePicker) {
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
    public static GridPane createGridPane(){ // creates GridPane with specific settings
        GridPane gp = new GridPane();
        gp.setAlignment(Pos.CENTER);
        gp.setVgap(20);
        gp.setHgap(20);
        return gp;
    }
    public static DatePicker datePicker(){ // creates date picker
        DatePicker datePicker = new DatePicker();
        datePicker.setPromptText("DD " + " MM " + " YYYY ");
        style(datePicker);
        return datePicker;
    }

    public static HBox createHBox(){ // creates an HBox
        HBox hb = new HBox();
        hb.setSpacing(20);
        hb.setAlignment(Pos.CENTER);
        return hb;
    }
    public static VBox createVBox(){ // creates a VBox
        VBox vBox = new VBox();
        vBox.setSpacing(40);
        vBox.setStyle("-fx-background-color:lightSkyBlue;");
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
    public static void style(Node x) { // Styles a Node with cases
        if (x instanceof TextField) {
            x.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-text-fill: black; -fx-font-family: 'Segoe Print'; -fx-background-radius: 50; -fx-border-radius: 50;");
            x.setOnMouseMoved(e -> {
                x.setStyle("-fx-background-color: deepSkyBlue; -fx-border-color: black; -fx-text-fill: white; -fx-font-family: 'Segoe Print'; -fx-background-radius: 50; -fx-border-radius: 50;");
            });
            x.setOnMouseClicked(e -> {
                x.setStyle("-fx-background-color: deepSkyBlue; -fx-border-color: black; -fx-text-fill: white; -fx-font-family: 'Segoe Print'; -fx-background-radius: 50; -fx-border-radius: 50;");
            });
            x.setOnMouseExited(e -> {
                x.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-text-fill: black; -fx-font-family: 'Segoe Print'; -fx-background-radius: 50; -fx-border-radius: 50;");
            });

        } else if (x instanceof DatePicker) {
            x.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-text-fill: black; -fx-font-family: 'Segoe Print';");
            x.setOnMouseMoved(e -> {
                x.setStyle("-fx-background-color: deepSkyBlue; -fx-border-color: black; -fx-text-fill: white; -fx-font-family: 'Segoe Print';");
            });
            x.setOnMouseClicked(e -> {
                x.setStyle("-fx-background-color: deepSkyBlue; -fx-border-color: black; -fx-text-fill: white; -fx-font-family: 'Segoe Print';");
            });
            x.setOnMouseExited(e -> {
                x.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-text-fill: black; -fx-font-family: 'Segoe Print';");
            });

        } else {
            x.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-text-fill: black; -fx-font-family: 'Segoe Print'; -fx-background-radius: 50; -fx-border-radius: 50;");
            x.setOnMouseMoved(e -> {
                x.setStyle("-fx-background-color: deepSkyBlue; -fx-border-color: black; -fx-text-fill: white; -fx-font-family: 'Segoe Print'; -fx-background-radius: 50; -fx-border-radius: 50;");
            });
            x.setOnMouseClicked(e -> {
                x.setStyle("-fx-background-color: deepSkyBlue; -fx-border-color: black; -fx-text-fill: white; -fx-font-family: 'Segoe Print'; -fx-background-radius: 50; -fx-border-radius: 50;");
            });
            x.setOnMouseExited(e -> {
                x.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-text-fill: black; -fx-font-family: 'Segoe Print'; -fx-background-radius: 50; -fx-border-radius: 50;");
            });
        }
    }
    public static Label styleLabel(String text){
        Label label = new Label(text);
        label.setStyle("-fx-font-family: 'Segoe Print';-fx-font-size:15;-fx-font-weight:bold;");
        label.setTextFill(Color.BLACK);
        return label;

    }

    public  static void showWarning(String text){ // creates a stage that has a warning written on it
        Stage stage = new Stage();
        Button button = new Button("Ok");
        VBox vBox = Style.createVBox();
        vBox.setSpacing(20);
        button.setMaxWidth(80);
        Text warning = new Text(text);
        warning.setFill(Color.BLACK);
        warning.setFont(Font.font("Arial",12.5));
        vBox.getChildren().addAll(warning,button);
        vBox.setStyle("-fx-background-color:lightSkyBlue;");
        Scene scene = new Scene(vBox,500,110);
        button.setOnAction(e->{
            stage.close();
        });
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("Warning!");
        stage.show();
    }
    public static HBox genders(RadioButton[] radioButtons){ // creates a gender layout using radioButtons and HBox
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
    public static ObservableList<String> getDistrictList() {
        return districtList;
    }

}