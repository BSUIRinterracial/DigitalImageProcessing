package com.veromeev.bsuir.dip.l1.util.cluster;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Cluster {

    @Getter private final int id;
    @Getter private final Centroid centroid;
    @Getter private final List<ImageRegion> imageRegions = new ArrayList<>();

    public Cluster(ImageRegion randomCentroid, int id) {
        centroid = new Centroid(randomCentroid.getSquare(),
                randomCentroid.getPerimeter(),
                randomCentroid.getElongation(),
                randomCentroid.getCompactness());
        this.id = id;
    }

    public void recalculateCentroid() {
        imageRegions.sort(ImageRegion::comparePerimeter);
        centroid.setPerimeter(imageRegions.get(imageRegions.size() / 2).getPerimeter());

        imageRegions.sort(ImageRegion::compareSquare);
        centroid.setSquare(imageRegions.get(imageRegions.size() / 2).getSquare());

        imageRegions.sort(ImageRegion::compareElongation);
        centroid.setElongation(imageRegions.get(imageRegions.size() / 2).getElongation());

        imageRegions.sort(ImageRegion::compareCompactness);
        centroid.setCompactness(imageRegions.get(imageRegions.size() / 2).getCompactness());
    }

}
