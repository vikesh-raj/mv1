package com.example;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONReader {

    private static void readJson(String filename) {
        JSONParser parser = new JSONParser();
        try(BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            JSONObject root = (JSONObject) parser.parse(reader);
            String name = (String) root.get("name");
            System.out.println("name = " + name);

            JSONArray friends = (JSONArray) root.get("friends");
            for (Object j : friends) {
                JSONObject friend = (JSONObject)j;
                System.out.println("friend name " + (String)friend.get("name"));
            }
        } catch (FileNotFoundException e) {
            System.out.println("file not found " + filename);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        readJson("test.json");
    }
}