package donnees;

/**
 * Interface fonctionnelle permettant d'effectuer des opérations, avec un int en paramètre.
 * Elle est utilisée dans le tetris attack pour les options, et mettre à jour les variables correspondant aux raccourcis, résolution et volume.
 * @author Sacha
 *
 */
@FunctionalInterface
public interface Changement {

	public void changer(int nouveau);
}
