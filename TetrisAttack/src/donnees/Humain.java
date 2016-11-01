package donnees;

/**
 * Classe représentant un joueur Humain.
 * @author Sacha
 *
 */
public class Humain extends Joueur{

	/**
	 * Configuration de touches du joueur.
	 */
	private KeyboardSetting keyboardSetting;
	
	/**
	 * Constructeur de {@link Humain}.
	 * @param idJoueur L'id du joueur. 1 ou 2.
	 * @param keyboardSetting La configuration de touches du joueur.
	 */
	public Humain(int idJoueur, KeyboardSetting keyboardSetting) {
		super(idJoueur);
		this.keyboardSetting = keyboardSetting;
	}
	
	/**
	 * Méthode qui la configuration de touches du joueur.
	 * @return la configuration de touches du joueur.
	 */
	public KeyboardSetting getKeyboardSetting() {
		return keyboardSetting;
	}
	
}
