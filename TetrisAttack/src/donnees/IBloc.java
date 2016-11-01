package donnees;

/**
 * Interface permettant de regrouper les blocs et les briques.
 * @author Sacha
 *
 */
public interface IBloc {
	
	/**
	 * Méthode qui fait bouger le Ibloc vers le bas si possible.
	 * @param grille Grille sur laquelle il descend
	 * @return retourne le {@link IBloc} qui tombe encore après, null si rien.
	 */
	public IBloc moveDown(Grille grille);

}
