package com.tsystems.javaschool.tasks.pyramid;

import java.util.Collections;
import java.util.List;

public class PyramidBuilder {

    /**
     * Builds a pyramid with sorted values (with minumum value at the top line and maximum at the bottom,
     * from left to right). All vacant positions in the array are zeros.
     *
     * @param inputNumbers to be used in the pyramid
     * @return 2d array with pyramid inside
     * @throws {@link CannotBuildPyramidException} if the pyramid cannot be build with given input
     */
    public int[][] buildPyramid(List<Integer> inputNumbers) {
        try {
            Collections.sort(inputNumbers);
            int rows = checkCanBuildPyramid(inputNumbers);
            if (rows == -1) {
                throw new CannotBuildPyramidException();
            }
            int columns = rows + rows - 1;
            int[][] array = new int[rows][columns];
            fillWithZeros(array);
            int startListIndex = 0;
            for (int i = 0; i < rows; i++) {
                startListIndex = fillRowWithElementsOfList(inputNumbers, startListIndex,
                        i + 1, array, columns, i);
            }
            return array;
        } catch (Throwable ex) {
            throw new CannotBuildPyramidException();
        }
    }

    private int checkCanBuildPyramid(List<Integer> inputNumbers) {
        int size = inputNumbers.size();
        int sum = 0;
        for (int i = 1; ; i++) {
            sum = sum + i;
            if (sum == size) {
                return i;
            }
            if (sum > size) {
                return -1;
            }
        }
    }

    private void fillWithZeros(int[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                array[i][j] = 0;
            }
        }
    }

    private int fillRowWithElementsOfList(List<Integer> inputNumbers, int startListIndex,
                                          int quantityOfElementsInRow, int[][] array, int columns,
                                          int rowNumber) {
        int start = (columns - quantityOfElementsInRow - (quantityOfElementsInRow - 1)) / 2;
        int quantity = 1;
        for (int j = start; quantity <= quantityOfElementsInRow; j = j + 2) {
            Integer element = inputNumbers.get(startListIndex);
            if (element == null) {
                throw new CannotBuildPyramidException();
            }
            array[rowNumber][j] = element;
            startListIndex++;
            quantity++;
        }
        return startListIndex;
    }
}
