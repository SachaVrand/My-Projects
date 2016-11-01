package donnees;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import principal.RessourcesAudio;

/**
 * Classe représentant la grille de jeu composée des différents Blocs.
 * @author Sacha
 *
 */
public class Grille {
	
	/**
	 * Nombre de lignes maximum de la grille de base, incluant la ligne supplémentaire du bas.
	 */
	public final static int MAXLINES = 13;
	
	/**
	 * Nombre de blocs maximum par ligne.
	 */
	public final static int MAXBLOCPERLINE = 6;
	
	/**
	 * Matrice de blocs, organisé de facon horizontales. line.get(y) retourne la yème ligne horizontale.
	 */
	private ArrayList<ArrayList<Bloc>> lignesHorizontales;
	
	/**
	 * Matrice de blocs, organisé de facon verticales. line.get(x) retourne la xème ligne verticale.
	 */
	private ArrayList<ArrayList<Bloc>> lignesVerticales;
	
	/**
	 * Liste des briques présentes sur la grille.
	 */
	private volatile ArrayList<Brique> briquesPresentes;
	
	/**
	 * Constructeur de Grille. Instancie une nouvelle grille avec la moitié des ligne jouables remplies par des blocs aléatoires.
	 */
	public Grille() {
		initGrille();
		briquesPresentes = new ArrayList<>();
	}
	
	/**
	 * Méthode qui initalise les blocs de la grille.
	 */
	private void initGrille()
	{
		//Instanciation des listes.
		this.lignesHorizontales = new ArrayList<>();
		this.lignesVerticales = new ArrayList<>();
		for(int i = 0; i < MAXLINES; i++)
		{
			this.lignesHorizontales.add(new ArrayList<Bloc>());
		}
		for(int i = 0; i < MAXBLOCPERLINE; i++)
		{
			this.lignesVerticales.add(new ArrayList<Bloc>());
		}
		
		//Initialisation de tous les blocs à la couleur vide.
		for(int y = 0; y < MAXLINES; y++)
		{
			for(int x = 0; x < MAXBLOCPERLINE; x++)
			{
				Bloc newBloc = new Bloc(BlocColor.Vide,x,y);
				lignesHorizontales.get(y).add(newBloc);
				lignesVerticales.get(x).add(newBloc);
			}
		}
	}

	/**
	 * Méthode qui permet de set une grille de départ. Change les couleurs des 6 premières ligne de blocs avec couleurs aléatoires, 
	 * ne comportant aucune combinaison de base.
	 */
	public void randomStartGrille() {
		for(int y = 0; y < (MAXLINES-1)/2; y++)
		{
			for(int x = 0; x < MAXBLOCPERLINE; x++)
			{
				lignesHorizontales.get(y).get(x).setCouleur(getRandomBlocColor(x, y));
			}
		}
	}
	
	/**
	 * Méthode permet de vider la grille. Set tous les blocs avec la couleur vide, et l'état normal. 
	 * Vide aussi le dictionnaire de briques.
	 */
	public synchronized void emptyGrille()
	{
		for(int y = 0; y < lignesHorizontales.size(); y++)
		{
			for(int x = 0; x < MAXBLOCPERLINE; x++)
			{
				lignesHorizontales.get(y).get(x).reset();
			}
		}
		briquesPresentes.clear();
	}
	
	public void debugComboGrille()
	{
		/*
		lignesVerticales.get(0).get(1).setCouleur(BlocColor.Bleu);
		lignesVerticales.get(0).get(2).setCouleur(BlocColor.Bleu);
		lignesVerticales.get(0).get(3).setCouleur(BlocColor.Vert);
		lignesVerticales.get(0).get(4).setCouleur(BlocColor.Bleu);
		lignesVerticales.get(1).get(1).setCouleur(BlocColor.Vert);
		lignesVerticales.get(1).get(2).setCouleur(BlocColor.Vert);
		lignesVerticales.get(1).get(3).setCouleur(BlocColor.Bleu);*/
		ajouterBriqueSolide(1, 6);
		ajouterBriqueSolide(1, 6);
		ajouterBriqueSolide(1, 6);
		ajouterBriqueSolide(1, 6);
		ajouterBriqueSolide(1, 6);
	}
	
