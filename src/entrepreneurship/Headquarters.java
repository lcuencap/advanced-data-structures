package entrepreneurship;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa i emmagatzema la informació de les seus, que són els vertex del graf.
 */
public class Headquarters {

    private final int ID;
    private final String company;
    private final String product;
    private final Material material;
    private final List<Route> suppliers;
    private final List<Route> clients;
    private boolean visited;
    private int dijkstra_distance;
    private int dijkstra_parent;

    public Headquarters(int ID, String product, String company, Material material) {
        this.ID = ID;
        this.product = product;
        this.company = company;
        this.material = material;
        this.suppliers = new ArrayList<>(); // es podria modificar perquè només s'inicialitzi en afegir una ruta.
        this.clients = new ArrayList<>();
        this.visited = false;
        dijkstra_distance = Integer.MAX_VALUE;
        dijkstra_parent = -1;
    }

    public String getProduct(){return product;}
    public int getDijkstraDistance() {
        return dijkstra_distance;
    }

    public int getDijkstraParent() {
        return dijkstra_parent;
    }

    public void setDijkstraDistance(int distance) {
        dijkstra_distance = distance;
    }

    public void setDijkstraParent(int parent) {
        dijkstra_parent = parent;
    }

    public int getID() {
        return ID;
    }

    public String getCompany() {
        return company;
    }

    public Material getMaterial() {
        return material;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    /**
     * Afegeix una ruta que arriba a la seu.
     * @param route ruta a afegir.
     */
    public void addSupplier(Route route) {
        this.suppliers.add(route);
    }

    public void addClient(Route route) {
        this.clients.add(route);
    }

    public ArrayList<Route> getSuppliers() { return (ArrayList<Route>) suppliers;}
    public ArrayList<Route> getClients() {return (ArrayList<Route>) clients;}

    public boolean isEnd() {return clients.isEmpty();}
    public boolean isBeginning() {
        return suppliers.isEmpty();
    }
}
