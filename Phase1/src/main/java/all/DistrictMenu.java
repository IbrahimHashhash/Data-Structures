
package all;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
public class DistrictMenu extends Menu {
    private MenuItem[] items; // declares an array of items
    private static final Label errorLabel = new Label();
    private int counter = 0;

    public DistrictMenu() { // public constructor
        super("District");
        items = new MenuItem[6];
        String[] operations = {"Insert", "Update", "Delete", "Navigate","Total", "SaveToFile"};
        for (int i = 0; i < items.length; i++) { // adds the items to the menu
            items[i] = new MenuItem(operations[i]);
            this.getItems().add(items[i]);
        }
        errorLabel.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 12));
        errorLabel.setTextFill(Color.RED);
        // initializes the event handlers in the constructor
        handleInsert();
        handleUpdate();
        handleDelete();
        handleNavigate();
        handleTotal();
        saveToFile();
    }

    private void showErrorLabel(String message, Color color) { // method to show the error label based on color
        errorLabel.setText(message);
        errorLabel.setTextFill(color);
    }

    private void handleInsert() { // a method to insert a district
        items[0].setOnAction(e -> {
            errorLabel.setText(null); // sets label to null
            TextField tf = Style.createTextField(); // creates text field
            tf.setPromptText("Enter District..");
            HBox hb = Style.createHBox();
            Label label = Style.createLabel("Insert");
            Button insertButton = new Button("Insert");
            VBox vBox = Style.createVBox();
            hb.getChildren().addAll(label, tf);
            vBox.getChildren().addAll(hb, insertButton, errorLabel);
            MainScreen.getSp().setCenter(vBox);
            insertButton.setOnAction(e1 -> {
                if (Style.TextFieldLogic(tf)) { // checks if text field is empty
                    String districtName = tf.getText().trim();
                    if (Manager.getDistricts().exists(districtName)) { // checks if district exists
                        Manager.getDistricts().add(new District(districtName)); // adds the district
                        showErrorLabel("District has been added successfully", Color.GREEN);
                        tf.clear();
                    } else {
                        showErrorLabel("District already exists", Color.RED);
                    }
                } else {
                    showErrorLabel("TextField is Empty, Enter something", Color.RED);
                }
            });
        });
    }

    private void handleUpdate() { // a method used to change the name of the district
        items[1].setOnAction(e -> {
            errorLabel.setText(null);
            GridPane gp = new GridPane();
            gp.setVgap(20);
            gp.setAlignment(Pos.CENTER);
            TextField newDistrictTextField = Style.createTextField();
            newDistrictTextField.setPromptText("Enter New District Name..");
            Label districtLabel = Style.createLabel("District");
            Label newDistrictLabel = Style.createLabel("New District");
            gp.add(districtLabel, 0, 0);
            gp.add(Manager.getDistrictComboBox(), 1, 0);
            gp.add(newDistrictLabel, 0, 1);
            gp.add(newDistrictTextField, 1, 1);
            gp.setHgap(10);
            Button updateButton = new Button("Update");
            VBox vBox = Style.createVBox();
            vBox.getChildren().addAll(gp, updateButton, errorLabel);

            updateButton.setOnAction(event -> {
                if (Manager.getDistrictComboBox().getValue() != null && !newDistrictTextField.getText().isEmpty()) { // checks if combo box and textfield is empty
                    String newDistrictName = newDistrictTextField.getText().trim();
                    if(Manager.getCboDistrict().contains(new District(newDistrictName))){
                        errorLabel.setText("You can't use this name, it already exists");
                        return;
                    }
                    District selectedDistrict = Manager.getDistrictComboBox().getValue(); // declares and creates a district object
                    int index = Manager.getDistricts().lastIndexOf(selectedDistrict);
                    DistrictList.Node current = Manager.getDistricts().getDistrictNode(index);
                    Manager.getDistricts().updateMartyrDistrict(selectedDistrict,newDistrictName);
                    Manager.getDistricts().remove(index);
                    selectedDistrict.setDistrict(newDistrictName);
                    Manager.getDistricts().add(current.getElement());
                    MartyrTableView.getTableView().refresh();
                    showErrorLabel("District has been updated Successfully", Color.GREEN);
                } else {
                    showErrorLabel("Please select a district and enter a new district name.", Color.RED);
                }
            });

            MainScreen.getSp().setCenter(vBox);
        });
    }

    private void handleDelete() { // a method used to delete a district
        items[2].setOnAction(e -> {
            errorLabel.setText(null);
            HBox hb = Style.createHBox();
            Label label = Style.createLabel("Delete");
            Button deleteButton = new Button("Delete");
            VBox vBox = Style.createVBox();
            hb.getChildren().addAll(label, Manager.getDistrictComboBox());
            vBox.getChildren().addAll(hb, deleteButton, errorLabel);
            MainScreen.getSp().setCenter(vBox);
            MartyrTableView.getTableView().refresh();
            deleteButton.setOnAction(e1 -> {
                Object selectedDistrict = Manager.getDistrictComboBox().getValue();
                if (selectedDistrict != null) {
                    if (Manager.getDistricts().remove(selectedDistrict)) { // removes the district
                        showErrorLabel("District has been removed", Color.GREEN);
                        Manager.getDistrictComboBox().getItems().remove(selectedDistrict);
                    }
                } else {
                    showErrorLabel("Select a District please", Color.RED);
                }
            });
        });
    }

    private void handleNavigate() { // a method used to navigate between districts
        items[3].setOnAction(e -> {
            counter = 0;
            errorLabel.setText(null);
            HBox hb = Style.createHBox();
            Button prev = Style.createLeftTriangleButton(); // creates button
            Button next = Style.createRightTriangleButton();
            prev.setPrefSize(50, 50);
            next.setPrefSize(50, 50);
            hb.getChildren().addAll(prev, next);
            TextArea ta = Style.createTextArea();

            Label label = new Label("District");
            label.setFont(Font.font("Arial",FontWeight.BOLD,18));
            VBox vBox = Style.createVBox();
            vBox.getChildren().addAll(label, ta, hb);

            if (!Manager.getDistricts().isEmpty()) { // if districtList is not empty
                int totalFemales = 0;
                int totalMales = 0;
                int totalMartyrs = 0;
                double averageAge = 0;
                updateDistrictStats(ta, totalFemales, totalMales, totalMartyrs,averageAge,label); // updates the variables
                prev.setOnAction(e1 -> {
                    if (!Manager.getDistricts().isEmpty()) { // if district isEmpty, decrement counter
                        counter--;
                        if (counter < 0) {
                            counter = Manager.getDistricts().size() - 1;
                        }
                        updateDistrictStats(ta, totalFemales, totalMales, totalMartyrs,averageAge,label); // updates the textArea information
                    }
                });

                next.setOnAction(e1 -> {
                    if (!Manager.getDistricts().isEmpty()) { // if manager is not empty increment counter
                        counter++;
                        if (counter >= Manager.getDistricts().size()) {
                            counter = 0;
                        }
                        updateDistrictStats(ta, totalFemales, totalMales, totalMartyrs,averageAge,label); // updates the variables
                    }
                });
            }

            MainScreen.getSp().setCenter(vBox);
        });
    }
    // a method used to update the district statistics
    private void updateDistrictStats(TextArea ta, int totalFemales, int totalMales, int totalMartyrs,double averageAge, Label label) {
        DistrictList.Node current = Manager.getDistricts().getDistrictNode(counter);
            totalFemales = current.totalFemales(counter);
            totalMales = current.totalMales(counter);
            totalMartyrs = current.totalMartyrs(counter);
            averageAge = current.getAverage(counter);
            String commonDate = current.findMostCommonDeathDate(counter);
            label.setText(current.getElement().toString());
            // updates the text area content
            ta.setText("\nTotal Martyrs: " + totalMartyrs + "\n\nTotal Males: " + totalMales + "\n\nTotal Females: " + totalFemales + "\n\nAverage age: " + String.format("%.1f", averageAge) + "\n\nMost Common Date: " + commonDate);

    }
    private void handleTotal() { // a method used to display the number of martyrs based on date
        items[4].setOnAction(e -> {
            errorLabel.setText(null);
            TextField[] dates = new TextField[3];
            for (int i = 0; i < dates.length; i++) {
                dates[i] = new TextField();
            }
            HBox dateTextField = Style.createDateInput(dates[0], dates[1], dates[2]);
            HBox hb = Style.createHBox();
            Label label = Style.createLabel("Date"); // label for date
            Label showNum = new Label();
            showNum.setFont(Font.font("Arial",FontWeight.SEMI_BOLD,16));
            Button totalButton = new Button("Total Number"); // button for total number
            VBox vBox = Style.createVBox();
            hb.getChildren().addAll(label, dateTextField);
            vBox.getChildren().addAll(hb, totalButton,showNum,errorLabel);
            totalButton.setOnAction(e1->{
                if(dates[0].getText().isEmpty() ||dates[1].getText().isEmpty() || dates[2].getText().isEmpty()){
                    showErrorLabel("please enter all the dates",Color.RED);
                    return;
                }
                String date = dates[0].getText().trim() + "/" + dates[1].getText().trim() + "/" +dates[2].getText().trim();
                if(Manager.isValidDate(date)){
                    errorLabel.setText("Invalid Date");
                    return;
                }


                showNum.setText("Number of Martyrs" + " of Date " + date + ": " + Manager.getDistricts().getTotalMartyrsWithDate(date));
                for (TextField textField : dates) {
                    textField.clear();
                }
                errorLabel.setText(null);
            });
            MainScreen.getSp().setCenter(vBox);
        });
    }
    public void saveToFile(){ // a method used to write a district statistics to a file
        items[5].setOnAction(e1 ->{
        Button button = new Button("Save to File");
        TextArea ta = Style.createTextArea();
        VBox vBox = Style.createVBox();
        GridPane gp = new GridPane();
        gp.setHgap(10);
        gp.setVgap(10);
        gp.add(Style.createLabel("Enter District"),0,0);
        gp.add(Manager.getDistrictComboBox(),1,0);
        gp.add(button,0,1);
        gp.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(ta,gp,errorLabel);
        button.setOnAction(e-> {
            if (Manager.getDistrictComboBox().getValue() == null) {
                errorLabel.setText("Select District pelase");
                return;
            }
            int i = Manager.getDistricts().lastIndexOf(Manager.getDistrictComboBox().getValue()); // gets the index of the district
            if (i != -1) {
                DistrictList.Node current = Manager.getDistricts().getDistrictNode(i); // gets the node of the district
                int totalFemales = current.totalFemales(i);
                int totalMales = current.totalMales(i);
                int totalMartyrs = current.totalMartyrs(i);
                double averageAge = current.getAverage(i);
                String commonDate = current.findMostCommonDeathDate(i);
                String avg = String.format("%.1f", averageAge);
                ta.setText("\n\nTotal Martyrs: " + totalMartyrs + "\nTotal Males: " + totalMales + "\nTotal Females: " + totalFemales + "\nAverage age: " + String.format("%.1f", averageAge) + "\nMost Common Date: " + commonDate);
                    String[] info = {String.valueOf(totalFemales), String.valueOf(totalMales), String.valueOf(totalMartyrs), commonDate, avg};
                    try {
                        File f = new File(current.getElement().toString()+".txt");
                        PrintWriter pw = new PrintWriter(f); // creates a printWriter object to set the text of the file
                        pw.write("District Name: " + current.getElement().toString());
                        String[]  labels = {"Total Females: ", "Total Males: ", "Total Martyrs: ", "Common Date: ", "Average age: "};
                        for (int j = 0; j < 5; j++) {
                            pw.write("\n"+labels[j] + info[j]); // writes the information to the file
                        }
                        pw.println("\n------------------");
                        pw.close();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                   showErrorLabel("Information has been saved successfully",Color.GREEN);
            } else {
                errorLabel.setText("District doesn't Exist");
            }
        });

        MainScreen.getSp().setCenter(vBox);
    });

    }

}
