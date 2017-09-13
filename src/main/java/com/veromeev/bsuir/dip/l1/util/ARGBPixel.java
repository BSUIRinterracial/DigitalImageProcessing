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

    public byte getA() {
        return A;
    }
    public void setA(byte a) {
        A = a;
    }
    public byte getR() {
        return R;
    }
    public void setR(byte r) {
        R = r;
    }
    public byte getG() {
        return G;
    }
    public void setG(byte g) {
        G = g;
    }
    public byte getB() {
        return B;
    }
    public void setB(byte b) {
        B = b;
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

    public byte brightness() {
        return Conv.itob ((int) ( ((double)Conv.btoi(R)) * 0.3
                        + ((double) Conv.btoi(G)) * 0.59
                        + ((double) Conv.btoi(B)) * 0.11));
    }

}
