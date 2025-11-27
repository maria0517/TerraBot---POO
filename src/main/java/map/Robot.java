package map;


import constants.Const;
import entities.Entity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;

public final class Robot {
    private int x;
    private int y;
    private double energyPoints;
    private boolean isCharging;

    // lista in care retin entitatile scanate
    private List<Entity> scannedEntities;

    // HashMap pentru fapte: cheia = subiect, valoarea = lista de fapte
    private Map<String, List<String>> factsDatabase;

    // constructor robot
    public Robot(final int x, final int y, final double energyPoints) {
        this.x = x;
        this.y = y;
        this.energyPoints = energyPoints;

        // init la lista
        scannedEntities = new ArrayList<>();
        factsDatabase = new LinkedHashMap<>(); // fol linked ca sa pastrez ordinea
    }

    // getteri setteri la toata lumea
    public void setX(final int x) {
        this.x = x;
    }

    public void setY(final int y) {
        this.y = y;
    }

    public void setEnergyPoints(final double energyPoints) {
        this.energyPoints = energyPoints;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public double getEnergyPoints() {
        return this.energyPoints;
    }

    /**
     * functie mutare robot
     */
    public String moveRobot(final MapA mapaEfec) {
        int min = Const.NR_MARE;
        double scor = 0;
        int nrBadEntities = 0;
        int nrPatratica = 0;

        // System.out.println("ma mut de la " + this.getX() + " " + this.getY() + " " +
        // energyPoints);

        if (mapaEfec.verifCell(x, y + 1)) { // dreapta
            // dreapta care e dreapta
            scor = mapaEfec.getCell(x, y + 1).calculateScore();
            nrBadEntities = mapaEfec.getCell(x, y + 1).calcAllEntities();
            scor = Math.round(Math.abs(scor / nrBadEntities));
            int integerScor = (int) scor;
            if (integerScor < min) {
                min = integerScor;
                nrPatratica = 2;
            }
        }

        if (mapaEfec.verifCell(x + 1, y)) { // jos
            // asta e sus la mine, jos aici
            scor = mapaEfec.getCell(x + 1, y).calculateScore();
            nrBadEntities = mapaEfec.getCell(x + 1, y).calcAllEntities();
            scor = Math.round(Math.abs(scor / nrBadEntities));
            int integerScor = (int) scor;
            if (integerScor < min) {
                min = integerScor;
                nrPatratica = Const.UN_TREI;
            }
        }

        if (mapaEfec.verifCell(x, y - 1)) { // stanga
            // asta e stanga pe bune
            scor = mapaEfec.getCell(x, y - 1).calculateScore();
            nrBadEntities = mapaEfec.getCell(x, y - 1).calcAllEntities();
            scor = Math.round(Math.abs(scor / nrBadEntities));
            int integerScor = (int) scor;
            if (integerScor < min) {
                min = integerScor;
                nrPatratica = Const.UN_PATRU;
            }
        }

        if (mapaEfec.verifCell(x - 1, y)) { // sus
            // asta e sus cica (la mine in cap e jos)
            scor = mapaEfec.getCell(x - 1, y).calculateScore();
            nrBadEntities = mapaEfec.getCell(x - 1, y).calcAllEntities();
            scor = Math.round(Math.abs(scor / nrBadEntities));
            int integerScor = (int) scor;
            if (integerScor < min) {
                // schimb
                // am cea mai mare patratica
                min = integerScor;
                nrPatratica = 1;
            }
        }


        // acuma am patratica de pe care ma mut
        // acum sa vad ce verific -> daca pot sa fac si daca am unde
        if (energyPoints >= min && nrPatratica != 0) {
            // verific pe ce patratica
            energyPoints = energyPoints - min;
            if (nrPatratica == 1) {
                this.x = x - 1;
            }
            if (nrPatratica == 2) {
                this.y = y + 1;
            }
            if (nrPatratica == Const.UN_TREI) {
                this.x = x + 1;
            }
            if (nrPatratica == Const.UN_PATRU) {
                this.y = y - 1;
            }
            // System.out.println("ajung pe " + x + " " + y + " cu costul: " + min);
            return "The robot has successfully moved to position (" + this.x + ", " + this.y + ").";
        }
        // daca am ajuns aici ori nu pot sa ma mut ori n am baterie
        return "ERROR: Not enough battery left. Cannot perform action";
    }

    /**
     * metoda scanare entitati
     */
    public void addScanObj(final Entity elemScanat) {
        // adaug elem
        scannedEntities.add(elemScanat);
    }

    /**
     * getter pentru lista de entitati scanate
     */
    public List<Entity> getScannedEntities() {
        return scannedEntities;
    }

    /**
     * adauga un fact in knowledge
     */
    public void addFact(final String subject, final String fact) {
        factsDatabase.putIfAbsent(subject, new ArrayList<>());
        factsDatabase.get(subject).add(fact);
    }

    /**
     * getter pentru fapte
     */
    public List<String> getFacts(final String subject) {
        return factsDatabase.getOrDefault(subject, new ArrayList<>());
    }

    /**
     * functie pentru afisare knowledge
     */
    public ArrayNode getKnowledgeBase(final ObjectMapper mapper) {
        ArrayNode outputArray = mapper.createArrayNode();

        for (Map.Entry<String, List<String>> entry : this.factsDatabase.entrySet()) {
            ObjectNode topicNode = mapper.createObjectNode();
            topicNode.put("topic", entry.getKey());

            ArrayNode factsArray = mapper.createArrayNode();
            for (String fact : entry.getValue()) {
                factsArray.add(fact);
            }
            topicNode.set("facts", factsArray);
            outputArray.add(topicNode);
        }
        // afisare
        return outputArray;
    }
}
