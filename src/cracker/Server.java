package cracker;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Created by mart on 30.09.15.
 */
public class Server implements Runnable {
    private int port;

    public Server(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        HttpServer server = null; //queued incoming conns 10
        try {
            server = HttpServer.create(new InetSocketAddress(port), 10);

            server.createContext("/info", new InfoHandler());
            server.createContext("/resource", new ResourceQueryHandler());
            server.createContext("/resourcereply", new ResourceQueryReplyHandler());
            server.createContext("/checkmd5", new CheckMd5Handler());
            server.createContext("/answermd5", new AnswerMd5Handler());

            server.setExecutor(null); // creates a default executor
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
