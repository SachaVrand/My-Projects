package principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Classe repr�sentant un joueur de type Ordinateur.
 * @author Sacha et Clement
 *
 */
public class Ordi extends Joueur
{
	/**
	 * Classe interne permettant de repr�senter la probabilit� d'une carte. Cette est uniquement utile pour l'intelligence artificielle.
	 * Elle permet de "determiner" si une carte � plus de chance d'�tre celle du crime ou non, et si elle n'appartient pas au crime elle permet de savoir qui poss�de la carte.
	 * @author Sacha
	 *
	 */
	private class ProbabiliteCarte
	{
		/**
		 * Carte repr�sentant une carte du jeu Cluedo.
		 */
		Carte carte;
		
		/**
		 * Indice du joueur poss�dant la carte. -1 si on ne sait pas qui la poss�de.
		 */
		int indJoueurPossedant;
		/**
		 * Entier repr�sentant la probabilit� d'une carte. 
		 */
		int indiceProbabilite;
		
		/**
		 * Instancie un nouvel objet ProbabiliteCarte avec le nom du joueur poss�dant � "inconnu"
		 * @param carte Carte du jeu Cluedo.
		 */
		public ProbabiliteCarte(Carte carte)
		{
			this.carte = carte;
			this.indJoueurPossedant = -1;
			indiceProbabilite = 100;
		}
	}
	
	/**
	 * Constante enti�re repr�sentant la valeur � ajouter aux probalilit�s dans le cas d'une suggestion que personne n'as pu r�futer.
	 */
	public final static int SUGGEST_NO_REFUTATION = 30;
	
	/**
	 * Constante enti�re repr�sentant la valeur � ajouter aux probabilit�s dans le cas d'une accusation d'un joueur qui � echouer.
	 */
	public final static int LOSING_AFTER_ACCUSE = -10;
	
	/**
	 * Constante enti�re repr�sentant la valeur � ajouter aux probabilit�s dans le cas d'une suggestion que quelqu'un � pu r�futer.
	 */
	public final static int SUGGEST_REFUTATION = -20;
	
	/**
	 * Liste des ProbabiliteCarte pour les cartes de type Suspect
	 */
	private List<ProbabiliteCarte> listePSuspects;
	
	/**
	 * Liste des ProbabiliteCarte pour les cartes de type Arme
	 */
	private List<ProbabiliteCarte> listePArmes;
	
	/**
	 * Liste des ProbabiliteCarte pour les cartes de type Lieu
	 */
	private List<ProbabiliteCarte> listePLieux;
	
	/**
	 * Repr�sente le niveau d'intelligence de l'objet Ordi. 0 = Random
	 */
	private int niveauIA;
	
	/**
	 * Repr�sente la liste des cartes deja montr�es par l'ordi � n'importe quel joueur.
	 */
	private List<String> cartesDejaMontrees;
	
	/**
	 * Pour le niveau 2 de refuter : table de hachage ayant pour cl� l'indice du joueur, et valeur une liste de cartes deja montr�es
	 */
	private HashMap<Integer, List<String>> cartesMontreesParJoueur;
	
	/**
	 * indice dans la liste des joueurs de la partie repr�sentant le joueur en train de jouer.
	 */
	private int joueurActuel;
	
	/**
	 * indice dans la liste des joueurs de la partie repr�sentant le joueur en train de r�futer.
	 */
	private int joueurRefutant;
	
	/**
	 * Bool�en repr�sentant si ma suggestion a pu �tre r�futer. Vrai si elle n'as pas pu �tre r�futer, sinon faux.
	 */
	private boolean auncuneRefutationDeMonCoup;
	
	/**
	 * Bool�en repr�sentant si la suggestiondu joueur autre que moi a pu �tre r�futer. Vrai si elle n'as pas pu �tre r�futer, sinon faux. et null si c'�tait le tour de ce joueur avant.
	 */
	private Boolean aucuneRefutationAutre;
	
	/**
	 * Entier permettant de savoir si il reste des cartes inconnues � determiner, si l'entier est �gale � 3 alors il ne reste plus que les cartes du crime dans les listes de ProbabiliteCarte.
	 * sinon si il est inf�rieur � 3, il reste plus d'une inconnu dans une des listes de ProbabiliteCarte
	 */
	private int doitAccuser;
	
	/**
	 * Tableau de String repr�sentant la derniere suggestion que ce joueur a fait.
	 */
	private String[] monDernierCoupJouer;
	
