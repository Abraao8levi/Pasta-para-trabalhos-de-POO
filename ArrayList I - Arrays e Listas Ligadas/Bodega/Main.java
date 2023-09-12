import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

 class Pessoa {
    private String nome;

    public Pessoa(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return nome;
    }
}

class Mercantil {
    private List<Queue<Pessoa>> caixas; // caixas do supermercado
    private Queue<Pessoa> esperando; // lista de clientes esperando

    public Mercantil(int qtd_caixas) {
        caixas = new ArrayList<>(qtd_caixas);
        for (int i = 0; i < qtd_caixas; i++) {
            caixas.add(new LinkedList<>());
        }
        esperando = new LinkedList<>();
    }

    private boolean validarIndice(int indice) {
        return indice >= 0 && indice < caixas.size();
    }

    public void chegar(Pessoa pessoa) {
        esperando.offer(pessoa);
    }

    public boolean chamarNoCaixa(int indice) {
        if (!validarIndice(indice)) {
            System.out.println("fail: caixa inexistente");
            return false;
        }

        if (caixas.get(indice).isEmpty()) {
            if (!esperando.isEmpty()) {
                caixas.get(indice).offer(esperando.poll());
                return true;
            } else {
                System.out.println("fail: sem clientes");
                return false;
            }
        } else {
            System.out.println("fail: caixa ocupado");
            return false;
        }
    }

    public Pessoa finalizar(int indice) {
        if (!validarIndice(indice)) {
            System.out.println("fail: caixa inexistente");
            return null;
        }

        if (caixas.get(indice).isEmpty()) {
            System.out.println("fail: caixa vazio");
            return null;
        }

        return caixas.get(indice).poll();
    }

    @Override
    public String toString() {
        StringBuilder caixasStr = new StringBuilder("Caixas: [");
        for (Queue<Pessoa> caixa : caixas) {
            if (caixa.isEmpty()) {
                caixasStr.append("-----, ");
            } else {
                caixasStr.append(caixa.peek()).append(", ");
            }
        }
        if (caixas.size() > 0) {
            caixasStr.setLength(caixasStr.length() - 2); // Remove a última vírgula e espaço
        }
        caixasStr.append("]\n");
        return caixasStr.toString() + "Espera: " + esperando;
    }
}

public class Main {
    public static void main(String[] args) {
        Mercantil supermercado = new Mercantil(1);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String line = scanner.nextLine();
            System.out.println("$" + line);
            String[] command = line.split(" ");

            if (command[0].equals("end")) {
                break;
            } else if (command[0].equals("init")) {
                supermercado = new Mercantil(Integer.parseInt(command[1]));
            } else if (command[0].equals("call")) {
                supermercado.chamarNoCaixa(Integer.parseInt(command[1]));
            } else if (command[0].equals("finish")) {
                supermercado.finalizar(Integer.parseInt(command[1]));
            } else if (command[0].equals("arrive")) {
                supermercado.chegar(new Pessoa(command[1]));
            } else if (command[0].equals("show")) {
                System.out.println(supermercado);
            } else {
                System.out.println("fail: comando invalido");
            }
        }

        scanner.close();
    }
}
