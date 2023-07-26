package org.example;

import org.json.JSONObject;
import org.json.JSONException;
import org.json.JSONTokener;
import com.opencsv.CSVWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonComparator {

    public static void main(String[] args) {
        String jsonFile1 = "file1.json";
        String jsonFile2 = "file2.json";
        String outputFile = "output.csv";

        JSONObject jsonObject1 = readJsonFile(jsonFile1);
        JSONObject jsonObject2 = readJsonFile(jsonFile2);

        List<String[]> differences = compareJsonObjects("", jsonObject1, jsonObject2);

        writeCsvFile(outputFile, differences);
    }

    private static JSONObject readJsonFile(String filename) {
        try (FileReader reader = new FileReader(filename)) {
            JSONTokener tokener = new JSONTokener(reader);
            return new JSONObject(tokener);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static List<String[]> compareJsonObjects(String jsonPath, JSONObject obj1, JSONObject obj2) {
        List<String[]> differences = new ArrayList<>();

        for (String key : obj1.keySet()) {
            if (obj2.has(key)) {
                Object val1 = obj1.get(key);
                Object val2 = obj2.get(key);

                if (!val1.equals(val2)) {
                    String diffValue = "Value1: " + val1 + ", Value2: " + val2;
                    differences.add(new String[]{jsonPath + "." + key, diffValue});
                }

                if (val1 instanceof JSONObject && val2 instanceof JSONObject) {
                    differences.addAll(compareJsonObjects(jsonPath + "." + key, (JSONObject) val1, (JSONObject) val2));
                }
            } else {
                String diffValue = "Value1: " + obj1.get(key) + ", Value2: <not present>";
                differences.add(new String[]{jsonPath + "." + key, diffValue});
            }
        }

        for (String key : obj2.keySet()) {
            if (!obj1.has(key)) {
                String diffValue = "Value1: <not present>, Value2: " + obj2.get(key);
                differences.add(new String[]{jsonPath + "." + key, diffValue});
            }
        }

        return differences;
    }

    private static void writeCsvFile(String filename, List<String[]> data) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filename))) {
            writer.writeAll(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
