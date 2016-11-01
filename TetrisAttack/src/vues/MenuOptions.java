package vues;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import controleurs.MenuOptionsController;
import principal.Ressources;
import principal.RessourcesAudio;

/**
 * Classe représentant le menu des options.
 * @author Sacha
 *
 */
@SuppressWarnings("serial")
public class MenuOptions extends JPanel{
	
	/**
	 * largeur de cette vue
	 */
	private static final int PREFWIDTH = (int)(Ressources.BGMENUGIF.getIconWidth()*1.5);
	
	/**
	 * hauteur de cette vue.
	 */
	private static final int PREFHEIGHT = (int)(Ressources.BGMENUGIF.getIconHeight()*1.5);
	
	/**
	 * dimension de cette vue.
	 */
	private static final Dimension PREFDIM = new Dimension(PREFWIDTH,PREFHEIGHT);
	
	/**
	 * Image de fond de cette vue.
	 */
	private static final Image BACKGROUND = Ressources.BGMENUGIF.getImage();
	
	/**
	 * Panel permettant de regrouper toutes les options de cette vue.
	 */
	private JPanel panelCentral;
	
	/**
	 * Panel contenant le panel centrale permettant d'arrondir les bords pour correctement afficher la bordure.
	 */
	private JPanel panelArrondie;
	
	/**
	 * Panel d'option des raccourcis du joueur 1.
	 */
	private PanelOption panelRaccourcisJ1;
	
	/**
	 * Panel d'option des raccourcis du joueur 2.
	 */
	private PanelOption panelRaccourcisJ2;
	
	/**
	 * Panel d'option du son.
	 */
	private PanelOption panelSon;
	
	/**
	 * Panel d'option de la résolution.
	 */
	private PanelOption panelResolution;
	
	/**
	 * tableau de toutes les options présentent dans cette vue.
	 */
	private PanelSousOption[] panelsOptions;
	
	/**
	 * Constructeur de {@link MenuOptions}.
	 */
	public MenuOptions() {
		super();
		initComposants();
	}
	
