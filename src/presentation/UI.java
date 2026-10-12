package presentation;

import sugar.Job;
import terror.Point;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UI {

    private final Scanner scanner;

    public UI() {
        scanner = new Scanner(System.in);
    }

    private int askForInt(String message, int min, int max) {
        int option = 0;

        do {
            System.out.print(message);
            try {
                option = scanner.nextInt();
                if (option < min || option > max) {
                    System.out.print("Please enter a number between " + min + " and " + max + ".\n");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a number.");
            } finally {
                scanner.nextLine();
            }
        } while (option < min || option > max);

        return option;
    }

    private float askForFloat(String message, float min, float max) {
        float option = 0f;

        do {
            System.out.print(message);
            try {
                option = Float.parseFloat(scanner.nextLine().trim().replace(",", "."));
                if (option < min || option > max) {
                    System.out.print("Please enter a number between " + min + " and " + max + ".\n");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        } while (option < min || option > max);

        return option;
    }

    private String askForString(String message){
        System.out.print(message);

        return scanner.nextLine();
    }

    public int mainMenu() {
        System.out.println(".:*· SalleNetwork ·*:.\n");
        System.out.println("1. Canal Emprenedoria (Grafs)");
        System.out.println("2. Canal Sucre (Arbres binaris de cerca)");
        System.out.println("3. Canal Terror (Arbres-R)");
        System.out.println("4. Canal Ciència (Taules)\n");
        System.out.println("5. Apagar\n");

        return askForInt("Escull un canal: ", 1, 5);
    }

    public char entrepreneurshipMenu() {
        System.out.println("\n\tA. Intel·ligència comercial");
        System.out.println("\tB. Execució de la cadena");
        System.out.println("\tC. Transport de matèria prima\n");
        System.out.println("\tD. Tornar enrere\n");


        char answer = 0;

        do {
            String draft = askForString("Quina tasca vols emprendre? ");

            if (draft.length() == 1) {
                answer = draft.charAt(0);
            } else {
                System.out.println("Please introduce a single character.");
            }

            if (answer != 'A' && answer != 'B' && answer != 'C' && answer != 'D') {
                System.out.println("Please introduce one of the shown options. (A, B, C or D)");
            }

        } while (answer != 'A' && answer != 'B' && answer != 'C' && answer != 'D');

        return answer;
    }
    public char scienceChannelMenu() {
        System.out.println("\n\tA. Inventar invent\n" +
                " \tB. Destruir inventt\n" +
                " \tC. Fer inventari\n" +
                " \tD. Cerca alfabètica\n" +
                " \tE. Tafaneria\n" +
                " \tF. Reparació\n" +
                " \tG. Estadística de colors\n" +
                " \n\tH. Tornar enrere\n");

        char answer = 0;

        do {
            String draft = askForString("Quina opció vols executar? ");

            if (draft.length() == 1) {
                answer = draft.charAt(0);
            } else {
                System.out.println("Please introduce a single character.");
            }

            if (answer != 'A' && answer != 'B' && answer != 'C' && answer != 'D' && answer != 'E' && answer != 'F' && answer != 'G' && answer != 'H') {
                System.out.println("Please introduce one of the shown options. (A, B, C, D, E, F, G or H)");
            }

        } while (answer != 'A' && answer != 'B' && answer != 'C' && answer != 'D' && answer != 'E' && answer != 'F' && answer != 'G' && answer != 'H');

        return answer;
    }
    public char sugarChannelMenu() {
        System.out.println("\n\tA. Afegir habitant\n" +
                " \tB. Eliminar habitant\n" +
                " \tC. Representació visual\n" +
                " \tD. Cerca d’infiltrats\n" +
                " \tE. Captura de clons\n" +
                " \tF. Simulació d’infecció\n" +
                " \tG. Simulació de desinfecció\n" +
                " \n\tH. Tornar enrere\n");

        char answer = 0;

        do {
            String draft = askForString("Quina opció vols tastar? ");

            if (draft.length() == 1) {
                answer = draft.charAt(0);
            } else {
                System.out.println("Please introduce a single character.");
            }

            if (answer != 'A' && answer != 'B' && answer != 'C' && answer != 'D' && answer != 'E' && answer != 'F' && answer != 'G' && answer != 'H') {
                System.out.println("Please introduce one of the shown options. (A, B, C, D, E, F, G or H)");
            }

        } while (answer != 'A' && answer != 'B' && answer != 'C' && answer != 'D' && answer != 'E' && answer != 'F' && answer != 'G' && answer != 'H');

        return answer;
    }

    public char terrorChannelMenu() {
        System.out.println("\n\tA. Afegir monstre");
        System.out.println("\tB. Eliminar monstre");
        System.out.println("\tC. Representació gràfica");
        System.out.println("\tD. Cerca de monstres");
        System.out.println("\tE. Zona segura");
        System.out.println("\n\tF. Tornar enrere\n");


        char answer = 0;

        do {
            String draft = askForString("Quina opció vols agitar? ");

            if (draft.length() == 1) {
                answer = draft.charAt(0);
            } else {
                System.out.println("Please introduce a single character.");
            }

            if (answer != 'A' && answer != 'B' && answer != 'C' && answer != 'D' && answer != 'E' && answer != 'F') {
                System.out.println("Please introduce one of the shown options. (A, B, C or D)");
            }

        } while (answer != 'A' && answer != 'B' && answer != 'C' && answer != 'D' && answer != 'E' && answer != 'F');

        return answer;
    }

    public int askForId () {
        int id = 0;
        do {
            System.out.print("Identificador de l’habitant: ");
            try {
                id = scanner.nextInt();
                if (id < 1) {
                    System.out.print("ERROR: Index cannot be a negative or null value.\n");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a number.");
            } finally {
                scanner.nextLine();
            }
        } while (id < 1);
        return id;
    }

    public String askForStringIn(String str) {
        String name = null;
        do {
            System.out.print(str);
            try {
                name = scanner.nextLine();
                if (name.isBlank()) {
                    System.out.println("ERROR: String cannot be null.\n");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a String.");
            }
        } while (name.isBlank());
        return name;
    }

    public String askForName() {
        String name = null;
        do {
            System.out.print("Nom de l’habitant: ");
            try {
                name = scanner.nextLine();
                if (name.isBlank()) {
                    System.out.println("ERROR: Name of the citizen cannot be null.\n");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a String.");
            }
        } while (name.isBlank());
        return name;
    }
    public Job askForJob() {
        Job job = null;
        do {
            System.out.print("Professió de l’habitant: ");
            try {
                job = Job.parseJob(scanner.nextLine());
                if (job == null) {
                    System.out.print("ERROR: Please introduce a valid job.\n");
                }
            } catch (InputMismatchException e) {
                System.out.print("Please enter a valid job.");
            }
        } while (job == null);
        return job;
    }

    public Integer askForIndex() {
        int index = 0;
        do {
            System.out.print("Índex de sucre de l’habitant: ");
            try {
                index = scanner.nextInt();
                if (index < 0) {
                    System.out.print("ERROR: Index cannot be a negative value.\n");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a number.");
            }
        } while (index < 0);
        return index;
    }

    public float askForSugarIndex() {
        return askForFloat("\nÍndex de Sucre: ", 0, 100);
    }

    public String askForBase() {
        String base;
        do {
            base = askForString("\nTipus de base: ");

            if (!base.equals("Profiterola") && !base.equals("Polvoró")) {
                System.out.println("El tipus de base només pot ser Profiterola o Polvoró");
            }
        } while (!base.equalsIgnoreCase("profiterola") && !base.equalsIgnoreCase("polvoró"));

        return base;
    }

    public int askForHqToSpy() {
        return askForInt("Identificador de la seu a espiar: ", 0, Integer.MAX_VALUE);
    }

    public int askForCitizenToRemove() {
        System.out.println();
        return askForInt("Identificador de l'habitant: ", 0, Integer.MAX_VALUE);
    }

    public String askForInfectionOrigin() {
        return askForString("Nom del pacient zero: ");
    }
    public void showMessgae(String ms) {
        System.out.println(ms);
    }

    public float askForMinSugarIndex() {
        return askForFloat("\nÍndex mínim: ", 0, 100);
    }
    public float askForMaxSugarIndex() {
        return askForFloat("Índex màxim: ", 1, 100);
    }

    public void incorrectInput(String error) {
        System.out.println("\nERROR: " + error);
    }
    public Point askForPoint() {
        System.out.println("Entra el punt a consultar (lat, long):");
        String answ = scanner.nextLine();
        String[] coordinates = answ.split(",");
        return new Point(Float.parseFloat(coordinates[0]), Float.parseFloat(coordinates[1]));
    }
    public int askForMonsterK() {
        return askForInt("Entra la quantitat de monstres a considerar (K):", 0, Integer.MAX_VALUE);
    }
    public Point[] askForPoints() {
        float lat;
        float longitude;
        String input_info[] = new String[2];
        String input;
        Point[] p1 = new Point[2];

        System.out.println();
        System.out.print("Entra el primer punt de l'àrea (lat,long): ");
        input = scanner.nextLine();
        input_info = input.split(",");
        lat = Float.parseFloat(input_info[0]);
        longitude = Float.parseFloat(input_info[1]);
        p1[0] = new Point(longitude, lat);

        System.out.print("Entra el segon punt de l'àrea (lat,long): ");
        input = scanner.nextLine();
        input_info = input.split(",");
        lat = Float.parseFloat(input_info[0]);
        longitude = Float.parseFloat(input_info[1]);
        p1[1] = new Point(longitude, lat);
        return p1;
    }
    public float[] askForCoordinates() {
        float[] coordinates = new float[2];

        coordinates[0] = askForFloat("Insert the x coordinates of the Monster to remove: ",
                -Float.MAX_VALUE, Float.MAX_VALUE);
        coordinates[1] = askForFloat("Insert the y coordinates of the Monster to remove: ",
                -Float.MAX_VALUE, Float.MAX_VALUE);

        return coordinates;
    }

    public int askForMonsterId() {
        int r;
        while (true) {
            try {
                r = askForInt("Identificador del monstre: ", 0, 1000);
                return r;
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid integer.");
                scanner.nextLine();
            }
        }
    }


    public String askForMonsterName() {
        String name = askForString("Nom del monstre: ");
        return name;
    }

    public String askForMonsterLevel() {
        String level = askForString("Nivell de perillositat: ");
        return level;
    }

    public boolean askIfMonsterKnown() {
        String response;
        do {
            response = askForString("És conegut? Sí/No: ").trim().toLowerCase();
            if (!response.equals("sí") && !response.equals("no")) {
                System.out.println("Entra 'Sí' o 'No'.");
            }
        } while (!response.equals("sí") && !response.equals("no"));
        return response.equals("sí");
    }

    public float askForMonsterSize() {
        float size = askForFloat("Mida del monstre: ", 0.0f, Float.MAX_VALUE);
        return size;
    }

    public float askForMonsterLongitude() {
        float longitude = askForFloat("Longitud del monstre: ", -180.0f, 180.0f);
        return longitude;
    }

    public float askForMonsterLatitude() {
        float latitude = askForFloat("Latitud del monstre: ", -90.0f, 90.0f);
        return latitude;
    }
    public void bye() {
        System.out.println("\nApagant SalleNetwork...\n" +
                "\n----------:::----------");
    }

    public int getSeuOrigen() {
        return askForInt("Identificador de la seu origen: ", 0, Integer.MAX_VALUE);
    }

    public int getSeuDesti() {
        return askForInt("Identificador de la seu destí: ", 0, Integer.MAX_VALUE);
    }
}
