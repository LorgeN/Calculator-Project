package com.lorgen.terminal;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int rowCount = scanner.nextInt(); // Row count
        int columnCount = scanner.nextInt(); // Column count

        boolean[][] map = new boolean[rowCount][columnCount];

        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                map[rowIndex][columnIndex] = scanner.nextInt() == 1;
            }
            /*
            String input = scanner.nextLine();
            char[] contents = input.toCharArray();
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                map[rowIndex][columnIndex] = contents[columnIndex] == '1';
            }
            */
        }

        Map<Integer, Set<String>> sections = new HashMap<>();
        for (int r = 0; r < rowCount; r++) {
            column:
            for (int c = 0; c < columnCount; c++) {
                String strRep = r + "," + c;
                for (Set<String> strings : sections.values()) {
                    if (!strings.contains(strRep)) {
                        continue;
                    }

                    continue column;
                }

                sections.put(sections.size(), getSection(r, c, map));
            }
        }

        int queryCount = scanner.nextInt();
        for (int i = 0; i < queryCount; i++) {
            int r1 = scanner.nextInt() - 1;
            int c1 = scanner.nextInt() - 1;
            int r2 = scanner.nextInt() - 1;
            int c2 = scanner.nextInt() - 1;

            boolean isDecimal1 = map[r1][c1];
            boolean isDecimal2 = map[r2][c2];
            if (isDecimal1 != isDecimal2) {
                System.out.println("neither");
                continue;
            }

            String str1 = r1 + "," + c1;
            String str2 = r2 + "," + c2;
            int section1 = -1, section2 = -1;
            for (Entry<Integer, Set<String>> entry : sections.entrySet()) {
                if (entry.getValue().contains(str1)) {
                    section1 = entry.getKey();
                }

                if (entry.getValue().contains(str2)) {
                    section2 = entry.getKey();
                }

                if (section1 == -1 || section2 == -1) {
                    continue;
                }

                break;
            }

            if (section1 != section2) {
                System.out.println("neither");
            }

            System.out.println(isDecimal1 ? "decimal" : "binary");
        }
    }

    private static Set<String> getSection(int r, int c, boolean[][] map) {
        Set<String> set = new HashSet<>();
        set.add(r + "," + c);
        return getAdjacent(set, r, c, map);
    }

    private static Set<String> getAdjacent(Set<String> set, int r, int c, boolean[][] map) {
        boolean current = map[r][c];
        if (r < (map.length - 1) && map[r + 1][c] == current) {
            if (set.add((r + 1) + "," + c)) {
                set.addAll(getAdjacent(set, r + 1, c, map));
            }
        }

        if (r > 0 && map[r - 1][c] == current) {
            if (set.add((r - 1) + "," + c)) {
                set.addAll(getAdjacent(set, r - 1, c, map));
            }
        }

        if (c < (map[0].length - 1) && map[r][c + 1] == current) {
            if (set.add(r + "," + (c + 1))) {
                set.addAll(getAdjacent(set, r, c + 1, map));
            }
        }

        if (c > 0 && map[r][c - 1] == current) {
            if (set.add(r + "," + (c - 1))) {
                set.addAll(getAdjacent(set, r, c - 1, map));
            }
        }

        return set;
    }
}
