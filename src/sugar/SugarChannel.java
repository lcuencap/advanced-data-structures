package sugar;

import java.util.ArrayList;

public class SugarChannel {

    private static Citizen root;

    public SugarChannel(Citizen root) {
        this.root = root;

        if (this.root != null) {
            rebalanceTree();
        }
    }

    public void addCitizen(Citizen currentCitizen, Citizen newCitizen) {
        if (newCitizen.getSugarIndex() <= currentCitizen.getSugarIndex()) {
            if (currentCitizen.getLeftChild() != null) {
                addCitizen(currentCitizen.getLeftChild(), newCitizen);
            } else {
                currentCitizen.setLeftChild(newCitizen);
                newCitizen.setParent(currentCitizen);
            }
        } else {
            if (currentCitizen.getRightChild() != null) {
                addCitizen(currentCitizen.getRightChild(), newCitizen);
            } else {
                currentCitizen.setRightChild(newCitizen);
                newCitizen.setParent(currentCitizen);
            }
        }

        if (root != null) {
            rebalanceTree();
        }
    }

    /**
     * Mètode que implementa la rotació cap a la dreta
     * @param A arrel del subarbre al que s'aplica la rotació
     */
    private static Citizen rightRotate(Citizen A) {
        // Referenciem nodes
        Citizen P = A.getParent();
        Citizen B = A.getLeftChild();

        A.setLeftChild(B.getRightChild());
        if(B.getRightChild() != null) {
            B.getRightChild().setParent(A);
        }
        B.setRightChild(A);
        A.setParent(B);
        B.setParent(P);
        // Actualitzem referència del node pare al node arrel del subarbre reorganitzat
        if(P != null) {
            if(P.getLeftChild() == A) {
                P.setLeftChild(B);
            }
            else {
                P.setRightChild(B);
            }
        }
        else {
            root = B;
        }
        return B;
    }

    private static Citizen leftRotate(Citizen A) {
        // Referenciem nodes
        Citizen P = A.getParent();
        Citizen B = A.getRightChild();

        A.setRightChild(B.getLeftChild());
        if(B.getLeftChild() != null) {
            B.getLeftChild().setParent(A);
        }
        B.setLeftChild(A);
        A.setParent(B);
        B.setParent(P);
        // Actualitzem referència del node pare al node arrel del subarbre reorganitzat
        if(P != null) {
            if(P.getLeftChild() == A) {
                P.setLeftChild(B);
            }
            else {
                P.setRightChild(B);
            }
        }
        else {
            root = B;
        }
        return B;
    }

    public static void rebalanceTree() {
        boolean balanced  = false;
        while (!balanced) {
            Citizen A = searchUnbalance(root);
            if (A != null) {
                executeRotation(A);
            }
            else {
                balanced = true;
            }
        }
    }
    public static Citizen searchUnbalance(Citizen node) {
        Citizen value;
        if (node.getBalanceFactor() > 1 || node.getBalanceFactor() < -1) {
            return node;
        }
        if (node.getLeftChild() != null) {
            value = searchUnbalance(node.getLeftChild());
            if (value != null) {
                return value;
            }
        }
        if (node.getRightChild() != null) {
            value = searchUnbalance(node.getRightChild());
            if (value != null) {
                return value;
            }
        }

        return null;
    }

    private static void executeRotation(Citizen A) {
        int balance = A.getBalanceFactor();
        if (balance >= 2) {
            if (A.getRightChild().getBalanceFactor() >= 0) {
                leftRotate(A);
            }
            else {
                rightRotate(A.getRightChild());
                leftRotate(A);
            }
        }
        else if (balance <= -2) {
            if (A.getLeftChild().getBalanceFactor() <= 0) {
                rightRotate(A);
            }
            else {
                leftRotate(A.getLeftChild());
                rightRotate(A);
            }
        }
    }

    private void moveRightToLeft(Citizen toRemove) {
        if (toRemove.getRightChild() != null) {
            Citizen aux = toRemove.getLeftChild();
            while (aux.getRightChild() != null) {
                aux = aux.getRightChild();
            }
            aux.setRightChild(toRemove.getRightChild());
            toRemove.getRightChild().setParent(aux);
        }
    }
    private void removeLeftBranchSide (Citizen parent, Citizen toRemove) {
        parent.setLeftChild(null);
        // Mirem si el node a eliminar te fills per la esquerra
        if (toRemove.getLeftChild() != null) {
            parent.setLeftChild(toRemove.getLeftChild());
            // En cas de que també tingui fills per la dreta movem tota la branca de la dreta al final de la
            // branca esquerra per la dreta del node a eliminar.
            moveRightToLeft(toRemove);
            toRemove.getLeftChild().setParent(parent);
            // Mirem si el node a eliminar te fills per la dreta i hem vist que no tenia per la esquerra.
        } else if (toRemove.getRightChild() != null) {
            parent.setLeftChild(toRemove.getRightChild());
            toRemove.getRightChild().setParent(parent);
            // Com que ja sabem que no te fills per la esquerra sabem que es un node de fill unic.
        }
    }

