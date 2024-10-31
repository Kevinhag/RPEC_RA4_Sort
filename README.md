# Projeto de Comparação de Algoritmos de Ordenação

Este projeto visa comparar o desempenho de vários algoritmos de ordenação, analisando-os quanto ao tempo de execução, número de iterações e número de trocas para diferentes tamanhos de arrays. A implementação dos algoritmos é realizada em Java, enquanto scripts em Python são utilizados para automatizar a execução dos testes e para realizar a análise dos dados coletados.

## Descrição dos Arquivos

### 1. Sort.java
Este arquivo contém a implementação de quatro algoritmos de ordenação:
- **Quick Sort**: Um dos algoritmos mais eficientes para conjuntos de dados grandes, com complexidade média de `O(n log n)`.
- **Bogo Sort**: Um algoritmo de força bruta extremamente ineficiente (`O((n+1)!)`) utilizado aqui para demonstrar o contraste de performance.
- **Merge Sort**: Algoritmo de ordenação por divisão e conquista, com complexidade garantida de `O(n log n)`.
- **Gnome Sort**: Um algoritmo baseado em trocas adjacentes, útil para mostrar diferenças em eficiência quando comparado a algoritmos mais sofisticados.

Cada algoritmo é implementado em métodos separados, permitindo fácil configuração e execução para diferentes tamanhos de arrays e seeds.

### 2. sort.py
Este script Python automatiza a execução do arquivo `Sort.java`. Ele:
- Compila `Sort.java`.
- Executa os algoritmos de ordenação em cinco tamanhos de arrays (`1000`, `10000`, `100000`, `500000` e `1000000` elementos).
- Repete cada configuração dez vezes, variando a seed para cada execução, garantindo assim uma distribuição estatisticamente significativa dos resultados.
- Armazena os dados de tempo de execução, número de iterações e trocas em um arquivo CSV (`dados.csv`), usado posteriormente para análise.

### 3. graficos.py
O script `graficos.py` realiza a análise e visualização dos dados coletados:
- Lê `dados.csv` e processa as informações, calculando médias de tempo de execução, iterações e trocas para cada tipo de ordenação e cada tamanho de array.
- Gera gráficos usando `matplotlib` e `seaborn` para uma análise visual dos resultados.
- Cria três gráficos principais:
  - **Média do Tempo (ms)**: Representa o tempo médio de execução dos algoritmos para cada tamanho de array.
  - **Média de Iterações**: Mostra o número médio de iterações executadas por cada algoritmo para cada tamanho de array.
  - **Média de Trocas**: Exibe o número médio de trocas realizadas por cada algoritmo em diferentes tamanhos de arrays.

### 4. dados.csv
Este arquivo armazena os resultados de cada execução. As colunas incluem:
- Tipo de algoritmo de ordenação (`tipo de sort`)
- Tamanho do array (`tamanho do array`)
- Tempo de execução (`tempo do sort (ms)`)
- Número de iterações (`iterações`)
- Número de trocas (`trocas`)

Os dados são utilizados para calcular médias e facilitar a geração dos gráficos comparativos.

