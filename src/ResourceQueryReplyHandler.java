import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.apache.velocity.runtime.parser.node.MathUtils;
import org.apache.velocity.tools.generic.ValueParser;
import org.json.JSONObject;

import java.io.*;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mart on 16.09.15.
 */
public class ResourceQueryReplyHandler implements HttpHandler {
    private int myport;

    @Override
    public void handle(HttpExchange t) throws IOException {
        myport = t.getLocalAddress().getPort();
        System.out.println("Resource query reply (POST) received on port: " + myport + " sending back response");

        InputStream is = t.getRequestBody();


        InputStreamReader isr =
                new InputStreamReader(is, "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String query = br.readLine();
        JSONObject jsonObj = new JSONObject(query);
        String ip = jsonObj.getString("ip");
        String port = jsonObj.getString("port");
        String id = jsonObj.getString("id");
        String resource = jsonObj.getString("resource");
        System.out.println("(my port: " + myport + ") Data from resource query: " + ip + ":" + port + " id: " + id + " resource: " + resource);
        Slave slave = new Slave(ip, port, resource, id);


        //store slave data and add to list
        SlaveHandler.addToList(slave);
        List<Slave> slaves = SlaveHandler.getSlaves();
        if (slaves.size() > 11) {
            try {
                SlaveHandler.setMaxLength(4);
                System.out.println("GOT ENOUGH SLAVES " + slaves.size());
                sendCalculationRequests(slaves);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String response = "0";
        t.sendResponseHeaders(200, response.length());
        OutputStream outputStream = t.getResponseBody();
        outputStream.write(response.getBytes());
        outputStream.close();

    }

    private void sendCalculationRequests(List<Slave> slaves) throws Exception {
        String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String md5Hash = SlaveHandler.getMd5Hash();
        long possibilities = (long) Math.pow(alphabet.length(), SlaveHandler.getMaxLength());
        long size = possibilities / slaves.size();
        long range = 0;
        DoRequests doRequests = new DoRequests();
        for (int i = 0; i < slaves.size() - 1; i++) {
            Slave s = slaves.get(i);
            String start = String.valueOf(range);
            System.out.println("Numbers: " + possibilities + " size " + size + " range " + range);
            range = range + size;
            String end = String.valueOf(range);
            System.out.println("Sending calculation request to " + s.getPort() + " with ranges " + start + " - " + end);

            doRequests.postCalculationQuery(s.getIp(), s.getPort(), s.getId(), md5Hash, start, end, alphabet, myport);
        }
        Slave s = slaves.get(slaves.size() - 1);
        doRequests.postCalculationQuery(s.getIp(), s.getPort(), s.getId(), md5Hash, String.valueOf(range),
                String.valueOf(possibilities), alphabet, myport);

        System.out.println("löpp " + possibilities + " " + range);
    }

}
