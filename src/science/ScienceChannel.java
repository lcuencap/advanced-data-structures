package science;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScienceChannel {

    HashTable<Invention> hashTable;

    private float numBrokenGreen;
    private float numBrokenYellow;
    private float numBrokenRed;
    private float numBrokenBlue;
    private float numBrokenPink;
    private float numBrokenOrange;

    public ScienceChannel(int size) {
        hashTable = new HashTable<>(Invention.class, size);

        numBrokenGreen = 0;
        numBrokenRed = 0;
        numBrokenYellow = 0;
        numBrokenBlue = 0;
        numBrokenPink = 0;
        numBrokenOrange = 0;
    }

    public void add(Invention invent) {
        hashTable.add(invent);
        updateStatistics(invent);
    }

    public void remove(String name) {
        hashTable.remove(name);
    }

    //Fer inventari
    public Invention getInventionByName(String name) {
        return hashTable.get(name);
    }

    //Cerca alfabètica
    public List<Invention> searchByLetter(char letter) {
        letter = Character.toUpperCase(letter);
        List<Invention> result = new ArrayList<>();

        List<Invention> allInventions = hashTable.getAll();

        for (Invention invention : allInventions) {
            if (invention.getName().toUpperCase().charAt(0) == letter) {
                result.add(invention);
            }
        }

        Collections.sort(result, (a, b) -> a.getName().compareToIgnoreCase(b.getName()));
        return result;
    }


    //Tafaneria
    public boolean markAsBroken(String name) {
        Invention invent = hashTable.get(name);
        if (invent != null && invent.getStatus()) {
            invent.setStatus(false);
            updateStatistics(invent);
            return true;
        } else {
            return false;
        }
    }

    //Reparació
    public boolean markAsFunctional(String name) {
        Invention invent = hashTable.get(name);
        if (invent != null && !invent.getStatus()) {
            invent.setStatus(true);
            removeBroken(invent);
            return true;
        } else {
            return false;
        }
    }

    //Estadística de colors
    public void showStatistics() {
        float totalBroken = numBrokenGreen + numBrokenYellow + numBrokenRed + numBrokenBlue + numBrokenPink + numBrokenOrange;

        if (totalBroken >= 0) {
            System.out.println("Total broken inventions: " + totalBroken);
            System.out.println("\nTotal green broken inventions: " + numBrokenGreen / totalBroken * 100);
            System.out.println("Total yellow broken inventions: " + numBrokenYellow / totalBroken * 100);
            System.out.println("Total red broken inventions: " + numBrokenRed / totalBroken * 100);
            System.out.println("Total blue broken inventions: " + numBrokenBlue / totalBroken * 100);
            System.out.println("Total pink broken inventions: " + numBrokenPink / totalBroken * 100);
            System.out.println("Total orange broken inventions: " + numBrokenOrange / totalBroken * 100);
        } else {
            System.out.println("There are no broken inventions");
        }
    }

    public void updateStatistics(Invention invention) {
        switch (invention.color) {
            case "GREEN":
                numBrokenGreen++;
                break;
            case "YELLOW":
                numBrokenYellow++;
                break;
            case "RED":
                numBrokenRed++;
                break;
            case "BLUE":
                numBrokenBlue++;
                break;
            case "PINK":
                numBrokenPink++;
                break;
            case "ORANGE":
                numBrokenOrange++;
                break;
        }
    }

    public void removeBroken(Invention invention) {
        switch (invention.color) {
            case "GREEN":
                numBrokenGreen--;
                break;
            case "YELLOW":
                numBrokenYellow--;
                break;
            case "RED":
                numBrokenRed--;
                break;
            case "BLUE":
                numBrokenBlue--;
                break;
            case "PINK":
                numBrokenPink--;
                break;
            case "ORANGE":
                numBrokenOrange--;
                break;
        }
    }
}
