package controleurs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

import principal.Ressources;
import principal.RessourcesAudio;
import vues.FenetrePrincipal;
import vues.MenuOptions;
import vues.PanelSousOption;
import vues.SORaccourcis;
import vues.SOResolution;
import vues.SOSon;

/**
 * Controleur du menu des options.
 * @author Sacha
 *
 */
public class MenuOptionsController {
	
	/**
	 * Tableau des différentes valeurs possibles pour le son.
	 */
	public static int[] valeursSon = new int[]{-70,-60,-50,-40,-30,-20,-15,-10,-5,0,5};
	
	/**
	 * Vue du menu des options.
	 */
	private MenuOptions menuOptions;
	
	/**
	 * Indice, par rapport au tableaux des sous option, indiquant la sous option sur laquelle on se trouve.
	 */
	private int currentIndex;
	
	/**
	 * Tableau des sous options dans l'ordre.
	 */
	private PanelSousOption[] optionsOrder;
	
	/**
	 * Booléen indiquant si l'utilisateur selectionne une option ou est en train de choisir une valeur.
	 * Vrai si il choisie, faux si il selectionne.
	 */
	private boolean isChoosing;
	
	/**
	 * Constante de {@link KeyEvent} de la derniere sous option raccourci selectionnée.
	 */
	private int lastKeyValue;
	
	/**
	 * Valeur de la résolution juste après la selection.
	 */
	private int lastResolutionValue;
	
	/**
	 * valeur de la resolutionactuellement choisi.
	 */
	private int valeurResolution;
	
	/**
	 * Indice de la valeur du son choisi, par rapport au tableau des valeurs de son.
	 */
	private int indValeurSon;
	
	/**
	 * {@link KeyListener} pour les sous options de type raccourcis.
	 */
	private MouseAdapter listenerRaccourcis;
	
	/**
	 * {@link KeyListener} pour les sous options de type resolution.
	 */
	private MouseAdapter listenerResolution;
	
	/**
	 * {@link KeyListener} pour les fleches des sous options de type resolution.
	 */
	private MouseAdapter flecheResolution;
	
	/**
	 * {@link KeyListener} pour les fleches des sous options de type son.
	 */
	private MouseAdapter flecheSon;
	
	/**
	 * dernière sous options cliquée.
	 */
	private PanelSousOption lastPanelClicked;
	
	/**
	 * Constructeur de {@link MenuOptionsController}. Instancie un nouveau controleur pour un menu d'options
	 * avec toutes les variables initialisée ainsi que les listeners.
	 * @param menuOptions Vue du menu d'options que l'on controle.
	 */
	public MenuOptionsController(MenuOptions menuOptions) {
		this.menuOptions = menuOptions;
		this.currentIndex = 0;
		this.isChoosing = false;
		this.lastKeyValue = -1;
		this.lastResolutionValue = -1;
		this.valeurResolution = Ressources.multResolution;
		this.indValeurSon = getIndVolume();
		this.optionsOrder = menuOptions.getPanelsOptions();
		this.optionsOrder[currentIndex].setCurseurPresent(true);
		this.lastPanelClicked = optionsOrder[0];
		this.initListeners();
	}
	
