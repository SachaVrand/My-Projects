import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

/**
 * Classe repr�sentant le plateau de jeu de l'othello.
 * Consiste en un panel compos� d'une grille de Pion.
 * G�re aussi les int�raction avec les pions. 
 * Ainsi que les retournements de pions et activations de cases jouables.
 * @author Vrand
 *
 */
public class Plateau extends JPanel implements ActionListener{
	
	private static final long serialVersionUID = -4916543843864419409L;
	
	/**
	 * Liste contenant les differentes grilles de pion apr�s chaque tour des joueurs. Sert pour la sauvegarde de chaque �tats successif du plateau.
	 */
	ArrayList<Object> listePlateauxSauvegarde = null;
	
	/**
	 * Grille de pions se trouvant sur la plateau.
	 */
	private Pion[][] tableauPion = null;
	
	/**
	 * Couleur jouant ce tour.
	 */
	private Couleur CouleurJouant = null;
	
	/**
	 * Jeu sur lequel est dispos� ce plateau.
	 */
	private Jeu jeu = null;
	
	/**
	 * Num�ro du tour actuel.
	 */
	private int numeroCoup;

	/**
	 * Instancie un nouveau plateau avec comme LayoutManager un GridBagLayout. 
	 * Le plateau est instancier avec les 4 pions de d�part blanc et noir,et le reste � vide.
	 * Le numero de coup est initialis� � 1, et les cases jouables sont activ�es.
	 * @param couleurCommencant Couleur commencant � jouer.
	 * @param jeu Instance de Jeu correspondant au jeu en cours.
	 */
	public Plateau(Couleur couleurCommencant,Jeu jeu) 
	{
		super(new GridBagLayout());
		this.tableauPion = new Pion[8][8];
		this.CouleurJouant = couleurCommencant;
		this.numeroCoup = 1;
		listePlateauxSauvegarde = new ArrayList<Object>();
		this.load();
		this.activerCasesJouables(CouleurJouant);
		listePlateauxSauvegarde.add(getPlateauSousTabCouleur());
		this.jeu = jeu;
	}
	
	/**
	 * Instancie un nouveau plateau avec comme LayoutManager un GridBagLayout. 
	 * Ce constructeur sert pour l'affichage d'une sauvegarde.
	 * Le plateau est instancier l'aide du tableau de pions sauvegard�s pass� en param�tre.
	 * Les pions sont des JLabel dans ce cas avec pour texte le coup et la couleur l'ayant jou�.
	 * @param tab Tableau de pion � afficher sur le plateau.
	 */
	public Plateau(PionSauvegarde[][] tab)
	{
		super(new GridBagLayout());
		this.loadSauvegarde(tab);
	}
	
	/**
	 * Instancie un nouveau plateau avec comme LayoutManager un GridBagLayout. 
	 * Le plateau est � partir du tableau de couleur pass�e en param�tre.
	 * @param tab Tableau de couleur � deux dimensions repr�sentant le plateau.
	 */
	public Plateau(Couleur[][] tab)
	{
		super(new GridBagLayout());
		this.setBackground(new Color(0,153,75));
		this.loadGrillePourLeReplay(tab);
	}
	
	/**
	 * M�thode qui cr�e et charge les diff�rents composants graphiques du panel.
	 * C'est � dire un plateau de Pion avec les 4 pions de bases. Remplis le tableau de pions avec les objets cr��s.
	 */
	private void load() 
	{
		this.setBackground(new Color(0,153,75));
		GridBagConstraints gbc = new GridBagConstraints();
		Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
		this.setBorder(border);
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		for (int j = 0; j < 8; j++) 
		{
			gbc.gridy = j;
			for (int i = 0; i < 8; i++) 
			{
				if ((j == 3 && i == 3)||(j == 4 && i == 4)) 
				{
					//Les pions de base blanc
					gbc.gridx = i;
					Pion p = new Pion(j, i,Couleur.BLANC);
					p.setBorder(border);
					this.add(p, gbc);
					tableauPion[j][i] = p;
					p.addActionListener(this);
				} 
				else if ((j == 3 && i == 4)||(j == 4 && i == 3)) 
				{
					//Les pions de base noir
					gbc.gridx = i;
					Pion p = new Pion(j, i,Couleur.NOIR);
					p.setBorder(border);
					this.add(p, gbc);
					tableauPion[j][i] = p;
					p.addActionListener(this);
				}
				else 
				{
					//le reste des pions de base
					gbc.gridx = i;
					Pion p = new Pion(j, i,Couleur.VIDE);
					p.setBorder(border);
					this.add(p, gbc);
					tableauPion[j][i] = p;
					p.addActionListener(this);
				}
			}
		}

	}
	
