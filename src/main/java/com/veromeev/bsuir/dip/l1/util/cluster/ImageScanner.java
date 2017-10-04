package com.veromeev.bsuir.dip.l1.util.cluster;

import com.veromeev.bsuir.dip.l1.util.ARGBImage;
import com.veromeev.bsuir.dip.l1.util.ARGBPixel;
import javafx.util.Pair;

import java.util.*;

/**
 * Created by jack on 9/28/17.
 *
 * @author Jack Veromeyev
 */
public class ImageScanner {

    private final ARGBImage image;

    List<ImageRegion> regions;

    Map<Pair<Integer, Integer>, Integer> markedPixels;

    public ImageScanner(ARGBImage image, int clusterAmount) {
        this.image = image;
        regions = new ArrayList<>();
        markedPixels = new HashMap<>();
    }

    public int scan() {
        int currentRegion = 0;
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                ARGBPixel p = image.getPixel(i, j);
                if (p.isWhite()) {
                    Integer topNeighbourRegionNumber = markedPixels.get(new Pair(i, j-1));
                    Integer leftNeighbourRegionNumber = markedPixels.get(new Pair(i-1, j));

                    if (topNeighbourRegionNumber == null && leftNeighbourRegionNumber == null) {
                        currentRegion ++;
                        markedPixels.put(new Pair<>(i, j), currentRegion);
                    } else if (topNeighbourRegionNumber != null && leftNeighbourRegionNumber == null) {
                        markedPixels.put(new Pair<>(i, j), topNeighbourRegionNumber);
                    } else if (topNeighbourRegionNumber == null && leftNeighbourRegionNumber != null) {
                        markedPixels.put(new Pair<>(i, j), leftNeighbourRegionNumber);
                    } else if (topNeighbourRegionNumber.equals(leftNeighbourRegionNumber)) {
                        markedPixels.put(new Pair<>(i, j), leftNeighbourRegionNumber);
                    } else if (! topNeighbourRegionNumber.equals(leftNeighbourRegionNumber)) {
                        int min = (topNeighbourRegionNumber > leftNeighbourRegionNumber)
                                ? leftNeighbourRegionNumber
                                : topNeighbourRegionNumber;
                        int max = (topNeighbourRegionNumber < leftNeighbourRegionNumber)
                                ? leftNeighbourRegionNumber
                                : topNeighbourRegionNumber;
                        markedPixels.put(new Pair<>(i, j), min);

                        markedPixels.keySet().forEach(key -> {
                            if (markedPixels.get(key) == max) {
                                markedPixels.put(key, min);
                            }
                        });
//                        currentRegion = max;
                    } else {
                        System.err.println("DA, ETO ZHESTSKA");
                    }
                }
            }
        }

        HashSet<Integer> valueSet = new HashSet<Integer>(markedPixels.values());
        List<Integer> valueList = new ArrayList<>(valueSet);
        valueList.sort(Integer::compareTo);
        int coef = 200/valueList.size();

        for (int i = valueList.size()-1; i >= 0; i--) {
            final int j = i;
            List<Integer> finalValueList = valueList;
            markedPixels.keySet().forEach(key -> {
                if (markedPixels.get(key) == finalValueList.get(j)) {
                    ARGBPixel p = image.getPixel(key.getKey(), key.getValue());
                    p.setRGB(55 + j*coef, 55+j*coef, 55+j*coef);
                }
            });
        }

//        values = new HashSet<Integer>(markedPixels.values());
//        valueList = new ArrayList<>(values);
//        valueList.sort(Integer::compareTo);
//        for (int i = 0; i < valueList.size(); i++) {
//            final int j = i;
//            List<Integer> finalValueList1 = valueList;
//            markedPixels.keySet().forEach(key -> {
//                if (markedPixels.get(key) == finalValueList1.get(j)) {
//                    markedPixels.put(key, j+1);
//                }
//            });
//        }

        System.out.println(valueList.size());
//        markedPixels.forEach((k, v)-> {
//            System.out.println("[" + k.getKey() + "," + k.getValue() + "] - " + v);
//        });
        return currentRegion;
    }

}
