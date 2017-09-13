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
        ImageIO.write(bufferedImage, "png", outputFile);
    }

    public void forEach(SingleARGBPixelProcessor processor) {
        for (ARGBPixel[] pixelRow : pixels) {
            for (ARGBPixel pixel : pixelRow) {
                processor.apply(pixel);
            }
        }
    }

    public BarChart<String, Number> createHistogram(String name) {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> barChart =
                new BarChart<String, Number>(xAxis,yAxis);
        barChart.setCategoryGap(0);
        barChart.setBarGap(0);

        xAxis.setLabel("Brightness");
        yAxis.setLabel("Amount");

        yAxis.setMaxHeight(50000);

        XYChart.Series series1 = new XYChart.Series();
        series1.setName(name);

        int[] data = new int[256];
        for (int i : data) i = 0;

        for (ARGBPixel[] pixelRow : pixels) {
            for (ARGBPixel pixel : pixelRow) {
                int brightness = Conv.btoi(pixel.brightness());
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
}
