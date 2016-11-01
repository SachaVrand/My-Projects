package donnees;

import java.sql.Time;
import java.util.Observable;

/**
 * Classe représentant une partie du mode versus.
 * @author Sacha
 *
 */
public class PartieVersus extends Observable{

	/**
	 * Joueur représentant le premier joueur.
	 */
	private Joueur joueur1;
	
	/**
	 * Joueur représentant le deuxième joueur.
	 */
	private Joueur joueur2;
	
	/**
	 * {@link Time} représentant le temps écoulée depuis le début de la manche.
	 */
	private TimeModel time;
	
	/**
	 * {@link EtatPartie} représentant l'état de la partie.
	 */
	private EtatPartie etatPartie;
	
	
	/**
	 * Constructeur de {@link PartieVersus}.
	 * @param joueur1 Premier Joueur.
	 * @param joueur2 Deuxième Joueur.
	 */
	public PartieVersus(Joueur joueur1, Joueur joueur2) {
		this.time = new TimeModel();
		this.joueur1 = joueur1;
		this.joueur2 = joueur2;
	}
	
	/**
	 * Méthode qui retourne le TimeModel de cette {@link PartieVersus}.
	 * @return le TimeModel de cette {@link PartieVersus}.
	 */
	public TimeModel getTime() {
		return time;
	}
	
	/**
	 * Méthode qui retourne le premier {@link Joueur} de la partie.
	 * @return Le premier {@link Joueur} de la partie.
	 */
	public Joueur getJoueur1() {
		return joueur1;
	}
	
	/**
	 * Méthode qui retourne le deuxième {@link Joueur} de la partie.
	 * @return Le deuxième {@link Joueur} de la partie.
	 */
	public Joueur getJoueur2() {
		return joueur2;
	}
	
	/**
	 * Méthode qui retourne l'état de la partie.
	 * @return L'état de la partie.
	 */
	public EtatPartie getEtatPartie() {
		return etatPartie;
	}
	
	/**
	 * Méthode set l'état de la partie. Notifie les observateurs avec en paramètre l'ancienne état.
	 * @param etatPartie Nouvelle état de la partie.
	 */
	public void setEtatPartie(EtatPartie etatPartie) {
		this.etatPartie = etatPartie;
		super.setChanged();
		super.notifyObservers();
	}
}
