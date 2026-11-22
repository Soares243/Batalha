# Batalha dos Algoritmos de Ordenacao

Projeto Java para comparar o Quick Sort (Ester), o Insertion Sort (Matheus) e novos
algoritmos implementados pelo grupo.

## Estrutura

```text
src/
  br/com/batalha/
    Main.java
    algoritmos/
      SortingAlgorithm.java
      InsertionSort.java
      QuickSort.java
test/
  br/com/batalha/algoritmos/
    InsertionSortTest.java
    QuickSortTest.java
resultados/
```

## Requisitos

- JDK 17 ou superior
- Terminal PowerShell no Windows

## Compilar

Na raiz do projeto:

```powershell
New-Item -ItemType Directory -Force build, build\test | Out-Null
javac -encoding UTF-8 -d build (Get-ChildItem -Recurse -Filter *.java -Path src).FullName
javac -encoding UTF-8 -cp build -d build\test (Get-ChildItem -Recurse -Filter *.java -Path test).FullName
```

## Executar testes

```powershell
java -ea -cp "build;build\test" br.com.batalha.algoritmos.InsertionSortTest
java -ea -cp "build;build\test" br.com.batalha.algoritmos.QuickSortTest
```

## Executar o experimento

```powershell
java -cp build br.com.batalha.Main
```

Os resultados sao gravados em `resultados/resultados.csv`. A primeira execucao de
cada algoritmo/tamanho/tipo de dados e usada como aquecimento e descartada. Sao
calculadas cinco execucoes medidas.
O tempo e medido com `System.nanoTime()`. Cada resultado, incluindo o aquecimento,
e comparado com uma copia ordenada por `Arrays.sort()`. O conjunto comeca em n=10
para validar os algoritmos em vetores pequenos.
Cada algoritmo pode ser usado individualmente instanciando sua classe e chamando
`sort(int[])`. As metricas ficam disponiveis por `getComparisons()`, `getMoves()`
(trocas ou movimentos, conforme o algoritmo) e `getExecutionTime()` (nanosegundos).

## Dados avaliados

- Aleatorios: numeros inteiros pseudoaleatorios gerados com semente fixa.
- Ordenados: valores crescentes.
- Inversamente ordenados: valores decrescentes.
- Tamanhos: 10, 100, 1.000, 5.000, 10.000, 50.000 e 100.000.
- O Insertion Sort para em 50.000, conforme a limitacao recomendada.

## Adicionar um novo algoritmo

1. Crie uma classe em `src/br/com/batalha/algoritmos`.
2. Implemente `SortingAlgorithm`.
3. Conte comparacoes e trocas/movimentos no metodo `sort`.
4. Atualize o metodo `sort` antes de finalizar para que o tempo seja medido apenas
   pela ordenacao.
5. Instancie a nova classe na lista `algorithms` do `Main`.
6. Recompile e execute o benchmark.
