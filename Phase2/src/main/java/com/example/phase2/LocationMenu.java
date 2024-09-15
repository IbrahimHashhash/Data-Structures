package com.example.phase2;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.List;

public class LocationMenu extends Menu {
    private static Label label = new Label();
    static LNode location = null;
    public LocationMenu(){
        this.setText("Location");
        initialize();

    }

    public void initialize(){
        String[] label ={"Insert","Update","Delete"};
        MenuItem[] items = new MenuItem[3];
        for (int i=0;i<items.length;i++){
            items[i] = new MenuItem(label[i]);
        }
        this.getItems().addAll(items[0],items[1],items[2]);
        items[0].setOnAction(e->{
            handleInsert();
        });
        items[1].setOnAction(e->{
            handleUpdate();
        });
        items[2].setOnAction(e->{
            handleDelete();
        });

    }

    private void updateLabel(String msg, boolean result){
        label.setText(msg);
        label.setStyle("-fx-text-fill: darkred;-fx-background-color:white; -fx-font-family: 'Segoe Print';-fx-font-size:15;-fx-font-weight:bold;");
        if(result){
            label.setTextFill(Color.GREEN);
        }else{
            label.setTextFill(Color.RED);
        }
    }

    public void handleInsert(){
        label.setText(null);
        VBox vBox = Style.createVBox();
        ComboBox<String> districtCbo = Style.getDistrictCbo();
        TextField tf = new TextField();
        Style.style(tf);
        tf.setPromptText("Enter Location..");
        tf.setMaxWidth(150);
        Button bt = new Button("Insert");
        GridPane gp = Style.createGridPane();
        gp.add(Style.createText("District"),0,0);
        gp.add(districtCbo,1,0);
        gp.add(Style.createText("Location"),0,1);
        gp.add(tf,1,1);
        vBox.getChildren().addAll(gp,bt,label);
        Style.style(bt);
        bt.setOnAction(e->{
            if(districtCbo.getValue()!=null){
                if(!tf.getText().isEmpty()){
                   DNode district = Manager.getDistrictTree().find(districtCbo.getValue());
                    if(district.getLocation().find(tf.getText())==null){
                        if(Manager.checkInput(tf.getText())) {
                            district.getLocation().insert(tf.getText());
                            updateLabel("Location has been added successfully",true);

                        }else{
                            updateLabel("Invalid input",false);
                        }
                    }else{
                        updateLabel("Location already exists",false);
                    }
                }else{
                    updateLabel("TextField is empty",false);
                }
            }else{
                updateLabel("Select District",false);
            }
        });

        MainScreen.getSidePane().setCenter(vBox);
    }

    public void handleUpdate(){
        label.setText(null);
        VBox vBox = Style.createVBox();
        ComboBox<String> districtCbo = Style.getDistrictCbo();
        ComboBox<String> locationCbo = Style.getLocationCbo();
        locationCbo.getItems().clear();
        TextField tf = new TextField();
        tf.setPromptText("Enter Location..");
        tf.setMaxWidth(150);
        Style.style(tf);
        Button bt = new Button("Update");
        vBox.getChildren().addAll(Style.locationGrid(districtCbo,locationCbo,tf),bt,label);
        MainScreen.getSidePane().setCenter(vBox);
        districtCbo.setOnAction(e->{
            locationCbo.getItems().clear();
            ObservableList<String> list = Manager.getDistrictTree().collectLocations(districtCbo.getValue());
            locationCbo.getItems().addAll(list);
        });
        Style.style(bt);
        bt.setOnAction(e->{
            if(districtCbo.getValue()!=null){
                if(!tf.getText().isEmpty()) {
                    if (locationCbo.getValue() != null) {
                        DNode district = Manager.getDistrictTree().find(districtCbo.getValue());
                        if (district.getLocation().find(tf.getText()) == null) {
                            if (Manager.checkInput(tf.getText())) {
                                district.getLocation().remove(String.valueOf(locationCbo.getValue()));
                                district.getLocation().insert(tf.getText());
                                updateLabel("Location has been updated successfully", true);

                            } else {
                                updateLabel("Invalid input", false);
                            }
                        } else {
                            updateLabel("Location already exists", false);
                        }
                    } else {
                        updateLabel("Select Location", false);
                    }
                }else{
                    updateLabel("TextField is empty",false);

                }
            }else{
                updateLabel("Select District",false);
            }

        });
    }

