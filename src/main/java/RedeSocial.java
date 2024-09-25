import java.util.*;

public class RedeSocial {

    private Map<String, Usuario> usuarios;

    public RedeSocial() {
        // Carregar dados do arquivo JSON ao iniciar a rede social
        this.usuarios = Optional.ofNullable(PersistenciaJSON.carregarDados()).orElse(new HashMap<>());
    }

    // Adicionar usuário
    public void adicionarUsuario(Usuario usuario) {
        if (!usuarios.containsKey(usuario.getEmail())) {
            usuarios.put(usuario.getEmail(), usuario);
            salvarDados();  // Salvar ao adicionar usuário
        }
    }

    // Buscar usuário por email e senha
    public Usuario buscarUsuarioPorEmailESenha(String email, String senha) {
        Usuario usuario = usuarios.get(email);
        if (usuario != null && usuario.getSenha().equals(senha)) {
            return usuario;
        }
        return null;
    }

    // Buscar usuário por nome
    public Usuario buscarUsuario(String nome) {
        for (Usuario usuario : usuarios.values()) {
            if (usuario.getNome().equalsIgnoreCase(nome)) {
                return usuario;
            }
        }
        return null;
    }

    // Recomendar amigos em comum
    public List<String> recomendarAmigosEmComum(Usuario usuario) {
        Set<Usuario> amigosDoUsuario = usuario.getAmigos();
        Map<String, Integer> contagemAmigosEmComum = new HashMap<>();

        for (Usuario amigo : amigosDoUsuario) {
            for (Usuario amigoDoAmigo : amigo.getAmigos()) {
                if (!amigosDoUsuario.contains(amigoDoAmigo) && !amigoDoAmigo.equals(usuario)) {
                    contagemAmigosEmComum.put(amigoDoAmigo.getNome(), contagemAmigosEmComum.getOrDefault(amigoDoAmigo.getNome(), 0) + 1);
                }
            }
        }

        List<String> recomendacoes = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : contagemAmigosEmComum.entrySet()) {
            if (entry.getValue() > 1) {
                recomendacoes.add(entry.getKey());
            }
        }

        return recomendacoes;
    }

    public void adicionarInteresse(String interesse, Usuario usuario) {
        usuario.getInteresses().add(interesse);
    }

    // Encontrar usuários por interesse
    public List<String> encontrarUsuariosComInteresses(String interesse) {
        List<String> usuariosComInteresse = new ArrayList<>();
        for (Usuario usuario : usuarios.values()) {
            if (usuario.getInteresses().contains(interesse)) {
                usuariosComInteresse.add(usuario.getNome());
            }
        }
        return usuariosComInteresse;
    }

    // Salvar os dados da rede social no arquivo JSON
    public void salvarDados() {
        PersistenciaJSON.salvarDados(usuarios);
    }

    public List<Usuario> getUsuarios() {
        return new ArrayList<>(usuarios.values());
    }
}
