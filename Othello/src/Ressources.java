import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * Classe permettant d'acceder aux chemins des différentes images du projet.
 * @author Vrand
 *
 */
public class Ressources {
	
	/**
	 * Icon du passage de tour de noir
	 */
	public static Icon noirPasseTour = new ImageIcon(OthelloMain.imagePath + "NoirPasseTour.png");
	
	/**
	 * Icon du passe de tour de blanc.
	 */
	public static Icon blancPasseTour = new ImageIcon(OthelloMain.imagePath + "BlancPasseTour.png");
	
	/**
	 * Chemin pour l'image d'un pion noir.
	 */
	public static String cheminImageNoir = OthelloMain.imagePath + "noir.png";
	
	/**
	 * Chemin pour l'image d'un pion blanc.
	 */
	public static String cheminImageBlanc = OthelloMain.imagePath + "blanc.png";
	
	/**
	 * Chemin pour l'image vide d'un pion.
	 */
	public static String cheminImageVide = OthelloMain.imagePath + "vide.png";
	
	/**
	 * Chemin pour l'image d'un pion en surbrillance.
	 */
	public static String cheminImageSurbrillance = OthelloMain.imagePath + "surbrillance.png";

	/**
	 * Image du menu principal.
	 */
	public static Icon imageTitreJeu = new ImageIcon("Images/TitreMenuPrincipal.png");
	
	/**
	 * Image du titre du choix de la difficulté.
	 */
	public static Icon imageTitreChoixDifficulté = new ImageIcon("Images/MessageChoixDiff.png");
	
	/**
	 * Image du bouton retour en arriere dans le menu du replay
	 */
	public static Icon imageBtbnRetourArriere = new ImageIcon("Images/Retour.png");
	
	/**
	 * Image du bouton avance dans le menu du replay
	 */
	public static Icon imageBtbnAvance = new ImageIcon("Images/Avance.png");
	
}
