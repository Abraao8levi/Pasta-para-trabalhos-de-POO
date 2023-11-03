import java.util.*;
import java.text.DecimalFormat;

class Client {
    private String clientId;
    private ArrayList<Account> accounts;

    public Client( String clientId ) {
        this.clientId = clientId;
        this.accounts = new ArrayList<Account>();
    }

    public void addAccount( Account acc ) {
        this.accounts.add( acc );
    }

    public ArrayList<Account> getAccounts() {
        return this.accounts;
    }
    public String getClientId() {
        return this.clientId;
    }

    @Override
    public String toString() {
        // Almir [0, 1]
        return this.clientId + " [" + this.accounts.get(0).getAccId()
                             + ", " + this.accounts.get(1).getAccId() + "]\n";
    }
}

abstract class Account {
    protected double balance;
    private static int nextAccountId = 0;
    protected int accId;
    protected String clientId;
    protected String typeId;

    public Account( String clientId, String typeId ) {
        this.balance = 0;
        this.accId = nextAccountId++;
        this.clientId = clientId;
        this.typeId = typeId;
    }

    public void deposit( double value ) {
        this.balance += value;
    }
    public void withdraw( double value ) throws Exception {
        if ( value > this.balance ) {
            throw new Exception("fail: saldo insuficiente");
        }

        this.balance -= value;
    }
    public void transfer( Account other, double value ) throws Exception {
        this.withdraw( value );
        other.deposit( value );
    }

    @Override
    public String toString() {
        DecimalFormat d = new DecimalFormat("0.00"); //double x = 4.3; System.out.println( d.format(x) ); //4.30
        // 0:Almir:0.00:CC
        return this.accId + ":" + this.clientId
                          + ":" + d.format(this.balance)
                          + ":" + this.typeId + "\n";
    }

    public double getBalance() {
        return this.balance;
    }
    public int getAccId() {
        return this.accId;
    }
    public String getClientId() {
        return this.clientId;
    }
    public String getTypeId() {
        return this.typeId;
    }

    public abstract void updateMonthly();
}

class CheckingAccount extends Account {
    protected double monthlyFee;
    
    public CheckingAccount( String clientId ) {
        super( clientId, "CC" );
        this.monthlyFee = 20.0;
    }
    
    @Override
    public void updateMonthly() {
        this.balance -= this.monthlyFee;
    }
}

class SavingsAccount extends Account {
    protected double monthlyInterest;
    
    public SavingsAccount( String clientId ) {
        super( clientId, "CP" );
        this.monthlyInterest = 0.01;
    }
    
    @Override
    public void updateMonthly() {
        // this.balance += this.monthlyInterest * this.balance;
        this.balance *= (1 + this.monthlyInterest);
    }
}


class Agency {
    private Map<Integer, Account> accounts;
    private Map<String, Client> clients;

    private Account getAccount(int accountId) throws Exception {
        if ( !this.accounts.containsKey(accountId) ) {
            throw new Exception("fail: conta nao encontrada");
        }

        return this.accounts.get( accountId );
    }

    public Agency() {
        this.accounts = new HashMap<Integer,Account>();
        // this.clients = new HashMap<String,Client>();
        this.clients = new LinkedHashMap<String,Client>();
    }

    // cria uma conta para o cliente
    // cria um objeto cliente e insere no mapa de clientes
    // cria uma conta corrente e uma conta poupança e insere no mapa de contas
    // faz o vínculo cruzado colocando as contas dentro do objeto do cliente
    public void addClient(String clientId) {
        Client c = new Client( clientId );
        this.clients.put( clientId, c );
        
        CheckingAccount cc = new CheckingAccount( clientId );
        SavingsAccount cp = new SavingsAccount( clientId );
        this.accounts.put( cc.getAccId(), cc );
        this.accounts.put( cp.getAccId(), cp );
        
        c.addAccount( cc );
        c.addAccount( cp );
    }

    // procura pela conta usando o getAccount e realiza a operação de depósito
    // utiliza o método deposit da classe Account
    public void deposit(int accId, double value) throws Exception {
        this.getAccount( accId ).deposit( value );
    }

    // procura pela conta e realiza a operação de saque
    // utiliza o método withdraw da classe Account
    public void withdraw(int accId, double value) throws Exception {
        this.getAccount( accId ).withdraw( value );
    }

    // procura pela conta e realiza a operação de transferência
    // utiliza o método transfer da classe Account
    public void transfer(int fromAccId, int toAccId, double value) throws Exception {
        this.getAccount( fromAccId ).transfer( this.getAccount( toAccId ), value );
    }

    // realiza a operação de atualização mensal em todas as contas
    public void updateMonthly() {
        for ( Account acc : this.accounts.values() ) {
            acc.updateMonthly();
        }
    }

    @Override
    public String toString() {
        String s = "- Clients\n";
        for ( Client client : this.clients.values() ) {
            s += client;
        }
        s += "- Accounts\n";
        for ( Account acc : this.accounts.values() ) {
            s += acc;
        }
        return s;
    }
}
public class Solver {
    public static void main(String[] arg) {
        Agency agency = new Agency();

        while (true) {
            String line = input();
            println("$" + line);
            String[] args = line.split(" ");

            try {
                if      (args[0].equals("end"))       { break; }
                else if (args[0].equals("show"))      { print(agency); }
                else if (args[0].equals("addCli"))    { agency.addClient( args[1] ); }
                else if (args[0].equals("deposito"))  { agency.deposit( (int) number(args[1]), number(args[2]) ); }
                else if (args[0].equals("saque"))     { agency.withdraw( (int) number(args[1]), number(args[2]) ); }
                else if (args[0].equals("transf"))    { agency.transfer( (int) number(args[1]), (int) number(args[2]), number(args[3]) ); }
                else if (args[0].equals("update"))    { agency.updateMonthly(); }
                else                                { println("fail: comando invalido"); }
            } catch (Exception e) {
                println( e.getMessage() );
            }
        }
    }

    private static Scanner scanner = new Scanner(System.in);
    private static String  input()                { return scanner.nextLine();        }
    private static double  number(String value)   { return Double.parseDouble(value); }
    public  static void    println(Object value)  { System.out.println(value);        }
    public  static void    print(Object value)    { System.out.print(value);          }
}