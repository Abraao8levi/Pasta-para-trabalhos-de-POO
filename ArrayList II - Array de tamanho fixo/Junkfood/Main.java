
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Slot {
    private String name; // Nome do item no slot
    private float price; // Preço do item no slot
    private int quantity;   // Quantidade de itens no slot
   
    // Construtor para inicializar um slot com nome, preço e quantidade
    public Slot(String name, float price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String toString() {
        if (name.equals("empty")) {
            return "[" + "   " + name + " : " + quantity + " U " + ": " + String.format("%.2f", price) + " RS" + "]";
        } else {
            return "[" + " " + name + " : " + quantity + " U " + ": " + String.format("%.2f", price) + " RS" + "]";
        }
    }
}
// Classe para representar a Máquina de Venda

class VendingMachine {
    private List<Slot> slots; // Lista de slots na máquina
    private float profit; // Lucro da máquina
    private float cash;  // Dinheiro disponível na máquina
    private int capacity;// Capacidade total de slots na máquina
    private float totalSales;  // Total de vendas realizadas pela máquina

    // Construtor para inicializar a máquina com uma capacidade específica
    public VendingMachine(int capacity) {
        this.capacity = capacity;
        slots = new ArrayList<>();
        for (int i = 0; i < capacity; i++) {
            slots.add(new Slot("empty", 0.0f, 0));
        }
    }

    public Slot getSlot(int index) { //responsável pela ordenação de cada elemento em uma dada coleção 
        return slots.get(index);
    }

    public void setSlot(int index, String name, int quantity, float price) {
        if (index > capacity - 1) {
            System.out.println("fail: indice nao existe");
        } else {
            slots.get(index).setName(name);
            slots.get(index).setPrice(price);
            slots.get(index).setQuantity(quantity);
        }
    }

    public void clearSlots(int index) {
        slots.get(index).setName("empty");
        slots.get(index).setPrice(0.0f);
        slots.get(index).setQuantity(0);
    }

    public void setCash(float cash) {
        this.cash += cash;
    }

    public void withdrawCash() {
        System.out.println("voce recebeu " + String.format("%.2f", cash) + " RS");
        cash = 0.0f;
    }

    public float getCash() {
        return cash;
    }

    public float getProfit() {
        return profit;
    }
  //Verifica se o índice fornecido é válido (dentro dos limites de capacidade)
    public void buyItem(int index) {
        if (index > capacity - 1) {
            System.out.println("fail: indice nao existe");
        } else if (slots.get(index).getPrice() <= cash) { // Verifica se o preço do item no slot é menor ou igual ao dinheiro disponível
            if (slots.get(index).getQuantity() > 0) { // Verifica se há pelo menos um item disponível no slot
                slots.get(index).setQuantity(slots.get(index).getQuantity() - 1);
                cash -= slots.get(index).getPrice();
                totalSales += slots.get(index).getPrice();
                System.out.println("voce comprou um " + slots.get(index).getName());
            } else {// Caso não haja produtos disponíveis no slot, exibe uma mensagem de erro
                System.out.println("fail: espiral sem produtos");
            }
        } else { // Caso o dinheiro disponível seja insuficiente para comprar o item, exibe uma mensagem de erro
            System.out.println("fail: saldo insuficiente");
        }
    }

    public float getTotalSales() {
        return totalSales;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("saldo: ").append(String.format("%.2f", cash)).append("\n");
        for (int i = 0; i < slots.size(); i++) {
            Slot element = slots.get(i);
            if (i == slots.size() - 1) {
                sb.append(i).append(" ").append(element.toString());
            } else {
                sb.append(i).append(" ").append(element.toString()).append("\n");
            }
        }
        return sb.toString();
    }
}

public class Main {
    public static void main(String[] args) {
        VendingMachine machine = new VendingMachine(0); // máquina
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String line = scanner.nextLine();
            System.out.println("$" + line);

            String[] input = line.split(" ");

            if (input[0].equals("end")) {
                break;
            } else if (input[0].equals("show")) {
                System.out.println(machine.toString());
            } else if (input[0].equals("init")) {
                machine = new VendingMachine(Integer.parseInt(input[1]));
            } else if (input[0].equals("set")) {
                machine.setSlot(Integer.parseInt(input[1]), input[2], Integer.parseInt(input[3]), Float.parseFloat(input[4]));
            } else if (input[0].equals("limpar")) {
                machine.clearSlots(Integer.parseInt(input[1]));
            } else if (input[0].equals("dinheiro")) {
                machine.setCash(Float.parseFloat(input[1]));
            } else if (input[0].equals("troco")) {
                machine.withdrawCash();
            } else if (input[0].equals("comprar")) {
                machine.buyItem(Integer.parseInt(input[1]));
            } else if (input[0].equals("apurado")) {
              System.out.println("apurado total: " + String.format("%.2f", machine.getTotalSales()));


            }
        }

        scanner.close();
    }
}
