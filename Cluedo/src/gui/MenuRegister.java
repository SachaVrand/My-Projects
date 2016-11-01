package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import principal.Humain;
import principal.Joueur;
import principal.Ordi;

/**
 * Classe repr�sentant le panel du menu pour se connecter � une partie en tant que client. Comporte plusieurs composants permettant la connexion � la partie.
 * @author Sacha
 *
 */
public class MenuRegister extends JPanel{

	/**
	 * ID permettant de sauvegarder l'objet. N'est pas utilis�.
	 */
	private static final long serialVersionUID = -5761830545778961543L;
	
	/**
	 * Bouton permettant de se connecter � la partie.
	 */
	private JButton btnRegister;
	
	/**
	 * Bouton permettant de revenir au menu principal.
	 */
	private JButton btnRetour;
	
	/**
	 * label de l'adresse de l'h�te.
	 */
	private JLabel lblAdrrHote;
	
	/**
	 * Label du nom du joueur.
	 */
	private JLabel lblNomJoueur;
	
	/**
	 * Label tu type du joueur.
	 */
	private JLabel lblTypeJoueur;
	
	/**
	 * Label du titre du menu.
	 */
	private JLabel lblRegister;
	
	/**
	 * Label d'erreur, affich� en cas d'erreur lors de la tentative de connexion � l'h�te.
	 */
	private JLabel lblError;
	
	/**
	 * Liste permettant de selectionner le type du joueur.
	 */
	private JComboBox<String> cbxTypeJoueur;
	
	/**
	 * Liste permettant de selectionner le niveau de l'IA si le type du joueur est un Ordi.
	 */
	private JComboBox<Integer> cbxNivIA;
	
	/**
	 * Zone de texte permettant de r�cup�rer le nom de l'adresse de l'h�te.
	 */
	private JTextField tfAddrHote;
	
	/**
	 * Zone de texte permettant de r�cup�rer le nom du joueur.
	 */
	private JTextField tfNomJoueur;
	
	/**
	 * Constructeur de la classe MenuRegister. Instancie un nouveau panel, charge ses composants et listeners.
	 */
	public MenuRegister()
	{
		super();
		this.setLayout(new GridBagLayout());
		this.load();
		this.loadListeners();
	}
	
	/**
	 * M�thode permettant de charger les diff�rents composants graphique.
	 */
	private void load()
	{
		btnRegister = new JButton("Connexion");
		btnRetour = new JButton("Retour");
		lblNomJoueur = new JLabel("Nom du joueur : ");
		lblAdrrHote = new JLabel("Adresse de l'h�te : ");
		lblTypeJoueur = new JLabel("Type du joueur : ");
		lblRegister = new JLabel("Register");
		lblRegister.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 35));
		lblError = new JLabel();
		lblError.setVisible(false);
		tfNomJoueur = new JTextField();
		tfNomJoueur.setPreferredSize(new Dimension(120, 25));
		tfAddrHote = new JTextField();
		tfAddrHote.setPreferredSize(new Dimension(120, 25));
		cbxTypeJoueur = new JComboBox<String>(new String[]{"Humain","Ordinateur"});
		cbxNivIA = new JComboBox<Integer>(new Integer[]{0,1,2});
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.insets = new Insets(25, 25, 25, 25);
		this.add(lblRegister,gbc);
		
		gbc.gridwidth = 1;
		gbc.gridy++;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.insets = new Insets(0, 25, 10, 5);
		this.add(lblNomJoueur, gbc);
		
		gbc.gridx = 1;
		gbc.insets = new Insets(0, 5, 10, 25);
		this.add(tfNomJoueur, gbc);
		
		gbc.gridy++;
		gbc.gridx = 0;
		gbc.insets = new Insets(10, 25, 10, 5);
		this.add(lblAdrrHote, gbc);
		
		gbc.gridx = 1;
		gbc.insets = new Insets(10, 5, 10, 25);
		this.add(tfAddrHote, gbc);
		
		gbc.gridy++;
		gbc.gridx = 0;
		gbc.insets = new Insets(10, 25, 10, 5);
		this.add(lblTypeJoueur, gbc);
		
		gbc.gridx = 1;
		gbc.insets = new Insets(10, 5, 10, 25);
		JPanel panelType = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		panelType.add(cbxTypeJoueur);
		panelType.add(cbxNivIA);
		this.add(panelType, gbc);
		cbxNivIA.setEnabled(false);
		
		gbc.gridy++;
		gbc.gridx = 0;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(10, 5, 10, 5);
		gbc.gridwidth = 2;
		JPanel panelButtons = new JPanel(new FlowLayout());
		panelButtons.add(btnRegister);
		panelButtons.add(btnRetour);
		this.add(panelButtons,gbc);
		gbc.gridy++;
		gbc.insets = new Insets(10, 5, 10, 5);
		this.add(lblError, gbc);
		
	}
	
	/**
	 * M�thode permettant de charger les diff�rents listener.
	 */
	private void loadListeners()
	{
		btnRetour.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				GraphicalUserInterface.afficherGUIMenuPrincipal();
				
			}
		});
		
		btnRegister.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				lblError.setText("");
				lblError.setVisible(false);
				
				String nom = tfNomJoueur.getText();
				String typeJoueur = (String)cbxTypeJoueur.getSelectedItem();
				String addr = tfAddrHote.getText();
				Joueur j;
				if(nom.equals("") || addr.equals(""))
				{	
					if(nom.equals(""))
					{
						tfNomJoueur.setBorder(BorderFactory.createLineBorder(Color.red));
					}
					else
					{
						tfNomJoueur.setBorder(new JTextField().getBorder());
					}
					if(addr.equals(""))
					{
						tfAddrHote.setBorder(BorderFactory.createLineBorder(Color.red));
					}
					else
					{
						tfAddrHote.setBorder(new JTextField().getBorder());
					}
				}
				else
				{
					tfNomJoueur.setBorder(new JTextField().getBorder());
					tfAddrHote.setBorder(new JTextField().getBorder());
					if(typeJoueur.equals("Ordinateur"))
					{
						j = new Ordi(nom, (int) cbxNivIA.getSelectedItem());
					}
					else
					{
						j = new Humain(nom);
					}
					setButtonsEnabled(false);
					GraphicalUserInterface.lancerPartieClient(j, addr);
				}				
			}
		});
		
		cbxTypeJoueur.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				String tmp = (String)cbxTypeJoueur.getSelectedItem();
				if(tmp.equals("Ordinateur"))
				{
					cbxNivIA.setEnabled(true);
				}
				else
				{
					cbxNivIA.setEnabled(false);
				}
			}
		});	
	}
	
	/**
	 * M�thode permettant d'afficher un erreur sur le label d'erreur.
	 * @param text Chaine repr�sentant l�rreur survenue.
	 */
	public void changeTextlblError(String text)
	{
		this.lblError.setText(text);
		this.lblError.setVisible(true);
	}
	
	/**
	 * M�thode permettant de changer l'�tat des boutons du panel.
	 * @param b Vrai s'il sont cliquable, faux sinon.
	 */
	public void setButtonsEnabled(boolean b)
	{
		btnRegister.setEnabled(b);
		btnRetour.setEnabled(b);
	}
}
