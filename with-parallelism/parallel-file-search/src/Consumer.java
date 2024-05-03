import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Consumer {

    private final BlockingQueue<String> queue = new ArrayBlockingQueue<>(10);

    private String name;

    private long startTime;

    public Consumer(String name, long startTime) {
        this.startTime = startTime;
        this.name = name;
        for (var t = 1; t <= 7; t++) {
            var thread = new Thread(this::searchName);
            thread.start();
        }
    }

    public void add(String line) {
        try {
            queue.put(line);
        } catch (InterruptedException ignored) {
        }
    }

    public void searchName() {
        int numeroLinha = 0;
        while (true) {
            String path = null;
            try {
                path = queue.take();
                BufferedReader br = new BufferedReader(new FileReader(new File(path)));
                String linha;
                while ((linha = br.readLine()) != null) {
                    numeroLinha++;
                    if (linha.contains(name)) {
                        long endTime = System.currentTimeMillis();
                        long executionTime = endTime - startTime;
                        System.out.println("Tempo de execução: " + executionTime + " ms");
                        System.out.println("Nome encontrado no arquivo: " + path + ", na linha: " + numeroLinha);
                        System.exit(1);
                    }
                }
                br.close();
            } catch (Exception e) {}
        }
    }
}
