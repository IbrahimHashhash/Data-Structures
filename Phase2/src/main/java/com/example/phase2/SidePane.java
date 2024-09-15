package com.example.phase2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.List;

public class SidePane extends BorderPane {
    private static ImageView backgroundImage;
    private final HBox buttomBox = new HBox();
    public SidePane(){
        HBox hb = new HBox();
        Button helpButton = new Button("?");
        Style.style(helpButton);
        VBox vBox2 = Style.help();
        helpButton.setOnAction(e->{
            this.setCenter(vBox2);
        });
        TextField quickSearch = new TextField();
        quickSearch.setPromptText("Quick Search");
        HBox search = new HBox();
        search.setSpacing(5);
        Style.style(quickSearch);
        quickSearch.setPrefWidth(300);
        Button s = new Button("Search");
        Style.style(s);
        HBox nav = new HBox();
        search.getChildren().addAll(quickSearch,s);
        hb.setPadding(new Insets(5,5,5,5));
        hb.setAlignment(Pos.BASELINE_RIGHT);
        this.setPadding(new Insets(5, 0, 5, 0)); // Padding: top, right, bottom, left
        Button right = Style.createRightTriangleButton();
        Button left = Style.createLeftTriangleButton();
        nav.getChildren().addAll(left,right);
        nav.setSpacing(10);
        hb.getChildren().addAll(nav);
        hb.setSpacing(60);
        VBox vBox = mainManeu();
        this.setCenter(vBox);
        left.setOnAction(e->{
            this.setCenter(vBox);
        });
        buttomBox.getChildren().add(helpButton);
        buttomBox.setSpacing(10);
        buttomBox.setPadding(new Insets(3,3,3,3));
        this.setBottom(buttomBox);
        HBox hBox  = new HBox();
        hBox.getChildren().addAll(search);
        VBox tabVBox = new VBox();
        this.setTop(hb);
    }

    public VBox mainManeu(){
        VBox vBox = Style.createVBox();
        Button bt1 = new Button("Load File");
        bt1.setStyle("-fx-font-size: 14px;");
        Button bt2 = new Button("Total Statistics");
        bt2.setStyle("-fx-font-size: 14px;");
        Button bt3 = new Button("Clear All");
        bt3.setStyle("-fx-font-size: 14px;");
        Button bt4 = new Button("Background Image");
        bt4.setStyle("-fx-font-size: 14px;");
        Button bt5 = new Button("X");
        bt1.setPrefSize(145,50);
        bt2.setPrefSize(145,50);
        bt3.setPrefSize(145,50);
        bt4.setPrefSize(145,50);
        Style.style(bt1);
        Style.style(bt2);
        Style.style(bt3);
        Style.style(bt4);
        Style.style(bt5);
        Text text = new Text("Select Operation");
        text.setStyle("-fx-text-fill: darkred; -fx-font-family: 'Segoe Print';-fx-font-size:20;-fx-font-weight:bold;");
        text.setFill(Color.DARKRED);
        bt1.setOnAction(e->{
            FileMenu.handleLoad();
        });
        bt2.setOnAction(e->{

        });
        bt3.setOnAction(e->{
            boolean result = Style.showConfirmation("Are you sure you want to clear everything?");
            if(result){
                Manager.getDistrictTree().clear();
            }else{

            }

        });
        bt4.setOnAction(e->{
            FileChooser fc = new FileChooser();
            File selectedFile = fc.showOpenDialog(null);
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.jpg");
            fc.getExtensionFilters().add(extFilter);
            if (selectedFile != null) {
                setBackgroundImage(selectedFile);
            } else {
                System.out.println("no file selected");
            }
        });
        bt5.setOnAction(e->{
            if (backgroundImage != null) {
                this.getChildren().remove(backgroundImage);
                backgroundImage = null;
            }
        });
        HBox hb = Style.palestineColors();
        hb.setAlignment(Pos.CENTER);
        buttomBox.getChildren().add(bt5);
        vBox.getChildren().addAll(text,bt1,bt2,bt3,bt4,hb);
        return vBox;
    }

    private void setBackgroundImage(File file) {
        Image image = new Image(file.getAbsolutePath());
        if (backgroundImage == null) {
            backgroundImage = new ImageView();
            backgroundImage.fitWidthProperty().bind(this.widthProperty());
            backgroundImage.fitHeightProperty().bind(this.heightProperty());
            this.getChildren().add(0, backgroundImage); // Add the background image as the first child
        }
        backgroundImage.setImage(image);
    }

}
