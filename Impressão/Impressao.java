import java.util.Scanner;

class Aluno {
    String nome;
    int matr;
    String disc;
    float nota;

    void ler() {
        nome = input();
        matr = Integer.parseInt(input());
        disc = input();
        nota = Float.parseFloat(input());
    }

    void imprimir() {
        println("Nome = " + nome);
        println("Matrícula = " + matr);
        println("Disciplina = " + disc);
        println("Nota = " + nota);
    }

    static Scanner in = new Scanner(System.in);

    String input() {
        return in.nextLine();
    }

    void print(String str) {
        System.out.print(str);
    }

    void println(String str) {
        System.out.println(str);
    }
}

public class Impressao {
    public static void main(String[] arg) {
        Aluno a1 = new Aluno();

        a1.ler();

        a1.imprimir();
    }
}
