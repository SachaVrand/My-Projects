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
 * Classe représentant le plateau de jeu de l'othello.
 * Consiste en un panel composé d'une grille de Pion.
 * Gère aussi les intéraction avec les pions. 
 * Ainsi que les retournements de pions et activations de cases jouables.
 * @author Vrand
 *
 */
public class Plateau extends JPanel implements ActionListener{
	
	private static final long serialVersionUID = -4916543843864419409L;
	
	/**
	 * Liste contenant les differentes grilles de pion après chaque tour des joueurs. Sert pour la sauvegarde de chaque états successif du plateau.
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
	 * Jeu sur lequel est disposé ce plateau.
	 */
	private Jeu jeu = null;
	
	/**
	 * Numéro du tour actuel.
	 */
	private int numeroCoup;

	/**
	 * Instancie un nouveau plateau avec comme LayoutManager un GridBagLayout. 
	 * Le plateau est instancier avec les 4 pions de départ blanc et noir,et le reste à vide.
	 * Le numero de coup est initialisé à 1, et les cases jouables sont activées.
	 * @param couleurCommencant Couleur commencant à jouer.
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
	 * Le plateau est instancier l'aide du tableau de pions sauvegardés passé en paramètre.
	 * Les pions sont des JLabel dans ce cas avec pour texte le coup et la couleur l'ayant joué.
	 * @param tab Tableau de pion à afficher sur le plateau.
	 */
	public Plateau(PionSauvegarde[][] tab)
	{
		super(new GridBagLayout());
		this.loadSauvegarde(tab);
	}
	
	/**
	 * Instancie un nouveau plateau avec comme LayoutManager un GridBagLayout. 
	 * Le plateau est à partir du tableau de couleur passée en paramètre.
	 * @param tab Tableau de couleur à deux dimensions représentant le plateau.
	 */
	public Plateau(Couleur[][] tab)
	{
		super(new GridBagLayout());
		this.setBackground(new Color(0,153,75));
		this.loadGrillePourLeReplay(tab);
	}
	
	/**
	 * Méthode qui crée et charge les différents composants graphiques du panel.
	 * C'est à dire un plateau de Pion avec les 4 pions de bases. Remplis le tableau de pions avec les objets créés.
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
	 * Méthode qui crée et charge les composants graphiques du panel.
	 * C'est à dire un plateau avec des JLabels(avec coup et couleur ayant joué écrit dessus) à l'aide du tableau de pion.
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
	 * Méthode qui met à jour le plateau avec le tableau de couleur passé en paramètre.
	 * @param tab tableau de couleurs à deux dimensions à afficher sur le plateau.
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
	 * Méthode qui retourne la couleur jouant ce tour.
	 * @return Object Couleur jouant ce tour.
	 */
	public Couleur getCouleurJouant() {
		return CouleurJouant;
	}
	
	/**
	 * Méthode qui retourne la tableau de pions représentant la grille sur le plateau.
	 * @return tableau de pions à deux dimensions.
	 */
	public Pion[][] getTableauPion()
	{
		return this.tableauPion;
	}

	/**
	 * Méthode qui set la couleur jouant ce tour.
	 * @param couleurJouant couleur jouant ce tour.
	 */
	public void setCouleurJouant(Couleur couleurJouant) {
		CouleurJouant = couleurJouant;
	}

	/**
	 * Méthode qui active les cases jouables.
	 * @param couleur Couleur dont les cases jouables doivent être activées.
	 */
	public void activerCasesJouables(Couleur couleur)
	{
		activerCasesJouablesEnLigne(couleur);
		activerCasesJouablesEnColonne(couleur);
		activerCasesJouablesEnDiagonales(couleur);
	}
	
