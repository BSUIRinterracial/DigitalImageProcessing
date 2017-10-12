package com.veromeev.bsuir.dip.l1.util;

import com.veromeev.bsuir.dip.l1.util.lambda.ChannelProcessor;

public class ARGBPixel implements Comparable<ARGBPixel>{

    private int A, R, G, B;

    public ARGBPixel(int pixel) {
        this.A =  (pixel & 0xff000000) >>> 24;
        this.R =  (pixel & 0x00ff0000) >>> 16;
        this.G =  (pixel & 0x0000ff00) >>> 8;
        this.B =   pixel & 0x000000ff;
    }

    public ARGBPixel() {
        R = G = B = 0x00;
        A = (byte) 0xff;
    }
    public ARGBPixel(int r, int g, int b) {
        setA(255);
        setR(r);
        setG(g);
        setB(b);
    }

    public int getA() {
        return A;
    }
    public void setA(int a) {
        if (a > 0xff) a = 0xff;
        if (a < 0) a = 0;
        A = a;
    }
    public int getR() {
        return R;
    }
    public void setR(int r) {
        if (r > 0xff) r = 0xff;
        if (r < 0) r = 0;
        R = r;
    }
    public int getG() {
        return G;
    }
    public void setG(int g) {
        if (g > 0xff) g = 0xff;
        if (g < 0) g = 0;
        G = g;
    }
    public int getB() {
        return B;
    }
    public void setB(int b) {
        if (b > 0xff) b = 0xff;
        if (b < 0) b = 0;
        B = b;
    }

    public void setRGB(int r, int g, int b) {
        setR(r);
        setG(g);
        setB(b);
    }

    public void set(ARGBPixel p) {
        setA(p.getA());
        setR(p.getR());
        setG(p.getG());
        setB(p.getB());
    }

    public void forEachRGBChannel (ChannelProcessor p) {
        setR(p.processChannel(getR()));
        setG(p.processChannel(getG()));
        setB(p.processChannel(getB()));
    }

    public int toARGBInteger() {
        return ( ((A << 24) & 0xff000000) | ((R << 16) & 0x00ff0000) | ((G << 8) & 0x0000ff00) | (B & 0x000000ff));
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ARGBPixel{");
        sb.append("A=").append(String.format("%02X", A));
        sb.append(", R=").append(String.format("%02X", R));
        sb.append(", G=").append(String.format("%02X", G));
        sb.append(", B=").append(String.format("%02X", B));
        sb.append('}');
        return sb.toString();
    }

    public int brightness() {
        return (int) ( ((double)getR()) * 0.3
                        + ((double) getG()) * 0.59
                        + ((double) getB()) * 0.11);
    }

    public boolean isBlack() {
        return brightness() == 0;
    }

    public boolean isWhite() {
        return brightness() == 255;
    }

    public int hopfieldValue() {
        return isWhite() ? -1 : 1;
    }

    @Override
    public int compareTo(ARGBPixel o) {
        return brightness() - o.brightness();
    }
}
