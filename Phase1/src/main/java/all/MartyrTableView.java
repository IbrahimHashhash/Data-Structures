package all;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

class MartyrTableView { // a table view class to display all the martyrs in the list
    private static final TableView tableView = new TableView(); // private object of table view

    public MartyrTableView() { // public constructor
        initialize();
    }

    public static javafx.scene.Node initialize() {
        tableView.setItems(Manager.getMartyrs());
        // creates table view columns
        TableColumn<Martyr, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name")); // name
        nameColumn.setPrefWidth(250);

        TableColumn<Martyr, String> locationColumn = new TableColumn<>("Location");
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location")); // location
        locationColumn.setPrefWidth(200);
        TableColumn<Martyr, String> districtColumn = new TableColumn<>("District");
        districtColumn.setCellValueFactory(new PropertyValueFactory<>("district")); // district
        districtColumn.setPrefWidth(200);
        TableColumn<Martyr, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date")); // date
        dateColumn.setPrefWidth(200);
        TableColumn<Martyr, Byte> ageColumn = new TableColumn<>("Age");
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age")); // age

        TableColumn<Martyr, Character> genderColumn = new TableColumn<>("Gender");
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender")); // gender

        // adds the columns to table view
        tableView.getColumns().addAll(nameColumn, locationColumn, districtColumn, dateColumn, ageColumn, genderColumn);

        return tableView;
    }

    public static TableView<Martyr> getTableView() {
        return tableView;
    }
}
