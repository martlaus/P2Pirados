package cracker;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static List<Integer> ports = new ArrayList<>();

    public static void main(String[] args) throws Exception {

        //start servers on ports:
        ports.add(8000);
        ports.add(8001);
        ports.add(8002);
        ports.add(8003);
        ports.add(8004);
        ports.add(8005);
        ports.add(8006);
        ports.add(8007);
        ports.add(8008);
        ports.add(8009);
        ports.add(8010);
        ports.add(8011);
        ports.add(8012);


        for (int port : ports) {
            Server server2 = new Server(port);
            server2.run();
        }
    }
}
