package utils;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

/**
 * Created by mart on 11.10.15.
 */
public class MachinesReader {

    public JSONArray readFileToArray(String filename) {
        JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(new FileReader(
                    filename + ".txt"));

            return  (JSONArray) obj;
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return null;
    }
}
