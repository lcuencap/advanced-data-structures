package terror;

public class Point {

    public float x;
    public float y;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }
    public void setX(float val) {
        this.x = val;
    }
    public void setY(float val) {
        this.y = val;
    }
    public float getX() {
        return this.x;
    }
    public float getY() {
        return this.y;
    }

    public static Point[] getMaxAndMin(Point p1, Point p2) {
        //La posició 0 es per el minim de X i Y.
        //La posició 1 es per el maxim de X i Y.
        Point[] aux = new Point[2];
        aux[0] = new Point(0,0);
        aux[1] = new Point(0,0);
        aux[0].setX(Math.min(p1.getX(), p2.getX()));
        aux[0].setY(Math.min(p1.getY(), p2.getY()));
        aux[1].setX(Math.max(p1.getX(), p2.getX()));
        aux[1].setY(Math.max(p1.getY(), p2.getY()));
        return aux;
    }
}
