import java.util.ArrayList;
import java.util.Scanner;

abstract class Funcionario {
    protected int bonus;
    protected int diarias = 0;
    protected int maxDiarias;
    protected String nome;

    Funcionario(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void addDiaria() throws Exception {
        if (maxDiarias > diarias) {
            diarias++;

            return;
        }

        throw new Exception("fail: limite de diarias atingido");
    }

    public abstract int getSalario();

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }
}

class Professor extends Funcionario {
    protected String classe;

    Professor(String nome, String classe) {
        super(nome);
        this.classe = classe;
        maxDiarias = 2;
    }

    public String getClasse() {
        return classe;
    }

    @Override
    public int getSalario() {
        int extra = 100 * diarias + bonus;

        if (classe.equals("A")) {
            return 3000 + extra;
        }

        if (classe.equals("B")) {
            return 5000 + extra;
        }

        if (classe.equals("C")) {
            return 7000 + extra;
        }

        if (classe.equals("D")) {
            return 9000 + extra;
        }

        if (classe.equals("E")) {
            return 11000 + extra;
        }

        return -1;
    }

    @Override
    public String toString() {
        return "prof:" + nome + ":" + classe + ":" + getSalario();
    }
}

class Tercerizado extends Funcionario {
    protected int horas;
    protected String isSalubre;

    Tercerizado(String nome, int horas, String isSalubre) {
        super(nome);
        this.horas = horas;
        this.isSalubre = isSalubre;
    }

    @Override
    public int getSalario() {
        int acr = 0;

        if (isSalubre.equals("sim")) {
            acr = 500;
        }

        return 4 * horas + acr + bonus;
    }

    @Override
    public void addDiaria() throws Exception {
        throw new Exception("fail: terc nao pode receber diaria");
    }

    @Override
    public String toString() {
        return "ter:" + nome + ":" + horas + ":" + isSalubre + ":" + getSalario();
    }
}

class STA extends Funcionario {
    protected int nivel;

    STA(String nome, int nivel) {
        super(nome);
        this.nivel = nivel;
        maxDiarias = 1;
    }

    public int getNivel() {
        return nivel;
    }

    @Override
    public int getSalario() {
        return 3000 + 300 * nivel + 100 * diarias + bonus;
    }

    @Override
    public String toString() {
        return "sta:" + nome + ":" + nivel + ":" + getSalario();
    }
}

class UFC {
    private ArrayList<Funcionario> funcionarios;

    UFC() {
        funcionarios = new ArrayList<>();
    }

    public void addFuncionario(Funcionario funcionario) {
        funcionarios.add(funcionario);
    }

    public Funcionario getFuncionario(String nome) {
        return null;
    }

    public void rmFuncionario(String nome) {
        for (Funcionario i : funcionarios) {
            if (i.getNome().equals(nome)) {
                funcionarios.remove(i);

                break;
            }
        }
    }

    public void setBonus(int bonus) {
        for (Funcionario i : funcionarios) {
            funcionarios.get(funcionarios.indexOf(i)).setBonus(bonus / funcionarios.size());
        }
    }

    public void addDiaria(String nome) throws Exception {
        for (Funcionario i : funcionarios) {
            if (i.getNome().equals(nome)) {
                funcionarios.get(funcionarios.indexOf(i)).addDiaria();
            }
        }
    }

    @Override
    public String toString() {
        String str = "";

        for (Funcionario i : funcionarios) {
            str += i;

            if (!funcionarios.get(funcionarios.size() - 1).equals(i)) {
                str += "\n";
            }
        }

        return str;
    }

    public Funcionario showIndiv(String nome) {
        for (Funcionario i : funcionarios) {
            if (i.getNome().equals(nome)) {
                return i;
            }
        }

        return null;
    }
}

class Solver {
    private static Scanner in = new Scanner(System.in);
    private static String[] userInput;
    private static UFC ufc = new UFC();

    public static void main(String[] args) {
        while (true) {
            String line = input();
            System.out.println("$" + line);
            userInput = line.split(" ");

            try {
                menu();
            }

            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void menu() throws Exception {
        if (command("end")) {
            System.exit(0);
        }

        if (command("addProf")) {
            ufc.addFuncionario(new Professor(userInput[1], userInput[2]));
        }

        if (command("addSta")) {
            ufc.addFuncionario(new STA(userInput[1], toInt(userInput[2])));
        }

        if (command("addTer")) {
            ufc.addFuncionario(new Tercerizado(userInput[1], toInt(userInput[2]), userInput[3]));
        }

        if (command("showAll")) {
            System.out.println(ufc);
        }

        if (command("rm")) {
            ufc.rmFuncionario(userInput[1]);
        }

        if (command("show")) {
            System.out.println(ufc.showIndiv(userInput[1]));
        }

        if (command("addDiaria")) {
            ufc.addDiaria(userInput[1]);
        }

        if (command("setBonus")) {
            ufc.setBonus(toInt(userInput[1]));
        }
    }

    private static String input() {
        return in.nextLine();
    }

    private static boolean command(String command) {
        if (command.equals(userInput[0])) {
            return true;
        }

        return false;
    }

    private static int toInt(String num) {
        return Integer.parseInt(num);
    }
}