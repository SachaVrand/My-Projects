package donnees;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import principal.RessourcesAudio;

/**
 * Classe représentant une brique du jeu. Une brique représente un ensemble de blocs. 
 * Cette ensemble de blocs a des propriétéss particulères par rapport à des blocs normaux.
 * @author Sacha
 *
 */
public class Brique implements IBloc, Comparable<Brique>{
	
	/**
	 * Variable permettant à chaque nouvelle brique de lui attribuer un id permettant de différencier les différentes briques dans la partie.
	 */
	private static int lastId = 0;
	
	/**
	 * Id de cette brique. Permet de différencier cette brique d'une autre. Pour par exemple les casser.
	 */
	private int id;
	
	/**
	 * Ensemble des blocs constituant cette {@link Brique}.
	 */
	private HashSet<Bloc> blocsDeLaBrique;
	
	/**
	 * Entier correspondant à la hauteur de cette brique.
	 */
	private int hauteur;
	
	/**
	 * Entier correspondant à la longueur de cette brique.
	 */
	private int longueur;
	
	/**
	 * Entier correspondant à la position la plus basse en y de cette {@link Brique} dans la {@link Grille}. 
	 * La position de la ligne du bas cette brique.
	 */
	private int y;
	
	/**
	 * Liste des blocs de la {@link Grille} organisée de facon horizontales. 
	 * Permet de facilement avoir accès aux blocs de la {@link Grille} pour faire des opérations sur la grille.
	 */
	private ArrayList<ArrayList<Bloc>> lignesHorizontales;
	
	/**
	 * Bloc correspondant au bloc le plus en bas à gauche de cette brique.
	 */
	private Bloc bottomLeft;
	
	/**
	 * Bloc correspondant au bloc le plus en haut à gauche de ctte brique.
	 */
	private Bloc topLeft;
	
	/**
	 * Constructeur de la classe {@link Brique}. 
	 * Instancie une nouvelle brique avec comme ensemble de blocs les blocs de la grille de y à y + hauteur et de x à x + longueur.
	 * @param hauteur Hauteur de la brique.
	 * @param longueur Longueur de la brique.
	 * @param y Position de la ligne la plus de cette brique par rapport à la {@link Grille}.
	 * @param lignesHorizontales Matrice de bloc de la {@link Grille} organisée de facon horizontales.
	 */
	public Brique(int hauteur, int longueur, int y, ArrayList<ArrayList<Bloc>> lignesHorizontales) {
		this.id = ++lastId;
		this.hauteur = hauteur;
		this.longueur = longueur;
		this.lignesHorizontales = lignesHorizontales;
		this.blocsDeLaBrique = new HashSet<>(24,1);
		this.setY(y);
	}
	
	/**
	 * Méthode qui retourne l'Id de cette brique.
	 * @return L'id de cette brique.
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Méthode qui retourne l'ensemble des blocs qui composent cette brique.
	 * @return l'ensemble des blocs qui composent cette brique.
	 */
	public HashSet<Bloc> getBlocsDeLaBrique() {
		return blocsDeLaBrique;
	}
	
	/**
	 * Méthode qui retourne la hauteur de cette brique.
	 * @return Hauteur de cette brique.
	 */
	public int getHauteur() {
		return hauteur;
	}
	
	/**
	 * Méthode qui retourne la longueur de cette brique.
	 * @return longueur de cette brique.
	 */
	public int getLongueur() {
		return longueur;
	}
	
	/**
	 * Méthode qui retourne la position dans la grille de la ligne la plus basse de cette brique.
	 * @return Position en y des blocs du bas de la brique.
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Méthode qui retourne le bloc le plus en bas à gauche de cette brique.
	 * @return Le bloc le plus en bas à gauche de cette brique.
	 */
	public Bloc getBottomLeft() {
		return bottomLeft;
	}
	
	/**
	 * Méthode qui retourne le bloc le plus en haut à gauche de cette brique.
	 * @return le bloc le plus en haut à gauche de cette brique.
	 */
	public Bloc getTopLeft() {
		return topLeft;
	}
	
	/**
	 * Méthode qui permet de cette la position en y de cette brique.
	 * Cette méthode à aussi pour effet de mettre l'ensemble de blocs que composent cette brique.
	 * @param y Nouvelle position en y de cette brique.
	 */
	public void setY(int y) {
		this.y = y;
		updateBlocsInBrick();
	}
	
	/**
	 * Méthode qui permet de créer la brique. Associe a tous les blocs qui la composent la bonne couleur. 
	 * Si temp est à vrai alors la création sera temporisé de 150ms pour chaque brique. Et la création se fera de gauche à droite et de bas en haut.
	 * Sinon elle est crée dans n'importe qu'elle ordre sans temporisation.
	 * @param tempo Vrai si la brique doit être crée de facon temporisée. Sinon faux.
	 */
	public void creerBrique(boolean tempo)
	{
		Set<Bloc> set = blocsDeLaBrique;
		int minY = y, maxY = minY + hauteur - 1, minX = 0, maxX = longueur - 1;
		
		if(tempo)
		{
			set = new TreeSet<>(this.blocsDeLaBrique);
		}
		
		for(Bloc bloc : set)
		{
			BlocColor bc;
			int x = bloc.getX(), y = bloc.getY();
			if(hauteur == 1)
			{
				bc = (x == minX) ? (BlocColor.SolideGauche) : (x == maxX ? (BlocColor.SolideDroite) : (BlocColor.SolideMilieu));
				bloc.setCouleur(bc);
			}
			else
			{
				if(x == minX)
				{
					bc = (y == maxY)?(BlocColor.SolideSupGauche):((y == minY)?(BlocColor.SolideInfGauche):(BlocColor.SolideMilieuGauche));
				}
				else if(x == maxX)
				{
					bc = (y == maxY)?(BlocColor.SolideSupDroite):((y == minY)?(BlocColor.SolideInfDroite):(BlocColor.SolideMilieuDroite));
				}
				else
				{
					bc = (y == maxY)?(BlocColor.SolideMilieuHaut):((y == minY)?(BlocColor.SolideMilieuBas):(BlocColor.SolideMilieuMilieu));
				}
				bloc.setCouleur(bc);
			}
			
			if(tempo)
			{
				RessourcesAudio.lancerSonBreakBlocks();
				try {
					Thread.sleep(150);
				} catch (InterruptedException e) {
					return;
				}
			}
		}
		
	}
	
