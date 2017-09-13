package com.veromeev.bsuir.dip.l1.fxapp;

import com.veromeev.bsuir.dip.l1.util.ARGBImage;
import com.veromeev.bsuir.dip.l1.util.Conv;
import javafx.application.Application;
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

        image.forEach(pixel -> {
            int r = Conv.btoi(pixel.getR());
            int g = Conv.btoi(pixel.getG());
            int b = Conv.btoi(pixel.getB());

            int gray = (int) ( ((double)r) * 0.3 + ((double) g) * 0.59 + ((double) b) * 0.11);

            pixel.setR(Conv.itob(gray));
            pixel.setG(Conv.itob(gray));
            pixel.setB(Conv.itob(gray));
        });
        image.saveImage(resourcesPath + "2.png");

        System.out.println("end processing");
        primaryStage.close();
    }



}
