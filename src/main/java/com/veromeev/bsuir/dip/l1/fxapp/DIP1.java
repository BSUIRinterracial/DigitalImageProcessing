package com.veromeev.bsuir.dip.l1.fxapp;

import com.veromeev.bsuir.dip.l1.util.ARGBImage;
import com.veromeev.bsuir.dip.l1.util.Conv;
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

        ARGBImage image = new ARGBImage(resourcesPath + "1gray.png");

        TestInputParameters t = new TestInputParameters();
        ArrayList<Double> parameters = t.getParameters();
        parameters.forEach(System.out::println);

        BarChart<String, Number> b1 = image.createHistogram("before");

        double c = parameters.get(0);
        double gamma = parameters.get(1);


        System.out.println(Conv.btoi(image.getPixel(14, 14).getA()));

        image.forEach(pixel -> {

            int r = Conv.btoi(pixel.getR());
            int g = Conv.btoi(pixel.getG());
            int b = Conv.btoi(pixel.getB());

            r = (int)(Math.pow((double)r, gamma) * c);
            g = (int)(Math.pow((double)g, gamma) * c);
            b = (int)(Math.pow((double)b, gamma) * c);

            pixel.setR(Conv.itob(r));
            pixel.setG(Conv.itob(g));
            pixel.setB(Conv.itob(b));

        });


        image.saveImage(resourcesPath + "2.png");

        double[][] frame = new double[][]{
                {1,-2, 1},
                {-2, 5, -2},
                {1, -2, 1}
        };

        image.filter(frame);

        image.saveImage(resourcesPath + "3.png");


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