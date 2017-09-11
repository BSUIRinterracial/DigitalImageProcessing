package com.veromeev.bsuir.dip.l1;

public class PixelARGB {

    private byte A, R, G, B;

    public PixelARGB(int pixel) {
        this.A = (byte) (pixel >> 24);
        this.R = (byte) ((pixel >> 16) & 0x000000ff);
        this.G = (byte) ((pixel >> 8) & 0x000000ff);
        this.B = (byte) (pixel & 0x000000ff);
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
        return ((int)A << 24) | ((int)R << 16) | ((int)G << 8) | (B);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PixelARGB{");
        sb.append("A=").append(String.format("%02X", A));
        sb.append(", R=").append(String.format("%02X", R));
        sb.append(", G=").append(String.format("%02X", G));
        sb.append(", B=").append(String.format("%02X", B));
        sb.append('}');
        return sb.toString();
    }

}
