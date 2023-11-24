import java.util.Scanner;

class Paciente {
    int identificador;
    String nome;
    float altura;
    int idade;
    float peso;
}

public class PontuarioMedico {

    static void imprimirMenu() {
        System.out.println("---------PONTUARIO MEDICO---------");
        System.out.println("Digite 1: Inicializar o pontuario.");
        System.out.println("Digite 2: imprimir um paciente.");
        System.out.println("Digite 3: inserir um paciente.");
        System.out.println("Digite 4: editar um paciente.");
        System.out.println("Digite 5: Sair.");
        System.out.println("------------------------------------");
    }

    static void inicializarPontuario(Paciente pontuario[], int tam) {
        for (int i = 0; i < tam; i++) {
            pontuario[i].identificador = -1;
        }
        System.out.println("pontuario medico inicializado com sucesso!");
    }

    static Paciente lerPaciente(Scanner scanner) {
        Paciente paciente = new Paciente();

        System.out.println("Digite o indentificador do paciente: ");
        paciente.identificador = scanner.nextInt();

        System.out.println("Digite o seu nome: ");
        paciente.nome = scanner.next();

        System.out.println("Digite a sua altura: ");
        paciente.altura = scanner.nextFloat();

        System.out.println("Digite a sua idade: ");
        paciente.idade = scanner.nextInt();

        System.out.println("Digite o seu peso: ");
        paciente.peso = scanner.nextFloat();

        return paciente;
    }

    static void imprimirPaciente(Paciente paciente) {
        System.out.println("--------Paciente----------");
        System.out.println("Indentificador: " + paciente.identificador);
        System.out.println("Nome: " + paciente.nome);
        System.out.println("Altura: " + paciente.altura);
        System.out.println("Idade: " + paciente.idade);
        System.out.println("Peso: " + paciente.peso);
        System.out.println("--------------------------");
    }

    static void adicionarpaciente(Paciente pontuario[], int tam, Scanner scanner) {
        System.out.println("Adicionando pacientes no pontuario medico...");

        int adicionou = 0;

        for (int i = 0; i < tam; i++) {
            if (pontuario[i].identificador == -1) {
                Paciente pacienteAdicionar = lerPaciente(scanner);
                pontuario[i] = pacienteAdicionar;
                adicionou = 1;
                break;
            }
        }
        if (adicionou == 1)
            System.out.println("Paciente adicionado no pontuario com sucesso!");
        else
            System.out.println("Paciente nao adicionado no pontuario! Pontuario cheio.");
    }

    static void imprimirPontuario(Paciente pontuario[], int tam) {
        System.out.println("Imprimindo pontuario medico...");
        int imprimiu = 0;

        for (int i = 0; i < tam; i++) {
            if (pontuario[i].identificador != -1) {
                imprimirPaciente(pontuario[i]);
                imprimiu = 1;
            }
        }
        if (imprimiu == 0)
            System.out.println("Pontuario medico vazio! Adicione um paciente.");
    }

    static void editarPacienteNome(Paciente pontuario[], int tam, String editarNome) {
        int editou = 0;

        for (int i = 0; i < tam; i++) {
            if (pontuario[i].nome.equals(editarNome)) {
                System.out.println("Digite o novo nome: ");
                Scanner scanner = new Scanner(System.in);
                pontuario[i].nome = scanner.next();
                editou = 1;
                break;
            }
        }
        if (editou == 1)
            System.out.println("Nome editado com sucesso!");
        else
            System.out.println("Nome nao encontrado no pontuario!");
    }

    static void editarPacienteAltura(Paciente pontuario[], int tam, String editarAltura) {
        int editou = 0;

        for (int i = 0; i < tam; i++) {
            if (pontuario[i].nome.equals(editarAltura)) {
                System.out.println("Digite a nova altura: ");
                Scanner scanner = new Scanner(System.in);
                pontuario[i].altura = scanner.nextFloat();
                editou = 1;
                break;
            }
        }
        if (editou == 1)
            System.out.println("Altura editada com sucesso!");
        else
            System.out.println("Altura nao encontrada no pontuario!");
    }

