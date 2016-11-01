import java.io.Serializable;

/**
 * Classe qui représente un pion sauvegardé, pour alléger la sauvegarde d'un pion.
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
	 * Numero du tour auquel il a été joué.
	 */
	private int coup;
	
	/**
	 * Instancie un nouveau PionSauvegarde avec les paramètres.
	 * @param jouePar Couleur qui a joué le pion.
	 * @param couleurFin Couleur qu'a pris le pion à la fin.
	 * @param coup Numero du tour auquel il a été joué.
	 */
	public PionSauvegarde(Couleur jouePar, Couleur couleurFin, int coup)
	{
		this.jouePar = jouePar;
		this.couleurFin = couleurFin;
		this.coup = coup;
	}

	/**
	 * Méthode qui retourne la couleur qui à joué le pion.
	 * @return retourne la couleur qui à joué le pion.
	 */
	public Couleur getJouerPar() {
		return jouePar;
	}

	/**
	 * Méthode qui retourne la couleur qu'a pris le pion à la fin.
	 * @return retourne la couleur qu'a pris le pion à la fin.
	 */
	public Couleur getCouleur() {
		return couleurFin;
	}

	/**
	 * Méthode qui retourne le numero du tour auquel il a été joué.
	 * @return le numero du tour auquel il a été joué.
	 */
	public int getCoup() {
		return coup;
	}
}