	/**
	 * M�thode qui cr�e et charge les composants graphiques du panel.
	 * C'est � dire un plateau avec des JLabels(avec coup et couleur ayant jou� �crit dessus) � l'aide du tableau de pion.
	 */
	private void loadSauvegarde(PionSauvegarde[][] tab)
	{
		this.setBackground(new Color(0,153,75));
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		for (int j = 0; j < 8; j++) 
		{
			gbc.gridy = j;
			for (int i = 0; i < 8; i++) 
			{
				gbc.gridx = i;
				JLabel pion = new JLabel();
				pion.setHorizontalTextPosition(JLabel.CENTER);
				pion.setVerticalTextPosition(JLabel.CENTER);
				pion.setHorizontalAlignment(SwingConstants.CENTER);
				pion.setBorder(BorderFactory.createLineBorder(Color.black,1));
				if ((j == 3 && i == 3)||(j == 4 && i == 4)) 
				{
					pion.setIcon(new ImageIcon(tab[i][j].getCouleur().getImagePath()));
				}
				else if ((j == 3 && i == 4)||(j == 4 && i == 3)) 
				{
					pion.setIcon(new ImageIcon(tab[i][j].getCouleur().getImagePath()));
				}
				else if(tab[j][i].getCouleur().equals(Couleur.NOIR))
				{
					pion.setIcon(new ImageIcon(Couleur.NOIR.getImagePath()));
					pion.setForeground(Color.WHITE);
					String couleurJoue = "" + tab[j][i].getJouerPar().toString().charAt(0);
					int coup = tab[j][i].getCoup();
					pion.setText(couleurJoue + coup);
					
				}
				else if(tab[j][i].getCouleur().equals(Couleur.BLANC))
				{
					pion.setIcon(new ImageIcon(Couleur.BLANC.getImagePath()));
					pion.setForeground(Color.BLACK);
					String couleurJoue = "" + tab[j][i].getJouerPar().toString().charAt(0);
					int coup = tab[j][i].getCoup();
					pion.setText(couleurJoue + coup);

				}
				else
				{
					pion.setIcon(new ImageIcon(Couleur.VIDE.getImagePath()));
				}
				this.add(pion,gbc);
			}
		}
	}
	
	/**
	 * M�thode qui met � jour le plateau avec le tableau de couleur pass� en param�tre.
	 * @param tab tableau de couleurs � deux dimensions � afficher sur le plateau.
	 */
	public void loadGrillePourLeReplay(Couleur[][] tab)
	{
		this.setBackground(new Color(0,153,75));
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		for (int i = 0; i < 8; i++) 
		{
			gbc.gridy = i;
			for (int j = 0; j < 8; j++) 
			{
				gbc.gridx = j;
				JLabel pion = new JLabel();
				pion.setBorder(BorderFactory.createLineBorder(Color.black,1));
				pion.setIcon(new ImageIcon(tab[i][j].getImagePath()));
				this.add(pion,gbc);
			}
		}
	}
	
	/**
	 * M�thode qui retourne la couleur jouant ce tour.
	 * @return Object Couleur jouant ce tour.
	 */
	public Couleur getCouleurJouant() {
		return CouleurJouant;
	}
	
	/**
	 * M�thode qui retourne la tableau de pions repr�sentant la grille sur le plateau.
	 * @return tableau de pions � deux dimensions.
	 */
	public Pion[][] getTableauPion()
	{
		return this.tableauPion;
	}

	/**
	 * M�thode qui set la couleur jouant ce tour.
	 * @param couleurJouant couleur jouant ce tour.
	 */
	public void setCouleurJouant(Couleur couleurJouant) {
		CouleurJouant = couleurJouant;
	}

	/**
	 * M�thode qui active les cases jouables.
	 * @param couleur Couleur dont les cases jouables doivent �tre activ�es.
	 */
	public void activerCasesJouables(Couleur couleur)
	{
		activerCasesJouablesEnLigne(couleur);
		activerCasesJouablesEnColonne(couleur);
		activerCasesJouablesEnDiagonales(couleur);
	}
	
