package donnees;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Observable;

import controleurs.GrilleController;
import controleurs.JoueurController;

/**
 * Classe permettant de gérer les combinaisons, chaines, ajout de scores, ajout de briques chez l'adversaire et la tombée des blocs et briques.
 * @author Sacha
 *
 */
public class ThreadCombo extends Observable implements Runnable{
	
	/**
	 * Niveau du combo trouvée s'il y à.
	 */
	private int niveauCombo;
	
	/**
	 * Liste de toutes les combinaisons trouvées par ce thread.
	 */
	private ArrayList<Combinaison> totalCombinaisons;
	
	/**
	 * Controleur de la grille du joueur.
	 */
	private GrilleController myGC;
	
	/**
	 * Controleur de la grille du joueur adverse.
	 */
	private GrilleController opponentGC;
	
	/**
	 * Controleur du joueur adverse.
	 */
	private JoueurController opponentJC;
	
	/**
	 * Score du joueur.
	 */
	private Score score;
	
	/**
	 * Constructeur de {@link ThreadCombo}. Instancie un nouveau {@link ThreadCombo} avec le niveau de combo à 0. 
	 * Et une liste à vide pour le total des combinaisons.
	 * @param myJC Controleur du joueur.
	 * @param opponentJC Controleur du joueur adverse.
	 * @param score Score du joueur.
	 */
	public ThreadCombo(JoueurController myJC, JoueurController opponentJC,Score score) {
		super();
		this.myGC = myJC.getGrilleController();
		this.opponentGC = opponentJC.getGrilleController();
		this.opponentJC = opponentJC;
		this.niveauCombo = 0;
		this.totalCombinaisons = new ArrayList<>();
		this.score = score;
	}
	
	/**
	 * Vérifie si il y a une combinaison dans la grille du joueur à instant t. Si il n'y en a pas ne fais rien, et notifie les observateurs.
	 * Sinon il passe en animation les blocs de la combinaisons. Les détruits après 1.2sec. Fait tombée les blocs si il y a. 
	 * Et revérifie si il n'y a pas une autre combinaison. et ainsi de suite. Ajoute les score et envoie les briques chez l'adversaire.
	 */
	@Override
	public void run() {
		CombiBrick combinaisonsCourantes;
		
		this.faireLaTombee(myGC);
		
		while((combinaisonsCourantes = myGC.getGrille().recupererCombinaisons()).getAllCombinaisons().size() != 0)
		{
			if(niveauCombo == 0)
				myGC.suspendTimers();
			
			totalCombinaisons.addAll(combinaisonsCourantes.getAllCombinaisons());
			this.niveauCombo++;
			
			try {
				Thread.sleep(1200);
			} catch (InterruptedException e) {
				return;
			}

			//virer les blocs en animation et descendre les autres blocs.
			myGC.getGrille().virerBlocsEtBriques(combinaisonsCourantes);
			faireLaTombee(myGC);
			if(Thread.currentThread().isInterrupted()) return;
		}		

		if(totalCombinaisons.size()  > 0)
		{
			myGC.resumeTimers();
			score.addScore(calculScore());
			verifierAjoutBriques();
		}
		
		this.setChanged();
		this.notifyObservers();
	}
	
	/**
	 * Méthode qui calcul le score réalisé par le total des combinaisons.
	 * @return le score réalisé par le total des combinaisons.
	 */
	public int calculScore()
	{
		int scoreFinal = 0;
		for(Combinaison combinaison : totalCombinaisons)
		{
			scoreFinal += 10 * (combinaison.getTaille() - 2);
		}
		scoreFinal *= niveauCombo;
		return scoreFinal;
	}

	/**
	 * Méthode qui va vérifier si les combinaisons ont entrainées l'ajout de briques chez l'adversaire. Si oui les ajoutes.
	 */
	public void verifierAjoutBriques()
	{
		ArrayList<Dimension> dims = trouverDimensionsBriques();
		if(dims.size() > 0)
			preventionsAjoutsEtChuteBriques(dims);
	}
	
	/**
	 * Méthode qui permet d'avertir chez le joueur adversaire des briques qui vont tombées. Les ajoutes après 3sec et les fait tombées.
	 * @param listeBriques Liste des briques à ajoutées sous forme de dimensions.
	 */
	private void preventionsAjoutsEtChuteBriques(ArrayList<Dimension> listeBriques)
	{
		for(Dimension d : listeBriques)
		{
			opponentJC.prevenirBrique(d.width, d.height);
		}
		
		try { 
			Thread.sleep(3000); 
		} catch (InterruptedException e) {
			return;
		}
		
		for(Dimension d : listeBriques)
		{
			opponentJC.finPrevention(d.width, d.height);
		}
		
		opponentGC.suspendTimers();
		
		for(Dimension d : listeBriques)
		{
			opponentGC.getGrille().ajouterBriqueSolide(d.height, d.width);
		}
		
		this.faireLaTombee(opponentGC);
		if(Thread.currentThread().isInterrupted()){return;}
		opponentGC.getGrille().warnLines();
		opponentGC.resumeTimers();
	}
	
	/**
	 * Méthode qui permet de faire tombées les blocs et briques de facon temporisée.
	 * @param gc controleur de la grille où l'on doit faire tomber des blocs.
	 */
	private void faireLaTombee(GrilleController gc)
	{
		ArrayList<IBloc> fallingBlocks;
		
		fallingBlocks = gc.getGrille().searchForFallingBlocks();
		
		if(fallingBlocks.size() != 0)
		{
			gc.suspendTimers();
			
			try {
				Thread.sleep(60);
			} catch (InterruptedException e1) {
				return;
			}
			
			while(fallingBlocks.size() > 0)
			{
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					return;
				}
				fallingBlocks = gc.getGrille().moveDownFallingBlocks(fallingBlocks);
			}	
			gc.resumeTimers();
		}
		
		gc.getGrille().fadeWarnings();
	}
	
	/**
	 * Méthode qui permet de trouver toutes les briques à ajouter par rapport au total de combinaisons.
	 * @return Toute les briques à ajoutées sous forme de dimensions. hauteur et largeur de la brique.
	 */
	private ArrayList<Dimension> trouverDimensionsBriques()
	{
		ArrayList<Dimension> res = new ArrayList<>();
		
		if(totalCombinaisons.size() > 0)
		{
			if(niveauCombo == 1)
			{
				int taille = 0;
				for(Combinaison combinaison : totalCombinaisons)
				{
					taille += combinaison.getTaille();
				}
				
				if(taille >= 4 && taille <= 7)
				{
					res.add(new Dimension(taille - 1, 1));
				}
				else if(taille == 8)
				{
					res.add(new Dimension(3, 1));
					res.add(new Dimension(4, 1));
				}
				else if(taille == 9)
				{
					res.add(new Dimension(4, 1));
					res.add(new Dimension(5, 1));
				}
				else if(taille == 10)
				{
					res.add(new Dimension(5, 1));
					res.add(new Dimension(5, 1));
				}
				else if(taille == 11)
				{
					res.add(new Dimension(6, 1));
					res.add(new Dimension(6, 1));
				}
				else if(taille >= 12)
				{
					res.add(new Dimension(6, 1));
					res.add(new Dimension(6, 1));
					res.add(new Dimension(6, 1));
				}
			}
			else
			{
				res.add(new Dimension(6, niveauCombo - 1));
			}
		}
		
		return res;
	}
}
