package com.practice.tm;
import java.io.*;
import java.util.*;

public class LogAnalyzer {

    public static void analyzeLogFile(String inputFile, String outputFile, List<String> keywords) {
        BufferedReader reader = null;
        BufferedWriter writer = null;
        Map<String, Integer> keywordCountMap = new HashMap<>();

        try {
            reader = new BufferedReader(new FileReader(inputFile));
            writer = new BufferedWriter(new FileWriter(outputFile));

            for (String keyword : keywords) {
                keywordCountMap.put(keyword, 0);
            }

            String line;
            while ((line = reader.readLine()) != null) {
                for (String keyword : keywords) {
                    if (line.contains(keyword)) {
                        keywordCountMap.put(keyword, keywordCountMap.get(keyword) + 1);
                    }
                }
            }

            for (String keyword : keywords) {
                writer.write(keyword + ": " + keywordCountMap.get(keyword) + "\n");
            }

            System.out.println("Analysis complete. Results written to: " + outputFile);
        } catch (IOException e) {
            System.err.println("An error occurred while processing the log files: " + e.getMessage());
        } finally {
            try {
                if (reader != null) reader.close();
                if (writer != null) writer.close();
            } catch (IOException e) {
                System.err.println("An error occurred while closing the resources: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        String inputFile = "D:\\input.txt";
        String outputFile = "output_analysis.txt";

        List<String> keywords = Arrays.asList("ERROR", "WARNING", "INFO");

        analyzeLogFile(inputFile, outputFile, keywords);
    }
}
