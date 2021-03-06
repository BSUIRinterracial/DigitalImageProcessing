package com.veromeev.bsuir.dip.l1.util.lambda;

import com.veromeev.bsuir.dip.l1.util.ARGBPixel;

@FunctionalInterface
public interface TwoFrameResponseProcessor {
    ARGBPixel process(ARGBPixel response1, ARGBPixel response2);
}
