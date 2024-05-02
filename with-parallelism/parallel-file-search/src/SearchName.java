import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;

public class SearchName implements Runnable {

    private File file;
    private String name;
    private Boolean start;
    private long startTime;

    public SearchName(File file, String name, Boolean start, long startTime) {
        this.file = file;
        this.name = name;
        this.start = start;
        this.startTime = startTime;
    }

    @Override
    public void run() {
        boolean encontrado = false;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linha;
            int numeroLinha = 0;
            while ((linha = br.readLine()) != null) {
                numeroLinha++;
                if (linha.contains(name)) {
                    long endTime = System.currentTimeMillis();
                    long executionTime = endTime - startTime;
                    System.out.println("Tempo de execução: " + executionTime + " ms");
                    System.exit(1);
                    System.out.println("Nome encontrado no arquivo: " + file.getName() + ", na linha: " + numeroLinha);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

//        if (start == null) {
//            searchFirst3000(); // Ler as primeiras 3000 linhas
//        } else if (start) {
//            searchMiddle4000(); // Ler as próximas 4000 linhas (3001-7000)
//        } else {
//            searchLast3000(); // Ler as últimas 3000 linhas (7001-10000)
//        }
    }

    private void searchFirst3000() {
        try (BufferedReader reader = Files.newBufferedReader(file.toPath())) {
            String line;
            int lineCount = 0;

            while ((line = reader.readLine()) != null && lineCount < 3000) {
                // Verifica se a linha contém a string especificada
                if (line.contains(name)) {
                    long endTime = System.currentTimeMillis();
                    long executionTime = endTime - startTime;
                    System.out.println("Tempo de execução: " + executionTime + " ms");
                    System.exit(1);
                    return; // Encerra a busca
                }
                lineCount++;
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }

    private void searchMiddle4000() {
        try (BufferedReader reader = Files.newBufferedReader(file.toPath())) {
            String line;
            int lineCount = 0;

            // Pula as primeiras 3000 linhas
            while ((line = reader.readLine()) != null && lineCount < 3000) {
                lineCount++;
            }

            // Lê as próximas 4000 linhas (de 3001 a 7000)
            while ((line = reader.readLine()) != null && lineCount < 7000) {
                // Verifica se a linha contém a string especificada
                if (line.contains(name)) {
                    long endTime = System.currentTimeMillis();
                    long executionTime = endTime - startTime;
                    System.out.println("Tempo de execução: " + executionTime + " ms");
                    System.exit(1);
                    return; // Encerra a busca
                }
                lineCount++;
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }

    private void searchLast3000() {
        try (BufferedReader reader = Files.newBufferedReader(file.toPath())) {
            String line;
            int lineCount = 0;

            // Pula as primeiras 7000 linhas
            while ((line = reader.readLine()) != null && lineCount < 7000) {
                lineCount++;
            }

            // Lê as últimas 3000 linhas (de 7001 a 10000)
            while ((line = reader.readLine()) != null && lineCount < 10000) {
                // Verifica se a linha contém a string especificada
                if (line.contains(name)) {
                    long endTime = System.currentTimeMillis();
                    long executionTime = endTime - startTime;
                    System.out.println("Tempo de execução: " + executionTime + " ms");
                    System.exit(1);
                    return; // Encerra a busca
                }
                lineCount++;
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }

}