    public void handleDelete(){ // handles delete
        label.setText(null);
        VBox vBox = Style.createVBox();
        ComboBox<String> districtCbo = Style.getDistrictCbo();
        ComboBox<String> locationCbo = Style.getLocationCbo();
        locationCbo.getItems().clear();
        Button bt = new Button("Delete");
        locationCbo.getItems().clear();
        districtCbo.setOnAction(e->{
            locationCbo.getItems().clear();
            ObservableList<String> array = Manager.getDistrictTree().collectLocations(districtCbo.getValue());
            for (String s : array) {
                locationCbo.getItems().add(s);
            }
        });
        vBox.getChildren().addAll(Style.locationGrid(districtCbo,locationCbo),bt,label);
        Style.style(bt);
        bt.setOnAction(e-> {
                if (districtCbo.getValue() != null) {
                    if (locationCbo.getValue() != null) {
                        boolean result = Style.showConfirmation("Are you sure, you want to delete this item?" + "\nLocation Name: " + locationCbo.getValue() + "\nDistirct Name: " + districtCbo.getValue());
                        if (result) {
                            DNode district = Manager.getDistrictTree().find(districtCbo.getValue());
                            district.getLocation().remove(String.valueOf(locationCbo.getValue()));
                            updateLabel("Location has been removed successfully", true);
                        }
                    } else {
                        updateLabel("Select Location", false);
                    }
                } else {
                updateLabel("Select District", false);
            }
        });
        MainScreen.getSidePane().setCenter(vBox);
    }
    private static void styleLabel(Label label){
        label.setTextFill(Color.DARKRED);
        label.setStyle("-fx-font-family: 'Segoe Print';-fx-font-size:15;-fx-background-color:white;");

    }
    // a method to handle the navigation of location
    public static void handleNavigate(StackList stack){
        label.setText(null);
        HBox hb = Style.createHBox();
        Label label1 = new Label("Location");
        Label label2 = new Label();
        Label label3 = new Label();
        Label label4 = new Label();
        styleLabel(label4);
        styleLabel(label1);
        styleLabel(label2);
        styleLabel(label3);
        Button prev = Style.createLeftTriangleButton();
        Button next = Style.createRightTriangleButton();
        prev.setPrefSize(45,45);
        next.setPrefSize(45,45);
        hb.getChildren().addAll(prev,label1,next);
        if(!stack.isEmpty()) {
            System.out.println("Is queue empty " + stack.isEmpty());
             location = ((LNode) stack.pop());
            label1.setText(location.getElement() + "");
            label1.setStyle("-fx-font-family: 'Segoe Print';-fx-font-size:15;-fx-background-color:white;");
            label2.setText("Earliest Date: " + location.getDate().findMin().getElement());
            label3.setText("Latest Date: " + location.getDate().findMax().getElement());
            label4.setText("Maximum Martyr Date: " + location.getDate().maxMartyrsDate().getElement());
            StackList previous = new StackList();
            previous.push(location);
            next.setOnAction(e -> {
                 location = ((LNode) stack.pop());
                if(location!=null) {
                    label1.setText(location.getElement());
                    label2.setText("Earliest Date: " + location.getDate().findMin().getElement());
                    label3.setText("Latest Date: " + location.getDate().findMax().getElement());
                    label4.setText("Maximum Martyr Date: " + location.getDate().maxMartyrsDate().getElement());
                    previous.push(location);
                }
            });
            prev.setOnAction(e -> {
                 location = ((LNode) previous.pop());
                if(location!=null) {
                    label1.setText(location.getElement());
                    label2.setText("Earliest Date: " + location.getDate().findMin().getElement());
                    label3.setText("Latest Date: " + location.getDate().findMax().getElement());
                    label4.setText("Maximum Martyr Date: " + location.getDate().maxMartyrsDate().getElement());
                    stack.push(location);
                }
            });
        }
        VBox vBox = Style.createVBox();
        Button button = new Button("Load Date");
        button.setOnAction(e-> MartyrMenu.handleNavigate(location.getDate().inOrder(),stack));
        Style.style(button);
        Button back = new Button("Back");
        back.setOnAction(e->{
            DistrictMenu.handleNavigate();
        });
        Style.style(back);
        vBox.getChildren().addAll(hb,button,back,label2,label4,label3);
        MainScreen.getSidePane().setCenter(vBox);
    }
}
