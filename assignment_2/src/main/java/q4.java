import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.util.stream.IntStream;

public class q4 {

    private static byte[] getBytes(String str) {
        try {
            return Hex.decodeHex(str.toCharArray());
        } catch (DecoderException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] xorBytes(byte[] a, byte[] b) {
        int n = Math.min(a.length, b.length);
        byte res[] = new byte[n];
        IntStream.range(0, n).forEach(i -> {
            int x = (int)a[i];
            int y = (int)b[i];
            int z = x ^ y;
            res[i] = (byte)z;
        });
        return res;
    }

    private static void printBytes(byte[] a) {
        IntStream.range(0, a.length).forEach(i -> System.out.print(a[i] + " "));
        System.out.println();
    }

    public static void main(String args[]) {
        byte[] a1 = getBytes("290b6e3a39155d6f");
        byte[] b1 = getBytes("d6f491c5b645c008");
        printBytes(xorBytes(a1, b1));

        byte[] a2 = getBytes("5f67abaf5210722b");
        byte[] b2 = getBytes("bbe033c00bc9330e");
        printBytes(xorBytes(a2, b2));

        byte[] a3 = getBytes("9d1a4f78cb28d863");
        byte[] b3 = getBytes("75e5e3ea773ec3e6");
        printBytes(xorBytes(a3, b3));

        byte[] a4 = getBytes("7b50baab07640c3d");
        byte[] b4 = getBytes("ac343a22cea46d60");
        printBytes(xorBytes(a4, b4));
    }
}
