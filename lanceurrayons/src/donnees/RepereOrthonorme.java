package donnees;

public class RepereOrthonorme {

    private Vec3 u;
    private Vec3 v;
    private Vec3 w;

    public RepereOrthonorme(Camera camera) {
        // calcul du vecteur w
        w = (camera.getLookFrom().sub(camera.getLookAt())).norm();

        // Calcul du vecteur u
        u = (camera.getUp().cross(w)).norm();

        // Calcul du vecteur v
        v = (w.cross(u)).norm();
    }

    public Vec3 getU() {
        return u;
    }

    public Vec3 getV() {
        return v;
    }

    public Vec3 getW() {
        return w;
    }
}
