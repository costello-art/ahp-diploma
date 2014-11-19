package util;

import model.Actor;

import java.util.*;
import java.util.Map.Entry;

public class MapSort {
    public static Map<Actor, Double> sortByComparator(Map<Actor, Double> unsortMap, final boolean asc) {

        List<Entry<Actor, Double>> list = new LinkedList<>(unsortMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Entry<Actor, Double>>() {
            public int compare(Entry<Actor, Double> o1,
                               Entry<Actor, Double> o2) {
                if (asc) {
                    return o1.getValue().compareTo(o2.getValue());
                } else {
                    return o2.getValue().compareTo(o1.getValue());

                }
            }
        });

        // Maintaining insertion asc with the help of LinkedList
        Map<Actor, Double> sortedMap = new LinkedHashMap<>();
        for (Entry<Actor, Double> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

    public static void printMap(Map<String, Float> map) {
        for (Entry<String, Float> entry : map.entrySet()) {
            System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
        }
    }
}