	/**
	 * Méthode qui permet de récupérer une couleur de bloc aléatoire, ne correspondant pas à la couleur du bloc a droite, a gauche, au dessus et en bas.
	 * @param x colonne où le bloc va être changé.
	 * @param y ligne où le bloc va être changé.
	 * @return La couleur aléatoire du bloc.
	 */
	private BlocColor getRandomBlocColor(int x, int y)
	{
		if(x >= MAXBLOCPERLINE || x < 0 || y < 0 || y > lignesHorizontales.size()-1)
			return null;
		
		Random r = new Random();
		List<BlocColor> couleurs;
		HashSet<BlocColor> excluded = new HashSet<>();
		
		excluded.add((x == 0)?(BlocColor.Vide):(lignesHorizontales.get(y).get(x-1).getCouleur()));
		excluded.add((y == 0)?(BlocColor.Vide):(lignesHorizontales.get(y-1).get(x).getCouleur()));
		excluded.add((y == lignesHorizontales.size() - 1)?(BlocColor.Vide):(lignesHorizontales.get(y+1).get(x).getCouleur()));
		excluded.add((x == MAXBLOCPERLINE - 1)?(BlocColor.Vide):(lignesHorizontales.get(y).get(x+1).getCouleur()));
		
		couleurs = BlocColor.getColorChoices(excluded);
		return couleurs.get(r.nextInt(couleurs.size()));
	}
	
	/**
	 * Méthode qui retourne vrai si il y a au moins un bloc situé dans la ligne perdante, sinon faux.
	 * @return vrai si il y au moins un bloc dans la ligne perdante, faux sinon.
	 */
	public synchronized boolean hasLosingBlocs()
	{
		for(int x = 0; x < MAXBLOCPERLINE; x++)
		{
			if(!lignesHorizontales.get(MAXLINES-1).get(x).getCouleur().equals(BlocColor.Vide))
				return true;
		}
		return false;
	}
	
	/**
	 * Méthode qui permet de passer les lignes en état warning. 
	 * C'est à dire toutes les lignes qui possédent au moins un bloc au dessus de la ligne 12.
	 * L'état warning fait sautiller les blocs pour avertir le joueur.
	 */
	public synchronized void warnLines()
	{
		Bloc b1, b2;
		for(int x = 0; x < MAXBLOCPERLINE; x++)
		{
			for(int y = MAXLINES - 2; y < lignesHorizontales.size(); y++)
			{
				b2 = lignesHorizontales.get(y).get(x);
				if(!b2.getCouleur().equals(BlocColor.Vide) && b2.getEtat().canBeWarned())
				{
					for(int z = 1; z < lignesVerticales.get(x).size(); z++)
					{
						b1 = lignesVerticales.get(x).get(z);
						if(b1.getCouleur().isNormalColor() && b1.getEtat().canBeWarned())
						{
							b1.setEtat(EtatBloc.WARNING_ANIMATION);
						}
					}
					break;
				}
			}
		}
	}
	
	/**
	 * Méthode qui permet de passer les colonnes en état normal si elle étaient en warning et qu'elles ne doivent plus l'être.
	 */
	public synchronized void fadeWarnings()
	{
		boolean vide;
		Bloc b1,b2;
		for(int x = 0; x < MAXBLOCPERLINE; x++)
		{
			vide = true;
			for(int z = MAXLINES - 2; z < lignesHorizontales.size(); z++)
			{
				b2 = lignesHorizontales.get(z).get(x);
				
				if(!b2.getCouleur().equals(BlocColor.Vide))
				{
					vide = false;
					break;
				}
			}
			
			if(vide)
			{
				for(int y = 1; y < lignesVerticales.get(x).size(); y++)
				{
					b1 = lignesVerticales.get(x).get(y);
					if(b1.getCouleur().isNormalColor() && b1.getEtat().canFadeWarning())
					{
						b1.setEtat(EtatBloc.NORMAL);
					}
				}
			}
		}
	}
	
	/**
	 * Méthode qui ajoute une ligne de Bloc de couleur aléatoire, à l'indice x,y et de longueur length. 
	 * @param y coordonée y dans la grille où la ligne commence.
	 * @param x coordonnée x dans la grille où la ligne commence.
	 * @param length longueur de la ligne à ajouter.
	 */
	public synchronized void addRandomLine(int y, int x, int length)
	{	
		for(int i = x; i < x+length; i++)
		{
			lignesHorizontales.get(y).get(i).setCouleur(getRandomBlocColor(i, y));
		}
	}
	
