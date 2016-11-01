package donnees;

import java.math.BigDecimal;

public class Matrice4x4 {
    private double[][] matrice;

    public Matrice4x4() {
        matrice = new double[4][4];
    }

    public Matrice4x4(double[][] matrice) {
        this.matrice = matrice;
    }

    public Matrice4x4 add(Matrice4x4 m) {
        int i, j;
        Matrice4x4 res = new Matrice4x4();

        for (i = 0; i < 4; i++) {
            for (j = 0; j < 4; j++) {
                res.matrice[i][j] = matrice[i][j] + m.matrice[i][j];
            }
        }

        return res;
    }

    public Matrice4x4 sous(Matrice4x4 m) {
        int i, j;
        Matrice4x4 res = new Matrice4x4();

        for (i = 0; i < 4; i++) {
            for (j = 0; j < 4; j++) {
                res.matrice[i][j] = matrice[i][j] - m.matrice[i][j];
            }
        }

        return res;
    }

    public Matrice4x4 mult(Matrice4x4 m) {
        int x, y, i;
        double tmp;
        Matrice4x4 res = new Matrice4x4();

        for (x = 0; x < 4; x++) {
            for (y = 0; y < 4; y++) {
                for (tmp = 0, i = 0; i < 4; i++) {
                    tmp += matrice[x][i] * m.matrice[i][y];
                }
                res.matrice[x][y] = tmp;
            }
        }

        return res;
    }

    public Matrice4x4 mult(double a) {
        Matrice4x4 res = new Matrice4x4();
        int x, y;

        for (x = 0; x < matrice.length; x++) {
            for (y = 0; y < matrice.length; y++) {
                res.getMatrice()[x][y] = matrice[x][y] * a;
            }
        }

        return res;
    }

    public Vec4 mult(Vec4 v) {
        int x, i;
        double tmp;
        Vec4 res = new Vec4();

        for (x = 0; x < 4; x++) {
            for (tmp = 0, i = 0; i < 4; i++) {
                tmp += matrice[x][i] * v.getMatrice()[i];
            }
            res.getMatrice()[x] = tmp;
        }

        return res;
    }

    public boolean isIdentite() {
        int x, y;

        for (x = 0; x < matrice.length; x++) {
            for (y = 0; y < matrice.length; y++) {
                if (x == y && !BigDecimal.valueOf(matrice[x][y]).equals(BigDecimal.valueOf(1)))
                    return false;
                else if (x != y && !BigDecimal.valueOf(matrice[x][y]).equals(BigDecimal.valueOf(0)))
                    return false;

            }
        }

        return true;
    }

    public Matrice4x4 newIdentite() {
        Matrice4x4 res = new Matrice4x4();
        res.setMatrice(new double[][] { { 1, 0, 0, 0 }, { 0, 1, 0, 0 }, { 0, 0, 1, 0 }, { 0, 0, 0, 1 } });
        return res;
    }

    public Matrice4x4 newTranslate(Vec3 v) {
        Matrice4x4 res = new Matrice4x4();
        res.setMatrice(
                new double[][] { { 1, 0, 0, v.getX() }, { 0, 1, 0, v.getY() }, { 0, 0, 1, v.getZ() }, { 0, 0, 0, 1 } });
        return res;
    }

    public Matrice4x4 newScaling(Vec3 v) {
        Matrice4x4 res = new Matrice4x4();
        res.setMatrice(
                new double[][] { { v.getX(), 0, 0, 0 }, { 0, v.getY(), 0, 0 }, { 0, 0, v.getZ(), 0 }, { 0, 0, 0, 1 } });
        return res;
    }

    public Matrice4x4 newRotate(Vec3 v, double degre) {
        Matrice4x4 tmp = new Matrice4x4();

        double cos = Math.cos(Math.PI * (degre) / 180);
        double sin = Math.sin(Math.PI * (degre) / 180);
        double x = v.getX();
        double y = v.getY();
        double z = v.getZ();

        double c00 = cos + x * x * (1 - cos);
        double c01 = x * y * (1 - cos) - z * sin;
        double c02 = x * z * (1 - cos) + y * sin;
        double c03 = 0;

        double c10 = x * y * (1 - cos) + z * sin;
        double c11 = cos + y * y * (1 - cos);
        double c12 = y * z * (1 - cos) - x * sin;
        double c13 = 0;

        double c20 = x * z * (1 - cos) - y * sin;
        double c21 = y * z * (1 - cos) + x * sin;
        double c22 = cos + z * z * (1 - cos);
        double c23 = 0;

        double c30 = 0;
        double c31 = 0;
        double c32 = 0;
        double c33 = 1;

        tmp.setMatrice(new double[][] { { c00, c01, c02, c03 }, { c10, c11, c12, c13 }, { c20, c21, c22, c23 },
                { c30, c31, c32, c33 } });

        return tmp;
    }

