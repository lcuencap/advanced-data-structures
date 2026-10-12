package presentation;

import entrepreneurship.Entrepreneurship;
import science.Invention;
import science.ScienceChannel;
import sugar.Citizen;
import sugar.SugarChannel;
import terror.Monster;
import terror.Point;
import terror.RTreeVisualization;
import terror.TerrorChannel;

import java.util.ArrayList;

public class Controller {

    private final UI ui;
    private final Entrepreneurship entrepreneurship;
    private final SugarChannel sugarChannel;
    private final TerrorChannel terrorChannel;
    private final ScienceChannel scienceChannel;
    private final RTreeVisualization rTreeVisualization;

    public Controller(UI ui, Entrepreneurship entrepreneurship, SugarChannel sugarChannel,
                      TerrorChannel terrorChannel, ScienceChannel scienceChannel) {
        this.ui = ui;
        this.entrepreneurship = entrepreneurship;
        this.sugarChannel = sugarChannel;
        this.terrorChannel = terrorChannel;
        this.scienceChannel = scienceChannel;
        rTreeVisualization = new RTreeVisualization(terrorChannel);
    }

    public void run() {
        int option;
        do {
            option = ui.mainMenu();
            switch (option) {
                case 1:
                    entrepreneurshipChannel();
                    break;
                case 2:
                    sugarChannelMenu();
                    break;
                case 3:
                    terrorChannel();
                    break;
                case 4:
                    scienceChannelMenu();
                case 5:
                    ui.bye();
            }
        } while (option != 5);
    }
    private void scienceChannelMenu() {
        String name;
        boolean done;
        char option;
        do {
            option = ui.scienceChannelMenu();
            switch (option) {
                case 'A':
                    name = ui.askForStringIn("\nNom de l’invent: ");
                    String worksStr = ui.askForStringIn("Funciona? Sí/No: ");
                    String color = ui.askForStringIn("Color: ");
                    boolean works = false;
                    if (worksStr.equals("sí") || worksStr.equals("Sí")) {
                        works = true;
                    }
                    Invention invention = new Invention(name, works, color);
                    scienceChannel.add(invention);
                    break;
                case 'B':
                    name = ui.askForStringIn("\nNom de l’invent a destruir:");
                    scienceChannel.remove(name);
                    break;
                case 'C':
                    name = ui.askForStringIn("\nNom de l’invent:");
                    Invention foundInv = scienceChannel.getInventionByName(name);
                    if (foundInv != null) {
                        System.out.println("S’ha trobat l’invent ‘" +
                                foundInv.getName() + "'. Estat: " +
                                (foundInv.getStatus() ? "Funcional." : "No Funcional.") +
                                " Color: " + foundInv.getColor());
                        System.out.println();
                    } else {
                        System.out.println("\nInvent no trobat\n");
                    }
                    break;
                case 'D':
                    name = ui.askForStringIn("Lletra a cercar: ");
                    ArrayList<Invention> invents = (ArrayList<Invention>) scienceChannel.searchByLetter(name.charAt(0));
                    System.out.println("\nS'han trobat " + invents.size() + " invents amb aquesta lletra:\n");
                    for (Invention aux : invents) {
                        System.out.println("\t* " + aux.getName() + ". Estat: " + (aux.getStatus() ? "Funcional" : "Espatllat") + ". Color: " + aux.getColor() + ".");
                    }
                    System.out.println();
                    break;
                case 'E':
                    name = ui.askForStringIn("\nNom de l’invent a trencar: ");
                    done = scienceChannel.markAsBroken(name);
                    System.out.println("L'invent " + name + (done ? " ja no funciona." : " no s'ha pogut trencar."));
                    System.out.println();
                    break;
                case 'F':
                    name = ui.askForStringIn("\nNom de l’invent a reparar: ");
                    done = scienceChannel.markAsFunctional(name);
                    System.out.println("\nL'invent " + name + (done ? " ha quedat com si fos nou.\n" : " no s'ha pogut reparar.\n"));
                    break;
                case 'G':
                    System.out.println("\nGenerant estadístiques...\n");
                    scienceChannel.showStatistics();
                    System.out.println();
                    break;
            }
        }while (option != 'H');
    }

    private void sugarChannelMenu() {
        char option;
        do {
            option = ui.sugarChannelMenu();
            switch (option) {
                case 'A':
                    int id = ui.askForId();
                    String name = ui.askForName();
                    sugarChannel.addCitizen(sugarChannel.getRoot(), new Citizen(id, name, ui.askForIndex(), ui.askForJob()));
                    System.out.println();
                    System.out.println("El regne ara compta amb la presència de l’habitant "+ name+".");
                    break;
                case 'B':
                    int idCitizen = ui.askForCitizenToRemove();
                    sugarChannel.removeCitizen(idCitizen);
                    break;
                case 'C':
                    sugarChannel.showTreeRepresentation();
                    break;
                case 'D':
                    sugarChannel.infiltratorSearch(ui.askForSugarIndex(), ui.askForBase());
                    break;
                case 'E':
                    float min = ui.askForMinSugarIndex();
                    float max;
                    do {
                        max = ui.askForMaxSugarIndex();
                        if (max <= min) {
                            ui.incorrectInput("The maximum sugar índex must be greater than the minimum.\n");
                        }
                    }while (max <= min);
                    sugarChannel.cloneCapture(min, max);
                    break;
                case 'F':
                    String nameInfected = ui.askForInfectionOrigin();
                    sugarChannel.infectionSimulation(nameInfected);
                    break;
                case 'G':
                    int n_desinfectats = sugarChannel.disinfectionSimulation();
                    ui.showMessgae("S'han desinfectat " + n_desinfectats + " habitants anteriorment cítrics");
                    ui.showMessgae("");
            }
        } while (option != 'H');
    }

    private void entrepreneurshipChannel() {
        char task;
        do {
            task = ui.entrepreneurshipMenu();
            switch (task) {
                case 'A':
                    int idSpy = ui.askForHqToSpy();
                    entrepreneurship.showInteligenciaComercial(idSpy);
                    break;
                case 'B':
                    entrepreneurship.showExecutionOrder();
                    break;
                case 'C':
                    entrepreneurship.findShortestPath(ui.getSeuOrigen(), ui.getSeuDesti());
                    break;
            }
        } while (task != 'D');
    }

    private void terrorChannel() {
        char option;
        do {
            option = ui.terrorChannelMenu();
            switch (option) {
                case 'A':
                    terrorChannel.insertMonster(new Monster(ui.askForMonsterId(), ui.askForMonsterName(), ui.askForMonsterLevel(), ui.askIfMonsterKnown(), ui.askForMonsterSize(), new Point(ui.askForMonsterLatitude(), ui.askForMonsterLongitude())));
                    ui.showMessgae("\nUn nou monstre aguaita a la foscor.\n");
                    break;
                case 'B':
                    terrorChannel.removeMonster(ui.askForCoordinates());
                    ui.showMessgae("\nUn monstre ha desaparegut de la granja\n");
                    break;
                case 'C':
                    rTreeVisualization.startVisualization();
                    ui.showMessgae("\nGenerant mapa...\n");
                    break;
                case 'D':
                    Point[] p1 = ui.askForPoints();
                    Point[] minMax = Point.getMaxAndMin(p1[0], p1[1]);
                    terrorChannel.searchMonsters(minMax[0].getY(), minMax[0].getX(), minMax[1].getY(), minMax[1].getX());
                    break;
                case 'E':
                    terrorChannel.zonaSegura(ui.askForPoint(), ui.askForMonsterK());
                    break;
            }
        }while (option != 'F');
    }
}
