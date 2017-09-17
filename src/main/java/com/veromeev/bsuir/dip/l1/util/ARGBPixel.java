package com.veromeev.bsuir.dip.l1.util;

public class ARGBPixel {

    private byte A, R, G, B;

    public ARGBPixel(int pixel) {
        this.A = (byte) ((pixel&0xff000000) >> 24);
        this.R = (byte) (((pixel&0x00ff0000) >> 16) & 0x000000ff);
        this.G = (byte) (((pixel&0x0000ff00) >> 8) & 0x000000ff);
        this.B = (byte) (pixel & 0x000000ff);
    }

    public ARGBPixel() {
        R = G = B = 0x00;
        A = (byte) 0xff;
    }

    public int getA() {
        return Conv.btoi(A);
    }
    public void setA(int a) {
        if (a > 0xff) a = 0xff;
        if (a < 0) a = 0;
        A = Conv.itob(a);
    }
    public int getR() {
        return Conv.btoi(R);
    }
    public void setR(int r) {
        if (r > 0xff) r = 0xff;
        if (r < 0) r = 0;
        R = Conv.itob(r);
    }
    public int getG() {
        return Conv.btoi(G);
    }
    public void setG(int g) {
        if (g > 0xff) g = 0xff;
        if (g < 0) g = 0;
        G = Conv.itob(g);
    }
    public int getB() {
        return Conv.btoi(B);
    }
    public void setB(int b) {
        if (b > 0xff) b = 0xff;
        if (b < 0) b = 0;
        B = Conv.itob(b);
    }

    public void setRGB(int r, int g, int b) {
        setR(r);
        setG(g);
        setB(b);
    }

    public void forEachRGBChannel (ChannelProcessor p) {
        setR(p.processChannel(getR()));
        setG(p.processChannel(getG()));
        setB(p.processChannel(getB()));
    }

    public int toARGBInteger() {
        return ( (((int)A << 24) & 0xff000000) | (((int)R << 16) & 0x00ff0000) | (((int)G << 8) & 0x0000ff00) | (((int)B) & 0x000000ff));
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

}
