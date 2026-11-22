package br.com.batalha.algoritmos;

import java.util.Arrays;

public class MergeSortTest {
    public static void main(String[] args) {
        SortingAlgorithm algorithm = new MergeSort();
        assertMatchesReference(algorithm, new int[]{5, -1, 3, 3, 0, 10});
        assertMatchesReference(algorithm, new int[]{1, 2, 3, 4, 5});
        assertMatchesReference(algorithm, new int[]{5, 4, 3, 2, 1});
        assertMatchesReference(algorithm, new int[]{2, 2, 1, 2, 1});
        assertMatchesReference(algorithm, new int[]{8, -3, 5, 5, 0, 12, -9, 2, 1, 7});
        assertMatchesReference(algorithm, new int[]{});
        assertMatchesReference(algorithm, new int[]{42});
        System.out.println("MergeSortTest: OK");
    }

    private static void assertMatchesReference(SortingAlgorithm algorithm, int[] input) {
        int[] array = input.clone();
        int[] expected = input.clone();
        Arrays.sort(expected);
        algorithm.sort(array);
        if (!algorithm.isSorted(array))
            throw new AssertionError(algorithm.getName() + " nao ordenou o vetor");
        if (!Arrays.equals(array, expected))
            throw new AssertionError(algorithm.getName() + " produziu resultado incorreto");
    }
}
