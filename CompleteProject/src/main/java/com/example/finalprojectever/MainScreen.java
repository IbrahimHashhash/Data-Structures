package com.example.finalprojectever;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

public class MainScreen extends Application {
    private static final TabPane tabPane = new TabPane();
    private static final DateScreen dateTab = new DateScreen();
    @Override
    public void start(Stage stage)  {
        tabPane.getTabs().addAll(dateTab);
        tabPane.getSelectionModel().select(dateTab);
        dateTab.setStyle("-fx-background-color:lightSkyblue;");
        StackPane stackPane = new StackPane();
        Button button = new Button("Load");
        button.setPrefSize(100,50);
        Button exit = new Button("exit");
        exit.setPrefSize(100,50);
        Text text = Style.createText("Select Operation");
        VBox vBox = Style.createVBox();
        vBox.getChildren().addAll(text,button,exit);
        Style.style(button);
        Style.style(exit);
        stackPane.getChildren().add(vBox);
        button.setOnAction(e-> {
            handleLoad();

                if(Manager.getCounter()!=0){
                    stage.close();
        }
        });
        exit.setOnAction(e-> stage.close());
        Scene scene = new Scene(stackPane, 460, 420);
        stage.setTitle("FREE GAZA!");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void handleLoad(){ // a method to handle loading the file
        FileChooser fc = new FileChooser();
        File selectedFile = fc.showOpenDialog(null);
                if (selectedFile != null) {
                    String filePath = selectedFile.getAbsolutePath();
                    Manager.readFromFile(filePath);
                    if(Manager.getCounter()!=0){
                        Stage stage = new Stage();
                        Scene scene = new Scene(tabPane,700,600);
                        stage.setScene(scene);
                        stage.show();
                        stage.setMaximized(true);
            }
        } else {
            System.out.println("no file selected"); // if file not found then you print an error statement
        }
    }

    public static HBox SearchBar(TextField search,Button button){ // a gui component used for seaching martyrs by part of their name
        HBox hBox = new HBox();
        hBox.setStyle("-fx-background-color:lightSkyBlue;");
        hBox.setPadding(new Insets(5,5,5,5));
        search.setPromptText("Quick Search");
        button.setText("Enter");
        Style.style(button);
        Style.style(search);
        button.setPrefSize(60,20);
        search.setPrefWidth(300);
        hBox.setSpacing(5);
        hBox.getChildren().addAll(search,button);
        hBox.setAlignment(Pos.CENTER);
        return hBox;
    }
    public static TabPane getTabPane(){
        return tabPane;
    } // getter for tabpane




    public static void main(String[] args) {
        launch();
    }
}