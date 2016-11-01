import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Classe repr�sentant un partie sauvegard�e.
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
	 * Date a laquelle a �t� jou�e la partie sauvegard�e.
	 */
	private Date date;
	
	/**
	 * Tableau de pions � deux dimensions repr�sentant la grille � la fin de la partie.
	 */
	private PionSauvegarde[][] tab = null;
	
	/**
	 * Chaine repr�sentant le type de partie qui a �t� jou�.
	 */
	private String typePartie;
	
	/**
	 * Chaine repr�sentant la dur�e total de la partie sauvegard�e.
	 */
	private String tempsDeJeuTotal;
	
	/**
	 * Instancie une nouvelle sauvegarde.
	 * @param date Date � laquelle s'est pass�e la partie.
	 * @param tab repr�sentant la grille de pions � la fin de la partie.
	 * @param typePartie repr�sentant le tyep de partie jou�e(JeuJcIA..,JeuJcJ).
	 * @param tempsDeJeuTotal repr�sentant la dur�e de la partie.
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
	 * M�thode qui retourne la date de la sauvegarde sous la forme d'un objet Date.
	 * @return date de la sauvegarde.
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * M�thode qui set la date de la sauvegarde � partir d'un objet Date.
	 * @param date de la sauvegarde.
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * M�thode qui retourne le tableau de pion de la sauvegarde.
	 * @return un tableau de pions � deux dimensions.
	 */
	public PionSauvegarde[][] getTab() {
		return tab;
	}

	/**
	 * M�thode qui set le tableau de pion de la sauvegarde.
	 * @param tab de pions � deux dimensions.
	 */
	public void setTab(PionSauvegarde[][] tab) {
		this.tab = tab;
	}
	
	/**
	 * M�thode qui retourne le type de partie de la sauvegarde sous la forme d'une chaine de caract�res.
	 * @return retourne le type de partie de la sauvegarde sous la forme d'une chaine de caract�res.
	 */
	public String getTypePartie() {
		return typePartie;
	}

	/**
	 * M�thode qui set le type de partie de la sauvegarde.
	 * @param typePartie chaine de caract�res repr�sentant le type de partie de la sauvegarde.
	 */
	public void setTypePartie(String typePartie) {
		this.typePartie = typePartie;
	}

	/**
	 * M�thode qui retourne le score final de la partie sauvegard�e sous la forme d'une chaine de caract�res : "N" scoresNoir " - "B" scoreBlanc 
	 * @return chaine de caract�res repr�sentant le score final.
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
	 * M�thode qui retourne le score final de la partie sous la forme d'un tableau d'entiers.
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
	 * M�thode qui retourne la dur�e de la partie sauvegard�e.
	 * @return chaine caract�re repr�sentant la dur�e total de la partie sauvegard�e.
	 */
	public String getTempsDeJeuTotal() {
		return tempsDeJeuTotal;
	}

	/**
	 * M�thode qui set la dur�e total de la partie sauvegard�e.
	 * @param tempsDeJeuTotal chaine de caract�re repr�sentant la dur�e total de la partie.
	 */
	public void setTempsDeJeuTotal(String tempsDeJeuTotal) {
		this.tempsDeJeuTotal = tempsDeJeuTotal;
	}
	
	/**
	 * M�thode qui retourne la liste des plateaux et autres � afficher pour le replay.
	 * @return la liste des plateaux et autres � afficher pour le replay.
	 */
	public ArrayList<Object> getListeAJouer()
	{
		return this.listeAJouer;
	}
}