	/**
	 * Méthode qui fait remonter toutes les lignes et ajoute une nouvelle ligne aléatoire en bas de la grille.
	 */
	public synchronized void addNewLineFromBottom()
	{
		int y;
		boolean needNewLine = false;
		for(int i = 0; i < MAXBLOCPERLINE; i++)
		{
			if(lignesHorizontales.get(lignesHorizontales.size()-1).get(i).getCouleur().compareTo(BlocColor.Vide) != 0)
			{
				needNewLine = true;
				break;
			}
		}
		if(needNewLine)
		{
			createNewEmptyLine();
		}
		
		for(y = lignesHorizontales.size() - 1; y > 0; y--)
		{
			for(int x = 0; x < MAXBLOCPERLINE; x++)
			{
				lignesHorizontales.get(y).get(x).setCouleur(lignesHorizontales.get(y-1).get(x).getCouleur());
			}
		}
		for(Brique brique : briquesPresentes)
		{
			brique.setY(brique.getY() + 1);
		}
		
		this.addRandomLine(0, 0, MAXBLOCPERLINE);
		this.warnLines();
	}
	
	/**
	 * Méthode qui permet d'échanger deux blocs dans la grille.
	 * @param x1 coordonée x du premier bloc à échanger.
	 * @param y1 coordonée y du premier bloc à échanger.
	 * @param x2 coordonée x du deuxième bloc à échanger.
	 * @param y2 coordonée y du deuxième bloc à échanger.
	 * @return Vrai si on a pu faire un échange, faux sinon.
	 */
	public synchronized boolean exchange(int x1, int y1, int x2, int y2)
	{
		if(x1 >= MAXBLOCPERLINE || x1 < 0 || y1 < 0 || y1 >= MAXLINES)
			return false;
		else if(x2 >= MAXBLOCPERLINE || x2 < 0 || y2 < 0 || y2 >= MAXLINES)
			return false;
		else if(lignesHorizontales.get(y1).get(x1).getCouleur().isSolid() || lignesHorizontales.get(y2).get(x2).getCouleur().isSolid())
			return false;
		else if(!lignesHorizontales.get(y1).get(x1).getEtat().canSwitch() || !lignesHorizontales.get(y2).get(x2).getEtat().canSwitch())
			return false;
		
		lignesHorizontales.get(y1).get(x1).swap(lignesHorizontales.get(y2).get(x2));
		this.warnLines();
		return true;
	}

	/**
	 * Méthode qui retrouen la matrice de blocs organisée de facon horizontale.
	 * @return la matrice de blocs organisée de facon horizontale.
	 */
	public ArrayList<ArrayList<Bloc>> getLignesHorizontales() {
		return lignesHorizontales;
	}
	
	/**
	 * Méthode qui retrouen la matrice de blocs organisée de facon verticale.
	 * @return la matrice de blocs organisée de facon verticale.
	 */
	public ArrayList<ArrayList<Bloc>> getLignesVerticales() {
		return lignesVerticales;
	}
	
	/**
	 * Méthode qui permet de détruire tout les blocs de la liste des combinaisons.
	 * @param allCombinaisons liste des combinaisons qui doivent être détruitent.
	 */
	private void emptyBlocs(ArrayList<Combinaison> allCombinaisons)
	{
		for(Combinaison combi : allCombinaisons)
		{
			for(Bloc bloc : combi.getBlocsImpliques())
			{
				bloc.setCouleur(BlocColor.Vide);
				bloc.setEtat(EtatBloc.NORMAL);			
			}
		}
		
		if(allCombinaisons.size() > 0)
			RessourcesAudio.lancerSonBreakBlocks();
	}
	
