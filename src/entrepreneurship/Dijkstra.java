package entrepreneurship;

import java.util.ArrayList;

public class Dijkstra {

    private static boolean quedenVertex(Headquarters[] headquarters) {
        for(Headquarters hq: headquarters) {
            if(!hq.isVisited()) {
                return true;
            }
        }
        return false;
    }


    /**
     * Troba la seu amb la distància a l'origen més petita
     * @param headquarters llista de seus (graf)
     * @return seu amb distància mínima (nou actual)
     */
    private static Headquarters trobaVertexDistMin(Headquarters[] headquarters) {
        int min = headquarters[0].getDijkstraDistance();
        Headquarters nou_actual = headquarters[0];
        for(int i = 1; i < headquarters.length; i++) {
            // Si el nou actual assignat a l'inici del mètode ja ha estat visitat l'hem d'actualitzar pel proper node no visitat (sino bucle infinit)
            if((headquarters[i].getDijkstraDistance() < min && !headquarters[i].isVisited()) || (nou_actual.isVisited() && !headquarters[i].isVisited())) {
                min = headquarters[i].getDijkstraDistance();
                nou_actual = headquarters[i];
            }
        }
        return nou_actual;
    }


    /**
     * Reconstrueix el camí més curt entre el node inicial i final a partir de la informació a l'array de camins
     * Parteix del node final, troba el seu pare, a continuació el pare del pare i així successivament fins que s'arriba al node inicial
     *
     * @param inici        node inicial
     * @param fin          node final
     * @param headquarters llista de seus (graf)
     * @return llista que conté el camí més curt
     */
    private static ArrayList<Headquarters> reconstrueix(Headquarters inici, Headquarters fin, Headquarters[] headquarters) throws CamiInviableException {
        ArrayList<Headquarters> cami = new ArrayList<>();
        Headquarters actual = fin;
        while(actual.getID() != inici.getID()) {
            if(actual.getDijkstraParent() >= 0) {
                cami.add(actual);
                // actual = headquarters[índex pare actual]; Assignem el pare de l'actual com a nou actual
                actual = headquarters[Entrepreneurship.binarySearch(0, headquarters.length, actual.getDijkstraParent(), headquarters.clone())];
            }
            else {
                throw new CamiInviableException("No hi ha cap ruta que connecti els nodes especificats");
            }

        }
        cami.add(inici);
        return cami;
    }

    /**
     * Indica si el material que produeix el pare està restringit a la ruta que connecta amb l'adjacent
     * @param pare node pare
     * @param ruta ruta que connecta el pare amb l'adjacent
     * @return booleà on 'CERT' indica que la ruta està restringida, i 'FALS' que la ruta no hi està.
     */
    public static boolean rutaRestringida(Headquarters pare, Route ruta) {
        return pare.getMaterial() == ruta.getRestriction();
    }
    public static ArrayList<Headquarters> dijkstra(Headquarters[] headquarters, Headquarters inici, Headquarters fin) throws CamiInviableException {
        // Node actual
        Headquarters actual;
        actual = inici;
        // Variable per controlar el cas en que un camí no és viable, és a dir totes les rutes d'un node pare als respectius nodes adjacents estan restringides
        boolean camiViable = false;

        // Actualitzem distàncies a màxim enter, camins a -1 i visitats a fals
        for(Headquarters hq:headquarters) {
            hq.setVisited(false);
            hq.setDijkstraParent(-1);
            hq.setDijkstraDistance(Integer.MAX_VALUE);
        }

        // Assignem distància 0 al node inicial
        inici.setDijkstraDistance(0);

        // Mentre quedin vèrtex per visitar i no s'hagi visitat el final s'executarà el bucle
        // Mètode avaua el node final, quan acaba de mirar adjacents el marca com visitat i per tant el bucle acaba
        // Problema si el node final té sucessors i graf ciclic
        while(quedenVertex(headquarters) && !fin.isVisited()) {
            ArrayList<Route> adjacents = actual.getClients(); // Obtenim nodes adjacents al node actual
            for(Route route: adjacents) {
                if(!rutaRestringida(actual, route)) {
                    camiViable = true;
                    // Si l'adjacent no està visitat avaluem nova distància
                    if(!route.getAdjacentHQ().isVisited()) {
                        int dist_actual = actual.getDijkstraDistance();
                        int nova = dist_actual + route.getShippingTime();
                        int dist_adjacent = route.getAdjacentHQ().getDijkstraDistance();
                        // Si la nova distància és menor a la registrada fins al moment actualitzem tant el valor de distància com el node pare
                        if(nova < dist_adjacent) {
                            route.getAdjacentHQ().setDijkstraDistance(nova);
                            route.getAdjacentHQ().setDijkstraParent(actual.getID());
                        }
                    }
                }
            }
            // Avaluem si s'ha pogut avançar a un dels nodes adjacents
            if(!adjacents.isEmpty()) {
                if(!camiViable) {;
                    throw new CamiInviableException("Degut a les restriccions de les seus del graf el camí entre els nodes especificats no és viable") ;
                }
            }
            camiViable = false;
            // Una vegada s'han avaluat els nodes adjacents del node actual el marquem com a visitat
            actual.setVisited(true);
            // Assignem el node no visitat amb menor distància al node actual
            actual = trobaVertexDistMin(headquarters);
        }
        return reconstrueix(inici, fin, headquarters);
    }
}
