package principal;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import donnees.Camera;
import donnees.ColorRGB;
import donnees.GeometricObject;
import donnees.ImageSize;
import donnees.Intersection;
import donnees.Lumiere;
import donnees.NotInstantiableClassException;
import donnees.PixelSize;
import donnees.Point;
import donnees.RepereOrthonorme;
import donnees.Scene;
import donnees.Vec3;

public final class LanceurRayons {

    private LanceurRayons() throws NotInstantiableClassException {
        throw new NotInstantiableClassException();
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Un seul paramètre accepté : chemin du fichier de scène.");
            System.exit(1);
        }

        try {
            Scene scene = SceneReader.read(args[0]);
            executerLancerDeRayons(scene);
        } catch (IOException e) {
            System.out.println("Erreur lors de la lecture du fichier de scene.");
        }
    }

    private static void executerLancerDeRayons(Scene scene) {
        Camera cam = scene.getCamera();
        ImageSize imageSize = scene.getSize();
        RepereOrthonorme repere = new RepereOrthonorme(cam);
        PixelSize pixelSize = new PixelSize(imageSize, cam.getFovr());

        // creation de l'image à générer.
        BufferedImage img = new BufferedImage(imageSize.getLargeur(), imageSize.getHauteur(),
                BufferedImage.TYPE_INT_RGB);

        // Boucle du lanceur de rayons

        // crée un ensemble de 4 threads pour exécuter les tâches
        ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        // crée une tâche par ligne de l'image
        for (int j = 0; j < imageSize.getHauteur(); j++) {
            final int ligne = j;
            pool.execute(() -> rayTrace(img, ligne, imageSize.getLargeur(), scene, repere, pixelSize));
        }

        // indique de s'arrêter quand les tâches sont terminées
        pool.shutdown();

        // attend la complétion des tâches
        try {
            if (!pool.awaitTermination(21600, TimeUnit.SECONDS)) {
                System.err.println("Timeout reached !");
            }
        } catch (InterruptedException e1) {
            Logger.getGlobal().log(Level.WARNING, "Threads interrupted");
        }

        try {
            File imageDiff = new File(scene.getOutput());
            ImageIO.write(img, "png", imageDiff);
        } catch (IOException e) {
            System.out.println("Un problème a eu lieu durant la création de l'image de différence.");
        }
    }

    private static void rayTrace(BufferedImage img, int line, int width, Scene scene, RepereOrthonorme repere,
            PixelSize pixelSize) {
        Intersection obj;
        Vec3 d;
        ColorRGB pixelColor;

        for (int i = 0; i < width; i++) {
            pixelColor = ColorRGB.NULLCOLOR;

            for (Point point : scene.getSampling().getListePoints()) {
                d = getDirectionalVector(pixelSize, scene.getSize(), repere, i, line, point);
                obj = intersection(scene.getCamera().getLookFrom(), d, scene.getObjects());
                if (obj != null) {
                    pixelColor = pixelColor.add(calculCouleur(d, obj, scene, scene.getMaxdepth()));
                }
            }

            pixelColor = pixelColor.div((double) scene.getSampling().getNumberOfPoints());
            img.setRGB(i, line, pixelColor.getRGB());
        }
    }

    private static Intersection intersection(Point origin, Vec3 direction, List<GeometricObject> objectsScene) {
        double t = -1, tmpT;
        GeometricObject object = null;

        for (GeometricObject go : objectsScene) {
            // calcul de l'intersection pour chaque objet de la scene
            tmpT = go.getIntersection(direction, origin);
            // recuperation de l'intesection la plus proche parmis tous les
            // objets s'il y en a une.
            if (tmpT > 0.0d && (t < 0 || tmpT < t)) {
                t = tmpT;
                object = go;
            }
        }
        if (object != null) {
            return new Intersection(object, origin, direction, t);
        } else {
            return null;
        }
    }

    private static boolean isLightHidden(Point origin, Lumiere light, List<GeometricObject> objectsScene) {
        for (GeometricObject go : objectsScene) {
            double t = go.getIntersection(light.getLightDirection(origin), origin);
            if (t > 0.0d && t < light.getLightDistance(origin)) {
                return true;
            }
        }
        return false;
    }

    private static ColorRGB calculCouleur(Vec3 direction, Intersection intersection, Scene scene, int maxDepth) {
        if (maxDepth == 0) {
            return ColorRGB.NULLCOLOR;
        }

        GeometricObject object = intersection.getIntersectedObject();
        Vec3 n = object.getNormale(intersection.getIntersectionPoint());
        ColorRGB couleur;

        couleur = calculCouleurDirecte(intersection, direction, scene);

        if (!object.getSpecular().equals(ColorRGB.NULLCOLOR)) {
            couleur = couleur.add(calculCouleurIndirecte(intersection, direction, n, scene, maxDepth));
        }

        return couleur;
    }

    private static ColorRGB calculCouleurDirecte(Intersection intersection, Vec3 direction, Scene scene) {
        Point p = intersection.getIntersectionPoint();
        GeometricObject object = intersection.getIntersectedObject();
        Vec3 n = object.getNormale(p);
        ColorRGB sumMatteContribution = ColorRGB.NULLCOLOR, sumShininessContribution = ColorRGB.NULLCOLOR,
                couleurDirecte;

        // calcule pour chaque lumière
        for (Lumiere lumiere : scene.getLumieres()) {
            // Si les ombres ne sont pas actives ou que la lumière n'est pas
            // caché par un objet, on ajoute sa contribution.
            if (!scene.getShadow() || !isLightHidden(p.add(lumiere.getLightDirection(p).mul(GeometricObject.EPSILON)),
                    lumiere, scene.getObjects())) {
                sumMatteContribution = sumMatteContribution.add(lumiere.getMatteContribution(n, p));
                sumShininessContribution = sumShininessContribution
                        .add(lumiere.getShininessContribution(n, p, direction, object.getShininess()));
            }
        }

        // On ajoute la contribution de la lumiere * diffuse à la contribution
        // de la reflection * specular
        couleurDirecte = sumMatteContribution.times(object.getDiffuse(p))
                .add(sumShininessContribution.times(object.getSpecular()));

        // Et enfin on ajoute la lumiere ambiante.
        couleurDirecte = couleurDirecte.add(scene.getAmbient());
        return couleurDirecte;
    }

    private static ColorRGB calculCouleurIndirecte(Intersection intersection, Vec3 direction, Vec3 n, Scene scene,
            int maxDepth) {
        ColorRGB couleurIndirecte = ColorRGB.NULLCOLOR;
        // calcul de la direction reflechie du regard
        Vec3 r = reflectionDirection(direction, n);
        // Récupération de l'intersection de la direction reflechie du
        // regard avec un objet
        Intersection reflObjIntersection = intersection(
                intersection.getIntersectionPoint().add(r.mul(GeometricObject.EPSILON)), r, scene.getObjects());
        // Si il y a une intersection, on calcule la couleur.
        if (reflObjIntersection != null) {
            couleurIndirecte = calculCouleur(r, reflObjIntersection, scene, maxDepth - 1)
                    .times(intersection.getIntersectedObject().getSpecular());
        }

        return couleurIndirecte;
    }

    private static Vec3 reflectionDirection(Vec3 d, Vec3 n) {
        return d.add(n.mul(2 * (n.dot(d.opposite())))).norm();
    }

    public static Vec3 getDirectionalVector(PixelSize pixelSize, ImageSize imageSize, RepereOrthonorme repere, int i,
            int j, Point locationInPixel) {
        double a, b;
        Vec3 d;
        int imgHeight = imageSize.getHauteur(), imgWidth = imageSize.getLargeur(), newJ;

        newJ = imageSize.getHauteur() - 1 - j;

        imgHeight /= 2;
        imgWidth /= 2;
        a = (pixelSize.getPixelWidth() * (i - imgWidth + locationInPixel.getX())) / imgWidth;
        b = (pixelSize.getPixelHeight() * (newJ - imgHeight + locationInPixel.getY())) / imgHeight;
        d = (((repere.getU().mul(a)).add(repere.getV().mul(b))).sub(repere.getW())).norm();

        return d;
    }

    public static Point getIntersectionPoint(Vec3 d, Point origin, double t) {
        return origin.add(d.mul(t));
    }

}
