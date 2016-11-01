import javax.swing.JButton;

/**
 * Classe représentant les boutons "Regarder" dans le menu du choix des sauvegardes.
 * @author Vrand
 *
 */
public class BoutonRegarder extends JButton{
	
	private static final long serialVersionUID = 4630151939390195598L;
	
	/**
	 * Sauvegarde qui sera lancée après avoir cliquer sur ce bouton.
	 */
	private Sauvegarde save = null;
	
	/**
	 * Constructeur de la classe BoutonRegarder
	 * Instancie un nouveau JButton avec le texte spécifier en paramètre.
	 * @param text -> Texte à afficher sur le bouton
	 * @param save -> Sauvegarde associée au bouton
	 */
	public BoutonRegarder(String text,Sauvegarde save)
	{
		super(text);
		this.save = save;
	}
	
	/**
	 * Méthode qui retourne la sauvegarde associée au bouton
	 * @return sauvegarde associée au bouton
	 */
	public Sauvegarde getSauvegarde()
	{
		return this.save;
	}

}