	/**
	 * Méthode qui permet d'initialiser les composants graphiques de ce composant. 
	 */
	private void initComposants()
	{	
		PanelSousOption soToucheHautJ1 = new SORaccourcis("Haut",
				(int i) -> Ressources.keyboardSettingJ1.setKeyUp(i),Ressources.keyboardSettingJ1.getKeyUp());
		PanelSousOption soToucheBasJ1 = new SORaccourcis("Bas",
				(int i) -> Ressources.keyboardSettingJ1.setKeyDown(i),Ressources.keyboardSettingJ1.getKeyDown());
		PanelSousOption soToucheDroiteJ1 = new SORaccourcis("Droite",
				(int i) -> Ressources.keyboardSettingJ1.setKeyRight(i),Ressources.keyboardSettingJ1.getKeyRight());
		PanelSousOption soToucheGaucheJ1 = new SORaccourcis("Gauche",
				(int i) -> Ressources.keyboardSettingJ1.setKeyLeft(i),Ressources.keyboardSettingJ1.getKeyLeft());
		PanelSousOption soToucheMonteeJ1 = new SORaccourcis("Montée",
				(int i) -> Ressources.keyboardSettingJ1.setKeySecondaryAction(i), Ressources.keyboardSettingJ1.getKeySecondaryAction());
		PanelSousOption soToucheEchangeJ1 = new SORaccourcis("Echange",
				(int i) -> Ressources.keyboardSettingJ1.setKeyAction(i), Ressources.keyboardSettingJ1.getKeyAction());
		PanelSousOption[] soTouchesJ1 = new PanelSousOption[]{soToucheHautJ1,soToucheBasJ1,soToucheDroiteJ1,soToucheGaucheJ1,soToucheEchangeJ1,soToucheMonteeJ1};
		
		PanelSousOption soToucheHautJ2 = new SORaccourcis("Haut",
				(int i) -> Ressources.keyboardSettingJ1.setKeyUp(i), Ressources.keyboardSettingJ2.getKeyUp());
		PanelSousOption soToucheBasJ2 = new SORaccourcis("Bas",
				(int i) -> Ressources.keyboardSettingJ1.setKeyDown(i), Ressources.keyboardSettingJ2.getKeyDown());
		PanelSousOption soToucheDroiteJ2 = new SORaccourcis("Droite",
				(int i) -> Ressources.keyboardSettingJ1.setKeyRight(i), Ressources.keyboardSettingJ2.getKeyRight());
		PanelSousOption soToucheGaucheJ2 = new SORaccourcis("Gauche",
				(int i) -> Ressources.keyboardSettingJ1.setKeyLeft(i), Ressources.keyboardSettingJ2.getKeyLeft());
		PanelSousOption soToucheMonteeJ2 = new SORaccourcis("Montée",
				(int i) -> Ressources.keyboardSettingJ1.setKeySecondaryAction(i), Ressources.keyboardSettingJ2.getKeySecondaryAction());
		PanelSousOption soToucheEchangeJ2 = new SORaccourcis("Echange",
				(int i) -> Ressources.keyboardSettingJ1.setKeyAction(i), Ressources.keyboardSettingJ2.getKeyAction());
		PanelSousOption[] soTouchesJ2 = new PanelSousOption[]{soToucheHautJ2,soToucheBasJ2,soToucheDroiteJ2,soToucheGaucheJ2,soToucheEchangeJ2,soToucheMonteeJ2};
		
		PanelSousOption soMultiplicateur = new SOResolution("Résolution en jeu", (int i) -> Ressources.multResolution = i, Ressources.multResolution);
		PanelSousOption[] soResolution = new PanelSousOption[]{soMultiplicateur};
		
		PanelSousOption soSon = new SOSon("Volume", (int i) -> { Ressources.volumeMusique = i; RessourcesAudio.setVolumeMusiqueMenu();}, MenuOptionsController.getIndVolume());
		PanelSousOption[] soSons = new PanelSousOption[]{soSon};
		
		panelsOptions = new PanelSousOption[]{soToucheHautJ1,soToucheBasJ1,soToucheDroiteJ1,soToucheGaucheJ1,soToucheEchangeJ1,soToucheMonteeJ1,
				soToucheHautJ2,soToucheBasJ2,soToucheDroiteJ2,soToucheGaucheJ2,soToucheEchangeJ2,soToucheMonteeJ2,soMultiplicateur,soSon};
		
		panelRaccourcisJ1 = new PanelOption(soTouchesJ1, "Joueur 1");
		panelRaccourcisJ2 = new PanelOption(soTouchesJ2, "Joueur 2");	
		panelResolution = new PanelOption(soResolution, "Résolution");
		panelSon = new PanelOption(soSons, "Musique");
		
		this.panelCentral = new JPanel();
		this.panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
		this.panelCentral.add(panelRaccourcisJ1);
		this.panelCentral.add(panelRaccourcisJ2);
		this.panelCentral.add(panelResolution);
		this.panelCentral.add(panelSon);
		
		int hgap = 5;
		int vgap = hgap;
		this.panelArrondie = new RoundedPanel(new FlowLayout(FlowLayout.CENTER,hgap,vgap));
		this.panelArrondie.setBackground(Ressources.BLUEINNER2);
		this.panelArrondie.setBorder(Ressources.BLUEBORDER);
		this.panelArrondie.add(panelCentral);
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(60, 60, 60, 60);
		this.add(panelArrondie);
		this.setMinimumSize(PREFDIM);
		this.setPreferredSize(PREFDIM);
		this.setBackground(Color.black);
	}
	
	/**
	 * Méthode qui retourne toutes les sous options de cette vue.
	 * @return toutes les sous options de cette vue.
	 */
	public PanelSousOption[] getPanelsOptions() {
		return panelsOptions;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Dimension sourceDim = new Dimension(BACKGROUND.getWidth(null), BACKGROUND.getHeight(null));
		KeepRatioSize k = KeepRatioSize.getSizeKeepingRatio(sourceDim, this.getSize());
		g.drawImage(BACKGROUND, k.getX(), k.getY(), k.getWidth(), k.getHeight(), this);
	}

}
