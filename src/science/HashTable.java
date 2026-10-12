package science;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class HashTable<T> {
    private Class<T> classType;
    private List<T>[] values;
    private final float loadFactor = 0.75f;
    private final int synonymListMaxSize = 5;
    private int numElements;
    private int maxRehashAttempts;


    public HashTable(Class<T> t, int datasetSize) {
        this.classType = t;
        this.values = (List<T>[]) Array.newInstance(List.class, (int) (0.2 * datasetSize));
        for (int i = 0; i < values.length; i++) {
            values[i] = new ArrayList<>();
        }
        this.numElements = 0;
        this.maxRehashAttempts = determineMaxRehashAttempts(values.length);
    }

    private int determineMaxRehashAttempts(int table_size) {
        // El nombre máxim d'intents de rehash serà proporcional a la mida de la taula
        return (int) Math.round(table_size * 0.2) + 10;
    }



    public void add(T entry) {
        // Mirem si cal fer resize
        if ((float) numElements / values.length > loadFactor) {
            this.resize();
        }

        // Calculem el hash per les dues funcions auxiliars
        int hash = hash(entry.toString());

        if (values[hash].size() < synonymListMaxSize) {
            /*
            if (values[hash].isEmpty()) {
                numElements++;
            }
             */
            values[hash].add(entry);
            if (values[hash].size() == maxRehashAttempts) {
                numElements++;
            }
        } else {
            int hash2 = secondHash(entry.toString());

            // En cas de col·lisió apliquem double hashing fins a donar amb casella lliure
            int rehashAttempts = 0;
            while (values[hash].size() >= synonymListMaxSize && rehashAttempts < maxRehashAttempts) {
                hash = (hash + hash2 * rehashAttempts++) % values.length;
            }

            if (rehashAttempts == maxRehashAttempts) {
                resize();
                add(entry);
            } else {
                /*
                if (values[hash].isEmpty()) {
                    numElements++;
                }

                 */
                values[hash].add(entry);
                if (values[hash].size() == maxRehashAttempts) {
                    numElements++;
                }
            }
        }
    }


    private void insertIntoSynonymsList(int hash, T entry) {
        values[hash].add(entry);
        numElements++;
    }

    public void resize() {
        int new_size = (int) (1.2 * values.length) + 1;
        List<T>[] aux = this.values;
        this.values= (List<T>[]) Array.newInstance(List.class, new_size);

        // Inicialitzem nova taula
        for (int i = 0; i < new_size; i++) {
            this.values[i] = new ArrayList<>();
        }
        numElements = 0;
        // Copiem valors a la nova taula
        for (int i = 0; i < aux.length; i++) {
            for(T element : aux[i]) {
                add(element);
            }
        }

        System.out.println();

        this.maxRehashAttempts = determineMaxRehashAttempts(new_size);

    }

    private int hash(String toHash) {
        int m = values.length;
        int p = 31;

        int hashValue = 0;
        int pow = 1;

        for (int i = 0; i < toHash.length(); i++) {
            hashValue = (hashValue +  (toHash.charAt(i)) * pow) % m;
            pow = (pow * p) % m;
        }

        return hashValue % m;
    }

    private int secondHash(String toHash) {
        int m = values.length;
        int p = 37;

        int hashValue = 0;
        int pow = 1;

        for (int i = 0; i < toHash.length(); i++) {
            hashValue = (hashValue + toHash.charAt(i) * pow) % m;
            pow = (pow * p) % m;
        }

        return hashValue == 0 ? 1 : hashValue;
    }

    public T get(String name) {
        int hash1 = hash(name);
        int hash2 = secondHash(name);

        int i = 0;
        while (i < maxRehashAttempts) {
            for (T entry : values[hash1]) {
                if (entry.toString().equals(name)) {
                    return entry;
                }
            }
            hash1 = (hash1 + hash2 * (i++)) % values.length;
        }

        return null;
    }

    public int getHashValue(String name) {
        int hash1 = hash(name);
        int hash2 = secondHash(name);

        int i = 0;
        while (i < maxRehashAttempts) {
            for (T entry : values[hash1]) {
                if (entry.toString().equals(name)) {
                    return hash1;
                }
            }
            hash1 = (hash1 + hash2 * (i++)) % values.length;
        }

        return -1;
    }

    public List<T> getAll() {
        List<T> allElements = new ArrayList<>();
        for (List<T> element : values) {
            allElements.addAll(element);
        }
        return allElements;
    }
    public int getSize() {
        return numElements;
    }

    public void remove(String name) {
        int hash = getHashValue(name);
        if (hash > -1) {
            for (int i = 0; i < maxRehashAttempts; i++) {
                if (values[hash].get(i).toString().equals(name)) {
                    values[hash].remove(i);
                    if (values[hash].isEmpty()) {
                        if (numElements > 0) {
                            numElements--;
                            if ((float) numElements / values.length < 0.5) {
                                resizeSmall();
                            }
                        }
                    }
                    System.out.println("\nL'invent '" + name + "' ha estat destruït, és irrecuperable.\n");
                    return;
                }
            }
        }
        else {
            System.out.println("\nInvent no trobat\n");
        }
    }

    public void resizeSmall() {
        int new_size = (int) (0.8 * values.length) - 1;
        List<T>[] aux = this.values;
        this.values= (List<T>[]) Array.newInstance(List.class, new_size);

        // Inicialitzem nova taula
        for (int i = 0; i < new_size; i++) {
            this.values[i] = new ArrayList<>();
        }
        numElements = 0;
        // Copiem valors a la nova taula
        for (int i = 0; i < aux.length; i++) {
            for(T element : aux[i]) {
                add(element);
            }
        }

        this.maxRehashAttempts = determineMaxRehashAttempts(new_size);
    }
}
