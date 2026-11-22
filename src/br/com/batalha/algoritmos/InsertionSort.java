package br.com.batalha.algoritmos;

/**
 * Ordena um vetor por insercao: cada valor e inserido na posicao correta
 * dentro da parte do vetor que ja foi ordenada.
 * Responsavel: Matheus Lucas Fiene da Silva.
 */
public class InsertionSort implements SortingAlgorithm {
    // Metricas acumuladas durante a chamada mais recente de sort.
    private long comparisons;
    private long movements;
    private long executionTime;

    @Override
    public void sort(int[] array) {
        // Zera as metricas para que cada chamada seja medida separadamente.
        comparisons = 0;
        movements = 0;

        // O cronometro envolve somente o trabalho de ordenacao.
        long start = System.nanoTime();
        for (int i = 1; i < array.length; i++) {
            // key e o valor que sera inserido na parte ordenada a esquerda.
            int key = array[i];
            int j = i - 1;

            // Desloca para a direita os valores maiores que key.
            while (j >= 0) {
                comparisons++;
                if (array[j] <= key) {
                    break;
                }

                array[j + 1] = array[j];
                movements++;
                j--;
            }

            // Insere key no espaco aberto, contando a escrita como movimento.
            if (j + 1 != i) {
                array[j + 1] = key;
                movements++;
            }
        }
        // Guarda a duracao em nanossegundos para consulta posterior.
        executionTime = System.nanoTime() - start;
    }

    @Override
    public long getComparisons() {
        return comparisons;
    }

    @Override
    public long getMoves() {
        return movements;
    }

    @Override
    public long getExecutionTime() {
        return executionTime;
    }

    @Override
    public boolean isSorted(int[] array) {
        // Basta encontrar um par fora de ordem para rejeitar o vetor.
        for (int i = 1; i < array.length; i++) {
            if (array[i - 1] > array[i]) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String getName() {
        return "Insertion Sort";
    }
}
