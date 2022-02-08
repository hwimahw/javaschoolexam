package com.tsystems.javaschool.tasks.subsequence;

import java.util.List;

public class Subsequence {

    /**
     * Checks if it is possible to get a sequence which is equal to the first
     * one by removing some elements from the second one.
     *
     * @param x first sequence
     * @param y second sequence
     * @return <code>true</code> if possible, otherwise <code>false</code>
     */
    @SuppressWarnings("rawtypes")
    public boolean find(List x, List y) {
        if (x == null || y == null) {
            throw new IllegalArgumentException();
        }
        int index = -1;
        for (int i = 0; i < x.size(); i++) {
            Object fromX = x.get(i);
            int flag = 0;
            for (int j = index + 1; j < y.size(); j++) {
                Object fromY = y.get(j);
                if (fromX.equals(fromY)) {
                    index = j;
                    flag = 1;
                    break;
                }
            }
            if (flag == 0) {
                return false;
            }
        }
        return true;
    }
}
