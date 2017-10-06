package com.veromeev.bsuir.dip.l1.util.cluster;

import com.veromeev.bsuir.dip.l1.util.ARGBImage;
import com.veromeev.bsuir.dip.l1.util.ARGBPixel;

import java.util.List;

public class ImageClusterization {

    private final ARGBImage image;
    private final Cluster[] clusters;
    private final List<ImageRegion> regions;

    public ImageClusterization (ImageScanner imageScanner, int clusterAmount) {
        image = imageScanner.image;
        if (clusterAmount > imageScanner.regions.size())
            throw new IllegalArgumentException("нахуя столько кластеров");
        clusters = new Cluster[clusterAmount];
        regions = imageScanner.regions;
        for (int i = 0; i < clusterAmount; i++) {
            clusters[i] = new Cluster(imageScanner.regions.get(i), i);
        }
        initialClusterizationStep();
    }

    private void initialClusterizationStep() {
        regions.forEach(region -> {
            int nearestClusterNum = 0;
            double minDistance = Double.MAX_VALUE;
            for (Cluster cluster : clusters) {
                double d = cluster.getCentroid().distance(region);
                if (minDistance > d) {
                    minDistance = d;
                    nearestClusterNum = cluster.getId();
                }
            }
            clusters[nearestClusterNum].getImageRegions().add(region);
        });
        recalculateCentroids();
    }

    private void recalculateCentroids() {
        for (Cluster cluster : clusters) {
            cluster.recalculateCentroid();
        }

    }

    public ImageClusterization clusterization() {
        boolean nothingChanged = false;
        while (!nothingChanged) {
            nothingChanged = true;
            for (ImageRegion region : regions) {
                int nearestClusterId = 0;
                int currentClusterId = 0;
                double minDistance = Double.MAX_VALUE;
                for (Cluster cluster : clusters) {
                    if (cluster.getImageRegions().contains(region)) {
                        currentClusterId = cluster.getId();
                    }
                    double d = cluster.getCentroid().distance(region);
                    if (minDistance > d) {
                        minDistance = d;
                        nearestClusterId = cluster.getId();
                    }
                }
                if (nearestClusterId != currentClusterId) {
                    clusters[currentClusterId].getImageRegions().remove(region);
                    clusters[nearestClusterId].getImageRegions().add(region);
                    nothingChanged = false;
                }
            }
            recalculateCentroids();
        }
        return this;
    }

    public void colorizeClusters(){
        ARGBPixel[] colors = new ARGBPixel[clusters.length];
        int coef = 200 / (colors.length / 3 + 1);
        for (int i = 0; i < colors.length; i+=3) {
            int color = 55 + coef * (i / 3 + 1);

            colors[i] = new ARGBPixel();
            colors[i].setRGB(color, 0, 0);

            if ((i+1) == colors.length) break;
            colors[i+1] = new ARGBPixel();
            colors[i+1].setRGB(0, color, 0);

            if ((i+2) == colors.length) break;
            colors[i+2] = new ARGBPixel();
            colors[i+2].setRGB(0, 0, color);
        }
        // each pixel of some region is assigned to paticular color
        for (int i = 0; i < clusters.length; i++) {
            final ARGBPixel color = colors[i];
            clusters[i].getImageRegions().forEach(region -> region.region.forEach(
                    pair -> image.pixel(pair.getKey(), pair.getValue()).set(color)));
        }
    }
}
