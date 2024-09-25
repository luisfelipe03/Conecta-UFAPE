import java.util.Scanner;
import java.util.List;
import java.util.Set;

public class Main {

    private static RedeSocial redeSocial = new RedeSocial();
    private static Usuario usuarioAtual = null;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean rodando = true;

        // Menu principal
        while (rodando) {
            if (usuarioAtual == null) {
                System.out.println("Bem-vindo ao Conecta UFAPE!");
                System.out.println("1. Login");
                System.out.println("2. Registrar");
                System.out.println("3. Sair");

                int opcao = scanner.nextInt();
                scanner.nextLine();  // Consumir a nova linha

                switch (opcao) {
                    case 1 -> login(scanner);
                    case 2 -> registrar(scanner);
                    case 3 -> rodando = false;
                    default -> System.out.println("Opção inválida.");
                }
            } else {
                // Menu do usuário logado
                System.out.println("\nBem-vindo, " + usuarioAtual.getNome());
                System.out.println("1. Ver amigos");
                System.out.println("2. Recomendações de amigos em comum");
                System.out.println("3. Recomendações de amigos por interesse");
                System.out.println("4. Buscar usuário por nome");
                System.out.println("5. Adicionar interesse ao perfil");
                System.out.println("6. Logout");

                int opcao = scanner.nextInt();
                scanner.nextLine();  // Consumir a nova linha

                switch (opcao) {
                    case 1 -> verAmigos();
                    case 2 -> recomendacoesAmigosEmComum();
                    case 3 -> recomendacoesAmigosPorInteresse(scanner);
                    case 4 -> buscarUsuarioPorNome(scanner);
                    case 5 -> adicionarInteresseAoPerfil(scanner); // Nova opção para adicionar interesse
                    case 6 -> usuarioAtual = null;
                    default -> System.out.println("Opção inválida.");
                }
            }
        }

        scanner.close();
    }

    // Função para realizar login
    private static void login(Scanner scanner) {
        System.out.println("Digite seu email: ");
        String email = scanner.nextLine();
        System.out.println("Digite sua senha: ");
        String senha = scanner.nextLine();

        usuarioAtual = redeSocial.buscarUsuarioPorEmailESenha(email, senha);

        if (usuarioAtual != null) {
            System.out.println("Login bem-sucedido!");
        } else {
            System.out.println("Email ou senha incorretos.");
        }
    }

    // Função para registrar um novo usuário
    private static void registrar(Scanner scanner) {
        System.out.println("Digite seu nome: ");
        String nome = scanner.nextLine();
        System.out.println("Digite seu email: ");
        String email = scanner.nextLine();
        System.out.println("Digite sua senha: ");
        String senha = scanner.nextLine();

        Usuario novoUsuario = new Usuario(nome, email, senha);
        redeSocial.adicionarUsuario(novoUsuario);
        System.out.println("Usuário registrado com sucesso!");
    }

    // Função para ver a lista de amigos
    private static void verAmigos() {
        Set<Usuario> amigos = usuarioAtual.getAmigos();
        if (amigos.isEmpty()) {
            System.out.println("Você não tem amigos ainda.");
        } else {
            System.out.println("Seus amigos:");
            for (Usuario amigo : amigos) {
                System.out.println("- " + amigo.getNome());
            }
        }
    }

    // Recomendações de amigos em comum
    private static void recomendacoesAmigosEmComum() {
        List<String> recomendacoes = redeSocial.recomendarAmigosEmComum(usuarioAtual);
        if (recomendacoes.isEmpty()) {
            System.out.println("Nenhum amigo em comum encontrado.");
        } else {
            System.out.println("Recomendações de amigos em comum:");
            for (String nome : recomendacoes) {
                System.out.println("- " + nome);
            }
        }
    }

    // Recomendações de amigos por interesse
    private static void recomendacoesAmigosPorInteresse(Scanner scanner) {
        System.out.println("Digite um interesse: ");
        String interesse = scanner.nextLine();

        List<String> recomendacoes = redeSocial.encontrarUsuariosComInteresses(interesse);
        if (recomendacoes.isEmpty()) {
            System.out.println("Nenhum usuário encontrado com esse interesse.");
        } else {
            System.out.println("Recomendações de amigos por interesse:");
            for (String nome : recomendacoes) {
                System.out.println("- " + nome);
            }
        }
    }

    // Buscar usuário por nome
    private static void buscarUsuarioPorNome(Scanner scanner) {
        System.out.println("Digite o nome do usuário: ");
        String nome = scanner.nextLine();

        Usuario usuario = redeSocial.buscarUsuario(nome);
        if (usuario != null) {
            System.out.println("Usuário encontrado: " + usuario.getNome());
        } else {
            System.out.println("Usuário não encontrado.");
        }
    }

    // Nova função para adicionar interesse ao perfil do usuário
    private static void adicionarInteresseAoPerfil(Scanner scanner) {
        System.out.println("Digite o interesse que deseja adicionar ao seu perfil:");
        String interesse = scanner.nextLine();

        usuarioAtual.adicionarInteresse(interesse);
        System.out.println("Interesse adicionado com sucesso!");

        // Persistir a alteração de interesse no JSON
        redeSocial.salvarDados();
    }
}
