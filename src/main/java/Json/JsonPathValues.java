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

public class JsonPathValues {
    public static void main(String[] args) {
        try {
            // Load the JSON file
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(new File("src/main/resources/inp1.json"));

            // Create a list to store the paths and values
            List<String[]> records = new ArrayList<>();

            // Recursive function to traverse the JSON tree
            extractJSONPaths(jsonNode, "", records);

            // Write the data to a CSV file
            FileWriter out = new FileWriter("src/main/resources/Json2CSV_output.csv");
            CSVPrinter csvPrinter = new CSVPrinter(out, CSVFormat.DEFAULT);

            for (String[] record : records) {
                csvPrinter.printRecord((Object[]) record);
            }

            csvPrinter.flush();
            csvPrinter.close();

            System.out.println("CSV file generated successfully!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void extractJSONPaths(JsonNode node, String currentPath, List<String[]> records) {
        if (node.isObject()) {
            ObjectNode objectNode = (ObjectNode) node;
            Iterator<String> fieldNames = objectNode.fieldNames();

            while (fieldNames.hasNext()) {
                String fieldName = fieldNames.next();
                JsonNode fieldValue = objectNode.get(fieldName);
                String newPath = currentPath.isEmpty() ? fieldName : currentPath + "." + fieldName;
                extractJSONPaths(fieldValue, newPath, records);
            }
        } else if (node.isArray()) {
            for (int i = 0; i < node.size(); i++) {
                JsonNode arrayElement = node.get(i);
                String newPath = currentPath + "[" + i + "]";
                extractJSONPaths(arrayElement, newPath, records);
            }
        } else {
            // Leaf node, add the path and value to the records
            records.add(new String[]{currentPath, node.asText()});
        }
    }
}
