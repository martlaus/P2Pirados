package utils;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by mart on 16.09.15.
 */
public class UniqueIdentifierGenerator {
    private SecureRandom random = new SecureRandom();

    public String nextId() {
        return new BigInteger(130, random).toString(32);
    }
}
