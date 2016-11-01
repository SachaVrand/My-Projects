package principal;
import javax.swing.Icon;

/**
 * Classe repr�sentant une carte de type Arme.
 * @author Sacha et Clement
 *
 */
public class Arme extends Carte{

	/**
	 * Instancie une nouvelle Arme avec le nom et l'image pass�s en param�tres.
	 * @see Carte#Carte(String, Icon)
	 * @param nom Nom de la carte. Ex: Dagger.
	 * @param img Image correspondant � la carte.
	 */
	public Arme(String nom, Icon img) {
		super(nom, img);
	}

}
