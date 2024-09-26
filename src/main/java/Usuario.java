import java.util.HashSet;
import java.util.Set;

public class Usuario {
    private String nome;
    private String email;
    private String senha;
    private String curso;
    private Set<String> interesses;
    private Set<String> amigos; // Armazena e-mails dos amigos
    private Set<String> solicitacoesDeAmizade; // Armazena e-mails de usuários que enviaram solicitações

    public Usuario() {
        this.interesses = new HashSet<>();
        this.amigos = new HashSet<>();
        this.solicitacoesDeAmizade = new HashSet<>();
    }

    public Usuario(String nome, String email, String senha, String curso) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.curso = curso;
        this.interesses = new HashSet<>();
        this.amigos = new HashSet<>();
        this.solicitacoesDeAmizade = new HashSet<>();
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

    public Set<String> getAmigos() {
        return amigos; // Retorna e-mails dos amigos
    }

    public void adicionarInteresse(String interesse) {
        interesses.add(interesse);
    }

    public Set<String> getInteresses() {
        return interesses;
    }

    public Set<String> getSolicitacoesAmizadePendentes() {
        return solicitacoesDeAmizade; // Retorna e-mails de solicitações pendentes
    }

    public void adicionarSolicitacaoAmizade(Usuario usuario) {
        this.solicitacoesDeAmizade.add(usuario.getEmail()); // Adiciona o e-mail do usuário que enviou a solicitação
    }

    public void removerSolicitacaoAmizade(Usuario usuario) {
        this.solicitacoesDeAmizade.remove(usuario.getEmail()); // Remove o e-mail da solicitação
    }

    public void aceitarSolicitacaoAmizade(Usuario usuario) {
        this.amigos.add(usuario.getEmail()); // Adiciona o e-mail do amigo
        usuario.getAmigos().add(this.email);  // Adiciona o e-mail do usuário atual à lista do amigo
        this.solicitacoesDeAmizade.remove(usuario.getEmail()); // Remove a solicitação pendente
    }
}
