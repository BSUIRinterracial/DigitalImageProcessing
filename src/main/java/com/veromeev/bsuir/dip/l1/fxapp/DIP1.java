package com.veromeev.bsuir.dip.l1.fxapp;

import com.veromeev.bsuir.dip.l1.util.ARGBImage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class DIP1 extends Application {

    private final String resourcesPath = "./src/main/resources/";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        ARGBImage image = new ARGBImage(resourcesPath + "1.png");

        BarChart<String, Number> b1 = image.createHistogram("before");

        image.forEach(pixel -> {
            byte gray = pixel.brightness();
            pixel.setR(gray);
            pixel.setG(gray);
            pixel.setB(gray);
        });
        image.saveImage(resourcesPath + "2.png");

        BarChart<String, Number> b2 = image.createHistogram("after");


        VBox vBox = new VBox();
        vBox.getChildren().addAll(b1, b2);

        StackPane root = new StackPane();
        root.getChildren().add(vBox);

        Scene scene = new Scene(root, 800, 450);

        primaryStage.setTitle("dip");
        primaryStage.setScene(scene);
        primaryStage.show();

        System.out.println("end processing");
    }




}