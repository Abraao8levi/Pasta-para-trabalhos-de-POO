import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;// Isso é útil ao lidar com dados binários, como chaves criptográficas, que precisam ser representadas de maneira legível em formato de texto.
import java.util.Date;
import java.util.HashMap;
import java.util.InputMismatchException;//  Serve  para tratar as exceções do código.
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.crypto.Cipher; // A classe Cipher fornece funcionalidades para criptografar e descriptografar dados. Ela é a principal classe para operações de criptografia em Java
import javax.crypto.KeyGenerator;// A classe KeyGenerator é usada para gerar chaves secretas para algoritmos de criptografia simétrica. Ela fornece uma maneira fácil de gerar chaves aleatórias que podem ser usadas em conjunto com a classe Cipher para criptografar e descriptografar dados.
import javax.crypto.SecretKey;//  interface SecretKey representa uma chave secreta usada em algoritmos de criptografia simétrica.

// Interface que define as operações disponíveis no estacionamento
interface EstacionamentoInterface {
    // Métodos de cadastro e controle de clientes e funcionários
    void cadastrarCliente(String nome, String cpf);

    void cadastrarFuncionario(String nome, String cpf, String tipoAcesso);

    // Métodos para registro de entrada e saída de veículos
    void registrarEntradaVeiculo(String placa) throws EstacionamentoLotadoException;

    void registrarSaidaVeiculo(String placa) throws VeiculoNaoEncontradoException;

    // Métodos relacionados a cálculos e transações financeiras
    double calcularValorCobrado(double horasEstacionadas);

    void realizarPagamento(double valorCobrado, Veiculo veiculo, Date entrada, Date saida);

    // Métodos de consulta de informações sobre o estacionamento
    int getVagasDisponiveis();

    int getVagasOcupadas();

    double getReceitaTotal();

    // Métodos para geração de relatórios
    void gerarRelatorioTaxaOcupacao();

    void gerarRelatorioFluxoVeiculos();

    void gerarRelatorioReceita();
}

// Classe base para representar uma pessoa
class Pessoa {
    private String nome;
    private String cpf;
    private String cpfCriptografado;

    // Construtor que recebe nome e CPF, e criptografa o CPF
    public Pessoa(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
        try {
            this.cpfCriptografado = criptografar(cpf);
        } catch (Exception e) {
            e.printStackTrace(); // Trate a exceção apropriadamente no seu código
            this.cpfCriptografado = ""; // Atribua uma string vazia em caso de erro (trate isso apropriadamente)
        }
    }

    // Getters para nome, CPF e CPF criptografado
    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getCpfCriptografado() {
        return cpfCriptografado;
    }

    // Método privado para criptografar uma string usando o algoritmo AES
    private String criptografar(String data) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, SecurityExample.getSecretKey());
        byte[] encryptedData = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedData);
    }
}

// Classe que representa um usuário (cliente ou funcionário)
class Usuario extends Pessoa {
    private String tipoAcesso;

    // Construtor que recebe nome, CPF, e tipo de acesso
    public Usuario(String nome, String cpf, String tipoAcesso) {
        super(nome, cpf);
        this.tipoAcesso = tipoAcesso;
    }

    public String getTipoAcesso() {
        return tipoAcesso;
    }
}

// Classe que representa um veículo
class Veiculo {
    private String placa;
    private Date entrada;

    // Construtor que recebe a placa e registra a entrada como a data atual
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

// Classe que representa uma tarifa associada a um tipo de veículo (hora,
// diária, etc.)
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

class Transacao {
    private String placaVeiculo;
    private Date entrada;
    private Date saida;
    private double valorCobrado;

    // Construtor que recebe informações sobre a transação
    public Transacao(String placaVeiculo, Date entrada, Date saida, double valorCobrado) {
        this.placaVeiculo = placaVeiculo;
        this.entrada = entrada;
        this.saida = saida;
        this.valorCobrado = valorCobrado;
    }

    public String getPlacaVeiculo() {
        return placaVeiculo;
    }

    public Date getEntrada() {
        return entrada;
    }

