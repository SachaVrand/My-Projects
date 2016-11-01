package vues;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import donnees.TimeModel;
import principal.NumberFont;
import principal.Ressources;

/**
 * Classe représentant le timer en jeu sous forme graphique. Hérite de la classe Jpanel.
 * @author Sacha
 *
 */
@SuppressWarnings("serial")
public class TimeView extends JPanel implements Observer{

	/**
	 * Pas horizontale entre les composants.
	 */
	private static int HGAP = 2 * Ressources.multResolution;
	
	/**
	 * Pas verticale entre les composants.
	 */
	private static int VGAP = 1 * Ressources.multResolution;
	
	/**
	 * Image du label time de cette vue.
	 */
	private final static Image LBLTIMEIMG = Ressources.LBLTIME.getImage();
	
	/**
	 * hauteur du label time.
	 */
	private static int TIMELBLHEIGHT = Ressources.LBLTIME.getIconHeight() * Ressources.multResolution;
	
	/**
	 * Largeur du label time.
	 */
	private static int TIMELBLWIDTH = Ressources.LBLTIME.getIconWidth() * Ressources.multResolution;
	
	/**
	 * Dimension du label time.
	 */
	private static Dimension PREFDIMLBLTIME = new Dimension(TIMELBLWIDTH, TIMELBLHEIGHT);
	
	/**
	 * Dimension du panel permetttant de centrer le label time.
	 */
	private static Dimension PREFDIMPANCENTRE = new Dimension(TIMELBLWIDTH + (HGAP * 2), TIMELBLHEIGHT + (VGAP * 2));
	
	/**
	 * Dimension du panel affichant le temps.
	 */
	private static Dimension PREFDIMTEMPS = new Dimension((NumberPanel.NUMBERWIDTH *4) + (HGAP * 5), NumberPanel.NUMBERHEIGHT + VGAP * 2);
	
	/**
	 * Dimension de cette vue.
	 */
	public static Dimension PREFDIM = new Dimension(PREFDIMTEMPS.width, PREFDIMPANCENTRE.height + PREFDIMTEMPS.height);
	
	/**
	 * Modèle de ce timer.
	 */
	private TimeModel timeModel;
	
	/**
	 * Tableau de panel représentant le temps.
	 */
	private NumberPanel[] tabTime;
	
	/**
	 * Panel du label time.
	 */
	private JPanel panelLabelTime;
	
	/**
	 * Constructeur de {@link TimeView}. Instancie un nouveau panel avec un {@link FlowLayout} et le label du temps.
	 * @param timeModel Instance de {@link TimeModel} que doit ecouter ce panel et avec laquelle il se met à jour.
	 */
	public TimeView(TimeModel timeModel) {
		super();
		this.timeModel = timeModel;
		this.initComposants();
	}
	
	/**
	 * Méthode qui initialise les composants graphique de ce panel.
	 */
	private void initComposants()
	{
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		this.panelLabelTime = new JPanel(){
			
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(LBLTIMEIMG, 0, 0, TIMELBLWIDTH, TIMELBLHEIGHT, null);
			}
		};
		panelLabelTime.setPreferredSize(PREFDIMLBLTIME);
		panelLabelTime.setOpaque(false);
		
		JPanel panelCentre = new JPanel(new FlowLayout(FlowLayout.CENTER,HGAP,VGAP));
		panelCentre.setPreferredSize(PREFDIMPANCENTRE);
		panelCentre.setOpaque(false);
		panelCentre.add(panelLabelTime);
		
		JPanel panelChiffres = new JPanel(new FlowLayout(FlowLayout.CENTER,HGAP,VGAP));
		panelChiffres.setOpaque(false);
		String timeStr = timeModel.toString();
		tabTime = new NumberPanel[4];
		for(int i = 0; i < tabTime.length; i++)
		{
			NumberPanel chiffre = new NumberPanel(getImageFont(timeStr.charAt(i)));
			tabTime[i] = chiffre;
			panelChiffres.add(chiffre);
		}
		panelChiffres.setPreferredSize(PREFDIMTEMPS);
		
		this.add(panelCentre);
		this.add(panelChiffres);
		this.setOpaque(false);
		this.setPreferredSize(PREFDIM);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		for(int i = 0; i < tabTime.length; i++)
		{
			tabTime[i].setImage(getImageFont(timeModel.toString().charAt(i)));
		}
		this.repaint();
	}
	
	/**
	 * Méthode qui permet de récupérer l'image associé au chiffre sous forme de caractère.
	 * @param c caractère du chiffre.
	 * @return image du caractère.
	 */
	public Image getImageFont(char c)
	{
		return NumberFont.getNumberImageRed(c);
	}

}