    public double det() {
        return subDet(matrice);
    }

    private static double subDet(double[][] m) {
        double res = 0;

        if (m.length == 1) {
            res += m[0][0];
        } else if (m.length == 2) {
            res += m[0][0] * subDet(new double[][] { { m[1][1] } }) - // res = a
                                                                      // * det -
                    m[0][1] * subDet(new double[][] { { m[1][0] } }); // b * det
        } else if (m.length == 3) {
            res += m[0][0] * subDet(new double[][] { { m[1][1], m[1][2] }, { m[2][1], m[2][2] } }) - // res
                                                                                                     // =
                                                                                                     // a
                                                                                                     // *
                                                                                                     // det
                                                                                                     // -
                    m[0][1] * subDet(new double[][] { { m[1][0], m[1][2] }, { m[2][0], m[2][2] } }) + // b
                                                                                                      // *
                                                                                                      // det
                                                                                                      // +
                    m[0][2] * subDet(new double[][] { { m[1][0], m[1][1] }, { m[2][0], m[2][1] } }); // c
                                                                                                     // *
                                                                                                     // det
        } else if (m.length == 4) {
            res += m[0][0]
                    * subDet(new double[][] { { m[1][1], m[1][2], m[1][3] }, { m[2][1], m[2][2], m[2][3] },
                            { m[3][1], m[3][2], m[3][3] } })
                    - // res = a * det -
                    m[0][1] * subDet(new double[][] { { m[1][0], m[1][2], m[1][3] }, { m[2][0], m[2][2], m[2][3] },
                            { m[3][0], m[3][2], m[3][3] } })
                    + // b * det +
                    m[0][2] * subDet(new double[][] { { m[1][0], m[1][1], m[1][3] }, { m[2][0], m[2][1], m[2][3] },
                            { m[3][0], m[3][1], m[3][3] } })
                    - // c * det -
                    m[0][3] * subDet(new double[][] { { m[1][0], m[1][1], m[1][2] }, { m[2][0], m[2][1], m[2][2] },
                            { m[3][0], m[3][1], m[3][2] } }); // d * det
        }

        return res;
    }

    public Matrice4x4 inverser() {
        Matrice4x4 tmp = adj();

        double tmpDet = 1 / det();

        tmp = tmp.mult(tmpDet);

        return tmp;
    }

    public Matrice4x4 transposer() {
        int x, y;
        Matrice4x4 tmp = new Matrice4x4();

        for (x = 0; x < 4; x++) {
            for (y = 0; y < 4; y++) {
                tmp.getMatrice()[x][y] = matrice[y][x];
            }
        }

        return tmp;
    }

    private Matrice4x4 adj() {
        int x, y;
        double[][] res = new double[4][4];

        for (x = 0; x < matrice.length; x++) {
            for (y = 0; y < matrice.length; y++) {
                res[x][y] = Math.pow(-1, (double)x + y) * (subDet(withoutIJ(x, y)));
            }
        }

        return (new Matrice4x4(res)).transposer();
    }

    private double[][] withoutIJ(int i, int j) {
        int x, y, m, n;
        int l = matrice.length;
        double[][] res = new double[l - 1][l - 1];

        for (x = 0, m = 0; x < l; x++) {
            if (x == i) {
                continue;
            }  
            
            for (y = 0, n = 0; y < l; y++) {
                if (y == j) {
                    continue;
                }
                
                res[m][n] = matrice[x][y];
                n++;
            }
            m++;     
        }

        return res;
    }

    public double[][] getMatrice() {
        return matrice;
    }

    public void setMatrice(double[][] matrice) {
        this.matrice = matrice;
    }
}
