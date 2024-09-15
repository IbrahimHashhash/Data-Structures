package all;


import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
/*
Style class that has methods for the customization of GUI
 */
public class Style {
    public static VBox createVBox(){ // method that returns a VBox Node
        VBox vbox = new VBox();
        vbox.setSpacing(35);
        vbox.setAlignment(Pos.CENTER);
        return vbox;
    }

    public static Label createLabel(String text) { // method that returns a lable node
        Label label = new Label(text);
        label.setFont(Font.font("Arial", FontWeight.SEMI_BOLD,14));
        return label;
    }
    public static Button createRightTriangleButton() { // method that returns a triangular shaped button
        // Create an inverted triangle pointing to the right
        Polygon invertedTriangle = new Polygon();
        invertedTriangle.getPoints().addAll(
                0.0, 0.0,
                20.0, 10.0,
                0.0, 20.0
        );

        // Create a button with the inverted triangle shape
        Button button = new Button();
        button.setShape(invertedTriangle);
        button.setStyle("-fx-border-color: black; -fx-background-color: orange; -fx-border-width: 1.5px;");

        // Set action events for the button
        button.setOnMouseClicked(e -> {
            button.setStyle("-fx-border-color: black; -fx-background-color: Tomato; -fx-border-width: 1.5px;");
        });
        button.setOnMouseMoved(e -> {
            button.setStyle("-fx-border-color: black; -fx-background-color: DarkOrange; -fx-border-width: 1.5px;");
        });
        button.setOnMouseExited(e -> {
            button.setStyle("-fx-border-color: black; -fx-background-color: orange; -fx-border-width: 1.5px;");
        });

        return button;
    }
    public static Button createLeftTriangleButton() {// method that returns a triangular shaped button but inverted
        // Create an inverted triangle pointing to the left
        Polygon invertedTriangle = new Polygon();
        invertedTriangle.getPoints().addAll(
                20.0, 0.0,
                0.0, 10.0,
                20.0, 20.0
        );

        // Create a button with the inverted triangle shape
        Button button = new Button();
        button.setShape(invertedTriangle);
        button.setStyle("-fx-border-color: black; -fx-background-color: orange; -fx-border-width: 1.5px;");

        // Set action events for the button
        button.setOnMouseClicked(e -> button.setStyle("-fx-border-color: black; -fx-background-color: Tomato; -fx-border-width: 1.5px;"));
        button.setOnMouseMoved(e -> button.setStyle("-fx-border-color: black; -fx-background-color: darkOrange; -fx-border-width: 1.5px;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-border-color: black; -fx-background-color: orange; -fx-border-width: 1.5px;"));

        return button;
    }

    /*
    a method primarily created to create a GUI for searching martyr
     */
    public static VBox searchGUI(TextArea ta,TextField tf,TextField tf2,Button button,CheckBox cb1,CheckBox cb2,Label errorLabel){
        VBox vBox = Style.createVBox();
        GridPane gp = new GridPane();
        tf.setPromptText("Enter Martyr Name..");
        ta.setEditable(false);
        ta.setStyle("-fx-border-color:black;");
        tf.setPromptText("Enter Name..");
        gp.add(Style.createLabel("Name"),0,0);
        gp.add(tf,1,0);
        gp.add(Style.createLabel("Optional"),0,1);
        gp.add(Style.createLabel("District"),0,2);
        gp.add(Manager.getDistrictComboBox(),1,2);
        gp.add(cb2,2,2);
        gp.add(Style.createLabel("Location"),0,3);
        gp.add(tf2,1,3);
        gp.add(cb1,2,3);
        gp.setAlignment(Pos.CENTER);
        gp.setVgap(15);
        gp.setHgap(10);
        tf2.setPromptText("Enter Location..");
        vBox.getChildren().addAll(ta,gp,button,errorLabel);
        return vBox;
    }

    public static void styleButton(Button button) {
        // Set initial button style
        button.setStyle("-fx-background-color: white; -fx-border-color: black;");

        // Set action events for the button
        button.setOnMouseClicked(e -> button.setStyle("-fx-background-color: deepSkyBlue; -fx-border-color: black;"));
        button.setOnMouseMoved(e -> button.setStyle("-fx-background-color: deepSkyBlue; -fx-border-color: black;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: white; -fx-border-color: black;"));
    }

    public static GridPane createGrid(TextField[] tf){ // creates GridPane and handles it
        String[] lb1 = {"name","age","location"};
        String[] lb2 = {"Enter Name..","Enter Age..","Enter Location.."};
        GridPane gridPane = new GridPane();
        gridPane.setHgap(30);
        gridPane.setVgap(40);
        gridPane.setAlignment(Pos.CENTER);
        for(int i=0;i<tf.length;i++){
            tf[i] = new TextField();
            tf[i].setMaxSize(200, 40);
            Label label = Style.createLabel(lb1[i]);
            tf[i].setPromptText(lb2[i]);
            GridPane.setConstraints(label, 0, i);
            GridPane.setConstraints(tf[i], 1, i);
            gridPane.getChildren().addAll(label, tf[i]);
        }
        return gridPane;
    }


    public static TextField createTextField(){ // creates textfield
        TextField tf = new TextField();
        tf.setMaxSize(200, 60);
        return tf;

    }
    public static TextArea createTextArea(){
        TextArea ta = new TextArea();
        ta.setEditable(false);
        ta.setStyle("-fx-border-color: black;-fx-control-inner-background: aliceBlue;-fx-text-fill: DarkSlateGray;-fx-font-size: 14px;-fx-font-weight: bold;");
        return ta;
    }
    public static boolean TextFieldLogic(TextField tf){ // checks if textfield is empty
        return !tf.getText().isEmpty();
    } // creates
    public static HBox createDateInput(TextField month,TextField day,TextField year) {
        month.setPromptText("MM");
        month.setMaxWidth(38);
        day.setPromptText("DD");
        day.setMaxWidth(38);
        year.setPromptText("YYYY");
        year.setMaxWidth(60);
        HBox dateInputHBox = new HBox();
        dateInputHBox.getChildren().addAll(month,day,year);
        dateInputHBox.setSpacing(3);
        return dateInputHBox;
    }

    public static HBox createHBox(){ // creates HBox
        HBox hbox = new HBox();
        hbox.setSpacing(15);
        hbox.setAlignment(Pos.CENTER);
        return hbox;
    }
    // a method to create a VBox that has the following nodes added to it
        public static GridPane checkingBox(TextField tf1, TextField tf2) {
            GridPane gp = new GridPane();
            gp.add(Style.createLabel("Martyr name"), 0, 0);
            tf1.setPromptText("Enter Martyr Name..");
            tf2.setPromptText("Enter Location Name..");
            gp.add(tf1, 1, 0);
            gp.add(Style.createLabel("District"), 0, 1);
            gp.add(Manager.getDistrictComboBox(), 1, 1);
            gp.add(Style.createLabel("Location"), 0, 2);
            gp.add(tf2, 1, 2);
            gp.setVgap(20);
            gp.setHgap(10);
            gp.setAlignment(Pos.CENTER);
            return gp;

    }
}
