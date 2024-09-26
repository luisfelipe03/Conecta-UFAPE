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
        if (usuario != null && usuario.getSenha().equals(senha)) {
            return usuario;
        }
        return null;
    }

    public Usuario buscarUsuario(String nome) {
        for (Usuario usuario : usuarios.values()) {
            if (usuario.getNome().equalsIgnoreCase(nome)) {
                return usuario;
            }
        }
        return null;
    }

    public List<Usuario> recomendarAmigos(Usuario usuario) {
        Map<Usuario, Integer> pontuacoes = new HashMap<>();

        for (Usuario possivelAmigo : usuarios.values()) {
            if (!possivelAmigo.equals(usuario)) {
                int pontuacao = 0;

                // Contar amigos em comum
                Set<Usuario> amigosEmComum = new HashSet<>(usuario.getAmigos());
                amigosEmComum.retainAll(possivelAmigo.getAmigos());
                if (!amigosEmComum.isEmpty()) {
                    pontuacao += 1;  // Peso 1 para amigos em comum
                }

                // Contar interesses em comum
                Set<String> interessesEmComum = new HashSet<>(usuario.getInteresses());
                interessesEmComum.retainAll(possivelAmigo.getInteresses());
                if (!interessesEmComum.isEmpty()) {
                    pontuacao += 1;  // Peso 1 para interesses em comum
                }

                // Se ambos amigos e interesses em comum
                if (!amigosEmComum.isEmpty() && !interessesEmComum.isEmpty()) {
                    pontuacao = 2;  // Peso 2 se ambos forem verdadeiros
                }

                // Adiciona a pontuação ao mapa
                pontuacoes.put(possivelAmigo, pontuacao);
            }
        }

        // Ordena os possíveis amigos com base na pontuação
        List<Usuario> recomendacoes = new ArrayList<>(pontuacoes.keySet());
        recomendacoes.sort((u1, u2) -> pontuacoes.get(u2).compareTo(pontuacoes.get(u1))); // Ordena do maior para o menor

        return recomendacoes;
    }

//    public List<String> recomendarAmigosEmComum(Usuario usuario) {
//        Set<Usuario> amigosDoUsuario = usuario.getAmigos();
//        Map<String, Integer> contagemAmigosEmComum = new HashMap<>();
//
//        for (Usuario amigo : amigosDoUsuario) {
//            for (Usuario amigoDoAmigo : amigo.getAmigos()) {
//                if (!amigosDoUsuario.contains(amigoDoAmigo) && !amigoDoAmigo.equals(usuario)) {
//                    contagemAmigosEmComum.put(amigoDoAmigo.getNome(), contagemAmigosEmComum.getOrDefault(amigoDoAmigo.getNome(), 0) + 1);
//                }
//            }
//        }
//
//        List<String> recomendacoes = new ArrayList<>();
//        for (Map.Entry<String, Integer> entry : contagemAmigosEmComum.entrySet()) {
//            if (entry.getValue() > 1) {
//                recomendacoes.add(entry.getKey());
//            }
//        }
//
//        return recomendacoes;
//    }

//    public List<String> encontrarUsuariosComInteresses(String interesse) {
//        List<String> usuariosComInteresse = new ArrayList<>();
//        for (Usuario usuario : usuarios.values()) {
//            if (usuario.getInteresses().contains(interesse)) {
//                usuariosComInteresse.add(usuario.getNome());
//            }
//        }
//        return usuariosComInteresse;
//    }

    public void enviarSolicitacaoAmizade(Usuario remetente, Usuario destinatario) {
        destinatario.adicionarSolicitacaoAmizade(remetente);
        System.out.println(remetente.getNome() + " enviou uma solicitação de amizade para " + destinatario.getNome());
    }

    public void aceitarSolicitacaoAmizade(Usuario usuarioAtual, Usuario solicitante) {
        usuarioAtual.aceitarSolicitacaoAmizade(solicitante);
        System.out.println("Você agora é amigo de " + solicitante.getNome());
    }

    public void recusarSolicitacaoAmizade(Usuario usuarioAtual, Usuario solicitante) {
        usuarioAtual.removerSolicitacaoAmizade(solicitante);
    System.out.println("Pedido de amizade de " + solicitante.getNome() + " recusado!");
    }

    public void salvarDados() {
        PersistenciaJSON.salvarDados(usuarios);
    }

    public List<Usuario> getUsuarios() {
        return new ArrayList<>(usuarios.values());
    }
}
