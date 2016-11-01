package donnees;

public class Camera {
    private Point lookFrom;
    private Point lookAt;
    private Vec3 up;
    private double fovd;
    private double fovr;

    public Camera(Point lookFrom, Point lookAt, Vec3 up, double fov) {
        this.lookFrom = lookFrom;
        this.lookAt = lookAt;
        this.up = up;
        if (fov >= 0 && fov <= 360) {
            this.fovd = fov;
            this.fovr = fov * Math.PI / 180;
        }
    }

    public Vec3 getUp() {
        return up;
    }

    public double getFovd() {
        return fovd;
    }

    public Point getLookAt() {
        return lookAt;
    }

    public Point getLookFrom() {
        return lookFrom;
    }

    public double getFovr() {
        return fovr;
    }
}
