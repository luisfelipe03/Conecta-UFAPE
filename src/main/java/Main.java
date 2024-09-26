import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
import java.util.Set;

public class Main {

    private static final RedeSocial redeSocial = new RedeSocial();
    private static Usuario usuarioAtual = null;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean rodando = true;

        while (rodando) {
            if (usuarioAtual == null) {
                System.out.println("Bem-vindo ao Conecta UFAPE!");
                System.out.println("1. Login");
                System.out.println("2. Registrar");
                System.out.println("0. Sair");

                int opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1 -> login(scanner);
                    case 2 -> registrar(scanner);
                    case 0 -> rodando = false;
                    default -> System.out.println("Opção inválida.");
                }
            } else {
                System.out.println("\nBem-vindo, " + usuarioAtual.getNome().split(" ")[0]);
                System.out.println("1. Ver perfil");
                System.out.println("2. Adicionar interesse ao perfil");
                System.out.println("3. Recomendações de amigos");
                System.out.println("4. Buscar usuário por nome");
                System.out.println("5. Solicitações de amizade(" + usuarioAtual.getSolicitacoesAmizadePendentes().size() + ")");
                System.out.println("0. Logout");

                int opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1 -> verPerfil();
                    case 2 -> adicionarInteresseAoPerfil(scanner);
                    case 3 -> recomendacoesAmigosEmComumEInteresse();
                    case 4 -> buscarUsuarioPorNome(scanner);
                    case 5 -> solicitacoesAmizade();
                    case 0 -> usuarioAtual = null;
                    default -> System.out.println("Opção inválida.");
                }
            }
        }

        scanner.close();
    }

    private static void solicitacoesAmizade() {
        Set<String> solicitacoes = usuarioAtual.getSolicitacoesAmizadePendentes();

        if (solicitacoes.isEmpty()) {
            System.out.println("Você não tem nenhuma solicitação de amizade!");
            return;
        }

        System.out.println("Solicitações de Amizade Pendentes:");
        List<String> listaSolicitacoes = new ArrayList<>(solicitacoes);

        // Lista todas as solicitações de amizade
        for (int i = 0; i < listaSolicitacoes.size(); i++) {
            String emailSolicitante = listaSolicitacoes.get(i);
            System.out.println((i + 1) + ". " + emailSolicitante);
        }

        System.out.println("Escolha uma solicitação para aceitar ou recusar (digite o número correspondente): ");
        Scanner scanner = new Scanner(System.in);
        int opcao = scanner.nextInt();

        if (opcao > 0 && opcao <= listaSolicitacoes.size()) {
            String emailSelecionado = listaSolicitacoes.get(opcao - 1);
            Usuario solicitante = redeSocial.buscarUsuarioPorEmail(emailSelecionado);

            if (solicitante != null) {
                System.out.println("Você selecionou a solicitação de amizade de " + solicitante.getNome() + ".");
                System.out.println("Deseja aceitar ou recusar a solicitação? (digite 'a' para aceitar ou 'r' para recusar): ");
                char resposta = scanner.next().charAt(0);

                if (resposta == 'a' || resposta == 'A') {
                    redeSocial.aceitarSolicitacaoAmizade(usuarioAtual, solicitante);
                    System.out.println("Você agora é amigo de " + solicitante.getNome());
                } else if (resposta == 'r' || resposta == 'R') {
                    redeSocial.recusarSolicitacaoAmizade(usuarioAtual, solicitante);
                    System.out.println("Solicitação de amizade de " + solicitante.getNome() + " foi recusada.");
                } else {
                    System.out.println("Opção inválida. Nenhuma ação foi realizada.");
                }
            } else {
                System.out.println("Usuário não encontrado.");
            }
        } else {
            System.out.println("Opção inválida. Tente novamente.");
        }
    }

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

    private static void registrar(Scanner scanner) {
        System.out.println("Digite seu nome: ");
        String nome = scanner.nextLine();
        System.out.println("Digite seu curso: ");
        String curso = scanner.nextLine();
        System.out.println("Digite seu email: ");
        String email = scanner.nextLine();
        System.out.println("Digite sua senha: ");
        String senha = scanner.nextLine();

        Usuario novoUsuario = new Usuario(nome, email, senha, curso);
        redeSocial.adicionarUsuario(novoUsuario);
        System.out.println("Usuário registrado com sucesso!");
    }

    private static void verPerfil() {
        System.out.println("-=-=-=-=-= Conecta UFAPE =-=-=-=-=-=-");
        System.out.println("Usuário: " + usuarioAtual.getNome());
        System.out.println("Curso: " + usuarioAtual.getCurso());
        System.out.println("Email: " + usuarioAtual.getEmail());
        System.out.println("Interesses: " + usuarioAtual.getInteresses());

        Set<String> amigos = usuarioAtual.getAmigos(); // Obter a lista de amigos (agora apenas e-mails)
        if (amigos.isEmpty()) {
            System.out.println("\nVocê não tem amigos ainda.");
        } else {
            System.out.println("\nSeus amigos:");
            for (String emailAmigo : amigos) {
                Usuario amigo = redeSocial.buscarUsuarioPorEmail(emailAmigo); // Buscar o objeto Usuario pelo e-mail
                if (amigo != null) {
                    System.out.println("- " + amigo.getNome()); // Exibir o nome do amigo
                }
            }
        }
    }


    private static void recomendacoesAmigosEmComumEInteresse() {
        List<Usuario> recomendacoes = redeSocial.recomendarAmigos(usuarioAtual);
        if (recomendacoes.isEmpty()) {
            System.out.println("\nNenhuma recomendação encontrada.");
        } else {
            System.out.println("\nRecomendações de amigos:");
            for (int i = 0; i < recomendacoes.size(); i++) {
                System.out.println((i + 1) + ". " + recomendacoes.get(i).getNome()
                        + " | Curso: " + recomendacoes.get(i).getCurso()
                        + " | Interesses: " + recomendacoes.get(i).getInteresses());
            }

            System.out.println("Digite o número do usuário para enviar uma solicitação de amizade (ou '0' para cancelar):");
            Scanner scanner = new Scanner(System.in);
            int numeroEscolhido = scanner.nextInt();

            // Verifica se o usuário deseja cancelar
            if (numeroEscolhido == 0) {
                return;
            }

            // Envia a solicitação de amizade se o número for válido
            if (numeroEscolhido > 0 && numeroEscolhido <= recomendacoes.size()) {
                Usuario usuarioParaAdicionar = recomendacoes.get(numeroEscolhido - 1);
                redeSocial.enviarSolicitacaoAmizade(usuarioAtual, usuarioParaAdicionar);
                System.out.println("Solicitação de amizade enviada com Sucesso!");
            } else {
                System.out.println("Número inválido.");
            }
        }
    }

    private static void buscarUsuarioPorNome(Scanner scanner) {
        System.out.println("Digite o nome do usuário: ");
        String nome = scanner.nextLine();

        List<Usuario> usuariosEncontrados = new ArrayList<>();
        for (Usuario usuario : redeSocial.getUsuarios()) {
            if (usuario.getNome().toLowerCase().contains(nome.toLowerCase())) {
                usuariosEncontrados.add(usuario);
            }
        }

        if (usuariosEncontrados.isEmpty()) {
            System.out.println("Nenhum usuário encontrado.");
        } else {
            System.out.println("Usuários encontrados:");
            for (int i = 0; i < usuariosEncontrados.size(); i++) {
                System.out.println((i + 1) + ". " + usuariosEncontrados.get(i).getNome());
            }

            System.out.println("Digite o número do usuário para enviar uma solicitação de amizade (ou '0' para cancelar):");
            int numeroEscolhido = scanner.nextInt();

            // Verifica se o usuário deseja cancelar
            if (numeroEscolhido == 0) {
                return;
            }

            // Envia a solicitação de amizade se o número for válido
            if (numeroEscolhido > 0 && numeroEscolhido <= usuariosEncontrados.size()) {
                Usuario usuarioParaAdicionar = usuariosEncontrados.get(numeroEscolhido - 1);
                redeSocial.enviarSolicitacaoAmizade(usuarioAtual, usuarioParaAdicionar);
                System.out.println("Solicitação de amizade enviada para " + usuarioParaAdicionar.getNome());
            } else {
                System.out.println("Número inválido.");
            }
        }
    }


    private static void adicionarInteresseAoPerfil(Scanner scanner) {
        System.out.println("Digite o interesse que deseja adicionar ao seu perfil:");
        String interesse = scanner.nextLine();

        usuarioAtual.adicionarInteresse(interesse);
        System.out.println("Interesse adicionado com sucesso!");

        redeSocial.salvarDados();
    }
}