	/**
	 * Méthode qui permet d'initialiser tous les listeners.
	 */
	private void initListeners()
	{
		this.menuOptions.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				
				if(!isChoosing)
				{
					if(keyCode == KeyEvent.VK_UP)
					{
						RessourcesAudio.lancerSonDeplacement();
						optionsOrder[currentIndex].setCurseurPresent(false);
						currentIndex = (currentIndex - 1 >= 0)?(currentIndex - 1):(optionsOrder.length - 1);
						optionsOrder[currentIndex].setCurseurPresent(true);
						lastPanelClicked = optionsOrder[currentIndex];
					}
					else if(keyCode == KeyEvent.VK_DOWN)
					{
						RessourcesAudio.lancerSonDeplacement();
						optionsOrder[currentIndex].setCurseurPresent(false);
						currentIndex = (currentIndex + 1) % optionsOrder.length;
						optionsOrder[currentIndex].setCurseurPresent(true);
						lastPanelClicked = optionsOrder[currentIndex];
					}
					else if(keyCode == KeyEvent.VK_ESCAPE)
					{
						RessourcesAudio.lancerSonRetour();
						optionsOrder[currentIndex].setCurseurPresent(false);
						Ressources.ecrireConfiguration();
						FenetrePrincipal.afficherMenuPrincipal();
					}
					else
					{
						PanelSousOption panSelect = optionsOrder[currentIndex];
						if(panSelect instanceof SORaccourcis)
							interactionSelection(keyCode, (SORaccourcis)panSelect);
						else if(panSelect instanceof SOResolution)
							interactionSelection(keyCode, (SOResolution)panSelect);
						else if(panSelect instanceof SOSon)
							interactionSelection(keyCode, (SOSon)panSelect);
					}
				}
				else
				{
					PanelSousOption panSelect = optionsOrder[currentIndex];	
					if(panSelect instanceof SORaccourcis)
						interactionChoix(keyCode, (SORaccourcis)panSelect);
					else if(panSelect instanceof SOResolution)
						interactionChoix(keyCode,(SOResolution)panSelect);
				}
			}
		});
		
		listenerRaccourcis  = new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				SORaccourcis soRacc = (SORaccourcis)e.getSource();
				setCurseurPanelClicker(soRacc);
				if(isChoosing && !lastPanelClicked.equals(soRacc))
				{
					annulerChoix(lastPanelClicked);
					interactionSelection(KeyEvent.VK_ENTER, soRacc);
					lastPanelClicked = soRacc;
				}
				else if(isChoosing && lastPanelClicked.equals(soRacc))
				{
					interactionChoix(KeyEvent.VK_ESCAPE, soRacc);
					lastPanelClicked = soRacc;
				}
				else
				{
					interactionSelection(KeyEvent.VK_ENTER, soRacc);
					lastPanelClicked = soRacc;
				}
			}
		};
		
		listenerResolution = new MouseAdapter() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				SOResolution soRes = (SOResolution)e.getSource();
				setCurseurPanelClicker(soRes);
				if(isChoosing && !lastPanelClicked.equals(soRes))
				{
					annulerChoix(lastPanelClicked);
					interactionSelection(KeyEvent.VK_ENTER, soRes);
					lastPanelClicked = soRes;
				}
				else if(isChoosing && lastPanelClicked.equals(soRes))
				{
					interactionChoix(KeyEvent.VK_ENTER, soRes);
					lastPanelClicked = soRes;
				}
				else
				{
					interactionSelection(KeyEvent.VK_ENTER, soRes);
					lastPanelClicked = soRes;
				}
				
			}
		};
		
		flecheResolution = new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				JLabel fleche = (JLabel)e.getSource();
				int f = fleche.getName().equals("fd") ? KeyEvent.VK_RIGHT : KeyEvent.VK_LEFT;
				SOResolution soRes = (SOResolution)fleche.getParent();
				setCurseurPanelClicker(soRes);
				if(isChoosing && !lastPanelClicked.equals(soRes))
				{
					annulerChoix(lastPanelClicked);
					interactionSelection(f, soRes);
					lastPanelClicked = soRes;
				}
				else if(isChoosing && lastPanelClicked.equals(soRes))
				{
					interactionChoix(f, soRes);
				}
				else
				{
					interactionSelection(f, soRes);
					lastPanelClicked = soRes;
				}
			}
		};
		
		flecheSon = new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				JLabel fleche = (JLabel)e.getSource();
				int f = fleche.getName().equals("fd") ? KeyEvent.VK_RIGHT : KeyEvent.VK_LEFT;
				SOSon soSon = (SOSon)fleche.getParent();
				setCurseurPanelClicker(soSon);
				if(isChoosing && !lastPanelClicked.equals(soSon))
				{
					annulerChoix(lastPanelClicked);
					interactionSelection(f, soSon);
					lastPanelClicked = soSon;
				}
				else
				{
					interactionSelection(f, soSon);
					lastPanelClicked = soSon;
				}
			}
		};
		
		for(PanelSousOption so : optionsOrder)
		{
			if(so instanceof SORaccourcis)
				((SORaccourcis)so).ajouterListeners(this.listenerRaccourcis);
			else if(so instanceof SOResolution)
				((SOResolution)so).ajouterListeners(this.listenerResolution, flecheResolution);
			else if(so instanceof SOSon)
				((SOSon)so).ajouterListeners(flecheSon);
		}
	}
	
	/**
	 * Méthode qui permet de gérer l'interaction avec un panel de sous option quand l'utilisateur est en selection.
	 * @param keyCode Constante de {@link KeyEvent} représentant la touche appuyé.
	 * @param soRes panel de sous option de type resolution.
	 */
	private void interactionSelection(int keyCode, SOResolution soRes)
	{
		if(keyCode == KeyEvent.VK_LEFT)
		{
			RessourcesAudio.lancerSonSelection();
			lastResolutionValue = valeurResolution;
			valeurResolution = valeurResolution - 1 == 0 ? 4 : valeurResolution - 1;
			soRes.setValue(valeurResolution);
			soRes.clignoterValue();
			isChoosing = true;
		}
		else if(keyCode == KeyEvent.VK_RIGHT)
		{
			RessourcesAudio.lancerSonSelection();
			lastResolutionValue = valeurResolution;
			valeurResolution = valeurResolution + 1 == 5 ? 1 : valeurResolution + 1;
			soRes.setValue(valeurResolution);
			soRes.clignoterValue();
			isChoosing = true;
		}
		else if(keyCode == KeyEvent.VK_ENTER)
		{
			RessourcesAudio.lancerSonSelection();
			lastResolutionValue = valeurResolution;
			soRes.clignoterValue();
			isChoosing = true;		
		}
	}
	
	
	/**
	 * Méthode qui permet de gérer l'interaction avec un panel de sous option quand l'utilisateur est en selection.
	 * @param keyCode Constante de {@link KeyEvent} représentant la touche appuyé.
	 * @param soRacc panel de sous option de type raccourcis.
	 */
	private void interactionSelection(int keyCode, SORaccourcis soRacc)
	{
		if(keyCode == KeyEvent.VK_ENTER)
		{
			RessourcesAudio.lancerSonSelection();
			lastKeyValue = soRacc.getKeyCode();
			soRacc.clignoterValue();
			isChoosing = true;			
		}
	}
	
	/**
	 * Méthode qui permet de gérer l'interaction avec un panel de sous option quand l'utilisateur est en selection.
	 * @param keyCode Constante de {@link KeyEvent} représentant la touche appuyé.
	 * @param soSon panel de sous option de type son.
	 */
	private void interactionSelection(int keyCode, SOSon soSon)
	{
		if(keyCode == KeyEvent.VK_LEFT)
		{
			RessourcesAudio.lancerSonSelection();
			indValeurSon = indValeurSon - 1 == -1 ? 0 : indValeurSon - 1;
			soSon.setValue(indValeurSon);
			soSon.changerConfig(valeursSon[indValeurSon]);
		}
		else if(keyCode == KeyEvent.VK_RIGHT)
		{
			RessourcesAudio.lancerSonSelection();
			indValeurSon = indValeurSon + 1 == 11 ? 10 : indValeurSon + 1;
			soSon.setValue(indValeurSon);
			soSon.changerConfig(valeursSon[indValeurSon]);
		}
	}
	
	/**
	 * Méthode qui permet de gérer l'intéraction avec un panel de sous option quand l'utilisateur est en choix.
	 * @param keyCode Constante de {@link KeyEvent} représentant la touche appuyé.
	 * @param soRacc panel de sous option de type raccourcis.
	 */
	private void interactionChoix(int keyCode, SORaccourcis soRacc)
	{
		if(keyCode == KeyEvent.VK_ESCAPE)
		{
			RessourcesAudio.lancerSonRetour();
			soRacc.stopperClignotement();
			isChoosing = false;
			soRacc.setNormalColor();
			soRacc.setValue(lastKeyValue);
		}
		else
		{
			soRacc.setValue(keyCode);
			RessourcesAudio.lancerSonSelection();
			if(!Ressources.toucheDejaPrise(keyCode,lastKeyValue))
			{
				soRacc.setNormalColor();
				soRacc.stopperClignotement();
				soRacc.changerConfig(keyCode);
				isChoosing = false;
			}
			else
			{
				soRacc.setErrorColor();	
			}
		}
	}
	
	/**
	 * Méthode qui permet de gérer l'intéraction avec un panel de sous option quand l'utilisateur est en choix.
	 * @param keyCode Constante de {@link KeyEvent} représentant la touche appuyé.
	 * @param soRes panel de sous option de type resolution.
	 */
	private void interactionChoix(int keyCode, SOResolution soRes)
	{
		if(keyCode == KeyEvent.VK_ESCAPE)
		{
			RessourcesAudio.lancerSonRetour();
			valeurResolution = lastResolutionValue;
			soRes.setValue(lastResolutionValue);
			soRes.stopperClignotement();			
			isChoosing = false;
		}
		else if(keyCode == KeyEvent.VK_ENTER)
		{
			RessourcesAudio.lancerSonSelection();
			soRes.stopperClignotement();
			soRes.changerConfig(valeurResolution);
			isChoosing = false;
		}
		else if(keyCode == KeyEvent.VK_LEFT)
		{
			RessourcesAudio.lancerSonDeplacement();
			valeurResolution = valeurResolution - 1 == 0 ? 4 : valeurResolution - 1;
			soRes.setValue(valeurResolution);
		}
		else if(keyCode == KeyEvent.VK_RIGHT)
		{
			RessourcesAudio.lancerSonDeplacement();
			valeurResolution = valeurResolution + 1 == 5 ? 1 : valeurResolution + 1;
			soRes.setValue(valeurResolution);
		}
	}
	
	/**
	 * Méthode qui permet d'annuler le choix sur un panel de sous option.
	 * @param so un panel de sous option.
	 */
	private void annulerChoix(PanelSousOption so)
	{
		if(so instanceof SORaccourcis)
			interactionChoix(KeyEvent.VK_ESCAPE, (SORaccourcis)so);
		else if(so instanceof SOResolution)
			interactionChoix(KeyEvent.VK_ESCAPE, (SOResolution)so);
	}
	
	/**
	 * Méthode qui permet d'afficher le curseur sur la panel de sous option.
	 * @param pso panel de sous option cliqué.
	 */
	private void setCurseurPanelClicker(PanelSousOption pso)
	{
		lastPanelClicked.setCurseurPresent(false);
		currentIndex = getOptionIndex(pso);
		optionsOrder[currentIndex].setCurseurPresent(true);
	}
	
	/**
	 * Méthode qui retourne l'indexe de ce panel dans le tableau des sous options.
	 * @param option panel de sous option dont on souhaite connaitre l'indice.
	 * @return L'indice, dans le tableau des sous option, du panel.
	 */
	private int getOptionIndex(PanelSousOption option)
	{
		for(int i = 0; i < optionsOrder.length; i++)
		{
			if(optionsOrder[i].equals(option))
			{
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Méthode qui permet de récupérer l'indice de la valeur du son, dans le tableau des valeurs du son, par rapport à la valeur actuelle du volume.
	 * @return l'indice de la valeur du son, dans le tableau des valeurs du son, par rapport à la valeur actuelle du volume.
	 */
	public static int getIndVolume()
	{
		for(int i = 0; i < valeursSon.length; i++)
		{
			if(valeursSon[i] == Ressources.volumeMusique)
				return i;
		}
		return -1;
	}

}
