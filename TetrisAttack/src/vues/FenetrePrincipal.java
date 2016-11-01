package vues;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JFrame;

import controleurs.MenuAideController;
import controleurs.MenuOptionsController;
import controleurs.MenuPrincipalController;
import controleurs.PartieVersusController;
import controleurs.TitleScreenController;
import donnees.Humain;
import donnees.Joueur;
import donnees.PartieVersus;
import principal.Ressources;

/**
 * Classe représentant la fenêtre principal de l'application.
 * @author Sacha
 *
 */
@SuppressWarnings("serial")
public class FenetrePrincipal extends JFrame{

	/**
	 * Fenetre principal de l'application.
	 */
	private static FenetrePrincipal mainFrame = null;
	
	/**
	 * Représente les dimensions de l'écran.
	 */
	private static final Dimension SCREENSIZE = Toolkit.getDefaultToolkit().getScreenSize();
	
	/**
	 * Constructeur de FenetrePrincipal. Instancie une nouvelle fenêtre non redimensionnable.
	 */
	public FenetrePrincipal()
	{
		super("Tetris Attack");
		initComposants();
	}
	
	/**
	 * Méthode qui permet d'initialiser les composants de la fenêtre.
	 */
	private void initComposants()
	{
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * Méthode qui permet de créer la fenêtre principal.
	 */
	public static void createMainFrame()
	{
		FenetrePrincipal.mainFrame = new FenetrePrincipal();
	}
	
	/**
	 * Méthode qui permet d'afficher la partie versus dans la fenêtre principal.
	 */
	public static void afficherPartieVersus()
	{
		Joueur joueur1, joueur2;
		joueur1 = new Humain(1,Ressources.keyboardSettingJ1);
		joueur2 = new Humain(2,Ressources.keyboardSettingJ2);
		PartieVersus partie = new PartieVersus(joueur1, joueur2);
		PartieVersusView partieView = new PartieVersusView(partie);
		PartieVersusController pvController = new PartieVersusController(partie, partieView);
		//mainFrame.setResizable(false);
		//mainFrame.setMinimumSize(null);		
		mainFrame.setContentPane(partieView);	
		mainFrame.pack();
		partieView.requestFocusInWindow();
		FenetrePrincipal.setAtScreenCenter();
		pvController.debuterPartie();
	}
	
	/**
	 * Méthode qui permet d'afficher le menu principal dans la fenêtre principal.
	 */
	public static void afficherMenuPrincipal()
	{
		MenuPrincipal menuPrincipal = new MenuPrincipal();
		new MenuPrincipalController(menuPrincipal);
		mainFrame.setContentPane(menuPrincipal);
		//mainFrame.setResizable(true);
		//mainFrame.setMinimumSize(null);
		mainFrame.pack();
		//int minWidth = menuPrincipal.getMinimumSize().width + mainFrame.getInsets().left + mainFrame.getInsets().right;
		//int minHeight = menuPrincipal.getMinimumSize().height + mainFrame.getInsets().top + mainFrame.getInsets().bottom;
		//mainFrame.setMinimumSize(new Dimension(minWidth, minHeight));
		menuPrincipal.requestFocusInWindow();
	}
	
	/**
	 * Méthode qui permet d'afficher l'écran titre dans la fenetre principal.
	 */
	public static void afficherTitleScreen()
	{
		TitleScreenView titleScreen = new TitleScreenView();
		new TitleScreenController(titleScreen);
		mainFrame.setContentPane(titleScreen);
		//mainFrame.setMinimumSize(null);
		//mainFrame.setResizable(true);
		mainFrame.pack();
		//int minWidth = titleScreen.getMinimumSize().width + mainFrame.getInsets().left + mainFrame.getInsets().right;
		//int minHeight = titleScreen.getMinimumSize().height + mainFrame.getInsets().top + mainFrame.getInsets().bottom;
		//mainFrame.setMinimumSize(new Dimension(minWidth, minHeight));
		titleScreen.requestFocusInWindow();
	}
	
	/**
	 * Méthode qui permet d'afficher le menu options dans la fenetre principal.
	 */
	public static void afficherMenuOptions()
	{
		MenuOptions menuOptions = new MenuOptions();
		new MenuOptionsController(menuOptions);
		mainFrame.setContentPane(menuOptions);
		//mainFrame.setResizable(true);
		//mainFrame.setMinimumSize(null);
		mainFrame.pack();
		//int minWidth = menuOptions.getMinimumSize().width + mainFrame.getInsets().left + mainFrame.getInsets().right;
		//int minHeight = menuOptions.getMinimumSize().height + mainFrame.getInsets().top + mainFrame.getInsets().bottom;
		//mainFrame.setMinimumSize(new Dimension(minWidth, minHeight));
		menuOptions.requestFocusInWindow();
	}
	
	/**
	 * Méthode qui permet d'afficher le menu d'aide dans la fenetre principal.
	 */
	public static void afficherMenuAide()
	{
		MenuAide aideView = new MenuAide(Ressources.AIDES);
		new MenuAideController(aideView);
		mainFrame.setContentPane(aideView);
		//mainFrame.setResizable(true);
		//mainFrame.setMinimumSize(null);
		mainFrame.pack();
		//int minWidth = aideView.getMinimumSize().width + mainFrame.getInsets().left + mainFrame.getInsets().right;
		//int minHeight = aideView.getMinimumSize().height + mainFrame.getInsets().top + mainFrame.getInsets().bottom;
		//mainFrame.setMinimumSize(new Dimension(minWidth, minHeight));
		aideView.requestFocusInWindow();
	}
	
	/**
	 * Méthode qui permet d'afficher la fenetre principal au milieu de l'écran.
	 */
	public static void setAtScreenCenter()
	{
		if(!mainFrame.isVisible())
			mainFrame.setVisible(true);
		mainFrame.setLocation(new Point((SCREENSIZE.width / 2) - (mainFrame.getWidth() / 2), (SCREENSIZE.height / 2) - (mainFrame.getHeight() / 2)));
	}

}
