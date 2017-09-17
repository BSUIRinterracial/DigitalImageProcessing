package com.veromeev.bsuir.dip.l1.util;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ARGBImage {
    private ARGBPixel[][] pixels;
    private int width;
    private int height;
    private BufferedImage bufferedImage;

    public ARGBImage(String filename) throws IOException {
        File imgPath = new File(filename);
        bufferedImage = ImageIO.read(imgPath);
        width = bufferedImage.getWidth();
        height = bufferedImage.getHeight();
        pixels = new ARGBPixel[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                pixels[i][j] = new ARGBPixel(bufferedImage.getRGB(i, j));
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ARGBPixel getPixel(int x, int y) {
        return pixels[x][y];
    }

    public void saveImage(String filename) throws IOException {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                bufferedImage.setRGB(i, j, pixels[i][j].toARGBInteger());
            }
        }
        File outputFile = new File(filename);
        ImageIO.write(bufferedImage, "jpg", outputFile);
    }

    public void forEach(SingleARGBPixelProcessor processor) {
        for (ARGBPixel[] pixelRow : pixels) {
            for (ARGBPixel pixel : pixelRow) {
                processor.apply(pixel);
            }
        }
    }
    public void forEach(ChannelProcessor processor) {
        for (ARGBPixel[] pixelRow : pixels) {
            for (ARGBPixel pixel : pixelRow) {
                pixel.forEachRGBChannel(processor);
            }
        }
    }

    public BarChart<String, Number> createHistogram(String name) {

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis(0, 20000, 100);

        final BarChart<String,Number> barChart =
                new BarChart<>(xAxis, yAxis);

        barChart.setCategoryGap(0);
        barChart.setBarGap(0);

        xAxis.setLabel("Brightness");
        yAxis.setLabel("Amount");

        yAxis.setPrefHeight(1000);

        XYChart.Series series1 = new XYChart.Series();
        series1.setName(name);

        int[] data = new int[256];

        for (ARGBPixel[] pixelRow : pixels) {
            for (ARGBPixel pixel : pixelRow) {
                int brightness = pixel.brightness();
                data[brightness] ++;
            }
        }

        for (int i = 0; i < data.length; i++) {
            String s = i%10 == 0 ? Integer.toString(i) : "";
            series1.getData().add(new XYChart.Data(s, data[i]));
        }

        barChart.getData().addAll(series1);
        return barChart;
    }

    public void filter(double[][] frame) {
        pixels = createFilteredImage(frame);
    }

    public void filter(double[][] f1, double[][] f2, TwoFrameResponseProcessor p) {
        ARGBPixel[][] i1 = createFilteredImage(f1);
        ARGBPixel[][] i2 = createFilteredImage(f2);

        ARGBPixel[][] newPixels = new ARGBPixel[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                newPixels[i][j] = p.process(i1[i][j], i2[i][j]);
            }
        }
        pixels = newPixels;
    }


    private ARGBPixel[][] createFilteredImage(double[][] frame) {
        ARGBPixel[][] newPixels = new ARGBPixel[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                newPixels[i][j] = new ARGBPixel();
            }
        }
        for (int i = 1; i < width-1; i++) {
            for (int j = 1; j < height-1; j++) {
                filterPixel(frame, i, j, newPixels);
            }
        }
        return newPixels;
    }

    private void filterPixel(double[][] frame, int x, int y, ARGBPixel[][] newPixels) {

        int r = (int)(frame[0][0] * (double)pixels[x-1][y-1].getR()) +
                (int)(frame[0][1] * (double)pixels[x-1][y].getR()) +
                (int)(frame[0][2] * (double)pixels[x-1][y+1].getR()) +
                (int)(frame[1][0] * (double)pixels[x][y-1].getR()) +
                (int)(frame[1][1] * (double)pixels[x][y].getR()) +
                (int)(frame[1][2] * (double)pixels[x][y+1].getR()) +
                (int)(frame[2][0] * (double)pixels[x+1][y-1].getR()) +
                (int)(frame[2][1] * (double)pixels[x+1][y].getR()) +
                (int)(frame[2][2] * (double)pixels[x+1][y+1].getR());

        int g = (int)(frame[0][0] * (double)pixels[x-1][y-1].getG()) +
                (int)(frame[0][1] * (double)pixels[x-1][y].getG()) +
                (int)(frame[0][2] * (double)pixels[x-1][y+1].getG()) +
                (int)(frame[1][0] * (double)pixels[x][y-1].getG()) +
                (int)(frame[1][1] * (double)pixels[x][y].getG()) +
                (int)(frame[1][2] * (double)pixels[x][y+1].getG()) +
                (int)(frame[2][0] * (double)pixels[x+1][y-1].getG()) +
                (int)(frame[2][1] * (double)pixels[x+1][y].getG()) +
                (int)(frame[2][2] * (double)pixels[x+1][y+1].getG());

        int b = (int)(frame[0][0] * (double)pixels[x-1][y-1].getB()) +
                (int)(frame[0][1] * (double)pixels[x-1][y].getB()) +
                (int)(frame[0][2] * (double)pixels[x-1][y+1].getB()) +
                (int)(frame[1][0] * (double)pixels[x][y-1].getB()) +
                (int)(frame[1][1] * (double)pixels[x][y].getB()) +
                (int)(frame[1][2] * (double)pixels[x][y+1].getB()) +
                (int)(frame[2][0] * (double)pixels[x+1][y-1].getB()) +
                (int)(frame[2][1] * (double)pixels[x+1][y].getB()) +
                (int)(frame[2][2] * (double)pixels[x+1][y+1].getB());

        newPixels[x][y].setR(r);
        newPixels[x][y].setG(g);
        newPixels[x][y].setB(b);

    }


}
