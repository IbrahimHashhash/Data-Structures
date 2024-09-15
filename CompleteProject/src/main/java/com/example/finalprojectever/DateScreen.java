package com.example.finalprojectever;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DateScreen extends Tab {
    private static final Label label = new Label();
    private final BorderPane borderPane = new BorderPane();
    private final VBox vBox = Style.createVBox();
    private final MartyrTableView martyrTableView = new MartyrTableView();
    private static  HashEntry hash;


    public DateScreen(){
        borderPane.setStyle("-fx-background-color:lightSkyBlue;");
        SplitPane splitPane = new SplitPane();
        martyrTableView.getItems().addAll(Style.getMartyrObList());
        initialize();
        VBox searching = new VBox();
        searching.setAlignment(Pos.CENTER);
        TextField search = new TextField();
        Button enter = new Button();
        enter.setOnAction(e->{
            martyrTableView.getItems().clear();
            ObservableList<Martyr> martyrs = Manager.hashMap.searchAll(search.getText());
            martyrTableView.getItems().addAll(martyrs);
        });
        HBox hBox = MainScreen.SearchBar(search,enter);
        searching.getChildren().addAll(martyrTableView,hBox);
        splitPane.getItems().addAll(searching,borderPane);
        VBox.setVgrow(martyrTableView, Priority.ALWAYS);
        this.setContent(splitPane);
        this.setClosable(false);
        splitPane.setDividerPositions(0.73); // sets the split pane divider at position 70%
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
                    LocalDate localDate = Manager.localDate(martyr.getDate());
                    HashEntry hashEntry = Manager.hashMap.findDate(localDate);
                    new UpdateMartyr(martyr,hashEntry);
                    Style.showWarning("You are updating " + martyr.getName());
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
                        LocalDate localDate = Manager.localDate(martyr.getDate());
                        String martyrName = martyr.getName();
                        Manager.hashMap.findDate(localDate).getTree().delete(martyr);
                        Style.showWarning(martyrName + " has been deleted successfully");

                    }
                }catch (NullPointerException ex){
                    System.out.println(ex.toString());
                }
            }
        });

    }
    public void initialize(){
        this.setText("Date Screen");
        HBox saveBox = new HBox();
        saveBox.setSpacing(20);
        saveBox.setPadding(new Insets(10,10,10,10));

        Button bt1 = new Button("Insert");
        Button bt2 = new Button("Update");
        Button bt3 = new Button("Delete");
        Button bt4 = new Button("Navigate");
        Button bt5 = new Button("Back");
        Button bt6 = new Button("Save");
        Button bt7 = new Button("Save AS");
        Button bt8 = new Button("Print");
        Button bt9 = new Button("Search");

        bt1.setPrefSize(150,60);
        bt2.setPrefSize(150,60);
        bt3.setPrefSize(150,60);
        bt4.setPrefSize(150,60);
        bt5.setPrefSize(70,25);
        bt6.setPrefSize(70,25);
        bt7.setPrefSize(70,25);
        bt8.setPrefSize(150,60);
        bt9.setPrefSize(150,60);
        Style.style(bt9);

        Style.style(bt8);
        Style.style(bt7);
        Style.style(bt6);
        Style.style(bt1);
        Style.style(bt2);
        Style.style(bt3);
        Style.style(bt4);
        Style.style(bt5);
        HBox hBox = Style.createHBox();
        hBox.getChildren().addAll(bt5);
        hBox.setPadding(new Insets(10,10,10,10));
        vBox.getChildren().addAll(bt1,bt2,bt3,bt4,bt8,bt9);
        saveBox.getChildren().addAll(bt6,bt7);
        borderPane.setTop(saveBox);
        borderPane.setCenter(vBox);
        borderPane.setBottom(hBox);
        bt1.setOnAction(e-> handleInsert());

        bt2.setOnAction(e-> handleUpdate());

        bt3.setOnAction(e-> handleDelete());

        bt4.setOnAction(e-> handleNavigate());

        bt5.setOnAction(e-> borderPane.setCenter(vBox));

        bt6.setOnAction(e-> Manager.saveAll());

        bt7.setOnAction(e-> Manager.handleSaveAs());
        bt9.setOnAction(e->handleSearchDate());
        TextArea ta = new TextArea();
        ta.setEditable(false);
        ta.setStyle("-fx-font-size:17px;-fx-font-family: 'Segoe UI';-fx-font-weight:bold;-fx-border-color:black;-fx-control-inner-background:white;-fx-text-fill:black;");
        ta.setMaxWidth(320);
        Button include = new Button("Include Empty");
        Button exclude = new Button("Exclude Empty");
        include.setPrefSize(150,60);
        exclude.setPrefSize(150,60);

        Style.style(include);
        Style.style(exclude);
        VBox includeExclude = Style.createVBox();
        includeExclude.getChildren().addAll(include,exclude);
        bt8.setOnAction(e-> {
            borderPane.setCenter(includeExclude);
        });
        include.setOnAction(e->{
            ta.clear();
            ta.setText(Manager.hashMap.print(true));
            borderPane.setCenter(ta);
        });
        exclude.setOnAction(e->{
            ta.clear();
            ta.setText(Manager.hashMap.print(false));
            borderPane.setCenter(ta);
        });

    }
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

    public void handleInsert(){
        label.setText(null);
        DatePicker datePicker = Style.datePicker();
        Button button = new Button("Insert");
        Style.style(button);
        GridPane gridPane = Style.createGridPane();
        gridPane.add(Style.styleLabel("Date"),0,0);
        gridPane.add(datePicker,1,0);
        VBox vBox = Style.createVBox();
        vBox.getChildren().addAll(gridPane,button,label);
        button.setOnAction(e->{
            if(datePicker.getValue()==null){
                updateLabel("Pick a date",false);
                return;
            }
            if (datePicker.getValue()!=null){
                LocalDate date = datePicker.getValue();

                LocalDateTime currentDateTime = LocalDateTime.now();

                LocalDateTime targetDateTime = LocalDateTime.of(date, LocalTime.MIDNIGHT);

                if (targetDateTime.isAfter(currentDateTime)) {
                    updateLabel("Error: The date you picked is in the future",false);
                    return;
                }

            }

            if(Manager.hashMap.findDate(datePicker.getValue()) ==null) {
                Manager.hashMap.insert(datePicker.getValue());
                updateLabel("Date has been added successfully", true);
            }else{
                updateLabel("Date already exists", false);
            }

        });

        borderPane.setCenter(vBox);

    }
    public void handleUpdate(){ // handles update
        label.setText(null);
        DatePicker datePicker = Style.datePicker();
        DatePicker newDatePicker = Style.datePicker();
        Button button = new Button("update");
        Style.style(button);
        VBox vBox = Style.createVBox();
        GridPane gridPane = Style.createGridPane();
        gridPane.add(Style.styleLabel("Date"),0,0);
        gridPane.add(datePicker,1,0);
        gridPane.add(Style.styleLabel("new Date"),0,1);
        gridPane.add(newDatePicker,1,1);


        vBox.getChildren().addAll(gridPane,button,label);
        button.setOnAction(e->{
            if(datePicker.getValue()==null){
                updateLabel("Pick a date",false);
                return;
            }
            if (newDatePicker.getValue()!=null){
                LocalDate date = newDatePicker.getValue();

                LocalDateTime currentDateTime = LocalDateTime.now();

                LocalDateTime targetDateTime = LocalDateTime.of(date, LocalTime.MIDNIGHT);

                if (targetDateTime.isAfter(currentDateTime)) {
                    updateLabel("Error: The date you picked is in the future",false);
                    return;
                }

            }
            if(newDatePicker.getValue()==null){
                updateLabel("Pick a date",false);
                return;
            }
            if(Manager.hashMap.findDate(newDatePicker.getValue())!=null){
                updateLabel("Date already exists",false);
                return;
            }

            HashEntry date = Manager.hashMap.findDate(datePicker.getValue());

            if(date !=null) {
                boolean result = Style.showConfirmation("Are you sure want to update this date: " + datePicker.getValue()
                        + "\nTotal Number of Martyrs: " + date.getTree().getTotal()  + "\nTotal Number of Males: "  + date.getTree().getMale()+ "\nTotal Number of Females: " + date.getTree().getFemale() + "\nAverage age of total Martyrs: " + date.getTree().getMale());
                if(result){
                    date.setValue(newDatePicker.getValue());
                    updateLabel("Date has been updated successfully", true);
                    date.getTree().updateDate(Manager.martyrDate(String.valueOf(newDatePicker.getValue())));
                }

            }else{
                updateLabel("Date doesn't exist", false);
            }

        });
        borderPane.setCenter(vBox);

    }

    public void handleDelete(){ // a method to handle delete
        label.setText(null);
        DatePicker datePicker = Style.datePicker();
        Button button = new Button("Delete");
        Style.style(button);
        VBox vBox = Style.createVBox();
        GridPane gridPane = Style.createGridPane();
        gridPane.add(Style.styleLabel("Date"),0,0);
        gridPane.add(datePicker,1,0);
        vBox.getChildren().addAll(gridPane,button,label);
        button.setOnAction(e->{
            if(datePicker.getValue()==null){
                updateLabel("Pick a date",false);
                return;
            }
            HashEntry date = Manager.hashMap.findDate(datePicker.getValue()); // finds the date to ensure it exists
            if (date !=null) { // if not null
                boolean result = Style.showConfirmation("Are you sure want to delete this date: " + datePicker.getValue() // confirmation
                + "\nTotal Number of Martyrs: " + date.getTree().getTotal()  + "\nTotal Number of Males: "  + date.getTree().getMale()+ "\nTotal Number of Females: " + date.getTree().getFemale() + "\nAverage age of total Martyrs: " + date.getTree().getMale());
                if(result){ // if
                Manager.hashMap.delete(datePicker.getValue());
                updateLabel("Date has been deleted successfully", true);
                }
            }else{
                updateLabel("Date is not found", false);

            }


        });
        borderPane.setCenter(vBox);

    }
    public void handleNavigate() { // a method to handle the navigation of the dates, it navigates using two stacks, one for next and the other for previous, it pops and pushes
        Button left = Style.createLeftTriangleButton();
        Button right = Style.createRightTriangleButton();
        Style.style(left);
        Style.style(right);
        HBox hBox = Style.createHBox();
        left.setPrefSize(50, 50);
        right.setPrefSize(50, 50);
        StackList next = Manager.hashMap.getList();
        StackList prev = new StackList();
        hash = (HashEntry) next.peek();
        Label label1 = new Label(hash.getValue() + " ");
        label1.setStyle("-fx-font-family: 'Segoe Print';-fx-font-size:20;-fx-background-color:white;");
        hBox.getChildren().addAll(left,label1,right);
        Label[] labels = new Label[8];
        labels[0] = Style.styleLabel("Total Martyrs:   " + hash.getTree().getTotal());
        labels[1] = Style.styleLabel("Total Males:   " + hash.getTree().getFemale());
        labels[2] = Style.styleLabel("Total Females:   " + hash.getTree().getMale());
        labels[3] = Style.styleLabel("Average Age:   " + hash.getTree().getAverageAge());
        labels[4] = Style.styleLabel(""); // Placeholder for Max Location
        labels[5] = Style.styleLabel(""); // Placeholder for Max District
        labels[6] = Style.styleLabel("Youngest Martyr:   " + hash.getTree().minAge());
        labels[7] = Style.styleLabel("Oldest Martyr:   " + hash.getTree().maxAge());

        if (!hash.getTree().isEmpty()) {
            labels[4].setText("Max Location:   " + hash.getTree().findMaxLocation());
            labels[5].setText("Max District:   "+ hash.getTree().findMaxDistrict() );
        } else {
            labels[4].setText("Max Location:   Unknown");
            labels[5].setText("Max District:   Unknown");
        }

        VBox lBox = new VBox(); // Create an HBox to hold the labels
        lBox.getChildren().addAll(labels);
        lBox.setSpacing(5);
        lBox.setAlignment(Pos.CENTER);
        VBox vBox = Style.createVBox();
        vBox.setSpacing(18);
        lBox.setStyle("-fx-background-color:white;-fx-border-color:black;-fx-border-width:1px;-fx-background-radius: 50; -fx-border-radius: 50;");
        borderPane.setCenter(vBox);
        Button load = new Button("Load");
        vBox.getChildren().addAll(hBox,load, lBox);
        load.setMaxWidth(80);

            left.setOnAction(e -> {
                hash = (HashEntry) prev.pop();
                if (hash == null) {
                    while (!next.isEmpty()) {
                        prev.push(next.pop());
                    }
                    hash = (HashEntry) prev.pop();
                }
                label1.setText(hash.getValue() + "");
                updateLabels(labels, hash);
                next.push(hash);
            });
            right.setOnAction(e -> {
                hash = (HashEntry) next.pop();
                if (hash == null) {
                    while (!prev.isEmpty()) {
                        next.push(prev.pop());
                    }
                    hash = (HashEntry) next.pop();
                }
                label1.setText(hash.getValue() + "");
                updateLabels(labels, hash);
                prev.push(hash);
            });
            Style.style(load);
            load.setOnAction(e -> {
                MartyrScreen martyrScreen = new MartyrScreen(hash);
                martyrScreen.setStyle("-fx-background-color:lightSkyBlue;");
                MainScreen.getTabPane().getTabs().add(martyrScreen);
                MainScreen.getTabPane().getSelectionModel().select(martyrScreen);
            });
    }

    private void updateLabels(Label[] labels, HashEntry hash) {
        // Update the labels with new values from the hash entry
        labels[0].setText("Total Martyrs:   " + hash.getTree().getTotal());
        labels[1].setText("Total Males:   " + hash.getTree().getMale());
        labels[2].setText("Total Females:   " + hash.getTree().getFemale());
        labels[3].setText("Average Age:   " + hash.getTree().getAverageAge());
        if (!hash.getTree().isEmpty()) {
            labels[4].setText("Max Location:   " + hash.getTree().findMaxLocation());
            labels[5].setText("Max District:   "+ hash.getTree().findMaxDistrict() );
        } else {
            labels[4].setText("Max Location:   Unknown");
            labels[5].setText("Max District:   Unknown");
        }
        labels[6].setText("Youngest Martyr:   " + hash.getTree().minAge());
        labels[7].setText("Oldest Martyr:   " + hash.getTree().maxAge());

    }
    public void handleSearchDate(){
        label.setText(null);
        VBox vBox = Style.createVBox();
        DatePicker datePicker = Style.datePicker();
        Button button = new Button("Search");
        Style.style(button);
        vBox.getChildren().addAll(datePicker,button,label);
        button.setOnAction(e->{
            if(datePicker.getValue()==null){
                updateLabel("Enter Date",false);
                return;
            }
           HashEntry hash = Manager.hashMap.findDate(datePicker.getValue());
            if(hash!=null){
                Button load = new Button("Load");
                Style.style(load);
                vBox.getChildren().remove(button);
                vBox.getChildren().add(1,load);
                label.setText(null);
                load.setOnAction(e1->{
                    MartyrScreen martyrScreen = new MartyrScreen(hash);
                    martyrScreen.setStyle("-fx-background-color:lightSkyBlue;");
                    MainScreen.getTabPane().getTabs().add(martyrScreen);
                    MainScreen.getTabPane().getSelectionModel().select(martyrScreen);
                    vBox.getChildren().remove(load);
                    vBox.getChildren().add(1,button);
                });
            }else{
                updateLabel("Date is not found",false);
            }

        });
        borderPane.setCenter(vBox);
    }
}
