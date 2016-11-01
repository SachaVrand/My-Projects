package principal;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Classe regroupant les ressources graphique des blocs et briques.
 * @author Sacha
 *
 */
public class RessourcesBlocs {

	public final static BufferedImage BLOCROUGE = initImage("Images/Blocs/rouge1_16x16.png");
	public final static BufferedImage BLOCVERT = initImage("Images/Blocs/vert1_16x16.png");
	public final static BufferedImage BLOCVIOLET = initImage("Images/Blocs/violet1_16x16.png");
	public final static BufferedImage BLOCJAUNE = initImage("Images/Blocs/jaune1_16x16.png");
	public final static BufferedImage BLOCTURQUOISE = initImage("Images/Blocs/cyan1_16x16.png");
	public final static BufferedImage BLOCBLEU = initImage("Images/Blocs/bleu1_16x16.png");
	public final static BufferedImage BLOCVIDE = initImage("Images/Blocs/vide_16x16.png");

	public final static BufferedImage BLOCROUGE2 = initImage("Images/Blocs/rouge2_16x16.png");
	public final static BufferedImage BLOCVERT2 = initImage("Images/Blocs/vert2_16x16.png");
	public final static BufferedImage BLOCVIOLET2 = initImage("Images/Blocs/violet2_16x16.png");
	public final static BufferedImage BLOCJAUNE2 = initImage("Images/Blocs/jaune2_16x16.png");
	public final static BufferedImage BLOCTURQUOISE2 = initImage("Images/Blocs/cyan2_16x16.png");
	public final static BufferedImage BLOCBLEU2 = initImage("Images/Blocs/bleu2_16x16.png");

	public final static BufferedImage BLOCROUGE3 = initImage("Images/Blocs/rouge3_16x16.png");
	public final static BufferedImage BLOCVERT3 = initImage("Images/Blocs/vert3_16x16.png");
	public final static BufferedImage BLOCVIOLET3 = initImage("Images/Blocs/violet3_16x16.png");
	public final static BufferedImage BLOCJAUNE3 = initImage("Images/Blocs/jaune3_16x16.png");
	public final static BufferedImage BLOCTURQUOISE3 = initImage("Images/Blocs/cyan3_16x16.png");
	public final static BufferedImage BLOCBLEU3 = initImage("Images/Blocs/bleu3_16x16.png");

	public final static BufferedImage BLOCROUGE4 = initImage("Images/Blocs/rouge4_16x16.png");
	public final static BufferedImage BLOCVERT4 = initImage("Images/Blocs/vert4_16x16.png");
	public final static BufferedImage BLOCVIOLET4 = initImage("Images/Blocs/violet4_16x16.png");
	public final static BufferedImage BLOCJAUNE4 = initImage("Images/Blocs/jaune4_16x16.png");
	public final static BufferedImage BLOCTURQUOISE4 = initImage("Images/Blocs/cyan4_16x16.png");
	public final static BufferedImage BLOCBLEU4 = initImage("Images/Blocs/bleu4_16x16.png");

	public final static BufferedImage BLOCROUGE5 = initImage("Images/Blocs/rouge5_16x16.png");
	public final static BufferedImage BLOCVERT5 = initImage("Images/Blocs/vert5_16x16.png");
	public final static BufferedImage BLOCVIOLET5 = initImage("Images/Blocs/violet5_16x16.png");
	public final static BufferedImage BLOCJAUNE5 = initImage("Images/Blocs/jaune5_16x16.png");
	public final static BufferedImage BLOCTURQUOISE5 = initImage("Images/Blocs/cyan5_16x16.png");
	public final static BufferedImage BLOCBLEU5 = initImage("Images/Blocs/bleu5_16x16.png");

	public final static BufferedImage BLOCROUGE6 = initImage("Images/Blocs/rouge6_16x16.png");
	public final static BufferedImage BLOCVERT6 = initImage("Images/Blocs/vert6_16x16.png");
	public final static BufferedImage BLOCVIOLET6 = initImage("Images/Blocs/violet6_16x16.png");
	public final static BufferedImage BLOCJAUNE6 = initImage("Images/Blocs/jaune6_16x16.png");
	public final static BufferedImage BLOCTURQUOISE6 = initImage("Images/Blocs/cyan6_16x16.png");
	public final static BufferedImage BLOCBLEU6 = initImage("Images/Blocs/bleu6_16x16.png");

