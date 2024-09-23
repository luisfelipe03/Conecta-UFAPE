import java.util.HashSet;
import java.util.Set;

public class Usuario {
    String nome;
    Set<Usuario> amigos;

    public Usuario(String nome) {
        this.nome = nome;
        this.amigos = new HashSet<>();
    }
}
