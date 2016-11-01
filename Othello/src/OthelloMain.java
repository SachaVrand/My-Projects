import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

/**
 * Main du jeu Othello.
 * Regroupe diff�rentes fonctions statiques telles que le main, et les fonction permettant d'afficher les dif�rents panel de jeu et de menu.
 * @author Vrand
 *
 */
public class OthelloMain 
{
	/**
	 * Fen�tre principal de l'application.
	 */
	public static JFrame mainFrame = null;
	
	/**
	 * Menu principal de l'application.
	 */
	private static MenuPrincipal mainMenu = null;
	
	/**
	 * Panel de jeu qui sera afficher quand un type de jeu sera lancer.
	 */
	private static Jeu jeu = null;
	
	/**
	 * Dimension de l'�cran de l'utilisateur.
	 */
	public static Dimension dimensionEcran = null;
	
	/**
	 * Chemin permettant d'accder au images associ�es � la taille de l'�cran.
	 */
	public static String imagePath = null;
	
	/**
	 * Main de l'othello.
	 * @param args aucun arguments neccessaires.
	 */
	public static void main(String[] args) 
	{
		mainFrame = new JFrame("Othello");
		dimensionEcran = new Dimension(mainFrame.getToolkit().getScreenSize());
		choixRepertoireImages();
		mainMenu = new MenuPrincipal();
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setResizable(false);
		BorderLayout bl = new BorderLayout();
		mainFrame.getContentPane().setLayout(bl);
		mainFrame.getContentPane().add(mainMenu);
		mainFrame.pack();
		mainFrame.setLocation((int)((dimensionEcran.width / 2) - (mainFrame.getWidth()/2)), (int)((dimensionEcran.getHeight()/2)-(mainFrame.getHeight()/2)));
		mainFrame.setVisible(true);
	}
	
	/**
	 * M�thode qui cr�e un instance de MenuPrincipal et remplace le ContentPane de la fen�tre princpal par ce panel.
	 * Repositionne aussi la fen�tre principal au milieu de l'�cran.
	 */
	public static void afficherMenuPrincipal()
	{
		jeu = null;
		mainFrame.setEnabled(true);
		mainMenu = new MenuPrincipal();
		mainFrame.setContentPane(mainMenu);
		mainFrame.pack();
		mainFrame.setLocation((int)((dimensionEcran.width / 2) - (mainFrame.getWidth()/2)), (int)((dimensionEcran.getHeight()/2)-(mainFrame.getHeight()/2)));
	}

	/**
	 * M�thode qui cr�e un instance de JeuJcj et remplace le ContentPane de la fen�tre princpal par ce panel.
	 * Repositionne aussi la fen�tre principal au milieu de l'�cran.
	 */
	public static void afficherJeuJcj()
	{
		mainFrame.setEnabled(true);
		jeu = new JeuJcJ();
		mainFrame.setContentPane(jeu);
		mainFrame.pack();
		mainFrame.setLocation((int)((dimensionEcran.width / 2) - (mainFrame.getWidth()/2)), (int)((dimensionEcran.getHeight()/2)-(mainFrame.getHeight()/2)));
	}
	
	/**
	 * M�thode qui d�sactive la fen�tre principale. (setEnabled(false)).
	 */
	public static void desactiverMainFrame()
	{
		mainFrame.setEnabled(false);
	}
	
	/**
	 * M�thode qui cr�e un instance de JeuJcIAFacile et remplace le ContentPane de la fen�tre princpal par ce panel.
	 * Repositionne aussi la fen�tre principal au milieu de l'�cran.
	 */
	public static void afficherJeuJcIAFacile()
	{
		mainFrame.setEnabled(true);
		jeu = new JeuJcIAFacile(Couleur.BLANC);
		mainFrame.setContentPane(jeu);
		mainFrame.pack();
		mainFrame.setLocation((int)((dimensionEcran.width / 2) - (mainFrame.getWidth()/2)), (int)((dimensionEcran.getHeight()/2)-(mainFrame.getHeight()/2)));
	}
	
