package Json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NewJsonCompare {
    public static void main(String[] args) {
        try {
            // Load the two JSON files
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode1 = objectMapper.readTree(new File("src/main/resources/inp1.json"));
            JsonNode jsonNode2 = objectMapper.readTree(new File("src/main/resources/inp2.json"));

            // Compare the JSON files and generate mismatch data
            List<String[]> mismatchRecords = new ArrayList<>();
            compareJSON(jsonNode1, jsonNode2, "", mismatchRecords);

            // Write mismatch data to CSV
            FileWriter mismatchOut = new FileWriter("src/main/resources/mismatches.csv");
            CSVPrinter mismatchCsvPrinter = new CSVPrinter(mismatchOut, CSVFormat.DEFAULT);

            for (String[] record : mismatchRecords) {
                mismatchCsvPrinter.printRecord((Object[]) record);
            }

            mismatchCsvPrinter.flush();
            mismatchCsvPrinter.close();

            System.out.println("Mismatch CSV file generated successfully!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void compareJSON(JsonNode node1, JsonNode node2, String currentPath, List<String[]> mismatchRecords) {
        if (!node1.equals(node2)) {
            if (node1.isObject() && node2.isObject()) {
                ObjectNode objectNode1 = (ObjectNode) node1;
                ObjectNode objectNode2 = (ObjectNode) node2;

                Iterator<String> fieldNames = objectNode1.fieldNames();
                while (fieldNames.hasNext()) {
                    String fieldName = fieldNames.next();
                    JsonNode fieldValue1 = objectNode1.get(fieldName);
                    JsonNode fieldValue2 = objectNode2.get(fieldName);

                    String newPath = currentPath.isEmpty() ? fieldName : currentPath + "." + fieldName;
                    compareJSON(fieldValue1, fieldValue2, newPath, mismatchRecords);
                }
            } else if (node1.isArray() && node2.isArray()) {
                for (int i = 0; i < node1.size(); i++) {
                    JsonNode arrayElement1 = node1.get(i);
                    JsonNode arrayElement2 = node2.get(i);

                    String newPath = currentPath + "[" + i + "]";
                    compareJSON(arrayElement1, arrayElement2, newPath, mismatchRecords);
                }
            } else {
                // Leaf node mismatch, add the path and values to the mismatch records
                String value1 = node1.isTextual() ? node1.asText() : node1.toString();
                String value2 = node2.isTextual() ? node2.asText() : node2.toString();

                mismatchRecords.add(new String[]{"TC1", currentPath, value1, value2});
            }
        }
    }
}
