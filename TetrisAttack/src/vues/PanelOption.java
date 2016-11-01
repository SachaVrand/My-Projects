package vues;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import principal.Ressources;

/**
 * Classe représentant un groupe de sous options.
 * @author Sacha
 *
 */
@SuppressWarnings("serial")
public class PanelOption extends JPanel{
	
	/**
	 * Toutes les sous options contenu dans cette vue.
	 */
	private PanelSousOption[] panelsSousOption;
	
	/**
	 * Constructeur de {@link PanelOption}.
	 * @param panelsSousOption Toutes les sous options contenu dans ce panel.
	 * @param title titre de panel.
	 */
	public PanelOption(PanelSousOption[] panelsSousOption, String title) {
		super();
		this.panelsSousOption = panelsSousOption;
		initComposants(title);
	}
	
	/**
	 * Méthode qui permet d'initialiser les composants graphiques de ce composant.
	 * @param title Titre du panel d'option. 
	 */
	private void initComposants(String title)
	{
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		for(JPanel panel : panelsSousOption)
		{
			this.add(panel);
		}
		Border border = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		Font font = new Font(Ressources.FONTNAMEMENU, Font.BOLD, 25);
		this.setBorder(BorderFactory.createTitledBorder(border, title, TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION, font, Color.BLACK));
		this.setBackground(Ressources.BLUEINNER2);
	}

}
