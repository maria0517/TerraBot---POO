package Map;


import Entities.Entity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.*;

public class Robot {
    private int x;
    private int y;
    private double energyPoints;
    private boolean isCharging;

    // lista in care retin entitatile scanate
    private List<Entity> scannedEntities;

    // HashMap pentru fapte: cheia = subiect, valoarea = lista de fapte
    private Map<String, List<String>> factsDatabase;

    // constructor robot
    public Robot(int x, int y, double energyPoints) {
        this.x = x;
        this.y = y;
        this.energyPoints = energyPoints;

        // init la lista
        scannedEntities = new ArrayList<>();
        factsDatabase = new LinkedHashMap<>(); // fol linked ca sa pastrez ordinea
    }

    // getteri setteri la toata lumea
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setEnergyPoints(double energyPoints) {
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

    public String moveRobot(MapA mapaEfec) {
        int min = 10000;
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
                nrPatratica = 3;
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
                nrPatratica = 4;
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
            if (nrPatratica == 3) {
                this.x = x + 1;
            }
            if (nrPatratica == 4) {
                this.y = y - 1;
            }
            // System.out.println("ajung pe " + x + " " + y + " cu costul: " + min);
            return "The robot has successfully moved to position (" + this.x + ", " + this.y + ").";
        }
        // daca am ajuns aici ori nu pot sa ma mut ori n am baterie
        return "ERROR: Not enough battery left. Cannot perform action";
    }

    public void addScanObj(Entity elem_scanat) {
        // adaug elem
        scannedEntities.add(elem_scanat);
    }

    // getter pentru lista de entitati scanate
    public List<Entity> getScannedEntities() {
        return scannedEntities;
    }

    // adauga un fact in knowledge
    public void addFact(String subject, String fact) {
        factsDatabase.putIfAbsent(subject, new ArrayList<>());
        factsDatabase.get(subject).add(fact);
    }

    // getter pentru fapte
    public List<String> getFacts(String subject) {
        return factsDatabase.getOrDefault(subject, new ArrayList<>());
    }

    public ArrayNode getKnowledgeBase(ObjectMapper mapper) {
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

        return outputArray;
    }
}
