package donnees;

public class Vec4 {
    private double[] matrice;

    public Vec4() {
        matrice = new double[4];
    }

    public Vec4(double x, double y, double z, double w) {
        this();

        matrice[0] = x;
        matrice[1] = y;
        matrice[2] = z;
        matrice[3] = w;
    }

    public Vec4(Point p) {
        this();

        matrice[0] = p.getX();
        matrice[1] = p.getY();
        matrice[2] = p.getZ();
        matrice[3] = 1;
    }

    public Vec4(Vec3 v) {
        this();

        matrice[0] = v.getX();
        matrice[1] = v.getY();
        matrice[2] = v.getZ();
        matrice[3] = 0;
    }

    public Matrice4x4 mult(Matrice4x4 m) {
        int x, y, i;
        double tmp;
        Matrice4x4 res = new Matrice4x4();

        for (x = 0; x < 4; x++) {
            for (y = 0; y < 4; y++) {
                for (tmp = 0, i = 0; i < 4; i++) {
                    tmp += matrice[i] * m.getMatrice()[i][y];
                }
                res.getMatrice()[x][y] = tmp;
            }
        }

        return res;
    }

    public Point parsePoint() {
        return new Point(matrice[0], matrice[1], matrice[2]);
    }

    public Vec3 parseVec3() {
        return new Vec3(matrice[0], matrice[1], matrice[2]);
    }

    // GET
    public double getX() {
        return matrice[0];
    }

    public double getY() {
        return matrice[1];
    }

    public double getZ() {
        return matrice[2];
    }

    public double getW() {
        return matrice[3];
    }

    public double[] getMatrice() {
        return matrice;
    }
}