	/**
	 * Tableau de String repr�sentant la derni�re suggest faite par un joueur autre que celui ci.
	 */
	private String[] dernierCoupJouer;
	
	/**
	 * Nombre de tour dans la partie. Pour le moment elle n'est pas utilis�.
	 */
	private int nombreTour;
	
	/**
	 * Instancie un nouvelle Ordi avec le nom de joueur et le niveau d'intelligence de l'IA choisi.
	 * Si le niveau d'IA choisi est inf�rieur � 2 instancie une ArrayList pour carteDejaMontrees, sinon instancie une HashMap vide pour CartesMontreesParJoueur.
	 * Initialise auncuneRefutationDeMonCoup � faux, aucuneRefutationAutre � faux, monDernierCoupJouer � null, dernierCoupJouer � null, doitAccuser � 0, joueurActuel � 0, joueurRefutant � 0.
	 * Instancie les trois listes de ProbabiliteCarte avec chacune des cartes du jeu et le joueur poss�dant � "inconnu" et l'indice de probabilit� � 0;
	 * @param nom Nom du joueur sous la forme de String
	 * @param IALevel Niveau du joueur sous la forme d'entier, de 0 � 2.
	 */
	public Ordi(String nom, int IALevel)
	{
		super(nom);
		this.niveauIA = IALevel;
		this.listePArmes = new ArrayList<ProbabiliteCarte>();
		this.listePLieux = new ArrayList<ProbabiliteCarte>();
		this.listePSuspects = new ArrayList<ProbabiliteCarte>();
		if(niveauIA < 2)
		{
			this.cartesDejaMontrees = new ArrayList<String>();
		}
		else
		{
			this.cartesMontreesParJoueur = new HashMap<>();
		}
		nombreTour = 1;
		auncuneRefutationDeMonCoup = false;
		aucuneRefutationAutre = false;
		monDernierCoupJouer = null;
		dernierCoupJouer = null;
		initialiserProbabiliteCartes();
		doitAccuser = 0;
		joueurActuel = -1;
		joueurRefutant = -1;
	}
	
	/**
	 * M�thode qui permet de changer le nom du joueur actuel avec celui pass� en param�tre.
	 * @param indJoueur Indice du joueur dans la liste des joueurs.
	 */
	public void setJoueurActuel(int indJoueur)
	{
		this.joueurActuel = indJoueur;
		if(niveauIA >= 2 && !cartesMontreesParJoueur.containsKey(indJoueur))
		{
			cartesMontreesParJoueur.put(indJoueur, new ArrayList<String>());
		}
	}
	
	/**
	 * M�thode qui permet de changer le nom du joueur r�futant avec celui pass� en param�tre.
	 * @param indJoueur Indice du joueur dans la liste des joueurs.
	 */
	public void setJoueurRefutant(int indJoueur)
	{
		this.joueurRefutant = indJoueur;
	}
	
	/**
	 * M�thode qui permet de changer le dernier coup jou� par un autre joueur avec le tableau de String pass� en param�tre.
	 * @param dernierCoup Tableau de String repr�sentant le dernier coup jouer par un autre joueur. [0]String de Carte [1]String de Carte [2]String de Carte
	 */
	public void setDernierCoupJouer(String[] dernierCoup)
	{
		this.dernierCoupJouer = dernierCoup;
	}
	
	/**
	 * M�thode qui permet de r�cup�rer le dernier coup jouer par un autre joueur.
	 * @return le dernier coup jouer par un autre joueur sous la forme d'un tableau de chaine de caract�res.
	 */
	public String[] getDernierCoupJouer()
	{
		return this.dernierCoupJouer;
	}
	
	/**
	 * M�thode qui permet de changer le bool�en repr�sentant si mon coup � pu �tre r�futer ou non
	 * @param b Vrai si il n'as pas pu etre r�futer, faux sinon.
	 */
	public void setAucuneRefutationDeMonCoup(boolean b)
	{
		this.auncuneRefutationDeMonCoup = b;
	}
	
	/**
	 * M�thode qui permet de changer le bool�en repr�sentant si le coup dd'un joueur autre que moi � pu �tre r�futer.
	 * @param b Vrai si n'as pas pu �tre r�futer, faux sinon. et null si c'etait mon tour juste avant.
	 */
	public void setAucuneRefutationAutre(Boolean b)
	{
		this.aucuneRefutationAutre = b;
	}
	
	/**
	 * M�thode qui permet de r�cup�rer le bool�en aucuneRefutationAutre
	 * @return aucuneRefutationAutre
	 */
	public Boolean getAucuneRefutationAutre()
	{
		return this.aucuneRefutationAutre;
	}
	