    public Date getSaida() {
        return saida;
    }

    public double getValorCobrado() {
        return valorCobrado;
    }
}

// Classe principal que implementa a interface EstacionamentoInterface
class Estacionamento implements EstacionamentoInterface {
    // Mapas para armazenar pessoas, usuários, e listas para veículos, tarifas e
    // transações
    protected Map<String, Pessoa> pessoas = new HashMap<>();
    protected Map<String, Usuario> usuarios = new HashMap<>();
    protected List<Veiculo> veiculosEstacionados = new ArrayList<>();
    protected List<Tarifa> tarifas = new ArrayList<>();
    protected List<Transacao> historicoTransacoes = new ArrayList<>();
    protected int vagasDisponiveis = 30;

    public void cadastrarCliente(String nome, String cpf) {
        Pessoa cliente = new Pessoa(nome, cpf);
        pessoas.put(cliente.getCpfCriptografado(), cliente);
    }

    public void cadastrarFuncionario(String nome, String cpf, String tipoAcesso) {
        Usuario funcionario = new Usuario(nome, cpf, tipoAcesso);
        usuarios.put(funcionario.getCpfCriptografado(), funcionario);
    }

    public void registrarEntradaVeiculo(String placa) throws EstacionamentoLotadoException {
        if (vagasDisponiveis > 0) {
            Veiculo veiculo = new Veiculo(placa);
            veiculosEstacionados.add(veiculo);
            vagasDisponiveis--;
        } else {
            throw new EstacionamentoLotadoException(
                    "Não é possível registrar a entrada. O estacionamento está lotado. Vagas disponíveis: "
                            + getVagasDisponiveis());
        }
    }

    public void registrarSaidaVeiculo(String placa) throws VeiculoNaoEncontradoException {
        for (Veiculo veiculo : veiculosEstacionados) {
            if (veiculo.getPlaca().equalsIgnoreCase(placa)) {
                Date entrada = veiculo.getEntrada();
                Date saida = new Date();
                long tempoEstacionado = saida.getTime() - entrada.getTime();
                double horasEstacionadas = tempoEstacionado / (1000.0 * 60.0 * 60.0);

                double valorCobrado = calcularValorCobrado(horasEstacionadas);
                realizarPagamento(valorCobrado, veiculo, entrada, saida);

                veiculosEstacionados.remove(veiculo);
                vagasDisponiveis++;
                historicoTransacoes.add(new Transacao(placa, entrada, saida, valorCobrado));
                return;
            }
        }
        throw new VeiculoNaoEncontradoException("Veículo com a placa " + placa
                + " não encontrado no estacionamento. Certifique-se de que a placa está correta e tente novamente.");
    }

    public double calcularValorCobrado(double horasEstacionadas) {
        for (Tarifa tarifa : tarifas) {
            if (tarifa.getTipo().equalsIgnoreCase("Hora")) {
                return tarifa.getValor() * horasEstacionadas;
            } else if (tarifa.getTipo().equalsIgnoreCase("Diária")) {
                return tarifa.getValor() * Math.ceil(horasEstacionadas / 24.0);
            }
        }
        return 0.0;
    }

