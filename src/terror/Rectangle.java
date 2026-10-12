
package terror;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class Rectangle implements Node {

    public static final int ORDER = 3;
    private List<Node> children;
    private Point[] vertex;

    public Rectangle(Node node) {
        children = new ArrayList<>();
        children.add(node);
        vertex = node.getVertex();
    }

    public static int getOrder() {return ORDER;}

    public Rectangle() {
        children = new ArrayList<>();
    }

    @Override
    public Point[] getVertex() {
        Point[] copy = new Point[vertex.length];
        for (int i = 0; i < vertex.length; i++) {
            copy[i] = new Point(vertex[i].x, vertex[i].y);
        }
        return copy;
    }


    public void insertMonster(Node node, Rectangle parent) {
        if (children.get(0).getClass() == Monster.class){
            children.add(node);
            updateBounds(node);
        }
        else {
            //Seguim buscant el node de rectangles fulla.
            Rectangle closest = (Rectangle) findClosestChild(node);
            closest.insertMonster(node, this);
        }
        // Mirem si hi ha overflow i si hi ha fem un resize, en cas que ens trobem en el root (parent == null) no farem
        if (children.size() > ORDER) {
            ArrayList<Node> tmpNodes = new ArrayList<>(children);
            //tmpNodes.add(node);
            reSize(this, parent, tmpNodes.toArray(new Node[0]));
        }
        // A la pujada recursiva reajustem la mida dels rectangles de cada nivell
        this.recalculateVertex();

    }

    private void reSize(Rectangle toResize, Rectangle parent, Node[] nodes) {
        Node maxNode1 = nodes[0];       //Pa pone algo
        Node maxNode2 = nodes[1];       //Pa pone algo
        float maxDist = Float.NEGATIVE_INFINITY;;
        float dist;

        //Busquem els dos nodes mes llunyans.
        for (int i = 0; i < nodes.length; i++) {
            for(int j = i + 1; j < nodes.length; j++) {
                // Calculem distancia entre punts.
                dist = calculateDistance(nodes[i], nodes[j]);
                if (maxDist < dist)  {
                    maxNode1 = nodes[i];
                    maxNode2 = nodes[j];
                    maxDist = dist;
                }
            }
        }

        //Dividim el rectangle i afegim els nodes.
        Rectangle r1 = new Rectangle(maxNode1);
        Rectangle r2 = new Rectangle(maxNode2);
        //Els afegim a una llista temporal per poderlos afegir amb la funcio de bestNodeToAdd.
        ArrayList <Rectangle> tempParent = new ArrayList<>();
        tempParent.add(r1);
        tempParent.add(r2);

        // Afegim els nodes restants al millor rectangle dels creats.
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i] != maxNode1 && nodes[i] != maxNode2) {
                Rectangle bestNode = bestNodeToAdd(tempParent, nodes[i]);
                bestNode.addChild(nodes[i]);
            }
        }

        r1.recalculateVertex();
        r2.recalculateVertex();

        //Afegim els nous rectangles als nodes corresponents.
        if (parent != null) {
            parent.removeChild(toResize);
            parent.addChild(r1);
            parent.addChild(r2);
        }
        else {
            toResize.children.clear();
            toResize.addChild(r1);
            toResize.addChild(r2);
        }
    }

    private Rectangle bestNodeToAdd(ArrayList<Rectangle> rectangles, Node toAdd) {
        float minSize = Float.MAX_VALUE;
        Rectangle bestRectangle = rectangles.get(0);

        for (Rectangle auxRectangle : rectangles) {
            float auxSize = auxRectangle.calculateNewAreaSize(toAdd);
            if (auxSize < minSize) {
                bestRectangle = auxRectangle;
                minSize = auxSize;
            }
        }
        return bestRectangle;
    }

    public void removeChild(Node toRemove){
        // Recalculem els vertex limits del rectangle
        float minX = Float.MAX_VALUE;
        float minY = Float.MAX_VALUE;
        float maxX = Float.NEGATIVE_INFINITY;
        float maxY = Float.NEGATIVE_INFINITY;
        float minPos;
        float maxPos;

        for (Node aux : children) {
            // Mirem cordenades X
            minPos = Math.min(aux.getVertex()[0].x, aux.getVertex()[1].x);
            maxPos = Math.max(aux.getVertex()[0].x, aux.getVertex()[1].x);
            if (minPos < minX) {
                minX = minPos;
            }
            if (maxPos > maxX) {
                maxX = maxPos;
            }

            // Mirem coordenades Y
            minPos = Math.min(aux.getVertex()[0].y, aux.getVertex()[1].y);
            maxPos = Math.max(aux.getVertex()[0].y, aux.getVertex()[1].y);
            if (minPos < minY) {
                minY = minPos;
            }
            if (maxPos > maxY) {
                maxY = maxPos;
            }
        }

        vertex[0].x = minX;
        vertex[1].x = maxX;
        vertex[0].y = minY;
        vertex[1].y = maxY;

        children.remove(toRemove);
    }

    public void recalculateVertex() {
        // Recalculem els vertex limits del rectangle
        float minX = Float.MAX_VALUE;
        float minY = Float.MAX_VALUE;
        float maxX = Float.NEGATIVE_INFINITY;
        float maxY = Float.NEGATIVE_INFINITY; // Float.MIN_VALUE seteja valor més petit POSITIU
        float minPos;
        float maxPos;

        for (Node aux : children) {
            // Mirem cordenades X
            minPos = Math.min(aux.getVertex()[0].x, aux.getVertex()[1].x);
            maxPos = Math.max(aux.getVertex()[0].x, aux.getVertex()[1].x);
            if (minPos < minX) {
                minX = minPos;
            }
            if (maxPos > maxX) {
                maxX = maxPos;
            }

            // Mirem coordenades Y
            minPos = Math.min(aux.getVertex()[0].y, aux.getVertex()[1].y);
            maxPos = Math.max(aux.getVertex()[0].y, aux.getVertex()[1].y);
            if (minPos < minY) {
                minY = minPos;
            }
            if (maxPos > maxY) {
                maxY = maxPos;
            }
        }
        vertex[0].x = minX;
        vertex[1].x = maxX;
        vertex[0].y = minY;
        vertex[1].y = maxY;
    }

    public float calculateNewAreaSize(Node node) {
        Point[] nodeVertex = node.getVertex();
        float posX1 = Math.min(vertex[0].x, nodeVertex[0].x);
        float posY1 = Math.min(vertex[0].y, nodeVertex[0].y);
        float posX2 = Math.max(vertex[1].x, nodeVertex[1].x);
        float posY2 = Math.max(vertex[1].y, nodeVertex[1].y);
        return Math.abs(posX1 - posX2) * 2 + Math.abs(posY1 - posY2) * 2;
    }



    public void addChild(Node toAdd) {
        children.add(toAdd);
        if (children.size() == 1) {
            vertex[0].x = toAdd.getVertex()[0].x;
            vertex[1].x = toAdd.getVertex()[1].x;
            vertex[0].y = toAdd.getVertex()[0].y;
            vertex[1].y = toAdd.getVertex()[1].y;
        }
        else {
            updateBounds(toAdd);
        }
    }


    private Node findClosestChild(Node node) {
        float minExpansion = Float.MAX_VALUE;
        Rectangle closest = null;

        for (Node child: children) {
            float originalPerimeter = calculatePerimeter(child);
            float newPerimeter = calculatePerimeter(child, node);

            float expansion = newPerimeter - originalPerimeter;

            if (expansion < minExpansion) {
                minExpansion = expansion;
                closest = (Rectangle) child;
            }
        }

        return closest;
    }

    private float calculateDistance(Node node1, Node node2) {
        Point center1 = getCenter(node1.getVertex());
        Point center2 = getCenter(node2.getVertex());
        //Es calcula la distància Euclidiana entre els dos centres dels nodes √((X1 – X2)^2 + (Y1 – Y2)^2).
        return (float) Math.sqrt(Math.pow(center1.x - center2.x, 2) + Math.pow(center1.y - center2.y, 2));
    }

    private Point getCenter(Point[] vertex) {
        float x = (vertex[0].x + vertex[1].x) / 2;
        float y = (vertex[0].y + vertex[1].y) / 2;
        return new Point(x, y);
    }

    private void updateBounds(Node node) {
        Point[] nodeVertex = node.getVertex();

        //S'actualitza els límits del rectangle tenint en compte els del node afegit.
        this.vertex[0].x = Math.min(this.vertex[0].x, nodeVertex[0].x);
        this.vertex[0].y = Math.min(this.vertex[0].y, nodeVertex[0].y);
        this.vertex[1].x = Math.max(this.vertex[1].x, nodeVertex[1].x);
        this.vertex[1].y = Math.max(this.vertex[1].y, nodeVertex[1].y);
    }

    public float calculatePerimeter (Node rectangle) {
        Point[] rectangleVertex = rectangle.getVertex();

        return Math.abs(rectangleVertex[0].x - rectangleVertex[1].x) * 2 +
                Math.abs(rectangleVertex[0].y - rectangleVertex[1].y) * 2;
    }

    public float calculatePerimeter (Node rectangle, Node node) {
        Point[] nodeVertex = node.getVertex();
        Point[] rectangleVertex = rectangle.getVertex();

        float minX = Math.min(rectangleVertex[0].x, nodeVertex[0].x);
        float minY = Math.min(rectangleVertex[0].y, nodeVertex[0].y);
        float maxX = Math.max(rectangleVertex[1].x, nodeVertex[1].x);
        float maxY = Math.max(rectangleVertex[1].y, nodeVertex[1].y);

        return Math.abs(minX - maxX) * 2 + Math.abs(minY - maxY) * 2;
    }

    public List<Node> getChildren() {
        return new ArrayList<>(this.children);
    }

    @Override
    public float minDistanceTo(Point queryPoint) {
        float minX = vertex[0].getX();
        float maxX = vertex[1].getX();
        float minY = vertex[0].getY();
        float maxY = vertex[1].getY();

        float dx = 0;
        float dy = 0;

        if (queryPoint.getX() < minX) {
            dx = minX - queryPoint.getX();
        } else if (queryPoint.getX() > maxX) {
            dx = queryPoint.getX() - maxX;
        }

        if (queryPoint.getY() < minY) {
            dy = minY - queryPoint.getY();
        } else if (queryPoint.getY() > maxY) {
            dy = queryPoint.getY() - maxY;
        }

        return (float) Math.sqrt(dx * dx + dy * dy);
    }


    public boolean contains(int x, int y) {
        return false;
    }

    public int getChildNum() {return children.size();}

    public void removeMonster(Point coordinates, Rectangle parent, Rectangle root) {
        if (children.get(0).getClass() == Monster.class) {
            children.removeIf(child -> child.isOverlap(coordinates));
            recalculateVertex();
        } else {
            for (Node child: children) {
                if (child.isOverlap(coordinates)) {
                    Rectangle overlap = (Rectangle) child;
                    overlap.removeMonster(coordinates, this, root);
                }
            }

            if (this.children.size() < ORDER * 0.3f) {
                for (Node child: this.children) {
                    root.insertMonster(child, null);
                }
                parent.children.remove(this);
                parent.recalculateVertex();
            }
        }
    }

    public boolean isOverlap(Point coordinates) {
        return coordinates.x >= vertex[0].x && coordinates.x <= vertex[1].x &&
                coordinates.y >= vertex[0].y && coordinates.y <= vertex[1].y;
    }




}
