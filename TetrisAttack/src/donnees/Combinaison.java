package donnees;

import java.util.HashSet;
import java.util.Set;

/**
 * Classe représentant une combinaison.
 * Voir règles du jeu.
 * @author Sacha
 *
 */
public class Combinaison {
	
	/**
	 * Ensemble des blocs qui sont dans la combinaison.
	 */
	private Set<Bloc> blocsImpliques;
	
	/**
	 * Taille de la combinaison.
	 */
	private int taille;
	
	/**
	 * Constructeur de {@link Combinaison}. Instancie une {@link Combinaison} avec comme taille la size de l'ensemble des blocs dans la combinaison.
	 * @param blocsImpliques Set des blocs dans la combinaison.
	 */
	public Combinaison(Set<Bloc> blocsImpliques) {
		this.taille = blocsImpliques.size();
		this.blocsImpliques = blocsImpliques;
	}
	
	/**
	 * Méthode qui retourne la taille de cette {@link Combinaison}.
	 * @return Entier représentant la taille de cette combinaison.
	 */
	public int getTaille() {
		return taille;
	}
	
	/**
	 * Méthode qui retourne l'ensemble des blocs dans la combinaison.
	 * @return l'ensemble des blocs dans la combinaison.
	 */
	public Set<Bloc> getBlocsImpliques() {
		return blocsImpliques;
	}
	
	/**
	 * Fonction qui permet de crée un combinaison à partir de deux autres combinaisons.
	 * Pour représenter les combinaisons croisée de même couleur.
	 * @param c1 Première combinaison.
	 * @param c2 Deuxième combinaison.
	 * @return La nouvelle combinaison avec comme blocs dans la combinaison l'union des deux ensemble de blocs. Et comme taille la taille de ce nouvelle ensemble.
	 */
	public static Combinaison createNewCombinaison(Combinaison c1, Combinaison c2)
	{
		Set<Bloc> newBlocsSet = new HashSet<>();
		newBlocsSet.addAll(c1.getBlocsImpliques());
		newBlocsSet.addAll(c2.getBlocsImpliques());
		return new Combinaison(newBlocsSet);
	}
}
