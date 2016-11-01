import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * Classe représentant le pion présent sur le plateau de jeu de l'othello.
 * @author Vrand
 *
 */
public class Pion extends JButton implements Serializable{

	private static final long serialVersionUID = 3211930930212292671L;
	
	/**
	 * Couleur du actuelle du pion, après retournements ou autres.
	 */
	private Couleur couleur;
	
	/**
	 * Booléen qui permet de savoir si ce pion est jouable. true si oui, sinon false.
	 */
	private boolean estJouable;
	
	/**
	 * Entier permettant de savoir à quel tour le pion a été joué.
	 */
	private int coup;
	
	/**
	 * Couleur qui a jouée ce pion.
	 */
	private Couleur jouerPar;
	
	/**
	 * Entier répsentant la ligne où se trouve ce pion sur la grille de jeu.
	 */
	private int ligne;
	
	/**
	 * Entier répsentant la colonne où se trouve ce pion sur la grille de jeu.
	 */
	private int colonne;
	
	/**
	 * Instancie un nouveau bouton avec pour image la couleur passée en paramètre.
	 * @param ligne sur laquelle le pion se trouve sur le plateau (de 0 à 7)
	 * @param colonne sur laquelle le pion se trouve sur le plateau (de 0 à 7)
	 * @param couleur associé au pion.
	 */
	public Pion(int ligne, int colonne, Couleur couleur)
	{
		super();
		this.setContentAreaFilled(false);
		this.ligne = ligne;
		this.colonne = colonne;
		this.estJouable = false;
		this.couleur = couleur;
		this.setIcon(new ImageIcon(couleur.getImagePath()));
	}
	
	/**
	 * Méthode qui retourne la ligne de la grille sur laquelle se trouve le pion.
	 * @return un entier représentant la ligne du pion sur la grille.
	 */
	public int getLigne()
	{
		return this.ligne;
	}
	
	/**
	 * Méthode qui retourne la colonne de la grille sur laquelle se trouve le pion.
	 * @return un entier représentant la colonne du pion sur la grille.
	 */
	public int getColonne()
	{
		return this.colonne;
	}
	
	/**
	 * Méthode qui retourne la couleur actuelle du pion.
	 * @return la couleur actuelle du pion.
	 */
	public Couleur getCouleur()
	{
		return this.couleur;
	}
	
	/**
	 * Méthode qui retourne un booléen représentant si le pion est jouable ou non.
	 * @return true si le pion est jouable, sinon false.
	 */
	public boolean getEstJouable()
	{
		return this.estJouable;
	}
	
	/**
	 * Méthode qui change la couleur du pion ainsi que l'image associé au pion en fonction de la couleur passée en paramètre.
	 * @param couleur nouvelle couleur du pion.
	 */
	public void setCouleur(Couleur couleur)
	{
		this.couleur = couleur;
		this.setIcon(new ImageIcon(couleur.getImagePath()));
	}
	
	/**
	 * Méthode qui set si le pion est jouable ou non.
	 * @param b true pour que le pion soit jouable sinon false.
	 */
	public void setEstJouable(boolean b)
	{
		this.estJouable = b;
	}
	
	/**
	 * Méthode qui set la variable estJouable du pion.
	 * Change la couleur et l'image du pion en fonction du paramètre passé.
	 * Si true, la couleur sera set à SURBRILLANCE, sinon à VIDE.
	 * @param b
	 */
	public void changementEstJouable(boolean b)
	{
		this.estJouable = b;
		if(b)
		{
			this.setCouleur(Couleur.SURBRILLANCE);
		}
		else
		{
			this.setCouleur(Couleur.VIDE);
		}
		
	}
	
	/**
	 * Méthode qui set le tour auquel a été joué le pion.
	 * @param coup entier représentant le tour auquel a été joué le pion.
	 */
	public void setCoup(int coup)
	{
		this.coup = coup;
	}
	
	/**
	 * Méthode qui set la couleur par laquelle a été joué le pion.
	 * @param c la couleur qui a jouée le pion.
	 */
	public void setJouerPar(Couleur c)
	{
		jouerPar = c;
	}
	
	/**
	 * Méthode qui retourne la couleur qui a jouée la pion.
	 * @return la couleur qui a jouée le pion.
	 */
	public Couleur getJouerPar()
	{
		return this.jouerPar;
	}
	
	/**
	 * Méthode qui retourne le tour auquel a été joué le pion.
	 * @return le tour auquel a été joué le pion.
	 */
	public int getCoup()
	{
		return this.coup;
	}
	
}
