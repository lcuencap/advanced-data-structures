package entrepreneurship;

import entrepreneurship.Headquarters;
import entrepreneurship.Material;

public class Route {
    private final Headquarters adjacentHQ;
    private final int shippingTime;
    private final Material restriction;

    public Route(Headquarters adjacentHQ, int shippingTime, Material restriction) {
        this.adjacentHQ = adjacentHQ;
        this.shippingTime = shippingTime;
        this.restriction = restriction;
    }

    public Headquarters getAdjacentHQ() {
        return adjacentHQ;
    }

    public int getShippingTime() {
        return shippingTime;
    }

    public Material getRestriction() {
        return restriction;
    }
}
