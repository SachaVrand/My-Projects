package compare;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import donnees.NotInstantiableClassException;

public final class Compare {

    private Compare() throws NotInstantiableClassException {
        throw new NotInstantiableClassException();
    }

    public static void main(String[] args) {
        BufferedImage img1, img2;
        int nb;
        
        if (args.length != 2) {
            System.out.println("Nombre de paramètre incorrect.");
            return;
        }

        try {
            img1 = ImageIO.read(new File(args[0]));
            img2 = ImageIO.read(new File(args[1]));
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement d'une image.");
            return;
        }

        if (img1.getWidth() != img2.getWidth() || img1.getHeight() != img2.getHeight()) {
            System.out.println("KO");
            System.out.println("Les images n'ont pas la même taille");
            return;
        } 

        nb = comparerImages(img1, img2);
        
        if (nb > 1000) {
            System.out.println("KO");
        } else {
            System.out.println("OK");
        }

        System.out.println(nb);
    }
    
    private static int comparerImages(BufferedImage img1, BufferedImage img2)
    {
        BufferedImage imgDiff = new BufferedImage(img1.getWidth(), img1.getHeight(), BufferedImage.TYPE_INT_RGB);
        int nb = 0;
        
        for (int x = 0; x < img1.getWidth(); x++) {
            for (int y = 0; y < img1.getHeight(); y++) {
                Color color1 = new Color(img1.getRGB(x, y));
                Color color2 = new Color(img2.getRGB(x, y));
                int diffRouge = Math.abs(color1.getRed() - color2.getRed());
                int diffVerte = Math.abs(color1.getGreen() - color2.getGreen());
                int diffBleue = Math.abs(color1.getBlue() - color2.getBlue());
                int colorDiff = new Color(diffRouge, diffVerte, diffBleue).getRGB();

                imgDiff.setRGB(x, y, colorDiff);

                if (img1.getRGB(x, y) != img2.getRGB(x, y)) {
                    nb++;
                }
            }
        }
        
        if (nb > 0) {
            try {
                File imageDiff = new File("diff.png");
                ImageIO.write(imgDiff, "png", imageDiff);
            } catch (IOException e) {
                System.out.println("Un problème a eu lieu durant la création de l'image de différence.");
            }
        }
        
        return nb;
    }
}
