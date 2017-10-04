package com.veromeev.bsuir.dip.l1.fxapp;

import com.veromeev.bsuir.dip.l1.util.ARGBImage;
import com.veromeev.bsuir.dip.l1.util.ImageProcessing;
import com.veromeev.bsuir.dip.l1.util.cluster.ImageScanner;
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

//        for (int i = 0; i < 10; i++) {
        int i = 0;
            ARGBImage image = new ARGBImage(resourcesPath + "easy/"+i+".jpg");

            ImageProcessing.medianFilter(image,  3, 2);

            image.saveImage(resourcesPath + "easy/"+i+"_medi.png");

            ImageProcessing.binarization(image, 175);

            image.saveImage(resourcesPath + "easy/"+i+"_medi_bin.png");

            ImageProcessing.morphCompress(image, 7, 1);

            image.saveImage(resourcesPath + "easy/"+i+"_medi_bin_morcom.png");

            ImageProcessing.moprhExpand(image, 3, 2);

            image.saveImage(resourcesPath + "easy/"+i+"_medi_bin_morcom_morexp.png");

        ImageScanner scanner = new ImageScanner(image, 100);
        scanner.scan();

        image.saveImage(resourcesPath + "easy/"+i+"_medi_bin_morcom_morexp_region.png");

//        }

        System.out.println("got it");
    }
}
