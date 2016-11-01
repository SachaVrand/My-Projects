package donnees;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import principal.RessourcesBlocs;

/**
 * Enumerations représentant les différentes couleurs que peuvent prendre les Bloc.
 * Les attributs de ces objets permettent de connaitres leur différentes images dans le jeu, pour les animations et autres.
 * @author Sacha
 *
 */
public enum BlocColor {
	
	Rouge(RessourcesBlocs.BLOCROUGE,RessourcesBlocs.ANIMCOMBIROUGE,RessourcesBlocs.BLOCROUGE5,RessourcesBlocs.ANIMWARNROUGE,
			RessourcesBlocs.BLOCROUGE6,RessourcesBlocs.LANDANIMROUGE),
	Vert(RessourcesBlocs.BLOCVERT,RessourcesBlocs.ANIMCOMBIVERT,RessourcesBlocs.BLOCVERT5,RessourcesBlocs.ANIMWARNVERT,
			RessourcesBlocs.BLOCVERT6,RessourcesBlocs.LANDANIMVERT),
	Violet(RessourcesBlocs.BLOCVIOLET,RessourcesBlocs.ANIMCOMBIVIOLET,RessourcesBlocs.BLOCVIOLET5,RessourcesBlocs.ANIMWARNVIOLET,
			RessourcesBlocs.BLOCVIOLET6,RessourcesBlocs.LANDANIMVIOLET),
	Jaune(RessourcesBlocs.BLOCJAUNE,RessourcesBlocs.ANIMCOMBIJAUNE, RessourcesBlocs.BLOCJAUNE5,RessourcesBlocs.ANIMWARNJAUNE,
			RessourcesBlocs.BLOCJAUNE6,RessourcesBlocs.LANDANIMJAUNE),
	Turquoise(RessourcesBlocs.BLOCTURQUOISE,RessourcesBlocs.ANIMCOMBITURQUOISE, RessourcesBlocs.BLOCTURQUOISE5,RessourcesBlocs.ANIMWARNTURQUOISE,
			RessourcesBlocs.BLOCTURQUOISE6,RessourcesBlocs.LANDANIMTURQUOISE),
	Bleu(RessourcesBlocs.BLOCBLEU,RessourcesBlocs.ANIMCOMBIBLEU, RessourcesBlocs.BLOCBLEU5,RessourcesBlocs.ANIMWARNBLEU,
			RessourcesBlocs.BLOCBLEU6,RessourcesBlocs.LANDANIMBLEU),
	Vide(RessourcesBlocs.BLOCVIDE,null,RessourcesBlocs.BLOCVIDE,null,null,null),
	SolideSupDroite(RessourcesBlocs.SOLIDESUPDROITE,RessourcesBlocs.ANIMSOLIDE,null,null,null,null),
	SolideSupGauche(RessourcesBlocs.SOLIDESUPGAUCHE,RessourcesBlocs.ANIMSOLIDE,null,null,null,null),
	SolideInfDroite(RessourcesBlocs.SOLIDEINFDROITE,RessourcesBlocs.ANIMSOLIDE,null,null,null,null),
	SolideInfGauche(RessourcesBlocs.SOLIDEINFGAUCHE,RessourcesBlocs.ANIMSOLIDE,null,null,null,null),
	SolideMilieu(RessourcesBlocs.SOLIDEMILIEU,RessourcesBlocs.ANIMSOLIDE,null,null,null,null),
	SolideMilieuHaut(RessourcesBlocs.SOLIDEMILIEUHAUT,RessourcesBlocs.ANIMSOLIDE,null,null,null,null),
	SolideMilieuBas(RessourcesBlocs.SOLIDEMILIEUBAS,RessourcesBlocs.ANIMSOLIDE,null,null,null,null),
	SolideMilieuGauche(RessourcesBlocs.SOLIDEMILIEUGAUCHE,RessourcesBlocs.ANIMSOLIDE,null,null,null,null),
	SolideMilieuDroite(RessourcesBlocs.SOLIDEMILIEUDROITE,RessourcesBlocs.ANIMSOLIDE,null,null,null,null),
	SolideMilieuMilieu(RessourcesBlocs.SOLIDEMILIEUMILIEU,RessourcesBlocs.ANIMSOLIDE,null,null,null,null),
	SolideGauche(RessourcesBlocs.SOLIDEGAUCHE,RessourcesBlocs.ANIMSOLIDE,null,null,null,null),
	SolideDroite(RessourcesBlocs.SOLIDEDROITE,RessourcesBlocs.ANIMSOLIDE,null,null,null,null);
	
	/**
	 * Image basique correspondant au bloc de cette couleur.
	 */
	private BufferedImage image;
	
	/**
	 * Tableau des images dans l'ordre, de l'animation pour une combinaison de cette couleur.
	 */
	private BufferedImage[] combiAnimationImages;
	
	/**
	 * Image de cette couleur quand il est situé sur la ligne 0 de la {@link Grille}.
	 */
	private BufferedImage bottomLineImage;
	
