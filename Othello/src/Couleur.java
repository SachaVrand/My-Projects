/**
 * Enum�ration repr�sentant les differentes couleurs que les pions peuvent prendre au cours d'une partie.
 * @author Vrand
 * 
 */
public enum Couleur {
	/**
	 * Repr�sente la couleur noir. L'un des deux joueurs.
	 */
	NOIR(Ressources.cheminImageNoir),
	
	/**
	 * Repr�sente la couleur Blanc. L'un des deux joueurs.
	 */
	BLANC(Ressources.cheminImageBlanc),
	
	/**
	 * Repr�sente la couleur Vide. Les cases qui n'ont pas encore �t� jou�es.
	 */
	VIDE(Ressources.cheminImageVide),
	
	/**
	 * Repr�sente la couleur en surbrillance. Les cases jouables.
	 */
	SURBRILLANCE(Ressources.cheminImageSurbrillance);
	
	/**
	 * Chemin de l'image associ� � la couleur.
	 */
	String path;
	
	/**
	 * Constructeur de l'�num�ration Couleur
	 * @param path Chemin de l'image associ� � la couleur
	 */
	Couleur(String path)
	{
		this.path = path;
	}
	
	/**
	 * M�thode permettant de r�cuperer le chemin de l'image
	 * @return chemin de l'image
	 */
	String getImagePath()
	{
		return this.path;
	}

}
