import org.json.JSONObject;
import org.json.simple.JSONArray;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.List;

/**
 * Created by mart on 16.09.15.
 */
public class DoRequests {

    // HTTP GET request
    public void sendGetRequests(JSONArray machines, String sendip, String sendport, String noask, String id) throws Exception {
        for (int i = 0; i < machines.size(); i++) {
            JSONArray machine = (JSONArray) machines.get(i);

            doGet((String) machine.get(0),(String) machine.get(1), sendip, sendport, noask, id);
        }

    }

    private void doGet(String machineURL, String machinePort, String sendip,  String sendport, String noask, String id) throws IOException {
        int ttl = 5;

        String url = "http://" + machineURL + ":" + machinePort + "/resource" +
                "?sendip=" + sendip + "&sendport=" + sendport + "&ttl=" + ttl +
                "&id=" + id + "&noask=" + noask;

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", "Mozilla/5.0");

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        //System.out.println(response.toString());
    }

    // HTTP POST request - Resource Reply
    public void postResourceReply(String sendip, String sendport, String id, String resource, String myport) throws Exception {
        String myip = InetAddress.getLocalHost().getHostAddress();

        String url = "http://" + sendip + ":" + sendport + "/resourcereply";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add request header
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");


        JSONObject parent = new JSONObject();
        parent.put("ip", myip); //minu ip, saadab esialgsele
        parent.put("port", myport); //minu port, saadab esialgsele
        parent.put("id", id);
        parent.put("resource", resource);


        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(parent.toString());
        wr.flush();
        wr.close();
        System.out.println("My port when posting resource query reply is : " + myport);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + parent.toString());
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        //System.out.println(response.toString());
    }

    // HTTP POST request - Calculation Query
    public void postCalculationQuery(String slaveip, String sendport, String id, String md5,
                                     String rangeStart, String rangeEnd, String alphabet, int myport) throws Exception {
        String myip = InetAddress.getLocalHost().getHostAddress();

        String url = "http://" + slaveip + ":" + sendport + "/checkmd5";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add request header
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");


        JSONObject parent = new JSONObject();
        parent.put("ip", myip); //Masteri ip
        parent.put("port", String.valueOf(myport)); //Masteri port
        parent.put("id", id);
        parent.put("md5", md5);
        parent.put("rangeStart", rangeStart);
        parent.put("rangeEnd", rangeEnd);
        parent.put("alphabet", alphabet);


        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(parent.toString());
        wr.flush();
        wr.close();
        System.out.println("My port is : " + myport);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + parent.toString());
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        //System.out.println("checkmd5 response: " + response.toString());
    }
}
