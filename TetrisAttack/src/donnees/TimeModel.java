package donnees;

import java.util.Observable;

/**
 * Classe représentant le temps en jeu.
 * @author Sacha
 *
 */
public class TimeModel extends Observable{
	
	/**
	 * Entier représentant le temps écoulée depuis le début de la manche.
	 */
	private int tempsEcoulee;
	
	/**
	 * Constructeur de {@link TimeModel}. Instancie un nouveau {@link TimeModel} avec le temps écoulée à 0.
	 */
	public TimeModel()
	{
		this.tempsEcoulee = 0;
	}
	
	@Override
	public String toString() {
		int min,sec;
		String minStr, secStr;
		
		min = tempsEcoulee/60;
		sec = tempsEcoulee%60;
		
		if(min>9) return "9'99";
		
		minStr = Integer.toString(min);
		secStr = (sec<10)?("0" + sec):(Integer.toString(sec));
		
		return minStr + "'" + secStr;
	}
	
	/**
	 * Méthode qui permet de remettre à zéro le temps écoulée. Notifie les observateurs du changement.
	 */
	public void resetTime()
	{
		this.tempsEcoulee = 0;
		this.setChanged();
		this.notifyObservers();
	}
	
	/**
	 * Méthode qui permet d'ajouter du temps écoulée. Notifie les observateurs.
	 * @param tempsSupp Temps écoulée supplémentaire à ajouter.
	 */
	public void update(int tempsSupp)
	{
		this.tempsEcoulee += tempsSupp;
		this.setChanged();
		this.notifyObservers();
	}
	
	/**
	 * Méthode qui retourne le temps écoulée.
	 * @return Le temps écoulée.
	 */
	public int getTempsEcoulee() {
		return tempsEcoulee;
	}
}
