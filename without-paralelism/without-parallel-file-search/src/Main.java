import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //Andrew Key
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o nome:");
        String name = scanner.nextLine();
        System.out.println("Buscando nome: " + name);
        scanner.close();

        String diretorio = "/home/joaorodrigues/git-hub/public-repositories/file-search-benchmark/files-to-read/dataset_g";

        long startTime = System.currentTimeMillis();

        for (int i = 1; i <= 7; i++) {
            File file = new File(diretorio, "a" + i + ".txt");
            var isPresent = SearchName.find(file, name);
            if(isPresent){
                break;
            }
        }

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        System.out.println("Tempo de execução: " + executionTime + " ms");

    }
}