	/**
	 * Méthode qui permet de reduire la brique d'une ligne. Met à jour sa position en y et ses blocs, et la recrée de facon temporisée.
	 * @return Vrai si la brique n'existe plus après reduction. Si sa hauteur est maintenant de 0. Sinon faux.
	 */
	public boolean removeBottomLine()
	{
		this.hauteur--;
		this.setY(y + 1);
		if(hauteur > 0)
			creerBrique(true);
		return hauteur == 0;
	}
	
	/**
	 * Méthode qui met à jour l'ensemble des blocs qui composent cette brique.
	 */
	private void updateBlocsInBrick()
	{
		this.blocsDeLaBrique.clear();
		
		for(int i = y; i < y + hauteur; i++)
		{
			for(int x = 0; x < longueur; x++)
			{
				if(x == 0 && i == y)
				{
					bottomLeft = lignesHorizontales.get(i).get(x);
				}
				if(x == 0 && i == (y + hauteur - 1))
				{
					topLeft = lignesHorizontales.get(i).get(x);
				}
				blocsDeLaBrique.add(lignesHorizontales.get(i).get(x));
			}
		}
	}
	
	/**
	 * Méthode qui permet de savoir si deux briques sont adjacentes.
	 * @param b2 L'autre avec laquelle comparé.
	 * @return Vrai si elles sont adjacentes, faux sinon.
	 */
	public boolean adjacentes(Brique b2)
	{
		if(bottomLeft.getY() == b2.topLeft.getY() + 1)
			return true;
		else if(topLeft.getY() == b2.bottomLeft.getY() - 1)
			return true;
		return false;
	}
	
	/**
	 * Méthode qui permet de savoir si cette brique peut descendre d'une ligne mais pas forcément tout de suite. 
	 * C'est à dire si tous les blocs en dessous sont soit vides soit en train de tombés.
	 * @return Vrai si la brique peut descendre d'une ligne, faux sinon.
	 */
	public boolean canGoDown()
	{
		Bloc b;
		for(int i = 0; i < this.getLongueur(); i++)
		{
			b = lignesHorizontales.get(y - 1).get(i);
			if(!b.isVoid())
			{
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Méthode qui permet de savoir si cette brique peut descendre d'une ligne maintenant. 
	 * C'est à dire si tous les blocs sont vides en dessous.
	 * @return Vrai si la brique peut descendre d'une ligne maintenant, sinon faux.
	 */
	public boolean canFallForOneLine()
	{	
		Bloc b;
		for(int i = 0; i < this.getLongueur(); i++)
		{
			b = lignesHorizontales.get(y - 1).get(i);
			if(!b.getCouleur().equals(BlocColor.Vide))
			{
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Méthode qui permet de savoir si cette brique peut être trouvée pour une combinaison. Si l'état de ses blocs ne l'en empeche pas.
	 * @return Vrai si elle peut être trouvée pour une combinaison. faux sinon.
	 */
	public boolean canCombine()
	{
		return this.bottomLeft.getEtat().canCombine();
	}
	
	/**
	 * Méthode qui permet de savoir si la brique peut tomber. Si l'état de ses blocs ne l'en empeche pas.
	 * @return Vrai si elle peut tomber, faux sinon.
	 */
	public boolean canFall()
	{
		return this.bottomLeft.getEtat().canFall();
	}

	@Override
	public int compareTo(Brique o) {
		return Integer.compare(this.id, o.id);
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		else if(obj instanceof Brique)
		{
			Brique o2 = (Brique)obj;
			return this.id == o2.id;
		}
		return false;
	}

	@Override
	public IBloc moveDown(Grille grille) {

		if(this.canFallForOneLine())
		{	
			this.setY(this.getY() - 1);
			this.creerBrique(false);
			
			for(Bloc bloc : this.getBlocsDeLaBrique())
			{
				bloc.setEtat(EtatBloc.FALLING);
			}
			
			for(int y = this.getY() + this.getHauteur(), x = 0; x < this.getLongueur(); x++)
			{
				Bloc bloc = lignesHorizontales.get(y).get(x);
				bloc.setCouleur(BlocColor.Vide);
				bloc.setEtat(EtatBloc.NORMAL);					
			}
		}
		
		if(this.canGoDown())
		{
			return this;
		}
		else
		{
			for(Bloc bloc : this.getBlocsDeLaBrique())
			{
				bloc.setEtat(EtatBloc.NORMAL);
			}
			return null;
		}
	}

}
