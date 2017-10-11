package com.veromeev.bsuir.dip.l1.util;

import java.util.ArrayList;

public class ImageProcessing {

    private ImageProcessing() {

    }

    public static void binarization(ARGBImage image, int threshold) {
        image.forEach(pixel -> {
                if (pixel.brightness() < threshold) {
                    pixel.forEachRGBChannel(channel -> 0);
                } else {
                    pixel.forEachRGBChannel(channel -> 255);
                }
        });
    }
    public static void medianFilter(ARGBImage image, int windowSize, int iterations) {
        for (int o = 0; o < iterations; o++) {
            image.filter(windowSize, frame ->{
                ARGBPixel p;
                ArrayList<ARGBPixel> pixels = new ArrayList<>(frame.length*frame.length);
                for (int i = 0; i < frame.length; i++) {
                    for (int j = 0; j < frame.length; j++) {
                        pixels.add(frame[i][j]);
                    }
                }
                pixels.sort(ARGBPixel::compareTo);
                p = pixels.get(pixels.size() / 2);
                return p;
            });
        }
    }

    public static void binarizationThroughMedianFilter(ARGBImage image, int threshold, int windowSize, int medianFilterIterations) {
        medianFilter(image, windowSize, medianFilterIterations);
        binarization(image, threshold);
    }

    public static void minFilter(ARGBImage image, int windowSize, int iterations) {
        for (int o = 0; o < iterations; o++) {
            image.filter(windowSize, frame ->{
                ARGBPixel minp = frame[0][0];
                for (int i = 0; i < frame.length; i++) {
                    for (int j = 0; j < frame.length; j++) {
                        if (minp.brightness() > frame[i][j].brightness()) minp = frame[i][j];
                    }
                }
                return minp;
            });
        }
    }

    public static void morphCompress(ARGBImage image, int windowSize, int iterations) {
        minFilter(image, windowSize, iterations);
    }

    public static void maxFilter(ARGBImage image, int windowSize, int iterations) {
        for (int o = 0; o < iterations; o++) {
            image.filter(windowSize, frame ->{
                ARGBPixel maxp = frame[0][0];
                for (int i = 0; i < frame.length; i++) {
                    for (int j = 0; j < frame.length; j++) {
                        if (maxp.brightness() < frame[i][j].brightness()) maxp = frame[i][j];
                    }
                }
                return maxp;
            });
        }
    }

    public static void moprhExpand(ARGBImage image, int windowSize, int iterations) {
        maxFilter(image, windowSize, iterations);
    }

}
