package com.example.phase2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.List;
import java.util.Stack;

public class DistrictMenu extends Menu {
    private static int counter = 0;
    private static DNode dNode = null;
    private static final Label label = new Label();
    private String[] districts = {
            "Jenin",
            "Tubas",
            "Tulkarm",
            "Nablus",
            "Qalqilya",
            "Salfit",
            "Ramallah and Al-Bireh",
            "Jericho",
            "East Jerusalem",
            "Bethlehem",
            "Hebron",
            "North Gaza",
            "Gaza",
            "Deir al-Balah",
            "Khan Yunis",
            "Rafah",
            "Israel",
            "Gush Katif",
            "Haifa",
            "Al-Quds",
            "Al-Bireh",
            "Al-Khalil"

    };

    public DistrictMenu(){
        this.setText("District");
        initialize();
    }
    private static void updateLabel(String msg, boolean result){
        label.setText(msg);
        label.setStyle("-fx-text-fill: darkred;-fx-background-color:white; -fx-font-family: 'Segoe Print';-fx-font-size:15;-fx-font-weight:bold;");
        if(result){
            label.setTextFill(Color.GREEN);
        }else{
            label.setTextFill(Color.RED);
        }
    }


    public void initialize(){
        String[] label ={"Insert","Update","Delete","Navigate","Save"};
        MenuItem[] items = new MenuItem[5];
        for (int i=0;i<items.length;i++){
            items[i] = new MenuItem(label[i]);
        }
        this.getItems().addAll(items[0],items[1],items[2],items[3],items[4]);
        items[0].setOnAction(e->{
            handleInsert();
        });
        items[1].setOnAction(e->{
            handleUpdate();
        });
        items[2].setOnAction(e->{
            handleDelete();
        });
        items[3].setOnAction(e->{
            handleNavigate();
        });
        items[4].setOnAction(e->{
            handleSave();
        });
    }

    public void handleInsert(){
        updateLabel(null, false);
        VBox vBox = Style.createVBox();
        ComboBox<String> districtComboBox = new ComboBox<>();
        districtComboBox.getItems().addAll(districts);
        districtComboBox.setPromptText("Select New District");
        Style.style(districtComboBox);

        Button bt = new Button("Insert");
        GridPane gp = Style.createGridPane();
        gp.add(Style.createText("New District"),0,0);
        gp.add(districtComboBox,1,0);
        vBox.getChildren().addAll(gp,bt,label);
        Style.style(bt);
        bt.setOnAction(e->{
            if(districtComboBox.getValue()!=null) {
            if(Manager.getDistrictTree().find(districtComboBox.getValue())==null){
                    Manager.getDistrictTree().insert(districtComboBox.getValue());
                    updateLabel("District added successfully", true);
            }else{
                updateLabel("District already exists successfully",false);
            }
            }else{
                updateLabel("Select District Please", false);
            }
        });
        MainScreen.getSidePane().setCenter(vBox);
    }

    public void handleUpdate(){
        updateLabel(null, false);
        ComboBox<String> newDistrictCbo = new ComboBox<>();
        newDistrictCbo.setPromptText("Select New District");
        newDistrictCbo.getItems().addAll(districts);
        ComboBox<String> districtCbo = Style.getDistrictCbo();
        VBox vBox = Style.createVBox();
        Button bt = new Button("Update");
        GridPane gp = Style.createGridPane();
        gp.add(Style.createText("Current District"),0,0);
        gp.add(districtCbo,1,0);
        gp.add(Style.createText("New Distirct"),0,1);
        gp.add(newDistrictCbo,1,1);
        vBox.getChildren().addAll(gp,bt,label);
        Style.style(newDistrictCbo);
        Style.style(bt);
        bt.setOnAction(e->{
            if(value(newDistrictCbo, districtCbo) ) {
                if(Manager.getDistrictTree().find(newDistrictCbo.getValue())==null){
                    String oldDistrictName = String.valueOf(districtCbo.getValue());
                    String newDistrictName = newDistrictCbo.getValue();

                    DNode district = Manager.getDistrictTree().find(oldDistrictName);

                    district.setElement(newDistrictName);

                    Manager.getDistrictTree().remove(oldDistrictName);

                    Manager.getDistrictTree().insert(newDistrictName);


                    updateLabel("District updated successfully", true);
                }else{
                    updateLabel("District name already exists",false);
                }
            }

        });
        MainScreen.getSidePane().setCenter(vBox);
    }

    public boolean value(ComboBox<?> districtComboBox, ComboBox<?> districtComboBox1) {
        if (districtComboBox.getValue() == null && districtComboBox1.getValue() == null) {
            updateLabel("Select both districts please", false);
            return false;
        } else if (districtComboBox.getValue() == null) {
            updateLabel("Select new district please", false);
            return false;
        } else if (districtComboBox1.getValue() == null) {
            updateLabel("Select current district please", false);
            return false;
        } else {
            return true;
        }
    }
    /*
    A method to handle Delete,
     */

