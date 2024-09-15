package all;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

// class representing a location menu
public class LocationMenu extends Menu {
    // array to hold menu items
    private MenuItem[] items;
    // comboBox for selecting gender
    static ComboBox<Character> genderComboBox = new ComboBox<>();
    // label to display errors
    private static final Label errorLabel = new Label();
    // counter variable
    private static int counter = 0;

    // constructor for LocationMenu
    public LocationMenu() {
        // Call to the superclass constructor with "Location" as the title
        super("Location");
        // Initialize the items array with 8 elements
        items = new MenuItem[8];
        // Array of operation names
        String[] operations = {"Insert Location", "Update Location", "Delete Location", "Navigate", "Show Statistics", "Insert New Martyr","Update/Delete Martyr","Search Martyr"};
        // Loop to create menu items for each operation
        for (int i = 0; i < items.length; i++) {
            // Create a new MenuItem with the operation name
            items[i] = new MenuItem(operations[i]);
            // Add the MenuItem to this menu
            this.getItems().add(items[i]);
        }
        // Add 'M', 'F', and 'N' options to the genderComboBox
        genderComboBox.getItems().addAll('M','F','N');
        // Set the font for the errorLabel
        errorLabel.setFont(Font.font("Arial",FontWeight.SEMI_BOLD,14));
        // Initialize the handlers for the menu items
        initializeHandlers();
    }

    // Method to initialize handlers for the menu items
    private void initializeHandlers() {
        // set the action for the "Insert" menu item
        items[0].setOnAction(e -> handleInsert());
        // set the action for the "Update" menu item
        items[1].setOnAction(e -> handleUpdate());
        // set the action for the "Delete" menu item
        items[2].setOnAction(e -> handleDelete());
        // set the action for the "Navigate" menu item
        items[3].setOnAction(e -> handleNavigate());
        // set the action for the "Show Statistics" menu item
        items[4].setOnAction(e -> handleStatistics());
        // set the action for the "Insert New Martyr" menu item
        items[5].setOnAction(e -> insertMartyr());
        // set the action for the "Update/Delete Martyr" menu item
        items[6].setOnAction(e -> updateMartyr());
        // set the action for the "Search Martyr" menu item
        items[7].setOnAction(e -> handleSearch());
    }
    public void handleInsert(){ // A method to insert a martyr
        items[0].setOnAction(e -> { // event handler
            errorLabel.setText(null); // sets the error label to null
            GridPane gp = new GridPane();
            gp.setHgap(10);
            gp.setVgap(40);
            gp.setAlignment(Pos.CENTER);
            TextField tf = new TextField();
            tf.setPromptText("Enter Location..");
            gp.add(Style.createLabel("District"), 0, 0);
            gp.add(Manager.getDistrictComboBox(), 1, 0);
            gp.add(Style.createLabel("Location"), 0, 1);
            gp.add(tf, 1, 1);
            Button bt1 = new Button("Add");
            gp.add(bt1, 0, 2);
            bt1.setOnAction(e1->{

            if(Manager.getDistrictComboBox().getValue()==null){
                errorLabel.setText("Select District");
                return;
            }
            if(tf.getText().isEmpty()){
                errorLabel.setText("Textfield is required");
                return;
            }
            if(Manager.getDistricts().getLocations(tf.getText())!=null){
                errorLabel.setText("Location already Exists");
                return;
            }
            // calls the district list from the manager class, then adds the location to the district
            Manager.getDistricts().addList(Manager.getDistrictComboBox().getValue(),new Location(tf.getText()));
            errorLabel.setText("Location has been added successfully");
            tf.clear();
            });

            VBox vBox = Style.createVBox();
            vBox.getChildren().addAll(gp,errorLabel);
            MainScreen.getSp().setCenter(vBox);
        });
    }

