package com.example.project0;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class InsertPane extends BorderPane { // a class for insertion that extends borderpane
    private Button closeButton = new Button("Close");
    private String name, firstName, fatherName, grandFatherName, surName, ageText, event, date;

    public InsertPane() { // a public constructor for the class
        Text text = new Text("If the Age was unknown, it is represented as -1, if the name is unknown, it is represented by Question Marks,\n or Name unknown to B'Tselem");
        text.setFill(Color.BLACK);
        this.setBottom(text);
        GridPane gp = new GridPane();
        this.setStyle("-fx-border-color: lightSkyBlue; -fx-background-color: LightSkyBlue;"); // styles the pane
        TextField[] tf = new TextField[7]; // declaring and creating an array of textfields
        String[] labels = {"First Name", "Father Name", "GrandFather Name", "SurName", "Age", "Event Location", "Date of Death"}; // an array of labels
        for (int i = 0; i < labels.length; i++) { // for loop, to create the objects of textfields and arrays
            Label lb = new Label(labels[i]);
            lb.setFont(Font.font("Arial", FontPosture.REGULAR, 14));
            tf[i] = new TextField();
            style(tf[i]);
            gp.add(lb, 0, i);
            gp.add(tf[i], 1, i);
        }
        CheckBox[] checkBoxes = new CheckBox[7]; // declaring and creating an array of CheckBoxes
        for (int i = 0; i < checkBoxes.length; i++) { // a for loop to add the checkboxes to the gridpane
            checkBoxes[i] = new CheckBox("UnKnown");
            gp.add(checkBoxes[i], 4, i); // adds it to column 4
        }
        for (int i = 0; i < checkBoxes.length; i++) { // a for loop to handle the array of checkboxes when selected
            int index = i;
            checkBoxes[i].setOnAction(e -> {
                if (checkBoxes[index].isSelected()) { // if selected then set text field to unknown
                    tf[index].setText("Unknown");
                    tf[index].setEditable(false); // set editable to false
                } else {
                    tf[index].clear(); // when unselected clear the textfields
                    tf[index].setEditable(true); // set the text field to editable
                }
            });
        }


        HBox hBox = new HBox(); // creating and HBox for buttons
        hBox.setSpacing(15);
        hBox.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(40, 40, 40, 40));
        Label genderLabel = new Label("Gender");
        genderLabel.setFont(Font.font("Arial", FontPosture.REGULAR, 14));
        ComboBox<Character> genderComboBox = new ComboBox<>();
        /*
        calling the style method to style the Nodes
         */
        style(genderComboBox);
        // declaring and creating buttons
        Button insertButton = new Button("Insert Martyr");
        style(insertButton);
        style(closeButton);
        genderComboBox.setPromptText("select gender");
        genderComboBox.getItems().addAll('M', 'F', 'N');

        gp.add(genderLabel, 0, labels.length);
        gp.add(genderComboBox, 1, labels.length);

        gp.setVgap(30);
        gp.setHgap(20);

        gp.setAlignment(Pos.CENTER);
        this.setLeft(gp);
        this.setRight(hBox);

        gp.add(insertButton, 0, labels.length + 1);
        gp.add(closeButton, 1, labels.length + 1);

        Label errorLabel = new Label();
        errorLabel.setTextFill(Color.BLACK);
        errorLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        gp.add(errorLabel, 0, labels.length + 2, 2, 1);

        insertButton.setOnAction(e -> {
            // assigns the variables to the TextField input
            this.firstName = tf[0].getText().trim();
            this.fatherName = tf[1].getText().trim();
            this.grandFatherName = tf[2].getText().trim();
            this.surName = tf[3].getText().trim();
            this.ageText = tf[4].getText().trim();
            this.event = tf[5].getText().trim();
            this.date = tf[6].getText().trim();
            /*
            if checkboxes were selected, it assigns the variables to question marks
             */
            if (checkBoxes[0].isSelected()) {
                this.firstName = "??????";
            }
            if (checkBoxes[1].isSelected()) {
                this.fatherName = "??????";
            }
            if (checkBoxes[2].isSelected()) {
                this.grandFatherName = "??????";
            }
            if (checkBoxes[3].isSelected()) {
                this.surName = "??????";
            }
            if (checkBoxes[4].isSelected()) {
                this.ageText = "-1";
            }
            if (checkBoxes[5].isSelected()) {
                this.event = "Unknown";
            }
            if (checkBoxes[6].isSelected()) {
                this.date = "Unknown";
            }
            // checks if the variables are empty, and checkboxes are not selected
            if (firstName.isEmpty() && !checkBoxes[0].isSelected() ||
                    fatherName.isEmpty() && !checkBoxes[1].isSelected() ||
                    grandFatherName.isEmpty() && !checkBoxes[2].isSelected() ||
                    surName.isEmpty() && !checkBoxes[3].isSelected() ||
                    ageText.isEmpty() && !checkBoxes[4].isSelected() ||
                    event.isEmpty() && !checkBoxes[5].isSelected() ||
                    date.isEmpty() && !checkBoxes[6].isSelected()) {
                errorLabel.setText("All fields are required");
                return;
            }
            // handles the error that may occur from the user
            if (!Manager.isValidAge(ageText) && !checkBoxes[4].isSelected()) {
                errorLabel.setText("Age must be a positive number");
                return;
            }
            if (genderComboBox.getValue() == null) {
                errorLabel.setText("Select Gender");
                return;
            }
            if (!Manager.isValidDate(date) && !checkBoxes[6].isSelected()) {
                errorLabel.setText("Invalid Date");
                return;
            }

            byte age = -1;
            if (!ageText.isEmpty()) {
                try {
                    age = Byte.parseByte(ageText);
                } catch (NumberFormatException ex) {
                    errorLabel.setText("Invalid age format");
                    return;
                }
            }
            Manager.doubleArrayLength(Manager.getMartyrs(), Manager.getMartyrCounter()); // calls the static double array method from manager class, to double the array
            char gender = genderComboBox.getValue(); // gets the value of the combo box

            if (checkBoxes[0].isSelected() && checkBoxes[1].isSelected() && checkBoxes[2].isSelected() && checkBoxes[3].isSelected())
                this.name = "Name unknown to B'Tselem";
            else
                this.name = firstName + " " + fatherName + " " + grandFatherName + " " + surName;
            if(Manager.exists(name)){
                errorLabel.setText("Name already Exists");
                return;
            }
            // calls the array from the manager class, and the increment counter, then adds martyr object to the array
            Manager.getMartyrs()[Manager.incrementCounter()] = new Martyr(name, age, event, date, gender);
            errorLabel.setText("Martyr Added Successfully");
            for (int i=0;i<tf.length;i++) { // clearing the textfields and checkboxes selction to false
                tf[i].clear();
                tf[i].setEditable(true);
                checkBoxes[i].setSelected(false);
            }

        });
    }

    /*
    Style method that takes a Node, and styles using Css
     */
    public void style(Node x){
        if(x instanceof TextField){ // if node was instance of TextField, it gives them a special customization
            x.setStyle("-fx-background-color: aliceBlue; -fx-border-color: DarkSlateGray; -fx-text-fill: DarkSlateGray;");
            x.setOnMouseClicked(e -> {
                x.setStyle("-fx-background-color: aliceBlue; -fx-border-color: lightGreen; -fx-text-fill: black;");
            });
            x.setOnMouseExited(e -> {
                x.setStyle("-fx-background-color: aliceBlue; -fx-border-color: DarkSlateGray; -fx-text-fill: DarkSlateGray;");
            });

        }else { // if node was not an instance of Textfield it handles styliny in a different way
            x.setStyle("-fx-background-color: aliceBlue; -fx-border-color: DarkSlateGray; -fx-text-fill: DarkSlateGray;");
            x.setOnMouseMoved(e -> {
                x.setStyle("-fx-background-color: SkyBlue; -fx-border-color: DarkSlateGray; -fx-text-fill: DarkSlateGray;");
            });
            x.setOnMouseClicked(e -> {
                x.setStyle("-fx-background-color: deepSkyBlue; -fx-border-color: black; -fx-text-fill: black;");
            });
            x.setOnMouseExited(e -> {
                x.setStyle("-fx-background-color: aliceBlue; -fx-border-color: DarkSlateGray; -fx-text-fill: DarkSlateGray;");
            });
        }
    }
    public Button getCloseButton() {
        return closeButton;
    }  // a getter method that returns the closeButton
}
