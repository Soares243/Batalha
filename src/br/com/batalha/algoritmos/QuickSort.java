package br.com.batalha.algoritmos;

/**
 * Ordena um vetor com Quick Sort e escolhe o pivo pela mediana de tres
 * para reduzir a chance de particoes muito desequilibradas.
 * Responsavel: Ester Soares Serafim.
 */
public class QuickSort implements SortingAlgorithm {
    // Metricas acumuladas durante a chamada mais recente de sort.
    private long comparisons;
    private long swaps;
    private long executionTime;

    @Override
    public void sort(int[] array) {
        // Reinicia os contadores antes de ordenar um novo vetor.
        comparisons = 0;
        swaps = 0;

        // Mede somente a ordenacao, sem incluir validacoes externas.
        long start = System.nanoTime();
        quickSort(array, 0, array.length - 1);
        executionTime = System.nanoTime() - start;
    }

    private void quickSort(int[] array, int low, int high) {
        // Faixa vazia ou de um elemento ja esta ordenada.
        if (low >= high) {
            return;
        }

        // Move o pivo escolhido para o final e particiona a faixa.
        int pivotIndex = medianOfThree(array, low, high);
        swap(array, pivotIndex, high);
        int pivot = array[high];

        // Ao final, i indica a posicao definitiva do pivo.
        int i = low;
        for (int j = low; j < high; j++) {
            comparisons++;
            if (array[j] <= pivot) {
                if (i != j) {
                    swap(array, i, j);
                }
                i++;
            }
        }

        if (i != high) {
            swap(array, i, high);
        }

        // Ordena recursivamente os valores antes e depois do pivo.
        quickSort(array, low, i - 1);
        quickSort(array, i + 1, high);
    }

    private int medianOfThree(int[] array, int low, int high) {
        // Compara inicio, meio e fim; as trocas deixam a mediana no indice do meio.
        int middle = low + (high - low) / 2;

        if (array[low] > array[middle]) {
            swap(array, low, middle);
        }
        if (array[middle] > array[high]) {
            swap(array, middle, high);
        }
        if (array[low] > array[middle]) {
            swap(array, low, middle);
        }
        return middle;
    }

    private void swap(int[] array, int first, int second) {
        // Troca dois valores e registra uma operacao de troca.
        int temporary = array[first];
        array[first] = array[second];
        array[second] = temporary;
        swaps++;
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
        // Confere se nenhum elemento e maior que o seguinte.
        for (int i = 1; i < array.length; i++) {
            if (array[i - 1] > array[i]) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String getName() {
        return "Quick Sort";
    }
}
