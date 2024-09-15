package com.example.phase2;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class MainScreen extends Application {
    private final SplitPane splitPane = new SplitPane();
    private static final TabPane tabPane = new TabPane();
    private final BorderPane root = new BorderPane();
    private static final SidePane sidePane = new SidePane();
    private final MartyrTableView martyrTableView = new MartyrTableView(Style.getMartyrObList());
    private final static HBox hb = new HBox();
    private static final ContextMenu contextMenu = new ContextMenu();
    @Override
    public void start(Stage stage) {
        MenuItem update = new MenuItem("Update"); // an update button to update a martyr
        MenuItem delete = new MenuItem("Delete"); // a delete button to delete a martyr
        martyrTableView.setOnMouseClicked(e -> {
            contextMenu.show(martyrTableView, e.getScreenX(), e.getScreenY()); // pops up a menu that contains delete and update when clicking on the table view
            Martyr selectedPerson = martyrTableView.getSelectionModel().getSelectedItem();
            if(selectedPerson!=null) { // if selected person didn't equal to null
                MartyrMenu.name = selectedPerson.getName();
                MartyrMenu.district = selectedPerson.getDistrict();
                MartyrMenu.location = selectedPerson.getLocation();
                MartyrMenu.date = selectedPerson.getDate();
            }
        });
        update.setOnAction(e -> { // handing the update logic, it just takes the information from the table view item and it updates it
            Martyr martyr = martyrTableView.getSelectionModel().getSelectedItem();
            if(martyr!=null) {
                try {
                    Style.showWarning("You are updating " + martyr.getName());
                    DNode district = Manager.getDistrictTree().find(martyr.getDistrict());
                    LNode lNode = district.getLocation().find(martyr.getLocation());
                    BSDateNode bsDateNode = lNode.getDate().find(Manager.localDate(martyr.getDate()));
                    MartyrMenu.handleUpdates(martyr, bsDateNode, lNode, district);
                }catch (NullPointerException ex){
                    System.out.println(ex.toString());
                }
            }
        });
        delete.setOnAction(e -> {
            Martyr martyr = martyrTableView.getSelectionModel().getSelectedItem();
            if (martyr!=null){
                try {
                DNode district = Manager.getDistrictTree().find(martyr.getDistrict());
                LNode lNode = district.getLocation().find(martyr.getLocation());
                    String s = "Are you sure you want to delete this item?" + "\nName: " + martyr.getName() + "\nLocation: " + martyr.getLocation() + "\nDistrict: " + martyr.getDistrict() + "\nDate: " + martyr.getDate();

                    boolean result = Style.showConfirmation(s);
                if (result) {
                    lNode.getDate().find(Manager.localDate(MartyrMenu.date)).getHead().remove(martyr.getName());
                }
                }catch (NullPointerException ex){
                    System.out.println(ex.toString());
                }
            }
        });

        contextMenu.getItems().addAll(update, delete);
        Tab mainTab = new Tab("Display Screen");
        mainTab.setClosable(false);
        mainTab.setContent(getSearchBar());
        tabPane.getTabs().add(mainTab);
        root.setCenter(splitPane);
        splitPane.getItems().addAll(tabPane, sidePane);
        MenuBar mb1 = new MenuBar(new DistrictMenu());
        Style.styleMenu(mb1);
        MenuBar mb2 = new MenuBar(new LocationMenu());
        Style.styleMenu(mb2);
        MenuBar mb3 = new MenuBar(new MartyrMenu());
        Style.styleMenu(mb3);
        MenuBar mb4 = new MenuBar(new FileMenu());
        Style.styleMenu(mb4);
        HBox.setHgrow(mb4, Priority.ALWAYS); // set the Hgrow for MenuBar to always, so it fills the Top
        mb1.setPrefWidth(150);
        mb2.setPrefWidth(150);
        mb3.setPrefWidth(150); // sets the width of mb1
        hb.getChildren().addAll(mb1, mb2, mb3, mb4);
        root.setTop(hb);
        Scene scene = new Scene(root, 600, 600);
        stage.setTitle("B'Tselem");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
        splitPane.setDividerPositions(0.61); // sets the split pane divider at position 70%
    }
    // this method is used to get the search bar, it basically takes a Martyr's part name, and it searches that name in the tree.
    public VBox getSearchBar(){
        Button s = new Button("Search");
        Style.style(s);
        TextField quickSearch = new TextField();
        quickSearch.setPromptText("Quick Search...");
        quickSearch.setPrefWidth(300);
        Style.style(quickSearch);
        s.setOnAction(e -> {
            Style.getMartyrObList().clear();
            List<Martyr> martyrList = Manager.getDistrictTree().searchMartyr(quickSearch.getText());
            Style.getMartyrObList().addAll(martyrList);
        });
        VBox tabVBox = new VBox();
        VBox.setVgrow(martyrTableView, Priority.ALWAYS);
        HBox hBox = new HBox();
        hBox.setSpacing(15);
        hBox.setPadding(new Insets(2, 2, 2, 300));
        hBox.getChildren().addAll(quickSearch,s);
        tabVBox.getChildren().addAll(martyrTableView,hBox);
        return tabVBox;
    }

    public static SidePane getSidePane() { // a static method that returns side pane
        return sidePane;
    }
    public static TabPane getTabPane(){ // a static method that returns tabPane
        return tabPane;
    }
    public static void main(String[] args) { // a main method
        launch();
    }
}