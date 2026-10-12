package sugar;

import java.util.*;

public class InfiltratorSearch {

    private final Citizen root;

    public InfiltratorSearch(Citizen citizen) {
        root = citizen;
    }

    public void searchInfiltrators(float sugarIndex, String base) {
        StringBuilder stringBuilder = new StringBuilder();

        Citizen suspect = searchBySugarIndex(root, sugarIndex);

        if (suspect != null) {
            stringBuilder.append("\nS'ha trobat l'habitant ");
            appendCitizen(stringBuilder, suspect);

            if (base.equalsIgnoreCase("Profiterola")) {
                Citizen[] suspects = levelOrderTraversal(suspect);

                stringBuilder.append("\nBasant-nos en la fórmula Profiterola, la resta d’habitants sospitosos són:\n\n");
                for (Citizen citizen: suspects) {
                    stringBuilder.append("* ");
                    appendCitizen(stringBuilder, citizen);
                }
            } else {
                // Com l'arbre està ordenat per índex de sucre, en fer un inordre ja obtenim l'ordre esperat.
                inorder(suspect, stringBuilder);
            }
        } else {
            stringBuilder.append("\nNo hi ha cap habitant amb l'index de sucre introduït.");
        }

        System.out.println(stringBuilder);
    }

    private Citizen searchBySugarIndex(Citizen citizen, float sugarIndex) {
        if (citizen == null) {
            return null;
        }

        if (sugarIndex < citizen.getSugarIndex()) {
            return searchBySugarIndex(citizen.getLeftChild(), sugarIndex);
        }

        if (sugarIndex > citizen.getSugarIndex()) {
            return searchBySugarIndex(citizen.getRightChild(), sugarIndex);
        }

        return citizen;
    }

    private Citizen[] levelOrderTraversal(Citizen citizen) {
        List<Citizen> sameLevelCitizens = new ArrayList<>();
        Queue<Citizen> citizenQueue = new LinkedList<>();

        int targetLevel = findCitizenLevel(citizen, 0);

        citizenQueue.add(root);
        int currentLevel = 1;

        while (!citizenQueue.isEmpty()) {

            // Si es compleix aquesta condició vol dir que hem exhaurit un nivell.
            if (sameLevelCitizens.size() >= Math.pow(2, currentLevel)) {
                if (currentLevel == targetLevel) {
                    break;
                } else {
                    sameLevelCitizens.clear();
                    currentLevel++;
                }
            }

            Citizen currentCitizen = citizenQueue.poll();

            if (currentCitizen != null) {
                citizenQueue.add(currentCitizen.getLeftChild());
                sameLevelCitizens.add(currentCitizen.getLeftChild());

                citizenQueue.add(currentCitizen.getRightChild());
                sameLevelCitizens.add(currentCitizen.getRightChild());
            } else {
                //Si l'habitant és null, afegim els seus dos fills com a null per a poder seguir comptant.
                sameLevelCitizens.add(null);
                sameLevelCitizens.add(null);
            }
        }

        // Retornem els habitants del nivell de l'introduït, esborrant els nulls que hi puguin haver.
        return sameLevelCitizens.stream().filter(Objects::nonNull).toArray(Citizen[]::new);
    }

    private void inorder(Citizen citizen, StringBuilder stringBuilder) {

        if (citizen.getLeftChild() != null) {
            inorder(citizen.getLeftChild(), stringBuilder);
        }

        stringBuilder.append("\t* ");
        appendCitizen(stringBuilder, citizen);

        if (citizen.getRightChild() != null) {
            inorder(citizen.getRightChild(), stringBuilder);
        }
    }

    private int findCitizenLevel(Citizen citizen, int level) {
        if (citizen.getParent() != null) {
            return findCitizenLevel(citizen.getParent(), level + 1);
        }

        return level;
    }

    private void appendCitizen(StringBuilder stringBuilder, Citizen citizen) {
        stringBuilder.append(citizen.getName()).append(" ");
        stringBuilder.append("(").append(citizen.getId()).append(", ").append(citizen.getJob()).append("): ");
        stringBuilder.append(citizen.getSugarIndex()).append("%\n");
    }
}
