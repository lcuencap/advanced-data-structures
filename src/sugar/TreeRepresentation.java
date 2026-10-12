package sugar;

import java.util.ArrayList;
import java.util.List;

public class TreeRepresentation {
    private static final String CYAN = "\u001B[36m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RESET = "\u001B[0m";

    public static void printTree(Citizen root) {
        if (root != null) {
            System.out.println();
            printTreeHelper(root, new ArrayList<Boolean>(), true);
            System.out.println();
        }
    }

    private static void printTreeHelper(Citizen current, ArrayList<Boolean> branches, boolean rightBranch) {
        if (current == null) {
            return;
        }

        if (current.getRightChild() != null) {
            branches.add(true);
            printTreeHelper(current.getRightChild(), branches, rightBranch);
            branches.removeLast();
        }

        if (!branches.isEmpty()) {
            for (int i = 1; i < branches.size(); i++) {
                if (branches.get(i) != branches.get(i-1)) {
                    System.out.print("|      ");
                }else {
                    System.out.print("       ");
                }
            }
            System.out.print("|--- ");
        } else {
            rightBranch = false;
            System.out.print("* ");
        }
        printCitizenInfo(current);
        System.out.println();

        if (current.getLeftChild() != null) {
            branches.add(false);
            printTreeHelper(current.getLeftChild(), branches, rightBranch);
            branches.removeLast();
        }
    }

    private static void printCitizenInfo(Citizen citizen) {
        String color = citizen.isLemon() ? YELLOW : CYAN;
        System.out.print(color + citizen.getName() + " (" + citizen.getId() + ", " + citizen.getJob().label + "): " + citizen.getSugarIndex() + "%" + RESET);
    }
}

