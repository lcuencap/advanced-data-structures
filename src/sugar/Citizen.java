package sugar;

import java.util.Random;

public class Citizen {
    private final int id;
    private final String name;
    private final float sugarIndex;
    private final Job job;
    private boolean isLemon;

    private Citizen parent;
    private Citizen leftChild;
    private Citizen rightChild;

    public Citizen(int id, String name, float sugarIndex, Job job) {
        this.id = id;
        this.name = name;
        this.sugarIndex = sugarIndex;
        this.job = job;
        this.isLemon = false;
    }

    /**
     * Mètode recursiu que calcula l'alçada d'un subarbre
     * @param node
     * @return alçada del subarbre
     */

    private int getSubtreeHeight(Citizen node) {
        if (node == null) {
            return 0;
        }
        return 1 + Math.max(getSubtreeHeight(node.getLeftChild()), getSubtreeHeight(node.getRightChild()));
    }


    /**
     * Mètode que calcula el factor d'equilibri del node
     * @return factor d'equilibri del node
     */
    public int getBalanceFactor() {
        // Alçada del subarbre dret
        int right_height = (rightChild != null? getSubtreeHeight(rightChild) : 0);
        // Alçada del subarbre esquerre
        int left_height = (leftChild != null? getSubtreeHeight(leftChild) : 0);

        return right_height - left_height;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getSugarIndex() {
        return sugarIndex;
    }

    public Job getJob() {
        return job;
    }

    public boolean isLemon() {
        return isLemon;
    }

    public void setLemon(boolean lemon) {
        isLemon = lemon;
    }

    public Citizen getParent() {
        return parent;
    }

    public void setParent(Citizen parent) {
        this.parent = parent;
    }

    public Citizen getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(Citizen leftChild) {
        this.leftChild = leftChild;
    }

    public Citizen getRightChild() {
        return rightChild;
    }

    public void setRightChild(Citizen rightChild) {
        this.rightChild = rightChild;
    }

    public boolean tryToInfect() {
        Random random = new Random();
        int randomNumber = random.nextInt(0,101);
        if (randomNumber <= job.infectionChance) {
            isLemon = true;
        }
        return isLemon;
    }
}
