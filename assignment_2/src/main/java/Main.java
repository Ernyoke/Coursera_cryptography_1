import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Main {

    public static class CypherEntry {
        private String key;
        private String iv;
        private String cypherText;
        private String mode;

        public CypherEntry(String key, String iv, String cypherText, String mode) {
            this.key = key;
            this.iv = iv;
            this.cypherText = cypherText;
            this.mode = mode; //CBC or CTR
        }

        public byte[] getKey() {
            return getBytes(key);
        }

        public byte[] getIv() {
            return getBytes(iv);
        }

        public byte[] getCypherText() {
            return getBytes(cypherText);
        }

        public String getMode() {
            return mode;
        }

        private static byte[] getBytes(String str) {
            try {
                return Hex.decodeHex(str.toCharArray());
            } catch (DecoderException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private static String decrypt(CypherEntry entry) {
        try {
            Key aesKey = new SecretKeySpec(entry.getKey(), "AES");
            Cipher c = Cipher.getInstance("AES/" + entry.getMode() + "/PKCS5Padding");
            IvParameterSpec ivSpec = new IvParameterSpec(entry.getIv());
            c.init(Cipher.DECRYPT_MODE, aesKey, ivSpec);
            byte[] resultBytes = c.doFinal(entry.getCypherText());
            return new String(resultBytes);
        } catch (NoSuchAlgorithmException
                | NoSuchPaddingException
                | InvalidKeyException
                | BadPaddingException
                | IllegalBlockSizeException
                | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        ArrayList<CypherEntry> entries = new ArrayList<>();
        entries.add(new CypherEntry("140b41b22a29beb4061bda66b6747e14",
                "4ca00ff4c898d61e1edbf1800618fb28",
                "28a226d160dad07883d04e008a7897ee2e4b7465d5290d0c0e6c6822236e1daafb94ffe0c5da05d9476be028ad7c1d81",
                "CBC"));
        entries.add(new CypherEntry("140b41b22a29beb4061bda66b6747e14",
                "5b68629feb8606f9a6667670b75b38a5",
                "b4832d0f26e1ab7da33249de7d4afc48e713ac646ace36e872ad5fb8a512428a6e21364b0c374df45503473c5242a253",
                "CBC"));
        entries.add(new CypherEntry("36f18357be4dbd77f050515c73fcf9f2",
                "69dda8455c7dd4254bf353b773304eec",
                "0ec7702330098ce7f7520d1cbbb20fc388d1b0adb5054dbd7370849dbf0b88d393f252e764f1f5f7ad97ef79d59ce29f5f51eeca32eabedd9afa9329",
                "CTR"));
        entries.add(new CypherEntry("36f18357be4dbd77f050515c73fcf9f2",
                "770b80259ec33beb2561358a9f2dc617",
                "e46218c0a53cbeca695ae45faa8952aa0e311bde9d4e01726d3184c34451",
                "CTR"));

        entries.stream()
                .map(Main::decrypt)
                .forEach(System.out::println);
    }
}