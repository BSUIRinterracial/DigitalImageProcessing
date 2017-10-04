package com.veromeev.bsuir.dip.l1.util.cluster;


import javafx.util.Pair;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by jack on 10/4/17.
 *
 * @author Jack Veromeyev
 */
public class ImageRegion {
    Set<Pair<Integer, Integer>> region;
    public ImageRegion() {
        region = new HashSet<>();
    }
    public void add (int x, int y) {
        region.add(new Pair(x,y));
    }

    public void add(ImageRegion region) {
        this.region.addAll(region.getRegion());
    }

    public Set<Pair<Integer,Integer>> getRegion() {
        return region;
    }
}
