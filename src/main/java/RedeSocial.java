import java.util.*;

public class RedeSocial {
    Map<String, Usuario> usuarios;

    public RedeSocial() {
        usuarios = new HashMap<>();
    }

    // Adicionar um novo usuário à rede
    public void adicionarUsuario(String nome) {
        if (!usuarios.containsKey(nome)) {
            usuarios.put(nome, new Usuario(nome));
        } else {
            System.out.println("Usuário já existe.");
        }
    }

    // Adicionar amizade entre dois usuários
    public void adicionarAmizade(String nome1, String nome2) {
        Usuario u1 = usuarios.get(nome1);
        Usuario u2 = usuarios.get(nome2);
        if (u1 != null && u2 != null && !u1.amigos.contains(u2)) {
            u1.amigos.add(u2);
            u2.amigos.add(u1);
        } else {
            System.out.println("Um dos usuários não existe ou a amizade já existe.");
        }
    }

    // Mostrar amigos em comum entre dois usuários
    public void amigosEmComum(String nome1, String nome2) {
        Usuario u1 = usuarios.get(nome1);
        Usuario u2 = usuarios.get(nome2);
        if (u1 != null && u2 != null) {
            Set<Usuario> comum = new HashSet<>(u1.amigos);
            comum.retainAll(u2.amigos);  // Interseção dos amigos
            if (comum.isEmpty()) {
                System.out.println("Não há amigos em comum.");
            } else {
                System.out.println("Amigos em comum:");
                for (Usuario u : comum) {
                    System.out.println(u.nome);
                }
            }
        } else {
            System.out.println("Um dos usuários não existe.");
        }
    }

    // Sugerir amigos para um usuário baseado em amigos de amigos
    public void sugerirAmigos(String nome) {
        Usuario usuario = usuarios.get(nome);
        if (usuario != null) {
            Map<Usuario, Integer> sugestoes = new HashMap<>();

            // Iterar pelos amigos do usuário
            for (Usuario amigo : usuario.amigos) {
                // Iterar pelos amigos dos amigos
                for (Usuario amigoDoAmigo : amigo.amigos) {
                    if (!amigoDoAmigo.equals(usuario) && !usuario.amigos.contains(amigoDoAmigo)) {
                        sugestoes.put(amigoDoAmigo, sugestoes.getOrDefault(amigoDoAmigo, 0) + 1);
                    }
                }
            }

            if (sugestoes.isEmpty()) {
                System.out.println("Nenhuma sugestão de amizade.");
            } else {
                // Ordenar as sugestões por número de amigos em comum
                sugestoes.entrySet().stream()
                        .sorted((e1, e2) -> e2.getValue() - e1.getValue())
                        .forEach(entry -> {
                            System.out.println("Sugerido: " + entry.getKey().nome + " - Amigos em comum: " + entry.getValue());
                        });
            }
        } else {
            System.out.println("Usuário não encontrado.");
        }
    }

    // Encontrar o grau de separação entre dois usuários
    public int grauDeSeparacao(String nome1, String nome2) {
        Usuario u1 = usuarios.get(nome1);
        Usuario u2 = usuarios.get(nome2);
        if (u1 == null || u2 == null) {
            System.out.println("Um dos usuários não existe.");
            return -1; // Usuário não encontrado
        }

        Queue<Usuario> fila = new LinkedList<>();
        Set<Usuario> visitado = new HashSet<>();
        Map<Usuario, Integer> distancia = new HashMap<>();

        fila.add(u1);
        visitado.add(u1);
        distancia.put(u1, 0);

        while (!fila.isEmpty()) {
            Usuario atual = fila.poll();
            if (atual.equals(u2)) {
                return distancia.get(atual); // Achou o outro usuário
            }
            for (Usuario amigo : atual.amigos) {
                if (!visitado.contains(amigo)) {
                    visitado.add(amigo);
                    fila.add(amigo);
                    distancia.put(amigo, distancia.get(atual) + 1);
                }
            }
        }

        return -1; // Sem conexão entre os usuários
    }
}
