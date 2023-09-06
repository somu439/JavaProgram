package StringManipulations;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class PropertyReader {

    private Map<String, List<String>> propertiesMap;

    public PropertyReader(String filePath) {
        propertiesMap = readProperties(filePath);
    }

    public Map<String, List<String>> readProperties(String filePath) {
        Properties properties = new Properties();

        try (InputStream inputStream = new FileInputStream(filePath)) {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        Map<String, List<String>> resultMap = new HashMap<>();
        for (String key : properties.stringPropertyNames()) {
            String value = properties.getProperty(key);
            String[] valuesArray = value.split(",");
            List<String> valuesList = Arrays.asList(valuesArray);
            resultMap.put(key, valuesList);
        }

        return resultMap;
    }

    public String getValueForArrayIndex(String key, int index) {
        List<String> values = propertiesMap.get(key);
        if (values != null && index >= 0 && index < values.size()) {
            return values.get(index);
        }
        return null;
    }

    public static void main(String[] args) {
        String filePath = "sample.properties";
        PropertyReader propertyReader = new PropertyReader(filePath);

        String key = "key2";
        int index = 2;

        String value = propertyReader.getValueForArrayIndex(key, index);
        if (value != null) {
            System.out.println("Value for " + key + " at index " + index + ": " + value);
        } else {
            System.out.println("Key not found or invalid index.");
        }
    }
}
