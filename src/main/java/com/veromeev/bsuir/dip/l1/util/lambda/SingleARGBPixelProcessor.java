package com.veromeev.bsuir.dip.l1.util.lambda;

import com.veromeev.bsuir.dip.l1.util.ARGBPixel;

@FunctionalInterface
public interface SingleARGBPixelProcessor {
    void apply(ARGBPixel pixel);
}
