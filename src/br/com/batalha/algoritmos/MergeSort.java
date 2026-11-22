package br.com.batalha.algoritmos;

public class MergeSort implements SortingAlgorithm {
    private long comparisons;
    private long movements;
    private long executionTime;

    @Override
    public void sort(int[] array) {
        comparisons = 0;
        movements = 0;
        long start = System.nanoTime();
        if (array.length > 1) {
            mergeSort(array, new int[array.length], 0, array.length - 1);
        }
        executionTime = System.nanoTime() - start;
    }

    private void mergeSort(int[] array, int[] auxiliary, int left, int right) {
        if (left >= right) return;
        int middle = left + (right - left) / 2;
        mergeSort(array, auxiliary, left, middle);
        mergeSort(array, auxiliary, middle + 1, right);
        merge(array, auxiliary, left, middle, right);
    }

    private void merge(int[] array, int[] auxiliary, int left, int middle, int right) {
        for (int i = left; i <= right; i++) auxiliary[i] = array[i];
        int i = left, j = middle + 1, k = left;
        while (i <= middle && j <= right) {
            comparisons++;
            if (auxiliary[i] <= auxiliary[j]) array[k++] = auxiliary[i++];
            else array[k++] = auxiliary[j++];
            movements++;
        }
        while (i <= middle) { array[k++] = auxiliary[i++]; movements++; }
        while (j <= right) { array[k++] = auxiliary[j++]; movements++; }
    }

    @Override public long getComparisons() { return comparisons; }
    @Override public long getMoves() { return movements; }
    @Override public long getExecutionTime() { return executionTime; }

    @Override
    public boolean isSorted(int[] array) {
        for (int i = 1; i < array.length; i++)
            if (array[i - 1] > array[i]) return false;
        return true;
    }

    @Override public String getName() { return "Merge Sort"; }
}
