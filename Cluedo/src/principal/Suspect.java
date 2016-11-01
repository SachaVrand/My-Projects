package principal;
import javax.swing.Icon;

/**
 * Classe repr�sentant une carte de type Suspect.
 * @author Sacha et Clement
 *
 */
public class Suspect extends Carte{

	/**
	 * Instancie un nouveau Suspect avec le nom et l'image pass�s en param�tres.
	 * @see Carte#Carte(String, Icon)
	 * @param nom Nom de la carte. Ex: Mrs_White.
	 * @param img Image correspondant � la carte.
	 */
	public Suspect(String nom, Icon img) {
		super(nom, img);
	}

}
