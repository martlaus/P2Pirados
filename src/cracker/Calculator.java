package cracker;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mart on 11.10.15.
 */
public class Calculator implements Runnable {
    private String myPort;
    private String id;
    private String masterPort;
    private String masterIp;
    private String alphabet;
    private String hash;
    private String start;
    private String end;

    public Calculator(String hash, String start, String end, String alphabet, String masterIp, String masterPort, String id, String myPort) {
        this.hash = hash;
        this.start = start;
        this.end = end;
        this.alphabet = alphabet;
        this.masterIp = masterIp;
        this.masterPort = masterPort;
        this.id = id;
        this.myPort = myPort;
    }

    public Calculator(String query, String myPort) {
        try {

            JSONObject jsonObj = new JSONObject(query);
            this.masterIp = jsonObj.getString("ip");
            this.masterPort = String.valueOf(jsonObj.getInt("port"));
            this.id = jsonObj.getString("id");
            this.hash = jsonObj.getString("md5");
            this.start = String.valueOf(jsonObj.getInt("rangeStart"));
            this.end = String.valueOf(jsonObj.getInt("rangeEnd"));
            this.alphabet = jsonObj.getString("alphabet");
            this.myPort = myPort;


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        System.out.println("starting calculations");
        DoRequests doRequests = new DoRequests();

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
                try {
                    doRequests.postAnswerMd5(masterIp, masterPort, myPort, id, hash, String.valueOf(0), ans);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        //no result, respond.
        try {
            doRequests.postAnswerMd5(masterIp, masterPort, myPort, id, hash, String.valueOf(1), "");
        } catch (Exception e) {
            e.printStackTrace();
        }
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