	/**
	 * M�thode qui permet � l'intelligence artificielle de faire une suggestion ou une accusation en fonction de son niveau d'intelligence. 
	 * @return Un tableau de String repr�sentant le coup jou�, [0]"suggest" ou "accuse", [1-3]Carte sous la forme de String, 1 de chaque type.
	 */
	@Override
	public String[] jouerCoup() 
	{
		String[] res = null;
		
		//Random
		if(niveauIA == 0)
		{
			res = getCoupRandom();
		}
		//Level 1
		else if(niveauIA == 1)
		{
			res = getCoupLevelOneOrTwo();
		}
		//Level 2
		else if(niveauIA == 2)
		{
			res = getCoupLevelOneOrTwo();
		}

		auncuneRefutationDeMonCoup = true;
		if(nombreTour == 1 && niveauIA > 1)
		{
			auncuneRefutationDeMonCoup = false;
		}
		monDernierCoupJouer = res;
		nombreTour++;
		return res;
	}

	/**
	 * M�thode qui permet � l'intelligence artificielle de r�futer en fonction de niveau d'intelligence.
	 * @return Chaine repr�sentant la carte montrer.
	 */
	@Override
	public String refuter(List<String> cartesCommun) 
	{
		String res = "";
		
		//Random
		if(niveauIA == 0)
		{
			res = getRefuterRandom(cartesCommun);
		}
		//Level 1
		else if(niveauIA == 1)
		{
			res = getRefuterLevelOne(cartesCommun);
		}
		//Level 2
		else if(niveauIA == 2)
		{
			res = getRefuterLevelTwo(cartesCommun);
		}

		return res;
	}
	
	/**
	 * M�thode qui permet de jouer un coup pour une intelligence de niveau 0.
	 * Sugg�re des cartes au hasard parmis celle qu'elle ne connais pas et accuse des lors qu'elle connait toutes les cartes des joueurs.
	 * @return Un tableau de String repr�sentant le coup jou�, [0]"suggest" ou "accuse", [1-3]Carte sous la forme de String, 1 de chaque type.
	 */
	private String[] getCoupRandom()
	{
		String[] res = new String[4];
		doitAccuser = 0;
		String tmpArme = this.getCarteProbable(this.listePArmes);
		String tmpLieu = this.getCarteProbable(this.listePLieux);
		String tmpSuspect = this.getCarteProbable(this.listePSuspects);
		
		if(doitAccuser == 3)
		{
			res[0] = "accuse";
			res[1] = tmpArme;
			res[2] = tmpLieu;
			res[3] = tmpSuspect;
		}
		else
		{
			res[0] = "suggest";
			res[1] = tmpArme;
			res[2] = tmpLieu;
			res[3] = tmpSuspect;
		}
		return res;
	}
	
	/**
	 * M�thode qui permet de jouer un coup pour une intelligence de niveau 1 ou 2.
	 * Dans le cas d'un niv 1 : Sugg�re des cartes au hasard parmis celle qu'elle ne connais pas. Et accuse d�s lors qu'elle � soit sugg�rer des cartes qui ne lui appartenais pas et que personne n'as pu r�pondre. Ou des lors qu'elle connais toutes les cartes des autres joueurs.
	 * Dans le cas d'un niveau 2: Sugg�re des cartes au hasard parmis celle qu'elle ne connais pas. Et accuse d�s lors qu'elle � soit sugg�rer des cartes qui ne lui appartenais pas et que personne n'as pu r�pondre. Ou des lors qu'elle connais toutes les cartes des autres joueurs.
	 * @return Un tableau de String repr�sentant le coup jou�, [0]"suggest" ou "accuse", [1-3]Carte sous la forme de String, 1 de chaque type.
	 */
	private String[] getCoupLevelOneOrTwo()
	{
		String[] res = new String[4];
		doitAccuser = 0;
		String tmpArme = this.getCarteProbable(this.listePArmes);
		String tmpLieu = this.getCarteProbable(this.listePLieux);
		String tmpSuspect = this.getCarteProbable(this.listePSuspects);
		
		
		if(auncuneRefutationDeMonCoup)
		{
			monDernierCoupJouer[0] = "accuse";
			return monDernierCoupJouer;
		}
		else
		{
			if(doitAccuser == 3)
			{
				res[0] = "accuse";
				res[1] = tmpArme;
				res[2] = tmpLieu;
				res[3] = tmpSuspect;
			}
			else
			{
				res[0] = "suggest";
				res[1] = tmpArme;
				res[2] = tmpLieu;
				res[3] = tmpSuspect;
			}
		}
		return res;
	}
	
