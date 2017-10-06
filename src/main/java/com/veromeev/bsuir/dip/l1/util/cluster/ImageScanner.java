package com.veromeev.bsuir.dip.l1.util.cluster;

import com.veromeev.bsuir.dip.l1.util.ARGBImage;
import com.veromeev.bsuir.dip.l1.util.ARGBPixel;
import lombok.val;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by jack on 9/28/17.
 *
 * @author Jack Veromeyev
 */
public class ImageScanner {

    final ARGBImage image;

    List<ImageRegion> regions = new ArrayList<>();
    /**
     * Maps image pixels to paticular region. Pixel at (x,y) is in
     * {@code regionField[x][y]} region
     */
    private final int[][] regionField;

    private boolean scaned = false;
    private int regionAmount = 0;

    public ImageScanner(ARGBImage image) {
        this.image = image;
        regionField = new int[image.width()][image.heght()];
    }

    public int scan() throws Exception {
        int currentRegion = 1;
        /* remembers a number that used to remember region.
         * Because of algorithm numbers are used uncontinually. So it can appear
         * thar regions have numbers 1,2,18,200
        */

        int regionedPixels = 0;
        for (int x = 0; x < image.width(); x++) {
            for (int y = 0; y < image.heght(); y++) {
                if (image.pixel(x, y).isWhite()) {
                    int topNeighbourRegion = regionField[x][y - 1];
                    int leftNeighbourRegion = regionField[x - 1][y];
                    if (topNeighbourRegion == 0 && leftNeighbourRegion == 0) {
                        regionField[x][y] = currentRegion;
                        currentRegion ++;
                    } else if (topNeighbourRegion == 0 && leftNeighbourRegion != 0) {
                        regionField[x][y] = leftNeighbourRegion;
                    } else if (topNeighbourRegion != 0 && leftNeighbourRegion == 0) {
                        regionField[x][y] = topNeighbourRegion;
                    } else if (topNeighbourRegion != 0 && leftNeighbourRegion != 0) {
                        if (topNeighbourRegion == leftNeighbourRegion) {
                            regionField[x][y] = topNeighbourRegion;
                        } else if (topNeighbourRegion != leftNeighbourRegion) {
//                            System.out.println("("+x+","+y+") conflict situation: " + topNeighbourRegion + " and " + leftNeighbourRegion);
                            for (int x1 = 0; x1 < image.width(); x1++) {
                                for (int y1 = 0; y1 < image.heght(); y1++) {
                                    if (regionField[x1][y1] == topNeighbourRegion) {
                                        regionField[x1][y1] = leftNeighbourRegion;
                                    }
                                }
                            }
                            regionField[x][y] = leftNeighbourRegion;
                        }
                    }
                    regionedPixels++;
                }
            }
        }

        val regionNumberSet = new HashSet<Integer>();
        for (int x = 0; x < image.width(); x++) {
            for (int y = 0; y < image.heght(); y++) {
                if (regionField[x][y] != 0) {
                    System.out.println("(" + x + "," + y + ") -> " + regionField[x][y]);
                    regionNumberSet.add(regionField[x][y]);
                }
            }
        }
//        System.out.println();
//        System.out.println(regionNumberSet.size() + " regions");
//        System.out.println("press enter to continue and normalize");
        val regionNumberList = new ArrayList<Integer>(regionNumberSet);
//        System.out.println("region numbers: " + regionNumberList) ;
//
//        System.in.read();
        /*
         * convert region numbers from {1,2,18,200} to {1,2,3,4}
         */

        for (int x = 0; x < image.width(); x++) {
            for (int y = 0; y < image.heght(); y++) {
                if (regionField[x][y] != 0) {
                    regionField[x][y] = regionNumberList.indexOf(regionField[x][y]) + 1;
                }
            }
        }
//        for (int x = 0; x < image.width(); x++) {
//            for (int y = 0; y < image.heght(); y++) {
//                if (regionField[x][y] != 0) System.out.println(" (" + x + "," + y + ") -> " + regionField[x][y]);
//            }
//        }


        scaned = true;
        return regionAmount = regionNumberList.size();
    }

    public void colorizeRegions() throws Exception {
        if (!scaned) {
            scan();
        }
        ARGBPixel[] colors = new ARGBPixel[regionAmount];
        int coef = 200 / (regionAmount / 3 + 1);
        for (int i = 0; i < regionAmount; i+=3) {
            int color = 55 + coef * (i / 3 + 1);

            colors[i] = new ARGBPixel();
            colors[i].setRGB(color, 0, 0);

            if ((i+1) == regionAmount) break;
            colors[i+1] = new ARGBPixel();
            colors[i+1].setRGB(0, color, 0);

            if ((i+2) == regionAmount) break;
            colors[i+2] = new ARGBPixel();
            colors[i+2].setRGB(0, 0, color);
        }
        // each pixel of some region is assigned to paticular color
        for (int x = 0; x < image.width(); x++) {
            for (int y = 0; y < image.heght(); y++) {
                if (regionField[x][y] != 0) {
                    image.pixel(x,y).set(colors[regionField[x][y] - 1]);
                }
            }
        }
    }

    public void paintAllRegionsBlack() {
        for (int x = 0; x < image.width(); x++) {
            for (int y = 0; y < image.heght(); y++) {
                if (regionField[x][y] != 0) {
                    image.pixel(x,y).setRGB(0, 0, 0);
                }
            }
        }
    }

    public void createRegionObjects() {
        for (int i = 0; i < regionAmount; i++) {
            regions.add(new ImageRegion(regionField, i+1));
        }
    }

    public void viewRegions() {
        regions.forEach(region -> {
            System.out.println(region.toString());
        });
    }


}
