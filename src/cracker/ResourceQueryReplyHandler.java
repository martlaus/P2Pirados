package cracker;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;

import java.io.*;

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
        String port = String.valueOf(jsonObj.getInt("port"));
        String id = jsonObj.getString("id");
        String resource = String.valueOf(jsonObj.getInt("resource"));
        System.out.println("(my port: " + myport + ") Data from resource query: " + ip + ":" + port + " id: " + id + " resource: " + resource);
        Slave slave = new Slave(ip, port, resource, id);

        if (SlaveHandler.getSlaves().size() == 0) {
            new CalculationRequestTimer(5, myport);

        }

        //store slave data and add to list
        SlaveHandler.addToList(slave);
        System.out.println("Number of slaves: " + SlaveHandler.getSlaves().size());


        String response = "0";
        t.sendResponseHeaders(200, response.length());
        OutputStream outputStream = t.getResponseBody();
        outputStream.write(response.getBytes());
        outputStream.close();
    }
}
