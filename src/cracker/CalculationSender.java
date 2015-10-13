package cracker;

/**
 * Created by mart on 14.10.15.
 */
public class CalculationSender implements Runnable {
    private int myport;
    private Slave s;
    private String md5Hash;
    private String alphabet;
    private String start;
    private String end;

    public CalculationSender(Slave s, int myport, String md5Hash, String alphabet, String start, String end) {
        this.myport = myport;
        this.s = s;
        this.md5Hash = md5Hash;
        this.alphabet = alphabet;
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        DoRequests doRequests = new DoRequests();


        System.out.println("Sending calculation request to " + s.getPort() + " with ranges " + start + " - " + end);

        try {
            doRequests.postCalculationQuery(s.getIp(), s.getPort(), s.getId(), md5Hash, start, end, alphabet, myport);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
