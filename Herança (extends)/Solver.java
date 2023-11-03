
import java.util.*;
import java.text.DecimalFormat;

class Point2D {
    public double x;
    public double y;

    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double distance(Point2D p) {
        double cH = this.x - p.x;
        double cV = this.y - p.y;
        return Math.sqrt(cH * cH + cV * cV);
    }

    @Override
    public String toString() {
        DecimalFormat d = new DecimalFormat("0.00");
        return "(" + d.format(this.x) + ", " + d.format(this.y) + ")";
    }
}

class Shape {
    private String name;

    public Shape() {
    }

    public Shape(String name) {
        this.name = name;
    }

    public boolean inside(Point2D p) {
        return false;
    }

    public double getArea() {
        return 0.0;
    }

    public double getPerimeter() {
        return 0.0;
    }

    public String getInfo() {
        DecimalFormat d = new DecimalFormat("0.00");
        return this.name + ": A=" + d.format(this.getArea()) + " P=" + d.format(this.getPerimeter());
    }

    @Override
    public String toString() {
        return this.name + ": ";
    }
}

class Circle extends Shape {
    public Point2D center;
    public double radius;

    public Circle(Point2D center, double radius) {
        super("Circ");
        this.center = center;
        this.radius = radius;
    }

    @Override
    public double getArea() {
        return Math.PI * this.radius * this.radius;
    }

    @Override
    public double getPerimeter() {
        return 2 * Math.PI * this.radius;
    }

    @Override
    public String toString() {
        DecimalFormat d = new DecimalFormat("0.00");
        return super.toString() + "C=" + this.center + ", R=" + d.format(this.radius);
    }
}

class Rectangle extends Shape {
    public Point2D p1;
    public Point2D p2;

    public Rectangle(Point2D p1, Point2D p2) {
        super("Rect");
        this.p1 = p1;
        this.p2 = p2;
    }

    @Override
    public double getArea() {
        double base = Math.abs(this.p1.x - this.p2.x);
        double altura = Math.abs(this.p1.y - this.p2.y);
        return base * altura;
    }

    @Override
    public double getPerimeter() {
        double base = Math.abs(this.p1.x - this.p2.x);
        double altura = Math.abs(this.p1.y - this.p2.y);
        return 2 * (base + altura);
    }

    @Override
    public String toString() {
        DecimalFormat d = new DecimalFormat("0.00");
        return super.toString() + "P1=" + this.p1 + " P2=" + this.p2;
    }
}

public class Solver {
    public static void main(String[] arg) {
        ArrayList<Shape> shapes = new ArrayList<Shape>();

        while (true) {
            String line = input();
            println("$" + line);
            String[] args = line.split(" ");

            if (args[0].equals("end")) {
                break;
            } else if (args[0].equals("circle")) {
                shapes.add(new Circle(new Point2D(number(args[1]), number(args[2])), number(args[3])));
            } else if (args[0].equals("rect")) {
                shapes.add(new Rectangle(new Point2D(number(args[1]), number(args[2])),
                        new Point2D(number(args[3]), number(args[4]))));
            } else if (args[0].equals("show")) {
                showAll(shapes);
            } else if (args[0].equals("info")) {
                infoAll(shapes);
            } else {
                println("fail: comando invalido");
            }
        }
    }

    private static Scanner scanner = new Scanner(System.in);

    private static String input() {
        return scanner.nextLine();
    }

    private static double number(String value) {
        return Double.parseDouble(value);
    }

    public static void println(Object value) {
        System.out.println(value);
    }

    public static void print(Object value) {
        System.out.print(value);
    }

    public static void showAll(ArrayList<Shape> shapes) {
        for (Shape s : shapes) {
            println(s);
        }
    }

    public static void infoAll(ArrayList<Shape> shapes) {
        for (Shape s : shapes) {
            println(s.getInfo());
        }
    }
}