	/**
	 * Méthode qui permet de réduire une brique suite à une combinaison.
	 * Retire la derniere ligne de la brique et la remplace par une nouvelle ligne de blocs aléatoire.
	 * Retire la brique, des briques presentes, si sa hauteur passe à 0.
	 * @param briquesAReduire Liste de briques qui doivent être reduitent.
	 */
	private void reduceBricks(ArrayList<Brique> briquesAReduire)
	{
		HashSet<Bloc> tmpBlocs = new HashSet<>();
		
		//Pour que la réduction temporisée des briques se fasse de bas en haut,
		//on trie les briques par ordre croissant.
		Collections.sort(briquesAReduire);
		
		for(Brique brique : briquesAReduire)
		{	
			if(!briquesPresentes.contains(brique))
				continue;
			
			//On retient tout les blocs de la briques, qui sont en état combinaison.
			//et ne peuvent donc pas être bougés, tombés...
			tmpBlocs.addAll(brique.getBlocsDeLaBrique());
			
			this.randomLineTempo(brique.getY(), 0, brique.getLongueur());
			
			if(brique.removeBottomLine()) 
				briquesPresentes.remove(brique);
		}
		
		//On repasse tous les blocs en état normal.
		for(Bloc bloc : tmpBlocs)
		{
			bloc.setEtat(EtatBloc.NORMAL);
		}
		
		this.warnLines();
	}
	
	/**
	 * Méthode qui crée une nouvelle ligne de blocs aléatoire en créant un bloc toute les 150ms.
	 * @param y position en y de la ligne à créée.
	 * @param x position en x de la ligne à créée.
	 * @param length longueur de la ligne créée.
	 */
	private void randomLineTempo(int y, int x, int length)
	{
		for(int i = x; i < x+length; i++)
		{
			lignesHorizontales.get(y).get(i).setCouleur(getRandomBlocColor(i, y));
			RessourcesAudio.lancerSonBreakBlocks();
			try {
				Thread.sleep(150);
			} catch (InterruptedException e) {
				return;
			}
		}
	}
	
	/**
	 * Méthode qui détruit les blocs et briques contenus dans la {@link CombiBrick}.
	 * @param cb combiBrick dont les blocs doivent être détruit.
	 */
	public void virerBlocsEtBriques(CombiBrick cb)
	{
		emptyBlocs(cb.getAllCombinaisons());
		reduceBricks(cb.getBriquesImpliquees());
	}

	/**
	 * Méthode qui permet de faire tomber tous les {@link IBloc} d'une ligne présent dans la liste en paramètre.
	 * @param fallingBlocks Liste des {@link IBloc} qui doivent tombés de 1 ligne.
	 * @return liste des {@link IBloc} qui continuent de tomber après.
	 */
	public synchronized ArrayList<IBloc> moveDownFallingBlocks(ArrayList<IBloc> fallingBlocks)
	{
		ArrayList<IBloc> res = new ArrayList<>();
		IBloc tmp;
		boolean blocsTombes = false, briquesTombees = false;
		
		for(IBloc iBloc : fallingBlocks)
		{
			if((tmp = iBloc.moveDown(this)) != null)
				res.add(tmp);
			else
			{
				if(iBloc instanceof Bloc)
					blocsTombes = true;
				else
					briquesTombees = true;
			}
		}
		
		if(blocsTombes)
		{
			RessourcesAudio.lancerSonFallBlocks();
		}
		
		if(briquesTombees)
		{
			RessourcesAudio.lancerSonChuteBrique();
		}
		
		return res;
	}
	
	/**
	 * Méthode qui va retourne tous les {@link IBloc} qui doivent tombée à un instant t et qui ne sont pas encore gérés.
	 * @return tous les {@link IBloc} qui doivent tombée à un instant t et qui ne sont pas encore gérés.
	 */
	public synchronized ArrayList<IBloc> searchForFallingBlocks()
	{
		int lastSize;
		ArrayList<IBloc> res = new ArrayList<>();
		
		do
		{
			lastSize= res.size();
			res.addAll(fallingBlocks());
			res.addAll(fallingBricks());
		}
		while(res.size() != lastSize);
		
		return res;
	}
	
	/**
	 * Méthode qui retourne tous les blocs qui doivent tombé et qui ne sont pas encore géré.
	 * @return toutes les blocs qui doivent tombé et qui ne sont pas encore géré.
	 */
	private ArrayList<IBloc> fallingBlocks()
	{
		ArrayList<IBloc> res = new ArrayList<>();
		Bloc bloc,blocEnDessous;
		for(int x = 0; x < MAXBLOCPERLINE; x++)
		{
			for(int y = 1; y < lignesVerticales.get(x).size() ; y++)
			{
				bloc = lignesVerticales.get(x).get(y);
				blocEnDessous = lignesVerticales.get(x).get(y-1);
				if(bloc.getCouleur().isNormalColor() && bloc.getEtat().canFall() && blocEnDessous.isVoid())
				{
					res.add(bloc);
					bloc.setEtat(EtatBloc.FALLING);
					
				}
			}
		}
		return res;
	}
	
