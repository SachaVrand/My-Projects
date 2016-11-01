package vues;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import donnees.Joueur;
import principal.Ressources;

/**
 * Classe représentant le panel d'information au milieu en bas de la partie.
 * @author Sacha
 *
 */
@SuppressWarnings("serial")
public class IGInfos extends JPanel implements Observer{
	
	/**
	 * Hauteur du gif en fond.
	 */
	private static int BGTIMEGIFHEIGHT = Ressources.BGTIMEGIF.getIconHeight() * Ressources.multResolution;
	
	/**
	 * Largeur du gif en fond.
	 */
	private static int BGTIMEGIFWIDTH = Ressources.BGTIMEGIF.getIconWidth() * Ressources.multResolution;
	
	/**
	 * Dimension du gif en fond.
	 */
	public static Dimension PREFDIM = new Dimension(BGTIMEGIFWIDTH,BGTIMEGIFHEIGHT);
	
	/**
	 * pas horizontale entre le composants.
	 */
	private static int HGAP = 2 * Ressources.multResolution;
	
	/**
	 * Pas verticale entre les composants.
	 */
	private static int VGAP = 1 * Ressources.multResolution;
	
	/**
	 * Hauteur du panel de victoire.
	 */
	private static int PANELVICHEIGHT = (WinPointPanel.WPHEIGHT * 2 + VGAP) ;
	
	/**
	 * Largeur du panel de victoire.
	 */
	private static int PANELVICWIDTH = (WinPointPanel.WPWIDTH * 2 + HGAP) ;
	
	/**
	 * Dimension du panel de victoire.
	 */
	private static Dimension PREFDIMVICTOIRES = new Dimension(PANELVICWIDTH, PANELVICHEIGHT);
	
	/**
	 * Image de fond de ce panel.
	 */
	private Image background;
	
	/**
	 * Vue du temps.
	 */
	private TimeView timeView;
	
	/**
	 * Panle représentant les victoires de manches des joueurs.
	 */
	private JPanel panelVictoires;
	
	/**
	 * Label de la manche 1 du joueur 1
	 */
	private WinPointPanel victoiresJ1_1;
	
	/**
	 * Label de la manche 1 du joueur 2
	 */
	private WinPointPanel victoiresJ2_1;
	
	/**
	 * Label de la manche 2 du joueur 1
	 */
	private WinPointPanel victoiresJ1_2;
	
	/**
	 * Label de la manche 2 du joueur 2
	 */
	private WinPointPanel victoiresJ2_2;
	
	/**
	 * Constructeur de {@link IGInfos}.
	 * @param timeView Vue du temps.
	 */
	public IGInfos(TimeView timeView) {
		super();
		this.timeView = timeView;
		this.background = Ressources.BGTIMEGIF.getImage();
		initComposants();
	}
	
	/**
	 * Méthode qui permet d'initialiser les composants graphiques de ce composant. 
	 */
	private void initComposants()
	{
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		this.panelVictoires = new JPanel(new GridLayout(2, 2, HGAP, VGAP));
		this.victoiresJ1_1 = new WinPointPanel(1);
		this.victoiresJ2_1 = new WinPointPanel(2);
		this.victoiresJ1_2 = new WinPointPanel(1);
		this.victoiresJ2_2 = new WinPointPanel(2);
		this.panelVictoires.add(victoiresJ1_1);
		this.panelVictoires.add(victoiresJ2_1);
		this.panelVictoires.add(victoiresJ1_2);
		this.panelVictoires.add(victoiresJ2_2);
		panelVictoires.setOpaque(false);
		JPanel panelCentre = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
		panelCentre.add(panelVictoires);
		panelCentre.setOpaque(false);
		panelCentre.setPreferredSize(PREFDIMVICTOIRES);
		
		this.add(timeView);
		this.add(panelCentre);
		this.setOpaque(false);
		
		this.setPreferredSize(PREFDIM);
	}
	
	/**
	 * Méthode qui met à jour l'icones des victoires de manches.
	 * @param noJoueur Numéro du joueur auquel ajouté une victoire.
	 * @param nombre 1 si première manche gagnée, 2 si c'est la deuxième
	 */
	public void ajouterVictoire(int noJoueur, int nombre)
	{
		if(noJoueur == 1)
		{
			if(nombre == 1)
				victoiresJ1_1.setGagne();
			else if(nombre == 2)
				victoiresJ1_2.setGagne();
		}
		else if(noJoueur == 2)
		{
			if(nombre == 1)
				victoiresJ2_1.setGagne();
			else if(nombre == 2)
				victoiresJ2_2.setGagne();
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);
	}

	@Override
	public void update(Observable o, Object arg) {
		Joueur j = (Joueur)o;
		ajouterVictoire(j.getIdJoueur(),Integer.parseInt(((String)arg).split(" ")[1]));
	}
}
