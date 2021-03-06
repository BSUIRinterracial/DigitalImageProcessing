package com.veromeev.bsuir.dip.l1.fxapp;

import com.veromeev.bsuir.dip.l1.hopfield.HopfieldNetwork;
import com.veromeev.bsuir.dip.l1.util.ARGBImage;
import com.veromeev.bsuir.dip.l1.util.ImageProcessing;
import com.veromeev.bsuir.dip.l1.util.NoisyImageCreator;
import com.veromeev.bsuir.dip.l1.util.cluster.ImageClusterization;
import com.veromeev.bsuir.dip.l1.util.cluster.ImageScanner;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class DIP1 extends Application {

    public static final String resourcesPath = "./src/main/resources/";
    public static final String hopfieldPath = resourcesPath + "hopfield/";



    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        hopfield();
//        noise();
        System.out.println("got it");
    }

    private void noise() throws IOException {
        NoisyImageCreator c = new NoisyImageCreator();
        c.createNoisedImages(resourcesPath + "hopfield/a");
        c.createNoisedImages(resourcesPath + "hopfield/d");
        c.createNoisedImages(resourcesPath + "hopfield/h");
        c.createNoisedImages(resourcesPath + "hopfield/i");
        c.createNoisedImages(resourcesPath + "hopfield/k");
        c.createNoisedImages(resourcesPath + "hopfield/n");
    }

    private void clusterize() throws Exception {

        for (int i = 0; i < 10; i++) {
//        int i = 0;
        ARGBImage image = new ARGBImage(resourcesPath + "easy/"+i+".jpg");

        ImageProcessing.medianFilter(image,  3, 2);

        image.saveImage(resourcesPath + "easy/"+i+"_medi.png");

        ImageProcessing.binarization(image, 175);

        image.saveImage(resourcesPath + "easy/"+i+"_medi_bin.png");

        ImageProcessing.morphCompress(image, 7, 1);

        image.saveImage(resourcesPath + "easy/"+i+"_medi_bin_morcom.png");

        ImageProcessing.moprhExpand(image, 3, 3);

        image.saveImage(resourcesPath + "easy/"+i+"_medi_bin_morcom_morexp.png");

        ImageScanner scanner = new ImageScanner(image);
        scanner.scan();
        scanner.colorizeRegions();

        image.saveImage(resourcesPath + "easy/"+i+"_medi_bin_morcom_morexp_region.png");

//            scanner.paintAllRegionsBlack();
//            image.saveImage(resourcesPath + "easy/"+i+"_medi_bin_morcom_morexp_all_region_black.png");

        scanner.createRegionObjects();
        scanner.viewRegions();
        new ImageClusterization(scanner, 2).clusterization().colorizeClusters();

        image.saveImage(resourcesPath + "easy/"+i+"_medi_bin_morcom_morexp_region_cluster_init.png");

        }

    }


    private void hopfield() {
        try {
            String[] names = new String[] {"a","i","k", "h", "n"};
            HopfieldNetwork network = new HopfieldNetwork(names);
            for (String name : names) {
                for (int j = 1; j < 11; j++) {
                    System.out.println(name + j);
                    network.recognizeForm(
                            new ARGBImage(hopfieldPath + name + "_" + j + ".png"))
                            .saveImage(hopfieldPath + name + "_" + j + "_resl.png");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
