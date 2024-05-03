import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o nome:");
        String name = scanner.nextLine();
        System.out.println("Buscando nome: " + name);
        scanner.close();

        List<File> files = new ArrayList<>();
        String directory = System.getProperty("user.dir") + "/files-to-read/dataset_g";

        long startTime = System.currentTimeMillis();
        var consume = new Consumer(name, startTime);
        for (int i = 1; i <= 7; i++) {
            consume.add(directory + "/a" + i + ".txt");
        }
    }
}

