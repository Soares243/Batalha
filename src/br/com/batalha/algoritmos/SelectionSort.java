package br.com.batalha.algoritmos;

/**
 * Implementacao do Selection Sort com contagem de comparacoes, trocas e tempo.
 */
public class SelectionSort implements SortingAlgorithm {
    private long comparisons;
    private long swaps;
    private long executionTime;

    @Override
    public void sort(int[] array) {
        comparisons = 0;
        swaps = 0;

        long start = System.nanoTime();

        for (int i = 0; i < array.length - 1; i++) {
            int minimumIndex = i;

            for (int j = i + 1; j < array.length; j++) {
                comparisons++;
                if (array[j] < array[minimumIndex]) {
                    minimumIndex = j;
                }
            }

            if (minimumIndex != i) {
                int temporary = array[i];
                array[i] = array[minimumIndex];
                array[minimumIndex] = temporary;
                swaps++;
            }
        }

        executionTime = System.nanoTime() - start;
    }

    @Override
    public long getComparisons() {
        return comparisons;
    }

    @Override
    public long getMoves() {
        return swaps;
    }

    @Override
    public long getExecutionTime() {
        return executionTime;
    }

    @Override
    public boolean isSorted(int[] array) {
        for (int i = 1; i < array.length; i++) {
            if (array[i - 1] > array[i]) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String getName() {
        return "Selection Sort";
    }
}
