package donnees;

import java.util.Observable;

/**
 * Classe qui permet de gérer le score d'un joueur.
 * @author Sacha
 *
 */
public class Score extends Observable{
	
	/**
	 * Entier représentant le score total du joueur.
	 */
	private int score;
	
	/**
	 * Constructeur de {@link Score}. Instancie un {@link Score} à 0.
	 */
	public Score() {
		this.score = 0;
	}
	
	/**
	 * Méthode qui permet de set le score. Notifie les observateurs.
	 * @param score Nouveau score.
	 */
	public void setScore(int score) {
		this.score = score;
		this.setChanged();
		this.notifyObservers();
	}
	
	/**
	 * Méthode qui per met d'ajouter du score.
	 * @param scoreSup score supplémentaire à ajouter.
	 */
	public void addScore(int scoreSup)
	{
		this.score += scoreSup;
		this.setChanged();
		this.notifyObservers();
	}
	
	@Override
	public String toString() {
		if(score >= 10000)
			return "9999";
		
		String strScore = Integer.toString(score);
		for(int i = strScore.length(); i < 4; i++)
		{
			strScore = "0" + strScore;
		}
		return strScore;
	}

}
