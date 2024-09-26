import java.util.HashSet;
import java.util.Set;

public class Usuario {

    private String nome;
    private String email;
    private String senha;
    private String curso;
    private Set<String> interesses;
    private Set<Usuario> amigos;

    public Usuario() {
        this.interesses = new HashSet<>();
        this.amigos = new HashSet<>();
    }

    public Usuario(String nome, String email, String senha, String curso) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.curso = curso;
        this.interesses = new HashSet<>();
        this.amigos = new HashSet<>();
    }

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

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
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

    public void adicionarInteresse(String interesse) {
        this.interesses.add(interesse);
    }

    public void adicionarAmigo(Usuario amigo) {
        this.amigos.add(amigo);
    }
}
