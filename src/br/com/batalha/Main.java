package br.com.batalha;

import br.com.batalha.algoritmos.InsertionSort;
import br.com.batalha.algoritmos.MergeSort;
import br.com.batalha.algoritmos.BubbleSort;
import br.com.batalha.algoritmos.QuickSort;
import br.com.batalha.algoritmos.SelectionSort;
import br.com.batalha.algoritmos.SortingAlgorithm;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Main {
    // Tamanhos testados pelo experimento, incluindo um vetor pequeno para validacao.
    private static final int[] SIZES = {10, 100, 1_000, 5_000, 10_000, 50_000, 100_000};
    // Quantidade de execucoes contabilizadas para calcular as medias.
    private static final int MEASURED_RUNS = 5;

    public static void main(String[] args) throws IOException {
        // Cada algoritmo implementa o mesmo contrato e pode ser incluido nesta lista.
        List<SortingAlgorithm> algorithms = new ArrayList<>();
        algorithms.add(new InsertionSort());
        algorithms.add(new QuickSort());
        algorithms.add(new MergeSort());
        algorithms.add(new BubbleSort());
        algorithms.add(new SelectionSort());

        // Para adicionar outro integrante, instancie e adicione aqui.
        // algorithms.add(new SelectionSort());

        // Permite escolher a pasta de saida por argumento; caso contrario, usa resultados.
        Path outputDirectory = args.length > 0
                ? Paths.get(args[0])
                : Paths.get("resultados");
        Files.createDirectories(outputDirectory);

        // O writer e fechado automaticamente ao sair do bloco try.
        Path csv = outputDirectory.resolve("resultados.csv");
        try (var writer = Files.newBufferedWriter(csv, StandardCharsets.UTF_8)) {
            // Cabecalho com as colunas de entrada, tempo e operacoes dos algoritmos.
            writer.write("algoritmo,tipo,n,tamanho_bytes,execucao_media_ms,"
                    + "comparacoes_media,trocas_ou_movimentos_media,"
                    + "tempo_minimo_ms,tempo_maximo_ms,ordenado\n");

            // Exibe uma tabela organizada no terminal sem alterar o arquivo CSV.
            System.out.println();
            System.out.println("==============================================================================================");
            System.out.println("                         RESULTADOS - BATALHA DOS ALGORITMOS");
            System.out.println("==============================================================================================");
            System.out.printf("%-16s | %-10s | %10s | %14s | %16s | %18s | %10s%n",
                    "ALGORITMO", "ENTRADA", "ELEMENTOS", "TEMPO MEDIO (ms)",
                    "COMPARACOES", "TROCAS/MOVIMENTOS", "ORDENADO");
            System.out.println("------------------+------------+------------+----------------+------------------+--------------------+----------");

            // Executa todos os algoritmos, tamanhos e formatos de entrada.
            for (SortingAlgorithm algorithm : algorithms) {
                for (int size : SIZES) {
                    // Evita execucoes excessivamente demoradas do Insertion Sort.
                    if (algorithm instanceof InsertionSort && size > 50_000) {
                        continue;
                    }
                    // Evita execucoes excessivamente demoradas do Bubble Sort.
                    if (algorithm instanceof BubbleSort && size > 10_000) {
                        continue;
                    }
                    // Selection Sort tambem tem custo quadratico; limita o benchmark a 10.000 elementos.
                    if (algorithm instanceof SelectionSort && size > 10_000) {
                        continue;
                    }

                    for (DataType dataType : DataType.values()) {
                        BenchmarkResult result = runBenchmark(
                                algorithm,
                                size,
                                dataType,
                                MEASURED_RUNS
                        );
                        writer.write(result.toCsvRow());
                        writer.flush();

                        // Mostra no terminal as mesmas metricas que continuam sendo salvas no CSV.
                        System.out.printf(java.util.Locale.ROOT,
                                "%-16s | %-10s | %10s | %14.3f | %16.0f | %18.0f | %10s%n",
                                result.algorithm(),
                                result.dataType(),
                                String.format(java.util.Locale.ROOT, "%,d", result.size()),
                                result.averageMilliseconds(),
                                result.averageComparisons(),
                                result.averageSwaps(),
                                result.sorted() ? "SIM" : "NAO");
                    }
                }
            }
        }

        System.out.println("==============================================================================================");
        System.out.println("Resultados gravados em: " + csv.toAbsolutePath());
    }

    private static BenchmarkResult runBenchmark(
            SortingAlgorithm algorithm,
            int size,
            DataType dataType,
            int measuredRuns
    ) {
        // Mantem a entrada original intacta e cria o resultado esperado como referencia.
        int[] baseArray = generateArray(size, dataType);
        int[] expected = baseArray.clone();
        Arrays.sort(expected);

        // Faz uma execucao de aquecimento antes de coletar as medidas.
        int[] warmupArray = baseArray.clone();
        algorithm.sort(warmupArray);
        validateResult(algorithm, warmupArray, expected, size, dataType);

        // Acumuladores das metricas e limites de tempo das execucoes medidas.
        long totalTime = 0;
        long totalComparisons = 0;
        long totalSwaps = 0;
        long minimumTime = Long.MAX_VALUE;
        long maximumTime = 0;
        boolean sorted = true;

        for (int run = 0; run < measuredRuns; run++) {
            // Cada repeticao recebe uma copia nova para partir da mesma entrada.
            int[] array = baseArray.clone();
            algorithm.sort(array);
            validateResult(algorithm, array, expected, size, dataType);
            sorted &= algorithm.isSorted(array);

            long time = algorithm.getExecutionTime();
            totalTime += time;
            totalComparisons += algorithm.getComparisons();
            totalSwaps += algorithm.getMoves();
            minimumTime = Math.min(minimumTime, time);
            maximumTime = Math.max(maximumTime, time);
        }

        // Converte nanosegundos para milissegundos e calcula as medias.
        return new BenchmarkResult(
                algorithm.getName(),
                dataType.label,
                size,
                (double) totalTime / measuredRuns / 1_000_000,
                totalComparisons / (double) measuredRuns,
                totalSwaps / (double) measuredRuns,
                minimumTime / 1_000_000.0,
                maximumTime / 1_000_000.0,
                sorted
        );
    }

    private static void validateResult(
            SortingAlgorithm algorithm,
            int[] result,
            int[] expected,
            int size,
            DataType dataType
    ) {
        // Falha explicitamente se o algoritmo divergir do resultado de Arrays.sort.
        if (!Arrays.equals(result, expected)) {
            throw new IllegalStateException(
                    algorithm.getName() + " produziu resultado incorreto para n="
                            + size + ", tipo=" + dataType.label
            );
        }
    }

    private static int[] generateArray(int size, DataType dataType) {
        int[] array = new int[size];

        if (dataType == DataType.RANDOM) {
            // Semente fixa torna os dados repetiveis entre execucoes do programa.
            Random random = new Random(2026L + size);
            for (int i = 0; i < size; i++) {
                array[i] = random.nextInt();
            }
            return array;
        }

        // Preenche primeiro em ordem crescente; para o caso reverso, inverte em seguida.
        for (int i = 0; i < size; i++) {
            array[i] = i;
        }

        if (dataType == DataType.REVERSE) {
            for (int i = 0; i < size / 2; i++) {
                int temporary = array[i];
                array[i] = array[size - 1 - i];
                array[size - 1 - i] = temporary;
            }
        }
        return array;
    }

    // Tipos de entrada usados para comparar o comportamento em cenarios diferentes.
    private enum DataType {
        RANDOM("aleatorio"),
        SORTED("ordenado"),
        REVERSE("invertido");

        private final String label;

        DataType(String label) {
            this.label = label;
        }
    }

    // Agrupa as metricas de uma combinacao de algoritmo, tamanho e tipo de entrada.
    private record BenchmarkResult(
            String algorithm,
            String dataType,
            int size,
            double averageMilliseconds,
            double averageComparisons,
            double averageSwaps,
            double minimumMilliseconds,
            double maximumMilliseconds,
            boolean sorted
    ) {
        private String toCsvRow() {
            // Serializa as metricas na mesma ordem declarada no cabecalho CSV.
            return String.join(",",
                    escape(algorithm),
                    escape(dataType),
                    Integer.toString(size),
                    Long.toString((long) size * Integer.BYTES),
                    format(averageMilliseconds),
                    format(averageComparisons),
                    format(averageSwaps),
                    format(minimumMilliseconds),
                    format(maximumMilliseconds),
                    Boolean.toString(sorted)
            ) + "\n";
        }

        private static String escape(String value) {
            // Envolve campos que contenham virgula para manter o CSV valido.
            return value.contains(",") ? "\"" + value + "\"" : value;
        }

        private static String format(double value) {
            // Usa ponto decimal independentemente da localidade do computador.
            return String.format(java.util.Locale.ROOT, "%.3f", value);
        }
    }
}
