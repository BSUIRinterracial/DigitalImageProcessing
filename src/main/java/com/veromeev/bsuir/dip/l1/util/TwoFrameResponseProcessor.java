package com.veromeev.bsuir.dip.l1.util;

@FunctionalInterface
public interface TwoFrameResponseProcessor {
    ARGBPixel process(ARGBPixel response1, ARGBPixel response2);
}
