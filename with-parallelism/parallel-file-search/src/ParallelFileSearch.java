import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class ParallelFileSearch {
    // Variável compartilhada para sinalizar se a palavra foi encontrada
    private static final AtomicBoolean palavraEncontrada = new AtomicBoolean(false);
    // Variável para armazenar a linha e o arquivo onde a palavra foi encontrada
    private static volatile String resultado = "";

    public static void main(String[] args) {
        // Palavra a ser buscada
        String palavraProcurada = "Phillip Fisher";

        // Diretório onde os arquivos estão localizados
        String diretorio = "/home/joaorodrigues/git-hub/public-repositories/file-search-benchmark/files-to-read/dataset_g/";

        // Lista de arquivos para pesquisar (de a1.txt até a7.txt)
        List<String> arquivos = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            arquivos.add(diretorio + "a" + i + ".txt");
        }

        // Ajuste o número de threads de acordo com a capacidade do seu sistema
        int numThreads = Math.min(Runtime.getRuntime().availableProcessors(), arquivos.size());
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        // Lista de tarefas (Callable)
        List<Future<String>> futures = new ArrayList<>();
        for (String arquivo : arquivos) {
            // Cria uma tarefa para cada arquivo
            Callable<String> task = new SearchTask(arquivo, palavraProcurada);
            // Envia a tarefa ao executor
            Future<String> future = executor.submit(task);
            futures.add(future);
        }

        System.out.println(Thread.activeCount());

        // Mede o tempo de busca
        long startTime = System.currentTimeMillis();

        // Verifica os resultados das tarefas
        try {
            for (Future<String> future : futures) {
                // Espera o resultado de cada tarefa
                String result = future.get();
                if (!result.equals("")) {
                    resultado = result;
                    // Atualiza o sinalizador para interromper todas as threads restantes
                    palavraEncontrada.set(true);
                    executor.shutdownNow(); // Interrompe todas as threads restantes
                    break; // Sai do loop, pois a palavra foi encontrada
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            // Garante o encerramento correto do executor
            executor.shutdown();
        }

        // Calcula o tempo total gasto na busca
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        // Exibe o resultado
        if (palavraEncontrada.get()) {
            System.out.println("A palavra '" + palavraProcurada + "' foi encontrada em " + resultado);
        } else {
            System.out.println("A palavra '" + palavraProcurada + "' não foi encontrada em nenhum dos arquivos.");
        }

        // Exibe o tempo total gasto na busca
        System.out.println("Tempo total gasto na busca: " + duration + " ms");
    }

    // Classe Callable para a tarefa de busca em um arquivo
    private static class SearchTask implements Callable<String> {
        private final String arquivo;
        private final String palavraProcurada;

        public SearchTask(String arquivo, String palavraProcurada) {
            this.arquivo = arquivo;
            this.palavraProcurada = palavraProcurada;
        }

        @Override
        public String call() throws IOException {
            // Lê o arquivo e procura a palavra específica
            try (BufferedReader reader = new BufferedReader(new FileReader(arquivo, StandardCharsets.UTF_8))) {
                String linha;
                int numeroLinha = 0;

                while ((linha = reader.readLine()) != null) {
                    numeroLinha++;
                    // Verifica se a palavra foi encontrada
                    if (palavraEncontrada.get()) {
                        return ""; // Interrompe a busca se a palavra já foi encontrada
                    }
                    if (linha.equals(palavraProcurada)) {
                        // Retorna o nome do arquivo e o número da linha onde a palavra foi encontrada
                        return "arquivo: " + arquivo + ", linha: " + numeroLinha;
                    }
                }
            }
            return ""; // Se a palavra não for encontrada, retorna uma string vazia
        }
    }
}