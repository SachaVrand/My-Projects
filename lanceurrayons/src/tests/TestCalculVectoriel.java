package tests;

import donnees.ColorRGB;
import donnees.NotInstantiableClassException;
import donnees.Point;
import donnees.Vec3;

public final class TestCalculVectoriel {

    private TestCalculVectoriel() throws NotInstantiableClassException {
        throw new NotInstantiableClassException();
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Erreur, 1 seul parametre autorisé");
            System.exit(1);
        }

        String[] data = args[0].split(",");

        if (data.length != 3) {
            System.err.println("Un seul paramètre de la forme '(P|C|V) d d d,methode,(P|C|V) d d d'");
        }

        String operation = data[1];

        Object o1 = buildObject(data[0]);
        Object o2 = buildObject(data[2]);
        try {
            Class<?> clazz2 = o2.getClass() == Double.class ? double.class : o2.getClass();
            Object o3 = o1.getClass().getMethod(operation, clazz2).invoke(o1, o2);
            System.out.println(display(o3));
        } catch (Exception e) {
            System.out.println("Interdit");
        }
    }

    public static Object buildObject(String data) {
        Object res = null;
        String[] dataRes = data.split(" ");
        try {
            switch (dataRes[0]) {
            case "P":
                res = new Point(Double.parseDouble(dataRes[1]), Double.parseDouble(dataRes[2]),
                        Double.parseDouble(dataRes[3]));
                break;
            case "V":
                res = new Vec3(Double.parseDouble(dataRes[1]), Double.parseDouble(dataRes[2]),
                        Double.parseDouble(dataRes[3]));
                break;
            case "C":
                res = new ColorRGB(Double.parseDouble(dataRes[1]), Double.parseDouble(dataRes[2]),
                        Double.parseDouble(dataRes[3]));
                break;
            default:
                res = Double.parseDouble(dataRes[0]);
                break;
            }
        } catch (NumberFormatException e) {
            return null;
        }
        return res;
    }

    public static String display(Object obj) {
        String res = "";
        if (obj instanceof Point) {
            Point point = (Point) obj;
            res += "P " + point.getX() + " " + point.getY() + " " + point.getZ();
        } else if (obj instanceof Vec3) {
            Vec3 vec = (Vec3) obj;
            res += "V " + vec.getX() + " " + vec.getY() + " " + vec.getZ();
        } else if (obj instanceof ColorRGB) {
            ColorRGB color = (ColorRGB) obj;
            res += "C " + color.getR() + " " + color.getG() + " " + color.getB();
        } else if (obj instanceof Double) {
            Double d = (Double) obj;
            res += d;
        }
        return res;
    }
}
