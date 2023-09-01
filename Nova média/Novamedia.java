
import java.util.Scanner;

class Aluno {
    String nome;
    float notas[];
    
    void ler() {
        nome = input();
        notas = new float[3];
        for (int i=0; i<3; i++) {
            notas[i] = Float.parseFloat( input() );
        }
    }
    
    float media() {
        float soma = 0;
        // for (int i=0; i<3; i++) {
        //     float nota = notas[i];
        for (float nota : notas) {
            soma += nota;
        }
        float med = soma/3;
        return med;
    }

    Scanner scan = new Scanner(System.in);
    String input() { return scan.nextLine(); }
}

class Novamedia {
    public static void main(String args[]) {
        Aluno alu = new Aluno();
        alu.ler();
        float med = alu.media();
        System.out.printf("%.2f",med);
    }
}




    
