import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;

class Pessoa {
    private String nome;
    private String cpf;

    public Pessoa(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }
}

class Usuario extends Pessoa {
    private String tipoAcesso;

    public Usuario(String nome, String cpf, String tipoAcesso) {
        super(nome, cpf);
        this.tipoAcesso = tipoAcesso;
    }

    public String getTipoAcesso() {
        return tipoAcesso;
    }
}

class Veiculo {
    private String placa;
    private Date entrada;

    public Veiculo(String placa) {
        this.placa = placa;
        this.entrada = new Date();
    }

    public String getPlaca() {
        return placa;
    }

    public Date getEntrada() {
        return entrada;
    }
}

class Tarifa {
    private String tipo;
    private double valor;

    public Tarifa(String tipo, double valor) {
        this.tipo = tipo;
        this.valor = valor;
    }

    public String getTipo() {
        return tipo;
    }

    public double getValor() {
        return valor;
    }
}

public class EstacionamentoApp {
    private static List<Pessoa> pessoas = new ArrayList<>();
    private static List<Usuario> usuarios = new ArrayList<>();
    private static List<Veiculo> veiculosEstacionados = new ArrayList<>();
    private static List<Tarifa> tarifas = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        tarifas.add(new Tarifa("Hora", 10.0)); // Exemplo de tarifa por hora
        tarifas.add(new Tarifa("Diaria", 50.0)); // Exemplo de tarifa por diária

        int escolha;
        do {
            System.out.println("\nSistema de Estacionamento - Menu");
            System.out.println("1. Cadastrar Cliente");
            System.out.println("2. Cadastrar Funcionário");
            System.out.println("3. Registrar Entrada de Veículo");
            System.out.println("4. Registrar Saída de Veículo");
            System.out.println("5. Listar Clientes Cadastrados");
            System.out.println("6. Listar Funcionários Cadastrados");
            System.out.println("7. Listar Tarifas");
            System.out.println("8. Sair");
            System.out.print("Escolha uma opção: ");
            
            if (scanner.hasNextInt()) {
                escolha = scanner.nextInt();
                scanner.nextLine(); // Consumir a quebra de linha
                switch (escolha) {
                    case 1:
                        cadastrarCliente();
                        break;
                    case 2:
                        cadastrarFuncionario();
                        break;
                    case 3:
                        registrarEntradaVeiculo();
                        break;
                    case 4:
                        registrarSaidaVeiculo();
                        break;
                    case 5:
                        listarClientes();
                        break;
                    case 6:
                        listarFuncionarios();
                        break;
                    case 7:
                        listarTarifas();
                        break;
                    case 8:
                        System.out.println("Saindo do sistema. Até logo!");
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } else {
                System.out.println("Opção inválida. Tente novamente.");
                scanner.nextLine(); // Consumir a entrada inválida
                escolha = 0; // Definir escolha como 0 para continuar o loop
            }
        } while (escolha != 8);
        
        scanner.close();
    }

    private static void cadastrarCliente() {
        System.out.print("Digite o nome do cliente: ");
        String nome = scanner.nextLine();
        System.out.print("Digite o CPF do cliente: ");
        String cpf = scanner.nextLine();

        Pessoa cliente = new Pessoa(nome, cpf);
        pessoas.add(cliente);

        System.out.println("Cliente cadastrado com sucesso!");
    }

    private static void cadastrarFuncionario() {
        System.out.print("Digite o nome do funcionário: ");
        String nome = scanner.nextLine();
        System.out.print("Digite o CPF do funcionário: ");
        String cpf = scanner.nextLine();
        System.out.print("Digite o tipo de acesso (funcionário ou administrador): ");
        String tipoAcesso = scanner.nextLine();

        Usuario funcionario = new Usuario(nome, cpf, tipoAcesso);
        usuarios.add(funcionario);

        System.out.println("Funcionário cadastrado com sucesso!");
    }

    private static void registrarEntradaVeiculo() {
        System.out.print("Digite a placa do veículo: ");
        String placa = scanner.nextLine();

        Veiculo veiculo = new Veiculo(placa);
        veiculosEstacionados.add(veiculo);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        System.out.println("Entrada registrada com sucesso em " + sdf.format(veiculo.getEntrada()));
    }

    private static void registrarSaidaVeiculo() {
        System.out.print("Digite a placa do veículo que está saindo: ");
        String placa = scanner.nextLine();

        for (Veiculo veiculo : veiculosEstacionados) {
            if (veiculo.getPlaca().equalsIgnoreCase(placa)) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date entrada = veiculo.getEntrada();
                Date saida = new Date();
                long tempoEstacionado = saida.getTime() - entrada.getTime();
                double horasEstacionadas = tempoEstacionado / (1000.0 * 60.0 * 60.0);

                System.out.println("Saída registrada com sucesso em " + sdf.format(saida));
                System.out.println("Tempo estacionado: " + horasEstacionadas + " horas");

                // Calcular o valor a ser cobrado com base na tarifa
                double valorCobrado = calcularValorCobrado(horasEstacionadas);
                System.out.println("Valor a ser cobrado: R$" + valorCobrado);

                veiculosEstacionados.remove(veiculo);
                return;
            }
        }
        System.out.println("Veículo com a placa " + placa + " não encontrado no estacionamento.");
    }

    private static double calcularValorCobrado(double horasEstacionadas) {

        for (Tarifa tarifa : tarifas) {
            if (tarifa.getTipo().equalsIgnoreCase("Hora")) {
                return tarifa.getValor() * horasEstacionadas;
            } else if (tarifa.getTipo().equalsIgnoreCase("Diaria")) {
                // Arredonda para cima para a diária completa.
                return tarifa.getValor() * Math.ceil(horasEstacionadas / 24.0);
            }
           
        }
        return 0.0; // Tarifa não encontrada, valor padrão.
    }

    private static void listarClientes() {
        System.out.println("\nLista de Clientes Cadastrados:");
        for (Pessoa pessoa : pessoas) {
            System.out.println("Nome: " + pessoa.getNome());
            System.out.println("CPF: " + pessoa.getCpf());
            System.out.println("-----------------------");
        }
    }

    private static void listarFuncionarios() {
        System.out.println("\nLista de Funcionários Cadastrados:");
        for (Usuario usuario : usuarios) {
            System.out.println("Nome: " + usuario.getNome());
            System.out.println("CPF: " + usuario.getCpf());
            System.out.println("Tipo de Acesso: " + usuario.getTipoAcesso());
            System.out.println("-----------------------");
        }
    }

    private static void listarTarifas() {
        System.out.println("\nLista de Tarifas:");
        for (Tarifa tarifa : tarifas) {
            System.out.println("Tipo: " + tarifa.getTipo());
            System.out.println("Valor: R$" + tarifa.getValor());
            System.out.println("-----------------------");
        }
    }
}