    public void handleDelete(){
        updateLabel(null, false);
        VBox vBox = Style.createVBox();
        Button bt = new Button("Delete");
        ComboBox<String> districtCbo = Style.getDistrictCbo();
        GridPane gp = Style.createGridPane();
        gp.add(Style.createText("Current District"),0,0);
        gp.add(districtCbo,1,0);
        vBox.getChildren().addAll(gp,bt,label);
        Style.style(bt);
        bt.setOnAction(e->{
                if(districtCbo.getValue()!=null) {
                        boolean result = Style.showConfirmation("Are you sure, you want to delete this item?" + "\n Name Of District: " + districtCbo.getValue() + "\nNumber Of Martyrs: " + Manager.getDistrictTree().totalNumberOfMartyrs(Manager.getDistrictTree().find(districtCbo.getValue())));
                        if(result) {
                            Manager.getDistrictTree().remove(districtCbo.getValue());
                            updateLabel("District removed successfully", true);
                        }
                }else{
                    updateLabel("Select District Please", false);
                }
        });
        MainScreen.getSidePane().setCenter(vBox);

    }


    /*
    A method that handles navigation, it uses two stacks that are ordered in order, meaning from smallest to largest, clicking on next
    would result to popping the element into a secondry stack, and clicking on previous would result to poping the element to the original stakc.
     */

    public static void handleNavigate() {
        updateLabel(null, false);
        HBox hb = Style.createHBox();
        Button prev = Style.createLeftTriangleButton();
        Button next = Style.createRightTriangleButton();
        prev.setPrefSize(45,45);
        next.setPrefSize(45,45);
        Label label1 = new Label("District");
        label1.setStyle("-fx-font-family: 'Segoe Print';-fx-font-size:20;-fx-background-color:white;");
        label1.setFont(Font.font("Arial",FontWeight.SEMI_BOLD,20));
        Label numberOfMartyrs = new Label("Number Of Martyrs: " + 0);
        numberOfMartyrs.setTextFill(Color.DARKRED);
        numberOfMartyrs.setStyle("-fx-font-family: 'Segoe Print';-fx-font-size:15;-fx-background-color:white;");

        VBox vBox = Style.createVBox();
        hb.getChildren().addAll(prev, label1, next);
        Button button = new Button("Load Location");
        StackList stack = Manager.getDistrictTree().inOrder();
        StackList previous = new StackList();
        Style.style(button);
        if(!stack.isEmpty()) {
            dNode = ((DNode) stack.pop());
            label1.setText(dNode.getElement());
            numberOfMartyrs.setText("Number Of Martyrs: " + dNode.getMartyrCounter(dNode));
            label1.setTextFill(Color.DARKRED);
            previous.push(dNode);
            if(dNode!=null) {
                next.setOnAction(e -> {
                    dNode = (DNode) stack.pop();
                    if (dNode != null) {
                        numberOfMartyrs.setText("Number Of Martyrs: " + dNode.getMartyrCounter(dNode));
                        label1.setText(dNode.getElement());
                        previous.push(dNode);
                    }

                });

                prev.setOnAction(e -> {
                    dNode = (DNode) previous.pop();
                    if (dNode != null) {
                        numberOfMartyrs.setText("Number Of Martyrs: " + dNode.getMartyrCounter(dNode));
                        label1.setText(dNode.getElement());
                        stack.push(dNode);
                    }
                });

                button.setOnAction(event -> {
                        LocationMenu.handleNavigate(dNode.getLocation().levelOrder());
                });
            }

        }
        numberOfMartyrs.setFont(Font.font("Arial",15));
        vBox.getChildren().addAll(hb,button,label,numberOfMartyrs);
        MainScreen.getSidePane().setCenter(vBox);
    }
    public void handleSave(){
        updateLabel(null, false);
        Button bt = new Button("Save");
        ComboBox<String> districtCbo = Style.getDistrictCbo();
        GridPane gp = Style.createGridPane();
        gp.add(Style.createText("Current District"),0,0);
        gp.add(districtCbo,1,0);
        VBox vBox = Style.createVBox();
        vBox.getChildren().addAll(gp,bt,label);
        MainScreen.getSidePane().setCenter(vBox);
        ObservableList<Martyr>  martyrObList = FXCollections.observableArrayList();
        Tab tab = new Tab();
        Style.style(bt);
        Style.style(bt);
        bt.setOnAction(e->{
            try {
                MainScreen.getTabPane().getTabs().remove(tab);
                if(districtCbo.getValue()==null){
                    updateLabel("Select District please",false);
                    return;
                }
                martyrObList.clear();
                tab.setText(districtCbo.getValue() + " Table");
                File f = new File(districtCbo.getValue()+".txt");
                PrintWriter pw = new PrintWriter(f); // creates a printWriter object to set the text of the file
                pw.print("Name," + "Date,"+ "Age," + "Location,"+ "District," + "Gender\n");
                List<Martyr> martyrs = Manager.getDistrictTree().collectMartyrsByDistrict(Manager.getDistrictTree().find(districtCbo.getValue()));
                MartyrTableView martyrTableView = new MartyrTableView(martyrObList);

                for (Martyr martyr : martyrs) {
                    pw.println(martyr.getName() + "," + martyr.getDate() + "," + martyr.getAge() + "," + martyr.getLocation() + "," + martyr.getDistrict() + "," + martyr.getGender());
                    martyrObList.add(martyr);
                }
                tab.setContent(martyrTableView);
                MainScreen.getTabPane().getTabs().add(tab);
                MainScreen.getTabPane().getSelectionModel().select(tab);
                updateLabel(districtCbo.getValue() + "'s information has been saved to the File:-" + "\n\n                           " + f.getName(),true);
                pw.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }


}
