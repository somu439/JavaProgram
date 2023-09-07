package Json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CompareJsonCsv2 {
    public static void main(String[] args) {
        try {
            // Load the JSON response file
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonResponse = objectMapper.readTree(new File("src/main/resources/inp1.json"));

            // Load the CSV file containing expected JSON paths and values
            FileReader csvFile = new FileReader("src/main/resources/Json2CSV_output.csv");
            Iterable<CSVRecord> csvRecords = CSVFormat.DEFAULT.parse(csvFile);

            // Prepare a list to store the results
            List<ResultData> results = new ArrayList<>();

            // Compare expected data to JSON response
            for (CSVRecord record : csvRecords) {
                if (record.size() >= 2) {
                    String jsonPath = record.get(0);
                    String expectedValue = record.get(1);
                    JsonNode actualValue = jsonResponse.at(jsonPath);

                    boolean match = false;
                    if (!actualValue.isMissingNode() && actualValue.toString().equals(expectedValue)) {
                        match = true;
                    }

                    results.add(new ResultData(jsonPath, expectedValue, actualValue.toString(), match));
                }
            }

            // Write the results to a CSV file
            FileWriter resultFile = new FileWriter("src/main/resources/results.csv");
            CSVPrinter csvPrinter = CSVFormat.DEFAULT
                    .withHeader("JSON Path", "Expected Value", "Actual Value", "Match")
                    .print(resultFile);

            for (ResultData result : results) {
                csvPrinter.printRecord(result.getJsonPath(), result.getExpectedValue(), result.getActualValue(), result.isMatch());
            }

            csvPrinter.flush();
            csvPrinter.close();

            System.out.println("JSON path-value comparison completed, and results are written to results.csv!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ResultData {
        private String jsonPath;
        private String expectedValue;
        private String actualValue;
        private boolean match;

        ResultData(String jsonPath, String expectedValue, String actualValue, boolean match) {
            this.jsonPath = jsonPath;
            this.expectedValue = expectedValue;
            this.actualValue = actualValue;
            this.match = match;
        }

        String getJsonPath() {
            return jsonPath;
        }

        String getExpectedValue() {
            return expectedValue;
        }

        String getActualValue() {
            return actualValue;
        }

        boolean isMatch() {
            return match;
        }
    }
}
