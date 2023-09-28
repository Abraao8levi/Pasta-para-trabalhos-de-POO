
import java.util.*;
import java.util.stream.*;

class Pass {
    private String name;
    private int age;

    public Pass(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Verifica se o passageiro é prioritário com base na idade
    public boolean isPriority() {
        return age >= 65;
    }

    // Obtém o nome do passageiro
    public String getName() {
        return this.name;
    }

    // Representação em string do passageiro (nome:idade)
    public String toString() {
        return this.name + ":" + this.age;
    }
}

class Topic {
    private List<Pass> prioritySeats; // Cadeiras prioritárias
    private List<Pass> normalSeats;   // Cadeiras normais
    private int capacity;             // Capacidade total da tópica
    private int qtdPriority;          // Quantidade de cadeiras prioritárias

    // Construtor da tópica com verificação de argumentos inválidos
    public Topic(int capacity, int qtdPriority) {
        if (capacity < 0 || qtdPriority < 0 || qtdPriority > capacity) {
            throw new IllegalArgumentException("Capacidade e quantidade de prioridades devem ser não negativas e qtdPriority não pode ser maior que capacity.");
             // Esta linha cria e lança uma exceção IllegalArgumentException com uma mensagem de erro personalizada.
           // Isso interromperá a execução do programa e sinalizará que um argumento inválido foi fornecido.
        }

        this.capacity = capacity;
        this.qtdPriority = qtdPriority;
        prioritySeats = new ArrayList<>(Collections.nCopies(qtdPriority, null));
        normalSeats = new ArrayList<>(Collections.nCopies(capacity - qtdPriority, null));
    }

    // Encontra a primeira posição livre em uma lista de cadeiras
    private int findFirstFreePos(List<Pass> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == null) {
                return i;
            }
        }
        return -1; // Retorna -1 se todas as cadeiras estiverem ocupadas
    }

    // Encontra um passageiro pelo nome em uma lista de cadeiras
    private int findByName(String name, List<Pass> list) {
        for (int i = 0; i < list.size(); i++) {
            Pass pass = list.get(i);
            if (pass != null && pass.getName().equals(name)) {
                return i; // Retorna a posição do passageiro encontrado
            }
        }
        return -1; // Retorna -1 se o passageiro não estiver na lista
    }

    // Insere um passageiro em uma lista de cadeiras
    private boolean insertOnList(Pass pass, List<Pass> list) {
        int pos = findFirstFreePos(list);
        if (pos != -1) {
            list.set(pos, pass); // Insere o passageiro na primeira cadeira livre
            return true;         // Retorna verdadeiro para sucesso na inserção
        }
        return false;            // Retorna falso se todas as cadeiras estiverem ocupadas
    }

    // Remove um passageiro pelo nome de uma lista de cadeiras
    private boolean removeFromList(String name, List<Pass> list) {
        int pos = findByName(name, list);
        if (pos != -1) {
            list.set(pos, null); // Remove o passageiro da lista
            return true;         // Retorna verdadeiro para sucesso na remoção
        }
        return false;            // Retorna falso se o passageiro não estiver na lista
    }

    // Verifica se um passageiro já está na tópica
    private boolean isPassengerInTopic(String name) {
        return findByName(name, prioritySeats) != -1 || findByName(name, normalSeats) != -1;
    }

    // Insere um passageiro na tópica com verificação de duplicação
    public boolean insert(Pass pass) {
        if (isPassengerInTopic(pass.getName())) {
            System.out.println("fail: " + pass.getName() + " ja esta na topic");
            return false; // Retorna falso se o passageiro já estiver na tópica
        }

        if (pass.isPriority()) {
            if (insertOnList(pass, prioritySeats)) {
                return true; // Retorna verdadeiro para sucesso na inserção
            } else if (insertOnList(pass, normalSeats)) {
                return true; // Retorna verdadeiro para sucesso na inserção
            }
        } else {
            if (insertOnList(pass, normalSeats)) {
                return true; // Retorna verdadeiro para sucesso na inserção
            } else if (insertOnList(pass, prioritySeats)) {
                return true; // Retorna verdadeiro para sucesso na inserção
            }
        }
        System.out.println("fail: topic lotada");
        return false; // Retorna falso se todas as cadeiras estiverem ocupadas
    }

    // Remove um passageiro da tópica
    public boolean remove(String name) {
        if (removeFromList(name, prioritySeats) || removeFromList(name, normalSeats)) {
            return true; // Retorna verdadeiro para sucesso na remoção
        }
        System.out.println("fail: " + name + " nao esta na topic");
        return false; // Retorna falso se o passageiro não estiver na tópica
    }

    // Representação em string da tópica (lista de cadeiras)
    public String toString() {
        return "[" + Stream.concat(
                prioritySeats.stream().map(p -> ("@" + ((p == null) ? ("") : ("" + p)))),
                normalSeats.stream().map(p -> ("=" + ((p == null) ? ("") : ("" + p)))))
                .collect(Collectors.joining(" ")) + "]";
    }
}

public class Solver {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Topic topic = new Topic(0, 0);
        while (true) {
            String line = scanner.nextLine();
            System.out.println("$" + line);
            String[] ui = line.split(" ");
            if (line.equals("end")) {
                break;
            } else if (ui[0].equals("init")) { // Inicializa a tópica (capacidade qtdPriority)
                topic = new Topic(Integer.parseInt(ui[1]), Integer.parseInt(ui[2]));
            } else if (ui[0].equals("show")) { // Mostra o estado da tópica
                System.out.println(topic);
            } else if (ui[0].equals("in")) { // Insere um passageiro (in nome idade)
                topic.insert(new Pass(ui[1], Integer.parseInt(ui[2])));
            } else if (ui[0].equals("out")) { // Remove um passageiro (out nome)
                topic.remove(ui[1]);
            } else {
                System.out.println("fail: comando invalido");
            }
        }
        scanner.close();
    }
}
