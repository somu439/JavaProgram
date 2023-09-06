package Collections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DuplicateLetterCounter {
    public static void main(String[] args) {
        String input = "hello there";
        List<Character> duplicates = new ArrayList<>();
        Map<Character, Integer> occurrences = new HashMap<>();

        for (char c : input.toCharArray()) {
            if (Character.isLetter(c)) {
                char lowercaseC = Character.toLowerCase(c);
                if (occurrences.containsKey(lowercaseC)) {
                    occurrences.put(lowercaseC, occurrences.get(lowercaseC) + 1);
                } else {
                    occurrences.put(lowercaseC, 1);
                }
                if (occurrences.get(lowercaseC) == 2) {
                    duplicates.add(lowercaseC);
                }
            }
        }

        System.out.println("Duplicate Letters:");
        for (char c : duplicates) {
            System.out.println(c + " - Occurrences: " + occurrences.get(c));
        }
    }
}
