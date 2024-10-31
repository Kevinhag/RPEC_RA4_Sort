import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.time.Instant;
import java.time.Duration;

class Sort {
    private static long iteracoes = 0;
    private static long trocas = 0;
    private static Random randomizador = new Random();
    private static String CSV = "dados.csv";
    private static String tempoSort;
    private static String tipo;
    private static int tamanhoLista;

    public static int tamanhoArgs(String[] args) { // nao pode usar .lenght kkkk
        int tamanhoArgs = 0;
        for (String arg : args) {
            tamanhoArgs++;
        }
        return tamanhoArgs;
    }

    public static void main(String[] args) {

        if (tamanhoArgs(args) >= 3) {
            randomizador.setSeed(Long.parseLong(args[2]));
        } else {
            randomizador.setSeed(123);
        }

        Instant inicio;
        Instant fim;

        if (tamanhoArgs(args) >= 2) {
            tamanhoLista = Integer.parseInt(args[1]);
        } else {
            tamanhoLista = 1000000; // nao coloque numeros unicos para lista muito grande
        }

        Instant criaLista = Instant.now();
        int[] lista = criarLista(tamanhoLista, 0); // 0 para lista com numeros aleatorios, 1 para lista sem repetições
        Instant fimCriaLista = Instant.now();

        printarLista(lista, tamanhoLista, 1);

        System.out.println(tamanhoArgs(args));

        if (tamanhoArgs(args) >= 1) {

            // nome do arquivo, tamanho da lista, seed
            // executar com os argumentos: java sort.java quick 10000 123

            String arg = args[0];
            switch (arg) {
                case "gnome":
                    tipo = "GnomeSort";

                    inicio = Instant.now();
                    gnomeSort(lista, tamanhoLista);
                    fim = Instant.now();

                    tempoSort = Duration.between(inicio, fim).toMillis() + "ms";
                    break;

                case "bogo":

                    if (tamanhoLista >= 13) {
                        System.out.println("Tamanho da lista muito grande para BogoSort. Alterando para 11...");
                        tamanhoLista = 11; // limitei para 11 pq dependendo do tamanho dos números tbm altera o tempo
                    }

                    tipo = "BogoSort";

                    inicio = Instant.now();
                    bogoSort(lista, tamanhoLista);
                    fim = Instant.now();

                    tempoSort = Duration.between(inicio, fim).toMillis() + "ms";
                    break;

                case "quick":
                    tipo = "QuickSort";

                    inicio = Instant.now();
                    quickSort(lista, 0, tamanhoLista - 1);
                    fim = Instant.now();

                    tempoSort = Duration.between(inicio, fim).toMillis() + "ms";
                    break;

                case "merge":
                    tipo = "MergeSort";

                    inicio = Instant.now();
                    mergeSort(lista);
                    fim = Instant.now();

                    tempoSort = Duration.between(inicio, fim).toMillis() + "ms";
                    break;

                default:
                    System.out.println("Argumento inválido.");
                    break;
            }
        }

/*         tipo = "QuickSort";
        inicio = Instant.now();
        quickSort(lista, 0, tamanhoLista - 1);
        fim = Instant.now();
        tempoSort = Duration.between(inicio, fim).toMillis() + "ms"; */

        printarLista(lista, tamanhoLista, 1);

        System.out.println("\nTamanho da Lista: " + tamanhoLista);
        System.out.println("Tempo para Criar Lista: " + Duration.between(criaLista, fimCriaLista).toMillis() + "ms");
        System.out.println("Tempo para " + tipo + ": " + tempoSort);
        System.out.println("Quantidade de Trocas: " + trocas);
        System.out.println("Quantidade de Iterações: " + iteracoes + "\n");

        if (tipo != null) {
            try {
                FileWriter arquivoCSV = new FileWriter(CSV, true);
                PrintWriter gravarArq = new PrintWriter(arquivoCSV);
                String primeiraLinha = null;

                if (arquivoCSV.equals(null)) {
                    System.out.println("Arquivo não encontrado.");
                } else {
                    FileReader leitor = new FileReader(CSV);
                    BufferedReader leitorCSV = new BufferedReader(leitor);
                    primeiraLinha = leitorCSV.readLine();
                    leitor.close();
                }

                if (primeiraLinha == null) { // primeira linha do csv
                    gravarArq.println("Tipo de Sort,Tamanho do Array,Tempo do Sort (ms),Trocas,Iterações");
                }

                gravarArq.println(tipo + "," + tamanhoLista + "," + tempoSort + "," + trocas + "," + iteracoes); // dados das linhas
                gravarArq.close();
                arquivoCSV.close();

                System.out.println("Arquivo CSV salvo com sucesso!");
            } catch (IOException e) {
                System.out.println("Erro ao criar arquivo CSV: " + e.getMessage());
            }
        }

    }

    private static boolean verificaNumero(int[] lista, int atual, int novoNum) {
        for (int i = 0; i < atual; i++) {
            if (lista[i] == novoNum) {
                return true;
            }
        }
        return false;
    }

    public static int[] criarLista(int tamanho, int unico) {
        switch (unico) {
            case 1: // fiz pra testar os tipos de Sort, mas demora bem mais pra criar a lista
                int[] lista = new int[tamanho];
                int indice = 0;
                while (indice < tamanho) {
                    int novoNum = randomizador.nextInt(tamanho);
                    if (!verificaNumero(lista, indice, novoNum)) {
                        lista[indice] = novoNum;
                        indice++;
                    }
                }
                return lista;

            default:
                int[] listaRandom = new int[tamanho];
                for (int i = 0; i < tamanho; i++) {
                    listaRandom[i] = randomizador.nextInt(tamanho);
                }
                return listaRandom;

        }
    }

