
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


 class Passageiro {
    private String nome;

    Passageiro(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return nome;
    }
}

class Item {
    private float weight;
    private String name;

    Item(float weight, String name) {
        this.weight = weight;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return name + ":" + weight;
    }
}

class Trem {
    private ArrayList<Vagao> vagoes;
    private ArrayList<Cargo> cargos;
    private char[] a = new char[20];
    private int count = 0;
    private int maxCapacidade;
    private String movimentacao = "";
    private String cadastro = "";

    Trem(int capacidade) {
        vagoes = new ArrayList<>();
        cargos = new ArrayList<>();
        maxCapacidade = capacidade;
    }

    public void addVagao(int capacidade) {
        if (vagoes.size() + cargos.size() >= maxCapacidade) {
            throw new RuntimeException("fail: limite de vagões atingido");
        }

        vagoes.add(new Vagao(capacidade));
        a[count] = 'v';
        count++;
    }

    public void desembarcar(String nome) {
        for (Cargo i : cargos) {
            for (Item j : i.getItems()) {
                if (j.getName().equals(nome)) {
                    cargos.get(cargos.indexOf(i)).removeItem(i.getItems().indexOf(j));

                    movimentacoes(j.toString(), "out");
                    return;
                }
            }
        }

        int indexVagao = -1;
        int indexPassageiro = -1;

        search: for (Vagao i : vagoes) {
            for (Passageiro j : i.getPassageiros()) {
                if (j.getNome().equals(nome)) {
                    indexVagao = vagoes.indexOf(i);
                    indexPassageiro = i.getPassageiros().indexOf(j);

                    break search;
                }
            }
        }

        if (indexPassageiro == -1) {
            throw new RuntimeException("fail: " + nome + " nao esta no trem");
        }

        vagoes.get(indexVagao).desembarcar(nome, indexPassageiro);
        movimentacoes(nome, "out");
    }

    public void embarcar(String nome) {
        int indexVagao = -1;
        int indexPassageiro = -1;

        search: for (Vagao i : vagoes) {
            for (Passageiro j : i.getPassageiros()) {
                if (j.getNome().equals(nome)) {
                    throw new RuntimeException("fail: " + nome + " já está no trem");
                }

                if (j.getNome().equals("-")) {
                    indexVagao = vagoes.indexOf(i);
                    indexPassageiro = i.getPassageiros().indexOf(j);

                    break search;
                }
            }
        }

        cadastro(nome);

        if (indexPassageiro == -1) {
            throw new RuntimeException("fail: trem lotado");
        }

        vagoes.get(indexVagao).embarcar(nome, indexPassageiro);
        movimentacoes(nome, "in");
    }

    private void movimentacoes(String nome, String tipo) {
        if (!movimentacao.isBlank()) {
            movimentacao += "\n";
        }

        movimentacao += nome + " " + tipo;
    }

    private void cadastro(String nome) {
        if (cadastro.contains(nome)) {
            return;
        }

        if (!cadastro.isBlank()) {
            cadastro += "\n";
        }

        cadastro += nome;
    }

    public void addItem(String nome, float weight) {
        cadastro(nome + ":" + weight);
        cargos.get(0).addItem(nome, weight);
        movimentacoes(nome + ":" + weight, "in");
    }

    @Override
    public String toString() {
        String str = "";
        int x = 0;
        int y = 0;

        for (int i = 0; i < a.length; i++) {
            if (a[i] == 'v') {
                str += "[" + vagoes.get(x).toString() + "]";
                x++;
            }

            if (a[i] == 'c') {
                str += "(" + cargos.get(y).toString() + ")";
                y++;
            }
        }

        if (str.equals("")) {
            str = "[]";
        }

        return "Trem " + str;
    }

    public void addCargo(float capacidade) {
        if (vagoes.size() + cargos.size() >= maxCapacidade) {
            throw new RuntimeException("fail: limite de vagões atingido");
        }

        cargos.add(new Cargo(capacidade));
        a[count] = 'c';
        count++;
    }

    public String getCadastro() {
        String[] temp = cadastro.split("\n");

        Arrays.sort(temp);

        cadastro = String.join("\n", temp);

        return cadastro;
    }

    public String getMovimentacao() {
        return movimentacao;
    }
}
class Cargo {
    private float capacity;
    private ArrayList<Item> items;

    Cargo(float capacity) {
        items = new ArrayList<>();

        this.capacity = capacity;
    }

    @Override
    public String toString() {
        String str = " ";

        for (Item i : items) {
            str += i + " ";
        }

        return str + "_" + getLeftCapacity() + " ";
    }

    public void addItem(String name, float weight) {
        Item item = new Item(weight, name);

        float x = getLeftCapacity() - item.getWeight();

        if (x <= 0) {
            throw new RuntimeException("fail: trem lotado");
        }

        items.add(new Item(weight, name));
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void removeItem(int index) {
        items.remove(index);
    }

    private float getLeftCapacity() {
        float weight = 0;

        for (Item i : items) {
            weight += i.getWeight();
        }

        return capacity - weight;
    }
}

class Vagao {
    private ArrayList<Passageiro> passageiros;
    private int maxCapacidade;

    Vagao(int capacidade) {
        passageiros = new ArrayList<>();
        maxCapacidade = capacidade;

        for (int i = 0; i < capacidade; i++) {
            passageiros.add(new Passageiro("-"));
        }
    }

    public void embarcar(String nome, int indexPassageiro) {
        passageiros.set(indexPassageiro, new Passageiro(nome));
    }

    public void desembarcar(String nome, int indexPassageiro) {
        passageiros.set(indexPassageiro, new Passageiro("-"));
    }

    @Override
    public String toString() {
        String str = " ";

        for (Passageiro i : passageiros) {
            str += i + " ";
        }

        return str;
    }

    public ArrayList<Passageiro> getPassageiros() {
        return passageiros;
    }
}

public class Solver {
    private static Scanner in = new Scanner(System.in);
    private static Trem trem;

    public static void main(String[] args) {
        while (true) {
            try {
                String line = input();
                System.out.println("$" + line);
                String[] arg = line.split(" ");

                switch (arg[0]) {
                    case "end":
                        return;

                    case "init":
                        trem = new Trem(numberInt(arg[1]));
                        break;

                    case "nwvp":
                        trem.addVagao(numberInt(arg[1]));
                        break;

                    case "nwvc":
                        trem.addCargo(numberFloat(arg[1]));
                        break;

                    case "addp":
                        trem.embarcar(arg[1]);
                        break;

                    case "addc":
                        trem.addItem(arg[1], numberFloat(arg[2]));
                        break;

                    case "la":
                        System.out.println(trem);
                        break;

                    case "show":
                        System.out.println(trem);
                        break;

                    case "cadastro":
                        System.out.println(trem.getCadastro());
                        break;

                    case "movimentacao":
                        System.out.println(trem.getMovimentacao());
                        break;

                    case "sair":
                        trem.desembarcar(arg[1]);
                        break;

                    default:
                        System.out.println("fail: comando invalido");
                        break;
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static String input() {
        return in.nextLine();
    }

    private static int numberInt(String num) {
        return Integer.parseInt(num);
    }

    private static float numberFloat(String num) {
        return Float.parseFloat(num);
    }
}