package analysis;

import java.util.*;

import utils.LoggerUtil;

public class WordAnalyzer {

    public static void printRepeatedWords(List<String> titles) {

        Map<String, Integer> wordCount = new HashMap<>();
        boolean found = false;

        for (String title : titles) {

            String clean = title.toLowerCase().replaceAll("[^a-zA-Z ]", "");
            String[] words = clean.split("\\s+");

            for (String word : words) {

                if (word.length() < 3)
                    continue;

                wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
            }
        }

        LoggerUtil.log("\n===== Repeated Words (>2 times) =====");

        for (Map.Entry<String, Integer> entry : wordCount.entrySet()) {

            if (entry.getValue() > 2) {
            	LoggerUtil.log(entry.getKey() + " → " + entry.getValue());
                found = true;
            }
        }

        if (!found) {
        	LoggerUtil.log("No repetition found");
        }
    }
}
