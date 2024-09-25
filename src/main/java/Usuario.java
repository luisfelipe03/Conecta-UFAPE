import java.util.HashSet;
import java.util.Set;

public class Usuario {

    private String nome;
    private String email;
    private String senha;
    private Set<String> interesses;
    private Set<Usuario> amigos;

    // Construtor padrão necessário para Jackson
    public Usuario() {
        this.interesses = new HashSet<>();
        this.amigos = new HashSet<>();
    }

    // Construtor com argumentos
    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.interesses = new HashSet<>();
        this.amigos = new HashSet<>();
    }

    // Getters e Setters para Jackson e outros usos
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Set<String> getInteresses() {
        return interesses;
    }

    public void setInteresses(Set<String> interesses) {
        this.interesses = interesses;
    }

    public Set<Usuario> getAmigos() {
        return amigos;
    }

    public void setAmigos(Set<Usuario> amigos) {
        this.amigos = amigos;
    }

    // Adicionar métodos auxiliares, como adicionar interesse ou amigos
    public void adicionarInteresse(String interesse) {
        this.interesses.add(interesse);
    }

    public void adicionarAmigo(Usuario amigo) {
        this.amigos.add(amigo);
    }
}
