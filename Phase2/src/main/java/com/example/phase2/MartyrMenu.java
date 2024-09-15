package com.example.phase2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.time.LocalDate;

public class MartyrMenu extends Menu { // martyr menu
    private static int counter = 0;
    private static final Label label = new Label();

    static String name,district,location,date;
    public MartyrMenu(){
        this.setText("Martyr");
        initialize();
    }

    public void initialize(){
        MenuItem[] items = new MenuItem[4];
        String[] labels = {"Insert","Update","Delete","Search"};
        for(int i=0;i<items.length;i++){
            items[i] = new MenuItem(labels[i]);
        }
        this.getItems().addAll(items[0],items[1],items[2],items[3]);
        items[0].setOnAction(e-> handleInsert());
        items[1].setOnAction(e-> handleUpdate());
        items[2].setOnAction(e-> handleDelete());
        items[3].setOnAction(e-> handleSearch());
    }
    private static void updateLabel(String msg, boolean result){
        label.setText(null);
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
        TextField tf1 = new TextField();
        TextField tf2 = new TextField();
        ComboBox<String> districtCbo = Style.getDistrictCbo();
        ComboBox<String> locationCbo = Style.getLocationCbo();
        locationCbo.getItems().clear();
        ComboBox<Character> genderCbo = Style.getGenderCbo();
        DatePicker datePicker = Style.datePicker();
        GridPane gp = Style.martyrInfo(tf1,tf2,genderCbo,locationCbo,districtCbo,datePicker);
        VBox vBox = Style.createVBox();
        Button button = new Button("Insert");
        Style.style(button);
        Text text = new Text("Enter the following Information");
        text.setStyle("-fx-text-fill: darkred; -fx-font-family: 'Segoe Print';-fx-font-size:20;");
        text.setFill(Color.DARKRED);
        text.setFont(Font.font("Arial",19));
        vBox.getChildren().addAll(text,gp,button,label);
        districtCbo.setOnAction(e->{
            locationCbo.getItems().clear();
            ObservableList<String> list = Manager.getDistrictTree().collectLocations(districtCbo.getValue());
            locationCbo.getItems().addAll(list);
        });
        button.setOnAction(e->{
            String name = tf1.getText();
            if(tf1.getText().isEmpty() && tf2.getText().isEmpty()){
                updateLabel("All TextFields are required",false);
                return;
            }
            if(tf1.getText().isEmpty()){
                updateLabel("Enter Name",false);
                return;
            }
            if(tf2.getText().isEmpty()){
                updateLabel("Enter age",false);
                return;
            }
            String ageS = tf2.getText();
            if(Manager.isValidAge(ageS)==-2){
                updateLabel("Invalid age",false);
                return;
            }
            if(genderCbo.getValue()==null) {
                updateLabel("Select Gender", false);
                return;
            }
            if(districtCbo.getValue()==null){
                updateLabel("Select District",false);
                return;
            }
            if(locationCbo.getValue()==null){
                updateLabel("Select Location",false);
                return;
            }
            if(datePicker.getValue()==null){
                updateLabel("Pick a date",false);
                return;
            }
            short age = Manager.isValidAge(ageS);
            Martyr martyr = new Martyr(name,String.valueOf(datePicker.getValue()),age,String.valueOf(locationCbo.getValue()),String.valueOf(districtCbo.getValue()),genderCbo.getValue());
            DNode district = Manager.getDistrictTree().find(String.valueOf(districtCbo.getValue()));
            LNode location = district.getLocation().find(String.valueOf(locationCbo.getValue()));
            BSDateNode date = location.getDate().find(datePicker.getValue());
            if(date==null) {
                location.getDate().insert(datePicker.getValue());
                location.getDate().find(datePicker.getValue()).addMartyr(martyr);
            }else{
                if (date.getHead().contains(martyr)) {
                    updateLabel("Martyr already exists",false);
                    return;
                }
                date.addMartyr(martyr);
            }
            tf1.clear();
            tf2.clear();
            datePicker.setValue(null);
            updateLabel("Martyr added successfully",true);
        });
        MainScreen.getSidePane().setCenter(vBox);
    }
    public static void handleUpdate(){
        label.setText(null);
        TextField tf = new TextField();
        Style.style(tf);
        ComboBox<String> districtCbo = Style.getDistrictCbo();
        ComboBox<String> locationCbo = Style.getLocationCbo();
        DatePicker datePicker = Style.datePicker();
        GridPane gp1 = Style.checkMartyr(districtCbo,locationCbo,datePicker,tf);
        Button search = new Button("Search");
        Style.style(search);
        VBox vBox1 = Style.createVBox();
        MainScreen.getSidePane().setCenter(vBox1);
        Style.style(search);
        Button button = new Button("Fill Attributes");
        Text text = new Text("Select a Martyr from search table to fill the attributes");
        text.setStyle("-fx-background-color:white;-fx-font-family: 'Segoe Print';-fx-font-size:9;");

        button.setOnAction(e->{
            tf.setText(name);
            districtCbo.setValue(district);
            locationCbo.setValue(location);
            datePicker.setValue(Manager.localDate(date));
        });
        Style.style(button);
        vBox1.getChildren().addAll(gp1,button,search,text,label);
        districtCbo.setOnAction(e->{
            locationCbo.getItems().clear();
            ObservableList<String> list = Manager.getDistrictTree().collectLocations(districtCbo.getValue());
            locationCbo.getItems().addAll(list);

        });
        search.setOnAction(e->{
            if(tf.getText()==null){
                updateLabel("Enter Name",false);
                return;
            }
            if(districtCbo.getValue()==null){
                updateLabel("Select District",false);
                return;
            }
            if(locationCbo.getValue()==null){
                updateLabel("Select Location",false);
                return;
            }
            if(datePicker.getValue()==null){
                updateLabel("Pick a date",false);
                return;
            }
            String district = districtCbo.getValue();
            String location  = locationCbo.getValue();
            LocalDate date = datePicker.getValue();
            DNode dNode = Manager.getDistrictTree().find(district);
            LNode lNode = dNode.getLocation().find(location);

            BSDateNode dateNode1 =lNode.getDate().find(date);
            if(dateNode1!=null){
                Martyr martyr = dateNode1.martyr(dateNode1,tf.getText());
                if(martyr!=null) {
                    handleUpdates(martyr,dateNode1,lNode,dNode);
                }else{
                    updateLabel("Martyr doesn't exist",false);
                }

            }else{
                updateLabel("Date doesn't exist",false);
            }
        });
    }
    /*
    A method to handle updating the martyr, it takes district, location, date and the name of the martyr, to make sure it is right martyr
    that is updating.
     */
    public static void handleUpdates(Martyr martyr,BSDateNode dateNode,LNode locationNode, DNode districtNode){
        TextField tf1 = new TextField(); // text field
        TextField tf2 = new TextField(); // text field
        Style.style(tf1); // styling the text field
        Style.style(tf2); // styling the text field
        ComboBox<String> districtCbo = Style.getDistrictCbo();
        ComboBox<String> locationCbo = Style.getLocationCbo();
        ComboBox<Character> gender = Style.getGenderCbo();
        DatePicker datePicker1 = Style.datePicker();
        GridPane gp = Style.martyrInfo(tf1,tf2,gender,locationCbo,districtCbo,datePicker1);
        VBox vBox = Style.createVBox();
        Button button = new Button("Update");
        Style.style(button);
        Text text = new Text("Enter the following Information");
        text.setFont(Font.font("Arial",16));
        text.setStyle("-fx-font-family: 'Segoe Print';-fx-font-size:20;-fx-font-weight:bold;");
        text.setFill(Color.DARKRED);
        CheckBox[] cbo = new CheckBox[6];
        String[] label1 = {"Name","Age","Date","Gender","Location","District"};
        FlowPane checkBoxes = Style.checkBoxes(cbo,label1);
        cbo[4].setOnAction(e->{
            if(districtCbo.getValue()!=null) {
                locationCbo.getItems().clear();
                districtCbo.setValue(districtNode.getElement());
                ObservableList<String> list = Manager.getDistrictTree().collectLocations(districtCbo.getValue());
                locationCbo.getItems().addAll(list);
            }
        });
        cbo[5].setOnAction(e->{
            if(districtCbo.getValue()!=null) {
                ObservableList<String> list = Manager.getDistrictTree().collectLocations(districtCbo.getValue());
                locationCbo.getItems().addAll(list);
            }
        });

        button.setOnAction(e-> {
            if(!cbo[0].isSelected() && !cbo[1].isSelected() && !cbo[2].isSelected() && !cbo[3].isSelected()  && !cbo[4].isSelected() &&!cbo[5].isSelected()){
                updateLabel("You haven't updated anything.", false);
                return;
            }

            if (cbo[5].isSelected() && districtCbo.getValue()==null) {
                updateLabel("Please select district.", false);
                return;
            }

            if (cbo[5].isSelected() && districtCbo.getValue() != null && locationCbo.getValue() == null && datePicker1.getValue()==null) {
                updateLabel("Please select district, location and date.", false);
                return;
            }
            if (cbo[1].isSelected() && locationCbo.getValue() == null) {
                updateLabel("Please select both location.", false);
                return;
            }

            // Check if checkbox for Location is selected but ComboBox is null
            if (cbo[1].isSelected() && locationCbo.getValue() != null && datePicker1.getValue()==null) {
                updateLabel("Please select both location and date.", false);
                return;
            }

            // Check if checkbox for Date is selected but DatePicker is null
            if (cbo[2].isSelected() && datePicker1.getValue() == null) {
                updateLabel("Please select a Date.", false);
                return;
            }

            if (cbo[0].isSelected()) {
                if (!tf1.getText().isEmpty()) {
                    martyr.setName(tf1.getText());
                } else {
                    updateLabel("Enter Name", false);
                    return;
                }
            }
            if (cbo[1].isSelected()) {
                if (!tf2.getText().isEmpty()) {
                    martyr.setAge(Manager.isValidAge(tf2.getText()));
                } else {
                    updateLabel("Enter Age", false);
                    return;
                }
            }
            if (cbo[2].isSelected() ) {
                if(datePicker1.getValue() != null){
                    dateNode.getHead().remove(martyr.getName());
                    BSDateNode newDateNode = locationNode.getDate().find(datePicker1.getValue());
                    if(newDateNode!=null){
                        martyr.setDate(Manager.martyrDate(String.valueOf(datePicker1.getValue())));
                        newDateNode.getHead().add(martyr);
                    }else{
                        martyr.setDate(Manager.martyrDate(String.valueOf(datePicker1.getValue())));
                        locationNode.getDate().insert(datePicker1.getValue());
                        locationNode.getDate().find(datePicker1.getValue()).getHead().add(martyr);
                    }

                } else {
                    updateLabel("Enter date", false);
                    return;
                }
            }
            if(cbo[3].isSelected()) {
                if (gender.getValue() != null) {
                    martyr.setGender(gender.getValue());
                } else {
                    updateLabel("Select Gender", false);
                    return;
                }
            }
            if(cbo[4].isSelected()) {
                if (locationCbo.getValue() != null && datePicker1.getValue()!=null) {
                    dateNode.getHead().remove(martyr.getName());
                    LNode newLNode = districtNode.getLocation().find(locationCbo.getValue());
                    BSDateNode newDateNode = newLNode.getDate().find(datePicker1.getValue());
                    if(newDateNode!=null){
                        martyr.setDate(Manager.martyrDate(String.valueOf(datePicker1.getValue())));
                        newDateNode.getHead().add(martyr);
                    }else{
                        martyr.setDate(Manager.martyrDate(String.valueOf(datePicker1.getValue())));
                        locationNode.getDate().insert(datePicker1.getValue());
                        locationNode.getDate().find(datePicker1.getValue()).getHead().add(martyr);
                    }
                    martyr.setLocation(locationCbo.getValue());
                    martyr.setDate(Manager.martyrDate(String.valueOf(datePicker1.getValue())));
                } else {
                    updateLabel("Select both location and date", false);
                    return;
                }
            }

            if(cbo[5].isSelected()) {
                if (districtCbo.getValue() != null && locationCbo.getValue() != null && datePicker1.getValue()!=null ) {
                    dateNode.getHead().remove(martyr.getName());
                    LNode lNode1 =  Manager.getDistrictTree().find(districtCbo.getValue()).getLocation().find(locationCbo.getValue());
                    BSDateNode newDateNode = lNode1.getDate().find(datePicker1.getValue());
                    if(newDateNode!=null){
                        martyr.setDate(Manager.martyrDate(String.valueOf(datePicker1.getValue())));
                        newDateNode.getHead().add(martyr);
                    }else{
                        martyr.setDate(Manager.martyrDate(String.valueOf(datePicker1.getValue())));
                        lNode1.getDate().insert(datePicker1.getValue());
                        lNode1.getDate().find(datePicker1.getValue()).getHead().add(martyr);
                    }
                    martyr.setDistrict(districtCbo.getValue());
                    martyr.setLocation(locationCbo.getValue());
                    martyr.setDate(Manager.martyrDate(String.valueOf(datePicker1.getValue())));
                } else {
                    updateLabel("District, Location and Date are all required",false);
                }
            }
            if(dateNode.getHead().isEmpty()){
                locationNode.getDate().remove(dateNode.getElement());
            }
            handleUpdate();
            updateLabel("Martyr has been updated successfully",true);
        });

        vBox.getChildren().addAll(text,gp,button,checkBoxes, label);
        MainScreen.getSidePane().setCenter(vBox);
    }

