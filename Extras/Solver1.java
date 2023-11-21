import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Scanner;

class Pass {
    protected String id;

    Pass(String id) {

        setId(id);

    }

    // Métodos gets

    public String getId() {

        return this.id;

    }

    // Métodos sets

    public void setId(String id) {

        this.id = id;

    }
}

class Pessoa extends Pass {

    Pessoa(String id) {

        super(id);

    }

    @Override
    public String toString() {

        // return String.format("%s", getId());
        return getId();

    }

}

class Carga extends Pass {

    private float peso;

    Carga(String id, float peso) {

        super(id);
        setPeso(peso);

    }

    // Métodos gets
    public float getPeso() {

        return this.peso;

    }

    // Métodos sets
    public void setPeso(float peso) {

        this.peso = peso;

    }

    @Override
    public String toString() {

        // return String.format("%s", getId());
        return getId() + ":" + getPeso();

    }

}

class Vagao {

    private LinkedList<Pass> cadeiras;
    private int capacidade;

    Vagao(int capacidade) {

        cadeiras = new LinkedList<Pass>();
        this.capacidade = capacidade;

        for (int i = 0; i < this.capacidade; i++) {
            cadeiras.add(null);
        }

    }

    @Override
    public String toString() {

        String V = "[ ";

        for (Pass pass : cadeiras) {
            V += ((pass != null) ? pass : "-") + " ";
        }

        return V + "]";
        // return String.format("[ %s]", V);

    }

    public void embarcar(Pass pass) {

        int tamanho = cadeiras.size();

        for (int i = 0; i < tamanho; i++) {

            Pass cadeira = cadeiras.get(i);

            if (cadeira == null) {

                // cadeiras.add(i, pass);
                // cadeiras.remove(i + 1);
                cadeiras.set(i, pass);
                break;

            }

        }

    }

    public void desembarcar(String idPass) {

        int tamanho = cadeiras.size();

        for (int i = 0; i < tamanho; i++) {

            Pass cadeira = cadeiras.get(i);

            if (cadeira != null) {
                if (cadeira.getId().equals(idPass)) {

                    // cadeiras.remove(i);
                    // cadeiras.add(i, new Passageiro("-"));
                    cadeiras.set(i, null);
                    break;

                }
            }

        }

    }

    public boolean search(String idPass) {

        for (Pass pass : cadeiras) {

            if (pass != null) {
                if (pass.getId().equals(idPass)) {

                    return true;

                }
            }

        }

        return false;

    }

    public Pass getPass(String idPass) {

        for (Pass pass : cadeiras) {

            if (pass != null) {
                if (pass.getId().equals(idPass)) {

                    return pass;

                }
            }

        }

        return null;

    }

    public boolean isFull() {

        boolean full = true;

        for (Pass pass : cadeiras) {

            if (pass == null) {

                full = false;

            }

        }

        return full;

    }

}

class Trem {

    private int maxVagoes;
    private ArrayList<Vagao> vagoes;
    private static Registro registro = new Registro();

    Trem(int maxVagoes) {

        vagoes = new ArrayList<Vagao>();
        this.maxVagoes = maxVagoes;

    }

    public String toString() {

        String SV = "Trem ";

        for (Vagao vagao : vagoes) {
            SV += vagao;
        }

        return SV;
        // return String.format("Trem %s", SV);

    }

    public void addVagao(Vagao vagao) {

        int tamanho = vagoes.size();

        if (tamanho == maxVagoes) {

            Solver.output("fail: limite de vagões atingido");
            return;
        }

        vagoes.add(vagao);

    }

    public Pass getPass(String idPass) {

        for (Vagao vagao : vagoes) {

            Pass p = vagao.getPass(idPass);

            if (p != null) {

                return p;

            }

        }

        return null;

    }

    public void embarcar(Pass pass) {

        registro.cadastrar(pass);

        boolean verifica = true;

        for (Vagao vagao : vagoes) {

            if (vagao.search(pass.getId())) {

                Solver.output("fail: " + pass.getId() + " já está no trem");
                return;

            }

        }

        for (Vagao vagao : vagoes) {

            if (!vagao.isFull()) {

                Movimento mov = new Movimento(pass, Direcao.IN);

                registro.addMov(mov);

                vagao.embarcar(pass);
                verifica = false;
                break;

            }

        }

        if (verifica == true) {

            Solver.output("fail: trem lotado");
            return;

        }

    }

