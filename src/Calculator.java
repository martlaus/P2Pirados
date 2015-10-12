import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mart on 11.10.15.
 */
public class Calculator implements Runnable {
    private String id;
    private String masterPort;
    private String masterIp;
    private String alphabet;
    private String hash;
    private String start;
    private String end;

    public Calculator(String hash, String start, String end, String alphabet, String masterIp, String masterPort, String id) {
        this.hash = hash;
        this.start = start;
        this.end = end;
        this.alphabet = alphabet;
        this.masterIp = masterIp;
        this.masterPort = masterPort;
        this.id = id;
    }

    @Override
    public void run() {

        for (Long i = Long.valueOf(start); i < Long.valueOf(end); i++) {
            String ans = numberToString(i, alphabet.toCharArray());

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
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (hash.equals(resultHash)) {
                System.out.println("MATCH FOUND " + ans);
                //anwser back result
            }
        }

        //no result, respond.
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