        public void handleUpdate(){ // a method to update the location by changing its name only
                errorLabel.setText(null);
                // handles the grid pane
                GridPane gp = new GridPane();
                gp.setHgap(10);
                gp.setVgap(40);
                gp.setAlignment(Pos.CENTER);
                TextField tf1 = new TextField();
                tf1.setPromptText("Enter current Location..");
                TextField tf2 = new TextField();
                tf2.setPromptText("Enter new Location..");
                gp.add(Style.createLabel("Current Location"), 0, 0);
                gp.add(tf1, 1, 0);
                gp.add(Style.createLabel("District"), 0, 1);
                gp.add(Manager.getDistrictComboBox(), 1, 1);
                gp.add(Style.createLabel("New Location"), 0, 2);
                gp.add(tf2, 1, 2);

            Button bt1 = new Button("Update"); // button used to update
                gp.add(bt1, 0, 3);
                VBox vBox = Style.createVBox();
                vBox.getChildren().addAll(gp,errorLabel); // adds the nodes to the vBox
                MainScreen.getSp().setCenter(vBox);
                bt1.setOnAction(e->{
                    if(tf1.getText().isEmpty() || tf2.getText().isEmpty()){
                        errorLabel.setText("All TextFields are required");
                        return;
                    }
                    if(Manager.getDistrictComboBox().getValue()==null){
                        errorLabel.setText("select district");
                        return;
                    }

                    Location location1 = new Location(tf1.getText()); // creates a location object
                    District district = Manager.getDistrictComboBox().getValue();
                    int index = Manager.getDistricts().lastIndexOf(district); // gets the index of the district
                    DistrictList.Node current = Manager.getDistricts().getDistrictNode(index); // creates a node object that references to a specific node
                    int j = current.getHead().lastIndexOf(location1); // the index of location
                    if(j!=-1) {
                        if(Manager.getDistricts().getLocations(tf2.getText().trim())!=null){
                            errorLabel.setText("You can't use this name, because it already exists");
                            return;
                        }
                        LocationNode locationNode = Manager.getDistricts().getLocationNode(Manager.getDistrictComboBox().getValue(), new Location(tf1.getText().trim()));
                        MartyrNode martyrNode = locationNode.getHead().getFront();
                        while (martyrNode!=null) {
                            ((Martyr) martyrNode.getElement()).setLocation(tf2.getText());
                            martyrNode = martyrNode.getNext();
                        }
                        current.getHead().remove(j);
                        ((Location) locationNode.getElement()).setLocationName(tf2.getText());// sets the location name
                        current.getHead().addNode(locationNode,current.getHead().size());
                        MartyrTableView.getTableView().refresh();
                        errorLabel.setText("Location updated successfully");
                        tf1.clear();
                        tf2.clear();
                    }else{
                        errorLabel.setText("Location doesn't Exist");
                    }

                });

        }
        public void handleDelete(){ // A method to delete a location
                errorLabel.setText(null);
                GridPane gp = new GridPane();
                gp.setHgap(10);
                gp.setVgap(40);
                gp.setAlignment(Pos.CENTER);
                gp.add(Style.createLabel("District"), 0, 0);
                TextField tf2 = new TextField();
                tf2.setPromptText("Enter Location..");
                gp.add(Manager.getDistrictComboBox(), 1, 0);
                CheckBox cb = new CheckBox("Known");
                gp.add(cb,2,0);
                gp.add(Style.createLabel("Location"), 0, 1);
                gp.add(tf2, 1, 1);
                Button bt1 = new Button("Delete");
                gp.add(bt1, 0, 2);
                VBox vBox = Style.createVBox();
                vBox.getChildren().addAll(gp,errorLabel);
                bt1.setOnAction(e->{
                    if(tf2.getText().isEmpty()){
                        errorLabel.setText("TextField is required");
                        return;
                    }
                    if(cb.isSelected()) {
                        if(Manager.getDistrictComboBox().getValue()==null){
                            errorLabel.setText("Select Distirct");
                            return;
                        }
                        int index = Manager.getDistricts().lastIndexOf(Manager.getDistrictComboBox().getValue()); // gets the index of the district
                        DistrictList.Node current = Manager.getDistricts().getDistrictNode(index); // gets the node of the district by index
                        int indexLocaiton = current.getHead().lastIndexOf(new Location(tf2.getText().trim())); // gets the index of location
                        if (current.getHead().remove(indexLocaiton)) { // removes the location
                            errorLabel.setText("Location has been removed successfully");
                            tf2.clear();
                        } else {
                            errorLabel.setText("Location doesn't exist");
                        }
                    }else {
                        if(Manager.getDistricts().getDistrictByLocation(new Location(tf2.getText()))!=null) { // gets district location
                            int indexLocaiton = Manager.getDistricts().getDistrictByLocation(new Location(tf2.getText())).getHead().lastIndexOf(new Location(tf2.getText().trim()));
                            if (indexLocaiton != -1) {
                                Manager.getDistricts().getDistrictByLocation(new Location(tf2.getText())).getHead().remove(indexLocaiton);
                                errorLabel.setText("Location has been removed successfully");
                            }
                        }else{
                            errorLabel.setText("Location doesn't exist");
                        }
                    }
                });
                MainScreen.getSp().setCenter(vBox);
        }
    private void handleNavigate() { // a method used to navigate between locations and show their stats
            counter = 0;
            errorLabel.setText(null);
            HBox hb = Style.createHBox();
            Button prev = Style.createLeftTriangleButton();
            Button next = Style.createRightTriangleButton();
            prev.setPrefSize(50, 50);
            next.setPrefSize(50, 50);
            hb.getChildren().addAll(prev, next);
            TextArea ta = Style.createTextArea();
            Label label = new Label("Location");
            label.setFont(Font.font("Arial", FontWeight.BOLD, 18));
            VBox vBox = Style.createVBox();
            vBox.getChildren().addAll(label, ta, hb,Manager.getDistrictComboBox());
            Manager.getDistrictComboBox().setOnAction(e3 ->{
            if (!Manager.getDistricts().isEmpty()) {
                District district = Manager.getDistrictComboBox().getValue();
                int index = Manager.getDistricts().lastIndexOf(district);
                DistrictList.Node current = Manager.getDistricts().getDistrictNode(index); // declares and creates Node obj from DistrictList
                if (index >= 0) {
                    LocationList locationList = current.getHead();
                    if (locationList != null) {
                        updateLocationStats(ta, label, locationList); // calls the updateLocation method

                        prev.setOnAction(e1 -> { // event handler for prev
                            counter--;
                            if (counter < 0) {
                                counter = locationList.size() - 1;
                            }
                            updateLocationStats(ta, label, locationList);
                        });

                        next.setOnAction(e1 -> { // event handler for next
                            counter++;
                            if (counter >= locationList.size()) {
                                counter = 0;
                            }
                            updateLocationStats(ta, label, locationList);
                        });
                    }
                }
            }
            });
            MainScreen.getSp().setCenter(vBox);

    }

