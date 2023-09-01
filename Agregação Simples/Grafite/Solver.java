// 1. Fiz tudo e passou em todos os testes.
// 2. Fiz com o Gilardo, mas eu peguei todos os conceitos.
// 3. fiz a parte * public void writePage(), tava rodando mas não estava passando no teste 8 e 10.
// 4. Levei 3 horas no computador do lab de redes e mais 4 horas em casa.


import java.text.DecimalFormat;
import java.util.Scanner;

class Lead {
    private float thickness;
    private String hardness;
    private int size;

    public Lead(float thickness, String hardness, int size) {
        this.thickness = thickness;
        this.hardness = hardness;
        this.size = size;
    }

    public float getThickness() {
        return thickness;
    }

    public String getHardness() {
        return hardness;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int usagePerSheet() {
        if (hardness.equals("HB"))
            return 1;
        else if (hardness.equals("2B"))
            return 2;
        else if (hardness.equals("4B"))
            return 4;
        else
            return 6;
    }

    public String toString() {
        DecimalFormat form = new DecimalFormat("0.0");
        return form.format(thickness) + ":" + hardness + ":" + size;
    }
}

class Pencil {
    private float thickness;
    private Lead tip;

    public Pencil(float thickness) {
        this.thickness = thickness;
    }

    public float getThickness() {
        return thickness;
    }

    public void setThickness(float value) {
        this.thickness = value;
    }

    public boolean hasGrafite() {
        return tip != null;
    }

    public boolean insert(Lead grafite) {
        if (thickness != grafite.getThickness()) {
            System.out.println("fail: calibre incompativel");
            return false;
        }
        if (hasGrafite()) {
            System.out.println("fail: ja existe grafite");
            return false;
        }
        tip = grafite;
        return true;
    }

    public Lead remove() {
        Lead removedTip = tip;
        tip = null;
        return removedTip;
    }

   /* public void writePage() {
        if (!hasGrafite()) {
            System.out.println("fail: nao existe grafite");
            return;
        }
        int usage = tip.usagePerSheet();
        if (tip.getSize() < usage) {
           System.out.println("fail: tamanho insuficiente");
            return;
            
        }
     
     
        
        tip.setSize(tip.getSize() - usage);
    */ public void writePage(){
     if(this.tip==null){
          System.out.println("fail: nao existe grafite");
            return;
     }    
     if (this.tip.getSize()==10){
          System.out.println("fail: tamanho insuficiente");
            return;
         
     }
        int finalSize= this.tip.getSize()-this.tip.usagePerSheet();
        if(finalSize >=10){
            this.tip.setSize(finalSize);
            
            
        }
        else{
        this.tip.setSize(10);
        System.out.println("fail: folha incompleta");
            
        }
    }
    
   
    

    public String toString() {
        String saida = "calibre: " + thickness + ", grafite: ";
        if (tip != null)
            saida += "[" + tip + "]";
        else
            saida += "null";
        return saida;
    }
}

public class Solver {
    public static void main(String[] args) {
        Pencil lap = new Pencil(0.5f);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String line = scanner.nextLine();
            String[] argsL = line.split(" ");
            write('$' + line);

            if ("end".equals(argsL[0])) {
                break;
            } else if ("init".equals(argsL[0])) {
                lap = new Pencil(Float.parseFloat(argsL[1]));
            } else if ("insert".equals(argsL[0])) {
                lap.insert(new Lead(Float.parseFloat(argsL[1]), argsL[2], Integer.parseInt(argsL[3])));
            } else if ("remove".equals(argsL[0])) {
                lap.remove();
            } else if ("write".equals(argsL[0])) {
                lap.writePage();
            } else if ("show".equals(argsL[0])) {
                write(lap.toString());
            }
        }
    }

    public static void write(String value) {
        System.out.println(value);
    }
}
