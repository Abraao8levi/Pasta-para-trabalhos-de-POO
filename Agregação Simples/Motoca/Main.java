import java.util.*;

class Person {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String toString() {
        return name + ":" + age;
    }
}

class Motorcycle {
    private Person person; // agregação
    private int power;
    private int time;

    public Motorcycle(int power) {
        this.power = power;
        this.time = 0;
        this.person = null;
    }

    public int getPower() {
        return power;
    }

    public int getTime() {
        return time;
    }

    public Person getPerson() {
        return person;
    }

    public boolean enter(Person person) {
        if (this.person == null) {
            this.person = person;
            return true;
        } else {
            System.out.println("fail: busy motorcycle");
            return false;
        }
    }

    public Person leave() {
        if (person != null) {
            Person personLeaving = person;
            person = null;
            return personLeaving;
        } else {
            System.out.println("fail: empty motorcycle");
            return null;
        }
    }

    public String honk() {
        int pot = 1;
        while (pot < power) {
            pot++;
        }
        return "P" + "e".repeat(pot) + "m";
    }

    public void buyTime(int time) {
        this.time += time;
    }

    public void drive(int time) {
        if (this.time == 0) {
            System.out.println("fail: buy time first");
            return;
        }
        if (person == null) {
            System.out.println("fail: empty motorcycle");
            return;
        }
        if (person.getAge() > 10) {
            System.out.println("fail: too old to drive");
            return;
        }
        if (this.time >= time) {
            this.time -= time;
        } else {
            System.out.println("fail: time finished after " + this.time + " minutes");
            this.time = 0;
        }
    }

    public String toString() {
        String personStr = (person == null) ? "empty" : person.toString();
        return "power:" + power + ", time:" + time + ", person:(" + personStr + ")";
    }
}

public class Main {
    static Motorcycle motoca = new Motorcycle(1);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String line = input(scanner);
            args = line.split(" ");
            write('$' + line);

            if (args[0].equals("show")) {
                System.out.println(motoca);
            } else if (args[0].equals("init")) {
                motoca = new Motorcycle(Integer.parseInt(args[1]));
            } else if (args[0].equals("enter")) {
                motoca.enter(new Person(args[1], Integer.parseInt(args[2])));
            } else if (args[0].equals("end")) {
                break;
            } else if (args[0].equals("leave")) {
                Person person = motoca.leave();
                if (person != null) {
                    System.out.println(person.toString());
                }
            } else if (args[0].equals("honk")) {
                System.out.println(motoca.honk());
            } else if (args[0].equals("buy")) {
                motoca.buyTime(Integer.parseInt(args[1]));
            } else if (args[0].equals("drive")) {
                motoca.drive(Integer.parseInt(args[1]));
            } else {
                System.out.println("fail: comando invalido");
            }
        }
    }

    public static String input(Scanner scanner) {
        return scanner.nextLine();
    }

    public static void write(String value) {
        System.out.println(value);
    }
}
