package principal;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * Enum�ration des differentes cartes de type Lieu pr�sentes dans le jeu cluedo.
 * @author Sacha et Clement
 *
 */
public enum Lieux {
	
	/**
	 * Repr�sente la carte Kitchen
	 */
	Kitchen(new ImageIcon("Images/Kitchen.jpg")),
	
	/**
	 * Repr�sente la carte Ballroom
	 */
	Ballroom(new ImageIcon("Images/Ballroom.jpg")),
	
	/**
	 * Repr�sente la carte Conservatory
	 */
	Conservatory(new ImageIcon("Images/Conservatory.jpg")),
	
	/**
	 * Repr�sente la carte Dining_room
	 */
	DiningRoom(new ImageIcon("Images/DiningRoom.jpg")),
	
	/**
	 * Repr�sente la carte Billard_room
	 */
	BillardRoom(new ImageIcon("Images/BillardRoom.jpg")),
	
	/**
	 * Repr�sente la carte Library
	 */
	Library(new ImageIcon("Images/Library.jpg")),
	
	/**
	 * Repr�sente la carte Lounge
	 */
	Lounge(new ImageIcon("Images/Lounge.jpg")),
	
	/**
	 * Repr�sente la carte Hall
	 */
	Hall(new ImageIcon("Images/Hall.jpg")),
	
	/**
	 * Repr�sente la carte Study
	 */
	Study(new ImageIcon("Images/Study.jpg"));
	
	/**
	 * Repr�sente l'image associ�e � la carte.
	 */
	private Icon img;
	
	/**
	 * Constructeur d l'�num�ration Lieux
	 * @param img Icon repr�sentant l'iamage de la carte.
	 */
	Lieux(Icon img)
	{
		this.img = img;
	}
	
	/**
	 * M�thode qui retourne l'image associ�e � la carte sous la forme d'un Icon
	 * @return Icon repr�sentant l'image associ�e � la carte.
	 */
	public Icon getImage()
	{
		return this.img;
	}

}
