import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * Classe repr�sentant le pion pr�sent sur le plateau de jeu de l'othello.
 * @author Vrand
 *
 */
public class Pion extends JButton implements Serializable{

	private static final long serialVersionUID = 3211930930212292671L;
	
	/**
	 * Couleur du actuelle du pion, apr�s retournements ou autres.
	 */
	private Couleur couleur;
	
	/**
	 * Bool�en qui permet de savoir si ce pion est jouable. true si oui, sinon false.
	 */
	private boolean estJouable;
	
	/**
	 * Entier permettant de savoir � quel tour le pion a �t� jou�.
	 */
	private int coup;
	
	/**
	 * Couleur qui a jou�e ce pion.
	 */
	private Couleur jouerPar;
	
	/**
	 * Entier r�psentant la ligne o� se trouve ce pion sur la grille de jeu.
	 */
	private int ligne;
	
	/**
	 * Entier r�psentant la colonne o� se trouve ce pion sur la grille de jeu.
	 */
	private int colonne;
	
	/**
	 * Instancie un nouveau bouton avec pour image la couleur pass�e en param�tre.
	 * @param ligne sur laquelle le pion se trouve sur le plateau (de 0 � 7)
	 * @param colonne sur laquelle le pion se trouve sur le plateau (de 0 � 7)
	 * @param couleur associ� au pion.
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
	 * M�thode qui retourne la ligne de la grille sur laquelle se trouve le pion.
	 * @return un entier repr�sentant la ligne du pion sur la grille.
	 */
	public int getLigne()
	{
		return this.ligne;
	}
	
	/**
	 * M�thode qui retourne la colonne de la grille sur laquelle se trouve le pion.
	 * @return un entier repr�sentant la colonne du pion sur la grille.
	 */
	public int getColonne()
	{
		return this.colonne;
	}
	
	/**
	 * M�thode qui retourne la couleur actuelle du pion.
	 * @return la couleur actuelle du pion.
	 */
	public Couleur getCouleur()
	{
		return this.couleur;
	}
	
	/**
	 * M�thode qui retourne un bool�en repr�sentant si le pion est jouable ou non.
	 * @return true si le pion est jouable, sinon false.
	 */
	public boolean getEstJouable()
	{
		return this.estJouable;
	}
	
	/**
	 * M�thode qui change la couleur du pion ainsi que l'image associ� au pion en fonction de la couleur pass�e en param�tre.
	 * @param couleur nouvelle couleur du pion.
	 */
	public void setCouleur(Couleur couleur)
	{
		this.couleur = couleur;
		this.setIcon(new ImageIcon(couleur.getImagePath()));
	}
	
	/**
	 * M�thode qui set si le pion est jouable ou non.
	 * @param b true pour que le pion soit jouable sinon false.
	 */
	public void setEstJouable(boolean b)
	{
		this.estJouable = b;
	}
	
	/**
	 * M�thode qui set la variable estJouable du pion.
	 * Change la couleur et l'image du pion en fonction du param�tre pass�.
	 * Si true, la couleur sera set � SURBRILLANCE, sinon � VIDE.
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
	 * M�thode qui set le tour auquel a �t� jou� le pion.
	 * @param coup entier repr�sentant le tour auquel a �t� jou� le pion.
	 */
	public void setCoup(int coup)
	{
		this.coup = coup;
	}
	
	/**
	 * M�thode qui set la couleur par laquelle a �t� jou� le pion.
	 * @param c la couleur qui a jou�e le pion.
	 */
	public void setJouerPar(Couleur c)
	{
		jouerPar = c;
	}
	
	/**
	 * M�thode qui retourne la couleur qui a jou�e la pion.
	 * @return la couleur qui a jou�e le pion.
	 */
	public Couleur getJouerPar()
	{
		return this.jouerPar;
	}
	
	/**
	 * M�thode qui retourne le tour auquel a �t� jou� le pion.
	 * @return le tour auquel a �t� jou� le pion.
	 */
	public int getCoup()
	{
		return this.coup;
	}
	
}
