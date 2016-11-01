package principal;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.SwingUtilities;

import vues.FenetrePrincipal;

/**
 * Classe principal du Tetris attack. Permet de lancer l'application.
 * @author Sacha
 *
 */
public class TetrisAttack {

	/**
	 * Méthode qui charge les polices, recupère les configurations du fichier xml et lance l'application.
	 * @param args Aucun demandée.
	 */
	public static void main(String[] args) {

		chargerPolices();
		Ressources.recupererConfiguration();
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				FenetrePrincipal.createMainFrame();
				FenetrePrincipal.afficherTitleScreen();
				FenetrePrincipal.setAtScreenCenter();
			}
		});
	}
	
	/**
	 * Méthode qui charge les polices d'écriture du dossier Font.
	 */
	private static void chargerPolices()
	{
		try (DirectoryStream<Path> stream = Files.newDirectoryStream((new File("Font")).toPath())) {
		    for (Path file: stream) {
		    	GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(Font.createFont(Font.TRUETYPE_FONT, file.toFile()));
		    }
		} catch (IOException | DirectoryIteratorException x) {
		    System.err.println(x);
		} catch (FontFormatException e) {
			System.err.println(e);
		}	
	}

}
