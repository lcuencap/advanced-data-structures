package entrepreneurship;

import java.util.*;

public class Toposort {
    private static void topologicalSortUtil(Headquarters hq, Queue<Headquarters> list) {
        //Recorrem tots els vertex adjacents
        for (Route route : hq.getSuppliers()) {
            Headquarters adjacentHq = route.getAdjacentHQ();
            if (adjacentHq.getSuppliers().isEmpty() && !list.contains(adjacentHq)) {
                list.add(adjacentHq);
            }
            if (!adjacentHq.isVisited()) {
                topologicalSortUtil(adjacentHq, list);
            }
        }
        hq.setVisited(true);
        //Guardem el hq resultant.
        if (!list.contains(hq)) {
            list.add(hq);
        }
    }


    public static void topologicalSort(Headquarters[] headquarters) {
        Queue<Headquarters> list = new LinkedList<>();

        if (isAcyclic(headquarters)){
            //Si el vertex no ha estat visitat es crida la funció que té la lógica d'ordenació recursiva
            for (Headquarters hq : headquarters) {
                if (!hq.isVisited()) {
                    topologicalSortUtil(hq, list);
                }
            }

            //Imprimim l'ordre
            while (!list.isEmpty()) {
                Headquarters hq = list.remove();
                System.out.println(hq.getID() + " - " + hq.getCompany() +", makes "+hq.getMaterial());
            }
        }else {
            System.out.println("No es pot realitzar l'ordenació basada en l'execució de la cadena degut a que " +
                    "el conjunt de dades proporcionat és cíclic.");

        }


    }
    private static boolean dfsHasCycle(Headquarters hq, ArrayList<Headquarters> visited, ArrayList<Headquarters> remaining) {
        if (!visited.contains(hq)) {
            visited.add(hq);
            remaining.add(hq);

            for (Route supplier : hq.getSuppliers()) {
                Headquarters child = supplier.getAdjacentHQ();
                if (!visited.contains(child) && dfsHasCycle(child, visited, remaining)) {
                    return true;
                } else if (remaining.contains(child)) {
                    return true;
                }
            }
        }
        remaining.remove(hq);
        return false;
    }

    public static boolean isAcyclic(Headquarters[] headquarters) {
        ArrayList<Headquarters> visited = new ArrayList<>();
        for (Headquarters hq : headquarters) {
            if (!visited.contains(hq)) {
                ArrayList<Headquarters> remaining = new ArrayList<>();
                if (dfsHasCycle(hq, visited, remaining)) {
                    return false;
                }
            }
        }
        return true;
    }
}
