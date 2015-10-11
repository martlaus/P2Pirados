import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mart on 11.10.15.
 */
public class Calculator implements Runnable {
    private String alphabet;
    private String hash;
    private String start;
    private String end;

    public Calculator(String hash, String start, String end, String alphabet) {
        this.hash = hash;
        this.start = start;
        this.end = end;
        this.alphabet = alphabet;
    }

    @Override
    public void run() {
        System.out.println("CALCULATING");

        for (Long i = Long.valueOf(start); i < Long.valueOf(end); i++) {
            String ans = numberToString(i, alphabet.toCharArray());
            if (ans.equals("koer")) {
                System.out.println("sõna on koer");
            }
            String resultHash = null;
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(ans.getBytes());
                byte[] digest = md.digest();
                StringBuffer sb = new StringBuffer();
                for (byte b : digest) {
                    sb.append(String.format("%02x", b & 0xff));
                }
                resultHash = sb.toString();
                //System.out.println("digested(hex):" + resultHash);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (hash.equals(resultHash)) {
                System.out.println("MATCH FOUND");
            }
        }
        System.out.println("Nothing to see here...");

    }


    public String numberToString(long number, char[] alphabet) {

        List<String> result = new ArrayList<>();

        while (number >= alphabet.length) {
            int location = (int) (number % alphabet.length);
            result.add(0, String.valueOf(alphabet[location]));
            number = (int) (Math.floor(number / alphabet.length) - 1);
        }

        result.add(0, String.valueOf(alphabet[(int) number]));

        return String.join("", result);
    }
}