    public void desembarcar(String idPass) {

        boolean verifica = false;
        int contador = 0;
        int tamanho = vagoes.size();

        for (Vagao vagao : vagoes) {

            contador++;

            if (vagao.search(idPass)) {

                verifica = true;

            }

            if (contador == tamanho && verifica == false) {

                Solver.output("fail: " + idPass + " nao esta no trem");
                return;

            }

            vagao.desembarcar(idPass);

        }

        Movimento mov = new Movimento(getPass(idPass), Direcao.OUT);
        registro.addMov(mov);

    }

}

enum Direcao {

    IN("in"),
    OUT("out");

    private String label;

    Direcao(String label) {

        this.label = label;

    }

    public String toString() {
        // return String.format("%s", this.getLabel());

        // String label = "in";
        // if (this.name().equals("OUT")) {
        // label = "out";
        // }

        // String.format("%s", label);

        switch (this.name()) {
            case "IN":
                return "in";
            case "OUT":
                return "out";
            default:
                return "fail";
        }

    }

    // Métodos gets

    public String getLabel() {

        return this.label;

    }

}

class Movimento {

    private Pass pass;
    private Direcao dir;

    Movimento(Pass pass, Direcao dir) {

        this.pass = pass;
        this.dir = dir;

    }

    public String toString() {

        // return String.format("%s %s", this.pass, this.dir);
        return this.pass + " " + this.dir;

    }

    // Métoos gets

    public String getNome() {

        return this.pass.getId();

    }

}

class Registro {

    private static ArrayList<Pass> cadastro;
    private static ArrayList<Movimento> movimentacao;

    Registro() {

        cadastro = new ArrayList<Pass>();
        movimentacao = new ArrayList<Movimento>();

    }

    public String toString() {

        return "";

    }

    public String outMov() {

        String string = "";

        for (Movimento mov : movimentacao) {
            string += mov + "\n";
        }

        return string;

    }

    public String outCad() {

        Collections.sort(cadastro, Comparator.comparing(Pass::getId));

        String string = "";

        for (Pass pass : cadastro) {
            string += pass + "\n";
        }

        return string;

    }

    public void cadastrar(Pass pass) {

        for (Pass p : cadastro) {
            if (p.getId().equals(pass.getId())) {
                return;
            }
        }

        cadastro.add(pass);

    }

    public void addMov(Movimento mov) {

        movimentacao.add(mov);

    }

}

class Solver1 {

    public static void main(String[] arg) {

        Trem trem = new Trem(1);

        Registro registro = new Registro();

        while (true) {

            var line = input();
            output("$" + line);
            var args = line.split(" ");

            if (args[0].equals("end")) {
                break;
            } else if (args[0].equals("init")) {
                trem = new Trem((int) number(args[1]));
            } else if (args[0].equals("entrarPes")) {
                trem.embarcar(new Pessoa(args[1]));
            } else if (args[0].equals("entrarCar")) {
                trem.embarcar(new Carga(args[1], number(args[2])));
            } else if (args[0].equals("sair")) {
                trem.desembarcar(args[1]);
            } else if (args[0].equals("nwvag")) {
                trem.addVagao(new Vagao((int) number(args[1])));
            } else if (args[0].equals("show")) {
                output(trem);
            } else if (args[0].equals("la")) {
                output(trem);
            } else if (args[0].equals("movimentacao")) {
                print(registro.outMov());
            } else if (args[0].equals("cadastro")) {
                print(registro.outCad());
            }

        }

    }

    public static Scanner in = new Scanner(System.in);

    public static String input() {
        return in.nextLine();
    }

    public static void print(Object value) {
        System.out.print(value);
    }

    public static void output(Object value) {
        System.out.println(value);
    }

    public static float number(String value) {
        return Float.parseFloat(value);
    }

}