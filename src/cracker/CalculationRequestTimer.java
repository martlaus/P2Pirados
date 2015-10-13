package cracker;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by mart on 14.10.15.
 */
public class CalculationRequestTimer {

    Timer timer;
    int myport;

    public CalculationRequestTimer(int seconds, int myport) {
        this.myport = myport;
        System.out.println("Timer started");
        timer = new Timer();
        timer.schedule(new RemindTask(), seconds * 1000);
    }

    private void sendCalculationRequests(List<Slave> slaves) throws Exception {

        String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String md5Hash = SlaveHandler.getMd5Hash();
        long possibilities = (long) Math.pow(alphabet.length(), SlaveHandler.getMaxLength());
        long size = possibilities / slaves.size();
        long range = 0;
        DoRequests doRequests = new DoRequests();
        System.out.println("Number of slaves: " + SlaveHandler.getSlaves().size());

        for (int i = 0; i < slaves.size() - 1; i++) {
            Slave slave = slaves.get(i);
            String start = String.valueOf(range);
            range = range + size;
            String end = String.valueOf(range);
            CalculationSender calculationSender = new CalculationSender(slave, myport, md5Hash, alphabet, start, end);
            calculationSender.run();

        }

        Slave s = slaves.get(slaves.size() - 1);
        try {
            doRequests.postCalculationQuery(s.getIp(), s.getPort(), s.getId(), md5Hash, String.valueOf(range),
                    String.valueOf(possibilities), alphabet, myport);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    class RemindTask extends TimerTask {
        public void run() {
            System.out.println("Time's up!");
            timer.cancel();
            SlaveHandler.setMaxLength(4);
            List<Slave> slaves = SlaveHandler.getSlaves();
            try {
                sendCalculationRequests(slaves);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