    static void editarPacienteIdade(Paciente pontuario[], int tam, String editarIdade) {
        int editou = 0;

        for (int i = 0; i < tam; i++) {
            if (pontuario[i].nome.equals(editarIdade)) {
                System.out.println("Digite a nova idade: ");
                Scanner scanner = new Scanner(System.in);
                pontuario[i].idade = scanner.nextInt();
                editou = 1;
                break;
            }
        }
        if (editou == 1)
            System.out.println("Idade editada com sucesso!");
        else
            System.out.println("Idade nao encontrada no pontuario!");
    }

    static void editarPacientePeso(Paciente pontuario[], int tam, String editarPeso) {
        int editou = 0;

        for (int i = 0; i < tam; i++) {
            if (pontuario[i].nome.equals(editarPeso)) {
                System.out.println("Digite o novo peso: ");
                Scanner scanner = new Scanner(System.in);
                pontuario[i].peso = scanner.nextFloat();
                editou = 1;
                break;
            }
        }
        if (editou == 1)
            System.out.println("Peso editado com sucesso!");
        else
            System.out.println("Peso nao encontrado no pontuario!");
    }

    static void editarPaciente(Paciente pontuario[], int tam) {
        System.out.println("Digite o nome do paciente que deseja editar: ");
        Scanner scanner = new Scanner(System.in);
        String editarNome = scanner.next();

        System.out.println("Digite o que deseja editar: ");
        System.out.println("Digite 1: Nome.");
        System.out.println("Digite 2: Altura.");
        System.out.println("Digite 3: Idade.");
        System.out.println("Digite 4: Peso.");
        int opcao = scanner.nextInt();

        switch (opcao) {
            case 1:
                editarPacienteNome(pontuario, tam, editarNome);
                break;
            case 2:
                editarPacienteAltura(pontuario, tam, editarNome);
                break;
            case 3:
                editarPacienteIdade(pontuario, tam, editarNome);
                break;
            case 4:
                editarPacientePeso(pontuario, tam, editarNome);
                break;
            default:
                System.out.println("Opcao invalida!");
                break;
        }
    }

    static void removerPaciente(Paciente pontuario[], int tam) {
        System.out.println("Digite o nome do paciente que deseja remover: ");
        Scanner scanner = new Scanner(System.in);
        String removerNome = scanner.next();

        int removeu = 0;

        for (int i = 0; i < tam; i++) {
            if (pontuario[i].nome.equals(removerNome)) {
                pontuario[i].identificador = -1;
                removeu = 1;
                break;
            }
        }
        if (removeu == 1)
            System.out.println("Paciente removido com sucesso!");
        else
            System.out.println("Paciente nao encontrado no pontuario!");
    }

    public static void main(String[] args) {
        int tam = 10;
        Paciente pontuario[] = new Paciente[tam];

        for (int i = 0; i < tam; i++) {
            pontuario[i] = new Paciente();
            pontuario[i].identificador = -1;
        }

        int opcao = 0;
        Scanner scanner = new Scanner(System.in);

        while (opcao != 6) {
            System.out.println("Digite a opcao desejada: ");
            System.out.println("Digite 1: Adicionar paciente.");
            System.out.println("Digite 2: Imprimir pontuario.");
            System.out.println("Digite 3: Editar paciente.");
            System.out.println("Digite 4: Remover paciente.");
            System.out.println("Digite 5: Sair.");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    adicionarpaciente(pontuario, tam, scanner);
                    break;
                case 2:
                    imprimirPontuario(pontuario, tam);
                    break;
                case 3:
                    editarPaciente(pontuario, tam);
                    break;
                case 4:
                    removerPaciente(pontuario, tam);
                    break;
                case 5:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opcao invalida!");
                    break;
            }
        }

        scanner.close();
    }
}
