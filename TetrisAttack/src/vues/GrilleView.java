package vues;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JPanel;

import donnees.Bloc;
import donnees.Grille;
import principal.Ressources;

/**
 * Classe représentant la vue de la grille.
 * @author Sacha
 *
 */
@SuppressWarnings("serial")
public class GrilleView extends JPanel{
	
	/**
	 * Couleur intérieur du curseur.
	 */
	private final static Color CURSORCOLOR = Color.WHITE;
	
	/**
	 * Pas en pixel de la montée de la grille.
	 */
	private final static int STEPCLIMB = 1;
	
	/**
	 * Hauteur du bas de la grille.
	 */
	public static int BASGRILLEHEIGHT = Ressources.BASGRILLE.getIconHeight() * Ressources.multResolution;
	
	/**
	 * Hauteur du fond de la grille.
	 */
	public static int BGHEIGHT = Ressources.BGJ1.getIconHeight() * Ressources.multResolution;
	
	/**
	 * Largeur du fond de la grille.
	 */
	public static int BGWIDTH = Ressources.BGJ2.getIconWidth() * Ressources.multResolution;
	
	/**
	 * Dimension de la grille.
	 */
	public static Dimension PREFDIM = new Dimension(BGWIDTH, BGHEIGHT + BASGRILLEHEIGHT);
	
	/**
	 * Pas en pixel de la montée du mur de transition.
	 */
	private static int STEPTRANSI = 1 * Ressources.multResolution;
	
	/**
	 * modele de grille que cette vue représente.
	 */
	private Grille grille;
	
	/**
	 * Position du curseur dans la grille. (position de la partie gauche)
	 */
	private Point cursorPosition;
	
	/**
	 * Matrice de vues de blocs que possède cette grille.
	 */
	private ArrayList<ArrayList<BlocView>> grilleBlocView;
	
	/**
	 * Vue du curseur.
	 */
	private CursorBorder cursorBorder;
	
	/**
	 * Image de fond de la grille.
	 */
	private Image backgroundImage;
	
	/**
	 * Panel représentant la matrice de vues de blocs.
	 */
	private JPanel panelBlocs;
	
	/**
	 * Mur de transition de cette grille.
	 */
	private MurTransition murTransition;
	
	/**
	 * Constructeur de {@link GrilleView}. 
	 * @param grille Modele que cette vue représente.
	 * @param bgImage Image de fond de cette grille.
	 * @param noJoueur Numéro de joueur possédant cette grille.
	 */
	public GrilleView(Grille grille, Image bgImage, int noJoueur)
	{
		super();
		this.grille = grille;
		this.grilleBlocView = new ArrayList<>();
		for(int i = 0; i < Grille.MAXLINES ; i++)
			grilleBlocView.add(new ArrayList<>());
		this.backgroundImage = bgImage;
		this.initComposants(noJoueur);
	}
	
	/**
	 * Méthode qui permet d'initialiser les composants graphiques de ce composant.
	 * @param noJoueur Numéro du joueur qui possède ce panel.
	 */
	private void initComposants(int noJoueur)
	{
		this.setLayout(new BorderLayout());
		
		panelBlocs = new JPanel(new GridBagLayout());
		panelBlocs.setOpaque(false);
		GridBagConstraints gbc = new GridBagConstraints();
		int x,y;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		
		ArrayList<ArrayList<Bloc>> grilleTmp = grille.getLignesHorizontales();
		for(y = Grille.MAXLINES - 1, gbc.gridy = 0; gbc.gridy < Grille.MAXLINES; y--, gbc.gridy++)
		{
			for(x = 0, gbc.gridx = 0; x < Grille.MAXBLOCPERLINE; x++, gbc.gridx++)
			{
				BlocView newBlocView = new BlocView(grilleTmp.get(y).get(x));
				grilleTmp.get(y).get(x).addObserver(newBlocView);
				this.grilleBlocView.get(y).add(newBlocView);
				panelBlocs.add(newBlocView, gbc);
			}
		}
		panelBlocs.setBounds(0, 0 , BlocView.BLOCWIDTH * Grille.MAXBLOCPERLINE, BlocView.BLOCHEIGHT * (Grille.MAXLINES));
		panelBlocs.setOpaque(false);
		
		this.murTransition = new MurTransition(noJoueur);
		this.murTransition.setBounds(0, MurTransition.MURHEIGHT - BASGRILLEHEIGHT, MurTransition.MURWIDTH, MurTransition.MURHEIGHT);
		
		JPanel panelNull = new JPanel(null);
		panelNull.add(murTransition);
		panelNull.add(panelBlocs);	
		panelNull.setOpaque(false);
		this.add(panelNull,BorderLayout.CENTER);
		
		resetCursor();
		
		this.setFocusable(true);
		this.setOpaque(false);
		this.setPreferredSize(PREFDIM);
	}
	
