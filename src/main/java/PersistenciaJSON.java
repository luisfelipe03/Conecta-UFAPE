import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PersistenciaJSON {

    private static final String CAMINHO_JSON = "src/main/resources/data.json";
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static void salvarDados(Map<String, Usuario> usuarios) {
        try {
            objectMapper.writeValue(new File(CAMINHO_JSON), usuarios);
        } catch (IOException e) {
            System.out.println("Erro ao salvar dados no JSON: " + e.getMessage());
        }
    }

    public static Map<String, Usuario> carregarDados() {
        try {
            File arquivo = new File(CAMINHO_JSON);

            if (!arquivo.exists()) {
                System.out.println("Arquivo data.json não encontrado, criando um novo.");
                arquivo.createNewFile();
                salvarDados(new HashMap<>());
                return new HashMap<>();
            }

            if (arquivo.length() == 0) {
                System.out.println("Arquivo data.json vazio, inicializando.");
                return new HashMap<>();
            }

            return objectMapper.readValue(arquivo, new TypeReference<Map<String, Usuario>>() {});

        } catch (IOException e) {
            System.out.println("Erro ao carregar dados do JSON: " + e.getMessage());
            return new HashMap<>();
        }
    }
}
