import org.apache.commons.codec.binary.Hex;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Main {

    private static final int BUFFER_SIZE = 1024;

    private static byte[] append(byte[] a, byte[] b) {
        byte[] buffer = new byte[a.length + b.length];
        System.arraycopy(a, 0, buffer, 0, a.length);
        System.arraycopy(b, 0, buffer, a.length, b.length);
        return buffer;
    }

    private static void printHex(byte[] buffer) {
        System.out.println(Hex.encodeHexString(buffer));
    }

    private static ArrayList<byte[]> readBlocks(String filename) {
        ArrayList<byte[]> blocks = new ArrayList<>();
        try {
            InputStream inputStream = new FileInputStream(filename);
            int bytesRead;
            byte[] buffer= new byte[BUFFER_SIZE];
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byte[] copy = new byte[bytesRead];
                System.arraycopy(buffer, 0, copy, 0, bytesRead);
                blocks.add(copy);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return blocks;
    }

    private static byte[] calculateHash(String filename) {
        byte[] hash = null;
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            ArrayList<byte[]> blocks = readBlocks(filename);
            for (int i = blocks.size() - 1; i >= 0; --i) {
                byte block[] = blocks.get(i);
                if (hash != null) {
                    block = append(block, hash);
                }
                messageDigest.update(block, 0, block.length);
                hash = messageDigest.digest();
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hash;
    }

    public static void main(String[] args) {
        String filename = "6.1.intro.mp4_download";
        printHex(calculateHash(filename));
    }
}
