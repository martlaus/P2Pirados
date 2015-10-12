import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;

import java.io.*;

/**
 * Created by mart on 16.09.15.
 */
public class CheckMd5Handler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        int myport = httpExchange.getLocalAddress().getPort();
        System.out.println("Check md5 query (POST) received on port: " + myport + " starting calculations and then sending back response");

        InputStream is = httpExchange.getRequestBody();

        InputStreamReader isr =
                new InputStreamReader(is, "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String query = br.readLine();

        JSONObject jsonObj = new JSONObject(query);
        String masterIp = jsonObj.getString("ip");
        String masterPort = jsonObj.getString("port");
        String id = jsonObj.getString("id");
        String md5 = jsonObj.getString("md5");
        String rangeStart = jsonObj.getString("rangeStart");
        String rangeEnd = jsonObj.getString("rangeEnd");
        String alphabet = jsonObj.getString("alphabet");

        String response = "OK";
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream outputStream = httpExchange.getResponseBody();
        outputStream.write(response.getBytes());
        outputStream.close();

        Calculator calculator = new Calculator(md5, rangeStart, rangeEnd, alphabet, masterIp, masterPort, id);
        calculator.run();

//        System.out.println("(my port: " + myport + ") Data from calculation request query: " + masterIp + ":" +
//                masterPort + " id: " + id + " md5: " + md5 + " start " + rangeStart + " end " + rangeEnd + " alphabet " +
//                alphabet);
    }
}
