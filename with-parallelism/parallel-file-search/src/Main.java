import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //Andrew Key
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Digite o nome:");
//        String name = scanner.nextLine();
//        System.out.println("Buscando nome: " + name);
//        scanner.close();

        List<File> files = new ArrayList<>();

        String directory = "/home/joaorodrigues/git-hub/public-repositories/file-search-benchmark/files-to-read/dataset_g";
        String name = "Jeffery Reid"; //"Andrew Key";
        long startTime = System.currentTimeMillis();

        for (int i = 1; i < 7; i++) {
            File file = new File(directory, "a" + i + ".txt");

            //Thread thread = new Thread(new SearchName(file, name, true, startTime));
            Thread thread2 = new Thread(new SearchName(file, name, null, startTime));
           // Thread thread1 = new Thread(new SearchName(file, name, false, startTime));

//            thread.start();
//            thread1.start();
            thread2.start();
        }


        System.out.println(Thread.activeCount());

//        List<String> values = new ArrayList<>();
//
//        try {
//            // Crie um scanner para ler o arquivo
//            Scanner scanner2 = new Scanner(file);
//
//            // Leia cada linha do arquivo
//            while (scanner2.hasNextLine()) {
//                // Adicione cada linha à lista de valores
//                values.add(scanner.nextLine());
//            }
//
//            // Feche o scanner
//            scanner.close();
//
//        } catch (FileNotFoundException e) {
//            System.err.println("Arquivo não encontrado: " + e.getMessage());
//        }



    }
}

