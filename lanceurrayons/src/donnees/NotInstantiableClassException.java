package donnees;

@SuppressWarnings("serial")
// DLB: extends RuntimeException
public class NotInstantiableClassException extends Exception {

    public NotInstantiableClassException() {
        super("Classe non instanciable");
    }
}
