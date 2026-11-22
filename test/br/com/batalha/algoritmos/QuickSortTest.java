package br.com.batalha.algoritmos;

import java.util.Arrays;

public class QuickSortTest {
    public static void main(String[] args) {
        // Testa entradas comuns, ordenadas, invertidas, com repeticoes e com n=10.
        SortingAlgorithm algorithm = new QuickSort();

        int[] array = {5, -1, 3, 3, 0, 10};
        assertMatchesReference(algorithm, array);

        int[] sorted = {1, 2, 3, 4, 5};
        assertMatchesReference(algorithm, sorted);

        int[] reversed = {5, 4, 3, 2, 1};
        assertMatchesReference(algorithm, reversed);

        int[] duplicates = {2, 2, 1, 2, 1};
        assertMatchesReference(algorithm, duplicates);

        int[] tenElements = {8, -3, 5, 5, 0, 12, -9, 2, 1, 7};
        assertMatchesReference(algorithm, tenElements);

        System.out.println("QuickSortTest: OK");
    }

    private static void assertMatchesReference(
            SortingAlgorithm algorithm,
            int[] input
    ) {
        // Cria o resultado esperado independentemente usando Arrays.sort.
        int[] array = input.clone();
        int[] expected = input.clone();
        Arrays.sort(expected);
        algorithm.sort(array);

        // Verifica que a saida esta crescente e corresponde a referencia.
        if (!algorithm.isSorted(array)) {
            throw new AssertionError(algorithm.getName() + " nao ordenou o vetor");
        }
        if (!Arrays.equals(array, expected)) {
            throw new AssertionError(algorithm.getName() + " produziu resultado incorreto");
        }
    }
}
