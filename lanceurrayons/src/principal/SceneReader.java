package principal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import donnees.Camera;
import donnees.ColorRGB;
import donnees.ImageSize;
import donnees.LumiereDirectionnelle;
import donnees.LumierePoint;
import donnees.Material;
import donnees.Matrice4x4;
import donnees.NotInstantiableClassException;
import donnees.PlainColor;
import donnees.Plane;
import donnees.Point;
import donnees.Sampling;
import donnees.Scene;
import donnees.Sphere;
import donnees.Texture;
import donnees.Triangle;
import donnees.Vec3;

public final class SceneReader {

    private static final String ERRMSG = "Erreur :";

    // DLB: idéalement, cette exception devrait être de type Runtime, donc ne devrait
    // pas apparaître dans la signature du constructeur.
    private SceneReader() throws NotInstantiableClassException {
        throw new NotInstantiableClassException();
    }

    public static Scene read(String file) throws IOException {
        String line;
        String[] linesSplit;

        Scene scene = new Scene();
        Material diffuse = new PlainColor(ColorRGB.NULLCOLOR);
        ColorRGB specular = ColorRGB.NULLCOLOR;
        ArrayList<Matrice4x4> contextes = new ArrayList<>();
        contextes.add((new Matrice4x4()).newIdentite());
        int shininess = 1;

        try (BufferedReader buff = new BufferedReader(new FileReader(file))) {

            while ((line = buff.readLine()) != null) {
                line = removeEspaceUseless(line);
                linesSplit = line.split(" ");

                if (line.length() == 0 || linesSplit[0] == "#") {
                    continue;
                }

                // SIZE
                if (linesSplit[0].equals(KeyWord.SIZE.getLibelle())
                        && linesSplit.length == KeyWord.SIZE.getNbParam() + 1) {
                    scene.setSize(new ImageSize(Integer.parseInt(linesSplit[1]), Integer.parseInt(linesSplit[2])));
                }
                // OUTPUT
                else if (linesSplit[0].equals(KeyWord.OUTPUT.getLibelle())
                        && linesSplit.length == KeyWord.OUTPUT.getNbParam() + 1) {
                    scene.setOutput(linesSplit[1]);
                }
                // CAMERA
                else if (linesSplit[0].equals(KeyWord.CAMERA.getLibelle())
                        && linesSplit.length == KeyWord.CAMERA.getNbParam() + 1) {
                    Point lookFrom = new Point(Double.parseDouble(linesSplit[1]), Double.parseDouble(linesSplit[2]),
                            Double.parseDouble(linesSplit[3]));
                    Point lookAt = new Point(Double.parseDouble(linesSplit[4]), Double.parseDouble(linesSplit[5]),
                            Double.parseDouble(linesSplit[6]));
                    Vec3 up = new Vec3(Double.parseDouble(linesSplit[7]), Double.parseDouble(linesSplit[8]),
                            Double.parseDouble(linesSplit[9]));
                    scene.setCamera(new Camera(lookFrom, lookAt, up, Double.parseDouble(linesSplit[10])));
                }
                // AMBIENT
                else if (linesSplit[0].equals(KeyWord.AMBIENT.getLibelle())
                        && linesSplit.length == KeyWord.AMBIENT.getNbParam() + 1) {
                    scene.setAmbient(new ColorRGB(Double.parseDouble(linesSplit[1]), Double.parseDouble(linesSplit[2]),
                            Double.parseDouble(linesSplit[3])));
                }
                // DIFFUSE
                else if (linesSplit[0].equals(KeyWord.DIFFUSE.getLibelle())
                        && linesSplit.length == KeyWord.DIFFUSE.getNbParam() + 1) {
                    diffuse = new PlainColor(new ColorRGB(Double.parseDouble(linesSplit[1]),
                            Double.parseDouble(linesSplit[2]), Double.parseDouble(linesSplit[3])));
                }
                // SPECULAR
                else if (linesSplit[0].equals(KeyWord.SPECULAR.getLibelle())
                        && linesSplit.length == KeyWord.SPECULAR.getNbParam() + 1) {
                    specular = new ColorRGB(Double.parseDouble(linesSplit[1]), Double.parseDouble(linesSplit[2]),
                            Double.parseDouble(linesSplit[3]));
                }
                // DIRECTIONAL
                else if (linesSplit[0].equals(KeyWord.DIRECTIONAL.getLibelle())
                        && linesSplit.length == KeyWord.DIRECTIONAL.getNbParam() + 1) {
                    LumiereDirectionnelle tmp = new LumiereDirectionnelle(Double.parseDouble(linesSplit[1]),
                            Double.parseDouble(linesSplit[2]), Double.parseDouble(linesSplit[3]),
                            Double.parseDouble(linesSplit[4]), Double.parseDouble(linesSplit[5]),
                            Double.parseDouble(linesSplit[6]));
                    scene.addLumiere(tmp);
                }
                // POINT
                if (linesSplit[0].equals(KeyWord.POINT.getLibelle())
                        && linesSplit.length == KeyWord.POINT.getNbParam() + 1) {
                    LumierePoint tmp = new LumierePoint(Double.parseDouble(linesSplit[1]),
                            Double.parseDouble(linesSplit[2]), Double.parseDouble(linesSplit[3]),
                            Double.parseDouble(linesSplit[4]), Double.parseDouble(linesSplit[5]),
                            Double.parseDouble(linesSplit[6]));
                    scene.addLumiere(tmp);
                }
                // MAXVERTS
                else if (linesSplit[0].equals(KeyWord.MAXVERTS.getLibelle())
                        && linesSplit.length == KeyWord.MAXVERTS.getNbParam() + 1) {
                    scene.setMaxverts(Integer.parseInt(linesSplit[1]));
                }
                // VERTEX
                else if (linesSplit[0].equals(KeyWord.VERTEX.getLibelle())
                        && linesSplit.length == KeyWord.VERTEX.getNbParam() + 1) {
                    Point tmp = new Point(Double.parseDouble(linesSplit[1]), Double.parseDouble(linesSplit[2]),
                            Double.parseDouble(linesSplit[3]));
                    scene.addVertex(tmp);
                }
                // TRI
                else if (linesSplit[0].equals(KeyWord.TRI.getLibelle())
                        && linesSplit.length == KeyWord.TRI.getNbParam() + 1) {
                    int indice1 = Integer.parseInt(linesSplit[1]);
                    int indice2 = Integer.parseInt(linesSplit[2]);
                    int indice3 = Integer.parseInt(linesSplit[3]);
                    int nbVertexs = scene.getVertexs().size();

                    if (indice1 >= nbVertexs || indice2 >= nbVertexs || indice3 >= nbVertexs) {
                        System.out.println(ERRMSG + " indice de points pour la création de triangle incorrecte");
                    } else {
                        Triangle tmp = new Triangle(scene.getVertexs().get(indice1), scene.getVertexs().get(indice2),
                                scene.getVertexs().get(indice3), diffuse, specular, shininess,
                                contextes.get(contextes.size() - 1));
                        scene.addObject(tmp);
                    }
                }
                // SPHERE
                else if (linesSplit[0].equals(KeyWord.SPHERE.getLibelle())
                        && linesSplit.length == KeyWord.SPHERE.getNbParam() + 1) {
                    Point centre = new Point(Double.parseDouble(linesSplit[1]), Double.parseDouble(linesSplit[2]),
                            Double.parseDouble(linesSplit[3]));
                    Sphere tmp = new Sphere(centre, Double.parseDouble(linesSplit[4]), diffuse, specular, shininess,
                            contextes.get(contextes.size() - 1));
                    scene.addObject(tmp);
                }
                // PLANE
                else if (linesSplit[0].equals(KeyWord.PLANE.getLibelle())
                        && linesSplit.length == KeyWord.PLANE.getNbParam() + 1) {

                    Point passePar = new Point(Double.parseDouble(linesSplit[1]), Double.parseDouble(linesSplit[2]),
                            Double.parseDouble(linesSplit[3]));
                    Vec3 laNormale = new Vec3(Double.parseDouble(linesSplit[4]), Double.parseDouble(linesSplit[5]),
                            Double.parseDouble(linesSplit[6]));

                    scene.addObject(new Plane(passePar, laNormale, diffuse, specular, shininess));
                }
                // SHADOW
                else if (linesSplit[0].equals(KeyWord.SHADOW.getLibelle())
                        && linesSplit.length == KeyWord.SHADOW.getNbParam() + 1) {
                    scene.setShadow(Boolean.parseBoolean(linesSplit[1]));
                }
                // SHININESS
                else if (linesSplit[0].equals(KeyWord.SHININESS.getLibelle())
                        && linesSplit.length == KeyWord.SHININESS.getNbParam() + 1) {
                    shininess = Integer.parseInt(linesSplit[1]);
                }
                // MAXDEPTH
                else if (linesSplit[0].equals(KeyWord.MAXDEPTH.getLibelle())
                        && linesSplit.length == KeyWord.MAXDEPTH.getNbParam() + 1) {
                    scene.setMaxdepth(Integer.parseInt(linesSplit[1]));
                }
                // TRANSLATE
                else if (linesSplit[0].equals(KeyWord.TRANSLATE.getLibelle())
                        && linesSplit.length == KeyWord.TRANSLATE.getNbParam() + 1) {
                    Matrice4x4 tmp = new Matrice4x4();
                    tmp = tmp.newTranslate(new Vec3(Double.parseDouble(linesSplit[1]),
                            Double.parseDouble(linesSplit[2]), Double.parseDouble(linesSplit[3])));
                    contextes.set(contextes.size() - 1, contextes.get(contextes.size() - 1).mult(tmp));
                }
                // SCALE
                else if (linesSplit[0].equals(KeyWord.SCALE.getLibelle())
                        && linesSplit.length == KeyWord.SCALE.getNbParam() + 1) {
                    Matrice4x4 tmp = new Matrice4x4();
                    tmp = tmp.newScaling(new Vec3(Double.parseDouble(linesSplit[1]), Double.parseDouble(linesSplit[2]),
                            Double.parseDouble(linesSplit[3])));
                    contextes.set(contextes.size() - 1, contextes.get(contextes.size() - 1).mult(tmp));
                }
                // ROTATE
                else if (linesSplit[0].equals(KeyWord.ROTATE.getLibelle())
                        && linesSplit.length == KeyWord.ROTATE.getNbParam() + 1) {
                    Matrice4x4 tmp = new Matrice4x4();
                    tmp = tmp.newRotate(new Vec3(Double.parseDouble(linesSplit[1]), Double.parseDouble(linesSplit[2]),
                            Double.parseDouble(linesSplit[3])), Double.parseDouble(linesSplit[4]));
                    contextes.set(contextes.size() - 1, contextes.get(contextes.size() - 1).mult(tmp));
                }
                // CHECKER
                else if (linesSplit[0].equals(KeyWord.CHECKER.getLibelle())
                        && linesSplit.length == KeyWord.CHECKER.getNbParam() + 1) {
                    diffuse = new Texture(
                            new ColorRGB(Double.parseDouble(linesSplit[1]), Double.parseDouble(linesSplit[2]),
                                    Double.parseDouble(linesSplit[3])),
                            new ColorRGB(Double.parseDouble(linesSplit[4]), Double.parseDouble(linesSplit[5]),
                                    Double.parseDouble(linesSplit[6])),
                            Double.parseDouble(linesSplit[7]));
                }
                // SAMPLING
                else if (linesSplit[0].equals(KeyWord.SAMPLING.getLibelle())
                        && linesSplit.length == KeyWord.SAMPLING.getNbParam() + 1) {
                    scene.setSampling(Sampling.createSampling(linesSplit[1], Integer.parseInt(linesSplit[2])));
                }
                // PUSHTRANSFORM
                else if (linesSplit[0].equals(KeyWord.PUSHTRANSFORM.getLibelle())
                        && linesSplit.length == KeyWord.PUSHTRANSFORM.getNbParam() + 1) {
                    Matrice4x4 tmp = new Matrice4x4(contextes.get(contextes.size() - 1).getMatrice());
                    contextes.add(tmp);
                }
                // POPTRANSFORM
                else if (linesSplit[0].equals(KeyWord.POPTRANSFORM.getLibelle())
                        && linesSplit.length == KeyWord.PUSHTRANSFORM.getNbParam() + 1) {
                    contextes.remove(contextes.size() - 1);
                    if (contextes.isEmpty())
                        contextes.add((new Matrice4x4()).newIdentite());
                }
            }

            buff.close();

        } catch (NumberFormatException e) {
            throw new IOException();
        }

        return scene;
    }

    // retire les espaces au début et à la fin de la chaine "s", ainsi que les
    // espaces redondants
    private static String removeEspaceUseless(String s) {
        return s.trim().replaceAll("[ ]+", " ");
    }
}
