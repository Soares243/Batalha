package br.com.batalha.algoritmos;

import java.util.Arrays;

public class InsertionSortTest {
    public static void main(String[] args) {
        // O mesmo algoritmo e exercitado com entradas de formatos diferentes.
        SortingAlgorithm algorithm = new InsertionSort();

        int[] array = {5, -1, 3, 3, 0, 10};
        assertMatchesReference(algorithm, array);

        int[] sorted = {1, 2, 3, 4, 5};
        assertMatchesReference(algorithm, sorted);

        int[] reversed = {5, 4, 3, 2, 1};
        assertMatchesReference(algorithm, reversed);

        int[] tenElements = {8, -3, 5, 5, 0, 12, -9, 2, 1, 7};
        assertMatchesReference(algorithm, tenElements);

        System.out.println("InsertionSortTest: OK");
    }

    private static void assertMatchesReference(
            SortingAlgorithm algorithm,
            int[] input
    ) {
        // Ordena uma copia com a biblioteca padrao para servir de referencia.
        int[] array = input.clone();
        int[] expected = input.clone();
        Arrays.sort(expected);
        algorithm.sort(array);

        // Confere tanto a propriedade de ordem quanto a igualdade exata dos valores.
        if (!algorithm.isSorted(array)) {
            throw new AssertionError(algorithm.getName() + " nao ordenou o vetor");
        }
        if (!Arrays.equals(array, expected)) {
            throw new AssertionError(algorithm.getName() + " produziu resultado incorreto");
        }
    }
}
