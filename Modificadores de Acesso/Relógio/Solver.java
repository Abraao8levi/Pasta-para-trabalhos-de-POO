// 1. Fiz tudo, passou em todos os testes.
// 2. Fiz só, mas assistir aula gravada de POO do Professor do dia 28/08.
// 3. A parte do do relógio eu entendi, mas não muito a parte do Split na parte da classe Solver.
// 4. Levei 5 horas divido em 2 dias.




import java.util.Scanner;

class Time {
    private int hour = 0;
    private int minute = 0;
    private int second = 0;

    public Time(int hour, int minute, int second) {
        this.setHour(hour);
        this.setMinute(minute);
        this.setSecond(second);
    }

    public void setHour(int hour) {
        if (hour < 0 || hour > 23) {
            System.out.println("fail: hora invalida");
        } else {
            this.hour = hour;
        }
    }

    public void setMinute(int minute) {
        if (minute < 0 || minute > 59) {
            System.out.println("fail: minuto invalido");
        } else {
            this.minute = minute;
        }
    }

    public void setSecond(int second) {
        if (second < 0 || second > 59) {
            System.out.println("fail: segundo invalido");
        } else {
            this.second = second;
        }
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getSecond() {
        return second;
    }

    public void nextSecond() {
        second++;
        if (second > 59) {
            second = 0;
            minute++;
            if (minute > 59) {
                minute = 0;
                hour++;
                if (hour > 23) {
                    hour = 0;
                }
            }
        }
    }

    public String toString() {
        return String.format("%02d:%02d:%02d", hour, minute, second);
    }
}

public class Solver {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Time time = new Time(0, 0, 0);

        while (true) {
            String line = input();
            write("$" + line);
            String[] argsSplit = line.split(" ");

            if (argsSplit[0].equals("end")) {
                break;
            } else if (argsSplit[0].equals("show")) {
                write(time.toString());
            } else if (argsSplit[0].equals("next")) {
                time.nextSecond();
            } else if (argsSplit[0].equals("set")) {
                time.setHour((int) number(argsSplit[1]));
                time.setMinute((int) number(argsSplit[2]));
                time.setSecond((int) number(argsSplit[3]));
            } else if (argsSplit[0].equals("init")) {
                time = new Time((int) number(argsSplit[1]), (int) number(argsSplit[2]), (int) number(argsSplit[3]));
            } else {
                write("fail: comando invalido");
            }
        }
    }

    private static String input() {
        return scanner.nextLine();
    }

    private static double number(String value) {
        return Double.parseDouble(value);
    }

    private static void write(String value) {
        System.out.println(value);
    }
}
