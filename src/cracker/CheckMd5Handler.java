package cracker;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;

/**
 * Created by mart on 16.09.15.
 */
public class CheckMd5Handler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String myport = String.valueOf(httpExchange.getLocalAddress().getPort());
        System.out.println("Check md5 query (POST) received on port: " + myport + " starting calculations and then sending back response");

        InputStream is = httpExchange.getRequestBody();

        InputStreamReader isr =
                new InputStreamReader(is, "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String query = br.readLine();

        String response = "0";
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream outputStream = httpExchange.getResponseBody();
        outputStream.write(response.getBytes());
        outputStream.close();

        //cracker.Calculator calculator = new cracker.Calculator(md5, rangeStart, rangeEnd, alphabet, masterIp, masterPort, id, myport);
        Calculator calculator = new Calculator(query, myport);
        calculator.run();


    }
}
