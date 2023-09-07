package Json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CompareJsonCsv {
    public static void main(String[] args) {
        try {
            // Load the JSON response file
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonResponse = objectMapper.readTree(new File("src/main/resources/inp1.json"));

            // Load the CSV file containing expected JSON paths and values
            FileReader csvFile = new FileReader("src/main/resources/Json2CSV_output.csv");
            CSVParser csvParser = CSVFormat.DEFAULT.parse(csvFile);

            // Store expected JSON path-value pairs from CSV
            List<ExpectedData> expectedDataList = new ArrayList<>();
            for (CSVRecord record : csvParser) {
                if (record.size() >= 2) {
                    String jsonPath = record.get(0);
                    String expectedValue = record.get(1);
                    expectedDataList.add(new ExpectedData(jsonPath, expectedValue));
                }
            }

            // Compare expected data to JSON response
            for (ExpectedData expectedData : expectedDataList) {
                String jsonPath = expectedData.getJsonPath();
                String expectedValue = expectedData.getExpectedValue();
                JsonNode actualValue = jsonResponse.at(jsonPath);

                if (actualValue.isMissingNode() || !actualValue.toString().equals(expectedValue)) {
                    System.out.println("Mismatch found at JSON path: " + jsonPath);
                    System.out.println("Expected: " + expectedValue);
                    System.out.println("Actual: " + actualValue);
                }
            }

            System.out.println("JSON path-value comparison completed!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ExpectedData {
        private String jsonPath;
        private String expectedValue;

        ExpectedData(String jsonPath, String expectedValue) {
            this.jsonPath = jsonPath;
            this.expectedValue = expectedValue;
        }

        String getJsonPath() {
            return jsonPath;
        }

        String getExpectedValue() {
            return expectedValue;
        }
    }
}

