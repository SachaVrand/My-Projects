import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Classe représentant un partie sauvegardée.
 * @author Vrand
 *
 */
public class Sauvegarde implements Serializable{

	private static final long serialVersionUID = 4785110236661412922L;
	
	/**
	 * Liste des plateaux et passage tours succesifs pour le replay.
	 */
	ArrayList<Object> listeAJouer = null;
	
	/**
	 * Date a laquelle a été jouée la partie sauvegardée.
	 */
	private Date date;
	
	/**
	 * Tableau de pions à deux dimensions représentant la grille à la fin de la partie.
	 */
	private PionSauvegarde[][] tab = null;
	
	/**
	 * Chaine représentant le type de partie qui a été joué.
	 */
	private String typePartie;
	
	/**
	 * Chaine représentant la durée total de la partie sauvegardée.
	 */
	private String tempsDeJeuTotal;
	
	/**
	 * Instancie une nouvelle sauvegarde.
	 * @param date Date à laquelle s'est passée la partie.
	 * @param tab représentant la grille de pions à la fin de la partie.
	 * @param typePartie représentant le tyep de partie jouée(JeuJcIA..,JeuJcJ).
	 * @param tempsDeJeuTotal représentant la durée de la partie.
	 */
	public Sauvegarde(Date date, PionSauvegarde[][] tab, String typePartie, String tempsDeJeuTotal,ArrayList<Object> lst)
	{
		this.date = date;
		this.setTab(tab);
		listeAJouer = lst;
		this.typePartie = typePartie;
		this.tempsDeJeuTotal = tempsDeJeuTotal;
	}

	/**
	 * Méthode qui retourne la date de la sauvegarde sous la forme d'un objet Date.
	 * @return date de la sauvegarde.
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Méthode qui set la date de la sauvegarde à partir d'un objet Date.
	 * @param date de la sauvegarde.
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * Méthode qui retourne le tableau de pion de la sauvegarde.
	 * @return un tableau de pions à deux dimensions.
	 */
	public PionSauvegarde[][] getTab() {
		return tab;
	}

	/**
	 * Méthode qui set le tableau de pion de la sauvegarde.
	 * @param tab de pions à deux dimensions.
	 */
	public void setTab(PionSauvegarde[][] tab) {
		this.tab = tab;
	}
	
	/**
	 * Méthode qui retourne le type de partie de la sauvegarde sous la forme d'une chaine de caractères.
	 * @return retourne le type de partie de la sauvegarde sous la forme d'une chaine de caractères.
	 */
	public String getTypePartie() {
		return typePartie;
	}

	/**
	 * Méthode qui set le type de partie de la sauvegarde.
	 * @param typePartie chaine de caractères représentant le type de partie de la sauvegarde.
	 */
	public void setTypePartie(String typePartie) {
		this.typePartie = typePartie;
	}

	/**
	 * Méthode qui retourne le score final de la partie sauvegardée sous la forme d'une chaine de caractères : "N" scoresNoir " - "B" scoreBlanc 
	 * @return chaine de caractères représentant le score final.
	 */
	public String getScore()
	{
		int[] scores = new int[2];
		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				if(tab[i][j].getCouleur().equals(Couleur.NOIR))
				{
					scores[0]++;
				}
				else if(tab[i][j].getCouleur().equals(Couleur.BLANC))
				{
					scores[1]++;
				}
			}
		}
		return "N" + scores[0] + " - B" + scores[1];
	}
	
	/**
	 * Méthode qui retourne le score final de la partie sous la forme d'un tableau d'entiers.
	 * @return tab[0]->score noir, tab[1]->score blanc
	 */
	public int[] getScoreIntTab()
	{
		int[] scores = new int[2];
		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				if(tab[i][j].getCouleur().equals(Couleur.NOIR))
				{
					scores[0]++;
				}
				else if(tab[i][j].getCouleur().equals(Couleur.BLANC))
				{
					scores[1]++;
				}
			}
		}
		return scores;
	}

	/**
	 * Méthode qui retourne la durée de la partie sauvegardée.
	 * @return chaine caractère représentant la durée total de la partie sauvegardée.
	 */
	public String getTempsDeJeuTotal() {
		return tempsDeJeuTotal;
	}

	/**
	 * Méthode qui set la durée total de la partie sauvegardée.
	 * @param tempsDeJeuTotal chaine de caractère représentant la durée total de la partie.
	 */
	public void setTempsDeJeuTotal(String tempsDeJeuTotal) {
		this.tempsDeJeuTotal = tempsDeJeuTotal;
	}
	
	/**
	 * Méthode qui retourne la liste des plateaux et autres à afficher pour le replay.
	 * @return la liste des plateaux et autres à afficher pour le replay.
	 */
	public ArrayList<Object> getListeAJouer()
	{
		return this.listeAJouer;
	}
}