    public void realizarPagamento(double valorCobrado, Veiculo veiculo, Date entrada, Date saida) {
        double valorPago = 0.0;
        String valorCobradoFormatado = String.format("R$%.2f", valorCobrado);
        System.out.println("Valor a ser pago: R$" + valorCobradoFormatado);
        Scanner scanner = new Scanner(System.in);
        while (valorPago < valorCobrado) {
            try {
                System.out.print("Digite o valor pago: R$");
                double pagamento = scanner.nextDouble();
                valorPago += pagamento;
                if (valorPago < valorCobrado) {
                    System.out.println("Valor insuficiente. Faltam R$" + (valorCobrado - valorPago));
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Certifique-se de inserir um número válido.");
                scanner.next();
            }
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
        }

        double troco = valorPago - valorCobrado;
        if (troco > 0) {
            String trocoFormatado = String.format("R$%.2f", troco);
            System.out.println("Pagamento realizado com sucesso. Troco: R$" + trocoFormatado);
        } else {
            System.out.println("Pagamento exato realizado com sucesso.");
        }
    }

    public int getVagasDisponiveis() {
        return vagasDisponiveis;
    }

    public int getVagasOcupadas() {
        return veiculosEstacionados.size();
    }

    public double getReceitaTotal() {
        double receitaTotal = 0.0;
        for (Transacao transacao : historicoTransacoes) {
            receitaTotal += transacao.getValorCobrado();
        }
        return receitaTotal;
    }

    public void gerarRelatorioTaxaOcupacao() {
        double taxaOcupacao = (double) getVagasOcupadas() / (getVagasDisponiveis() + getVagasOcupadas()) * 100;
        System.out.printf("Taxa de Ocupação: %.2f%%\n", taxaOcupacao);
    }

    public void gerarRelatorioFluxoVeiculos() {
        System.out.println("Total de Veículos Estacionados: " + getVagasOcupadas());
    }

    public void gerarRelatorioReceita() {
        System.out.println("Receita Total: R$" + getReceitaTotal());
    }
}

class EstacionamentoLotadoException extends Exception {
    public EstacionamentoLotadoException(String message) {
        super(message);
    }
}

class VeiculoNaoEncontradoException extends Exception {
    public VeiculoNaoEncontradoException(String message) {
        super(message);
    }
}

class SecurityExample {
    private static SecretKey secretKey = null;

    public static void main(String[] args) throws Exception {
        // Geração da chave secreta
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128); // Tamanho da chave: 128 bits
        secretKey = keyGenerator.generateKey();
    }

    public static SecretKey getSecretKey() {
        return secretKey;
    }

    // Métodos para criptografar e descriptografar usando a chave secreta do
    // estacionamento
    public static String criptografar(String data) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedData = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedData);
    }

    public static String descriptografar(String encryptedData) throws Exception {
        byte[] decodedData = Base64.getDecoder().decode(encryptedData);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedData = cipher.doFinal(decodedData);
        return new String(decryptedData);
    }
}

public class EstacionamentoApp {
    private static Estacionamento estacionamento = new Estacionamento();
    private static final int MAX_CLIENTES = 15;