	/**
	 * Tableau des images dans l'ordre, de l'animation de "warning" de cette couleur.
	 */
	private BufferedImage[] warningAnimationImages;
	
	/**
	 * Image de cette couleur correspondant a l'image de fin. 
	 * L'image de fin apparait quand sur tous les blocs qui appartienenSt a une ligne dont un blocs étaient au de la 13ème ligne.
	 */
	private BufferedImage imageFin;
	
	/**
	 * Tableau d'images dans l'odre, de l'animation de chute pour un bloc de cette couleur.
	 */
	private BufferedImage[] landingAnimationImages;
	
	/**
	 * Constructeur de {@link BlocColor}. Instancie une nouvelle {@link BlocColor}. Si la couleur n'as aucune image pour une fonctionnalités, le null est accordé.
	 * @param image Image basique correspondant au bloc de cette couleur.
	 * @param combiAnimationImages Tableau des images dans l'ordre, de l'animation pour une combinaison de cette couleur.
	 * @param bottomLineImage Image de cette couleur quand il est situé sur la ligne 0 de la {@link Grille}.
	 * @param warningAnimationImages Tableau des images dans l'ordre, de l'animation de "warning" de cette couleur.
	 * @param imageFin Image de cette couleur correspondant a l'image de fin. 
	 * @param landingAnimationImages Tableau d'images dans l'odre, de l'animation de chute pour un bloc de cette couleur.
	 */
	private BlocColor(BufferedImage image, BufferedImage[] combiAnimationImages, BufferedImage bottomLineImage, BufferedImage[] warningAnimationImages, 
			BufferedImage imageFin, BufferedImage[] landingAnimationImages) {
		this.image = image;
		this.combiAnimationImages = combiAnimationImages;
		this.bottomLineImage = bottomLineImage;
		this.warningAnimationImages = warningAnimationImages;
		this.imageFin = imageFin;
		this.landingAnimationImages = landingAnimationImages;
	}
	
	/**
	 * Méthode qui retourne l'image de cette couleur.
	 * @return l'image de cette couleur.
	 */
	public BufferedImage getImage() {
		return image;
	}
	
	/**
	 * Méthode qui retourne les images correspondant à l'animation de combinaison d'un bloc de cette couleur.
	 * @return Tableau des images dans l'ordre, de l'animation pour une combinaison de cette couleur.
	 */
	public BufferedImage[] getCombiAnimationImages() {
		return combiAnimationImages;
	}
	
	/**
	 * Méthode qui retourne l'image correspondant à une bloc de cette couleur sur la ligne 0.
	 * @return Image de cette couleur quand il est situé sur la ligne 0 de la {@link Grille}.
	 */
	public BufferedImage getBottomLineImage() {
		return bottomLineImage;
	}
	
	/**
	 * Méthode qui retourne les images correspondant à l'animation de "warning" d'un bloc de cette couleur.
	 * @return Tableau des images dans l'ordre, de l'animation de "warning" de cette couleur.
	 */
	public BufferedImage[] getWarningAnimationImages() {
		return warningAnimationImages;
	}
	
	/**
	 * Méthode qui retourne l'image de fin d'un bloc de cette couleur.
	 * @return Image de cette couleur correspondant a l'image de fin. 
	 */
	public BufferedImage getImageFin() {
		return imageFin;
	}
	
	/**
	 * Méthode qui retourne les images correspondant à l'animation de chute d'un bloc de cette couleur.
	 * @return Tableau d'images dans l'odre, de l'animation de chute pour un bloc de cette couleur.
	 */
	public BufferedImage[] getLandingAnimationImages() {
		return landingAnimationImages;
	}
	
	/**
	 * Méthode qui permet de savoir si ce bloc correspond a une partie d'une brique.
	 * @return Vrai si cette couleur correspondant à une partie d'une brique.
	 */
	public boolean isSolid()
	{
		return (ordinal() > 6);
	}
	
	/**
	 * Méthode qui permet de savoir si cette couleur correspondant à une couleur normale. C'est à dire soit rouge, bleu, vert, jaune, turquoise ou violet.
	 * @return Vrai si cette couleur est une couleur normale. Faux sinon.
	 */
	public boolean isNormalColor()
	{
		return (ordinal() < 6);
	}
	
	/**
	 * Fonction qui retourne toutes les couleurs possibles que la génération d'un bloc aléatoire peut faire apparaitre. 
	 * En excluant les couleurs passée en paramètre.
	 * @param excluded Set de couleurs à exclure des choix de retour.
	 * @return Set de couleurs possible que peut générer la grille.
	 */
	public static List<BlocColor> getColorChoices(HashSet<BlocColor> excluded)
	{
		List<BlocColor> res = new ArrayList<>();
		for(BlocColor bc : BlocColor.values())
		{
			if(bc.ordinal() <= 5 && !excluded.contains(bc))
				res.add(bc);
		}
		return res;
	}
}