    private void removeRightBranchSide (Citizen parent, Citizen toRemove) {
        parent.setRightChild(null);

        if (toRemove.getLeftChild() != null) {
            parent.setRightChild(toRemove.getLeftChild());
            moveRightToLeft(toRemove);
            toRemove.getLeftChild().setParent(parent);
        } else if (toRemove.getRightChild() != null) {
            parent.setRightChild(toRemove.getRightChild());
            toRemove.getRightChild().setParent(parent);
        }
    }

    public void removeCitizen(int idCitizen) {
        //Citizen root = this.root; //citizenTree.get(0);
        Citizen removeElement = findById(root, idCitizen);
        String name = removeElement.getName();
        Citizen aux;
        Citizen aux2;
        if (removeElement == root) {
            if (root.getLeftChild() != null) {
                moveRightToLeft(root);
                root = root.getLeftChild();
                root.setParent(null);
            } else if (root.getRightChild() != null) {
                root = root.getRightChild();
                root.setParent(null);
            } else {
                root = null;
            }
        }
        else {
            aux = removeElement.getParent();
            if (aux.getRightChild() == removeElement) {
                // Ve de la branca dreta.
                removeRightBranchSide(aux, removeElement);
            }
            else {
                removeLeftBranchSide(aux, removeElement);
            }
        }
        rebalanceTree();
        System.out.println();
        System.out.println("L’habitant " + name + " ha estat consumit.");
    }

    /**
     * Algorisme per fer una cerca d'arbre binari de manera iterativa. En cas de no trobar l'element que es busca
     * retorna el valor de null.
     * Busca l'id introduit en l'arbre que conte la clase SugarChannel.
     * Aquesta cerca assumeix que el parametre de distribució de l'arbre es el identificador.
     * @param id identificador del Citizen que es vol buscar.
     * @return retorna la instancia del ciutada que es volia trobar a partir del seu ID. En cas de no trobar el ciutada
     * retorna null.
     */
    private Citizen findById(Citizen node, int id) {
        Citizen value;
        if (node.getId() == id) {
            return node;
        }
        if (node.getLeftChild() != null) {
            value = findById(node.getLeftChild(), id);
            if (value != null) {
                return value;
            }
        }
        if (node.getRightChild() != null) {
            value = findById(node.getRightChild(), id);
            if (value != null) {
                return value;
            }
        }
        return null;
    }

    public void showTreeRepresentation () {
        TreeRepresentation.printTree(root);
    }

    private void extendInfection(Citizen node) {
        boolean infected = node.tryToInfect();
        if (infected) {
            System.out.println("\t* " + node.getName() + " (" + node.getId() + ", " + node.getJob().label + "): " + node.getJob().infectionChance + "%");
            if (node.getLeftChild() != null) {
                extendInfection(node.getLeftChild());
            }
            if (node.getRightChild() != null) {
                extendInfection(node.getRightChild());
            }
        }
    }

    public void infectionSimulation(String name) {
        Citizen node = searchByName(root, name);
        if (node != null) {
            System.out.println("\nS'han infectat els següents habitatnts:\n");
            node.setLemon(true);
            extendInfection(node);
            System.out.println("");
        }
        else {
            System.out.println("\nPacient zero no trobat\n");
        }
    }

    /**
     * Métode que comptabilitza el nombre d'habitants infectats i els desinfecta si ho estan.
     * A cada crida recursiva avaluem primerement el nombre d'infectats al subarbre esquerre del node.
     * Una vegada vegada recorregut el subarbre esquerre i comptabilitzats el nombre d'infectats,
     * avaluem el nombre d'infectats al subarbre dret partint d'aquest valor
     * @param node Habitant que s'avalua a cada crida recursiva
     * @return total d'infectats
     */
    public int postorderDisinfect(Citizen node) {
        if (node == null) {
            return 0;
        }
        int total = 0;
        if (node.isLemon()) {
            node.setLemon(false);
            total++;
        }
        total += postorderDisinfect(node.getLeftChild());
        total += postorderDisinfect(node.getRightChild());

        return total;
    }

    public int disinfectionSimulation() {
        int n_infected = postorderDisinfect(root);
        return n_infected;
    }

    private Citizen searchByName(Citizen node, String name) {
        Citizen value;
        if (node.getName().equals(name)) {
            return node;
        }
        if (node.getLeftChild() != null) {
            value = searchByName(node.getLeftChild(), name);
            if (value != null) {
                return value;
            }
        }
        if (node.getRightChild() != null) {
            value = searchByName(node.getRightChild(), name);
            if (value != null) {
                return value;
            }
        }
        return null;
    }

    public Citizen getRoot() {
        return root;
    }

    public void infiltratorSearch(float sugarIndex, String base) {
        InfiltratorSearch infiltratorSearch = new InfiltratorSearch(root);

        infiltratorSearch.searchInfiltrators(sugarIndex, base);
    }
    public void cloneCapture (float minIndex, float maxIndex) {
        CloneCapture cloneCapture = new CloneCapture(minIndex, maxIndex, getRoot());
        cloneCapture.captureClones();
    }
}
