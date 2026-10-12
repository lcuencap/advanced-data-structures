import entrepreneurship.Entrepreneurship;
import entrepreneurship.Headquarters;
import entrepreneurship.Material;
import entrepreneurship.Route;
import science.HashTable;
import science.Invention;
import science.ScienceChannel;
import sugar.Citizen;
import sugar.Job;
import sugar.SugarChannel;
import terror.Monster;
import terror.Point;
import terror.Rectangle;
import terror.TerrorChannel;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe encarregada de llegir i interpretar el fitxer de dades per construir-ne el graf.
 */
public class DatasetReader {

    private List<String> lines;

    /**
     * Llegeix el fitxer que es troba en el path obtingut i en guarda el contingut en una llista
     * amb les lines en forma d'String.
     */
    public DatasetReader() {}

    /**
     * Analitza el contingut del dataset i retorna la representació del graf.
     * @return Llista amb totes les seus que representen el graf.
     */
    public Headquarters[] buildGraph(String filepath) {
        readFile(filepath);
        List<Headquarters> tmpGraph = new ArrayList<>();

        readHeadquarters(tmpGraph);
        Headquarters[] graph = tmpGraph.toArray(new Headquarters[0]);
        quickSort(graph, 0, graph.length - 1);
        readRoutes(graph);

        return graph;
    }


    public SugarChannel buildTree(String filepath) {
        readFile(filepath);

        int citizenCount = Integer.parseInt(lines.get(0));

        String[] info = lines.get(1).split(";");
        info[2] = info[2].replace(",", ".");

        SugarChannel sugarChannel = new SugarChannel(new Citizen(Integer.parseInt(info[0]), info[1],
                Float.parseFloat(info[2]), Job.parseJob(info[3])));

        for(int i = 2; i <= citizenCount; i++) {
            info = lines.get(i).split(";");
            info[2] = info[2].replace(",", ".");

            Citizen citizen = new Citizen(Integer.parseInt(info[0]), info[1],
                    Float.parseFloat(info[2]), Job.parseJob(info[3]));

            sugarChannel.addCitizen(sugarChannel.getRoot(), citizen);
        }

        return sugarChannel;
    }

    public TerrorChannel buildRTree(String filepath) {
        readFile(filepath);

        int monsterCount = Integer.parseInt(lines.get(0));

        String[] info = lines.get(1).split(";");
        info[4] = info[4].replace(",", ".");
        info[5] = info[5].replace(",", ".");
        info[6] = info[6].replace(",", ".");

        Monster monster = new Monster(Integer.parseInt(info[0]), info[1], info[2], info[3].equals("1"),
                Float.parseFloat(info[4]), new Point(Float.parseFloat(info[5]), Float.parseFloat(info[6])));

        Rectangle rectangle = new Rectangle(monster);

        Rectangle root = new Rectangle(rectangle);

        TerrorChannel terrorChannel = new TerrorChannel(root);

        for (int i = 2; i < monsterCount + 1; i++) {
            info = lines.get(i).split(";");
            info[4] = info[4].replace(",", ".");
            info[5] = info[5].replace(",", ".");
            info[6] = info[6].replace(",", ".");

            monster = new Monster(Integer.parseInt(info[0]), info[1], info[2], info[3].equals("1"),
                    Float.parseFloat(info[4]), new Point(Float.parseFloat(info[5]), Float.parseFloat(info[6])));

            terrorChannel.insertMonster(monster);
        }

        return terrorChannel;
    }

    public ScienceChannel buildTable(String filepath) {
        readFile(filepath);

        int inventCount = Integer.parseInt(lines.get(0));
        ScienceChannel scienceChannel = new ScienceChannel(inventCount);

        for(int i = 1; i <= inventCount; i++) {
            String info[] = lines.get(i).split(";");
            Invention invention = new Invention(info[0], Boolean.parseBoolean(info[1]), info[2]);
            scienceChannel.add(invention);
        }

        return scienceChannel;
    }

    private void readFile(String filepath) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath));
            lines = bufferedReader.lines().toList();
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        }
    }

    /**
     * Llegeix la part del dataset que conté les diferents seus, les interpreta i
     * les guarda en una llista
     * @param headquarters llista buida on es guardarà la representació del graph.
     * @return llista amb totes les seus del dataset.
     */
    private List<Headquarters> readHeadquarters(List<Headquarters> headquarters) {
        int headquartersCount = Integer.parseInt(lines.get(0));

        for (int i = 1; i <= headquartersCount; i++) {
            String[] info = lines.get(i).split(";");
            headquarters.add(new Headquarters(Integer.parseInt(info[0]), info[1] , info[2], Material.parseMaterial(info[3])));
        }

        return headquarters;
    }

    /**
     * Llegeix la part del dataset que conté les rutes d'enviament, les interpreta i
     * les afegeix a la seu corresponent (la seu destí).
     * @param headquarters llista amb les seus sense les seves rutes inicialitzades.
     */
    private void readRoutes(Headquarters[] headquarters) {
        int headquartersCount = Integer.parseInt(lines.get(0));

        for (int i = headquartersCount + 2; i < lines.size(); i++) {
            String[] info = lines.get(i).split(";");

            int supplierIndex = Entrepreneurship.binarySearch(
                    0, headquarters.length - 1, Integer.parseInt(info[0]), headquarters
            );

            int clientIndex = Entrepreneurship.binarySearch(
                    0, headquarters.length - 1, Integer.parseInt(info[1]), headquarters
            );

            headquarters[supplierIndex].addClient(new Route(
                    headquarters[clientIndex], Integer.parseInt(info[2]), Material.parseMaterial(info[3])
            ));

            headquarters[clientIndex].addSupplier(new Route(
                    headquarters[supplierIndex], Integer.parseInt(info[2]), Material.parseMaterial(info[3])
            ));
        }
    }

    private static int partition(Headquarters[] headquarters, int left, int right) {
        int middle = (left + right) / 2;
        Headquarters pivot = headquarters[middle];

        while(true) {
            while (headquarters[left].getID() < pivot.getID()) {
                left++;
            }

            while (headquarters[right].getID() > pivot.getID()) {
                right--;
            }

            if (left >= right) {
                return right;
            }

            Headquarters temp = headquarters[left];
            headquarters[left] = headquarters[right];
            headquarters[right] = temp;

            left++;
            right--;
        }
    }

    private static void quickSort(Headquarters[] shoes, int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition(shoes, begin, end);
            quickSort(shoes, begin, partitionIndex);
            quickSort(shoes, partitionIndex + 1, end);
        }
    }
}
