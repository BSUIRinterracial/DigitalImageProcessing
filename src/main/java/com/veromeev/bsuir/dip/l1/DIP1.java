package com.veromeev.bsuir.dip.l1;

import javafx.application.Application;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DIP1 extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private final String resourcesPath = "./src/main/resources/";
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        File imgPath = new File(resourcesPath + "1.png");
        BufferedImage bufferedImage = ImageIO.read(imgPath);
        System.out.println("norm, che");

        PixelARGB p;
        ARGBImage image = new ARGBImage(resourcesPath + "1.png");
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                p = image.getPixel(i, j);
                int r = p.getR() & 0xFF;
                r += 10;
                r &= 0x000000ff;
                p.setR((byte)r);
            }
        }
        image.saveImage(resourcesPath + "2.png");


    }
}
