import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Usuario {
    String nome;
    Set<String> interesses;
    List<Usuario> amigos;

    public Usuario(String nome) {
        this.nome = nome;
        this.amigos = new ArrayList<>();
        this.interesses = new HashSet<>(); // Adiciona conjunto de interesses
    }

    // Método para adicionar interesses
    public void adicionarInteresse(String interesse) {
        this.interesses.add(interesse);
    }

    public Set<String> getInteresses() {
        return interesses;
    }
}
