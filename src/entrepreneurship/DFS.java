package entrepreneurship;

import java.util.ArrayList;
import java.util.List;

public class DFS {

    public static List<List<Headquarters>> getVertexsByLevelFromCompany(Headquarters[] graph) {

        //La llista la omplirem en ordre ascendent. La posició 0 de la llista seran les seus al final de la cadena de
        //producció. Ja que recorrerem el graf fen una cerca en profunditat des de el vertex on acaban les dependencies
        // fins l'origen d'aquestes.
        List <List<Headquarters>> hqList = new ArrayList<>();

        for (Headquarters hq : graph) {
            if (hq.isEnd() && !hq.isVisited()) {
                DFS(hq,0, hqList);
            }
        }
        return hqList;
    }

    public static void DFS(Headquarters actual, int level, List <List<Headquarters>> hqList) {
        // Marquem el vertex com a visitat
        actual.setVisited(true);

        // Mirem si el nivell de la seu en el graf estroba en un nivell ja visitat previament o no.
        if (level >= hqList.size()) {
            hqList.add(new ArrayList<>());
        }

        // Afegim la seu en la que ens trobem actualment en el recorregut del graf al nivell corresponent de la llista.
        hqList.get(level).add(actual);

        level++;

        // Obtenim els pares
        ArrayList<Route> adjacents = actual.getSuppliers();
        for (Route adj : adjacents) {
            if(!adj.getAdjacentHQ().isVisited()) {
                DFS(adj.getAdjacentHQ(), level, hqList);
            }
        }
        level--;
    }
}
