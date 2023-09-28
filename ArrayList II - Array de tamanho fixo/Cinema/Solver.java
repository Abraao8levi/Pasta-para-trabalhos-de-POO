
import java.util.*;

class Client {
    private String id;
    private String fone;

    public Client(String id, String fone) {
        this.id = id;
        this.fone = fone;
    }

    @Override
    public String toString() {
        return id + ":" + fone;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFone() {
        return this.fone;
    }

    public void setFone(String fone) {
        this.fone = fone;
    }
}

class Sala {
    private List<Client> cadeiras;

    public Sala(int capacidade) {
        cadeiras = new ArrayList<>(capacidade);
        for (int i = 0; i < capacidade; i++) {
            cadeiras.add(null);
        }
    }

    public void reservar(String id, String fone, int pos) {
        if (pos < 0 || pos >= cadeiras.size()) {
            System.out.println("fail: cadeira nao existe");
            return;
        }

        if (cadeiras.get(pos) != null) {
            System.out.println("fail: cadeira ja esta ocupada");
            return;
        }

        for (Client cliente : cadeiras) {
            if (cliente != null && cliente.getId().equals(id)) {
                System.out.println("fail: cliente ja esta no cinema");
                return;
            }
        }

        cadeiras.set(pos, new Client(id, fone));
    }

    public void cancelar(String id) {
        for (int i = 0; i < cadeiras.size(); i++) {
            Client cliente = cadeiras.get(i);
            if (cliente != null && cliente.getId().equals(id)) {
                cadeiras.set(i, null);
                return;
            }
        }
        System.out.println("fail: cliente nao esta no cinema");
    }

@Override
public String toString() {
    StringBuilder saida = new StringBuilder("[");
    for (int i = 0; i < cadeiras.size(); i++) {
        Client cliente = cadeiras.get(i);
        if (cliente == null) {
            saida.append("-");  
        } else {
            saida.append(cliente);
        }
        
       
        if (i < cadeiras.size() - 1) {
            saida.append(" ");
        }
    }
    saida.append("]");
    return saida.toString();
}
}
class Solver {
    static Shell sh = new Shell();
    static Sala sala = new Sala(0);

    public static void main(String args[]) {
        sh.chain.put("init", () -> {
            sala = new Sala(sh.getInt(1));
        });
        sh.chain.put("show", () -> {
            System.out.println(sala);
        });
        sh.chain.put("reservar", () -> {
            sala.reservar(sh.getStr(1), sh.getStr(2), sh.getInt(3));
        });
        sh.chain.put("cancelar", () -> {
            sala.cancelar(sh.getStr(1));
        });

        sh.execute();
    }

    static int getInt(int pos) {
        return Integer.parseInt(sh.param.get(pos));
    }

    static String getStr(int pos) {
        return sh.param.get(pos);
    }
}
class Shell {
    public Scanner scanner = new Scanner(System.in);
    public HashMap<String, Runnable> chain = new HashMap<>();
    public ArrayList<String> param = new ArrayList<>();

    public Shell() {
        Locale.setDefault(new Locale("en", "US"));
    }

    public void execute() {
        while (true) {
            param.clear();
            String line = scanner.nextLine();
            Collections.addAll(param, line.split(" "));
            System.out.println("$" + line);
            if (param.get(0).equals("end")) {
                break;
            } else if (chain.containsKey(param.get(0))) {
                chain.get(param.get(0)).run();
            } else {
                System.out.println("fail: comando invalido");
            }
        }
    }

    public int getInt(int pos) {
        return Integer.parseInt(param.get(pos));
    }

    public String getStr(int pos) {
        return param.get(pos);
    }
}
