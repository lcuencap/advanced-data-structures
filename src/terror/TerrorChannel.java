package terror;

import java.util.ArrayList;
import java.util.List;

public class TerrorChannel {

    Rectangle root;

    public TerrorChannel(Rectangle root) {
        this.root = root;
    }

    public void insertMonster(Node node) {
        root.insertMonster(node, null);
    }
    public void zonaSegura(Point point, int k) {
        RTreeKnn solver = new RTreeKnn(root, point, k);
        List<Monster> kNearestMonsters = solver.kNearestNeighbors();
        System.out.println("El nivell mitjà de perillositat és: "+ solver.averageDangerLevel +"\n" +
                "Els "+k+" monstres desconeguts més propers al punt són, en ordre de proximitat:\n");
        for (Monster monster : kNearestMonsters) {
            System.out.println("\t* "+monster.getName()+" ("+monster.getCoordinates().x+","+monster.getCoordinates().y+"): "+monster.getDangerLevel()+", desconegut, "+monster.getSize()+"m\n");
        }
    }

    public void removeMonster(float[] coordinates) {
        Point point = new Point(coordinates[0], coordinates[1]);
        root.removeMonster(point, null, root);
    }

    public void searchMonsters(float minLat, float minLong, float maxLat, float maxLong) {
        ArrayList<Monster> monsters = new ArrayList<>();
        showMonstersInArea(root, minLat, minLong, maxLat, maxLong, monsters);
        System.out.println();
        System.out.println("S'han trobat " + monsters.size() + " monstres en aquesta àrea:");
        System.out.println();
        for (Monster monster : monsters) {
            System.out.printf("\t* %s (%f, %f): %s, %s, %.1fm\n",   monster.getName(), monster.getCoordinates().getX(),
                                                                monster.getCoordinates().getY(), monster.getDangerLevel(),
                                                                (monster.isKnown() ? "desconegut" : "conegut"),
                                                                monster.getSize());
        }

        System.out.println();

    }
    private void showMonstersInArea(Node node, float minLat, float minLong, float maxLat, float maxLong, ArrayList<Monster> monsterList) {
        ArrayList<Node> childrens = new ArrayList<>(node.getChildren());
        for (Node child : childrens) {
            if (isInTheArea(child, minLat, minLong, maxLat, maxLong)) {
                if (child.getClass() == Monster.class) {
                    monsterList.add((Monster) child);
                }
                else {
                    showMonstersInArea(child, minLat, minLong, maxLat, maxLong, monsterList);
                }
            }
        }
    }

    private boolean isInTheArea(Node node, float minLat, float minLong, float maxLat, float maxLong) {
        boolean foundX = false;
        boolean foundY = false;

        float minX = Math.min(node.getVertex()[0].getX(), node.getVertex()[1].getX());
        float maxX = Math.max(node.getVertex()[0].getX(), node.getVertex()[1].getX());
        float minY = Math.min(node.getVertex()[0].getY(), node.getVertex()[1].getY());
        float maxY = Math.max(node.getVertex()[0].getY(), node.getVertex()[1].getY());

        // Mirem el cas en que el rectangle que volem mirar es troba dins l'area del rectangle de cerca. També es
        // contempla el cas en que hi hagi mínim un vertex dins el rectangle.
        Point[] vertexs = node.getVertex();
        for (Point vertex : vertexs) {
            if (vertex.getX() >= minLong && vertex.getX() <= maxLong) {
                foundX = true;
            }
            if (vertex.getY() >= minLat && vertex.getY() <= maxLat) {
                foundY = true;
            }
        }
        if (foundX && foundY) {
            return true;
        }
        // Cas en el que volem mirar si el rectangle de cerca es troba dins del rectangle a mirar.
        else {
            if (minLong >= minX && minLong <= maxX) {
                foundX = true;
            }
            if (maxLong >= minX && maxLong <= maxX) {
                foundX = true;
            }
            if (minLat >= minY && minLat <= maxY) {
                foundY = true;
            }
            if (maxLat >= minY && maxLat <= maxY) {
                foundY = true;
            }

            if (foundX && foundY) {
                return true;
            }

        }
        return false;
    }
}
