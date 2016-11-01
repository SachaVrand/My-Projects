package vues;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import donnees.PartieVersus;
import principal.Ressources;

/**
 * Classe représentant la vue de la partie.
 * @author Sacha
 *
 */
@SuppressWarnings("serial")
public class PartieVersusView extends JPanel{
	
	/**
	 * Largeur d'écart entre le fond et le premier panel de joueur. Sert à correctement positionner les panels par rapport au background.
	 */
	private static int OUTERBORDERWIDTH = 4 * Ressources.multResolution;
	
	/**
	 * Largeur d'écart entre le joueur, le score et l'autre joueur. Sert à correctement positionner les panels.
	 */
	private static int MIDDLEBORDERWIDTH = 4 * Ressources.multResolution;
	
	/**
	 * Hauteur de l'image de fond la partie.
	 */
	private static int BGHEIGHT = Ressources.BACKGROUNDINGAME.getIconHeight() * Ressources.multResolution;
	
	/**
	 * Largeur de l'image de fond de la partie.
	 */
	private static int BGWIDTH = Ressources.BACKGROUNDINGAME.getIconWidth() * Ressources.multResolution;
	
	/**
	 * Dimension de cette vue.
	 */
	private static Dimension PREFDIM = new Dimension(BGWIDTH, BGHEIGHT);
	
	/**
	 * Modèle de la partie à représenter.
	 */
	protected PartieVersus partie;
	
	/**
	 * Panel du timer de temps écoulée.
	 */
	protected TimeView timeView;
	
	/**
	 * Image de fond de cette vue.
	 */
	private Image bgImage;
	
	/**
	 * Vue du joueur 1.
	 */
	private JoueurView joueurViewJ1;
	
	/**
	 * Vue du joueur 2.
	 */
	private JoueurView joueurViewJ2;
	
	/**
	 * Vue des informations en jeu.
	 */
	private IGInfos inGameInfos;
	
	/**
	 * Constructeur de {@link PartieVersusView}. Instancie une nouvelle vue de partie.
	 * @param partie Le modele de la partie que la vue doit représenter.
	 */
	public PartieVersusView(PartieVersus partie) {
		super(new FlowLayout(FlowLayout.CENTER,OUTERBORDERWIDTH,0));
		this.partie = partie;
		this.timeView = new TimeView(partie.getTime());
		this.bgImage = Ressources.BACKGROUNDINGAME.getImage();
		this.initComposants();
	}
	
	/**
	 * Méthode qui permet d'initialiser les composants graphiques de ce composant. 
	 */
	private void initComposants()
	{
		PartieVersus tmp = (PartieVersus)partie;
		joueurViewJ1 = new JoueurView(tmp.getJoueur1(), Ressources.BGJ1.getImage(), 1);
		joueurViewJ2 = new JoueurView(tmp.getJoueur2(), Ressources.BGJ2.getImage(), 2);
		inGameInfos = new IGInfos(timeView);
		
		JPanel panelMiddle = new JPanel();
		panelMiddle.setLayout(new BoxLayout(panelMiddle, BoxLayout.Y_AXIS));
		int hauteurEspaceMilieu = (int) (JoueurView.PREFDIM.getHeight() - (ScoreView.PREFDIM.getHeight()*2) - IGInfos.PREFDIM.getHeight() - GrilleView.BASGRILLEHEIGHT);
		panelMiddle.add(Box.createVerticalStrut(hauteurEspaceMilieu));
		panelMiddle.add(joueurViewJ1.getScoreView());
		panelMiddle.add(joueurViewJ2.getScoreView());
		panelMiddle.add(inGameInfos);
		panelMiddle.add(Box.createVerticalStrut(GrilleView.BASGRILLEHEIGHT));
		panelMiddle.setOpaque(false);
		
		JPanel panelAll = new JPanel(new FlowLayout(FlowLayout.LEFT,MIDDLEBORDERWIDTH, 0));
		panelAll.add(joueurViewJ1);
		panelAll.add(panelMiddle);
		panelAll.add(joueurViewJ2);
		panelAll.setOpaque(false);
		
		this.add(panelAll);
		this.setOpaque(false);
		this.setPreferredSize(PREFDIM);
	}
	
	/**
	 * Méthode qui retourne la vue du joueur 1.
	 * @return la vue du joueur 1.
	 */
	public JoueurView getJoueurViewJ1() {
		return joueurViewJ1;
	}
	
	/**
	 * Méthode qui retourne la vue du joueur 2.
	 * @return vue du joueur 2.
	 */
	public JoueurView getJoueurViewJ2() {
		return joueurViewJ2;
	}
	
	/**
	 * Méthode qui retourne la vue des informations en jeu.
	 * @return vue des informations en jeu.
	 */
	public IGInfos getInGameInfos() {
		return inGameInfos;
	}
	
	/**
	 * Méthode qui retourne la vue du timer.
	 * @return le panel représentant le timer.
	 */
	public TimeView getTimeView() {
		return timeView;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(bgImage, 0, 0, BGWIDTH, BGHEIGHT, this);
	}

}
