package com.example.project0;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class SupportGuidePane extends BorderPane { // a simple supportGuidePane class, to help the user understand how to use the program
    private Button help = new Button("?"); // declaring and creating a button called "?"
    private Stage stage = new Stage();
    private Scene scene = new Scene(this, 400, 500);
    private TextArea ta = new TextArea();

    Button bt = new Button("Understood");
    public SupportGuidePane() { // a no arg constructor
        this.setStyle("-fx-border-color: lightSkyBlue; -fx-background-color: LightSkyBlue;");
        String text ="""
                Button "1": used to insert a Martyr

                Button "2": used to delete a Martyr

                Button "3": used to Search for a Martyr

                Button "4": used to read from file

                Button "5": used to display the number of Martyrs

                Button "Cancel": used to Cancel an ongoing operation

                Button "Display Martyrs": used to display the Martyrs
                 in a Table View

                Button "Enter": used to insert Text

                Answer the Questions by typing in the TextField\s
                and clicking on "Enter"
                
                **Note: if the name is unknown, you must type name\s
                unknown by b'tselem, so it gives you the index
                
                **Note: you can choose whether the attributes are known\n
                or unknown

                **Note: you can only do the operations by clicking on\s
                the buttons""";
        ta.setText(text);
        ta.setEditable(false);
        ta.setStyle("-fx-text-fill: red;");
        ta.setMaxHeight(500);
        this.setPadding(new Insets(10,10,10,10));
        this.setCenter(ta);
        this.setBottom(bt);
        bt.setAlignment(Pos.CENTER);
        bt.setOnAction(e -> {
            stage.close();
        });
    }

    public Button getHelp() { // a getter method that returns the Help button
        help.setOnAction(e -> { // action event for the button, so it pops out when the user clicks on it
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Support Guide");
            stage.show();
        });
        return help;
    }

}
