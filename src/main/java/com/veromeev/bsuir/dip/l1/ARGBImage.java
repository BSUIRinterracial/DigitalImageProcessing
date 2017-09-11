package com.veromeev.bsuir.dip.l1;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by jack on 9/11/17.
 *
 * @author Jack Veromeyev
 */
public class ARGBImage {
    private PixelARGB[][] pixels;
    private int width;
    private int height;
    private BufferedImage bufferedImage;

    public ARGBImage(String filename) throws IOException {
        File imgPath = new File(filename);
        bufferedImage = ImageIO.read(imgPath);
        width = bufferedImage.getWidth();
        height = bufferedImage.getHeight();
        pixels = new PixelARGB[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                pixels[i][j] = new PixelARGB(bufferedImage.getRGB(i, j));
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public PixelARGB getPixel(int x, int y) {
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
}
