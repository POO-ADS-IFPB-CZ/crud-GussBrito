// GenericDao.java
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GenericDao<T> {

    private final String filePath;

    public GenericDao(String fileName) {
        // Cria um caminho para o arquivo na pasta do usuário
        this.filePath = System.getProperty("user.home") + File.separator + fileName;
    }

    @SuppressWarnings("unchecked")
    public List<T> listar() {
        File file = new File(filePath);
        if (!file.exists()) {
            return new ArrayList<>(); // Retorna lista vazia se o arquivo não existe
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (List<T>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao ler do arquivo: " + e.getMessage());
            return new ArrayList<>(); // Retorna lista vazia em caso de erro
        }
    }

    public void salvar(List<T> lista) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(lista);
        } catch (IOException e) {
            System.err.println("Erro ao salvar no arquivo: " + e.getMessage());
        }
    }
}
