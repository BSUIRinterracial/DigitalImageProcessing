package com.veromeev.bsuir.dip.l1.util.cluster;

import javafx.util.Pair;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

@ToString(of = {"perimeter", "square", "compactness", "elongation"})
public class ImageRegion {
    List<Pair<Integer, Integer>> region = new ArrayList<>();
    final int[][] regionField;

    @Getter private int perimeter = 0;
    @Getter private double compactness;
    @Getter private final int square;
    @Getter private final double elongation;

    private double centerOfMassY = 0.0;
    private double centerOfMassX = 0.0;

    private double m20 = 0.0, m02 = 0.0, m11 = 0.0;

    public ImageRegion(int[][] regionField, int n) {
        this.regionField = regionField;
        for (int x = 0; x < regionField.length; x++) {
            for (int y = 0; y < regionField[0].length; y++) {
                if (regionField[x][y] == n) {
                    region.add(new Pair<>(x, y));
                    centerOfMassX += x;
                    centerOfMassY += y;
                    if (isBoundary(x,y)) {
                        perimeter++;
                    }
                }
            }
        }
        square = region.size();
        centerOfMassX /= (double) square;
        centerOfMassY /= (double) square;
        compactness = (double) (perimeter * perimeter) / getSquare();
        region.forEach(point -> {
            m11 += (point.getKey() - centerOfMassX) * (point.getValue() - centerOfMassY);
            m20 += (point.getKey() - centerOfMassX) * (point.getKey() - centerOfMassX);
            m02 += (point.getValue() - centerOfMassY) * (point.getValue() - centerOfMassY);
        });
        elongation = (m20+m02 + sqrt(pow(m20-m02, 2) + 4*pow(m11,2))) / (m20+m02 - sqrt(pow(m20-m02, 2) + 4*pow(m11,2)));
    }

    private boolean isBoundary(int x, int y) {
        if (x > 0) {
            if ((y > 0 && regionField[x-1][y-1] == 0)
                    || (regionField[x-1][y] == 0)
                    || (y < regionField[0].length - 1 && regionField[x-1][y+1] == 0)) return true;
        } else if (x < regionField.length) {
            if ((y > 0 && regionField[x+1][y-1] == 0)
                    || (regionField[x+1][y] == 0)
                    || (y < regionField[0].length - 1 && regionField[x+1][y+1] == 0)) return true;
        } else {
            if ((y > 0 && regionField[x][y-1] == 0)
                    || (y < regionField[0].length - 1 && regionField[x][y+1] == 0)) return true;
        }
        return false;
    }

    int comparePerimeter(ImageRegion o) {
        return perimeter - o.getPerimeter();
    }

    int compareSquare(ImageRegion o) {
        return square - o.getSquare();
    }

    int compareElongation(ImageRegion o) {
        return (int)(elongation - o.getElongation());
    }

    int compareCompactness(ImageRegion o) {
        return (int) (compactness - o.getCompactness());
    }

}
