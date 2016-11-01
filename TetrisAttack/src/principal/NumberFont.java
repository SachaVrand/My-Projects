package principal;

import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * Classe regroupant les chiffres en jeu.
 * @author Sacha
 *
 */
public final class NumberFont {
	//Images des chiffres en jeu.
	public static final ImageIcon TZERO = new ImageIcon("Images/Numbers/RedZero.png");
	private static final ImageIcon TONE = new ImageIcon("Images/Numbers/RedOne.png");
	private static final ImageIcon TTWO = new ImageIcon("Images/Numbers/RedTwo.png");
	private static final ImageIcon TTHREE = new ImageIcon("Images/Numbers/RedThree.png");
	private static final ImageIcon TFOUR = new ImageIcon("Images/Numbers/RedFour.png");
	private static final ImageIcon TFIVE = new ImageIcon("Images/Numbers/RedFive.png");
	private static final ImageIcon TSIX = new ImageIcon("Images/Numbers/RedSix.png");
	private static final ImageIcon TSEVEN = new ImageIcon("Images/Numbers/RedSeven.png");
	private static final ImageIcon TEIGHT = new ImageIcon("Images/Numbers/RedEight.png");
	private static final ImageIcon TNINE = new ImageIcon("Images/Numbers/RedNine.png");
	private static final ImageIcon[] TABNUMBERRED = new ImageIcon[]{TZERO,TONE,TTWO,TTHREE,TFOUR,TFIVE,TSIX,TSEVEN,TEIGHT,TNINE};
	private static final ImageIcon COMMA = new ImageIcon("Images/Numbers/Comma.png");
	
	private static final ImageIcon SZERO = new ImageIcon("Images/Numbers/BlueZero.png");
	private static final ImageIcon SONE = new ImageIcon("Images/Numbers/BlueOne.png");
	private static final ImageIcon STWO = new ImageIcon("Images/Numbers/BlueTwo.png");
	private static final ImageIcon STHREE = new ImageIcon("Images/Numbers/BlueThree.png");
	private static final ImageIcon SFOUR = new ImageIcon("Images/Numbers/BlueFour.png");
	private static final ImageIcon SFIVE = new ImageIcon("Images/Numbers/BlueFive.png");
	private static final ImageIcon SSIX = new ImageIcon("Images/Numbers/BlueSix.png");
	private static final ImageIcon SSEVEN = new ImageIcon("Images/Numbers/BlueSeven.png");
	private static final ImageIcon SEIGHT = new ImageIcon("Images/Numbers/BlueEight.png");
	private static final ImageIcon SNINE = new ImageIcon("Images/Numbers/BlueNine.png");
	private static final ImageIcon[] TABNUMBERBLUE = new ImageIcon[]{SZERO,SONE,STWO,STHREE,SFOUR,SFIVE,SSIX,SSEVEN,SEIGHT,SNINE};
	
	private NumberFont() throws Exception
	{
		throw new Exception();
	}
	
	/**
	 * Fonction qui retourne l'image du chiffre en rouge correspondant au caractère.
	 * @param c caractère du chiffre à retourner.
	 * @return retourne l'image du chiffre de c.
	 */
	public static Image getNumberImageRed(char c)
	{
		if(c >= '0' && c <= '9')
		{
			return TABNUMBERRED[Character.getNumericValue(c)].getImage();
		}
		else if(c == '\'')
		{
			return COMMA.getImage();
		}
		return null;
	}
	
	/**
	 * Fonction qui retourne l'image du chiffre en bleu correspondant au caractère.
	 * @param c caractère du chiffre à retourner.
	 * @return retourne l'image du chiffre de c.
	 */
	public static Image getNumberImageBlue(char c)
	{
		if(c >= '0' && c <= '9')
		{
			return TABNUMBERBLUE[Character.getNumericValue(c)].getImage();
		}
		return null;
	}

}