	public final static BufferedImage BLOCROUGE7 = initImage("Images/Blocs/rouge7_16x16.png");
	public final static BufferedImage BLOCVERT7 = initImage("Images/Blocs/vert7_16x16.png");
	public final static BufferedImage BLOCVIOLET7 = initImage("Images/Blocs/violet7_16x16.png");
	public final static BufferedImage BLOCJAUNE7 = initImage("Images/Blocs/jaune7_16x16.png");
	public final static BufferedImage BLOCTURQUOISE7 = initImage("Images/Blocs/cyan7_16x16.png");
	public final static BufferedImage BLOCBLEU7 = initImage("Images/Blocs/bleu7_16x16.png");

	public final static BufferedImage BLOCROUGE8 = initImage("Images/Blocs/rouge8_16x16.png");
	public final static BufferedImage BLOCVERT8 = initImage("Images/Blocs/vert8_16x16.png");
	public final static BufferedImage BLOCVIOLET8 = initImage("Images/Blocs/violet8_16x16.png");
	public final static BufferedImage BLOCJAUNE8 = initImage("Images/Blocs/jaune8_16x16.png");
	public final static BufferedImage BLOCTURQUOISE8 = initImage("Images/Blocs/cyan8_16x16.png");
	public final static BufferedImage BLOCBLEU8 = initImage("Images/Blocs/bleu8_16x16.png");

	public final static BufferedImage SOLIDEMILIEU = initImage("Images/Blocs/solideMilieu.png");
	public final static BufferedImage SOLIDEMILIEUHAUT = initImage("Images/Blocs/solideMilieuHaut.png");
	public final static BufferedImage SOLIDEMILIEUBAS = initImage("Images/Blocs/solideMilieuBas.png");
	public final static BufferedImage SOLIDEMILIEUDROITE = initImage("Images/Blocs/solideMilieuDroite.png");
	public final static BufferedImage SOLIDEMILIEUGAUCHE = initImage("Images/Blocs/solideMilieuGauche.png");
	public final static BufferedImage SOLIDEMILIEUMILIEU = initImage("Images/Blocs/solideMilieuMilieu.png");
	public final static BufferedImage SOLIDEGAUCHE = initImage("Images/Blocs/solideGauche.png");
	public final static BufferedImage SOLIDEDROITE = initImage("Images/Blocs/solideDroite.png");
	public final static BufferedImage SOLIDEINFGAUCHE = initImage("Images/Blocs/solideInfGauche.png");
	public final static BufferedImage SOLIDESUPGAUCHE = initImage("Images/Blocs/solideSupGauche.png");
	public final static BufferedImage SOLIDEINFDROITE = initImage("Images/Blocs/solideInfDroite.png");
	public final static BufferedImage SOLIDESUPDROITE = initImage("Images/Blocs/solideSupDroite.png");
	public final static BufferedImage SOLIDEANIME = initImage("Images/Blocs/solideAnime.png");
	public final static BufferedImage SOLIDEANIME2 = initImage("Images/Blocs/gris7_16x16.png");