	/**
	 * M�thode qui permet de r�futer pour une intelligence de niveau 0.
	 * Montre une carte au hasard parmis celle qu'elle peut r�futer.
	 * @param cartesCommun Liste des cartes en commun entre la suggestion et les cartes de ce joueur.
	 * @return Une carte sous la forme de String.
	 */
	private String getRefuterRandom(List<String> cartesCommun)
	{
		Collections.shuffle(cartesCommun);
		return cartesCommun.get(0);
	}
	
	/**
	 * M�thode qui permet de r�futer pour une intelligence de niveau 1.
	 * Montre une carte qu'elle � d�j� montrer si possible, sinon montre une carte au hasard.
	 * @param cartesCommun Liste des cartes en commun entre la suggestion et les cartes de ce joueur.
	 * @return Une carte sous la forme de String.
	 */
	private String getRefuterLevelOne(List<String> cartesCommun)
	{
		String res = "";
		if(cartesDejaMontrees.size() != 0)
		{
			for(String carteDejaMontre : cartesDejaMontrees)
			{
				for(String carte : cartesCommun)
				{
					if(carteDejaMontre.equals(carte))
					{
						return carte;
					}
				}
			}
		}
		res = getRefuterRandom(cartesCommun);
		cartesDejaMontrees.add(res);
		return res;
	}
	
	/**
	 * M�thode qui permet de r�fute pour une intelligence de niveau 2.
	 * Montre une carte que le joueur qui sugg�re lui � d�j� demand� en priorit�, sinon une carte qu'elle a d�j� montr�e � un autre joueur, sinon une carte au hasard.
	 * @param cartesCommun cartesCommun Liste des cartes en commun entre la suggestion et les cartes de ce joueur.
	 * @return Une carte sous la forme de String.
	 */
	private String getRefuterLevelTwo(List<String> cartesCommun)
	{
		//cas o� nous avons d�j� montr� l'une des cartes demand�es au joueur.
		for (String carte : cartesCommun)
		{
			if(cartesMontreesParJoueur.get(joueurActuel).contains(carte))
			{
				return carte;
			}
		}
		//cas o� nous avons deja montr� l'une des cartes demand�es � un autre joueur.
		for(List<String> listeCartes : cartesMontreesParJoueur.values())
		{
			for(String carte : listeCartes)
			{
				if(cartesCommun.contains(carte))
				{
					cartesMontreesParJoueur.get(joueurActuel).add(carte);
					return carte;
				}
			}
		}
		//cas o� aucune des cartes demand�es n'a d�j� �t� montr�es.
		String res = getRefuterRandom(cartesCommun);
		cartesMontreesParJoueur.get(joueurActuel).add(res);
		return res;
	}
	
	/**
	 * M�thode qui permet d'initialiser les listes de ProbabiliteCarte avec toutes les cartes du jeu.
	 */
	private void initialiserProbabiliteCartes()
	{
		for(Carte c : Carte.creerPaquetDeCartes())
		{
			if(c instanceof Arme)
			{
				listePArmes.add(new ProbabiliteCarte(c));
			}
			else if(c instanceof Lieu)
			{
				listePLieux.add(new ProbabiliteCarte(c));
			}
			else if(c instanceof Suspect)
			{
				listePSuspects.add(new ProbabiliteCarte(c));
			}
		}
	}
	
	/**
	 * M�thode qui permet d'ajouter une carte aux cartes du joueur.
	 * @param c Carte � ajouter.
	 */
	@Override
	public void ajouterCarte(Carte c)
	{
		super.ajouterCarte(c);
		this.ajouterCarteConnue(c, myIndInList);
	}
	
	/**
	 * M�thode qui permet de changer le nomJoueurPossedant de ProbabiliteCarte apr�s avoir d�couvert � qui elle appartient.
	 * @param c Carte d�couverte
	 * @param indJoueur Indice du joueur la poss�dant.
	 */
	public void ajouterCarteConnue(Carte c, int indJoueur)
	{
		if(c instanceof Arme)
		{
			for(ProbabiliteCarte pc : listePArmes)
			{
				if(pc.carte.equals(c))
				{
					pc.indJoueurPossedant = indJoueur;
					pc.indiceProbabilite = 0;
					break;
				}
			}
		}
		else if(c instanceof Lieu)
		{
			for(ProbabiliteCarte pc : listePLieux)
			{
				if(pc.carte.equals(c))
				{
					pc.indJoueurPossedant = indJoueur;
					pc.indiceProbabilite = 0;
					break;
				}
			}
		}
		else if(c instanceof Suspect)
		{
			for(ProbabiliteCarte pc : listePSuspects)
			{
				if(pc.carte.equals(c))
				{
					pc.indJoueurPossedant = indJoueur;
					pc.indiceProbabilite = 0;
					break;
				}
			}
		}
	}
	