	/**
	 * Méthode qui active les cases jouables dont un retournement en ligne est possible, par rapport à la couleur passé en paramètre.
	 * @param couleur Couleur dont les cases jouables doivent être activées.
	 */
	private void activerCasesJouablesEnLigne(Couleur couleur)
	{
		Couleur couleurOpposee = getCouleurOpposee(couleur);
		
		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				//vï¿½rification des cases avant un pion jouable en ligne
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
				//vï¿½rification des cases aprï¿½s un pion jouable en ligne
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
	 * Méthode qui active les cases jouables dont un retournement en colonne est possible, par rapport à la couleur passé en paramètre.
	 * @param couleur Couleur dont les cases jouables doivent être activées.
	 */
	private void activerCasesJouablesEnColonne(Couleur couleur)
	{
		Couleur couleurOpposee = getCouleurOpposee(couleur);
		
		for(int j = 0; j < 8; j++)
		{
			for(int i = 0; i < 8; i++)
			{
				//vï¿½rification des cases avant un pion jouable en colonne
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
				//vï¿½rification des cases jouables aprï¿½s un pion en colonne
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
	 * Méthode qui active les cases jouables dont un retournement en diagonale est possible, par rapport à la couleur passé en paramètre.
	 * @param couleur Couleur dont les cases jouables doivent être activées.
	 */
	private void activerCasesJouablesEnDiagonales(Couleur couleur)
	{
		Couleur couleurOpposee = getCouleurOpposee(couleur);
		
		for(int j = 0; j < 8; j++)
		{
			for(int i = 0; i < 8; i++)
			{
				//vï¿½rification des cases jouables en diagonale vers le bas d'un pion
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
				
				//vï¿½rification des cases jouables en diagonales vers le haut d'un pion
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
	 * Méthode qui effectue les retournements des pions par rapport au pion et à la couleur passées en paramètres.
	 * @param couleur Couleur effectuant un retournement.
	 * @param pion Pion où le joueur à jouer.
	 */
	public void modifierPions(Couleur couleur, Pion pion)
	{
		modifierPionsEnLigne(couleur, pion);
		modifierPionsEnColonne(couleur, pion);
		modifierPionsEnDiagonale(couleur, pion);
	}
	
	/**
	 * Méthode qui effectue les retournements des pions en ligne par rapport au pion et à la couleur passées en paramètres.
	 * @param couleur Couleur effectuant un retournement.
	 * @param pion Pion où le joueur à jouer.
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
	 * Méthode qui effectue les retournements des pions en colonne par rapport au pion et à la couleur passées en paramètres.
	 * @param couleur Couleur effectuant un retournement.
	 * @param pion Pion où le joueur à jouer.
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
	 * Méthode qui effectue les retournements des pions en diagonale par rapport au pion et à la couleur passées en paramètres.
	 * @param couleur Couleur effectuant un retournement.
	 * @param pion Pion où le joueur à jouer.
	 */
	private void modifierPionsEnDiagonale(Couleur couleur, Pion pion)
	{
		Couleur couleurOpposee = getCouleurOpposee(couleur);
		boolean versLeHautGauchePossible = false;
		boolean versLeBasGauchePossible = false;
		boolean versLeHautDroitePossible = false;
		boolean versLeBasDroitePossible = false;
		
		//Recherche d'un pion de meme couleur vers le haut ï¿½ gauche pour savoir si le changement est possible
		for(int i = pion.getLigne()-2, j = pion.getColonne() -2 ; i >= 0 && j >= 0; i--,j--)
		{
			if(tableauPion[i][j].getCouleur().equals(couleur))
			{
				versLeHautGauchePossible = true;
				break;
			}
		}
		
		//Recherche d'un pion de meme couleur vers le haut ï¿½ droite pour savoir si le changement est possible
		for(int i = pion.getLigne()-2,j=pion.getColonne() + 2 ; i >= 0 && j < 8; i--,j++)
		{
			if(tableauPion[i][j].getCouleur().equals(couleur))
			{
				versLeHautDroitePossible = true;
				break;
			}
		}
		
		//Recherche d'un pion de meme couleur vers le bas ï¿½ gauche pour savoir si le changement est possible
		for(int i = pion.getLigne()+2, j = pion.getColonne()-2 ; i < 8 && j >= 0; i++,j--)
		{
			if(tableauPion[i][j].getCouleur().equals(couleur))
			{
				versLeBasGauchePossible = true;
				break;
			}
		}
		
		//Recherche d'un pion de meme couleur vers le bas ï¿½ droite pour savoir si le changement est possible
		for(int i = pion.getLigne()+2, j=pion.getColonne()+2 ; i < 8 && j < 8; i++,j++)
		{
			if(tableauPion[i][j].getCouleur().equals(couleur))
			{
				versLeBasDroitePossible = true;
				break;
			}
		}
		
		//changement de tous ceux possible vers le haut ï¿½ gauche si possible
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
		//changement de tous ceux possible vers le haut ï¿½ droite si possible
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
		
		//changement de tous ceux possible vers bas ï¿½ gauche si possible
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
		
		//changement de tous ceux possible vers bas ï¿½ droite si possible
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
	 * Méthode qui retourne le nombre de retournements possibles pour la position du pion et la couleur passées en paramètres.
	 * @param couleur Couleur dont vous vous souhaiter vérifier le nombre de retournements.
	 * @param pion Pion (position) dont vous souhaiter vérifier le nombre de retournements possible.
	 * @return le nombre de retournements possibles pour la position et la couleur du pion passées en paramètres.
	 */
	public int nombreDeRetournements(Couleur couleur,Pion pion)
	{
		return nombreDeRetournementsEnLigne(couleur,pion) + nombreDeRetournementsEnColonne(couleur,pion) + nombreDeRetournementsEnDiagonale(couleur, pion);
	}
	
	/**
	 * Méthode qui retourne le nombre de retournements possibles en colonne pour la position du pion et la couleur passées en paramètres.
	 * @param couleur Couleur dont vous vous souhaiter vérifier le nombre de retournements.
	 * @param pion Pion (position) dont vous souhaiter vérifier le nombre de retournements possible.
	 * @return le nombre de retournements possibles pour la position et la couleur du pion passées en paramètres.
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
	 * Méthode qui retourne le nombre de retournements possibles en ligne pour la position du pion et la couleur passées en paramètres.
	 * @param couleur Couleur dont vous vous souhaiter vérifier le nombre de retournements.
	 * @param pion Pion (position) dont vous souhaiter vérifier le nombre de retournements possible.
	 * @return le nombre de retournements possibles pour la position et la couleur du pion passées en paramètres.
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
	 * Méthode qui retourne le nombre de retournements possibles en diagonale pour la position du pion et la couleur passées en paramètres.
	 * @param couleur Couleur dont vous vous souhaiter vérifier le nombre de retournements.
	 * @param pion Pion (position) dont vous souhaiter vérifier le nombre de retournements possible.
	 * @return le nombre de retournements possibles pour la position et la couleur du pion passées en paramètres.
	 */
	private int nombreDeRetournementsEnDiagonale(Couleur couleur, Pion pion)
	{
		Couleur couleurOpposee = getCouleurOpposee(couleur);
		int nbRetVersLeHautGauche = 0;
		int nbRetVersLeBasGauche = 0;
		int nbRetVersLeHautDroite = 0;
		int nbRetVersLeBasDroite = 0;
		int tmp = 0;
		
		//vers le haut ï¿½ gauche
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
		//vers le haut ï¿½ droite
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
		//vers le bas ï¿½ gauche
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
		//vers le bas ï¿½ droite
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
	 * Retourne la couleur opposée à celle passée en paramètre. Si NOIR retourne BLANC et vice versa.
	 * @param couleur Couleur soit NOIR soit BLANC dont vous souhaiter retourner l'opposée
	 * @return La couleur opposée à celle passée en paramètre.
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
	 * Méthode qui retourne le tableau de pion sous forme d'un tableau de Couleur.
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
	 * Méthode qui ajoute la couleur sous forme d'icon passée en paramètre dans la liste de sauvegarde pour retenir le fait qu'elle ait passée son tour.
	 * @param ic Icon de la couleur qui passe son tour.
	 */
	public void ajouterUnTourPasseALaSauvegarde(Icon ic)
	{
		listePlateauxSauvegarde.add(ic);
	}
	
	/**
	 * Méthode qui retourne la liste des plateau successif pour la sauvegarde.
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
