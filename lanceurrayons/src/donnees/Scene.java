package donnees;

import java.util.ArrayList;
import java.util.List;

public class Scene {
    private String file;
    private String output;
    private ImageSize size;
    private Camera camera;
    private ColorRGB ambient;
    private ColorRGB specular;
    private ArrayList<Lumiere> lumieres;
    private int maxverts;
    private ArrayList<Point> vertexs;
    private ArrayList<GeometricObject> objects;
    private Plane plane;
    private boolean shadow;
    private int maxdepth;
    private Sampling sampling;

    public Scene() {
        this.file = null;
        this.size = null;
        this.output = "output.png";
        this.camera = null;
        this.ambient = ColorRGB.NULLCOLOR;
        this.specular = ColorRGB.NULLCOLOR;
        this.lumieres = new ArrayList<>();
        this.maxverts = 0;
        this.vertexs = new ArrayList<>();
        this.objects = new ArrayList<>();
        this.plane = null;
        this.shadow = false;
        this.maxdepth = 1;
        this.sampling = Sampling.createSampling(Sampling.TOKENMIDDLE, 1);
    }

    // GET
    public ColorRGB getAmbient() {
        return ambient;
    }

    public Camera getCamera() {
        return camera;
    }

    public List<Lumiere> getLumieres() {
        return lumieres;
    }

    public String getFile() {
        return file;
    }

    public int getMaxverts() {
        return maxverts;
    }

    public String getOutput() {
        return output;
    }

    public Plane getPlane() {
        return plane;
    }

    public ImageSize getSize() {
        return size;
    }

    public ColorRGB getSpecular() {
        return specular;
    }

    public List<GeometricObject> getObjects() {
        return objects;
    }

    public List<Point> getVertexs() {
        return vertexs;
    }

    public boolean getShadow() {
        return shadow;
    }

    public int getMaxdepth() {
        return maxdepth;
    }

    public Sampling getSampling() {
        return sampling;
    }

    // SET
    public void setAmbient(ColorRGB ambient) {
        this.ambient = ambient;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public void setMaxverts(int maxverts) {
        this.maxverts = maxverts;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }

    public void setSize(ImageSize size) {
        this.size = size;
    }

    public void setSpecular(ColorRGB specular) {
        this.specular = specular;
    }

    public void setShadow(boolean shadow) {
        this.shadow = shadow;
    }

    public void setMaxdepth(int maxdepth) {
        this.maxdepth = maxdepth;
    }

    public void setSampling(Sampling sampling) {
        this.sampling = sampling;
    }

    // ADD
    public void addVertex(Point vertex) {
        this.vertexs.add(vertex);
    }

    public void addObject(GeometricObject object) {
        this.objects.add(object);
    }

    public void addLumiere(Lumiere lumiere) {
        this.lumieres.add(lumiere);
    }

}
