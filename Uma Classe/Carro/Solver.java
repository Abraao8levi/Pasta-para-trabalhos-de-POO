import java.util.Scanner;  
public class Solver {
    public static void main(String[] a) {
        Car car = new Car();

        while (true) {
            var line = input();
            write("$" + line);
            var args = line.split(" ");

            if (args[0].equals("end")) {
                break;
            } else if (args[0].equals("show")) {
                System.out.println(car);
            } else if (args[0].equals("enter")) {
                car.enter();
            } else if (args[0].equals("leave")) {
                car.leave();
            } else if (args[0].equals("drive")) {
                car.drive(Integer.parseInt(args[1]));
            } else if (args[0].equals("fuel")) {
                car.fuel(Integer.parseInt(args[1]));
            } else {
                write("fail: comando invalido");
            }
        }
    }

    private static Scanner scanner = new Scanner(System.in);

    private static String input() {
        return scanner.nextLine();
    }

    private static void write(String value) {
        System.out.println(value);
    }
}
