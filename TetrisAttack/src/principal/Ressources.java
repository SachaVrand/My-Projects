package principal;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.Node;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import donnees.KeyboardSetting;
import vues.MenuButtonBorder;

/**
 * Classe regroupant une partie des ressources graphique et autres du jeu. Permet aussi l'écriture et la lecture dans le fichier de configuration.
 * @author Sacha
 *
 */
public class Ressources {
	
	public static KeyboardSetting keyboardSettingJ2 = new KeyboardSetting(KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT, KeyEvent.VK_NUMPAD1, KeyEvent.VK_NUMPAD2);
	public static KeyboardSetting keyboardSettingJ1 = new KeyboardSetting(KeyEvent.VK_Z, KeyEvent.VK_S, KeyEvent.VK_D, KeyEvent.VK_Q, KeyEvent.VK_V, KeyEvent.VK_B);
	
	public static Integer multResolution = 3;
	public static Integer volumeMusique = 0;
	
	//Les diiférentes couleurs composants les boutons du menu.
	public final static Color ROSEINNER = new Color(200, 80, 152);
	public final static Color ROSEOUTSIDE = new Color(128, 32, 80);
	public final static Color ROSEMIDDLE = new Color(248, 128, 248);
	public final static Color ROSEINNER2 = new Color(160,80,144);
	
	public final static Color BLUEINNER = new Color(40, 152, 240);
	public final static Color BLUEOUTSIDE = new Color(32, 72, 136);
	public final static Color BLUEMIDDLE = new Color(92, 208, 248);
	public final static Color BLUEINNER2 = new Color(72,120,224);
	
	public final static Color YELLOWINNER = new Color(160, 136, 0);
	public final static Color YELLOWOUTSIDE = new Color(120, 80, 0);
	public final static Color YELLOWMIDDLE = new Color(210, 179, 0);
	public final static Color YELLOWINNER2 = new Color(144,112,0);
	
	public final static Color GREENINNER = new Color(72,160,24);
	public final static Color GREENOUTSIDE = new Color(56,112,16);
	public final static Color GREENMIDDLE = new Color(83,185,28);
	public final static Color GREENINNER2 = new Color(64,136,24);
	
	public final static Color ORANGEINNER = new Color(190, 70, 0);
	public final static Color ORANGEOUTSIDE = new Color(190, 45, 0);
	public final static Color ORANGEMIDDLE = new Color(190, 122, 0);
	public final static Color ORANGEINNER2 = new Color(190,105,0);
	
	
	public final static Color BACKGROUNDBTNCOLOR = new Color(205, 205, 205);
	
	public final static MenuButtonBorder REDBORDER = new MenuButtonBorder(ROSEOUTSIDE, ROSEINNER, ROSEMIDDLE, ROSEINNER2);
	public final static MenuButtonBorder BLUEBORDER = new MenuButtonBorder(BLUEOUTSIDE, BLUEINNER, BLUEMIDDLE, BLUEINNER2);
	public final static MenuButtonBorder YELLOWBORDER = new MenuButtonBorder(YELLOWOUTSIDE, YELLOWINNER, YELLOWMIDDLE, YELLOWINNER2);
	public final static MenuButtonBorder GREENBORDER = new MenuButtonBorder(GREENOUTSIDE, GREENINNER, GREENMIDDLE, GREENINNER2);
	public final static MenuButtonBorder ORANGEBORDER = new MenuButtonBorder(ORANGEOUTSIDE, ORANGEINNER, ORANGEMIDDLE, ORANGEINNER2);
	
	public final static ImageIcon BGJ1 = new ImageIcon("Images/BGJ1.png");
	public final static ImageIcon BGJ2 = new ImageIcon("Images/BGJ2.png");
	public final static ImageIcon WINLOGO = new ImageIcon("Images/WinLogo.png");
	public final static ImageIcon LOSELOGO =  new ImageIcon("Images/LoseLogo.png");
	public final static ImageIcon DRAWLOGO =  new ImageIcon("Images/DrawLogo.png");
	
