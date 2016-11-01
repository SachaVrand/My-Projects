package principal;

public enum KeyWord {
    SIZE("size", 2), OUTPUT("output", 1), CAMERA("camera", 10), AMBIENT("ambient", 3), DIFFUSE("diffuse",
            3), SPECULAR("specular", 3), DIRECTIONAL("directional", 6), POINT("point", 6), MAXVERTS("maxverts",
                    1), VERTEX("vertex", 3), TRI("tri", 3), SPHERE("sphere", 4), PLANE("plane", 6), SHADOW("shadow",
                            1), SHININESS("shininess", 1), MAXDEPTH("maxdepth", 1), TRANSLATE("translate", 3), SCALE(
                                    "scale", 3), ROTATE("rotate", 4), CHECKER("checker", 7), SAMPLING("sampling",
                                            2), PUSHTRANSFORM("pushTransform", 0), POPTRANSFORM("popTransform", 0);

    private String libelle;
    private int nbParam;

    private KeyWord(String libelle, int nbParam) {
        this.libelle = libelle;
        this.nbParam = nbParam;
    }

    public String getLibelle() {
        return libelle;
    }

    public int getNbParam() {
        return nbParam;
    }
}
