package com.veromeev.bsuir.dip.l1.util.lambda;

import com.veromeev.bsuir.dip.l1.util.ARGBPixel;

/**
 * Created by jack on 9/28/17.
 *
 * @author Jack Veromeyev
 */
@FunctionalInterface
public interface PixelFrameProcessor {
    ARGBPixel process(ARGBPixel[][] frame);
}
