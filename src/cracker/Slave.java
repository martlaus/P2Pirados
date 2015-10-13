package cracker;

/**
 * Created by mart on 27.09.15.
 */
public class Slave {
    private String ip;
    private String port;
    private String resource;
    private String id;

    public Slave(String ip, String port, String resource, String id){
        this.ip = ip;
        this.port = port;
        this.resource = resource;
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
