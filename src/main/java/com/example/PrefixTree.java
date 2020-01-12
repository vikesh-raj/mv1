package com.example;

import java.util.ArrayList;
import java.util.List;

class PrefixTree {
    static class Node {
        boolean isEnd;
        String label;
        List<Node> children;

        Node(String label, boolean isEnd) {
            this.label = label;
            this.isEnd = isEnd;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(label);
            if (isEnd) {
                sb.append("$");
            }
            if (children != null) {
                sb.append("[");
                for (Node child: children) {
                    sb.append("(");
                    sb.append(child.toString());
                    sb.append(")");
                }
                sb.append("]");
            }
            return sb.toString();
        }
    }
    Node root = new Node("", true);

    void insert(String word) {
        insert(word, root);
    }

    public String toString() {
        return root.toString();
    }

    private String findLongestPrefix(String word1, String word2) {
        int len1 = word1.length();
        int len2 = word2.length();
        int minLength = len1 < len2 ? len1 : len2;
        int i = 0;
        for(i = 0; i < minLength; i++) {
            if (word1.charAt(i) != word2.charAt(i)) {
                break;
            }
        }
        if (i == 0) {
            return null;
        }
        return word1.substring(0, i);
    }

    private void insert(String word, Node current) {
        if (current.children == null) {
            current.children = new ArrayList<>();
            current.children.add(new Node(word, true));
            return;
        }
        for(Node child: current.children) {
            String common = findLongestPrefix(word, child.label);
            if (common == null) {
                continue;
            }

            if(common.length() == word.length() && common.length() == child.label.length()) {
                child.isEnd = true;
            } else if (common.length() == word.length()) {
                // split the node.
                String remaining = child.label.substring(word.length());
                Node n = new Node(remaining, child.isEnd);
                n.children = child.children;
                child.label = word;
                child.isEnd = true;
                child.children = new ArrayList<>();
                child.children.add(n);
            } else if (common.length() == child.label.length()) {
                // add children
                String remaining = word.substring(child.label.length());
                insert(remaining, child);
            } else {
                String labelRemaining = child.label.substring(common.length());
                String wordRemaining = word.substring(common.length());
    
                Node n1 = new Node(labelRemaining, child.isEnd);
                n1.children = child.children;
    
                Node n2 = new Node(wordRemaining, true);
    
                child.label = common;
                child.isEnd = false;
                child.children = new ArrayList<>();
                child.children.add(n1);
                child.children.add(n2);
            }
            return;
        }
        // Word not found. add a new node;
        current.children.add(new Node(word, true));
    }

    public boolean search(String word) {
        return search(word, root);
    }

    private boolean search(String word, Node current) {
        if (current.children == null) {
            return false;
        }
        for (Node child: current.children) {
            String common = findLongestPrefix(word, child.label);
            if (common == null) {
                continue;
            }
            if (common.length() == word.length() && child.isEnd) {
                return true;
            } else if (common.length() == child.label.length()) {
                String remaining = word.substring(common.length());
                return search(remaining, child);
            }
        }
        return false;
    }

    public static void main(String[] args) {
        PrefixTree t = new PrefixTree();
        t.insert("facebook");
        //t.insert("face");
        t.insert("facepalm");
        t.insert("this");
        t.insert("there");
        t.insert("then");
        t.insert("the");
        System.out.println(t);

        System.out.println("Has face = " + t.search("face"));
        System.out.println("Has facebook = " + t.search("facebook"));
        System.out.println("Has there = " + t.search("there"));
        System.out.println("Has the = " + t.search("the"));
    }
}