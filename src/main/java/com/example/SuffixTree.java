package com.example;

class SuffixTree extends PrefixTree {

    SuffixTree(String pattern) {
        int l = pattern.length();
        for(int i=l-1; i>= 0; i--) {
            String suffix = pattern.substring(i, l);
            insert(suffix);
        }
    }

    public boolean hasSubstring(String suffix) {
        return search(suffix);
    }

    public static void main(String[] args) {
        SuffixTree t = new SuffixTree("banana");
        System.out.println(t);

        System.out.println("Has hasSubstring nan = " + t.hasSubstring("nan"));
        System.out.println("Has hasSubstring nana = " + t.hasSubstring("nana"));
        System.out.println("Has hasSubstring ben = " + t.hasSubstring("ben"));
    }
}