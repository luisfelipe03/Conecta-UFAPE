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
        Queue<Usuario> fila = new LinkedList<>();

        // Adiciona o usuário atual à fila e ao conjunto de visitados
        fila.add(usuario);
        visitados.add(usuario);

        // Nível de distância que estamos explorando
        int nivelMaximo = 2; // por exemplo, amigos de amigos
        int nivelAtual = 0;

        while (!fila.isEmpty() && nivelAtual < nivelMaximo) {
            int tamanhoFila = fila.size();

            for (int i = 0; i < tamanhoFila; i++) {
                Usuario usuarioAtual = fila.poll();

                for (String emailAmigo : usuarioAtual.getAmigos()) {
                    Usuario amigo = buscarUsuarioPorEmail(emailAmigo);

                    if (amigo != null && !visitados.contains(amigo) && !amigo.equals(usuario)) {
                        // Adiciona amigo ao conjunto de visitados e à fila
                        visitados.add(amigo);
                        fila.add(amigo);

                        // Inicializa pontuação se o amigo ainda não estiver no mapa
                        pontuacoes.putIfAbsent(amigo, 0);

                        // Aumenta a pontuação com base nos interesses
                        Set<String> interessesEmComum = new HashSet<>(usuario.getInteresses());
                        interessesEmComum.retainAll(amigo.getInteresses());
                        if (!interessesEmComum.isEmpty()) {
                            pontuacoes.put(amigo, pontuacoes.get(amigo) + 1); // Peso 1 para interesses em comum
                        }

                        // Aumenta a pontuação com base nos cursos
                        if (usuario.getCurso().equalsIgnoreCase(amigo.getCurso())) {
                            pontuacoes.put(amigo, pontuacoes.get(amigo) + 1); // Peso 1 se os cursos forem os mesmos
                        }
                    }
                }
            }
            nivelAtual++;
        }

        // Ordena os possíveis amigos com base na pontuação
        List<Usuario> recomendacoes = new ArrayList<>(pontuacoes.keySet());
        recomendacoes.sort((u1, u2) -> pontuacoes.get(u2).compareTo(pontuacoes.get(u1))); // Ordena do maior para o menor

        return recomendacoes;
    }


    public void enviarSolicitacaoAmizade(Usuario remetente, Usuario destinatario) {
        destinatario.adicionarSolicitacaoAmizade(remetente);
        salvarDados();
        System.out.println(remetente.getNome() + " enviou uma solicitação de amizade para " + destinatario.getNome());
    }

    public void aceitarSolicitacaoAmizade(Usuario usuarioAtual, Usuario solicitante) {
        usuarioAtual.aceitarSolicitacaoAmizade(solicitante);
        salvarDados();
        System.out.println("Você agora é amigo de " + solicitante.getNome());
    }

    public void recusarSolicitacaoAmizade(Usuario usuarioAtual, Usuario solicitante) {
        usuarioAtual.removerSolicitacaoAmizade(solicitante);
        salvarDados();
        System.out.println("Pedido de amizade de " + solicitante.getNome() + " recusado!");
    }

    public void salvarDados() {
        PersistenciaJSON.salvarDados(usuarios);
    }

    public List<Usuario> getUsuarios() {
        return new ArrayList<>(usuarios.values());
    }
}
