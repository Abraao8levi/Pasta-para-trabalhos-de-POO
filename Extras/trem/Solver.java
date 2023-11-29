import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Scanner;

class Passageiro {
    
 private String id;
 
 Passageiro(String id) {

     setId(id);
 }
    
@Override
public String toString() {
    return getId();
}
public  String getId() {
    return this.id;
}    
    
public void setId(String id) {
    this.id = id; 
  }    
    
}

class Vagao {
     
  private LinkedList<Passageiro> cadeiras;
  private int capacidade;
  
  Vagao(int capacidade) {
      
      cadeiras = new LinkedList<Passageiro>();
      this.capacidade = capacidade;
      
      for(int i = 0; i < this.capacidade; i ++) { cadeiras.add(null); }
  }
    
   @Override
   public String toString() {
       
       String V = "[ ";
       for(Passageiro pass : cadeiras) {
          V += ( (pass!=null) ? pass : "-" ) + " ";
       }
       
       return V + "]";
   }
   public void embarcar(Passageiro pass) {
       int tamanho = cadeiras.size();
       for(int i = 0; i < tamanho; i++) {
           Passageiro cadeira = cadeiras.get(i);
           if(cadeira == null) {
               cadeiras.set(i,pass);
               break;
           }
       }
   }
 
  public void desembarcar(String idPass) {
     
     int tamanho = cadeiras.size();
     for(int i = 0; i < tamanho; i++) {
         Passageiro cadeira = cadeiras.get(i);
         if ( cadeira != null) {
             if(cadeira.getId().equals(idPass)) {
              cadeiras.set(i, null);
              break;
             }
         }
     }
 } 
    public boolean search(String idPass) {
        for(Passageiro pass : cadeiras){
            
            if ( pass != null ) {
                if(pass.getId().equals(idPass)) {
                    
                    return true;
                    
                }
            }
            
        }
        
        return false;
        
    }
    
    public Passageiro getPassageiro(String idPass) {
        
        for(Passageiro pass : cadeiras) {
            if (pass != null ) {
                if(pass.getId().equals(idPass)) {
                    return pass;
                } 
            }
        }
        return null;      
  }
    public boolean isFull() {
        boolean full = true;
        for(Passageiro pass : cadeiras) {
            if(pass == null) {
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
         for(Vagao vagao : vagoes) {SV += vagao; }
         return SV;
     }
     public void addVagao(Vagao vagao) {
         int tamanho = vagoes.size();
         if(tamanho == maxVagoes) {
             Solver.output("fail: limite de vagões atingido");
             return;
         }
         vagoes.add(vagao);
     }
      public void embarcar(Passageiro pass) {
          registro.cadastrar(pass);
          boolean verifica = true;
          for(Vagao vagao : vagoes) {
              if(vagao.search(pass.getId())) {
                  Solver.output("fail: " + pass.getId() + " já está no trem");
                return;
              }
          }
         for(Vagao vagao : vagoes) {
             if(!vagao.isFull()){
                 Movimento mov = new Movimento( pass,Direcao.IN );
                 registro.addMov(mov);
                 vagao.embarcar(pass);
                 verifica = false;
                 break;
             }
         }
          if(verifica == true) {
              Solver.output("fail: trem lotado");
              return;
          }
          
      }
     public Passageiro getPassageiro(String idPass) {
         for(Vagao vagao : vagoes) {
             Passageiro p = vagao.getPassageiro(idPass);
             if(p != null ){
                 return p;
         }
     }
     return null;
     }
    public void desembarcar(String idPass) {
        Passageiro pass = getPassageiro(idPass);
        
        boolean verifica = false;
        int contador = 0;
        int tamanho = vagoes.size();
        for(Vagao vagao : vagoes) {
            contador++;
            if(vagao.search(idPass)) {
                verifica = true;
            }
            if(contador == tamanho && verifica == false) {
                Solver.output("fail: " + idPass + " nao esta no trem");
                return;
            }
            vagao.desembarcar(idPass);
        }
         
         Movimento mov = new Movimento( pass, Direcao.OUT );
         registro.addMov(mov);
        
    }

}

enum Direcao{
    
    IN("in"),
    OUT("out");
    
    private String label;
    
    Direcao(String label){
        
        this.label = label;
        
    }
    
    public String toString(){
       
        switch ( this.name() ) {
            case "IN":
                return "in";
            case "OUT":
                return "out";
            default:
                return "fail";
        }        
    
    }
    
    
    
    public String getLabel() {
        
        return this.label;
        
    }
    
}
class Movimento{
    
    private Passageiro pass;
    private Direcao dir;
    
    Movimento(Passageiro pass, Direcao dir) {
        
        this.pass = pass;
        this.dir = dir;
        
    }
    
    public String toString(){
        
        
        return this.pass + " " + this.dir;
        
    }
     public String getNome() {
        
        return this.pass.getId();
        
    }
    
}

class Registro{
    
    private static ArrayList<Passageiro> cadastro;
    private static ArrayList<Movimento> movimentacao;
    
    Registro(){
        
        cadastro = new ArrayList<Passageiro>();
        movimentacao = new ArrayList<Movimento>();
        
    }
    
    
    public String toString(){
        
        return "";
        
    }
    
    public String outMov(){
        
        String string = "";
        
        for(Movimento mov : movimentacao){ string += mov + "\n"; }
        
        return string;
        
    }
    
    public String outCad(){
        
        Collections.sort(cadastro, Comparator.comparing(Passageiro::getId));
        
        String string = "";
        
        for(Passageiro pass : cadastro){ string += pass + "\n"; }
        
        return string;
        
    }
    
    public void cadastrar(Passageiro pass){
        
        for(Passageiro p : cadastro){  if(p.getId().equals(pass.getId())){ return; }  }
        
        cadastro.add(pass);
        
    }
    
    public void addMov(Movimento mov){
        
        movimentacao.add(mov);
        
    }
    
}

class Solver {
    
    public static void main(String[] arg){
        
        Trem trem = new Trem(1);
        
        Registro registro = new Registro();
        
        while(true){
            
            var line = input();
            output("$" + line);
            var args = line.split(" ");
            
            if(args[0].equals("end"))          { break; }
            else if(args[0].equals("init"))         { trem = new Trem((int) number(args[1])); }
            else if(args[0].equals("entrar"))       { trem.embarcar(new Passageiro(args[1])); }
            else if(args[0].equals("sair"))         { trem.desembarcar(args[1]); }
            else if(args[0].equals("nwvag"))        { trem.addVagao(new Vagao((int) number(args[1]))); }
            else if(args[0].equals("show"))         { output(trem); }
            else if(args[0].equals("la"))           { output(trem); }
            else if(args[0].equals("movimentacao")) { print(registro.outMov()); }
            else if(args[0].equals("cadastro"))     { print(registro.outCad()); }
            
        }
        
    }
    
    public static Scanner in = new Scanner(System.in);
    
    public static String input()             { return in.nextLine(); }
    public static void print(Object value)   { System.out.print(value); }
    public static void output(Object value)  { System.out.println(value); }
    public static float number(String value) { return Float.parseFloat(value); }
    
}