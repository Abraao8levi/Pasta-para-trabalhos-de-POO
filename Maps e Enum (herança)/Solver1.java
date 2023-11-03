 import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
    
    abstract class Valuable {
        public abstract String getLabel();
        public abstract double getValue();
        public abstract int getVolume();
    
        @Override
        public abstract String toString();
    }
    
    class Coin extends Valuable {
        private final String label;
        private final double value;
        private final int volume;
    
        private Coin(String label, double value, int volume) {
            this.label = label;
            this.value = value;
            this.volume = volume;
        }
    
        public static final Coin C10 = new Coin("M10", 0.10, 1);
        public static final Coin C25 = new Coin("M25", 0.25, 2);
        public static final Coin C50 = new Coin("M50", 0.50, 3);
        public static final Coin C100 = new Coin("M100", 1.00, 4);
    
        @Override
        public String getLabel() {
            return label;
        }
    
        @Override
        public double getValue() {
            return value;
        }
    
        @Override
        public int getVolume() {
            return volume;
        }
    
        @Override
        public String toString() {
            DecimalFormat df = new DecimalFormat("0.00");
            return label + ":" + df.format(value) + ":" + volume;
        }
    }
    
    class Item extends Valuable {
        private final String label;
        private final double value;
        private final int volume;
    
        public Item(String label, double value, int volume) {
            this.label = label;
            this.value = value;
            this.volume = volume;
        }
    
        @Override
        public String getLabel() {
            return label;
        }
    
        @Override
        public double getValue() {
            return value;
        }
    
        @Override
        public int getVolume() {
            return volume;
        }
    
        @Override
        public String toString() {
            DecimalFormat df = new DecimalFormat("0.00");
            return label + ":" + df.format(value) + ":" + volume;
        }
    }
    
    class Pig {
        private final int volumeMax;
        private boolean broken;
        private final List<Valuable> valuables;
    
        public Pig(int volumeMax) {
            this.volumeMax = volumeMax;
            this.broken = false;
            this.valuables = new ArrayList<>();
        }
    
        public void addValuable(Valuable val) {
            if (broken) {
                throw new IllegalArgumentException("fail: the pig is broken");
            }
            if (getVolume() + val.getVolume() > volumeMax) {
                throw new IllegalArgumentException("fail: the pig is full");
            }
            if (val instanceof Coin) {
                valuables.add(val);
            } else if (val instanceof Item) {
                valuables.add(val);
            }
        }
    
        public void breakPig() {
            if (broken) {
                return;
            }
            broken = true;
            List<Valuable> coins = new ArrayList<>();
          List<Valuable> items = new ArrayList<>();
               for (Valuable val : valuables) {
            if (val instanceof Coin) {
            coins.add(val);
          } else if (val instanceof Item) {
            items.add(val);
        }
    }
    
            valuables.clear();
            valuables.addAll(coins);
            valuables.addAll(items);
        }
    
        public List<Coin> extractCoins() {
            if (!broken) {
                throw new IllegalArgumentException("fail: you must break the pig first");
            }
            List<Coin> coins = new ArrayList<>();
            for (Valuable val : valuables) {
                if (val instanceof Coin) {
                    coins.add((Coin) val);
                }
            }
            valuables.removeAll(coins);
            return coins;
        }
    
        public List<Item> extractItems() {
            if (!broken) {
                throw new IllegalArgumentException("fail: you must break the pig first");
            }
            List<Item> items = new ArrayList<>();
            for (Valuable val : valuables) {
                if (val instanceof Item) {
                    items.add((Item) val);
                }
            }
            valuables.removeAll(items);
            return items;
        }
    
        public double getValue() {
            double value = 0;
            for (Valuable val : valuables) {
                value += val.getValue();
            }
            return value;
        }
    
        public int getVolume() {
            if (broken) {
                return 0;
            }
            int volume = 0;
            for (Valuable val : valuables) {
                volume += val.getVolume();
            }
            return volume;
        }
    
        public int getVolumeMax() {
            return volumeMax;
        }
    
        public boolean isBroken() {
            return broken;
        }
    
        @Override
        public String toString() {
            DecimalFormat df = new DecimalFormat("0.00");
            StringBuilder sb = new StringBuilder("[");
            for (int i = 0; i < valuables.size(); i++) {//
                sb.append(valuables.get(i).toString());
                if (i < valuables.size() - 1) {
                    sb.append(", ");
                }
            }
            sb.append("] : ");
            sb.append(df.format(getValue()));
            sb.append("$ : ");
            sb.append(getVolume());
            sb.append("/");
            sb.append(getVolumeMax());
            if (isBroken()) {
                sb.append(" : broken");
            } else {
                sb.append(" : intact");
            }
            return sb.toString();
        }
    }
    
    public class Solver1 {
        public static void main(String[] args) {
            Pig pig = new Pig(0);
            Scanner scanner = new Scanner(System.in);
    
            while (true) {
                try {
                    String line = scanner.nextLine();
                    String[] inputArgs = line.split(" ");
                    System.out.println("$" + line);
    
                    if (inputArgs.length == 0) {
                        System.out.println("fail: invalid command");
                        continue;
                    }
    
                    if (inputArgs[0].equals("end")) {
                        break;
                    } else if (inputArgs[0].equals("init")) {
                        if (inputArgs.length < 2) {
                            System.out.println("fail: invalid command");
                            continue;
                        }
                        pig = new Pig(Integer.parseInt(inputArgs[1]));
                    } else if (inputArgs[0].equals("addCoin")) {
                        if (inputArgs.length < 2) {
                            System.out.println("fail: invalid command");
                            continue;
                        }
                        int coinValue = Integer.parseInt(inputArgs[1]);
                        if (coinValue == 10 || coinValue == 25 || coinValue == 50 || coinValue == 100) {
                            Coin coin;
                            if (coinValue == 10) {
                                coin = Coin.C10;
                            } else if (coinValue == 25) {
                                coin = Coin.C25;
                            } else if (coinValue == 50) {
                                coin = Coin.C50;
                            } else {
                                coin = Coin.C100;
                            }
                            pig.addValuable(coin);
                        } else {
                            System.out.println("fail: invalid coin value");
                        }
                    } else if (inputArgs[0].equals("addItem")) {
                        if (inputArgs.length < 4) {
                            System.out.println("fail: invalid command");
                            continue;
                        }
                        Item item = new Item(inputArgs[1], Double.parseDouble(inputArgs[2]), Integer.parseInt(inputArgs[3]));
                        pig.addValuable(item);
                    } else if (inputArgs[0].equals("break")) {
                        pig.breakPig();
                    } else if (inputArgs[0].equals("extractCoins")) {
                        List<Coin> coins = pig.extractCoins();
                        if (!pig.isBroken()) {
                            System.out.println("fail: you must break the pig first");
                        } else {
                            String s = "[";
                            int cont = 0;
                            for (Coin coin : coins) {
                                s += coin.toString();
                                if (cont != coins.size() - 1) {
                                    s += ", ";
                                }
                                cont++;
                            }
                            s += "]";
                            System.out.println(s);
                        }
                    } else if (inputArgs[0].equals("extractItems")) {
                        List<Item> items = pig.extractItems();
                        if (!pig.isBroken()) {
                            System.out.println("fail: you must break the pig first");
                        } else {
                            /*String s = "[";
                            int cont = 0;
                            for (Item item : items) {
                                s += item.toString();
                                if (cont != items.size() - 1) {
                                    s += ", ";
                                }
                                cont++;
                            }
                            s += "]";
                            System.out.println(s);
                        }*/
                          String s = "[";
                            int cont = 0;
                            for (Item item : items) {
                                s += item.toString();
                                if (cont != items.size() - 1) {
                                    s += ", ";
                                }
                                cont++;
                            }
                            s += "]";
                            System.out.println(s);
                        }
                    } else if (inputArgs[0].equals("show")) {
                        System.out.println(pig.toString());
                    } else {
                        System.out.println("fail: invalid command");
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }