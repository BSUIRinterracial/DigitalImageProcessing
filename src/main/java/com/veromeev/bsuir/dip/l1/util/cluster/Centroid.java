package com.veromeev.bsuir.dip.l1.util.cluster;

import lombok.AllArgsConstructor;
import lombok.Data;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

@Data
@AllArgsConstructor
public class Centroid {
    private int square;
    private int perimeter;
    private double elongation;
    private double compactness;

    public double distance(ImageRegion region) {
        return sqrt( pow(region.getSquare() - square, 2)
                + pow(region.getPerimeter() - perimeter, 2)
                + pow(region.getElongation() - elongation, 2)
                + pow(region.getCompactness() - compactness, 2));
    }
}
