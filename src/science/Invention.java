package science;

import java.awt.*;

public class Invention {
    String name;
    Boolean functional;
    String color;

    public Invention(String name, Boolean functional, String color) {
        this.name = name;
        this.functional = functional;
        this.color = color;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public String getName() {
        return name;
    }

    public Boolean getStatus() {
        return functional;
    }

    public void setStatus(boolean b) {
        functional = b;
    }

    public String getColor() {
        return color;
    }
}
