package com.example.bincard.model;

import java.util.Comparator;

public class Comparators {
    public static Comparator<String> stringAtoZ = new Comparator<String>() {
        @Override
        public int compare(String s, String s1) {
            return s.compareTo(s1);
        }
    };
}