    public static void main(String[] args) {
        try {
            SecurityExample.main(args);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        estacionamento.tarifas.add(new Tarifa("Hora", 5.0));
        estacionamento.tarifas.add(new Tarifa("Diária", 30.0));

        int escolha;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("+-----  Bem Vindo ao  ParkEase  -----+");
            System.out.println("|                                  |");
            System.out.println("| 1. Cadastrar Cliente             |");
            System.out.println("| 2. Cadastrar Funcionário         |");
            System.out.println("| 3. Registrar Entrada de Veículo  |");
            System.out.println("| 4. Pesquisar Veículos            |");
            System.out.println("| 5. Registrar Saída de Veículo    |");
            System.out.println("| 6. Pesquisar Clientes Cadastrados|");
            System.out.println("| 7. Pesquisar Funcionários Cadastrados |");
            System.out.println("| 8. Tabela de Preços e Horas      |");
            System.out.println("| 9. Gerar Relatório de Taxa de Ocupação  |");
            System.out.println("| 10. Gerar Relatório de Fluxo de Veículos |");
            System.out.println("| 11. Gerar Relatório de Receita Total     |");
            System.out.println("| 12. Sair                         |");
            System.out.println("+------------------------------------+");
            System.out.print(" Escolha uma opção: ");
            if (scanner.hasNextInt()) {
                escolha = scanner.nextInt();
                scanner.nextLine();

                switch (escolha) {
                    case 1:
                        if (estacionamento.pessoas.size() >= MAX_CLIENTES) {
                            System.out.println("Número máximo de clientes cadastrados atingido.");
                        } else {
                            cadastrarCliente(scanner);
                        }
                        break;
                    case 2:
                        cadastrarFuncionario(scanner);
                        break;
                    case 3:
                        registrarEntradaVeiculo(scanner);
                        break;
                    case 4:
                        pesquisarVeiculos();
                        break;
                    case 5:
                        registrarSaidaVeiculo(scanner);
                        break;
                    case 6:
                        pesquisarClientesCadastrados();
                        break;
                    case 7:
                        pesquisarFuncionariosCadastrados();
                        break;
                    case 8:
                        exibirTabelaPrecosHoras();
                        break;
                    case 9:
                        estacionamento.gerarRelatorioTaxaOcupacao();
                        break;
                    case 10:
                        estacionamento.gerarRelatorioFluxoVeiculos();
                        break;
                    case 11:
                        estacionamento.gerarRelatorioReceita();
                        break;
                    case 12:
                        System.out.println("| Saindo do sistema. Até logo! |");
                        return;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } else {
                System.out.println("Opção inválida. Tente novamente.");
                scanner.nextLine();
                escolha = 0;
            }
        }
    }

    private static void cadastrarCliente(Scanner scanner) {
        System.out.print("Digite o nome do cliente: ");
        String nome = scanner.nextLine();
        System.out.print("Digite o CPF do cliente: ");
        String cpf = scanner.nextLine();

        estacionamento.cadastrarCliente(nome, cpf);
        System.out.println("Cliente cadastrado com sucesso!");
    }

    private static void cadastrarFuncionario(Scanner scanner) {
        System.out.print("Digite o nome do funcionário: ");
        String nome = scanner.nextLine();
        System.out.print("Digite o CPF do funcionário: ");
        String cpf = scanner.nextLine();
        System.out.print("Digite o tipo de acesso (funcionário ou administrador): ");
        String tipoAcesso = scanner.nextLine();

        estacionamento.cadastrarFuncionario(nome, cpf, tipoAcesso);
        System.out.println("Funcionário cadastrado com sucesso!");
    }

    private static void registrarEntradaVeiculo(Scanner scanner) {
        System.out.print("Digite a placa do veículo: ");
        String placa = scanner.nextLine();

        try {
            estacionamento.registrarEntradaVeiculo(placa);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            System.out.println("Entrada registrada com sucesso em " + sdf.format(estacionamento.veiculosEstacionados
                    .get(estacionamento.veiculosEstacionados.size() - 1).getEntrada()));
        } catch (EstacionamentoLotadoException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void registrarSaidaVeiculo(Scanner scanner) {
        System.out.print("Digite a placa do veículo que está saindo: ");
        String placa = scanner.nextLine();

        try {
            estacionamento.registrarSaidaVeiculo(placa);
            System.out.println("Saída registrada com sucesso.");
        } catch (VeiculoNaoEncontradoException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void exibirTabelaPrecosHoras() {
        System.out.println("\nTabela de Preços e Horas:");
        for (Tarifa tarifa : estacionamento.tarifas) {
            System.out.println("Tipo: " + tarifa.getTipo());
            System.out.println("Valor por Hora: R$" + tarifa.getValor());
            System.out.println("-----------------------");
        }
    }

    private static void pesquisarVeiculos() {
        System.out.println("\nLista de Veículos Estacionados:");
        for (Veiculo veiculo : estacionamento.veiculosEstacionados) {
            System.out.println("Placa: " + veiculo.getPlaca());
            System.out.println("Entrada: " + veiculo.getEntrada());
            System.out.println("-----------------------");
        }
    }

    private static void pesquisarClientesCadastrados() {
        System.out.println("\nLista de Clientes Cadastrados:");
        for (Pessoa pessoa : estacionamento.pessoas.values()) {
            System.out.println("Nome: " + pessoa.getNome());
            System.out.println("CPF: " + pessoa.getCpf());
            System.out.println("-----------------------");
        }
    }

    private static void pesquisarFuncionariosCadastrados() {
        System.out.println("\nLista de Funcionários Cadastrados:");
        for (Usuario usuario : estacionamento.usuarios.values()) {
            System.out.println("Nome: " + usuario.getNome());
            System.out.println("CPF: " + usuario.getCpf());
            System.out.println("Tipo de Acesso: " + usuario.getTipoAcesso());
            System.out.println("-----------------------");
        }
    }
}
