package sugar;

import entrepreneurship.Headquarters;

import java.util.ArrayList;
import java.util.List;

public class CloneCapture {
    public static Citizen closestToMin;
    private static float minSugarIndex;
    private static float maxSugarIndex;
    public Citizen root;
    private static List<Citizen> clones = new ArrayList<>();

    public CloneCapture (float min, float max, Citizen  root) {
        this.minSugarIndex = min;
        this.maxSugarIndex = max;
        this.root = root;
    }

    public void captureClones() {
        searchForClones(root);
        System.out.println("\n S'han capturat "+ clones.size() + " clons:\n");
        printCapturedClones();
        System.out.println("");
    }
    public static void searchForClones(Citizen citizen) {
        if (citizen.getLeftChild() != null && citizen.getSugarIndex() > minSugarIndex) {
            searchForClones(citizen.getLeftChild());
        }
        if (minSugarIndex < citizen.getSugarIndex() && maxSugarIndex > citizen.getSugarIndex()) {
            clones.add(citizen);
        }
        if (citizen.getRightChild() != null && citizen.getSugarIndex() < maxSugarIndex) {
            searchForClones(citizen.getRightChild());
        }
    }
    private void printCapturedClones () {
        List<Citizen> reversedClones = clones.reversed();
        for (Citizen current : reversedClones) {
            System.out.println("\t * " + current.getName() + " (" + current.getId() + ", " + current.getJob().label + "): " + current.getSugarIndex() + "%");
        }
    }

}
