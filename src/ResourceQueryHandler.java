import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mart on 16.09.15.
 */
public class ResourceQueryHandler implements HttpHandler {
    public void handle(HttpExchange t) throws IOException {
        System.out.println(" ");

        System.out.println("Resource query received on port: " + t.getLocalAddress().getPort());
        String response = "Response: Resource query received, sending back a response and recursively sending" +
                " resource queries to my machines";
        t.sendResponseHeaders(200, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();

        DoRequests doRequests = new DoRequests();
        String myport = String.valueOf(t.getLocalAddress().getPort());

        //read query params
        Map<String, String> params = queryToMap(t.getRequestURI().getQuery());
        String sendip = params.get("sendip"); //
        String sendport = params.get("sendport");
        String ttl = params.get("ttl");
        String id = params.get("id");
        String noask = params.get("noask");


        //send back resource query reply as post
        try {
            //resource capability on this machine is 100
            doRequests.postResourceReply(sendip, sendport, id, "100", myport);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //do querys to own machines
        MachinesReader machinesReader = new MachinesReader();
        JSONArray machines = machinesReader.readFileToArray(myport);


        try {
            //param ports and ips
            if (machines != null) {
                doRequests.sendGetRequests(machines, sendip, sendport, null, "4");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String, String> queryToMap(String query) {
        Map<String, String> result = new HashMap<String, String>();
        for (String param : query.split("&")) {
            String pair[] = param.split("=");
            if (pair.length > 1) {
                result.put(pair[0], pair[1]);
            } else {
                result.put(pair[0], "");
            }
        }
        return result;
    }
}