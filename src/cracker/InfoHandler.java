package cracker;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.simple.JSONArray;
import utils.MachinesReader;
import utils.UniqueIdentifierGenerator;

import java.io.IOException;
import java.io.OutputStream;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mart on 16.09.15.
 */
public class InfoHandler implements HttpHandler {

    public void handle(HttpExchange t) throws IOException {
        String sendport = String.valueOf(t.getLocalAddress().getPort());
        String response = "Welcome to the starting site on port " + sendport
                + ", trying to make resource queries to other servers in my machines.txt list.";


        try {
            Map<String, String> params = queryToMap(t.getRequestURI().getQuery());
            String md5Hash = params.get("md5"); //
            doQuerysToMyMachines(sendport, md5Hash);

        } catch (Exception e) {
            response = "Add a hash to calculate, like: ?md5=yourhash";
        }

        t.sendResponseHeaders(200, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();


    }

    public void doQuerysToMyMachines(String sendport, String md5Hash) throws UnknownHostException {
        UniqueIdentifierGenerator uniqueIdentifierGenerator = new UniqueIdentifierGenerator();
        String sendip = "192.168.0.103";
        SlaveHandler.setMd5Hash(md5Hash);


        //do resource querys
        MachinesReader machinesReader = new MachinesReader();
        JSONArray machines = machinesReader.readFileToArray(sendport);

        DoRequests doRequests = new DoRequests();

        //set query id
        String id = uniqueIdentifierGenerator.nextId();

        try {
            String noask = sendip + "_" + sendport;
            String ttl = "5";
            doRequests.sendGetRequests(machines, sendip, sendport, noask, id, ttl);
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
