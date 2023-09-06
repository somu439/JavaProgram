package StringManipulations;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StringDuplicate {
    public static void main(String[] args) {
        String s="gainjavaknowledge";
        List<String> dupElements= Arrays.stream(s.split(""))
                .collect(Collectors.groupingBy(Function.identity(),Collectors.counting()))
                .entrySet().stream()
                .filter(ele -> ele.getValue() >1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        System.out.println(dupElements);

    }
}