	/**
	 * M�thode qui permet d'ajouter, � l'indice de probabilit� des dernieres carte jouer par un autre joueur, si elle ne sont pas connu, l'entier pass� en param�tre.
	 * N'as pas d'effet si le niveauIA est inf�rieur � 2.
	 * Dans le cas o� la valeur vaut la constante SUGGEST_REFUTATION alors on v�rifie si nous connaissons les deux autres carte, et si oui changer le nomJoueurPossedant � celui qui vient de r�futer.
	 * @param valeur Entier � ajouter aux ProbabiliteCarte.
	 */
	public void changerProbDerCartes(int valeur)
	{
		if(niveauIA < 2)
		{
			return;
		}
		boolean trouve;
		int nbCarteConnues = 0;
		ProbabiliteCarte pcInconnu = null;
		
		for(String carte : dernierCoupJouer)
		{
			trouve = false;
			for(ProbabiliteCarte pc : listePArmes)
			{
				if(pc.carte.getNom().equals(carte))
				{
					if(pc.indJoueurPossedant == -1)
					{
						pc.indiceProbabilite += valeur;
						pcInconnu = pc;
					}
					else if(pc.indJoueurPossedant != joueurRefutant)
					{
						nbCarteConnues++;
					}
					trouve = true;
					break;
				}
			}
			if(!trouve)
			{
				for(ProbabiliteCarte pc : listePLieux)
				{
					if(pc.carte.getNom().equals(carte))
					{
						if(pc.indJoueurPossedant == -1)
						{
							pc.indiceProbabilite += valeur;
							pcInconnu = pc;
						}
						else if(pc.indJoueurPossedant != joueurRefutant)
						{
							nbCarteConnues++;
						}
						trouve = true;
						break;
					}
				}
				if(!trouve)
				{
					for(ProbabiliteCarte pc : listePSuspects)
					{
						if(pc.carte.getNom().equals(carte))
						{
							if(pc.indJoueurPossedant == -1)
							{
								pc.indiceProbabilite += valeur;
								pcInconnu = pc;
							}
							else if(pc.indJoueurPossedant != joueurRefutant)
							{
								nbCarteConnues++;
							}
							break;
						}
					}
				}
			}
		}
		if(valeur == SUGGEST_REFUTATION && nbCarteConnues == 2 && pcInconnu != null)
		{	
			pcInconnu.indiceProbabilite = 0;
			pcInconnu.indJoueurPossedant = joueurRefutant;
		}
	}
	
	/**
	 * M�thode qui retourne la carte qui � le plus de probabilit� d'�tre la carte du meutre.
	 * @param listeATrier Liste de ProbabiliteCarte dont vous souhaitez savoir la carte la plus probable.
	 * @return La carte avec la plus grande probabilit� d'�tre la carte du meutre.
	 */
	private String getCarteProbable(List<ProbabiliteCarte> listeATrier)
	{
		List<ProbabiliteCarte> res = new ArrayList<>();
		for(ProbabiliteCarte pc : listeATrier)
		{
			if(pc.indJoueurPossedant == -1)
			{
				res.add(pc);
			}
		}
		Collections.sort(res,new Comparator<ProbabiliteCarte>() {

			@Override
			public int compare(ProbabiliteCarte o1, ProbabiliteCarte o2) {
				if(o1.indiceProbabilite > o2.indiceProbabilite)
				{
					return -1;
				}
				else if(o1.indiceProbabilite < o2.indiceProbabilite)
				{
					return 1;
				}
				else
				{
					return 0;
				}
			}
			
		});
		if(res.size() == 1)
		{
			doitAccuser += 1;
			return res.get(0).carte.getNom();
		}
		int max = res.get(0).indiceProbabilite;
		Iterator<ProbabiliteCarte> it = res.iterator();
		while(it.hasNext())
		{
			if(it.next().indiceProbabilite != max)
			{
				it.remove();
			}
		}
		Collections.shuffle(res);
		return res.get(0).carte.getNom();
	}
}
