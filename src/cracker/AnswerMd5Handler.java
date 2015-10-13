package cracker;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;

import java.io.*;

/**
 * Created by mart on 25.09.15.
 */
public class AnswerMd5Handler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String myport = String.valueOf(httpExchange.getLocalAddress().getPort());
        System.out.println("Answer md5 query (POST) received on port: " + myport + " printing result to console for Master on start port");

        InputStream is = httpExchange.getRequestBody();

        InputStreamReader isr =
                new InputStreamReader(is, "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String query = br.readLine();

        JSONObject jsonObj = new JSONObject(query);
        String slaveIp = jsonObj.getString("ip");
        String slavePort = String.valueOf(jsonObj.getInt("port"));
        String id = jsonObj.getString("id");
        String md5 = jsonObj.getString("md5");
        String result = String.valueOf(jsonObj.getInt("result"));
        String resultstring = jsonObj.getString("resultstring");


        System.out.println("(my port: " + myport + ") Data from resource query: " + slaveIp + ":" + slavePort +
                " id: " + id + " md5Hash: " + md5 + " result code: " + result + " PASSWORD: " + resultstring);

        String response = "OK";
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream outputStream = httpExchange.getResponseBody();
        outputStream.write(response.getBytes());
        outputStream.close();
    }
}
