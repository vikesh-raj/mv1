package com.example;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class LineFileReader {

    private static void readFile(String filename) {
        StringBuilder sb = new StringBuilder();
        try(BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (FileNotFoundException e) {
            System.out.println("file not found " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(sb);
    }

    public static void main(String[] args) {
        readFile("test.json");
    }
}