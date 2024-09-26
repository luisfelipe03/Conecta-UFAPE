import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Usuario {
    private String nome;
    private String email;
    private String senha;
    private String curso;
    private Set<String> interesses;
    private Set<Usuario> amigos;
    private List<Usuario> solicitacoesDeAmizade;

    public Usuario(String nome, String email, String senha, String curso) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.interesses = new HashSet<>();
        this.amigos = new HashSet<>();
        this.solicitacoesDeAmizade = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public boolean validarSenha(String senha) {
        return this.senha.equals(senha);
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public Set<Usuario> getAmigos() {
        return amigos;
    }

    public void adicionarInteresse(String interesse) {
        interesses.add(interesse);
    }

    public Set<String> getInteresses() {
        return interesses;
    }

    public List<Usuario> getSolicitacoesAmizadePendentes() {
        return solicitacoesDeAmizade;
    }

    public void adicionarSolicitacaoAmizade(Usuario usuario) {
        this.solicitacoesDeAmizade.add(usuario);
    }

    public void removerSolicitacaoAmizade(Usuario usuario) {
        this.solicitacoesDeAmizade.remove(usuario);
    }

    public void aceitarSolicitacaoAmizade(Usuario usuario) {
        this.amigos.add(usuario);
        usuario.getAmigos().add(this);
        this.solicitacoesDeAmizade.remove(usuario);
    }
}
