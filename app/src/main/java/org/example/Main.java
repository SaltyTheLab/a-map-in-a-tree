package org.example;
public class Main {
    public static void main(String[] args) {
        TreeMap<String, String> map = new TreeMap<>();

        map.insert("keyOne", "22");
        map.insert("keyTwo", "34");
        map.insert("keyThree", "45");
        map.insert("keyfour", "60");
        map.insert("keyfive","100");

        System.out.println(map.get("keyOne"));
        System.out.println(map.get("keyThree")); 

        map.delete("keyTwo");
        System.out.println(map.get("keyTwo"));
    }
}