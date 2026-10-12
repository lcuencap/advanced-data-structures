package terror;

import java.awt.*;
import java.util.List;

public class Monster implements Node {

    private int id;
    private String name;
    private String dangerLevel;
    private boolean isKnown;
    private float size;
    private Point coordinates;
    private java.awt.Rectangle bounds;


    public Monster(int id, String name, String dangerLevel, boolean isKnown, float size, Point coordinates) {
        this.id = id;
        this.name = name;
        this.dangerLevel = dangerLevel;
        this.isKnown = isKnown;
        this.size = size;
        this.coordinates = coordinates;
        this.bounds = new java.awt.Rectangle((int) (coordinates.x - (int) (size / 2)), (int) (coordinates.y - (int) (size / 2)), (int) size, (int) size);
    }

    @Override
    public Point[] getVertex() {
        Point[] vertex = new Point[2];
        vertex[0] = new Point(coordinates.getX(), coordinates.getY());
        vertex[1] = new Point(coordinates.getX(), coordinates.getY());
        return vertex;
    }

    @Override
    public List<Node> getChildren() {
        return null;
    }

    @Override
    public float minDistanceTo(Point queryPoint) {
        float dx = this.coordinates.x - queryPoint.x;
        float dy = this.coordinates.y - queryPoint.y;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    @Override
    public boolean isOverlap(Point coordinates) {
        return coordinates.x >= this.coordinates.x && coordinates.x <= this.coordinates.x &&
                coordinates.y >= this.coordinates.y && coordinates.y <= this.coordinates.y;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDangerLevel() {
        return dangerLevel;
    }

    public boolean isKnown() {
        return isKnown;
    }

    public float getSize() {
        return size;
    }

    public Point getCoordinates() {
        return coordinates;
    }
    public java.awt.Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(java.awt.Rectangle bounds) {
        this.bounds = bounds;
    }

}
