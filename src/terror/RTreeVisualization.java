package terror;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;


public class RTreeVisualization extends JPanel {
    private TerrorChannel terrorChannel;
    private ZoomListener zoomListener;
    private double minLongitude, maxLongitude, minLatitude, maxLatitude;
    private Monster selectedMonster;
    private int offsetX = 0;
    private int offsetY = 0;

    public RTreeVisualization(TerrorChannel terrorChannel) {
        this.terrorChannel = terrorChannel;
        minLongitude = terrorChannel.root.getVertex()[0].x;
        minLatitude = terrorChannel.root.getVertex()[0].y;
        maxLongitude = terrorChannel.root.getVertex()[1].x;
        maxLatitude = terrorChannel.root.getVertex()[1].y;

        addMouseListener(new MonsterClickListener());
        zoomListener = new ZoomListener();
        addMouseWheelListener(zoomListener);
        addMouseMotionListener(new PanListener());

    }

    public void startVisualization() {
        JFrame frame = new JFrame("Visualització R-Tree");
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        frame.getContentPane().add(this, BorderLayout.CENTER);
        frame.setVisible(true);
    }


    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.translate(offsetX, offsetY);
        Rectangle r = new Rectangle(terrorChannel.root);
        //renderTree(r, g2d); // Opció per a visualitzar el root
        renderTree(terrorChannel.root, g2d);

        g2d.dispose();
    }

    private void renderTree(Node node, Graphics g) {
        if (node == null) return;

        for (Node child : node.getChildren()) {
            if (child instanceof Monster) {
                renderMonster((Monster) child, g);
            } else if (child instanceof Rectangle) {
                Rectangle rectangle = (Rectangle) child;
                renderRectangle(rectangle, g);
                renderTree(child, g);
            }
        }
    }


    private void renderMonster(Monster monster, Graphics g) {
        int x = calculateScreenX(monster.getCoordinates().getX());
        int y = calculateScreenY(monster.getCoordinates().getY());
        int size = (int) (monster.getSize() * 2 *  zoomListener.getScaleFactor()); // Apply zoom factor
        Color color = getColorForDangerLevel(monster.getDangerLevel());
        g.setColor(color);

        if (monster.isKnown()) {
            g.fillRect(x - size / 2, y - size / 2, size, size);
        } else {
            g.fillOval(x - size / 2, y - size / 2, size, size);
        }
        //if(monster.getId() == 7) System.out.println(monster.getBounds());
        java.awt.Rectangle monsterBounds = new java.awt.Rectangle(x - size / 2 + offsetX, y - size / 2 + offsetY, size, size);
        monster.setBounds(monsterBounds);
        //if(monster.getId() == 7) System.out.println(monster.getBounds());

    }


    private void renderRectangle(Rectangle rectangle, Graphics g) {
        Point[] vertex = rectangle.getVertex();

        int x1 = calculateScreenX(vertex[0].x);
        int y1 = calculateScreenY(vertex[0].y);
        int x2 = calculateScreenX(vertex[1].x);
        int y2 = calculateScreenY(vertex[1].y);

        // Necessitem que x1 sigui menor a x2
        if (x1 > x2) {
            int temp = x1;
            x1 = x2;
            x2 = temp;
        }
        // Necessitem que y1 sigui menor a y2
        if (y1 > y2) {
            int temp = y1;
            y1 = y2;
            y2 = temp;
        }

        int width = x2 - x1;
        int height = y2 - y1;

        long seed = rectangle.hashCode();

        Random random = new Random(seed);

        Color randomColor = getRandomColor(random);

        //g.setColor(Color.cyan);
        g.setColor(randomColor);


        g.fillRect(x1, y1, width, height);
        //g.drawRect(x1,y1,width, height);   // Si només volem que es mostri el contorn del rectangle
    }

    private Color getRandomColor(Random random) {
        int red = random.nextInt(255);
        int green = random.nextInt(255);
        int blue = random.nextInt(255);
        return new Color(red, green, blue);
    }


    /*
        int red = random.nextInt(128) + 128;
        int green = random.nextInt(128) + 128;
        int blue = random.nextInt(128) + 128;
     */
    private Color getColorForDangerLevel(String dangerLevel) {
        return Color.decode(dangerLevel);
    }

    private int calculateScreenX(double longitude) {
        double xRange = maxLongitude - minLongitude;
        double normalizedX = (longitude - minLongitude) / xRange;
        return (int) ((getWidth()-200) * normalizedX);
    }

    private int calculateScreenY(double latitude) {
        double yRange = maxLatitude - minLatitude;
        double normalizedY = (latitude - minLatitude) / yRange;
        return (int) ((getHeight()-200) * (1 - normalizedY));
    }

    private class MonsterClickListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);

            Monster monster = findMonsterAtPosition(e.getX(), e.getY());
            if (monster != null) {
                selectedMonster = monster;
                displayMonsterInformation(selectedMonster);
            } else {
                JOptionPane.showMessageDialog(RTreeVisualization.this, "No monster clicked!");
                selectedMonster = null;
            }
        }


        private Monster findMonsterAtPosition(int x, int y, Node node) {
            if (node instanceof Monster && ((Monster) node).getBounds().contains(x, y)) {
                return (Monster) node;
            } else if (node instanceof Rectangle) {
                for (Node child : ((Rectangle) node).getChildren()) {
                    Monster foundMonster = findMonsterAtPosition(x, y, child);
                    if (foundMonster != null) {
                        return foundMonster;
                    }
                }
            }
            return null;
        }

        private Monster findMonsterAtPosition(int x, int y) {
            return findMonsterAtPosition(x, y, terrorChannel.root);
        }
    }

    private void displayMonsterInformation(Monster monster) {
        StringBuilder message = new StringBuilder();
        message.append("ID: ").append(monster.getId()).append("\n");
        message.append("Name: ").append(monster.getName()).append("\n");
        message.append("Danger Level: ").append(monster.getDangerLevel()).append("\n");
        message.append("Known: ").append(monster.isKnown() ? "Yes" : "No").append("\n");
        message.append("Size: ").append(monster.getSize()).append("\n");
        message.append("Coordinates: ").append(monster.getCoordinates().getX()).append(", ").append(monster.getCoordinates().getY()).append("\n");
        JOptionPane.showMessageDialog(this, message.toString());
    }

    public class ZoomListener extends MouseAdapter {
        private double scaleFactor = 1.0;
        private int zoomCenterX, zoomCenterY;

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            int rotation = e.getWheelRotation();

            if (rotation < 0) {
                scaleFactor *= 1.1;
            } else {
                scaleFactor *= 0.9;
            }

            zoomCenterX = e.getX();
            zoomCenterY = e.getY();

            repaint();
        }
        public double getScaleFactor() {
            return scaleFactor;
        }
        private void repaint() {
            RTreeVisualization.this.repaint();
        }

    }

    private void updateOffset(int dx, int dy) {
        offsetX += dx;
        offsetY += dy;
        repaint();
    }

    private class PanListener extends MouseAdapter {
        private int lastX, lastY;
        private boolean isFirstClick = true;


        public void mouseDragged(MouseEvent e) {
            if (!isFirstClick) {
                int dx = e.getX() - lastX;
                int dy = e.getY() - lastY;

                updateOffset(dx, dy);

                repaint();
            }

            lastX = e.getX();
            lastY = e.getY();

            isFirstClick = false;
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            isFirstClick = true;
        }

    }
}

