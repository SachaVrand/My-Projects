/**
 * Enumération représentant les differentes couleurs que les pions peuvent prendre au cours d'une partie.
 * @author Vrand
 * 
 */
public enum Couleur {
	/**
	 * Représente la couleur noir. L'un des deux joueurs.
	 */
	NOIR(Ressources.cheminImageNoir),
	
	/**
	 * Représente la couleur Blanc. L'un des deux joueurs.
	 */
	BLANC(Ressources.cheminImageBlanc),
	
	/**
	 * Représente la couleur Vide. Les cases qui n'ont pas encore été jouées.
	 */
	VIDE(Ressources.cheminImageVide),
	
	/**
	 * Représente la couleur en surbrillance. Les cases jouables.
	 */
	SURBRILLANCE(Ressources.cheminImageSurbrillance);
	
	/**
	 * Chemin de l'image associé à la couleur.
	 */
	String path;
	
	/**
	 * Constructeur de l'énumération Couleur
	 * @param path Chemin de l'image associé à la couleur
	 */
	Couleur(String path)
	{
		this.path = path;
	}
	
	/**
	 * Méthode permettant de récuperer le chemin de l'image
	 * @return chemin de l'image
	 */
	String getImagePath()
	{
		return this.path;
	}

}
