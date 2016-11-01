package tests;

import java.io.IOException;

import donnees.NotInstantiableClassException;
import donnees.Scene;
import principal.SceneReader;

public final class TestSceneReader {

    private TestSceneReader() throws NotInstantiableClassException {
        throw new NotInstantiableClassException();
    }

    public static void main(String[] args) {
        Scene scene;
        int nbPixels;

        if (args.length != 1) {
            System.out.println("Erreur : nombre de paramètre incorrecte.");
        } else {
            try {
                scene = SceneReader.read(args[0]);
                System.out.println(scene.getOutput());

                nbPixels = scene.getSize().getHauteur() * scene.getSize().getLargeur();
                System.out.println(nbPixels);

                System.out.println(scene.getObjects().size());

                System.out.println(scene.getLumieres().size());
            } catch (IOException e) {
                System.out.println("Erreur lors de la lecture du fichier de scene.");
            }

        }
    }
}
