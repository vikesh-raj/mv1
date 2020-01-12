package com.example;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test.
     */
    @Test
    public void testApp() {
        String a = "hello";
        String b = "helloworld";
        assertTrue(b.startsWith(a));
        int start = a.length();
        String remaining = b.substring(start, b.length());
        assertTrue(remaining.equals("world"));
    }
}