	/**
	 * Méthode qui retourne toutes les briques qui doivent tombée et qui ne sont pas encore gérée.
	 * @return toutes les briques qui doivent tombée et qui ne sont pas encore gérée.
	 */
	private ArrayList<IBloc> fallingBricks()
	{
		ArrayList<IBloc> res = new ArrayList<>();
		
		for(Brique brique : briquesPresentes)
		{	
			if(!brique.canFall())
			{
				continue;
			}
			if(brique.canGoDown())
			{
				for(Bloc bloc : brique.getBlocsDeLaBrique())
				{
					bloc.setEtat(EtatBloc.FALLING);
				}
				res.add(brique);
			}
		}
		return res;
	}
	
	/**
	 * Méthode qui permet de trouver la brique qui possèdent ce bloc. Retourne null si aucune ne le possède.
	 * @param bloc bloc dont on cherche à savoir si il appartient à une brique présente.
	 * @return la brique qui contient le bloc, null si aucunes.
	 */
	private Brique findBrick(Bloc bloc)
	{
		for(Brique brique : briquesPresentes)
		{
			if(brique.getBlocsDeLaBrique().contains(bloc) && brique.canCombine())
				return brique;
		}
		return null;
	}
	
	/**
	 * Méthode qui retourne les briques à coté de ce bloc.
	 * @param bloc bloc dont on cherche à connaitre les briques voisines.
	 * @return la liste des briques voisines, vide si aucune.
	 */
	private ArrayList<Brique> bricksNextTo(Bloc bloc)
	{
		ArrayList<Brique> res = new ArrayList<>();
		int x = bloc.getX(), y = bloc.getY();
		Brique b;
		
		if(y != lignesHorizontales.size() - 1 && (b = findBrick(lignesHorizontales.get(y+1).get(x))) != null)
		{
			res.add(b);
		}
		if(y != 0 && (b = findBrick(lignesHorizontales.get(y-1).get(x))) != null)
		{
			res.add(b);
		}
		if(x != MAXBLOCPERLINE - 1 && (b = findBrick(lignesHorizontales.get(y).get(x+1))) != null)
		{
			res.add(b);
		}
		if(x != 0 && (b = findBrick(lignesHorizontales.get(y).get(x-1))) != null)
		{
			res.add(b);
		}
		
		return res;
	}
	
	/**
	 * Méthode qui permet de récupérer les briques à coté des blocs en combinaisons de la liste des combinaisons passée en paramètre.
	 * @param combinaisons Liste des combinaisons dont on cherche les briques voisines.
	 * @return Ensemble des briques voisines. vide si aucunes.
	 */
	public synchronized HashSet<Brique> briquesACoteDe(ArrayList<Combinaison> combinaisons)
	{
		HashSet<Brique> res = new HashSet<>();
		for(Combinaison combinaison : combinaisons)
		{
			for(Bloc bloc : combinaison.getBlocsImpliques())
			{
				res.addAll(bricksNextTo(bloc));
			}
		}
		return res;
	}
	
	/**
	 * Méthode qui permet d'ajouter une brique à cette grille.
	 * @param hauteur hauteur de la brique à ajoutée.
	 * @param longueur longueur de la brique à ajoutée.
	 */
	public synchronized void ajouterBriqueSolide(int hauteur, int longueur)
	{
		creerEspaceSiNecessaire(hauteur, longueur);
		creerBriqueSolide(hauteur, longueur);
	}
	
	/**
	 * Méthode qui permet de créer de l'espace pour une brique, si la matrice n'as pas assez de place.
	 * @param hauteur hauteur de la brique à ajoutée.
	 * @param longueur longueur de la brique à ajoutée.
	 */
	private void creerEspaceSiNecessaire(int hauteur, int longueur)
	{
		//Les briques devant apparaitre peu à peu on les crée au dessus de ce qui est visible.
		
		//Si il n'y a pas d'espace supplémentaire, on le crée.
		if(lignesHorizontales.size() == MAXLINES)
		{
			for(int i = 0; i < hauteur; i++)
			{
				createNewEmptyLine();
			}
		}
		//Sinon on cherche combien on doit en ajouter pour intégrer la brique en haut a gauche.
		else
		{
			int nbLigne = lignesHorizontales.size();
			//Si nous n'avons pas assez d'espaces minimum supplémentaire pour inserer la brique dans le non visible, on le crée.
			if(nbLigne < MAXLINES + hauteur)
			{
				for(int i = 0 ; i < MAXLINES + hauteur - nbLigne; i++)
				{
					createNewEmptyLine();
				}
			}
			
			int cpt = 0;
			//On regarde si dans l'espace supp qu'on a, on peut intégrer la brique.
			for(int i = lignesHorizontales.size() -1; i <= MAXLINES || cpt < hauteur; i--)
			{
				if(!doesFitInLine(i, longueur))
					break;
				cpt++;
			}
			
			// Si il nous en manquais, on l'ajoute.
			for(int i = cpt; i < hauteur; i++)
			{
				createNewEmptyLine();
			}
		}
	}
	
