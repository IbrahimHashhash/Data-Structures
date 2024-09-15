package all;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
/*
 This is the MainScreen class it contains the main components that make the GUI, SidePane, TableView, MenuBars
 */
public class MainScreen extends Application {
    private static final SidePane sp = new SidePane();
    private static String filePath = "";
    @Override
    public void start(Stage stage)  { // overriden start method
        BorderPane bp = new BorderPane();
        HBox hb = new HBox();
        // Declaring and Assigning MenuBars
        MenuBar mb1 = new MenuBar(new DistrictMenu());
        MenuBar mb2 = new MenuBar(new LocationMenu());
        MenuBar mb3 = new MenuBar(fcMenu());
        HBox.setHgrow(mb3, Priority.ALWAYS); // set the Hgrow for MenuBar to always, so it fills the Top
        Manager.getDistrictComboBox().setPromptText("Select District"); // selects district
        LocationMenu.genderComboBox.setPromptText("Select gender"); // selects gender
        mb1.setPrefWidth(120); mb2.setPrefWidth(135); // sets the width of mb1
        hb.getChildren().addAll(mb1,mb2,mb3);
        bp.setTop(hb);
        SplitPane splitPane = new SplitPane();
        splitPane.getItems().addAll(MartyrTableView.initialize(),sp);// adds items to the table view
        Scene scene = new Scene(bp,400,400);
        bp.setCenter(splitPane);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
        stage.setTitle("B'tselem Management System");
        splitPane.setDividerPositions(0.66); // sets the split pane divider at position 70%
    }

    public static SidePane getSp() {
        return sp;
    }

    public static Menu fcMenu() { // a method to returns the file menu
        MenuItem menuItem1 = createLoadMenuItem();
        MenuItem menuItem2 = createSaveAllMenuItem();
        Menu menu = new Menu("File");
        menu.getItems().addAll(menuItem1, menuItem2);
        return menu;
    }

    private static MenuItem createLoadMenuItem() { // creates the menu item
        MenuItem menuItem = new MenuItem("Load");
        menuItem.setOnAction(e -> handleLoadAction());
        return menuItem;
    }


    public static void handleLoadAction() { // a method to load the file into the system
        FileChooser fc = new FileChooser();
        File selectedFile = fc.showOpenDialog(null);
        if (selectedFile != null) {
            Manager.readFromFile(selectedFile.getAbsolutePath());
            System.out.println(Manager.getDistricts().lastIndexOf(new District("al-quds")));
            filePath = selectedFile.getAbsolutePath();
        } else {
            System.out.println("no file selected");
        }
    }

    private static MenuItem createSaveAllMenuItem() { // returns "save all" menu
        MenuItem menuItem = new MenuItem("Save All");
        menuItem.setOnAction(e -> {
            if (filePath != null) {
                try (PrintWriter pw = new PrintWriter(filePath)) {
                    MartyrList martyrList = Manager.getDistricts().getAllMartyrs();
                    MartyrNode current = martyrList.getFront();

                    while (current != null) {
                        Martyr martyr = (Martyr) current.getElement();
                        pw.println(martyr.getName() + "," + martyr.getDate() + "," + martyr.getAge() + "," + martyr.getLocation() + "," + martyr.getDistrict() + "," + martyr.getGender());
                        current = current.getNext();
                    }
                } catch (FileNotFoundException ex) {
                    System.out.println(ex.toString());
                }
            }
        });
        return menuItem;
    }


    public static void main(String[] args){
        launch(args);
    }
}