    /*
    A method to handle deleting the martyr, it takes district, location, date and the name of the martyr, to make sure it is right martyr
    that is being deleted.
     */

    public static void handleDelete(){
        label.setText(null);
        VBox vBox = Style.createVBox();
        TextField tf = new TextField();
        Style.style(tf);
        ComboBox<String> districtCbo = Style.getDistrictCbo();
        ComboBox<String> locationCbo = Style.getLocationCbo();
        locationCbo.getItems().clear();
        DatePicker datePicker = Style.datePicker();
        Button button = new Button("Fill Attributes");
        button.setOnAction(e->{
            tf.setText(name);
            districtCbo.setValue(district);
            locationCbo.setValue(location);
            datePicker.setValue(Manager.localDate(date));
        });
        Style.style(button);
        Text text = new Text("Select a Martyr from search table to fill the attributes");
        text.setStyle("-fx-background-color:white;-fx-font-family: 'Segoe Print';-fx-font-size:9;");


        GridPane gp = Style.checkMartyr(districtCbo, locationCbo,datePicker,tf);
        Button delete = new Button("Delete");
        Style.style(delete);
        vBox.getChildren().addAll(gp,button,delete,text,label);
        districtCbo.setOnAction(e->{
            locationCbo.getItems().clear();
            ObservableList<String> list = Manager.getDistrictTree().collectLocations(districtCbo.getValue());
            locationCbo.getItems().addAll(list);
        });

        delete.setOnAction(e->{
            String s = "Are you sure you want to delete this item" + "\name: " + tf.getText() + "\nLocation: " + locationCbo.getValue() + "\nDistrict: " + districtCbo.getValue() + "\nDate: " + datePicker.getValue();
            String dString = String.valueOf(districtCbo.getValue());
            DNode district = Manager.getDistrictTree().find(dString);
            if(!tf.getText().isEmpty()) {
                if (dString != null) {
                    String lString = String.valueOf(locationCbo.getValue());
                    if (lString != null) {
                        LNode location = district.getLocation().find(lString);
                        if (datePicker.getValue() != null) {
                            BSDateNode date = location.getDate().find(datePicker.getValue());
                            if(date!=null) {
                                boolean result = Style.showConfirmation(s);
                                if (result) {
                                    if (date.getHead().remove(tf.getText())){
                                        updateLabel("Martyr has been removed successfully", true);
                                        if(date.getHead().isEmpty()){
                                            location.getDate().remove(date.getElement());
                                        }
                                    }else{
                                        updateLabel("Martyr doesn't exist", false);
                                    }
                                }else {
                                    updateLabel("Martyr doesn't exist", false);
                                }
                            }else {
                                updateLabel("date doesn't exist", false);
                            }
                        }else{
                            updateLabel("Select date please", false);
                        }
                    } else {
                        updateLabel("Select location please", false);
                    }
                } else {
                    updateLabel("Select district please", false);
                }

            }else{
                updateLabel("Enter a name please", false);

            }
        });
        MainScreen.getSidePane().setCenter(vBox);
    }

