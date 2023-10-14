
import java.util.*;
import java.util.stream.Collectors;
import java.text.DecimalFormat;
    enum Coin{
        C10 ( 0.10, 1, "C10" ),
        C25 ( 0.25, 2, "C25" ),
        C50 ( 0.50, 3, "C50" ),
        C100( 1.00, 4, "C100");
    
        private double value;
        private int volume;
        private String label;
    
        private Coin(double value, int volume, String label) {
            this.value = value;
            this.volume = volume;
            this.label = label;
        
    }
    public double getValue(){
        return this.value;
    }
    public int getVolume(){
        return this.volume;
    }
    public String getLabel(){
        return this.label;
    }
    @Override
    public String toString(){
       DecimalFormat d = new DecimalFormat("0.00");
       return d.format(this.value) + ":" + this.volume;
    }
    }
    class Item{
        private String label;
        private int volume;    
        public Item(String label, int volume){
        this.label = label;
        this.volume = volume;    
        }
        public  String getLabel() {
            return this.label;
        }
         public void setLabel(String label) {
        }
        public int getVolume() {
            return this.volume;
        }
        public void setVolume(int volume) {
        }
    @Override
         public String toString() {
            return this.label + ":" + this.volume;
        }
    }
        class ExtractException extends Exception {
        private ArrayList<String> labels;
        
        public ExtractException(String message, ArrayList<String> labels) {
            super(message);
            this.labels = labels;
        }
        
        ArrayList<String> getLabels() {
            return this.labels;
        }
    
        @Override
        public String getMessage() {
            return "fail: you must break the pig first";
        }
    }
    class BrokenException extends Exception {
        @Override
        public String getMessage() {
            return "fail: the pig is broken";
        }
    
    }
    class FullException extends Exception {
        @Override
        public String getMessage() {
            return "fail: the pig is full";
        }
    }
    class Pig{
        private boolean broken;
        private List<Coin> coins;
        private List<Item> items;
        private int volumeMax;
        public Pig(int volumeMax) {
            this.broken = false;
            this.coins = new ArrayList<Coin>();
            this.items = new ArrayList<Item>();
            this.volumeMax = volumeMax;
        }
        public Coin createCoin(String valor) {
            switch (valor) {
                case "10":
                    return Coin.C10;
                case "25":
                    return Coin.C25;
                case "50":
                    return Coin.C50;
                case "100":
                    return Coin.C100;
                default:
                    return null;
            }
    }
        public boolean addCoin(Coin coin) throws Exception {
            if (isBroken()) {
                throw new BrokenException();
            }
    
            if (coin.getVolume() > this.getVolumeRestante()) {
                throw new FullException();
            }
            
            this.coins.add( coin );
            return true;
        }
          public boolean addItem(Item item) throws Exception {
            if (isBroken()) {
                throw new BrokenException();
            }
    
            if (item.getVolume() > this.getVolumeRestante()) {
                throw new FullException();
            }
    
            this.items.add( item );
            return true;
        }
        public boolean breakPig() throws Exception {
            if (isBroken()) {
                throw new BrokenException();
            }
    
            this.broken = true;
            return true;
        }
        
        public ArrayList<String> extractCoins() throws Exception {
            if (!this.isBroken()) {
                throw new ExtractException("fail: you must break the pig first", new ArrayList<String>());
            }
            
            ArrayList<String> labels = new ArrayList<String>();
            for (Coin c : this.coins) {
                labels.add( c.toString() );
            }
            this.coins.clear();
            return labels;
        }
        public ArrayList<String> extractItems() throws Exception {
            if (!this.isBroken()) {
                throw new ExtractException("fail: you must break the pig first", new ArrayList<String>());
            }
    
            ArrayList<String> labels = new ArrayList<String>();
            for (Item i : this.items) {
                labels.add( i.toString() );
            }
            this.items.clear();
            return labels;
        }
         public String toString() {
            String s = "state=";
            s += ( this.isBroken() ) ? "broken" : "intact";
            s += " : coins=" + this.coins;
            s += " : items=" + this.items;
            DecimalFormat d = new DecimalFormat("0.00");
            s += " : value=" + d.format(this.getValue());
            s += " : volume=" + this.getVolume() + "/" + this.getVolumeMax();
            
            return s;
        }
         public int getVolume() {
            if (isBroken()) {
                return 0;
            }
         int volume = 0;
            for (Coin c : this.coins) {
                volume += c.getVolume();
            }
            for (Item i : this.items) {
                volume += i.getVolume();
            }
            return volume;
        }
     public double getValue() {
            double value = 0;
            for (Coin c : this.coins) {
                value += c.getValue();
            }
            return value;
        }
    
        public int getVolumeMax() {
            return this.volumeMax;
        }
    
        public int getVolumeRestante() {
            return this.getVolumeMax() - this.getVolume();
        }
    
        public boolean isBroken() {
            return this.broken;
        }
    }    
    public class Solver {
        public static void main(String[] arg) {
            Pig pig = new Pig(5);
    
            while (true) {
                String line = input();
                println("$" + line);
                String[] args = line.split(" ");
    
                try {
                    if      (args[0].equals("end"))          { break; }
                    else if (args[0].equals("init"))         { pig = new Pig( (int) number(args[1]) ); }
                    else if (args[0].equals("show"))         { println(pig); }
                    else if (args[0].equals("addCoin"))      { pig.addCoin( pig.createCoin( args[1] ) ); }
                    else if (args[0].equals("addItem"))      { pig.addItem( new Item( args[1], (int) number(args[2]) ) ); }
                    else if (args[0].equals("break"))        { pig.breakPig(); }
                    else if (args[0].equals("extractCoins")) { println("[" + String.join(", ", pig.extractCoins()) + "]"); }
                    else if (args[0].equals("extractItems")) { println("[" + String.join(", ", pig.extractItems()) + "]"); }
                    else                                     { println("fail: comando invalido"); }
                } catch (ExtractException e) {
                    println(e.getMessage());
                    println(e.getLabels());
                } catch (Exception e) {
                    println(e.getMessage());
                }
            }
        }
    
        private static Scanner scanner = new Scanner(System.in);
        private static String  input()                { return scanner.nextLine();        }
        private static double  number(String value)   { return Double.parseDouble(value); }
        public  static void    println(Object value)  { System.out.println(value);        }
        public  static void    print(Object value)    { System.out.print(value);          }
}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    















