package cracker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mart on 27.09.15.
 */
public class SlaveHandler {
    public static List<Slave> slaves = new ArrayList<>();
    public static String md5Hash;
    public static int maxLength = 6;

    public static void addToList(Slave slave) {
        slaves.add(slave);
    }

    public static List<Slave> getSlaves() {
        return slaves;
    }

    public static String getMd5Hash() {
        return md5Hash;
    }

    public static void setMd5Hash(String md5Hash) {
        SlaveHandler.md5Hash = md5Hash;
    }

    public static int getMaxLength() {
        return maxLength;
    }

    public static void setMaxLength(int maxLength) {
        SlaveHandler.maxLength = maxLength;
    }
}
