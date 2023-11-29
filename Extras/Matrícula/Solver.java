
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Discp {
    String id;
    Map<String, Aluno> alunos;

    Discp(String id) {
        this.id = id;
        this.alunos = new HashMap<String, Aluno>();
    }

    public String getId() {
        return id;
    }

    public List<Aluno> getAlunos() {
        return new ArrayList<Aluno>(alunos.values());
    }

    @Override
    public String toString() {
        ArrayList<Aluno> alunos = new ArrayList<Aluno>(this.alunos.values());
        Collections.sort(alunos, (a1, a2) -> a1.getId().compareTo(a2.getId()));
        String saida = id + " [";
        for (int i = 0; i < alunos.size(); i++) {
            if (i == alunos.size() - 1) {
                saida += alunos.get(i).getId();
            } else {
                saida += alunos.get(i).getId() + ", ";
            }
        }
        saida += "]";
        return saida;
    }

    public void addAluno(Aluno aluno) {
        alunos.put(aluno.getId(), aluno);
    }

    public void rmAluno(String idAluno) {
        alunos.remove(idAluno);
    }
}

class Aluno {
    String id;
    Map<String, Discp> disp;

    Aluno(String id) {
        this.id = id;
        this.disp = new HashMap<String, Discp>();
    }

    public String getId() {
        return id;
    }

    public List<Discp> getDisp() {
        return new ArrayList<Discp>(disp.values()); // Construtor de cópia para coleções
    }

    public void addDiscp(Discp discp) {
        disp.put(discp.getId(), discp);
    }

    public void rmDisp(String id) {
        disp.remove(id);
    }

    @Override
    public String toString() {
        ArrayList<Discp> disp = new ArrayList<Discp>(this.disp.values());
        Collections.sort(disp, (d1, d2) -> d1.getId().compareTo(d2.getId()));

        String saida = id + " [";
        for (int i = 0; i < disp.size(); i++) {
            if (i == disp.size() - 1) {
                saida += disp.get(i).getId();
            } else {
                saida += disp.get(i).getId() + ", ";
            }
        }
        saida += "]";
        return saida;
    }
}

class Sistema {
    Map<String, Aluno> alunos;
    Map<String, Discp> discp;

    Sistema() {
        this.alunos = new HashMap<String, Aluno>();
        this.discp = new HashMap<String, Discp>();
    }

    @Override
    public String toString() {
        String saida = "- alunos\n";
        List<Aluno> lista = new ArrayList<Aluno>(alunos.values());
        Collections.sort(lista, (a1, a2) -> a1.getId().compareTo(a2.getId()));
        for (Aluno aluno : lista) {
            saida += aluno + "\n";
        }
        saida += "- discps\n";
        List<Discp> lista2 = new ArrayList<Discp>(discp.values());
        Collections.sort(lista2, (d1, d2) -> d1.getId().compareTo(d2.getId()));
        for (Discp discp : lista2) {
            // Se for o ultimo nao da /n
            saida += discp + "\n";
        }
        // remove o ultimo \n
        saida = saida.substring(0, saida.length() - 1);
        return saida;
    }

    public void addAluno(String idAluno) {
        alunos.put(idAluno, new Aluno(idAluno));
    }

    public void addDiscp(String idDiscp) {
        discp.put(idDiscp, new Discp(idDiscp));

    }

    public List<Aluno> getAlunos() {
        return new ArrayList<Aluno>(alunos.values());
    }

    public List<Discp> getDiscp() {
        return new ArrayList<Discp>(discp.values());
    }

    public void matricular(String idAluno, String idDiscp) {
        Aluno aluno = alunos.get(idAluno);
        Discp discp = this.discp.get(idDiscp);
        aluno.addDiscp(discp);
        discp.addAluno(aluno);
    }

    public void desmatricular(String idAluno, String idDiscp) {
        Aluno aluno = alunos.get(idAluno);
        Discp discp = this.discp.get(idDiscp);
        aluno.rmDisp(idDiscp);
        discp.rmAluno(idAluno);
    }

    public void removerAluno(String idAluno) {
        Aluno aluno = alunos.get(idAluno);
        for (Discp discp : aluno.getDisp()) {
            discp.rmAluno(idAluno);
        }
        alunos.remove(idAluno);
    }

    public void removerDiscp(String idDiscp) {
        Discp discp = this.discp.get(idDiscp);
        for (Aluno aluno : discp.getAlunos()) {
            aluno.rmDisp(idDiscp);
        }
        this.discp.remove(idDiscp);
    }
}

public class Solver {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Sistema sistema = new Sistema();

        while (true) {
            String line = input();
            System.out.println("$" + line);
            String[] ui = line.split(" ");

            try {
                switch (ui[0]) {
                    case "end":
                        return;

                    case "nwalu":
                        for (int i = 1; i < ui.length; i++) {
                            sistema.addAluno(ui[i]);
                        }
                        break;

                    case "nwdis":
                        for (int i = 1; i < ui.length; i++) {
                            sistema.addDiscp(ui[i]);
                        }
                        break;

                    case "show":
                        System.out.println(sistema);
                        break;

                    case "tie":
                        for (int i = 2; i < ui.length; i++) {
                            sistema.matricular(ui[1], ui[i]);
                        }
                        break;

                    case "untie":
                        for (int i = 2; i < ui.length; i++) {
                            sistema.desmatricular(ui[1], ui[i]);
                        }
                        break;

                    case "rmalu":
                        sistema.removerAluno(ui[1]);
                        break;

                    case "rmdis":
                        sistema.removerDiscp(ui[1]);
                        break;

                    default:
                        System.out.println("fail: comando invalido");
                        break;
                }
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static Scanner scanner = new Scanner(System.in);

    private static String input() {
        return scanner.nextLine();
    }

    public static void println(Object value) {
        System.out.println(value);
    }

    public static void print(Object value) {
        System.out.print(value);
    }
}