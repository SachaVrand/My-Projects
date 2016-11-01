import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

/**
 * Classe représentant le menu de choix de la sauvegarde à visionner.
 * Elle représente un panel constitué des différentes sauvegardes enregistrées au préalable (5max par page)
 * et pouvant être visionnées.
 * @author Vrand
 *
 */
public class MenuChoixSauvegarde extends JPanel implements ActionListener
{
	
	private static final long serialVersionUID = -1366539699063624493L;
	
	/**
	 * Liste des sauvegardes présentes dans le dossier Saves.
	 */
	private ArrayList<Sauvegarde> listeSauvegardes = null;
	
	/**
	 * Indice des sauvegarde à affichées. De cette indice jusqu'à cette indice plus 5. Initialiser à 0. Ne peut pas être négatif.
	 */
	private int indiceListeAffichee = 0;
	
	/**
	 * Panel affichant les 5 sauvegardes au maximum à partir de indiceListeAffichee. 
	 */
	private JPanel panelListe = null;
	
	/**
	 * Bouton permettant de passer à la page précédente des sauvegardes si possible.
	 */
	private JButton boutonPrecedent = null;
	
	/**
	 * Bouton permettant de passer à la page suivante des sauvegardes si possible.
	 */
	private JButton boutonSuivant = null;
	
	/**
	 * Bouton permettant revenir au menu principal.
	 */
	private JButton boutonRetour = null;
	
	/**
	 * Constructeur de la classe MenuChoixSauvegarde.
	 * Crée un panel avec un borderlayout, un bouton suivant et prècèdent pour défiler les pages, 
	 * et un sous-panel avec un gridbaglayout affichant les différentes informations sur les sauvegarde ainsi qu'un bouton regarder pour les visionner.
	 */
	public MenuChoixSauvegarde()
	{
		this.listeSauvegardes = new ArrayList<Sauvegarde>();
		recupererSauvegardes();
		this.load();
		this.ajoutListenerBouton();
	}

	/**
	 * Méthode qui gère la création des différents composants graphiques du menu.
	 */
	private void load()
	{
		this.setLayout(new BorderLayout());
		
		panelListe = new JPanel();
		remplirPanelListe(panelListe);
		
		JPanel panelBoutons = new JPanel(new BorderLayout());
		this.boutonPrecedent = new JButton("Précédent");
		this.boutonPrecedent.setPreferredSize(new Dimension(100,30));
		this.boutonSuivant = new JButton("Suivant");
		this.boutonSuivant.setPreferredSize(new Dimension(100,30));
		this.boutonRetour = new JButton("Retour");
		this.boutonRetour.setPreferredSize(new Dimension(100,30));
		JPanel panelRetour = new JPanel();
		panelRetour.add(boutonRetour);
		panelBoutons.add(boutonPrecedent,BorderLayout.WEST);
		panelBoutons.add(boutonSuivant,BorderLayout.EAST);
		panelBoutons.add(panelRetour,BorderLayout.CENTER);
		this.add(panelBoutons,BorderLayout.SOUTH);
		
		if(listeSauvegardes.size() <= 5)
		{
			boutonSuivant.setEnabled(false);
		}
		boutonPrecedent.setEnabled(false);
	}
	
	/**
	 * Méthode qui gère la création et l'ajout des listener aux boutons du panel
	 */
	private void ajoutListenerBouton()
	{
		boutonPrecedent.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				boutonSuivant.setEnabled(true);
				indiceListeAffichee -= 5;
				remplirPanelListe(panelListe);
				
				if(indiceListeAffichee - 5 <= 0)
				{
					boutonPrecedent.setEnabled(false);
				}
			}
		});
		
		boutonSuivant.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				boutonPrecedent.setEnabled(true);
				indiceListeAffichee += 5;
				remplirPanelListe(panelListe);
				
				if(indiceListeAffichee + 5 >= listeSauvegardes.size())
				{
					boutonSuivant.setEnabled(false);
				}
			}
		});
		
		boutonRetour.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				OthelloMain.afficherMenuPrincipal();
			}
		});
	}
	
	/**
	 * Méthode qui gère la création et le chargement du sous panel affichant les informations concernant les sauvegardes.
	 * Crée le sous panel avec les 5 sauvegardes de indiceListeAffichee à indiceListeAffichee + 5 si possible.
	 * @param panelListe Panel à remplacer par le nouveau panel crée dans la fonction
	 */
	private void remplirPanelListe(JPanel panelListe)
	{
		panelListe.removeAll();
		panelListe.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yy");
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		gbc.ipadx = 10;
		gbc.ipady = 1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(10, 25, 10, 25);
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		JLabel lblDescDate = new JLabel("Date");
		JLabel lblDescScore = new JLabel("Score");
		JLabel lblDescTypePartie = new JLabel("Type de partie");
		JLabel lblDescTemps = new JLabel("Temps");
		panelListe.add(lblDescDate,gbc);
		gbc.gridx++;
		panelListe.add(lblDescScore,gbc);
		gbc.gridx++;
		panelListe.add(lblDescTypePartie,gbc);
		gbc.gridx++;
		panelListe.add(lblDescTemps,gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		
		gbc.gridwidth = 5;
		panelListe.add(new JSeparator(SwingConstants.HORIZONTAL),gbc);
		gbc.gridwidth = 1;
		
		gbc.gridy++;
		
		for(int i = indiceListeAffichee, j = i + 5; i < listeSauvegardes.size() && i < j; i++)
		{
			JLabel lblDate = new JLabel(formater.format(listeSauvegardes.get(i).getDate()));
			JLabel lblScore = new JLabel(listeSauvegardes.get(i).getScore());
			JLabel lblTypePartie = new JLabel(listeSauvegardes.get(i).getTypePartie());
			JLabel lblTempsPartie = new JLabel(listeSauvegardes.get(i).getTempsDeJeuTotal());
			BoutonRegarder boutonRegarder = new BoutonRegarder("Regarder",listeSauvegardes.get(i));
			boutonRegarder.addActionListener(this);
			panelListe.add(lblDate,gbc);
			gbc.gridx++;
			panelListe.add(lblScore,gbc);
			gbc.gridx++;
			panelListe.add(lblTypePartie,gbc);
			gbc.gridx++;
			panelListe.add(lblTempsPartie,gbc);
			gbc.gridx++;
			panelListe.add(boutonRegarder,gbc);
			
			
			gbc.gridy++;
			gbc.gridx = 0;
			gbc.gridwidth = 5;
			panelListe.add(new JSeparator(SwingConstants.HORIZONTAL),gbc);
			gbc.gridwidth = 1;
			
			gbc.gridy++;
		}
		this.add(panelListe,BorderLayout.CENTER);
		OthelloMain.mainFrame.pack();
	}
	
	/**
	 * Méthode qui recupère les differents objets Sauvegarde sérialisé dans le repertoire Saves et les ajoutent dans la liste des sauvegardes.
	 */
	private void recupererSauvegardes()
	{   
		File repertoire = new File("Saves");
		File[] files = repertoire.listFiles();
		Sauvegarde save = null;
		
		try
		{
			for(int i = 0; i < files.length; i++)
			{
				ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(files[i])));
				save = (Sauvegarde)in.readObject();
				listeSauvegardes.add(save);
				in.close();
			}	
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		BoutonRegarder btn = (BoutonRegarder)e.getSource();
		OthelloMain.afficherPartieSauvegarder(btn.getSauvegarde());
		
	}

}
