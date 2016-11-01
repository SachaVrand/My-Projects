package vues;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;

import javax.swing.JPanel;

/**
 * Classe représentant un panel nomal mais avec des bords arrondies. Utile pour s'adapter à la bordure arrondie des menu.
 * @author Sacha
 *
 */
@SuppressWarnings("serial")
public class RoundedPanel extends JPanel{
	
	/**
	 * Constructeur de {@link RoundedPanel}. Instancie un {@link RoundedPanel} comme un {@link JPanel} sans parmètre.
	 */
	public RoundedPanel() {
		super();
	}
	
	/**
	 * Constructeur de {@link RoundedPanel}. Instancie un {@link RoundedPanel} comme un {@link JPanel} avec en paramètre le layoutmanager.
	 * @param layout Le layout manager du panel.
	 */
	public RoundedPanel(LayoutManager layout)
	{
		super(layout);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(this.getBackground());
		g2d.fillRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 25, 45);
	}

}
