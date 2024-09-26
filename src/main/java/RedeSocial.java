import java.util.*;

public class RedeSocial {

    private Map<String, Usuario> usuarios;

    public RedeSocial() {
        this.usuarios = Optional.ofNullable(PersistenciaJSON.carregarDados())
                .orElse(new HashMap<>());
    }

    public void adicionarUsuario(Usuario usuario) {
        if (!usuarios.containsKey(usuario.getEmail())) {
            usuarios.put(usuario.getEmail(), usuario);
            salvarDados();
        }
    }

    public Usuario buscarUsuarioPorEmailESenha(String email, String senha) {
        Usuario usuario = usuarios.get(email);
        if (usuario != null && usuario.validarSenha(senha)) {
            return usuario;
        }
        return null;
    }

    public Usuario buscarUsuarioPorNome(String nome) {
        for (Usuario usuario : usuarios.values()) {
            if (usuario.getNome().equalsIgnoreCase(nome)) {
                return usuario;
            }
        }
        return null;
    }

    public Usuario buscarUsuarioPorEmail(String email) {
        return usuarios.get(email);  // Busca o objeto completo a partir do email
    }

    public List<Usuario> getAmigos(Usuario usuario) {
        List<Usuario> listaAmigos = new ArrayList<>();
        for (String emailAmigo : usuario.getAmigos()) {
            Usuario amigo = buscarUsuarioPorEmail(emailAmigo);
            if (amigo != null) {
                listaAmigos.add(amigo);
            }
        }
        return listaAmigos;
    }

    public List<Usuario> recomendarAmigos(Usuario usuario) {
        Map<Usuario, Integer> pontuacoes = new HashMap<>();
        Set<Usuario> visitados = new HashSet<>();

        // Adiciona o próprio usuário ao conjunto de visitados para não recomendá-lo a si mesmo
        visitados.add(usuario);

        // Verifica se o usuário tem amigos ou interesses
        boolean temAmigos = !usuario.getAmigos().isEmpty();
        boolean temInteresses = !usuario.getInteresses().isEmpty();
        boolean temCurso = usuario.getCurso() != null && !usuario.getCurso().isEmpty();

        // Se o usuário não tem amigos, interesses ou curso, não recomendamos ninguém
        if (!temAmigos && !temInteresses && !temCurso) {
            return new ArrayList<>(); // Retorna lista vazia
        }

        // Percorre todos os usuários na rede social para encontrar possíveis recomendações
        for (Usuario possivelAmigo : usuarios.values()) {
            // Verifica se o possível amigo já foi visitado ou é o próprio usuário
            if (!visitados.contains(possivelAmigo) && !usuario.getAmigos().contains(possivelAmigo.getEmail())) {
                int pontuacao = 0;

                // Aumenta a pontuação com base em amigos em comum
                Set<String> amigosEmComum = new HashSet<>(usuario.getAmigos());
                amigosEmComum.retainAll(possivelAmigo.getAmigos());
                if (!amigosEmComum.isEmpty()) {
                    pontuacao += 1; // Peso 1 para amigos em comum
                }

                // Aumenta a pontuação com base nos interesses em comum
                Set<String> interessesEmComum = new HashSet<>(usuario.getInteresses());
                interessesEmComum.retainAll(possivelAmigo.getInteresses());
                if (!interessesEmComum.isEmpty()) {
                    pontuacao += 2; // Peso 2 para interesses em comum
                }

                // Aumenta a pontuação com base no curso em comum
                if (usuario.getCurso().equalsIgnoreCase(possivelAmigo.getCurso())) {
                    pontuacao += 3; // Peso 3 se os cursos forem os mesmos
                }

                // Adiciona o possível amigo à lista de recomendados se tiver alguma pontuação
                if (pontuacao > 0) {
                    pontuacoes.put(possivelAmigo, pontuacao);
                }

                // Marca o usuário como visitado
                visitados.add(possivelAmigo);
            }
        }

        // Ordena os possíveis amigos com base na pontuação (prioridade: curso > interesses > amigos em comum)
        List<Usuario> recomendacoes = new ArrayList<>(pontuacoes.keySet());
        recomendacoes.sort((u1, u2) -> pontuacoes.get(u2).compareTo(pontuacoes.get(u1))); // Ordena do maior para o menor

        return recomendacoes;
    }

    public void enviarSolicitacaoAmizade(Usuario remetente, Usuario destinatario) {
        destinatario.adicionarSolicitacaoAmizade(remetente);
        salvarDados();
    }

    public void aceitarSolicitacaoAmizade(Usuario usuarioAtual, Usuario solicitante) {
        usuarioAtual.aceitarSolicitacaoAmizade(solicitante);
        salvarDados();
    }

    public void recusarSolicitacaoAmizade(Usuario usuarioAtual, Usuario solicitante) {
        usuarioAtual.removerSolicitacaoAmizade(solicitante);
        salvarDados();
    }

    public void salvarDados() {
        PersistenciaJSON.salvarDados(usuarios);
    }

    public List<Usuario> getUsuarios() {
        return new ArrayList<>(usuarios.values());
    }
}