    private void updateLocationStats(TextArea ta, Label label, LocationList locationList) { // updates the TextArea statistics
        if (locationList != null && !locationList.isEmpty()) {
            LocationNode locationNode = locationList.getNode(counter);
            if (locationNode != null) {
                String averageAge = String.format("%.1f", locationNode.getHead().getAverageAge());
                int femaleNumber = locationNode.getHead().getfCounter();
                int maleNumber = locationNode.getHead().getmCounter();
                int totalNumber = locationNode.getHead().size();
                if (!locationNode.getHead().isEmpty()) {
                    String youngestMartyr = ((Martyr) locationNode.getHead().getFront().getElement()).getName(); // gets the youngest martyr
                    String oldestMartyr = ((Martyr) locationNode.getHead().getBack().getElement()).getName(); // gets the oldest marty r
                    String mostCommonDate = locationNode.getHead().findMostCommonDeathDate(); // gets the most common date
                    label.setText(((Location)locationNode.getElement()).getLocationName());
                    ta.setText("\n\nAverage age: " + averageAge +
                            "\nFemale Number: " + femaleNumber +
                            "\nMale Number: " + maleNumber +
                            "\nTotal Number: " + totalNumber +
                            "\nYoungest Martyr: " + youngestMartyr +
                            "\nThe Oldest Martyr: " + oldestMartyr +
                            "\nMost Common Date: " + mostCommonDate);
                }else{
                    ta.setText("\n\nAverage age: 0.0 " +
                            "\nFemale Number: 0"  +
                            "\nMale Number: 0"  +
                            "\nTotal Number: 0"  +
                            "\nYoungest Martyr: 0"  +
                            "\nThe Oldest Martyr: 0"  +
                            "\nMost Common Date: NA");
                }
            }
        }
    }
        public void handleStatistics(){ // a method used to show the statistics of a given location
                errorLabel.setText(null);
                VBox vBox = Style.createVBox();
                TextArea ta = Style.createTextArea();

                ta.setMaxSize(400,400);
                ta.setPromptText("Statistics will be displayed here");
                TextField tf2 = new TextField();
                tf2.setPromptText("Enter location");
                tf2.setMaxSize(140,50);
                Button bt = new Button("Enter");
                GridPane gp = new GridPane();
                gp.add(Style.createLabel("District"),0,0);
                gp.add(Manager.getDistrictComboBox(),1,0);
                gp.add(Style.createLabel("Location"),0,1);
                gp.add(tf2,1,1);
                gp.setHgap(10);
                gp.setVgap(20);
                gp.setAlignment(Pos.CENTER);
                bt.setOnAction(e->{
                    if(tf2.getText().isEmpty()){
                        errorLabel.setText("Fill TextField");
                        return;
                    }
                    if(Manager.getDistrictComboBox().getValue()==null){
                        errorLabel.setText("Select District");
                        return;
                    }
                    errorLabel.setText("Enter District");
                    District district = Manager.getDistrictComboBox().getValue(); // gets district value
                    LocationNode location = Manager.getDistricts().getLocationNode(district,new Location(tf2.getText())); // gets location node
                    if(location!=null) { // if location is not equal to null
                        String averageAge = String.format("%.1f", location.getHead().getAverageAge());
                        if(!location.getHead().isEmpty()) { // if maryrList is not empty
                            ta.setText("\n\nAverage age: " + averageAge + "\nFemale Number: " + location.getHead().getfCounter() + "\nMale Number: " + location.getHead().getmCounter() + "\nTotal Number: " + location.getHead().size() + "\nYoungest Martyr: " + ((Martyr) location.getHead().getFront().getElement()).getName() + "\nThe Oldest Martyr: " + ((Martyr) location.getHead().getBack().getElement()).getName() + "\nMost Common Date: " + location.getHead().findMostCommonDeathDate());
                        }else{ // sets text if empty
                            ta.setText("""


                                    Average age: 0
                                    Female Number: 0
                                    Male Number: 0
                                    Total Number: 0
                                    Youngest Martyr: 0
                                    The Oldest Martyr: 0
                                    Most Common Date: 0""");
                        }
                        errorLabel.setText(null);
                    }else {
                        errorLabel.setText("Location doesn't exist");
                    }
                });

            vBox.getChildren().addAll(ta,gp,bt,errorLabel);
                MainScreen.getSp().setCenter(vBox);

        }
    public void insertMartyr() { // a method used to insert a martyr
            errorLabel.setText(null);
            TextField[] tf = new TextField[3];
            VBox vBox = Style.createVBox();
            GridPane gridPane = Style.createGrid(tf);

            TextField[] dates = new TextField[3];
            for(int i=0;i<3;i++)
                dates[i] = new TextField();
            HBox date = Style.createDateInput(dates[0],dates[1],dates[2]);
            gridPane.add(new Label("Date"), 0, tf.length);
            gridPane.add(date, 1, tf.length);

            gridPane.add(new Label("Gender"), 0, tf.length + 1);
            gridPane.add(genderComboBox, 1, tf.length + 1);

            gridPane.add(new Label("District"), 0, tf.length + 2);
            gridPane.add(Manager.getDistrictComboBox(), 1, tf.length + 2);

            HBox hb = Style.createHBox();
            Button bt1 = new Button("Insert");
            bt1.setPrefSize(60, 30);
            hb.getChildren().add(bt1);

            vBox.getChildren().addAll(gridPane, hb,errorLabel);
            MainScreen.getSp().setCenter(vBox);

            bt1.setOnAction(e1 -> {
                // Validate input fields
                if (!validateInput(tf, dates)) {
                    return;
                }

                String name = tf[0].getText().trim();
                String mDate = dates[0].getText().trim() + "/" + dates[1].getText().trim() + "/" + dates[2].getText().trim();
                String location = tf[2].getText().trim();
                char gender = genderComboBox.getValue();
                String district = Manager.getDistrictComboBox().getValue().getDistrict();
                if(Manager.isValidDate(mDate)){ // checks if date is valid
                    errorLabel.setText("Invalid Date");
                    return;
                }
                String ageS = tf[1].getText().trim();
                if(Manager.isValidAge(ageS)==-1 && !tf[1].getText().trim().equals("?")){ // checks if age is valid
                    errorLabel.setText("Invalid Age");
                    return;
                }


                byte age = Manager.isValidAge(ageS);


                Martyr martyr = new Martyr(name, mDate, age, location, district, gender); // creates an object of martyr
                LocationNode locationNode = Manager.getDistricts().getLocationNode(new District(district), new Location(location)); // gets location node
                if (locationNode != null) {
                    if(!locationNode.getHead().contains(martyr)) {
                        locationNode.getHead().add(martyr); // adds the martyr to location
                        for (TextField textField : tf) textField.clear();

                        for (TextField textField : dates) textField.clear();


                        errorLabel.setText("Martyr added successfully.");
                    }else{
                        errorLabel.setText("Martyr Name already exists.");

                    }

                } else {
                    errorLabel.setText("Location not found.");
                }
            });
    }