	/**
	 * M�thode qui active les cases jouables dont un retournement en ligne est possible, par rapport � la couleur pass� en param�tre.
	 * @param couleur Couleur dont les cases jouables doivent �tre activ�es.
	 */
	private void activerCasesJouablesEnLigne(Couleur couleur)
	{
		Couleur couleurOpposee = getCouleurOpposee(couleur);
		
		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				//v�rification des cases avant un pion jouable en ligne
				if(j != 7 && tableauPion[i][j+1].getCouleur().equals(couleurOpposee) && tableauPion[i][j].getCouleur().equals(Couleur.VIDE))
				{
					for(int k = j+2; k < 8; k++)
					{
						if(tableauPion[i][k].getCouleur().equals(Couleur.VIDE) || tableauPion[i][k].getCouleur().equals(Couleur.SURBRILLANCE))
						{
							break;
						}
						else if(tableauPion[i][k].getCouleur().equals(couleur))
						{
							tableauPion[i][j].changementEstJouable(true);
							break;
						}
					}
				}
				//v�rification des cases apr�s un pion jouable en ligne
				if(j != 0 && tableauPion[i][j-1].getCouleur().equals(couleurOpposee) && tableauPion[i][j].getCouleur().equals(Couleur.VIDE))
				{
					for(int k = j-2; k >= 0; k--)
					{
						if(tableauPion[i][k].getCouleur().equals(Couleur.VIDE) || tableauPion[i][k].getCouleur().equals(Couleur.SURBRILLANCE))
						{
							break;
						}
						else if(tableauPion[i][k].getCouleur().equals(couleur))
						{
							tableauPion[i][j].changementEstJouable(true);
							break;
						}
					}
				}
			}
		}
	}
	
	/**
	 * M�thode qui active les cases jouables dont un retournement en colonne est possible, par rapport � la couleur pass� en param�tre.
	 * @param couleur Couleur dont les cases jouables doivent �tre activ�es.
	 */
	private void activerCasesJouablesEnColonne(Couleur couleur)
	{
		Couleur couleurOpposee = getCouleurOpposee(couleur);
		
		for(int j = 0; j < 8; j++)
		{
			for(int i = 0; i < 8; i++)
			{
				//v�rification des cases avant un pion jouable en colonne
				if(i != 7 && tableauPion[i+1][j].getCouleur().equals(couleurOpposee) && tableauPion[i][j].getCouleur().equals(Couleur.VIDE))
				{
					for(int k = i+2; k < 8; k++)
					{
						if(tableauPion[k][j].getCouleur().equals(Couleur.VIDE) || tableauPion[k][j].getCouleur().equals(Couleur.SURBRILLANCE))
						{
							break;
						}
						else if(tableauPion[k][j].getCouleur().equals(couleur))
						{
							tableauPion[i][j].changementEstJouable(true);
							break;
						}	
					}
				}
				//v�rification des cases jouables apr�s un pion en colonne
				if(i != 0 && tableauPion[i-1][j].getCouleur().equals(couleurOpposee) && tableauPion[i][j].getCouleur().equals(Couleur.VIDE))
				{
					for(int k = i-2; k >= 0; k--)
					{
						if(tableauPion[k][j].getCouleur().equals(Couleur.VIDE) || tableauPion[k][j].getCouleur().equals(Couleur.SURBRILLANCE))
						{
							break;
						}
						else if(tableauPion[k][j].getCouleur().equals(couleur))
						{
							tableauPion[i][j].changementEstJouable(true);
							break;
						}
					}
				}
			}
		}
	}
	
	/**
	 * M�thode qui active les cases jouables dont un retournement en diagonale est possible, par rapport � la couleur pass� en param�tre.
	 * @param couleur Couleur dont les cases jouables doivent �tre activ�es.
	 */
	private void activerCasesJouablesEnDiagonales(Couleur couleur)
	{
		Couleur couleurOpposee = getCouleurOpposee(couleur);
		
		for(int j = 0; j < 8; j++)
		{
			for(int i = 0; i < 8; i++)
			{
				//v�rification des cases jouables en diagonale vers le bas d'un pion
				//A droite
				if(i != 7 && j!= 7 && tableauPion[i+1][j+1].getCouleur().equals(couleurOpposee) && tableauPion[i][j].getCouleur().equals(Couleur.VIDE))
				{
					for(int k = i+2, l = j+2; k < 8 && l < 8; k++,l++)
					{
						if(tableauPion[k][l].getCouleur().equals(Couleur.VIDE) || tableauPion[k][l].getCouleur().equals(Couleur.SURBRILLANCE))
						{
							break;
						}
						else if(tableauPion[k][l].getCouleur().equals(couleur))
						{
							tableauPion[i][j].changementEstJouable(true);
							break;
						}	
					}
				}
				//A gauche
				if(i != 7 && j != 0 && tableauPion[i+1][j-1].getCouleur().equals(couleurOpposee) && tableauPion[i][j].getCouleur().equals(Couleur.VIDE))
				{
					for(int k = i+2, l=j-2; k < 8 && l >=0; k++,l--)
					{
						if(tableauPion[k][l].getCouleur().equals(Couleur.VIDE) || tableauPion[k][l].getCouleur().equals(Couleur.SURBRILLANCE))
						{
							break;
						}
						else if(tableauPion[k][l].getCouleur().equals(couleur))
						{
							tableauPion[i][j].changementEstJouable(true);
							break;
						}	
					}
				}
				
				//v�rification des cases jouables en diagonales vers le haut d'un pion
				//A gauche
				if(i != 0 && j != 0 && tableauPion[i-1][j-1].getCouleur().equals(couleurOpposee) && tableauPion[i][j].getCouleur().equals(Couleur.VIDE))
				{
					for(int k = i-2,l=j-2; k >= 0 && l>=0; k--, l--)
					{
						if(tableauPion[k][l].getCouleur().equals(Couleur.VIDE) || tableauPion[k][l].getCouleur().equals(Couleur.SURBRILLANCE))
						{
							break;
						}
						else if(tableauPion[k][l].getCouleur().equals(couleur))
						{
							tableauPion[i][j].changementEstJouable(true);
							break;
						}
					}
				}
				//A droite
				if(i != 0 && j != 7 && tableauPion[i-1][j+1].getCouleur().equals(couleurOpposee) && tableauPion[i][j].getCouleur().equals(Couleur.VIDE))
				{
					for(int k = i-2,l=j+2; k >= 0 && l < 8; k--, l++)
					{
						if(tableauPion[k][l].getCouleur().equals(Couleur.VIDE) || tableauPion[k][l].getCouleur().equals(Couleur.SURBRILLANCE))
						{
							break;
						}
						else if(tableauPion[k][l].getCouleur().equals(couleur))
						{
							tableauPion[i][j].changementEstJouable(true);
							break;
						}
					}
				}
			}
		}
	}
	
	/**
	 * M�thode qui effectue les retournements des pions par rapport au pion et � la couleur pass�es en param�tres.
	 * @param couleur Couleur effectuant un retournement.
	 * @param pion Pion o� le joueur � jouer.
	 */
	public void modifierPions(Couleur couleur, Pion pion)
	{
		modifierPionsEnLigne(couleur, pion);
		modifierPionsEnColonne(couleur, pion);
		modifierPionsEnDiagonale(couleur, pion);
	}
	
	/**
	 * M�thode qui effectue les retournements des pions en ligne par rapport au pion et � la couleur pass�es en param�tres.
	 * @param couleur Couleur effectuant un retournement.
	 * @param pion Pion o� le joueur � jouer.
	 */
	private void modifierPionsEnLigne(Couleur couleur,Pion pion)
	{
		Couleur couleurOpposee = getCouleurOpposee(couleur);
		boolean versLaDroitePossible = false, versLaGauchePossible = false;
		
		//Recherche d'un pion de meme couleur vers la gauche pour savoir si le changement est possible
		for(int i = pion.getColonne()-2 ; i >= 0; i--)
		{
			if(tableauPion[pion.getLigne()][i].getCouleur().equals(couleur))
			{
				versLaGauchePossible = true;
				break;
			}
		}
		
		//Recherche d'un pion de meme couleur vers la droite pour savoir si le changement est possible
		for(int i = pion.getColonne()+2 ; i < 8; i++)
		{
			if(tableauPion[pion.getLigne()][i].getCouleur().equals(couleur))
			{
				versLaDroitePossible = true;
				break;
			}
		}
		
		//changement de tous ceux possible sur la droite
		if(versLaDroitePossible)
		{
			for(int i = pion.getColonne() + 1; i < 8; i++)
			{
				if(tableauPion[pion.getLigne()][i].getCouleur().equals(couleurOpposee))
				{
					tableauPion[pion.getLigne()][i].setCouleur(couleur);
				}
				else if(tableauPion[pion.getLigne()][i].getCouleur().equals(couleur))
				{
					break;
				}
				else
				{
					break;
				}
			}
		}
		
		//changement de tous ceux possible sur la gauche
		if(versLaGauchePossible)
		{
			for(int i = pion.getColonne() - 1; i >= 0; i--)
			{
				if(tableauPion[pion.getLigne()][i].getCouleur().equals(couleurOpposee))
				{
					tableauPion[pion.getLigne()][i].setCouleur(couleur);
				}
				else if(tableauPion[pion.getLigne()][i].getCouleur().equals(couleur))
				{
					break;
				}
				else
				{
					break;
				}
			}
		}
	}
	
	/**
	 * M�thode qui effectue les retournements des pions en colonne par rapport au pion et � la couleur pass�es en param�tres.
	 * @param couleur Couleur effectuant un retournement.
	 * @param pion Pion o� le joueur � jouer.
	 */
	private void modifierPionsEnColonne(Couleur couleur, Pion pion)
	{
		Couleur couleurOpposee = getCouleurOpposee(couleur);
		boolean versLeHautPossible = false;
		boolean versLeBasPossible = false;
		
		//Recherche d'un pion de meme couleur vers le haut pour savoir si le changement est possible
		for(int i = pion.getLigne()-2 ; i >= 0; i--)
		{
			if(tableauPion[i][pion.getColonne()].getCouleur().equals(couleur))
			{
				versLeHautPossible = true;
				break;
			}
		}
		
		//Recherche d'un pion de meme couleur vers le bas pour savoir si le changement est possible
		for(int i = pion.getLigne()+2 ; i < 8; i++)
		{
			if(tableauPion[i][pion.getColonne()].getCouleur().equals(couleur))
			{
				versLeBasPossible = true;
				break;
			}
		}
		
		//changement de tous ceux possible vers le haut
		if(versLeHautPossible)
		{
			for(int i = pion.getLigne()-1; i >= 0; i--)
			{
				if(tableauPion[i][pion.getColonne()].getCouleur().equals(couleurOpposee))
				{
					tableauPion[i][pion.getColonne()].setCouleur(couleur);
				}
				else if(tableauPion[i][pion.getColonne()].getCouleur().equals(couleur))
				{
					break;
				}
				else
				{
					break;
				}
			}
		}
		//changement de tous ceux possible vers le bas
		if(versLeBasPossible)
		{
			for(int i = pion.getLigne() + 1; i < 8; i++)
			{
				if(tableauPion[i][pion.getColonne()].getCouleur().equals(couleurOpposee))
				{
					tableauPion[i][pion.getColonne()].setCouleur(couleur);
				}
				else if(tableauPion[i][pion.getColonne()].getCouleur().equals(couleur))
				{
					break;
				}
				else
				{
					break;
				}
			}
		}
	}
	
	/**
	 * M�thode qui effectue les retournements des pions en diagonale par rapport au pion et � la couleur pass�es en param�tres.
	 * @param couleur Couleur effectuant un retournement.
	 * @param pion Pion o� le joueur � jouer.
	 */
	private void modifierPionsEnDiagonale(Couleur couleur, Pion pion)
	{
		Couleur couleurOpposee = getCouleurOpposee(couleur);
		boolean versLeHautGauchePossible = false;
		boolean versLeBasGauchePossible = false;
		boolean versLeHautDroitePossible = false;
		boolean versLeBasDroitePossible = false;
		
		//Recherche d'un pion de meme couleur vers le haut � gauche pour savoir si le changement est possible
		for(int i = pion.getLigne()-2, j = pion.getColonne() -2 ; i >= 0 && j >= 0; i--,j--)
		{
			if(tableauPion[i][j].getCouleur().equals(couleur))
			{
				versLeHautGauchePossible = true;
				break;
			}
		}
		
		//Recherche d'un pion de meme couleur vers le haut � droite pour savoir si le changement est possible
		for(int i = pion.getLigne()-2,j=pion.getColonne() + 2 ; i >= 0 && j < 8; i--,j++)
		{
			if(tableauPion[i][j].getCouleur().equals(couleur))
			{
				versLeHautDroitePossible = true;
				break;
			}
		}
		
		//Recherche d'un pion de meme couleur vers le bas � gauche pour savoir si le changement est possible
		for(int i = pion.getLigne()+2, j = pion.getColonne()-2 ; i < 8 && j >= 0; i++,j--)
		{
			if(tableauPion[i][j].getCouleur().equals(couleur))
			{
				versLeBasGauchePossible = true;
				break;
			}
		}
		
		//Recherche d'un pion de meme couleur vers le bas � droite pour savoir si le changement est possible
		for(int i = pion.getLigne()+2, j=pion.getColonne()+2 ; i < 8 && j < 8; i++,j++)
		{
			if(tableauPion[i][j].getCouleur().equals(couleur))
			{
				versLeBasDroitePossible = true;
				break;
			}
		}
		
		//changement de tous ceux possible vers le haut � gauche si possible
		if(versLeHautGauchePossible)
		{
			for(int i = pion.getLigne()-1,j= pion.getColonne()-1; i >= 0 && j>=0; i--, j--)
			{
				if(tableauPion[i][j].getCouleur().equals(couleurOpposee))
				{
					tableauPion[i][j].setCouleur(couleur);
				}
				else if(tableauPion[i][j].getCouleur().equals(couleur))
				{
					break;
				}
				else
				{
					break;
				}
			}
		}
		//changement de tous ceux possible vers le haut � droite si possible
		if(versLeHautDroitePossible)
		{
			for(int i = pion.getLigne() - 1, j = pion.getColonne() + 1; i >= 0 && j < 8; i--,j++)
			{
				if(tableauPion[i][j].getCouleur().equals(couleurOpposee))
				{
					tableauPion[i][j].setCouleur(couleur);
				}
				else if(tableauPion[i][j].getCouleur().equals(couleur))
				{
					break;
				}
				else
				{
					break;
				}
			}
		}
		
		//changement de tous ceux possible vers bas � gauche si possible
		if(versLeBasGauchePossible)
		{
			for(int i = pion.getLigne() + 1, j = pion.getColonne() - 1; i < 8 && j >= 0; i++,j--)
			{
				if(tableauPion[i][j].getCouleur().equals(couleurOpposee))
				{
					tableauPion[i][j].setCouleur(couleur);
				}
				else if(tableauPion[i][j].getCouleur().equals(couleur))
				{
					break;
				}
				else
				{
					break;
				}
			}
		}
		
		//changement de tous ceux possible vers bas � droite si possible
		if(versLeBasDroitePossible)
		{
			for(int i = pion.getLigne() + 1, j = pion.getColonne() + 1; i < 8 && j < 8; i++,j++)
			{
				if(tableauPion[i][j].getCouleur().equals(couleurOpposee))
				{
					tableauPion[i][j].setCouleur(couleur);
				}
				else if(tableauPion[i][j].getCouleur().equals(couleur))
				{
					break;
				}
				else
				{
					break;
				}
			}
		}
	}
	
	/**
	 * M�thode qui retourne le nombre de retournements possibles pour la position du pion et la couleur pass�es en param�tres.
	 * @param couleur Couleur dont vous vous souhaiter v�rifier le nombre de retournements.
	 * @param pion Pion (position) dont vous souhaiter v�rifier le nombre de retournements possible.
	 * @return le nombre de retournements possibles pour la position et la couleur du pion pass�es en param�tres.
	 */
	public int nombreDeRetournements(Couleur couleur,Pion pion)
	{
		return nombreDeRetournementsEnLigne(couleur,pion) + nombreDeRetournementsEnColonne(couleur,pion) + nombreDeRetournementsEnDiagonale(couleur, pion);
	}
	
	/**
	 * M�thode qui retourne le nombre de retournements possibles en colonne pour la position du pion et la couleur pass�es en param�tres.
	 * @param couleur Couleur dont vous vous souhaiter v�rifier le nombre de retournements.
	 * @param pion Pion (position) dont vous souhaiter v�rifier le nombre de retournements possible.
	 * @return le nombre de retournements possibles pour la position et la couleur du pion pass�es en param�tres.
	 */
	private int nombreDeRetournementsEnColonne(Couleur couleur, Pion pion)
	{
		Couleur couleurOpposee = getCouleurOpposee(couleur);
		int nbRetVersLeHaut = 0;
		int nbRetVersLeBas = 0;
		int tmp = 0;
		
		//vers le haut
		for(int i = pion.getLigne()-1 ; i >= 0; i--)
		{
			if(tableauPion[i][pion.getColonne()].getCouleur().equals(couleur))
			{
				nbRetVersLeHaut += tmp;
				break;
			}
			else if(tableauPion[i][pion.getColonne()].getCouleur().equals(couleurOpposee))
			{
				tmp++;
			}
			else
			{
				break;
			}
		}
		
		tmp = 0;
		
		//vers le bas
		for(int i = pion.getLigne()+1 ; i < 8; i++)
		{
			if(tableauPion[i][pion.getColonne()].getCouleur().equals(couleur))
			{
				nbRetVersLeBas += tmp;
				break;
			}
			else if(tableauPion[i][pion.getColonne()].getCouleur().equals(couleurOpposee))
			{
				tmp++;
			}
			else
			{
				break;
			}
		}
		
		return nbRetVersLeBas + nbRetVersLeHaut;
	}
	
	/**
	 * M�thode qui retourne le nombre de retournements possibles en ligne pour la position du pion et la couleur pass�es en param�tres.
	 * @param couleur Couleur dont vous vous souhaiter v�rifier le nombre de retournements.
	 * @param pion Pion (position) dont vous souhaiter v�rifier le nombre de retournements possible.
	 * @return le nombre de retournements possibles pour la position et la couleur du pion pass�es en param�tres.
	 */
	private int nombreDeRetournementsEnLigne(Couleur couleur, Pion pion)
	{
		Couleur couleurOpposee = getCouleurOpposee(couleur);
		int nbRetVersLaDroite = 0, nbRetVersLaGauche = 0,tmp = 0;
		
		//vers la gauche
		for(int i = pion.getColonne()-1 ; i >= 0; i--)
		{
			if(tableauPion[pion.getLigne()][i].getCouleur().equals(couleur))
			{
				nbRetVersLaGauche += tmp;
				break;
			}
			else if(tableauPion[pion.getLigne()][i].getCouleur().equals(couleurOpposee))
			{
				tmp++;
			}
			else
			{
				break;
			}
		}
		
		tmp = 0;
		
		//vers la droite
		for(int i = pion.getColonne()+1 ; i < 8; i++)
		{
			if(tableauPion[pion.getLigne()][i].getCouleur().equals(couleur))
			{
				nbRetVersLaDroite += tmp;
				break;
			}
			else if(tableauPion[pion.getLigne()][i].getCouleur().equals(couleurOpposee))
			{
				tmp++;
			}
			else
			{
				break;
			}
		}
		return nbRetVersLaDroite + nbRetVersLaGauche;
	}
	
	/**
	 * M�thode qui retourne le nombre de retournements possibles en diagonale pour la position du pion et la couleur pass�es en param�tres.
	 * @param couleur Couleur dont vous vous souhaiter v�rifier le nombre de retournements.
	 * @param pion Pion (position) dont vous souhaiter v�rifier le nombre de retournements possible.
	 * @return le nombre de retournements possibles pour la position et la couleur du pion pass�es en param�tres.
	 */
	private int nombreDeRetournementsEnDiagonale(Couleur couleur, Pion pion)
	{
		Couleur couleurOpposee = getCouleurOpposee(couleur);
		int nbRetVersLeHautGauche = 0;
		int nbRetVersLeBasGauche = 0;
		int nbRetVersLeHautDroite = 0;
		int nbRetVersLeBasDroite = 0;
		int tmp = 0;
		
		//vers le haut � gauche
		for(int i = pion.getLigne()-1, j = pion.getColonne() -1 ; i >= 0 && j >= 0; i--,j--)
		{
			if(tableauPion[i][j].getCouleur().equals(couleur))
			{
				nbRetVersLeHautGauche += tmp;
				break;
			}
			else if(tableauPion[i][j].getCouleur().equals(couleurOpposee))
			{
				tmp++;
			}
			else
			{
				break;
			}
		}
		tmp = 0;
		//vers le haut � droite
		for(int i = pion.getLigne()-1,j=pion.getColonne() + 1 ; i >= 0 && j < 8; i--,j++)
		{
			if(tableauPion[i][j].getCouleur().equals(couleur))
			{
				nbRetVersLeHautDroite += tmp;
				break;
			}
			else if(tableauPion[i][j].getCouleur().equals(couleurOpposee))
			{
				tmp++;
			}
			else
			{
				break;
			}
		}
		tmp = 0;
		//vers le bas � gauche
		for(int i = pion.getLigne()+1, j = pion.getColonne()-1 ; i < 8 && j >= 0; i++,j--)
		{
			if(tableauPion[i][j].getCouleur().equals(couleur))
			{
				nbRetVersLeBasGauche += tmp;
				break;
			}
			else if(tableauPion[i][j].getCouleur().equals(couleurOpposee))
			{
				tmp++;
			}
			else
			{
				break;
			}
		}
		tmp = 0;
		//vers le bas � droite
		for(int i = pion.getLigne()+1, j=pion.getColonne()+1 ; i < 8 && j < 8; i++,j++)
		{
			if(tableauPion[i][j].getCouleur().equals(couleur))
			{
				nbRetVersLeBasDroite += tmp;
				break;
			}
			else if(tableauPion[i][j].getCouleur().equals(couleurOpposee))
			{
				tmp++;
			}
			else
			{
				break;
			}
		}
		
		return nbRetVersLeBasDroite + nbRetVersLeBasGauche + nbRetVersLeHautDroite + nbRetVersLeHautGauche;
	}

	/**
	 * Retourne la couleur oppos�e � celle pass�e en param�tre. Si NOIR retourne BLANC et vice versa.
	 * @param couleur Couleur soit NOIR soit BLANC dont vous souhaiter retourner l'oppos�e
	 * @return La couleur oppos�e � celle pass�e en param�tre.
	 */
	private Couleur getCouleurOpposee(Couleur couleur)
	{
		if(couleur.equals(Couleur.BLANC))
		{
			return Couleur.NOIR;
		}
		else
		{
			return Couleur.BLANC;
		}
	}
	
	/**
	 * M�thode qui retourne le tableau de pion sous forme d'un tableau de Couleur.
	 * @return le tableau de pion sous forme d'un tableau de Couleur.
	 */
	private Couleur[][] getPlateauSousTabCouleur()
	{
		Couleur[][] tabCouleur = new Couleur[8][8];
		for(int i = 0; i < 8 ; i++)
		{
			for(int j = 0 ; j < 8 ; j++)
			{
				tabCouleur[i][j] = tableauPion[i][j].getCouleur(); 
			}
		}
		return tabCouleur;
	}
	
	/**
	 * M�thode qui ajoute la couleur sous forme d'icon pass�e en param�tre dans la liste de sauvegarde pour retenir le fait qu'elle ait pass�e son tour.
	 * @param ic Icon de la couleur qui passe son tour.
	 */
	public void ajouterUnTourPasseALaSauvegarde(Icon ic)
	{
		listePlateauxSauvegarde.add(ic);
	}
	
	/**
	 * M�thode qui retourne la liste des plateau successif pour la sauvegarde.
	 * @return la liste des plateau successif pour la sauvegarde.
	 */
	public ArrayList<Object> getListeSauvegarde()
	{
		return this.listePlateauxSauvegarde;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Pion p = (Pion)e.getSource();
		if(p.getEstJouable())
		{
			p.setCoup(numeroCoup++);
			p.setJouerPar(CouleurJouant);
			p.setCouleur(CouleurJouant);
			p.setEstJouable(false);
			for(int i = 0; i < 8; i++)
			{
				for(int j = 0; j<8 ; j++)
				{
					if(tableauPion[i][j].getEstJouable())
					{
						tableauPion[i][j].changementEstJouable(false);
					}
				}
			}
			modifierPions(CouleurJouant, p);
			if(CouleurJouant.equals(Couleur.NOIR))
			{
				this.CouleurJouant = Couleur.BLANC;
			}
			else
			{
				this.CouleurJouant = Couleur.NOIR;
			}
			activerCasesJouables(CouleurJouant);
			listePlateauxSauvegarde.add(getPlateauSousTabCouleur());
			this.jeu.update();
		}
		
	}

}