	/**
	 * M�thode qui cr�e un instance de JeuJcIAMoyen et remplace le ContentPane de la fen�tre princpal par ce panel.
	 * Repositionne aussi la fen�tre principal au milieu de l'�cran.
	 */
	public static void afficherJeuJcIAMoyen()
	{
		mainFrame.setEnabled(true);
		jeu = new JeuJcIAMoyen(Couleur.BLANC);
		mainFrame.setContentPane(jeu);
		mainFrame.pack();
		mainFrame.setLocation((int)((dimensionEcran.width / 2) - (mainFrame.getWidth()/2)), (int)((dimensionEcran.getHeight()/2)-(mainFrame.getHeight()/2)));
	}
	
	/**
	 * M�thode qui cr�e un instance de JcJcIADifficile et remplace le ContentPane de la fen�tre princpal par ce panel.
	 * Repositionne aussi la fen�tre principal au milieu de l'�cran.
	 */
	public static void afficherJeuJcIADifficile()
	{
		mainFrame.setEnabled(true);
		jeu = new JeuJcIADifficile(Couleur.BLANC);
		mainFrame.setContentPane(jeu);
		mainFrame.pack();
		mainFrame.setLocation((int)((dimensionEcran.width / 2) - (mainFrame.getWidth()/2)), (int)((dimensionEcran.getHeight()/2)-(mainFrame.getHeight()/2)));
	}
	
	/**
	 * M�thode qui cr�e un instance de MenuChoixDifficult�e et remplace le ContentPane de la fen�tre princpal par ce panel.
	 * Repositionne aussi la fen�tre principal au milieu de l'�cran.
	 */	
	public static void afficherMenuChoixDifficultee()
	{
		MenuChoixDifficulte menu = new MenuChoixDifficulte();
		mainFrame.setContentPane(menu);
		mainFrame.pack();
		mainFrame.setLocation((int)((dimensionEcran.width / 2) - (mainFrame.getWidth()/2)), (int)((dimensionEcran.getHeight()/2)-(mainFrame.getHeight()/2)));
	}
	
	/**
	 * M�thode qui cr�e un instance de MenuChoixSauvegardes et remplace le ContentPane de la fen�tre princpal par ce panel.
	 * Repositionne aussi la fen�tre principal au milieu de l'�cran.
	 */	
	public static void afficherPartiesPrecedentes()
	{
		MenuChoixSauvegarde menu = new MenuChoixSauvegarde();
		mainFrame.setContentPane(menu);
		mainFrame.pack();
		mainFrame.setLocation((int)((dimensionEcran.width / 2) - (mainFrame.getWidth()/2)), (int)((dimensionEcran.getHeight()/2)-(mainFrame.getHeight()/2)));
	}
	
	/**
	 * M�thode qui relance un instance du Jeu en cours en cr�ant un nouvelle instance de ce jeu 
	 * et en remplacant le ContentPane de la fen�tre principal par ce nouveau panel.
	 * Repositionne aussi la fen�tre principal au milieu de l'�cran.
	 */
	public static void rejouer()
	{
		if(jeu instanceof JeuJcJ)
		{
			OthelloMain.afficherJeuJcj();
		}
		else if(jeu instanceof JeuJcIAFacile)
		{
			OthelloMain.afficherJeuJcIAFacile();
		}
		else if(jeu instanceof JeuJcIADifficile)
		{
			OthelloMain.afficherJeuJcIADifficile();
		}
	}
	
	/**
	 * M�thode qui d�termine quelle taille d'image choisir, et change la variable de chemin des images, pour mieux adapater le jeu � l'�cran de l'utilisateur.
	 */
	private static void choixRepertoireImages()
	{
		if(dimensionEcran.height > 768 && dimensionEcran.width > 1366)
		{
			imagePath = "Images/Grandes/";
		}
		else
		{
			imagePath = "Images/Petites/";
		}
	}
	
	/**
	 * M�thode qui cr�e un instance de AffichageSauvegarde et remplace le ContentPane de la fen�tre princpal par ce panel.
	 * Repositionne aussi la fen�tre principal au milieu de l'�cran.
	 * @param save Sauvegarde repr�sentant la sauvegarde � rejouer.
	 */	
	public static void afficherPartieSauvegarder(Sauvegarde save)
	{
		AffichageSauvegarde jeuReplay = new AffichageSauvegarde(save);
		mainFrame.setContentPane(jeuReplay);
		mainFrame.pack();
		mainFrame.setLocation((int)((dimensionEcran.width / 2) - (mainFrame.getWidth()/2)), (int)((dimensionEcran.getHeight()/2)-(mainFrame.getHeight()/2)));
	}

}