	private void creerBriqueSolide(int hauteur, int longueur)
	{
		//Les brique sont ajoutées le plus en haut à gauche de la matrice.
		Brique brique = new Brique(hauteur, longueur, lignesHorizontales.size() - hauteur, lignesHorizontales);
		brique.creerBrique(false);
		briquesPresentes.add(brique);	
	}
	
	/**
	 * Méthode qui crée une nouvelle ligne vide au dessus de la dernière ligne de la matrice.
	 */
	private void createNewEmptyLine()
	{
		lignesHorizontales.add(new ArrayList<>());
		for(int i = 0; i < MAXBLOCPERLINE; i++)
		{
			Bloc newBloc = new Bloc(BlocColor.Vide,i,lignesHorizontales.size()-1);
			lignesHorizontales.get(lignesHorizontales.size()-1).add(newBloc);
			lignesVerticales.get(i).add(newBloc);
		}
	}
	
	/**
	 * Méthode qui permet de savoir si l'on mettre des blocs sur toute la longueur à cette endroit.
	 * @param ligne position en y où l'on souhaite savoir si c'est vide.
	 * @param longueur longueur de la ligne dont on souhaite savoir si c'est vide.
	 * @return Vrai si il y a de l'espace sinon faux.
	 */
	private boolean doesFitInLine(int ligne, int longueur)
	{
		for(int i = 0; i < longueur; i++)
		{
			if(lignesHorizontales.get(ligne).get(i).getCouleur().compareTo(BlocColor.Vide) != 0)
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * Méthode qui permet de trover une combinaison sur une ligne.
	 * @param ligne ligne où l'on cherche des combinaisons.
	 * @param indToStart indice de départ où l'on commence à cherche sur la ligne.
	 * @param indToEnd indice de fin où l'on arrete de cherche sur la ligne.
	 * @return la liste des combinaisons trouvées sur la ligne.
	 */
	private ArrayList<Combinaison> trouverCombinaisonLigne(ArrayList<Bloc> ligne, int indToStart, int indToEnd)
	{
		int cptMemeCouleur = 1,deb = 0,fin = 0;
		BlocColor prevColor = BlocColor.Vide;
		ArrayList<Combinaison> res = new ArrayList<>();
		
		for(int i = indToStart; i < indToEnd; i++)
		{
			Bloc tmp = ligne.get(i);
			if(tmp.getCouleur().compareTo(BlocColor.Vide) != 0 && tmp.getEtat().canCombine() && tmp.getCouleur().compareTo(prevColor) == 0 && !tmp.getCouleur().isSolid())
			{
				cptMemeCouleur++;
			}
			else
			{
				fin = i - 1;
				if(cptMemeCouleur >= 3)
				{
					Set<Bloc> blocImpliquees = new HashSet<>();
					for(int j = deb; j <= fin; j++)
					{
						blocImpliquees.add(ligne.get(j));
					}
					res.add(new Combinaison(blocImpliquees));
				}

				if(!tmp.getEtat().canCombine())
					prevColor = BlocColor.Vide;
				else
					prevColor = tmp.getCouleur();
				cptMemeCouleur = 1;
				deb = i;
			}
		}
		if(cptMemeCouleur >= 3)
		{
			fin = ligne.size() - 1;
			Set<Bloc> blocImpliquees = new HashSet<>();
			for(int j = deb; j <= fin; j++)
			{
				blocImpliquees.add(ligne.get(j));
			}
			res.add(new Combinaison(blocImpliquees));
		}
		
		return res;
	}
	
	/**
	 * Méthode qui permet de trouver les combinaisons sur la matrice.
	 * @return les combinaisons trouvées sur la matrice.
	 */
	private ArrayList<Combinaison> trouverCombinaisons()
	{
		ArrayList<Combinaison> combinaisons = new ArrayList<>();
		ArrayList<Combinaison> res = new ArrayList<>();
		
		//On récupère toutes les combinaisons.
		for(ArrayList<Bloc> ligne : lignesVerticales)
		{
			combinaisons.addAll(trouverCombinaisonLigne(ligne,1,ligne.size()));
		}
		for(int i = 1; i < lignesHorizontales.size(); i++)
		{
			combinaisons.addAll(trouverCombinaisonLigne(lignesHorizontales.get(i),0,lignesHorizontales.get(i).size()));
		}
		
		//On combine les combinaisons horizontales avec celles verticales si il y en a.
		boolean modif;
		Combinaison courant;
		
		while(combinaisons.size() != 0)
		{
			courant = combinaisons.remove(0);
			modif = true;
			while(modif)
			{
				modif = false;
				for(Combinaison c : combinaisons)
				{
					for(Bloc b : c.getBlocsImpliques())
					{
						if(courant.getBlocsImpliques().contains(b))
						{
							modif = true;
							break;
						}
					}
					if(modif)
					{
						courant = Combinaison.createNewCombinaison(courant, c);
						combinaisons.remove(c);
						break;
					}
				}
			}
			res.add(courant);
		}
		
		return res;
	}
	
	/**
	 * Méthode qui permet de set tous les blocs en état d'animation de combinaison.
	 * @param blocs tous les blocs qu'on souhaite passés en état d'animation de combinaison.
	 */
	private void setToCombiAnimation(Set<Bloc> blocs)
	{
		for(Bloc bloc : blocs)
		{
			bloc.setEtat(EtatBloc.COMBINAISON_ANIMATION);
		}
	}
	
	/**
	 * Méthode qui permet d'ajouter, à l'ensemble en paramètre, toutes les briques à adjacentes a ce même ensemble de briques.
	 * @param briques ensemble des briques dont on souhaite connaitre les briques adjacentes.
	 */
	private void ajouterBriquesAdjacentes(HashSet<Brique> briques)
	{	
		boolean ajout = false;
		HashSet<Brique> res = new HashSet<>();
		
		for(Brique b1 : briques)
		{
			for(Brique b2 : briquesPresentes)
			{
				if(b1.adjacentes(b2) && !briques.contains(b2))
				{
					res.add(b2);
					ajout = true;
				}
			}
		}

		briques.addAll(res);
		
		
		if(ajout)
			ajouterBriquesAdjacentes(briques);
	}
	
	/**
	 * Méthode qui permet de récupérer les combinaisons présentes dans la matrice ainsi que les briques touchée par ces combinaisons.
	 * @return un {@link CombiBrick} contenant les combinaisons et les briques touchées.
	 */
	public synchronized CombiBrick recupererCombinaisons()
	{
		ArrayList<Combinaison> allCombinaisons = trouverCombinaisons();
		HashSet<Brique> briquesTouchees = briquesACoteDe(allCombinaisons);
		ajouterBriquesAdjacentes(briquesTouchees);
		ArrayList<Brique> resBrique = new ArrayList<>(briquesTouchees);
		
		for(Combinaison combi : allCombinaisons)
		{
			setToCombiAnimation(combi.getBlocsImpliques());
		}
		
		for(Brique brique : briquesTouchees)
		{
			setToCombiAnimation(brique.getBlocsDeLaBrique());
		}
		
		return new CombiBrick(allCombinaisons, resBrique);
	}
	
	/**
	 * Méthode qui permet de passer tous les blocs dont les colonnes dépassent la 12ème ligne à la fin de la partie, en état fin.
	 * L'état fin indique les colonnes qui vous ont fait perdre.
	 */
	public void switchToFinishState()
	{
		ArrayList<Bloc> derniereLigne = lignesHorizontales.get(MAXLINES - 1);
		for(int x = 0; x < derniereLigne.size(); x++)
		{
			if(!derniereLigne.get(x).getCouleur().equals(BlocColor.Vide))
			{
				for(Bloc bloc : lignesVerticales.get(x))
				{
					if(bloc.getCouleur().isNormalColor())
					{
						bloc.setEtat(EtatBloc.FIN);
					}
				}
			}
		}
	}

}
