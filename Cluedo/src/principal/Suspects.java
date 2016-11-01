package principal;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * Enum�ration des differentes cartes de type Suspect pr�sentes dans le jeu cluedo.
 * @author Sacha et Clement
 *
 */
public enum Suspects {

	/**
	 * Repr�sente la carte Miss_Scarlett
	 */
	Scarlett(new ImageIcon("Images/Scarlett.jpg")),
	
	/**
	 * Repr�sente la carte Colonel_Mustard
	 */
	Mustard(new ImageIcon("Images/Mustard.jpg")),
	
	/**
	 * Repr�sente la carte Mrs_White
	 */
	White(new ImageIcon("Images/White.jpg")),
	
	/**
	 * Repr�sente la carte Professor_Plum
	 */
	Plum(new ImageIcon("Images/Plum.jpg")),
	
	/**
	 * Repr�sente la carte Mrs_Peacock
	 */
	Peacock(new ImageIcon("Images/Peacock.jpg")),
	
	/**
	 * Repr�sente la carte Reverend_Green
	 */
	Green(new ImageIcon("Images/Green.jpg"));
	
	/**
	 * Image associ�e � la carte.
	 */
	private Icon img;
	
	/**
	 * Constructeur de l'�num�ration.
	 * @param img Icon correspondant � l'image de la carte.
	 */
	Suspects(Icon img)
	{
		this.img = img;
	}
	
	/**
	 * M�thode qui retourne l'image associ�e sous la forme d'un Icon.
	 * @return Icon repr�sentant l'image de la carte.
	 */
	public Icon getImage()
	{
		return this.img;
	}
}
