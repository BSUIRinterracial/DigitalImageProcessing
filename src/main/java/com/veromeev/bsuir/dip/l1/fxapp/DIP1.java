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
        System.out.println("got it");
    }

    private void noise() throws IOException {
        NoisyImageCreator c = new NoisyImageCreator();
        c.createNoisedImages(resourcesPath + "hopfield/n_letter");
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
            HopfieldNetwork network = new HopfieldNetwork();
            network.train();
            String[] names = new String[] {"a_","i_","k_"};
            for (int i = 0; i <names.length ; i++) {
                for (int j = 1; j < 11; j++) {
                    network.recognizeForm(new ARGBImage(hopfieldPath + names[i] + j + ".png")).saveImage(hopfieldPath + names[i] + j + "_resl.png");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
