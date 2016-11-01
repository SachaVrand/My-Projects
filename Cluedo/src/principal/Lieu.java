package principal;
import javax.swing.Icon;

/**
 * Classe repr�sentant une carte de type Lieu.
 * @author Sacha et Clement
 *
 */
public class Lieu extends Carte{

	/**
	 * Instancie un nouveau Lieu avec le nom et l'image pass�s en param�tres.
	 * @see Carte#Carte(String, Icon)
	 * @param nom Nom de la carte. Ex: Kitchen.
	 * @param img Image correspondant � la carte.
	 */
	public Lieu(String nom, Icon img) {
		super(nom, img);
	}

}
