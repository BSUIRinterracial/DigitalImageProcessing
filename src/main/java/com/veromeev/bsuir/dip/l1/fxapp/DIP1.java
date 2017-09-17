package com.veromeev.bsuir.dip.l1.fxapp;

import com.veromeev.bsuir.dip.l1.util.ARGBImage;
import com.veromeev.bsuir.dip.l1.util.ARGBPixel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class DIP1 extends Application {

    private final String resourcesPath = "./src/main/resources/";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        ARGBImage image = new ARGBImage(resourcesPath + "10.jpg");

        TestInputParameters t = new TestInputParameters();
        ArrayList<Double> parameters = t.getParameters();

        BarChart<String, Number> b1 = image.createHistogram("before");

        double c = parameters.get(0);
        double gamma = parameters.get(1);

        image.forEach(channel -> (int)(Math.pow((double)channel, gamma) * c));


        image.saveImage(resourcesPath + "11.jpg");

        double[][] frame = new double[][]{
                {1, 0, -1},
                {2, 0, -2},
                {1, 0, -1}
        };

        double[][] frame2 = new double[][]{
                {-1, -2, -1},
                {0, 0, 0},
                {1, 2, 1}
        };

        image.filter(frame, frame2, (response1, response2) -> {

            int r1 = response1.getR();
            int g1 = response1.getG();
            int b11 = response1.getB();

            int r2 = response2.getR();
            int g2 = response2.getG();
            int b2 = response2.getB();

            int r = (int)Math.sqrt((r1*r1+r2*r2));
            int g = (int)Math.sqrt((g1*g1+g2*g2));
            int b = (int)Math.sqrt((b11 * b11 +b2*b2));

            ARGBPixel pixel = new ARGBPixel();
            pixel.setRGB(r,g,b);
            return pixel;
        });

        image.saveImage(resourcesPath + "12.jpg");

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