	public final static BufferedImage[] ANIMCOMBIROUGE = new BufferedImage[] { BLOCROUGE7, BLOCROUGE, BLOCROUGE7, BLOCROUGE,
			BLOCROUGE7, BLOCROUGE, BLOCROUGE6 };
	public final static BufferedImage[] ANIMCOMBIVERT = new BufferedImage[] { BLOCVERT7, BLOCVERT, BLOCVERT7, BLOCVERT,
			BLOCVERT7, BLOCVERT, BLOCVERT6 };
	public final static BufferedImage[] ANIMCOMBIVIOLET = new BufferedImage[] { BLOCVIOLET7, BLOCVIOLET, BLOCVIOLET7,
			BLOCVIOLET, BLOCVIOLET7, BLOCVIOLET, BLOCVIOLET6 };
	public final static BufferedImage[] ANIMCOMBIJAUNE = new BufferedImage[] { BLOCJAUNE7, BLOCJAUNE, BLOCJAUNE7, BLOCJAUNE,
			BLOCJAUNE7, BLOCJAUNE, BLOCJAUNE6 };
	public final static BufferedImage[] ANIMCOMBITURQUOISE = new BufferedImage[] { BLOCTURQUOISE7, BLOCTURQUOISE,
			BLOCTURQUOISE7, BLOCTURQUOISE, BLOCTURQUOISE7, BLOCTURQUOISE, BLOCTURQUOISE6 };
	public final static BufferedImage[] ANIMCOMBIBLEU = new BufferedImage[] { BLOCBLEU7, BLOCBLEU, BLOCBLEU7, BLOCBLEU,
			BLOCBLEU7, BLOCBLEU, BLOCBLEU6 };
	public final static BufferedImage[] ANIMSOLIDE = new BufferedImage[] { SOLIDEANIME, SOLIDEANIME2, SOLIDEANIME,
			SOLIDEANIME2, SOLIDEANIME, SOLIDEANIME2, SOLIDEANIME };

	public final static BufferedImage[] ANIMWARNROUGE = new BufferedImage[] { BLOCROUGE, BLOCROUGE2, BLOCROUGE3, BLOCROUGE4,
			BLOCROUGE3, BLOCROUGE2, BLOCROUGE };
	public final static BufferedImage[] ANIMWARNVERT = new BufferedImage[] { BLOCVERT, BLOCVERT2, BLOCVERT3, BLOCVERT4,
			BLOCVERT3, BLOCVERT2, BLOCVERT };
	public final static BufferedImage[] ANIMWARNVIOLET = new BufferedImage[] { BLOCVIOLET, BLOCVIOLET2, BLOCVIOLET3,
			BLOCVIOLET4, BLOCVIOLET3, BLOCVIOLET2, BLOCVIOLET };
	public final static BufferedImage[] ANIMWARNJAUNE = new BufferedImage[] { BLOCJAUNE, BLOCJAUNE2, BLOCJAUNE3, BLOCJAUNE4,
			BLOCJAUNE3, BLOCJAUNE2, BLOCJAUNE };
	public final static BufferedImage[] ANIMWARNTURQUOISE = new BufferedImage[] { BLOCTURQUOISE, BLOCTURQUOISE2,
			BLOCTURQUOISE3, BLOCTURQUOISE4, BLOCTURQUOISE3, BLOCTURQUOISE2, BLOCTURQUOISE };
	public final static BufferedImage[] ANIMWARNBLEU = new BufferedImage[] { BLOCBLEU, BLOCBLEU2, BLOCBLEU3, BLOCBLEU4,
			BLOCBLEU3, BLOCBLEU2, BLOCBLEU };

	public final static BufferedImage[] LANDANIMROUGE = new BufferedImage[]{BLOCROUGE2,BLOCROUGE3,BLOCROUGE2,BLOCROUGE};
	public final static BufferedImage[] LANDANIMVERT = new BufferedImage[]{BLOCVERT2,BLOCVERT3,BLOCVERT2,BLOCVERT};
	public final static BufferedImage[] LANDANIMVIOLET = new BufferedImage[]{BLOCVIOLET2,BLOCVIOLET3,BLOCVIOLET2,BLOCVIOLET};
	public final static BufferedImage[] LANDANIMJAUNE = new BufferedImage[]{BLOCJAUNE2,BLOCJAUNE3,BLOCJAUNE2,BLOCJAUNE};
	public final static BufferedImage[] LANDANIMTURQUOISE = new BufferedImage[]{BLOCTURQUOISE2,BLOCTURQUOISE3,BLOCTURQUOISE2,BLOCTURQUOISE};
	public final static BufferedImage[] LANDANIMBLEU = new BufferedImage[]{BLOCBLEU2,BLOCBLEU3,BLOCBLEU2,BLOCBLEU};
	
	private static BufferedImage initImage(String path) {
		try {
			return ImageIO.read(new File(path));
		} catch (IOException e) {
			return null;
		}
	}
}
