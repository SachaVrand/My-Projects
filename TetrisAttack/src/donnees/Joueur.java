package donnees;

import java.util.Observable;

/**
 * Classe représentant un joueur de la partie.
 * @author Sacha
 *
 */
public abstract class Joueur extends Observable{

	/**
	 * Id permettant de différencier les joueurs.
	 */
	private int idJoueur;
	
	/**
	 * Score du joueur dans la partie.
	 */
	private Score score;
	
	/**
	 * {@link Grille} sur laquelle joue le joueur.
	 */
	private Grille grille;
	
	/**
	 * Entier représentant le nombre de manches gagnées dans la partie.
	 */
	private int manchesGagnees;
	
	/**
	 * Constructeur de {@link Joueur}. Instancie un nouveau joueur avec une nouvelle grille, un nouveau score et son nombre de manches gagnées à 0.
	 * @param idJoueur L'id du joueur.
	 */
	public Joueur(int idJoueur) {
		this.idJoueur = idJoueur;
		this.score = new Score();
		this.grille = new Grille();
		this.manchesGagnees = 0;
	}
	
	/**
	 * Méthode retournant la {@link Grille} du joueur.
	 * @return la {@link Grille} du joueur.
	 */
	public Grille getGrille() {
		return grille;
	}
	
	/**
	 * Méthode retournant l'id du joueur.
	 * @return l'id du joueur.
	 */
	public int getIdJoueur() {
		return idJoueur;
	}
	
	/**
	 * Méthode retournant le {@link Score} du joueur.
	 * @return le {@link Score} du joueur.
	 */
	public Score getScore() {
		return score;
	}
	
	/**
	 * Méthode retournant le nombre de manches gagnées du joueur.
	 * @return Le nombre de manches gagnées.
	 */
	public int getManchesGagnees() {
		return manchesGagnees;
	}
	
	/**
	 * Méthode qui permet d'ajouter une manche gagnées au joueur. Notifie les observateurs de ce changement.
	 */
	public void ajouterMancheGagnee()
	{
		manchesGagnees++;
		super.setChanged();
		super.notifyObservers("Gagne " + manchesGagnees + " manche");
	}
}
