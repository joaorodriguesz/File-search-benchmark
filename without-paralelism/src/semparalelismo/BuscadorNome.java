package semparalelismo;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class BuscadorNome {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String nomeBuscado = "Andrew Key"; // Nome a ser buscado nos arquivos
		
        String diretorio = "C://Users/lab201a/Downloads/dataset_p"; // Caminho do diretório onde os arquivos estão
        
        for (int i = 1; i <= 5; i++) {
            File arquivo = new File(diretorio, "arq_" + i + ".txt");
            buscarNomeEmArquivo(arquivo, nomeBuscado);
        }
	}
	
	private static void buscarNomeEmArquivo(File arquivo, String nomeBuscado) {
        boolean encontrado = false;
        
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            int numeroLinha = 0;
            while ((linha = br.readLine()) != null) {
                numeroLinha++;
                if (linha.contains(nomeBuscado)) {
                    System.out.println("Nome encontrado no arquivo: " + arquivo.getName() + ", na linha: " + numeroLinha);
                    encontrado = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        if (!encontrado) {
            System.out.println("O nome não foi encontrado no arquivo: " + arquivo.getName());
        }
    }

}
