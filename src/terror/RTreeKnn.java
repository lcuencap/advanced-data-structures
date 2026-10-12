package terror;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class RTreeKnn {
    Rectangle root;
    Point point;
    int k;
    String averageDangerLevel;

    public RTreeKnn(Rectangle root, Point point, int k) {
        if (k <= 0) {
            throw new IllegalArgumentException("k must be positive");
        }
        this.root = root;
        this.point = point;
        this.k = k;
    }

    public List<Monster> kNearestNeighbors() {
        // Keep the farthest selected monster at the head so a closer one can replace it.
        PriorityQueue<MonsterDistance> nearest = new PriorityQueue<>(
                k, (a, b) -> Float.compare(b.distance, a.distance));
        findNearestNeighbors(root, point, nearest);

        if (nearest.isEmpty()) {
            averageDangerLevel = "N/A";
            return List.of();
        }

        int sumRed = 0;
        int sumGreen = 0;
        int sumBlue = 0;
        int count = nearest.size();
        List<Monster> reverseOrder = new ArrayList<>();
        while (!nearest.isEmpty()) {
            Monster monster = nearest.poll().monster;
            Color color = Color.decode(monster.getDangerLevel());
            sumRed += color.getRed();
            sumGreen += color.getGreen();
            sumBlue += color.getBlue();
            reverseOrder.add(monster);
        }
        averageDangerLevel = String.format("#%02X%02X%02X",
                sumRed / count, sumGreen / count, sumBlue / count);

        List<Monster> result = new ArrayList<>();
        for (int i = reverseOrder.size() - 1; i >= 0; i--) {
            result.add(reverseOrder.get(i));
        }
        return result;
    }

    private void findNearestNeighbors(Node node, Point query, PriorityQueue<MonsterDistance> nearest) {
        if (node == null) {
            return;
        }
        float distance = node.minDistanceTo(query);
        if (nearest.size() == k && distance > nearest.peek().distance) {
            return;
        }

        if (node instanceof Rectangle) {
            for (Node child : node.getChildren()) {
                findNearestNeighbors(child, query, nearest);
            }
        } else if (node instanceof Monster monster && !monster.isKnown()) {
            if (nearest.size() < k) {
                nearest.add(new MonsterDistance(monster, distance));
            } else if (distance < nearest.peek().distance) {
                nearest.poll();
                nearest.add(new MonsterDistance(monster, distance));
            }
        }
    }
}