    private static void styleLabel(Label label){
        label.setTextFill(Color.DARKRED);
        label.setStyle("-fx-font-family: 'Segoe Print';-fx-font-size:15;-fx-background-color:white;");

    }

    /*
    A method that handles navigation, it uses two stacks that are ordered in order, meaning from smallest to largest, clicking on next
    would result to popping the element into a secondry stack, and clicking on previous would result to poping the element to the original stakc.
     */

    public static void handleNavigate(StackList stack,StackList locations){
        label.setText(null);
        VBox vBox = Style.createVBox();
        Label label1 = new Label("Date");
        Label label2 = new Label();
        Label label3 = new Label();
        Label label4 = new Label();
        styleLabel(label1);
        styleLabel(label2);
        styleLabel(label3);
        styleLabel(label4);
        label2.setFont(Font.font("Arial",FontWeight.SEMI_BOLD,15));
        label3.setFont(Font.font("Arial",FontWeight.SEMI_BOLD,15));
        label4.setFont(Font.font("Arial",FontWeight.SEMI_BOLD,15));
        label1.setFont(Font.font("Arial",FontWeight.SEMI_BOLD,20));
        Button prev = Style.createLeftTriangleButton();
        Button next = Style.createRightTriangleButton();
        prev.setPrefSize(45,45);
        next.setPrefSize(45,45);
        HBox hb = Style.createHBox();
        hb.getChildren().addAll(prev,label1,next);
        ObservableList observableList = FXCollections.observableArrayList();
        Tab tab = new Tab("Martyr Table: " + ++counter);
        MartyrTableView martyrTableView = new MartyrTableView();
        tab.setContent(martyrTableView);
        martyrTableView.setItems(observableList);
        MainScreen.getTabPane().getTabs().add(tab);
        MainScreen.getTabPane().getSelectionModel().select(tab);
        if(!stack.isEmpty()) {
            StackList previous = new StackList();
            BSDateNode dateNode1 = ((BSDateNode) stack.pop());
            label1.setText(dateNode1.getElement() + "");
            label1.setStyle("-fx-font-family: 'Segoe Print';-fx-font-size:15;-fx-background-color:white;");
            label2.setText("The Youngest Martyr: " + dateNode1.youngest(dateNode1));
            label3.setText("The Oldest Martyr: " + dateNode1.oldest(dateNode1) );
            label4.setText("The Average Age: " + dateNode1.averageAge(dateNode1));
            previous.push(dateNode1);
            observableList.addAll(dateNode1.sortedMartyrs(dateNode1));
            next.setOnAction(e -> {
                observableList.clear();
                BSDateNode  dateNode = ((BSDateNode) stack.pop());
                if(dateNode!=null) {
                    label1.setText(dateNode.getElement() + "");
                    if(!dateNode.getHead().isEmpty()) {
                        label2.setText("The Youngest Martyr: " + dateNode.youngest(dateNode));
                        label3.setText("The Oldest Martyr: " + dateNode.oldest(dateNode));
                        label4.setText("The Average Age: " + dateNode.averageAge(dateNode));
                        observableList.addAll(dateNode.sortedMartyrs(dateNode));
                    }else{
                        label2.setText("The Youngest Martyr: " + 0);
                        label3.setText("The Oldest Martyr: " + 0);
                        label4.setText("The Average Age: " + 0);

                    }
                    previous.push(dateNode);
                }
            });
            prev.setOnAction(e -> {

                observableList.clear();
                BSDateNode dateNode = ((BSDateNode) previous.pop());

                if(dateNode!=null) {
                    label1.setText(dateNode.getElement() + "");
                    if(!dateNode.getHead().isEmpty()) {
                        label2.setText("The Youngest Martyr: " + dateNode.youngest(dateNode));
                        label3.setText("The Oldest Martyr: " + dateNode.oldest(dateNode));
                        label4.setText("The Average Age: " + dateNode.averageAge(dateNode));
                        observableList.addAll(dateNode.sortedMartyrs(dateNode));
                    }else{
                        label2.setText("The Youngest Martyr: " + 0);
                        label3.setText("The Oldest Martyr: " + 0);
                        label4.setText("The Average Age: " + 0);

                    }
                    stack.push(dateNode);
                }
            });
        }
        Button back = new Button("Back");
        back.setOnAction(e->{
                LocationMenu.handleNavigate(locations);
        });
        Style.style(back);
        vBox.getChildren().addAll(hb,back,label2,label4,label3);
        MainScreen.getSidePane().setCenter(vBox);

    }

