package terror;

import java.awt.*;
import java.util.List;

public interface Node {

    Point[] getVertex();
    public List<Node> getChildren();
    public boolean isOverlap(Point coordinates);

    float minDistanceTo(Point queryPoint);
}
