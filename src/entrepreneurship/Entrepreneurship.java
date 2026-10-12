package entrepreneurship;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Entrepreneurship {
    private final Headquarters[] graph;
    private final String filename = "Graphs/aCityXXS.paed";

    public Entrepreneurship(Headquarters[] graph) {
        this.graph = graph;
    }

    public void showInteligenciaComercial(int headquarterId) {

        // Obtenim la seu que volem espiar per tenir la seva informació
        int pos = cercaSeu(this.graph, headquarterId);
        if (pos != -1) {
            Headquarters toSpy = graph[pos];
            String company = toSpy.getCompany();

            //Mostrem la seu que es vol espiar
            System.out.println();
            System.out.println(toSpy.getID() + " - " + toSpy.getProduct() + " (" + toSpy.getMaterial() + ") : " + company);

            List<List<Headquarters>> hqList = DFS.getVertexsByLevelFromCompany(graph.clone());


            //Mostrem les empreses
            System.out.println();
            System.out.println("Les seus de l'empresa \"" + company + "\" són:");
            System.out.println();

            // Recorrem la "matriu" formada i filtrem les seus que formen part de la companyia de la seu que volem espiar.
            for (List<Headquarters> list : hqList) {
                for (Headquarters element : list) {
                    if (element.getCompany().equals(company) && headquarterId != element.getID()) {
                        System.out.println(element.getID() + " - " + element.getProduct() + " (" + element.getMaterial() + ") : " + company);
                    }
                }
            }
            System.out.println();
        }
        else {
            System.out.println("La seu introduida no es troba registrada");
        }
    }

    public void findShortestPath(int id_inici, int id_fin) {
        int inici = binarySearch(0, graph.length, id_inici, graph.clone());
        int fin = binarySearch(0, graph.length, id_fin, graph.clone());
        System.out.println();
        try {
            ArrayList<Headquarters> cami = Dijkstra.dijkstra(graph, graph[inici], graph[fin]);
            System.out.println("El millor camí per a transportar " + graph[inici].getMaterial() + " de la seu " + graph[inici].getID() + " a la " + graph[fin].getID() + " triga " + graph[fin].getDijkstraDistance() + " minuts");
            System.out.println("Cal passar per les següents seus: ");
            Collections.reverse(cami);
            for(Headquarters hq: cami) {
                System.out.printf(String.valueOf(hq.getID()) + " ");
            }
            System.out.println();
        } catch (CamiInviableException e) {
            System.out.println(e.getMessage());
        }
    }

    public void showExecutionOrder () {
        System.out.println("L’ordre en el que executar la cadena de producció és el següent:");
        Toposort.topologicalSort(graph.clone());
    }

    public int getPos(int id) {
        int start = 0;
        int end = graph.length - 1;
        Headquarters[] graphCopy = this.graph.clone();
        return Entrepreneurship.binarySearch(start, end, id, graphCopy);
    }

    public static int cercaSeu(Headquarters[] graph, int valueToFind) {
        int pos;
        pos = binarySearch(0, graph.length- 1, valueToFind, graph);
        // En cas de que no hagi trobat el valor retorna -1;

        return pos;
    }

    public static int binarySearch(int start, int end, int valueToFind, Headquarters[] graph) {
        int mid = start + (end - start) / 2;
        int midId = graph[mid].getID();

        if (end > start && midId > valueToFind)  {
            mid = binarySearch(start, mid - 1, valueToFind, graph);
        } else if (end > start && midId < valueToFind)  {
            mid = binarySearch(mid + 1, end, valueToFind, graph);
        }
        else if (midId != valueToFind) {
            return -1;
        }

        return mid;
    }
    public static void initializeList (Headquarters[] headquarters) {
        for (Headquarters hq: headquarters) {
            hq.setVisited(false);
        }
    }

}

