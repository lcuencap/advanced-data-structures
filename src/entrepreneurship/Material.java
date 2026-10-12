package entrepreneurship;

/**
 * Enum per representar els materials i poder optimitzar les comparacions que faran falta
 * per veure quines rutes es poden utilitzar.
 */
public enum Material {

    GLASS ("GLASS"),
    PLASTIC ("PLASTIC"),
    METAL ("METAL"),
    ORGANIC ("ORGANIC"),
    NONE ("NONE");

    /**
     * Etiqueta per a poder passar d'String a entrepreneurship.Material.
     */
    public final String label;

    private Material(String label) {
        this.label = label;
    }

    /**
     * Converteix un String amb la informació d'un material a un objecte entrepreneurship.Material
     * @param label String amb el nom d'un material.
     * @return Objecte de la classe entrepreneurship.Material amb el material corresponent segons l'input.
     */
    public static Material parseMaterial(String label) {
        for (Material material: values()) {
            if (material.label.equals(label)) {
                return material;
            }
        }
        return null;
    }
}
