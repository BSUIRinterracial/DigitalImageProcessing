package com.veromeev.bsuir.dip.l1.util;

import java.io.IOException;
import java.util.Random;

public class NoisyImageCreator {

    public void createNoisedImages(String fileNameWithoutFormat) throws IOException {

        for (int i = 1; i < 11; i++) {
            ARGBImage image = new ARGBImage(fileNameWithoutFormat + ".png");
            int square = image.square();
            int[] pixelsToReverse = randomInts(square, square * i / 10);
            for (int pixelNumber : pixelsToReverse) {
                int x = pixelNumber / image.width();
                int y = pixelNumber % image.width();
                ARGBPixel p = image.pixel(x, y);
                if (p.brightness() > 250) {
                    p.setRGB(0, 0, 0);
                } else  {
                    p.setRGB(255, 255, 255);
                }
            }
            image.saveImage(fileNameWithoutFormat + "_" + i + ".png");
        }

    }

    private int[] randomInts(int limit, int arrayLength) {
        return new Random(1)
                .ints(0, limit)
                .distinct()
                .limit(arrayLength)
                .sorted()
                .toArray();
    }
}
