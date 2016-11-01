import java.io.Serializable;

/**
 * Classe qui repr�sente un pion sauvegard�, pour all�ger la sauvegarde d'un pion.
 * @author Vrand
 *
 */
public class PionSauvegarde implements Serializable{
	
	private static final long serialVersionUID = -2956233111001137719L;

	/**
	 * Couleur qui a joue ce pion.
	 */
	private Couleur jouePar;
	
	/**
	 * Couleur de fin qu'a pris le pion.
	 */
	private Couleur couleurFin;
	
	/**
	 * Numero du tour auquel il a �t� jou�.
	 */
	private int coup;
	
	/**
	 * Instancie un nouveau PionSauvegarde avec les param�tres.
	 * @param jouePar Couleur qui a jou� le pion.
	 * @param couleurFin Couleur qu'a pris le pion � la fin.
	 * @param coup Numero du tour auquel il a �t� jou�.
	 */
	public PionSauvegarde(Couleur jouePar, Couleur couleurFin, int coup)
	{
		this.jouePar = jouePar;
		this.couleurFin = couleurFin;
		this.coup = coup;
	}

	/**
	 * M�thode qui retourne la couleur qui � jou� le pion.
	 * @return retourne la couleur qui � jou� le pion.
	 */
	public Couleur getJouerPar() {
		return jouePar;
	}

	/**
	 * M�thode qui retourne la couleur qu'a pris le pion � la fin.
	 * @return retourne la couleur qu'a pris le pion � la fin.
	 */
	public Couleur getCouleur() {
		return couleurFin;
	}

	/**
	 * M�thode qui retourne le numero du tour auquel il a �t� jou�.
	 * @return le numero du tour auquel il a �t� jou�.
	 */
	public int getCoup() {
		return coup;
	}
}
