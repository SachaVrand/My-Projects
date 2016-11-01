package donnees;

import java.util.ArrayList;

/**
 * Classe représentant tous les IBloc qu'une combinaison à trouvée.
 * Permet de connaitre les combinaisons et les briques touchées.
 * Permet de stocker les deux en un objet.
 * @author Sacha
 *
 */
public class CombiBrick {
	
	/**
	 * Liste de toutes les combinaisons trouvées.
	 */
	private volatile ArrayList<Combinaison> allCombinaisons;
	
	/**
	 * Liste des briques qui vont se reduire suite aux combinaisons.
	 */
	private volatile ArrayList<Brique> briquesImpliquees;
	
	/**
	 * Constructeur de {@link CombiBrick}.
	 * @param allCombinaisons Toutes les combinaisons.
	 * @param briquesImpliquees Toues les briques touchées.
	 */
	public CombiBrick(ArrayList<Combinaison> allCombinaisons, ArrayList<Brique> briquesImpliquees)
	{
		this.allCombinaisons = allCombinaisons;
		this.briquesImpliquees = briquesImpliquees;
	}
	
	/**
	 * Méthode qui retourne toutes les combinaisons retenues dans ce {@link CombiBrick}.
	 * @return Toutes les cmbinaisons retenues.
	 */
	public ArrayList<Combinaison> getAllCombinaisons() {
		return allCombinaisons;
	}
	
	/**
	 * Méthode qui retourne toutes les briques touchées par les combinaisons.
	 * @return Toutes les briques touchées.
	 */
	public ArrayList<Brique> getBriquesImpliquees() {
		return briquesImpliquees;
	}

}
