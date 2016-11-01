package principal;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * Enum�ration des differentes cartes de type Arme pr�sentes dans le jeu cluedo.
 * @author Sacha et Clement
 *
 */
public enum Armes {

	/**
	 * Repr�sente la carte Candlestick
	 */
	Candlestick(new ImageIcon("Images/Candlestick.jpg")),
	
	/**
	 * Repr�sente la carte Dagger
	 */
	Dagger(new ImageIcon("Images/Dagger.jpg")),
	
	/**
	 * Repr�sente la carte Lead_pipe
	 */
	LeadPipe(new ImageIcon("Images/LeadPipe.jpg")),
	
	/**
	 * Repr�sente la carte Revolver
	 */
	Revolver(new ImageIcon("Images/Revolver.jpg")),
	
	/**
	 * Repr�sente la carte Rope
	 */
	Rope(new ImageIcon("Images/Rope.jpg")),
	
	/**
	 * Repr�sente la carte Spanner
	 */
	Spanner(new ImageIcon("Images/Spanner.jpg"));
	
	/**
	 * Image de type Icon repr�sentant la carte.
	 */
	private Icon img;
	
	/**
	 * Constructeur de l'�num�ration.
	 * @param img Image de type Icon repr�sentant la carte.
	 */
	Armes(Icon img)
	{
		this.img = img;
	}
	
	/**
	 * M�thode qui retourne l'image associ�e � la carte sous la forme d'un Icon.
	 * @return Icon repr�sentant l'image associ�e � la carte.
	 */
	public Icon getImage()
	{
		return this.img;
	}
}
