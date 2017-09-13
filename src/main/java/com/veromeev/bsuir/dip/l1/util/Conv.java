package com.veromeev.bsuir.dip.l1.util;

/**
 * Created by jack on 9/13/17.
 *
 * @author Jack Veromeyev
 */
public class Conv {
    public static int btoi(byte b) {
        
        return (b & 0x000000ff);
    }
    public static byte itob(int i) {
        i &= 0x000000ff;
        return (byte) i;
    }
}
