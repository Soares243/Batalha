package br.com.batalha.algoritmos;

/**
 * Define as operacoes e metricas que todos os algoritmos de ordenacao oferecem.
 * Para incluir um algoritmo, crie uma classe que implemente esta interface.
 */
public interface SortingAlgorithm {
    /** Ordena o vetor recebido em ordem crescente e atualiza as metricas. */
    void sort(int[] array);

    /** Retorna quantas comparacoes entre valores foram realizadas na ultima ordenacao. */
    long getComparisons();

    /** Retorna as trocas ou movimentacoes realizadas na ultima ordenacao. */
    long getMoves();

    /** Retorna o tempo da ultima ordenacao em nanossegundos. */
    long getExecutionTime();

    /** Verifica se os valores do vetor estao em ordem crescente. */
    boolean isSorted(int[] array);

    /** Retorna o nome legivel do algoritmo. */
    String getName();
}