	/**
	 * Méthode qui retourne le panel des blocs.
	 * @return le panel des blocs.
	 */
	public JPanel getPanelBlocs() {
		return panelBlocs;
	}
	
	/**
	 * Méthode qui retourne le mur de transition.
	 * @return le mur de transition.
	 */
	public MurTransition getMurTransition() {
		return murTransition;
	}
	
	/**
	 * Méthode qui peint le curseur à la nouvelle position.
	 * @param cursorPosition Nouvelle position du curseur.
	 */
	public void paintCursor(Point cursorPosition)
	{
		grilleBlocView.get(this.cursorPosition.y).get(this.cursorPosition.x).paintCursorBorder(null);
		grilleBlocView.get(this.cursorPosition.y).get(this.cursorPosition.x + 1).paintCursorBorder(null);
		this.cursorPosition = cursorPosition;
		grilleBlocView.get(cursorPosition.y).get(cursorPosition.x).paintCursorBorder(cursorBorder);
		grilleBlocView.get(cursorPosition.y).get(cursorPosition.x + 1).paintCursorBorder(cursorBorder);
		if(this.getParent() != null && this.getParent().getParent() != null)
			this.getParent().getParent().repaint();
	}
	
	/**
	 * Méthoe qui désaffiche le curseur de la grille.
	 */
	public void removeCursor()
	{
		grilleBlocView.get(this.cursorPosition.y).get(this.cursorPosition.x).paintCursorBorder(null);
		grilleBlocView.get(this.cursorPosition.y).get(this.cursorPosition.x + 1).paintCursorBorder(null);
	}
	
	/**
	 * Méthode qui remet le curseur à sa position de base.
	 */
	public void resetCursor()
	{
		cursorBorder = new CursorBorder(CURSORCOLOR);
		cursorPosition = new Point(Grille.MAXBLOCPERLINE / 2 - 1, (Grille.MAXLINES-1)/4);
		paintCursor(cursorPosition);
	}
	
	/**
	 * Méthode qui retourne la position du curseur.
	 * @return la position du curseur.
	 */
	public Point getCursorPosition() {
		return cursorPosition;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroundImage, 0, 0, BGWIDTH, BGHEIGHT, null);
		g.setColor(new Color(0, 0, 0, 50));
		g.fillRect(0, 0, BGWIDTH, BGHEIGHT);
	}
	
	/**
	 * Méthode qui permet de montée d'un pas la grille pour la montée.
	 */
	public void moveUpPanelBlocs()
	{
		panelBlocs.setLocation(0, panelBlocs.getLocation().y - STEPCLIMB);
		panelBlocs.repaint();
	}
	
	/**
	 * Méthode qui repositionne la grille à sa position initiale.
	 */
	public void resetLocationPanelBlocs()
	{
		panelBlocs.setLocation(0, 0);
		panelBlocs.repaint();
	}
	
	/**
	 * Méthode qui permet de montée d'un pas le mur de transition.
	 */
	public void monteeMurTransition()
	{
		if(murTransition.getLocation().y - STEPTRANSI < 0)
		{
			murTransition.setLocation(new Point(0, 0));
		}
		else
		{
			murTransition.setLocation(new Point(0, murTransition.getLocation().y - STEPTRANSI));
		}
		this.repaint();
	}
	
	/**
	 * Méthode qui permet de descendre d'un pas le mur de transition.
	 */
	public void descendreMurTransition()
	{
		if(murTransition.getLocation().y + STEPTRANSI > BGHEIGHT)
		{
			murTransition.setLocation(0, BGHEIGHT);
		}
		else
		{
			murTransition.setLocation(new Point(0, murTransition.getLocation().y + STEPTRANSI));
		}
		this.repaint();
	}
}
