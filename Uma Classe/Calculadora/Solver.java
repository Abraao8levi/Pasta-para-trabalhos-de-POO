import java.util.Scanner;

public class Solver {
    static Calculator calc;

    public static void main(String[] args) {
        calc = new Calculator(0);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String line = scanner.nextLine();
            String[] argsL = line.split(" ");
            System.out.println('$' + line);

            if ("show".equals(argsL[0])) {
                System.out.println(calc.toString());
            } else if ("init".equals(argsL[0])) {
                calc = new Calculator(Integer.parseInt(argsL[1]));
            } else if ("charge".equals(argsL[0])) {
                calc.charge(Integer.parseInt(argsL[1]));
            } else if ("sum".equals(argsL[0])) {
                calc.sum(Integer.parseInt(argsL[1]), Integer.parseInt(argsL[2]));
            } else if ("div".equals(argsL[0])) {
                calc.division(Integer.parseInt(argsL[1]), Integer.parseInt(argsL[2]));
            } else if ("end".equals(argsL[0])) {
                break;
            } else {
                System.out.println("fail: comando invalido");
            }
        }

        scanner.close();
    }
}
