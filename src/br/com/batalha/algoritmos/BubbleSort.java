package br.com.batalha.algoritmos;

public class BubbleSort implements SortingAlgorithm {
    private long comparisons;
    private long swaps;
    private long executionTime;

    @Override
    public void sort(int[] array) {
        comparisons = 0;
        swaps = 0;
        long start = System.nanoTime();
        for (int end = array.length - 1; end > 0; end--) {
            boolean swapped = false;
            for (int i = 0; i < end; i++) {
                comparisons++;
                if (array[i] > array[i + 1]) {
                    int temporary = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = temporary;
                    swaps++;
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
        executionTime = System.nanoTime() - start;
    }

    @Override public long getComparisons() { return comparisons; }
    @Override public long getMoves() { return swaps; }
    @Override public long getExecutionTime() { return executionTime; }

    @Override
    public boolean isSorted(int[] array) {
        for (int i = 1; i < array.length; i++)
            if (array[i - 1] > array[i]) return false;
        return true;
    }

    @Override public String getName() { return "Bubble Sort"; }
}
