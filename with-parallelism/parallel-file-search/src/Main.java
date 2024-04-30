import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {
    public static void main(String[] args) {
        //Andrew Key
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o nome:");
        String name = scanner.nextLine();
        System.out.println("Buscando nome: " + name);
        scanner.close();

        List<File> files = new ArrayList<>();

        String directory = "/home/joaorodrigues/git-hub/public-repositories/file-search-benchmark/files-to-read/dataset_g";

        for (int i = 1; i <= 7; i++) {
            File file = new File(directory, "a" + i + ".txt");
            files.add(file);
        }

        AtomicBoolean foundName = new AtomicBoolean(false);
        ExecutorService executor = Executors.newFixedThreadPool(14); // 2 threads por arquivo


        executor.submit(new FileReaderTask(files.get(4), name, foundName, executor));
        executor.submit(new FileReaderTask(files.get(5), name, foundName, executor));


        // Aguarde o término de todas as threads
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}