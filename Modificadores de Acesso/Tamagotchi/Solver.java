
import java.util.Scanner;

public class Solver {
    public static void main(String[] a) {
        Pet pet = new Pet(0, 0, 0);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String line = input(scanner);
            write("$" + line);
            String[] args = line.split(" ");

            if (args[0].equals("end")) {
                break;
            } else if (args[0].equals("show")) {
                write(pet.toString());
            } else if (args[0].equals("init")) {
                pet = new Pet(Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));
            } else if (args[0].equals("play")) {
                pet.play();
            } else if (args[0].equals("eat")) {
                pet.eat();
            } else if (args[0].equals("sleep")) {
                if (pet.getEnergy() >= pet.getEnergyMax() - 4) {
                    write("fail: nao esta com sono");
                } else {
                    pet.sleep();
                }
            } else if (args[0].equals("shower")) {
                pet.shower();
            } else if (args[0].equals("clean")) {
                pet.cleanPet();
            } else {
                write("fail: comando invalido");
            }
        }
    }

    private static String input(Scanner scanner) {
        return scanner.nextLine();
    }

    private static void write(String value) {
        System.out.println(value);
    }
}
