import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

class FileReaderTask implements Runnable {
    private File file;
    private String nameToSearch;
    private AtomicBoolean foundName;
    private int startLine = 1;
    private int totalLines = 10000;
    private ExecutorService executor;

    public FileReaderTask(File filePath, String nameToSearch, AtomicBoolean foundName, ExecutorService executor) {
        this.file = filePath;
        this.nameToSearch = nameToSearch;
        this.foundName = foundName;
        this.executor = executor;
    }

    @Override
    public void run() {
        executor.submit(() -> readRange(0, 5000));
        executor.submit(() -> readRange(6000, 10000));
    }

    private void readRange(int startLine, int endLine) {
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
             FileChannel fileChannel = randomAccessFile.getChannel()) {
            // Calcula os offsets de início e fim de leitura com base nas linhas
            long startOffset = findOffset(randomAccessFile, startLine);
            long endOffset = findOffset(randomAccessFile, endLine);

            // Posiciona o canal no offset de início
            fileChannel.position(startOffset);

            ByteBuffer buffer = ByteBuffer.allocate(1024);
            StringBuilder contentBuilder = new StringBuilder();
            int currentLine = startLine; // Inicia com a linha de início

            while (fileChannel.position() < endOffset && !foundName.get()) {
                buffer.clear();

                // Lê o buffer
                int bytesRead = fileChannel.read(buffer);

                if (bytesRead == -1) {
                    break; // Final do arquivo
                }

                // Converte o buffer para string
                buffer.flip();
                contentBuilder.append(new String(buffer.array(), 0, buffer.limit()));

                // Processa o conteúdo lido
                String content = contentBuilder.toString();

                // Verifica se o nome está contido no conteúdo
                int indexOfName = content.indexOf(nameToSearch);

                if (indexOfName != -1) {
                    // Se o nome foi encontrado, calcule a linha em que foi encontrado
                    int lineBreaks = 0;
                    // Conta o número de quebras de linha antes do índice onde o nome foi encontrado
                    for (int i = 0; i < indexOfName; i++) {
                        if (content.charAt(i) == '\n') {
                            lineBreaks++;
                        }
                    }

                    // Calcula a linha em que o nome foi encontrado
                    int foundLine = currentLine + lineBreaks + 1;

                    // Sinaliza que o nome foi encontrado
                    foundName.set(true);
                    executor.shutdownNow(); // Interrompe todas as outras threads

                    // Retorna as informações necessárias
                    System.out.printf("Nome encontrado: %s\nArquivo: %s\nLinha: %d\n",
                            nameToSearch, file.getPath(), foundLine);
                    return;
                }

                // Atualiza o contador de linha
                for (int i = 0; i < content.length(); i++) {
                    if (content.charAt(i) == '\n') {
                        currentLine++;
                    }
                }

                // Limpa o builder para a próxima leitura
                contentBuilder.setLength(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private long findOffset(RandomAccessFile randomAccessFile, int lineNumber) throws IOException {
        long offset = 0;
        randomAccessFile.seek(0);
        String line;
        for (int i = 0; i < lineNumber; i++) {
            line = randomAccessFile.readLine();
            if (line == null) {
                break; // Chegou ao final do arquivo
            }
            offset = randomAccessFile.getFilePointer();
        }
        return offset;
    }
}