    public static void printarLista(int[] lista, int tamanho, int resumir) {
        switch (resumir) { // fiz pra nao ficar printando a lista inteira o tempo todo
            case 1:
                if (tamanho > 30) {
                    System.out.print("[");
                    for (int i = 0; i < 10; i++) {
                        System.out.printf("%d ", lista[i]);
                    }
                    System.out.print("..... ");
                    for (int i = tamanho - 10; i < tamanho; i++) {
                        System.out.printf("%d ", lista[i]);
                    }
                    System.out.println("\b]");
                } else {
                    printarLista(lista, tamanho, 0);
                }

                break;

            default:
                System.out.print("[");
                for (int i = 0; i < tamanho; i++) {
                    System.out.printf("%d ", lista[i]);
                }
                System.out.println("\b]");
                break;
        }
    }

    // ========================== BOGOSORT ==========================

    public static void bogoSort(int[] lista, int tamanho) {
        while (!estaOrdem(lista, tamanho)) {
            iteracoes++;
            // randomizar lista
            int temporario = 0;
            int aleatorio = 0;
            for (int i = 0; i < tamanho; i++) {
                aleatorio = randomizador.nextInt(tamanho);
                temporario = lista[aleatorio];
                lista[aleatorio] = lista[i];
                lista[i] = temporario;
                iteracoes++;
                trocas++;
            }
        }
    }

    public static boolean estaOrdem(int[] lista, int tamanho) {
        for (int i = 0; i < tamanho - 1; i++) {
            iteracoes++;
            if (lista[i] > lista[i + 1]) {
                iteracoes++;
                return false;
            }
        }
        return true;
    }

    // ========================== GNOMESORT ==========================

    public static void gnomeSort(int[] lista, int tamanho) {
        int posicao = 0;

        while (posicao < tamanho) {
            iteracoes++;
            if (posicao == 0 || lista[posicao] >= lista[posicao - 1]) {
                posicao++;
            } else {
                int temp = lista[posicao];
                lista[posicao] = lista[posicao - 1];
                lista[posicao - 1] = temp;
                posicao--;
                trocas++;
                iteracoes++;
            }
        }
    }

    // ========================== QUICKSORT ==========================

    public static int[] quickSort(int[] lista, int esq, int dir) {
        iteracoes++;
        if (esq < dir) {
            int pivot = particao(lista, esq, dir);
            quickSort(lista, esq, pivot - 1); // define o a parte antes do pivot
            quickSort(lista, pivot + 1, dir); // define a parte depois do pivot
        }
        return lista;
    }

    private static int particao(int[] lista, int esq, int dir) {
        iteracoes++;
        int pivot = lista[dir];
        int i = esq - 1;

        for (int j = esq; j < dir; j++) {
            iteracoes++;
            if (lista[j] < pivot) { // se o elemento for menor que o pivot, troca os elementos
                iteracoes++;
                i++;
                int temp = lista[i];
                lista[i] = lista[j];// troca os elementos
                lista[j] = temp;
                trocas++;
            }
        }
        i++;
        int temp = lista[i];
        lista[i] = lista[dir]; // troca o pivot
        lista[dir] = temp;
        trocas++;

        return i;
    }

    // ========================== MERGESORT ==========================

    public static void mergeSort(int[] lista) {
        iteracoes++;
        int tamanho = 0;
        for (int temp : lista) { // n pode usar .lenght kkkkkk
            tamanho++;
        }

        if (tamanho <= 1) {
            return;
        }

        int meio = tamanho / 2;

        //cria subarrays esquedo e direito
        int[] arrayEsq = new int[meio];
        int[] arrayDir = new int[tamanho - meio];

        int i = 0;
        int j = 0;

        // preenche os subarrays
        for (; i < tamanho; i++) {
            if (i < meio) {
                iteracoes++;
                arrayEsq[i] = lista[i];
            } else {
                iteracoes++;
                arrayDir[j] = lista[i];
                j++;
            }
        }

        // repete até que os subarrays tenham tamanho 1
        mergeSort(arrayEsq);
        mergeSort(arrayDir);

        // junta os subarrays, ordenando
        merge(lista, arrayEsq, arrayDir);

    }

    public static void merge(int[] lista, int[] esq, int[] dir) {
        iteracoes++;
        int tamanho = 0;
        for (int temp : lista) { // n pode usar .lenght kkkkkk
            tamanho++;
        }

        int tamanhoEsq = tamanho / 2;
        int tamanhoDir = tamanho - tamanhoEsq;

        int i = 0; // indices declarados fora do for, pq sao usados para todos os loops
        int e = 0;
        int d = 0;

        for (; e < tamanhoEsq && d < tamanhoDir; i++) { // junta as "subarrays"
            iteracoes++;
            if (esq[e] < dir[d]) {
                lista[i] = esq[e];
                e++;
                trocas++;
            } else {
                lista[i] = dir[d];
                d++;
                trocas++;
            }
        }

        for (; e < tamanhoEsq; e++, i++) {// se tiver elementos restantes no esquerdo, copia pro array principal
            iteracoes++;
            lista[i] = esq[e];
            trocas++;
        }

        for (; d < tamanhoDir; d++, i++) { // se tiver elementos restantes no direito, copia pro array principal
            iteracoes++;
            lista[i] = dir[d];
            trocas++;
        }
    }
}
