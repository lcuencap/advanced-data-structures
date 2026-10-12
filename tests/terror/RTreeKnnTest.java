package terror;

import java.util.List;

public class RTreeKnnTest {
    private static Monster monster(int id, float x, boolean known, String color) {
        return new Monster(id, "monster-" + id, color, known, 1, new Point(x, 0));
    }

    public static void main(String[] args) {
        Monster far = monster(1, 10, false, "#FF0000");
        Monster near = monster(2, 1, false, "#00FF00");
        Monster middle = monster(3, 3, false, "#0000FF");
        Monster known = monster(4, 0, true, "#FFFFFF");
        Rectangle root = new Rectangle(far);
        root.addChild(near);
        root.addChild(middle);
        root.addChild(known);

        RTreeKnn one = new RTreeKnn(root, new Point(0, 0), 1);
        if (!one.kNearestNeighbors().equals(List.of(near)) || !"#00FF00".equals(one.averageDangerLevel)) {
            throw new AssertionError("k=1 must replace the first, farther candidate");
        }

        RTreeKnn two = new RTreeKnn(root, new Point(0, 0), 2);
        if (!two.kNearestNeighbors().equals(List.of(near, middle)) || !"#007F7F".equals(two.averageDangerLevel)) {
            throw new AssertionError("k=2 must return the closest unknown monsters in order");
        }

        RTreeKnn empty = new RTreeKnn(new Rectangle(known), new Point(0, 0), 1);
        if (!empty.kNearestNeighbors().isEmpty() || !"N/A".equals(empty.averageDangerLevel)) {
            throw new AssertionError("known monsters must be excluded safely");
        }
        System.out.println("RTreeKnn checks passed");
    }
}