	public final static ImageIcon BGMENUGIF = new ImageIcon("Images/BGMENU.gif");
	public final static ImageIcon BGTIMEGIF = new ImageIcon("Images/backgroundTimer.gif");
	public final static ImageIcon BGSCOREJ1 = new ImageIcon("Images/scoreJ1.png");
	public final static ImageIcon BGSCOREJ2 = new ImageIcon("Images/scoreJ2.png");
	public final static ImageIcon LBLTIME = new ImageIcon("Images/labelTime.png");
	public final static ImageIcon BACKGROUNDINGAME = new ImageIcon("Images/jungle.gif");
	public final static ImageIcon WINNOPOINTJ1 = new ImageIcon("Images/winNoPointJ1.png");
	public final static ImageIcon WINNOPOINTJ2 = new ImageIcon("Images/winNoPointJ2.png");
	public final static ImageIcon WINPOINTJ1 = new ImageIcon("Images/winPointJ1.gif");
	public final static ImageIcon WINPOINTJ2 = new ImageIcon("Images/winPointJ2.gif");
	
	public final static ImageIcon IMGREADY = new ImageIcon("Images/Ready.png");
	public final static ImageIcon IMG3 = new ImageIcon("Images/3.png");
	public final static ImageIcon IMG2 = new ImageIcon("Images/2.png");
	public final static ImageIcon IMG1 = new ImageIcon("Images/1.png");
	public final static ImageIcon[] TABIMGCHIFFRE = new ImageIcon[]{IMG3,IMG2,IMG1};
	
	public final static ImageIcon MONTEETRANSITIONJ1 = new ImageIcon("Images/MonteeTransitionJ1.png");
	public final static ImageIcon MONTEETRANSITIONJ2 = new ImageIcon("Images/MonteeTransitionJ2.png");
	public final static ImageIcon BASGRILLE = new ImageIcon("Images/tmpBasGrille.png");
	
	public final static ImageIcon PUSHANYKEY = new ImageIcon("Images/pushAnyKey.gif");
	public static final ImageIcon PREVBRIQUE3 = new ImageIcon("Images/Blocs/PrevBrique3.png");
	public static final ImageIcon PREVBRIQUE4 = new ImageIcon("Images/Blocs/PrevBrique4.png");
	public static final ImageIcon PREVBRIQUE5 = new ImageIcon("Images/Blocs/PrevBrique5.png");
	public static final ImageIcon PREVBRIQUE6P = new ImageIcon("Images/Blocs/PrevBrique6p.png");
	public static final ImageIcon SELECTICON = new ImageIcon("Images/fleche.png");
	public static final String FONTNAMEMENU = "Awesome";
	public static final ImageIcon TITLEGIF = new ImageIcon("Images/titlescreen.gif");
	public static final ImageIcon BGBUTTONOPT = new ImageIcon("Images/bgButtonOpt.png");
	public static final ImageIcon FLECHEOPTD = new ImageIcon("Images/flecheOptionD.png");
	public static final ImageIcon FLECHEOPTG = new ImageIcon("Images/flecheOptionG.png");
	public static final ImageIcon FLECHEHAUT = new ImageIcon("Images/flecheHaut.png");
	public static final ImageIcon FLECHEBAS = new ImageIcon("Images/flecheBas.png");
	
	public static final ImageIcon AIDE1 = new ImageIcon("Aide/Concept.png");
	public static final ImageIcon AIDE2 = new ImageIcon("Aide/Deroulement.png");
	public static final ImageIcon AIDE3 = new ImageIcon("Aide/OperationsBases.png");
	public static final ImageIcon AIDE4 = new ImageIcon("Aide/IconeJeu.png");
	public static final ImageIcon AIDE5 = new ImageIcon("Aide/CombinaisonSimple.png");
	public static final ImageIcon AIDE6 = new ImageIcon("Aide/CombinaisonComplexe.png");
	public static final ImageIcon AIDE7 = new ImageIcon("Aide/chaine.png");
	public static final ImageIcon AIDE8 = new ImageIcon("Aide/briques.png");
	public static final ImageIcon AIDE9 = new ImageIcon("Aide/avertissementBriques.png");
	public static final ImageIcon AIDE10 = new ImageIcon("Aide/Scores.png");
	
	public static final ImageIcon[] AIDES = new ImageIcon[]{AIDE1,AIDE2,AIDE3,AIDE4,AIDE5,AIDE6,AIDE7,AIDE8,AIDE9,AIDE10};
	
