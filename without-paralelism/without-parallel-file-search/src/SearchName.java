import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class SearchName {
    public static Boolean find(File arquivo, String nomeBuscado) {
        boolean encontrado = false;

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            int numeroLinha = 0;
            while ((linha = br.readLine()) != null) {
                numeroLinha++;
                if (linha.contains(nomeBuscado)) {
                    System.out.println("Nome encontrado no arquivo: " + arquivo.getName() + ", na linha: " + numeroLinha);
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