    private boolean validateInput(TextField[] tf, TextField[] dates) { // checks if the text fields are valid
        if (isEmpty(tf) || isEmpty(dates)) {
            errorLabel.setText("All fields are required.");
            return false;
        }
        if (genderComboBox.getValue() == null) {
            errorLabel.setText("Select Gender.");
            return false;
        }
        if (Manager.getDistrictComboBox().getValue() == null) {
            errorLabel.setText("Select District.");
            return false;
        }
        return true;
    }

    private boolean isEmpty(TextField[] fields) {
        for (TextField field : fields) {
            if (field.getText().isEmpty()) {
                return true;
            }
        }
        return false;
    }
    public boolean checkMartyr(District distirct,Location location, String name){
        if(distirct!=null && location!=null){
            return Manager.getDistricts().getMartyrByLocation(distirct, location, name) != null;
        }
        return false;
    }
    public void updateMartyr() { // a method used to update the martyr info
            errorLabel.setText(null); // sets the error label to null
            TextField tf1 = new TextField();
            TextField tf2 = new TextField();
            GridPane gp = Style.checkingBox(tf1,tf2); // creates a gridPane from the Style class
            Button action = new Button("Search");
            VBox checkingBox = Style.createVBox();
            Button back = new Button("back");
            Style.styleButton(back);
            back.setOnAction(e-> updateMartyr());
                 checkingBox.getChildren().addAll(Style.createLabel("Search for the Martyr you want to Update"),gp,action,errorLabel,back);
                 Button bt1 = new Button("Update"); // button for update
                 Style.styleButton(bt1);
                 Button bt2 = new Button("Delete"); // button for delete
                 Style.styleButton(bt2);
                 bt1.setPrefSize(100, 40);
                 bt2.setPrefSize(100, 40);
                 Text text = new Text("Select Operation");
                 text.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 19));
                 VBox vBox1 = Style.createVBox();
                 vBox1.getChildren().addAll(text, bt1, bt2);
                 bt1.setOnAction(e1 -> {
                     MainScreen.getSp().setCenter(checkingBox);
                     action.setOnAction(event ->{ // event handler for action, its purpose is to check if the martyr exists or not, so it updates it
                         if(tf1.getText().isEmpty()){
                             errorLabel.setText("Enter Name please..");
                             return;
                         }
                         if(tf2.getText().isEmpty()){
                             errorLabel.setText("Enter Location please..");
                             return;
                         }
                         if(Manager.getDistrictComboBox().getValue()==null){
                             errorLabel.setText("Enter District please..");
                             return;
                         }
                         District currentDistrict = Manager.getDistrictComboBox().getValue(); // assigns the currentDistrict to the selected value of combo box
                         Location currrentLocation = new Location(tf2.getText().trim()); // assigns the currentLocation to the selected value of combo box
                         if (checkMartyr(currentDistrict, currrentLocation, tf1.getText())) { // if martyr exists
                             Martyr martyr = Manager.getDistricts().getMartyrByLocation(currentDistrict, currrentLocation, tf1.getText()); // gets the martyr object from the location and assigns it to an obj
                             TextField[] tf = new TextField[3];
                             VBox vBox = Style.createVBox();
                             GridPane gridPane = Style.createGrid(tf);
                             for (int i = 0; i < 3; i++) {
                                 tf[i].setEditable(false);
                             }
                             TextField[] dates = new TextField[3];
                             for (int i = 0; i < dates.length; i++) {
                                 dates[i] = new TextField();
                                 dates[i].setEditable(false); // sets editable to false
                             }
                             CheckBox[] cb1 = new CheckBox[5];
                             String[] check = {"Update name", "Update age", "Change location", "Update Date", "Update Gender"};
                             for (int i = 0; i < cb1.length; i++) {
                                 cb1[i] = new CheckBox(check[i]);
                             }
                             FlowPane fp = new FlowPane();
                             for (CheckBox checkBox : cb1) {
                                 fp.getChildren().addAll(checkBox);
                             }
                             fp.setVgap(10);
                             fp.setHgap(10);
                             for (int i = 0; i < 3; i++) { // a for loop to handle the array of checkboxes when selected
                                 int index = i;
                                 cb1[i].setOnAction(e2 -> {
                                     if (cb1[index].isSelected()) { // if selected then set text field to unknown
                                         tf[index].setEditable(true); // set editable to false
                                         tf[index].setStyle("-fx-border-color:lightGreen");
                                     } else {
                                         tf[index].setStyle("-fx-border-color: lightgrey;");
                                         tf[index].clear(); // when unselected clear the textfields
                                         tf[index].setEditable(false); // set the text field to editable
                                         tf[index].clear();
                                     }
                                 });
                             }
                             cb1[3].setOnAction(e3 -> {
                                 if (cb1[3].isSelected()) { // if selected then set text field to unknown
                                     for (int i = 0; i < 3; i++) {
                                         dates[i].setStyle("-fx-border-color:lightGreen");
                                         dates[i].setEditable(true);
                                     }
                                 } else {
                                     for (int i = 0; i < 3; i++) {
                                         dates[i].setStyle("-fx-border-color: lightgrey;");
                                         dates[i].setEditable(false);// sets editable to false
                                         dates[i].clear();//clears the dates text fields
                                     }
                                 }
                             });

                             HBox date = Style.createDateInput(dates[0], dates[1], dates[2]);
                             gridPane.add(new Label("Date"), 0, tf.length);
                             gridPane.add(date, 1, tf.length);
                             gridPane.add(new Label("District"), 0, tf.length + 1);
                             gridPane.add(Manager.getDistrictComboBox(), 1, tf.length + 1);
                             gridPane.add(new Label("Gender"), 0, tf.length + 2);
                             gridPane.add(genderComboBox, 1, tf.length + 2);
                             Button button = new Button("Update");
                             button.setPrefSize(60, 30);
                             vBox.getChildren().addAll(gridPane, button, new Label("please select the follow checkboxes to Update"), fp,errorLabel);
                             button.setOnAction(e3 -> {

                                 if(tf[0].isEditable() && !tf[0].getText().isEmpty()){
                                     martyr.setName(tf[0].getText().trim());
                                     MartyrTableView.getTableView().refresh(); // refreshes the table view
                                 }
                                 if(tf[1].isEditable()&& !tf[1].getText().isEmpty()){
                                     if(Manager.isValidAge(tf[1].getText().trim())==-1){
                                         errorLabel.setText("Invalid Age");
                                         return;
                                     }
                                     LocationNode locationNode = Manager.getDistricts().getLocationNode(currentDistrict, currrentLocation);
                                     int i = locationNode.getHead().lastIndexOf(martyr);
                                     locationNode.getHead().remove(i);
                                     martyr.setAge(Byte.parseByte(tf[1].getText().trim()));
                                     locationNode.addListHead(martyr);
                                     MartyrTableView.getTableView().refresh();
                                 }
                                 if(Manager.getDistrictComboBox().getValue()!=null && !tf[2].getText().isEmpty() && cb1[2].isSelected()) {
                                     LocationNode locationNode = Manager.getDistricts().getLocationNode(currentDistrict, currrentLocation);
                                     int i = locationNode.getHead().lastIndexOf(martyr);
                                     locationNode.getHead().remove(i);
                                     LocationNode newLocationNode = Manager.getDistricts().getLocationNode(Manager.getDistrictComboBox().getValue(), new Location(tf[2].getText()));
                                     if (newLocationNode != null) {
                                         martyr.setLocation(tf[2].getText().trim());
                                         martyr.setDistrict(Manager.getDistrictComboBox().getValue().getDistrict());
                                         newLocationNode.addListHead(martyr);
                                         MartyrTableView.getTableView().refresh();
                                     } else {
                                         errorLabel.setText("Location Doesn't Exist");
                                         return;
                                     }
                                 }else{
                                     errorLabel.setText("Select Both District and Location please");
                                 }

                                 if(dates[0].isEditable() && !(dates[0].getText().isEmpty() || dates[1].getText().isEmpty() || dates[2].getText().isEmpty())){
                                     String martyrDate = dates[0].getText().trim() + "/" + dates[1].getText().trim() + "/" + dates[2].getText().trim();
                                     if(Manager.isValidDate(martyrDate)){
                                         errorLabel.setText("Invalid Date");
                                         return;
                                     }
                                     martyr.setDate(martyrDate);
                                     MartyrTableView.getTableView().refresh();
                                 }
                                 if(cb1[4].isSelected() && genderComboBox.getValue()!=null){
                                     int j = Manager.getMartyrs().lastIndexOf(martyr);
                                     LocationNode locationNode =  Manager.getDistricts().getLocationNode(currentDistrict,currrentLocation);
                                     int index  = locationNode.getHead().lastIndexOf(martyr);
                                     ((Martyr) locationNode.getHead().get(index)).setGender(genderComboBox.getValue());
                                     martyr.setGender(genderComboBox.getValue());
                                     Manager.getMartyrs().set(j,martyr);
                                     MartyrTableView.getTableView().refresh();
                                 }

                                 errorLabel.setText("Updated Successfully");
                             });
                             MainScreen.getSp().setCenter(vBox);
                         }else{
                             errorLabel.setText("Martyr Doesn't Exist");
                         }
                     });

                 });
            MainScreen.getSp().setCenter(vBox1);
            bt2.setOnAction(event->{
                handleDeleteMartyr();
            });
    }



    public void handleDeleteMartyr(){ // a method to handle the deletion of the martyr
        TextField tf11 = new TextField();
        TextField tf22 = new TextField();
        tf11.setPromptText("Enter Martyr Name..");
        tf22.setPromptText("Enter Location Name..");GridPane gp1 = new GridPane();
        gp1.add(Style.createLabel("Martyr name"),0,0);
        gp1.add(tf11,1,0);
        gp1.add(Style.createLabel("District"),0,1);
        gp1.add(Manager.getDistrictComboBox(),1,1);
        gp1.add(Style.createLabel("Location"),0,2);
        gp1.add(tf22,1,2);
        gp1.setVgap(20);
        gp1.setHgap(10);
        gp1.setAlignment(Pos.CENTER);
        Button action1 = new Button("Delete");
        VBox checkingBox1 = Style.createVBox();
        Button back = new Button("back");
        Style.styleButton(back);
        back.setOnAction(e-> updateMartyr());
        checkingBox1.getChildren().addAll(Style.createLabel("Enter the following information"), gp1,action1,errorLabel,back);
        action1.setOnAction(e1-> {
            if(tf11.getText().isEmpty()){
                errorLabel.setText("Enter name please");
                return;
            }
            if(tf22.getText().isEmpty()){
                errorLabel.setText("Enter Location please");
                return;
            }
            String name = tf11.getText().trim();
            Location location = new Location(tf22.getText().trim());
            District district = Manager.getDistrictComboBox().getValue();
            if (checkMartyr(district, location, name)) {
                Martyr martyr = Manager.getDistricts().getMartyrByLocation(district, location, name); // gets the martyr from location and assigns to an obj
                LocationNode locoNode = Manager.getDistricts().getLocationNode(district,location); // gets the locationNode and assigns it to a locoNode obj
                locoNode.getHead().remove(locoNode.getHead().lastIndexOf(martyr)); // removes the martyr from the location
                errorLabel.setText("Martyr has been deleted");
                tf22.clear();
            }else{
                errorLabel.setText("Martyr is not found");
            }
        });
        MainScreen.getSp().setCenter(checkingBox1);
    }
    public void handleSearch(){ //  a method to handle the search
       errorLabel.setText(null);
       TextField tf = new TextField();
       TextArea ta = Style.createTextArea();
       TextField tf2 = new TextField();
       CheckBox cb1 = new CheckBox("Location known");
       CheckBox cb2 = new CheckBox("District known");
        Button button = getButton(tf, tf2,ta,cb1,cb2);
        VBox vBox  = Style.searchGUI(ta,tf,tf2,button,cb1,cb2,errorLabel); // creates the search GUI from the Style class
       MainScreen.getSp().setCenter(vBox);
        }

    private Button getButton(TextField tf,TextField tf2, TextArea ta,CheckBox cb1,CheckBox cb2) {
        Button button = new Button("Search");
        button.setOnAction(e1 -> {

            errorLabel.setText(null);
            ta.clear();
            if (!tf.getText().isEmpty()) {
                MartyrList  list = new MartyrList();
                if (!tf.getText().isEmpty() && !cb1.isSelected() && !cb2.isSelected()) { // checks if checkboxes are not selected and text field is not empty
                    list = Manager.getDistricts().getMartyrs(tf.getText().trim()); // searches martyr by only part of their name
                } else if (!tf.getText().isEmpty() && cb1.isSelected() && !cb2.isSelected()){
                    if(tf2.getText().isEmpty()){
                        errorLabel.setText("Select Location");
                        return;
                    }
                    list = Manager.getDistricts().getMartyrByLocation(tf2.getText().trim(),tf.getText().trim()); // searches martyr by part of their name and location
                } else if (!tf.getText().isEmpty() && cb1.isSelected() && cb2.isSelected()) {
                    if(Manager.getDistrictComboBox().getValue()==null){
                        errorLabel.setText("Select District");
                        return;
                    }
                    if(tf2.getText().isEmpty()){
                        errorLabel.setText("Select Location");
                        return;
                    }
                    // searches martyr by part of their name, location and district
                    list = Manager.getDistricts().getMartyrsByLocationAndDistrict(tf2.getText().trim(),Manager.getDistrictComboBox().getValue().getDistrict(),tf.getText().trim());
                }else if(!tf.getText().isEmpty() && !cb1.isSelected() && cb2.isSelected()){
                    if(Manager.getDistrictComboBox().getValue()==null){
                        errorLabel.setText("Select District");
                        return;
                    }
                    //searches martyr by part of their name and distirct
                    list = Manager.getDistricts().getMartyrByDistrict(Manager.getDistrictComboBox().getValue().getDistrict(),tf.getText().trim());
                }
                if (list != null && !list.isEmpty()) {
                    MartyrNode current = list.getFront();
                    int max = 0;
                    while (current != null && max<100) {
                        String name = ((Martyr) current.getElement()).getName();
                        String district = ((Martyr) current.getElement()).getDistrict();
                        String location = ((Martyr) current.getElement()).getLocation();

                        ta.appendText("\n" + name + " : " + district + " : " + location);
                        current = current.getNext();
                        max++; // when max reaches 100 the loop stops to prevent hanging the program
                    }

                } else {
                    errorLabel.setText("Martyr doesn't exist or list is null");
                }
            } else {
                errorLabel.setText("TextField is required");
            }
        });
        return button;
    }

}
