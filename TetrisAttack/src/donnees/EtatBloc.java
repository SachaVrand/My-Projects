package donnees;

/**
 * Classe représentant les états que peut prendre un bloc au cours d'une partie.
 * @author Sacha
 *
 */
public enum EtatBloc {
	
	NORMAL, 
	COMBINAISON_ANIMATION, 
	WARNING_ANIMATION, 
	FIN, 
	FALLING;
	
	/**
	 * Méthode qui retourne si ce bloc peut tomber.
	 * @return Vrai s'il peut tomber. faux sinon.
	 */
	public boolean canFall()
	{
		return this.equals(EtatBloc.NORMAL) || this.equals(EtatBloc.WARNING_ANIMATION);
	}
	
	/**
	 * Méthode qui retourne si un bloc peut être échangé.
	 * @return Vrai s'il peut être échanger, faux sinon.
	 */
	public boolean canSwitch()
	{
		return this.equals(EtatBloc.NORMAL) || this.equals(EtatBloc.WARNING_ANIMATION);
	}
	
	/**
	 * Méthode qui retourne si un bloc peut être trouvé pour une combinaison.
	 * @return Vrai s'il peut être trouvée, faux sinon.
	 */
	public boolean canCombine()
	{
		return this.equals(EtatBloc.NORMAL) || this.equals(EtatBloc.WARNING_ANIMATION);
	}
	
	/**
	 * Méthode qui retourne si un bloc peut passer à l'état warning.
	 * @return Vrai si le bloc peut être avertie, sinon faux.
	 */
	public boolean canBeWarned()
	{
		return this.equals(EtatBloc.NORMAL) || this.equals(EtatBloc.WARNING_ANIMATION);
	}
	
	/**
	 * Méthode qui retourne si un bloc peut sortir de l'état warning.
	 * @return Vrai si il peut, faux sinon.
	 */
	public boolean canFadeWarning()
	{
		return this.equals(EtatBloc.WARNING_ANIMATION);
	}

}
