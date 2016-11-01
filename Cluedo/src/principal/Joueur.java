package principal;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstraite r�presentant un joueur de la partie de cluedo.
 * @author Sacha et Clement
 *
 */
public abstract class Joueur {
	
	/**
	 * Liste des joueurs de la partie. N'est pas instanci�e ou affect�e d�s la cr�ation d'un joueur. Doit �tre set quand on en a besoin.
	 */
	protected List<Joueur> playersInTheGame;
	
	/**
	 * Repr�sente le nom du joueur.
	 */
	protected String nom;
	
	/**
	 * Bool�en repr�sentant si le joueur est encore en jeu ou non. true si oui, sinon false.
	 */
	protected boolean encoreEnJeu;
	
	/**
	 * Collection de carte que le joueur a durant une partie.
	 */
	protected List<Carte> cartesJoueur;
	
	/**
	 * Indice de ce joueur dans la liste des joueurs de la partie.
	 */
	protected int myIndInList = -1;
	
	/**
	 * Instancie un nouveau joueur avec le nom et l'adresse pass�s en param�tres, l'attribut encoreEnJeu � true et instancie une nouvelle collections de carte vide.
	 * @param nom Nom du joueur sous la forme d'une chaine de caract�re.
	 */
	public Joueur(String nom)
	{
		this.nom = nom;
		this.encoreEnJeu = true;
		this.cartesJoueur = new ArrayList<Carte>();
		this.playersInTheGame = null;
	}
	
	/**
	 * Retourne la collection de Carte que poss�de le joueur.
	 * @return Collections des cartes du joueur.
	 */
	public List<Carte> getCartesJoueur()
	{
		return this.cartesJoueur;
	}
	
	/**
	 * M�thode qui ajoute la carte pass�e en param�tre � la collection de carte du joueur.
	 * @param c Carte � ajouter
	 */
	public void ajouterCarte(Carte c)
	{
		this.cartesJoueur.add(c);
	}
	
	/**
	 * M�thode qui retourne si joueur est encore jeu ou non.
	 * @return true si encore en jeu, sinon false.
	 */
	public boolean getEncoreEnJeu()
	{
		return this.encoreEnJeu;
	}
	
	/**
	 * M�thode qui permet de set si joueur est encore en jeu.
	 * @param b true si encore en jeu, sinon false.
	 */
	public void setEncoreEnJeu(boolean b)
	{
		this.encoreEnJeu = b;
	}
	
	/**
	 * M�thode qui retourne le nom du joueur sous forme de chaine de caract�res.
	 * @return Nom du joueur sous forme de chaine de caract�res.
	 */
	public String getNom()
	{
		return this.nom;
	}
	
	/**
	 * M�thode qui permet d'affecter la chaine pass�e en param�tre au nom du Joueur.
	 * @param nom Le nom du joueur sous forme de chaine de caract�res.
	 */
	public void setNom(String nom)
	{
		this.nom = nom;
	}
	
	/**
	 * M�thode abstraite qui doit faire jouer (suggestion ou accusation) le joueur actuel sur la partie de cluedo.
	 * @return Un tableau de String repr�sentant les commandes taper par le joueur.
	 */
	public abstract String[] jouerCoup();
	
	/**
	 * M�thode qui doit permettre au joueur de refuter ou non, une suggestion.
	 * @param carteCommun Cartes en commun dans le paquet du joueur avec les cartes suggerer sous la forme de String
	 * @return chaine correspondant � la carte montrer, exit si le joueur veut quitter.
	 */
	public abstract String refuter(List<String> carteCommun);
	
	/**
	 * M�thode permettant de mettre � jour les cartes connues d'un joueur de la liste des joueurs de la partie. Ne fait rien si la liste des joueurs n'a pas �t� set avant.
	 * @param indPlayer Indice du joueur dans la liste.
	 * @param card Nouvelle carte connue sous la forme de chaine
	 */
	public void updateKnownedCardForGUI(int indPlayer, String card)
	{
		if(playersInTheGame == null || indPlayer == myIndInList) return;
		Carte carte = Carte.retrouverCarte(card);
		if(!playersInTheGame.get(indPlayer).getCartesJoueur().contains(carte))
			playersInTheGame.get(indPlayer).ajouterCarte(carte);
	}
	
	/**
	 * M�thode qui permet de set les joueurs de la partie. N'est utile que pour la partie graphique. Doit correspondre au joueur principal avec toutes es cartes et aux autres joueurs sans leurs cartes au d�but.
	 * @param playersInTheGame Liste des joueurs pr�sents dans la partie.
	 */
	public void setPlayersInTheGame(List<Joueur> playersInTheGame) {
		this.playersInTheGame = playersInTheGame;
	}
	
	/**
	 * M�thode qui permet de set l'indice du joueur dans la liste. Obligatoire pour l'IA. Utile pour la GUI.
	 * @param myIndInList Indice de ce joueur dans la liste des joueurs de la partie.
	 */
	public void setMyIndInList(int myIndInList) {
		this.myIndInList = myIndInList;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == this)
			return true;
		else
		{
			Joueur j = (Joueur)obj;
			if((j.nom.equals(this.nom)) && (j.getCartesJoueur().containsAll(this.cartesJoueur)))
				return true;
			return false;
		}
	}
}