    public void handleSearch(){ // a search method to search the martyrs in all districts and locations, there are 5 cases
        label.setText(null);
        Button button = new Button("Search");
        VBox vBox = Style.createVBox();
        TextField tf = new TextField();
        Style.style(tf);
        tf.setPromptText("Enter Name..");
        tf.setPrefWidth(200);
        ComboBox<String> districtCbo = Style.getDistrictCbo();
        ComboBox<String> locationCbo = Style.getLocationCbo();
        DatePicker datePicker = Style.datePicker();
        String[] label2 = {"District known","Location known","Date known"};
        CheckBox[] cbo = new CheckBox[3];

        districtCbo.setOnAction(e->{
            ObservableList<String> list = Manager.getDistrictTree().collectLocations(districtCbo.getValue());
            locationCbo.getItems().addAll(list);
        });
        FlowPane checkBoxes = Style.checkBoxes(cbo, label2);
        GridPane gp = Style.checkMartyr(districtCbo,locationCbo,datePicker,tf);
        vBox.getChildren().addAll(gp,checkBoxes,button,label);
        TextField locationTF = new TextField();
        HBox hb = Style.createHBox();
        Label label1 = Style.styleLabel("Location known");
        locationTF.setMaxWidth(150);
        locationTF.setPromptText("Location known");
        Style.style(locationTF);
        hb.getChildren().addAll(label1,locationTF);
        vBox.getChildren().add(1,hb);

        Style.style(button);
        button.setOnAction(e->{
            Style.getMartyrObList().clear();

            if(!cbo[0].isSelected() && !cbo[1].isSelected() && !cbo[2].isSelected()){
                Style.getMartyrObList().addAll(Manager.getDistrictTree().searchMartyr(tf.getText()));
            }

            if (cbo[0].isSelected() && districtCbo.getValue() == null) {
                updateLabel("Please select a District.", false);
                return;
            }

            // Check if checkbox for Location is selected but ComboBox is null
            if (cbo[1].isSelected() && locationCbo.getValue() == null) {
                updateLabel("Please select a Location.", false);
                return;
            }

            // Check if checkbox for Date is selected but DatePicker is null
            if (cbo[2].isSelected() && datePicker.getValue() == null) {
                updateLabel("Please select a Date.", false);
                return;
            }

            BSDateNode dateNode;
            DNode dnode;
            if(cbo[0].isSelected() && districtCbo.getValue()!=null){
                dnode = Manager.getDistrictTree().find(districtCbo.getValue());
                if (cbo[1].isSelected() && locationCbo.getValue()!=null) {
                    LNode lNode = dnode.getLocation().find(locationCbo.getValue());
                    if(cbo[2].isSelected()&& datePicker.getValue()!=null){
                        dateNode = lNode.getDate().find(datePicker.getValue());
                        ObservableList<Martyr> dateMartyrs = lNode.getDate().collectMartyrsByDate(dateNode);
                        Style.getMartyrObList().addAll(dateMartyrs);
                        return;
                    }
                    ObservableList<Martyr> locationMartyrs = dnode.getLocation().searchMartyr(lNode,tf.getText());
                    Style.getMartyrObList().addAll(locationMartyrs);
                    return;
                }
                ObservableList<Martyr> districtMartyrs = Manager.getDistrictTree().searchMartyr(districtCbo.getValue(),tf.getText());
                Style.getMartyrObList().addAll(districtMartyrs);
                return;
            }
            if(cbo[1].isSelected() && !locationTF.getText().isEmpty()){
                LNode lNode = Manager.getDistrictTree().findLocationNode(locationTF.getText());
                if(lNode!=null) {
                    ObservableList<Martyr> locationMartyrs = lNode.searchMartyr(lNode, tf.getText());
                    Style.getMartyrObList().addAll(locationMartyrs);
                }else{
                    updateLabel("Location is not found",false);
                }
            }

            if(cbo[2].isSelected() && datePicker.getValue()!=null){
                ObservableList<Martyr> dateMartyrs = Manager.getDistrictTree().collectMartyrsByDate(datePicker.getValue(),tf.getText());
                Style.getMartyrObList().addAll(dateMartyrs);
            }

        });
        MainScreen.getSidePane().setCenter(vBox);
    }

}
