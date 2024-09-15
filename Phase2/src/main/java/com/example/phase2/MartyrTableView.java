package com.example.phase2;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;

class MartyrTableView extends TableView<Martyr>{ // a table view class to display all the martyrs in the list
    public MartyrTableView() {
        this(Style.getMartyrObList());
    } // construct that takes the main martyr observableList

    public MartyrTableView(ObservableList<Martyr> items) { // constructor that takes a custom martyr list
        super(items); // calls the super constructor
        initializeColumns(); // initializes the columns
    }

    private void initializeColumns() {
        // initializes table view's columns
        this.setStyle("-fx-text-fill: darkred; -fx-font-family: 'Segoe Print';-fx-font-size:12;");
        TableColumn<Martyr, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setPrefWidth(340);
        // location column
        TableColumn<Martyr, String> locationColumn = new TableColumn<>("Location");
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        locationColumn.setPrefWidth(160);
        // district column
        TableColumn<Martyr, String> districtColumn = new TableColumn<>("District");
        districtColumn.setCellValueFactory(new PropertyValueFactory<>("district"));
        districtColumn.setPrefWidth(160);
        // date column
        TableColumn<Martyr, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        dateColumn.setPrefWidth(100);
        // age column
        TableColumn<Martyr, Byte> ageColumn = new TableColumn<>("Age");
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        ageColumn.setPrefWidth(65);
        // gender column
        TableColumn<Martyr, Character> genderColumn = new TableColumn<>("Gender");
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        this.getColumns().addAll(nameColumn, locationColumn, districtColumn, dateColumn, ageColumn, genderColumn);
    }

}


