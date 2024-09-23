public class Main {
    public static void main(String[] args) {
        RedeSocial rede = new RedeSocial();

        // Adicionando usuários
        rede.adicionarUsuario("Alice");
        rede.adicionarUsuario("Bob");
        rede.adicionarUsuario("Carlos");
        rede.adicionarUsuario("Diana");

        // Adicionando amizades
        rede.adicionarAmizade("Alice", "Bob");
        rede.adicionarAmizade("Bob", "Carlos");
        rede.adicionarAmizade("Carlos", "Diana");
        rede.adicionarAmizade("Alice", "Diana");

        // Amigos em comum
        System.out.println("Amigos em comum entre Alice e Carlos:");
        rede.amigosEmComum("Alice", "Carlos");

        // Sugerir amigos
        System.out.println("\nSugestão de amigos para Alice:");
        rede.sugerirAmigos("Alice");

        // Grau de separação
        System.out.println("\nGrau de separação entre Alice e Carlos:");
        int grau = rede.grauDeSeparacao("Alice", "Carlos");
        System.out.println("Grau de separação: " + grau);
    }
}
