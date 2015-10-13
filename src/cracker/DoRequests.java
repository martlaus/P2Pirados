package cracker;

import org.json.JSONObject;
import org.json.simple.JSONArray;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by mart on 16.09.15.
 */
public class DoRequests {

    // HTTP GET request
    public void sendGetRequests(JSONArray machines, String sendip, String sendport, String noask, String id, String ttl) throws Exception {
        for (int i = 0; i < machines.size(); i++) {
            JSONArray machine = (JSONArray) machines.get(i);

            doGet((String) machine.get(0), (String) machine.get(1), sendip, sendport, noask, id, ttl);
        }

    }

    //Send Resource query to slave
    private void doGet(String machineURL, String machinePort, String sendip, String sendport, String noask, String id, String ttl) throws IOException {
        //int ttl = 5;

        String url = "http://" + machineURL + ":" + machinePort + "/resource" +
                "?sendip=" + sendip + "&sendport=" + sendport + "&ttl=" + ttl +
                "&id=" + id + "&noask=" + noask;

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", "Mozilla/5.0");

        System.out.println("\nSending 'GET' request to URL : " + url + "\n");

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

    // HTTP POST request - Resource Reply to master
    public void postResourceReply(String sendip, String sendport, String id, String resource, String myport) throws Exception {
        String myip = "192.168.0.103";

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

        System.out.println("\nSending 'POST' resourcereply request to URL : " + url);
        System.out.println("Post parameters : " + parent.toString());

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

    // HTTP POST request - Calculation Query to slave
    public void postCalculationQuery(String slaveip, String sendport, String id, String md5,
                                     String rangeStart, String rangeEnd, String alphabet, int myport) throws Exception {
        String myip = "192.168.0.103";

        String url = "http://" + slaveip + ":" + sendport + "/checkmd5";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add request header
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");

        JSONObject parent = new JSONObject();
        parent.put("ip", myip); //Masteri ip
        parent.put("port", myport); //Masteri port
        parent.put("id", id);
        parent.put("md5", md5);
        parent.put("rangeStart", rangeStart);
        parent.put("rangeEnd", Long.valueOf(rangeEnd));
        parent.put("alphabet", alphabet);


        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(parent.toString());
        wr.flush();
        wr.close();
        System.out.println("My port is : " + myport);

        System.out.println("\nSending 'POST' checkmd5 request to URL : " + url);
        System.out.println("Post parameters : " + parent.toString());

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

    //POST md5 calculation response with the password if found
    public void postAnswerMd5(String masterIp, String masterPort, String myPort, String id, String md5,
                              String result, String resultString) throws Exception {
        String myip = "192.168.0.103";

        String url = "http://" + masterIp + ":" + masterPort + "/answermd5";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add request header
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");

        JSONObject parent = new JSONObject();
        parent.put("ip", myip); //slave ip
        parent.put("port", Long.valueOf(myPort)); //slave port
        parent.put("id", id);
        parent.put("md5", md5);
        parent.put("result", Long.valueOf(result));
        parent.put("resultstring", resultString);


        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(parent.toString());
        wr.flush();
        wr.close();
        System.out.println("My port is : " + myPort);

        System.out.println("\nSending 'POST' answermd5 request to URL : " + url);
        System.out.println("Post parameters : " + parent.toString());

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
