package com.example.finalprojectever;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class UpdateMartyr {
    static Stage stage = new Stage();

    public UpdateMartyr(Martyr martyr, HashEntry hashEntry){
        Scene scene = new Scene(handleUpdate(martyr,hashEntry),600,700);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("Update " + martyr.getName());
        stage.show();
    }

    static Label label = new Label();

    private static void updateLabel(String msg, boolean result){
        label.setText(null);
        label.setText(msg);
        if(result){
            label.setTextFill(Color.GREEN);
        } else {
            label.setTextFill(Color.RED);
        }
        label.setStyle("-fx-background-color:white; -fx-font-family: 'Segoe Print';-fx-font-size:15;-fx-font-weight:bold;");
    }

    public static VBox handleUpdate(Martyr martyr, HashEntry hash){
        label.setText(null);
        TextField tf1 = new TextField();
        TextField tf2 = new TextField();
        ComboBox<String> districtCbo = Style.getDistrictCbo();
        ComboBox<String> locationCbo = Style.getLocationCbo();
        locationCbo.getItems().clear();
        DatePicker datePicker = Style.datePicker();
        RadioButton[] g = new RadioButton[3];
        HBox gender = Style.genders(g);
        GridPane gp = Style.martyrInfo(tf1, tf2, gender, locationCbo, districtCbo, datePicker);
        VBox vBox = Style.createVBox();
        Button button = new Button("Update");
        Style.style(button);
        Text text = new Text("Enter the following Information");
        text.setStyle("-fx-text-fill: black; -fx-font-family: 'Segoe Print';-fx-font-size:20;");
        text.setFill(Color.BLACK);
        text.setFont(Font.font("Arial", 19));
        vBox.getChildren().addAll(text, gp, button, label);
        districtCbo.setOnAction(e -> {
            locationCbo.getItems().clear();
            ObservableList<String> list = FXCollections.observableArrayList();
            Manager.hashMap.traverse(districtCbo.getValue(),list);
            locationCbo.getItems().addAll(list);
        });

        String[] label1 = {"Name","Date","Age","Location","District","Gender"};
        CheckBox[] cb = new CheckBox[6];
        FlowPane fp = Style.checkBoxes(cb,label1);
        cb[3].setOnAction(e->{
            if(cb[3].isSelected()){
                locationCbo.getItems().clear();
                ObservableList<String> list = FXCollections.observableArrayList();
                districtCbo.setValue(martyr.getDistrict());
                Manager.hashMap.traverse(districtCbo.getValue(),list);
                locationCbo.getItems().addAll(list);
            }
        });

        vBox.getChildren().add(fp);
        button.setOnAction(e -> handleUpdateButton(martyr, hash, tf1, tf2, districtCbo, locationCbo, g, datePicker, cb));
        return vBox;
    }

    private static void handleUpdateButton(Martyr martyr, HashEntry hash, TextField tf1, TextField tf2, ComboBox<String> districtCbo,
                                           ComboBox<String> locationCbo, RadioButton[] g, DatePicker datePicker, CheckBox[] cb) {
        if (!cb[0].isSelected() && !cb[0].isSelected() && !cb[1].isSelected() && !cb[2].isSelected() && !cb[3].isSelected() && !cb[4].isSelected() && !cb[5].isSelected()) {
            updateLabel("you haven't updated anything", false);
            return;
        }

        if (cb[0].isSelected()) {
            updateName(martyr, hash, tf1.getText());
        }

        if (cb[2].isSelected()) {
            updateAge(martyr,hash,tf2.getText());
        }

        if (cb[5].isSelected()) {
            updateGender(martyr, hash,g);
        }

        if (cb[4].isSelected()) {
            updateDistrict(martyr, hash, districtCbo,locationCbo);
        }

        if (cb[3].isSelected()) {
            updateLocation(martyr, hash,locationCbo);
        }

        if (cb[1].isSelected()) {
            updateDate(martyr, hash, datePicker);
        }

        clearFields(tf1, tf2, datePicker);
        Style.showWarning("Martyr updated successfully");
        stage.close();
    }

    private static void updateName(Martyr martyr, HashEntry hash, String newName) {
        if (newName.isEmpty()) {
            updateLabel("Enter Name", false);
            return;
        }
        if (hash.getTree().contains(newName)) {
            updateLabel("Martyr already exists", false);
            return;
        }
        hash.getTree().delete(martyr);
        martyr.setName(newName);
        hash.getTree().insert(martyr);
        updateLabel("Updated Successfully", true);
    }

    private static void updateAge(Martyr martyr,HashEntry hash, String ageS) {
        if (ageS.isEmpty()) {
            updateLabel("Enter Age", false);
            return;
        }
        if (Manager.isValidAge(ageS) == -2) {
            updateLabel("Invalid age", false);
            return;
        }
        hash.getTree().updateAge(Manager.isValidAge(ageS),martyr.getAge());
        martyr.setAge(Manager.isValidAge(ageS));
        updateLabel("Updated Successfully", true);
    }

    private static void updateGender(Martyr martyr,HashEntry hash, RadioButton[] g) {
        char gen;
        if (g[0].isSelected()) {
            gen = 'M';
            if(martyr.getGender()=='F'){
                hash.getTree().decrementF();
                hash.getTree().incrementM();
            }
        } else if (g[1].isSelected()) {
            gen = 'F';
            if(martyr.getGender()=='M'){
                hash.getTree().decrementM();
                hash.getTree().incrementF();
            }

        } else if (g[2].isSelected()) {
            gen = 'N';
        } else {
            updateLabel("Select gender", false);
            return;
        }
        martyr.setGender(gen);
        updateLabel("Updated Successfully", true);
    }

    private static void updateDistrict(Martyr martyr, HashEntry hash, ComboBox<String> districtCbo,ComboBox<String> locationCbo) {
        if (districtCbo.getValue() == null) {
            updateLabel("Select District", false);
            return;
        }
        if(locationCbo.getValue() == null){
            updateLabel("Select Location", false);
            return;
        }
        hash.getTree().delete(martyr);
        martyr.setDistrict(districtCbo.getValue());
        martyr.setLocation(locationCbo.getValue());
        hash.getTree().insert(martyr);
        updateLabel("Updated Successfully", true);
    }

    private static void updateLocation(Martyr martyr, HashEntry hash,ComboBox<String> locationCbo) {
        if (locationCbo.getValue() == null) {
            updateLabel("Select Location", false);
            return;
        }
        hash.getTree().getLocationH().decrementEntry(martyr.getLocation());
        hash.getTree().getLocationH().insert(locationCbo.getValue());

        String location = locationCbo.getValue();
        martyr.setLocation(location);
        updateLabel("Updated Successfully", true);
    }

    private static void updateDate(Martyr martyr, HashEntry hash, DatePicker datePicker) {
        if (datePicker.getValue() == null) {
            updateLabel("Pick a date", false);
            return;
        }
        LocalDate date = datePicker.getValue();

        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime targetDateTime = LocalDateTime.of(date, LocalTime.MIDNIGHT);

        if (targetDateTime.isAfter(currentDateTime)) {
            updateLabel("Error: The date you picked is in the future", false);
            return;
        }

        martyr.setDate(Manager.martyrDate(String.valueOf(date)));
        hash.getTree().delete(martyr);
        Manager.hashMap.insert(martyr);
        updateLabel("Updated Successfully", true);
    }

    private static void clearFields(TextField tf1, TextField tf2, DatePicker datePicker) {
        tf1.clear();
        tf2.clear();
        datePicker.setValue(null);
    }
}