	public static final ImageIcon CREDIT1 = new ImageIcon("Credit/Credit-1.png");
	public static final ImageIcon CREDIT2 = new ImageIcon("Credit/Credit-2.png");
	public static final ImageIcon CREDIT3 = new ImageIcon("Credit/Credit-3.png");
	public static final ImageIcon CREDIT4 = new ImageIcon("Credit/Credit-4.png");
	public static final ImageIcon CREDIT5 = new ImageIcon("Credit/Credit-5.png");
	public static final ImageIcon CREDIT6 = new ImageIcon("Credit/Credit-6.png");
	public static final ImageIcon[] CREDITS = new ImageIcon[]{CREDIT1, CREDIT2, CREDIT3, CREDIT4, CREDIT5, CREDIT6};
	
	/**
	 * Méthode qui permet de récupérer les configuration dans le fichier configuration.xml
	 */
	public static void recupererConfiguration()
	{
		try {
			
			final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			final DocumentBuilder builder = factory.newDocumentBuilder();
			final Document document = builder.parse(new File("Configuration.xml"));
			final Element racine = document.getDocumentElement();
			
			final NodeList resolution = racine.getElementsByTagName("resolution");
			final NodeList keyboardSettings = racine.getElementsByTagName("keyboardSetting");
			final NodeList volumes = racine.getElementsByTagName("volume");
			
			recupererResolution(resolution);
			recupererSettingsJoueurs(keyboardSettings);
			recupererVolume(volumes);
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Méthode qui permet de récupérer la configuration de résolution par rapport à la liste de noeud resolution.
	 * @param resolution Liste des noeuds résolution.
	 */
	private static void recupererResolution(final NodeList resolution)
	{
		if(resolution.getLength() == 1)
		{	
			try{
				multResolution = Integer.parseInt(resolution.item(0).getTextContent());
			}
			catch(NumberFormatException e){
				System.err.println("Valeur incorrecte pour résolution, valeur par défaut (3) prise");
			}
		}
		else
		{
			System.err.println("Element resolution inccorecte, valeur de 3 prise par défaut.");
		}
	}
	
	/**
	 * Méthode qui permet de récupérer la configuration de volume par rapport à la liste de noeuds volume.
	 * @param volumes Liste des noeuds volume.
	 */
	private static void recupererVolume(final NodeList volumes)
	{
		if(volumes.getLength() == 1)
		{
			try
			{
				volumeMusique = Integer.parseInt(volumes.item(0).getTextContent());
			}
			catch(NumberFormatException e)
			{
				System.err.println("Valeur incorrecte volume, valeur par défaut prise.");
			}
		}
		else
		{
			System.err.println("Element volume incorrecte, valeur prise par défaut.");
		}
	}
	
	/**
	 * Méthode qui permet de récupérer les configurations de touches des joueurs.
	 * @param keyboardSettings Liste des noeuds keyboardSetting.
	 */
	private static void recupererSettingsJoueurs(final NodeList keyboardSettings)
	{
		if(keyboardSettings.getLength() == 2)
		{
			for(int i = 0; i < keyboardSettings.getLength(); i++)
			{
				if(keyboardSettings.item(i).getNodeType() == Node.ELEMENT_NODE)
				{
					Element keyboardSetting = (Element)keyboardSettings.item(i);
					NodeList touches = keyboardSetting.getChildNodes();
					KeyboardSetting ks = null;
					
					if(keyboardSetting.getAttribute("id").equals("1"))
					{
						ks = keyboardSettingJ1;
					}
					else if(keyboardSetting.getAttribute("id").equals("2"))
					{
						ks = keyboardSettingJ2;
					}
					
					for(int j = 0; j < touches.getLength(); j++)
					{
						if(touches.item(j).getNodeType() != Node.ELEMENT_NODE)
							continue;
						
						String nodeName = touches.item(j).getNodeName();
						int nodeVal = Integer.parseInt(touches.item(j).getTextContent());
						
						if(nodeName.equals("haut"))
						{
							ks.setKeyUp(nodeVal);
						}
						else if(nodeName.equals("bas"))
						{
							ks.setKeyDown(nodeVal);
						}
						else if(nodeName.equals("gauche"))
						{
							ks.setKeyLeft(nodeVal);
						}
						else if(nodeName.equals("droite"))
						{
							ks.setKeyRight(nodeVal);
						}
						else if(nodeName.equals("echange"))
						{
							ks.setKeyAction(nodeVal);
						}
						else if(nodeName.equals("montee"))
						{
							ks.setKeySecondaryAction(nodeVal);
						}
					}
				}
			}
		}
		else
		{
			System.err.println("Elements keyboardSettings incorrectes, valeurs par défaut prises.");
		}
	}
	
	/**
	 * Méthode qui permet d'écrire les configurations actuelle dans le fichier configuration.xml
	 */
	public static void ecrireConfiguration()
	{
		try {
			
			final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			final DocumentBuilder builder = factory.newDocumentBuilder();
			final Document document = builder.parse(new File("Configuration.xml"));
			final Element racine = document.getDocumentElement();
			
			final NodeList resolution = racine.getElementsByTagName("resolution");
			final NodeList keyboardSettings = racine.getElementsByTagName("keyboardSetting");
			final NodeList volumes = racine.getElementsByTagName("volume");
			
			ecrireResolution(resolution);
			ecrireSettingsJoueurs(keyboardSettings);
			ecrireVolume(volumes);
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(new File("Configuration.xml"));
			transformer.transform(source, result);
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Méthode qui permet d'écrire les configurations des touches des joueurs dans la liste des noeuds keyboardsetting.
	 * @param keyboardSettings liste des noeuds keyboardsetting.
	 */
	private static void ecrireSettingsJoueurs(final NodeList keyboardSettings)
	{
		for(int i = 0; i < keyboardSettings.getLength(); i++)
		{
			if(keyboardSettings.item(i).getNodeType() == Node.ELEMENT_NODE)
			{
				Element keyboardSetting = (Element)keyboardSettings.item(i);
				NodeList touches = keyboardSetting.getChildNodes();
				KeyboardSetting ks = null;
				
				if(keyboardSetting.getAttribute("id").equals("1"))
				{
					ks = keyboardSettingJ1;
				}
				else if(keyboardSetting.getAttribute("id").equals("2"))
				{
					ks = keyboardSettingJ2;
				}
				
				for(int j = 0; j < touches.getLength(); j++)
				{
					if(touches.item(j).getNodeType() != Node.ELEMENT_NODE)
						continue;
					
					Element touche = (Element)touches.item(j);
					String nodeName = touche.getNodeName();
					
					if(nodeName.equals("haut"))
					{
						touche.setTextContent(String.valueOf(ks.getKeyUp()));
					}
					else if(nodeName.equals("bas"))
					{
						touche.setTextContent(String.valueOf(ks.getKeyDown()));
					}
					else if(nodeName.equals("gauche"))
					{
						touche.setTextContent(String.valueOf(ks.getKeyLeft()));
					}
					else if(nodeName.equals("droite"))
					{
						touche.setTextContent(String.valueOf(ks.getKeyRight()));
					}
					else if(nodeName.equals("echange"))
					{
						touche.setTextContent(String.valueOf(ks.getKeyAction()));
					}
					else if(nodeName.equals("montee"))
					{
						touche.setTextContent(String.valueOf(ks.getKeySecondaryAction()));
					}
				}
			}
		}
	}
	
	/**
	 * Méthode qui permet d'écrire résolution actuelle dans la liste des noeuds résolution.
	 * @param resolution liste des noeuds résolution.
	 */
	private static void ecrireResolution(final NodeList resolution)
	{
		resolution.item(0).setTextContent(String.valueOf(multResolution));
	}
	
	/**
	 * Méthode qui permet d'écrire la configuration de volume dans la liste des noeuds volume.
	 * @param volume liste des noeuds volume.
	 */
	private static void ecrireVolume(final NodeList volume)
	{
		volume.item(0).setTextContent(String.valueOf(volumeMusique));
	}
	
	/**
	 * Méthode qui permet de savoir si une touche appartient à l'une des configurations de touches d'un joueur.
	 * @param t Constante de {@link KeyEvent} représentant la touche dont ou souhaite savoir si elle existe deja.
	 * @param exception Constante de {@link KeyEvent} réprésentant la touche à ne pas prendre en compte.
	 * @return Vrai si la touche existe déjà et qu'elle ne correspond pas a l'exception. faux sinon.
	 */
	public static boolean toucheDejaPrise(int t, int exception)
	{
		if(t == KeyEvent.VK_ESCAPE)
			return true;
		for(int touche : keyboardSettingJ1.allKeyValues())
		{
			if(touche == t && touche != exception)
				return true;
		}
		for(int touche : keyboardSettingJ2.allKeyValues())
		{
			if(touche == t && touche != exception)
				return true;
		}
		return false;
	}
}
