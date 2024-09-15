package com.example.finalprojectever;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class MartyrScreen extends Tab { // martyr screen which is loaded when selecting a date
    private final BorderPane borderPane = new BorderPane();
    private final VBox vBox = Style.createVBox();
    private static final Label label = new Label();
    static MartyrTableView martyrTableView;
    static Label height = new Label();
    static Label size = new Label();
    private static void updateLabel(String msg, boolean result){
        label.setText(null);
        label.setText(msg);
        if(result){
            label.setTextFill(Color.GREEN);
        }else{
            label.setTextFill(Color.RED);
        }
        label.setStyle("-fx-background-color:white; -fx-font-family: 'Segoe Print';-fx-font-size:15;-fx-font-weight:bold;");
    }

    public MartyrScreen(HashEntry hash){
       height.setText(" Height:  " + hash.getTree().getHeight() +"  ");
        height.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-text-fill: black; -fx-font-family: 'Segoe Print'; -fx-background-radius: 50; -fx-border-radius: 50;-fx-font-size:17;");
        size.setText(" Size:  " + hash.getTree().getSize() +"  ");
        size.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-text-fill: black; -fx-font-family: 'Segoe Print'; -fx-background-radius: 50; -fx-border-radius: 50;-fx-font-size:17;");
        HBox statistics = new HBox();
        statistics.setPadding(new Insets(5,5,5,5));
        statistics.setSpacing(15);
        statistics.getChildren().addAll(height,size);
        borderPane.setTop(statistics);
        ObservableList<Martyr> list = hash.getTree().levelOrder();
        martyrTableView = new MartyrTableView(list);
        ContextMenu contextMenu = new ContextMenu();
        MenuItem update = new MenuItem("Update");
        MenuItem delete = new MenuItem("Delete");
        contextMenu.getItems().addAll(delete,update);
        martyrTableView.setOnMouseClicked(e -> {
            contextMenu.show(martyrTableView, e.getScreenX(), e.getScreenY()); // pops up a menu that contains delete and update when clicking on the table view
        });
        update.setOnAction(e -> { // handing the update logic, it just takes the information from the table view item and it updates it
            Martyr martyr = martyrTableView.getSelectionModel().getSelectedItem();
            if(martyr!=null) {
                try {
                    Style.showWarning("You are updating " + martyr.getName());
                    borderPane.setCenter(UpdateMartyr.handleUpdate(martyr,hash));
                }catch (NullPointerException ex){
                    System.out.println(ex.toString());
                }
            }
        });
        delete.setOnAction(e -> {
            Martyr martyr = martyrTableView.getSelectionModel().getSelectedItem();
            if (martyr!=null){
                try {
                    String s = "Are you sure you want to delete this item?" + "\nName: " + martyr.getName() + "\nLocation: " + martyr.getLocation() + "\nDistrict: " + martyr.getDistrict() + "\nDate: " + martyr.getDate();

                    boolean result = Style.showConfirmation(s);
                    if (result) {
                        String martyrName = martyr.getName();
                        hash.getTree().delete(martyr);
                        height.setText(" Height:  " + hash.getTree().getHeight() +"  ");
                        size.setText(" Size:  " + hash.getTree().getSize() +"  ");
                        Style.showWarning(martyrName + " has been deleted successfully");
                    }
                }catch (NullPointerException ex){
                    System.out.println(ex.toString());
                }
            }
        });



        borderPane.setStyle("-fx-background-color:lightSkyBlue;");
        this.setText("Martyr Screen");
        SplitPane splitPane = new SplitPane();

        VBox searching = new VBox();
        searching.setAlignment(Pos.CENTER);
        TextField search = new TextField();
        Button enter = new Button();
        enter.setOnAction(e->{
            martyrTableView.getItems().clear();
            ObservableList<Martyr> martyrs = hash.getTree().searchSpecificHash(search.getText());
            martyrTableView.getItems().addAll(martyrs);
        });
        HBox hBox = MainScreen.SearchBar(search,enter);
        searching.getChildren().addAll(martyrTableView,hBox);
        splitPane.getItems().addAll(searching,borderPane);
        VBox.setVgrow(martyrTableView, Priority.ALWAYS);
        this.setContent(splitPane);
        initialize(hash);
        splitPane.setDividerPositions(0.60); // sets the split pane divider at position 70%
    }
    public void initialize(HashEntry hash){
        Button bt1 = new Button("Insert");
        Button bt2 = new Button("Update");
        Button bt3 = new Button("Delete");
        Button bt4 = new Button("Back");
        Button bt5 = new Button("Level Order");
        Button bt6 = new Button("HeapSort");

        Style.style(bt1);
        Style.style(bt2);
        Style.style(bt3);
        Style.style(bt4);
        Style.style(bt5);
        Style.style(bt6);
        bt6.setPrefSize(150,60);

        bt1.setPrefSize(150,60);
        bt2.setPrefSize(150,60);
        bt3.setPrefSize(150,60);
        bt4.setPrefSize(70,25);
        bt5.setPrefSize(150,60);

        vBox.getChildren().addAll(bt1,bt2,bt3,bt5,bt6);

        bt1.setOnAction(e->{
            handleInsert(hash);
        });
        bt2.setOnAction(e->{
            handleUpdate(hash);
        });
        bt3.setOnAction(e->{
            handleDelete(hash);
        });
        bt5.setOnAction(e->{
            martyrTableView.getItems().clear();
            ObservableList<Martyr> list = hash.getTree().levelOrder();
            martyrTableView.getItems().addAll(list);
        });

        bt6.setOnAction(e->{
            martyrTableView.getItems().clear();
            hash.getTree().minHeapSort(martyrTableView);
        });

        borderPane.setCenter(vBox);
        HBox hBox = Style.createHBox();
        hBox.getChildren().addAll(bt4);
        hBox.setPadding(new Insets(10,10,10,10));
        borderPane.setBottom(hBox);
        bt4.setOnAction(e->{
            borderPane.setCenter(vBox);
        });

    }
    public void handleInsert(HashEntry hash){ // a method to insert a martyr into the avl tree of the hashentry
        label.setText(null);
        // creates GUI components
        TextField tf1 = new TextField();
        TextField tf2 = new TextField();
        ComboBox<String> districtCbo = Style.getDistrictCbo();
        ComboBox<String> locationCbo = Style.getLocationCbo();
        locationCbo.getItems().clear();
        DatePicker datePicker = Style.datePicker();
        RadioButton[] g = new RadioButton[3];
        HBox gender = Style.genders(g);
        GridPane gp = Style.martyrInfo(tf1,tf2,gender,locationCbo,districtCbo,datePicker);
        VBox vBox = Style.createVBox();
        Button button = new Button("Insert");
        Style.style(button);
        Text text = new Text("Enter the following Information");
        text.setStyle("-fx-text-fill: black; -fx-font-family: 'Segoe Print';-fx-font-size:20;");
        text.setFill(Color.BLACK);
        text.setFont(Font.font("Arial",19));
        vBox.getChildren().addAll(text,gp,button,label);
        districtCbo.setOnAction(e -> { // comboBox action, transfers the location related to the district into the location comboBox
            locationCbo.getItems().clear();
            ObservableList<String> list = FXCollections.observableArrayList();
            Manager.hashMap.traverse(districtCbo.getValue(),list);
            locationCbo.getItems().addAll(list);

        });
        datePicker.setValue(hash.getValue());
        datePicker.setEditable(false);
        button.setOnAction(e->{
            datePicker.setValue(hash.getValue());
            String name = tf1.getText();
            if(tf1.getText().isEmpty() && tf2.getText().isEmpty()){ // checks if textfields are empty
                updateLabel("All TextFields are required",false);
                return;
            }
            if(tf1.getText().isEmpty()){ // checks if name is empty
                updateLabel("Enter Name",false);
                return;
            }
            if(tf2.getText().isEmpty()){ // checks if age is empty
                updateLabel("Enter age",false);
                return;
            }
            String ageS = tf2.getText();
            if(Manager.isValidAge(ageS)==-2){ // checks if age is valid
                updateLabel("Invalid age",false);
                return;
            }
            char gen;
            if(g[0].isSelected()){ // assigns gender
                gen = 'M';
            }else if(g[1].isSelected()){
                gen = 'F';
            }else if(g[2].isSelected()){
                gen = 'N';
            }else{
                label.setText("Select gender");
                return;
            }
            if(districtCbo.getValue()==null){ // checks if district is null
                updateLabel("Select District",false);
                return;
            }
            if(locationCbo.getValue()==null){ // checks if location is null
                updateLabel("Select Location",false);
                return;
            }
            short age = Manager.isValidAge(ageS);

            Martyr martyr = new Martyr(name,Manager.martyrDate(String.valueOf(datePicker.getValue())),age,String.valueOf(locationCbo.getValue()),String.valueOf(districtCbo.getValue()),gen);
            if(hash.getTree().contains(martyr.getName())){
                updateLabel("Martyr already exists",false);
                return;
            }
            height.setText(" Height:  " + hash.getTree().getHeight() +"  ");
            size.setText(" Size:  " + hash.getTree().getSize() +"  ");
            hash.getTree().insert(martyr);
            updateLabel("Martyr added successfully",true);

            tf1.clear();
            tf2.clear();

        });
        borderPane.setCenter(vBox);
    }
    public void handleDelete(HashEntry hash){ // a method to delete the martyr from the avl tree
        label.setText(null);
        TextField tf = new TextField();
        Style.style(tf);
        tf.setMaxWidth(200);
        tf.setPromptText("Enter Name");
        GridPane gridPane = Style.createGridPane();
        gridPane.add(Style.styleLabel("Name"),0,0);
        gridPane.add(tf,1,0);
        VBox vBox = Style.createVBox();
        Button button = new Button("Delete");
        vBox.getChildren().addAll(gridPane,button,label);
        Style.style(button);
        button.setOnAction(e->{
            if(tf.getText().isEmpty()){
                updateLabel("Enter Name",false);
                return;
            }
            String name = tf.getText();
            Martyr martyr = hash.getTree().find(name);

            if(martyr!=null){
                boolean result = Style.showConfirmation("Are you sure you want to delete " + name + "?" + "\n Date of Death: " + hash.getValue());
                if(result) {
                    hash.getTree().delete(martyr); // deletes the martyr from the tree
                    height.setText(" Height:  " + hash.getTree().getHeight() +"  ");
                    size.setText(" Size:  " + hash.getTree().getSize() +"  ");
                }
            }

        });
        borderPane.setCenter(vBox);

    }
    public void handleUpdate(HashEntry hash) {
        label.setText(null);
        TextField martyrName = new TextField();
        Button search = new Button("Search");
        GridPane gridPane = confirmation(martyrName,search);
        search.setOnAction(e->{
            if(martyrName.getText().isEmpty()){
                updateLabel("Enter Name",false);
                return;
            }
            Martyr martyr = hash.getTree().find(martyrName.getText());
            if(martyr!=null){
                borderPane.setCenter(UpdateMartyr.handleUpdate(martyr,hash));
            }else{
                updateLabel("Martyr is not found",false);
            }
        });
        borderPane.setCenter(gridPane);
    }
    public GridPane confirmation(TextField tf, Button button){ // searches the martyr before updating
        tf.setMaxWidth(200);
        tf.setPromptText("Enter Name");
        Style.style(button);
        Style.style(tf);
        GridPane gp = Style.createGridPane();
        gp.add(Style.styleLabel("Name"),0,0);
        gp.add(tf,1,0);
        gp.add(button,1,2);
        return gp;
    }
}
