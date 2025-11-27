package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import constants.Const;
import fileio.CommandInput;
import fileio.InputLoader;
import fileio.SimulationInput;

import map.*;
import entities.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The entry point to this homework. It runs the checker that tests your implementation.
 */
public final class Main {

    private Main() {
    }

    private static final ObjectMapper MAPPER = new ObjectMapper();
    public static final ObjectWriter WRITER = MAPPER.writer().withDefaultPrettyPrinter();

    /**
     * @param inputPath input file path
     * @param outputPath output file path
     * @throws IOException when files cannot be loaded.
     */
    public static void action(final String inputPath, final String outputPath)
            throws IOException {

        InputLoader inputLoader = new InputLoader(inputPath);
        ArrayNode output = MAPPER.createArrayNode();

        // ma ajuta la 17 si 18 pentru a avea toate simularile
        int counterSim = 0;
        ArrayList<SimulationInput> allSims = inputLoader.getSimulations();
        // acum incep sa vad comenzile
        ArrayList<CommandInput> allComs = inputLoader.getCommands();

        // toata var de imi trebuie sa le vad peste tot
        MapA mapaCurenta = null;
        Robot amicu = null;
        boolean beginSim = false;
        boolean endSim = false;
        boolean isCharging = false;
        int timeLoadStart = 1;
        int timeToRecharge = 0;
        int lastProcessedTimestamp = 0;
        int timeWeatherChange = 0;
        String weatherType = null;
        boolean weatherChange = false;

        // parcurg comenzile -> ele imi descriu simularile
        for (CommandInput comanda : allComs) {
            // acum cate o comanda pe rand
            ObjectNode result = MAPPER.createObjectNode();
            ObjectNode eroare = MAPPER.createObjectNode();

            if (comanda.getCommand().equals("startSimulation")) {
                // a inceput simularea
                // iau cate o simulare cu toate datele ei
                SimulationInput simulare = null;
                if (counterSim <= allSims.size() - 1) {
                    simulare = allSims.get(counterSim);
                    String[] dims = simulare.getTerritoryDim().split("x");
                    int width = Integer.parseInt(dims[0]);
                    int height = Integer.parseInt(dims[1]);
                    int energyPoints = simulare.getEnergyPoints();

                    mapaCurenta = new MapA(width, height, energyPoints);
                    // populez harta
                    mapaCurenta.populMap(simulare.getTerritorySectionParams());

                    // setez toate prostiile
                    timeLoadStart = 1;
                    timeToRecharge = 0;
                    lastProcessedTimestamp = 0;
                    // momentan amicu nu e la incarcat
                    isCharging = false;
                    beginSim = false;
                    endSim = false;
                    // il pun in coltul din stanga jos
                    amicu = new Robot(0, 0, energyPoints);

                    timeWeatherChange = 0;
                    weatherType = null;
                    // asta o sa ma ajute la afisare
                    weatherChange = false;
                    // increm pentru urmatoarea simulare
                    counterSim++;
                }

                // pentru debug
                System.out.println("La timestamp: " + comanda.getTimestamp());
                for (int i = 0; i < mapaCurenta.getWidth(); i++) {
                    for (int j = 0; j < mapaCurenta.getWidth(); j++) {
                        System.out.println("Celula (" + i + ", " + j + "):\n" + mapaCurenta.getCell(i, j) + "\n------------------------");
                    }
                }
                if (beginSim) {
                    // eroar - simularea a inceput deja
                    eroare.put("command", comanda.getCommand());
                    eroare.put("message",
                            "ERROR: Simulation already started. Cannot perform action");
                    eroare.put("timestamp", comanda.getTimestamp());
                    output.add(eroare);
                    // trec peste
                    continue;
                }
                // si a plecat pe bune
                beginSim = true;
                ObjectNode eBine = MAPPER.createObjectNode();
                eBine.put("command", "startSimulation");
                eBine.put("message", "Simulation has started.");
                eBine.put("timestamp", comanda.getTimestamp());
                output.add(eBine);
            }

            //  asta e pentru comenzile dintre endSim si startSim
            if (endSim && !comanda.getCommand().equals("startSimulation")) {
                eroare.put("command", comanda.getCommand());
                eroare.put("message", "ERROR: Simulation not started. Cannot perform action");
                eroare.put("timestamp", comanda.getTimestamp());
                output.add(eroare);
                continue;
            }

            // am depasit sau am ajuns la finalul incarcarii -> de acum
            // se poate folosi (trebuie inaintea oricarei comenzi, in afara de startSim
            if (timeLoadStart + timeToRecharge <= comanda.getTimestamp()) {
                isCharging = false;
                timeToRecharge = 0;
            }

            if (comanda.getCommand().equals("endSimulation") && !isCharging) {
                // incheie sim
                // o pot inchide doar daca robotul nu e
                // la incarcat, altfel sare in aer
                if (!beginSim || endSim) {
                    // s a inchis ceva ce nu s a deschis))
                    eroare.putArray("ERROR: Simulation not started. Cannot perform action");
                    output.add(eroare);

                    continue;
                }
                endSim = true;
                ObjectNode eBine = MAPPER.createObjectNode();
                eBine.put("command", "endSimulation");
                eBine.put("message", "Simulation has ended.");
                eBine.put("timestamp", comanda.getTimestamp());
                output.add(eBine);
            }

            // pentru schimbari meteo
            if (timeWeatherChange != 0 && timeWeatherChange + 2 <= comanda.getTimestamp()) {
                // le fac cu formula default scorurile la aer
                // doar pentru 2 iteratii tin conditiile meteo
                // mai intai "debifez true de la weathercond"
                mapaCurenta.setWeatherCheck(weatherType, 0);
                // recalc la toata lumea -> de fiecare cand fol aer calculez efectiv
            }
            // dintr o simulare de a mea
            // fac toate interactiunile cu care sunt restanta
            // mai ales daca am un recharge
            if (beginSim && !endSim) {
                for (int timestamp = lastProcessedTimestamp + 1;
                     timestamp <= comanda.getTimestamp(); timestamp++) {
                    mapaCurenta.interc1time(timestamp);
                    mapaCurenta.interc2time(timestamp); // asta doar la apa =)
                    mapaCurenta.intercAnimal(timestamp);
                }
            }
            // actualizez noul timp
            lastProcessedTimestamp = comanda.getTimestamp();

            // preiau comenzi daca nu a inceput o simulare
            // si daca robotelul nu este la incarcat
            if (beginSim && !endSim && !isCharging) {
                // aici sunt in timpul simularii
                if (comanda.getCommand().equals("printEnvConditions")) {
                    // patratica curenta -> data de coor robotel

                    result.put("command", "printEnvConditions");

                    // DOAR ASTA TREBUIE - apelezi metoda din Cell
                    Cell currentCell = mapaCurenta.getCell(amicu.getX(), amicu.getY());
                    ObjectNode cellInfo = currentCell.getEnvironmentInfo(weatherChange);

                    result.set("output", cellInfo);
                    result.put("timestamp", comanda.getTimestamp());
                    output.add(result);
                }
                if (comanda.getCommand().equals("printMap")) {
                    // pentru toata matricea
                    result.put("command", "printMap");
                    // obt info pentru toata harta
                    ArrayNode fullMapInfo = mapaCurenta.getMapInfo();
                    result.set("output", fullMapInfo);
                    result.put("timestamp", comanda.getTimestamp());
                    output.add(result);
                }
                // urmeaza aici move_robot
                if (comanda.getCommand().equals("moveRobot")) {
                    result.put("command", "moveRobot");
                    // metoda returneaza fie ca s a mutat fie ca nu
                    String mutareRobot = amicu.moveRobot(mapaCurenta);
                    result.put("message", mutareRobot);
                    result.put("timestamp", comanda.getTimestamp());
                    output.add(result);
                }
                if (comanda.getCommand().equals("getEnergyStatus")) {
                    result.put("command", "getEnergyStatus");
                    int baterieAmic = (int) amicu.getEnergyPoints();
                    result.put("message", "TerraBot has " + baterieAmic + " energy points left.");
                    result.put("timestamp", comanda.getTimestamp());
                    output.add(result);

                }
                if (comanda.getCommand().equals("rechargeBattery")) {
                    // trebuie incarcat amicul =)
                    isCharging = true;
                    // primesc timpul de incarcare
                    timeToRecharge = comanda.getTimeToCharge();
                    // cresc punctele de energie si timestampul
                    amicu.setEnergyPoints(timeToRecharge + amicu.getEnergyPoints());

                    result.put("command", "rechargeBattery");
                    result.put("message", "Robot battery is charging.");
                    result.put("timestamp", comanda.getTimestamp());
                    // timestamp = timestamp + timeToRecharge - 1 atunci se opresc incarcarea
                    // de cand a inceput sa se incarce amicu
                    timeLoadStart = comanda.getTimestamp();
                    output.add(result);
                }
                if (comanda.getCommand().equals("changeWeatherConditions")) {
                    // vremea pe care o citesc afecteaza intreaga matrice
                    weatherType = comanda.getType();
                    // pentru ce atribui
                    double value = 0;

                    // trebuie sa citesc ce val am (poate fi pentru mountain, rainfall, temperate)
                    if (comanda.getType().equals("peopleHiking")) {
                        value = comanda.getNumberOfHikers();
                    }
                    if (comanda.getType().equals("rainfall")) {
                        value = comanda.getRainfall();
                    }
                    if (comanda.getType().equals("polarStorm")) {
                        value = comanda.getWindSpeed();
                    }
                    if (comanda.getType().equals("newSeason")) {
                        String season = comanda.getSeason();
                        if (season.equalsIgnoreCase("Spring")) {
                            value = 1;
                        }
                        if (season.equalsIgnoreCase("Summer")) {
                            value = 2;
                        }
                        if (season.equalsIgnoreCase("Fall")) {
                            value = Const.UN_TREI;
                        }
                        if (season.equalsIgnoreCase("Winter")) {
                            value = Const.UN_PATRU;
                        }
                    }

                    // imi setez evenimentul meteo
                    // pentru 2 iteratii orice fac stiu ca am
                    // fenomen pe astea
                    weatherChange = mapaCurenta.setWeatherCheck(weatherType, value);
                    if (!weatherChange) {
                        // nu am aer pentru acel tip de schimbare meteo de pe harta
                        eroare.put("command", "changeWeatherConditions");
                        eroare.put("message", "ERROR: The weather change does "
                                + "not affect the environment. Cannot perform action");
                        eroare.put("timestamp", comanda.getTimestamp());
                        output.add(eroare);
                    } else {
                        timeWeatherChange = comanda.getTimestamp();

                        result.put("command", "changeWeatherConditions");
                        result.put("message", "The weather has changed.");
                        result.put("timestamp", comanda.getTimestamp());
                        output.add(result);
                    }
                }
                if (comanda.getCommand().equals("scanObject")) {
                    // preiau atributele
                    if (amicu.getEnergyPoints() - Const.UN_SAPTE < 0) {
                        // nu am puncte sa mai scanez -> eroare mare
                        eroare.put("command", "scanObject");
                        eroare.put("message", "ERROR: Not enough energy to perform action");
                        eroare.put("timestamp", comanda.getTimestamp());
                        output.add(eroare);
                    } else {
                        String color = comanda.getColor();
                        String smell = comanda.getSmell();
                        String sound = comanda.getSound();

                        if (color.equals("none") && smell.equals("none") && sound.equals("none")) {
                            // am apa trebuie cautata si daca da salvata + marcata ca si scanata
                          if (mapaCurenta.getCell(amicu.getX(), amicu.getY()).getWater() == null) {
                                // nu e apa
                                eroare.put("command", comanda.getCommand());
                                eroare.put("message", "ERROR: Object not found. "
                                        + "Cannot perform action");
                                eroare.put("timestamp", comanda.getTimestamp());
                                output.add(eroare);
                          } else {
                                // apa exista
                                Entity apaScan = mapaCurenta.getCell(amicu.getX(),
                                        amicu.getY()).getWater();
                                amicu.addScanObj(apaScan);
                                amicu.setEnergyPoints(amicu.getEnergyPoints() - Const.UN_SAPTE);
                                // salvez cand am "constientizat" apa
                                mapaCurenta.getCell(amicu.getX(), amicu.getY()).
                                        setStartInterWater(comanda.getTimestamp());
                                // o pun si ca scanata
                             mapaCurenta.getCell(amicu.getX(), amicu.getY()).setScannedWater(true);
                                result.put("command", "scanObject");
                                result.put("message", "The scanned object is water.");
                                result.put("timestamp", comanda.getTimestamp());
                                output.add(result);
                          }

                        }
                      if (!color.equals("none") && !smell.equals("none") && sound.equals("none")) {
                            // aici e plantuta
                            // trebuie sa vad daca exista pe patratica robotului asta,
                            // daca nu eroare
                          if (mapaCurenta.getCell(amicu.getX(), amicu.getY()).getPlant() == null) {
                                // nu am plantuta, e o mica problema
                                eroare.put("command", comanda.getCommand());
                                eroare.put("message", "ERROR: Object not found."
                                       + " Cannot perform action");
                                eroare.put("timestamp", comanda.getTimestamp());
                                output.add(eroare);
                          } else {
                                // planta exista, trebuie adaugata in inventarul robotelului
                                Entity plantutaScan = mapaCurenta.getCell(amicu.getX(),
                                        amicu.getY()).getPlant();
                                amicu.addScanObj(plantutaScan);
                                amicu.setEnergyPoints(amicu.getEnergyPoints() - Const.UN_SAPTE);
                                // salvez cand a inceput plantuta respectiva sa "traiasca"
                                mapaCurenta.getCell(amicu.getX(),
                                        amicu.getY()).setStartInterPlant(comanda.getTimestamp());
                                // o pun si pe harta pentru interactiuni
                                mapaCurenta.getCell(amicu.getX(),
                                        amicu.getY()).setScannedPlant(true);
                                result.put("command", "scanObject");
                                result.put("message", "The scanned object is a plant.");
                                result.put("timestamp", comanda.getTimestamp());
                                output.add(result);
                          }
                      }
                     if (!color.equals("none") && !smell.equals("none") && !sound.equals("none")) {
                         if (mapaCurenta.getCell(amicu.getX(), amicu.getY()).getAnimal() == null) {
                                eroare.put("command", comanda.getCommand());
                                eroare.put("message", "ERROR: Object not found. "
                                        + "Cannot perform action");
                                eroare.put("timestamp", comanda.getTimestamp());
                                output.add(eroare);
                         } else {
                                // animal exista, trebuie adaugata in inventarul robotelului
                                Entity animalScan = mapaCurenta.getCell(amicu.getX(),
                                        amicu.getY()).getAnimal();
                                amicu.addScanObj(animalScan);
                                amicu.setEnergyPoints(amicu.getEnergyPoints() - Const.UN_SAPTE);
                                // salvez cand a inceput plantuta respectiva sa "traiasca"
                                mapaCurenta.getCell(amicu.getX(),
                                        amicu.getY()).setStartInterAnimal(comanda.getTimestamp());
                                // o pun si pe harta pentru interactiuni
                                mapaCurenta.getCell(amicu.getX(),
                                        amicu.getY()).setScannedAnimal(true);
                                result.put("command", "scanObject");
                                result.put("message", "The scanned object is an animal.");
                                result.put("timestamp", comanda.getTimestamp());
                                output.add(result);
                         }
                     }
                    }
                }
                if (comanda.getCommand().equals("learnFact")) {
                    // acum trebuie mai intai sa verific daca am baterie pentru treaba asta
                    if (amicu.getEnergyPoints() < 2) {
                        // nu pot face miscarea
                        eroare.put("command", comanda.getCommand());
                        eroare.put("message", "ERROR: Not enough battery left."
                                + " Cannot perform action");
                        eroare.put("timestamp", comanda.getTimestamp());
                        output.add(eroare);
                    } else {
                        // vad ce subiect am si ma uit sa vad daca l am scanat
                        String component = comanda.getComponents();

                        boolean gasit = false;
                        for (Entity e : amicu.getScannedEntities()) {
                            if (e.getName().equals(component)) {
                                gasit = true;
                            }
                        }
                        if (!gasit) {
                            // n am scanat inca elem pe care vreau sa l pun
                            eroare.put("command", comanda.getCommand());
                            eroare.put("message", "ERROR: Subject not yet saved."
                                    + " Cannot perform action");
                            eroare.put("timestamp", comanda.getTimestamp());
                            output.add(eroare);
                        } else {
                            // am tot ce imi trebuie, scriu in database
                            String subject = comanda.getSubject();
                            amicu.addFact(component, subject);
                            // scad punctele de energie
                            amicu.setEnergyPoints(amicu.getEnergyPoints() - 2);
                            result.put("command", comanda.getCommand());
                            result.put("message", "The fact has been"
                                    + " successfully saved in the database.");
                            result.put("timestamp", comanda.getTimestamp());
                            output.add(result);
                        }
                    }
                }
                if (comanda.getCommand().equals("improveEnvironment")) {
                    eroare.put("command", comanda.getCommand());
                    if (amicu.getEnergyPoints() < Const.UN_ZECE) {
                        eroare.put("message", "ERROR: Not enough battery left. "
                                + "Cannot perform action");
                        eroare.put("timestamp", comanda.getTimestamp());
                        output.add(eroare);
                    } else {
                        // trebuie sa vad ce trebuie sa caut in baza de date
                        String elemToAdd = comanda.getName();
                        // imi trebuie cand adaug
                        String type = comanda.getType();
                        String improvementType = comanda.getImprovementType();
                        Entity entityToAdd = null;

                        boolean gasit = false;
                        for (Entity e : amicu.getScannedEntities()) {
                            if (e.getName().equals(elemToAdd)) {
                                gasit = true;
                                entityToAdd = e;
                            }
                        }
                        // daca n am gasit elem in cele scanate -> dau eroare
                        if (!gasit) {
                            eroare.put("message", "ERROR: Subject not yet saved. "
                                    + "Cannot perform action");
                            eroare.put("timestamp", comanda.getTimestamp());
                            output.add(eroare);
                        } else {
                            // acum il vad daca il am in facst
                            // trebuie formatat
                            String requiredFact = "";
                            switch (improvementType) {
                                case "plantVegetation":
                                    requiredFact = "Method to plant " + elemToAdd;
                                    break;
                                case "fertilizeSoil":
                                    requiredFact = "Method to fertilize with " + elemToAdd;
                                    break;
                                case "increaseHumidity":
                                    requiredFact = "Method to increase humidity.";
                                    break;
                                case "increaseMoisture":
                                    requiredFact = "Method to increaseMoisture";
                                    break;
                                default:
                                    break;
                            }

                            boolean factGasit = false;
                            // acum ca am formatul cerut, incep sa caut
                            // parcurg toate faptele din baza de date
                            List<String> factsForSubject = amicu.getFacts(elemToAdd);
                            if (factsForSubject != null) {
                                for (String fact : factsForSubject) {
                                    if (fact.equals(requiredFact)) {
                                        factGasit = true;
                                    }
                                }
                            }

                            if (!factGasit) {
                                // n am gasit facts pentru jucarie
                                eroare.put("message", "ERROR: Fact not yet saved. "
                                        + "Cannot perform action");
                                eroare.put("timestamp", comanda.getTimestamp());
                                output.add(eroare);
                            } else {
                                // am tot ce trebuie -> scad energia
                                amicu.setEnergyPoints(amicu.getEnergyPoints() - Const.UN_ZECE);

                                // dau mesaj
                                result.put("command", comanda.getCommand());

                                if (improvementType.equals("plantVegetation")) {
                                    result.put("message", "The " + elemToAdd
                                            + " was planted successfully.");
                                }
                                if (improvementType.equals("fertilizeSoil")) {
                                    result.put("message", "The soil was"
                                            + " successfully fertilized using " + elemToAdd);
                                }
                                if (improvementType.equals("increaseHumidity")) {
                                    result.put("message", "The humidity was"
                                            + " successfully increased using " + elemToAdd);
                                }
                                if (improvementType.equals("increaseMoisture")) {
                                    // asta e mai cu mot, nu vrea punct la final
                                    result.put("message", "The moisture was"
                                            + " successfully increased using " + elemToAdd);
                                }
                                result.put("timestamp", comanda.getTimestamp());
                                output.add(result);

                                // o fac fix in celula respectiva
                                mapaCurenta.getCell(amicu.getX(),
                                        amicu.getY()).applyImprov(improvementType);
                                // sterg elementul pe care l am folosit din inventarul robotului
                                amicu.getScannedEntities().remove(entityToAdd);
                            }
                        }
                    }
                }
                if (comanda.getCommand().equals("printKnowledgeBase")) {
                    // trebuie printat ce am
                    result.put("command", comanda.getCommand());
                    // acum trebuie sa iau tot
                    result.set("output", amicu.getKnowledgeBase(MAPPER));
                    result.put("timestamp", comanda.getTimestamp());
                    output.add(result);

                }
            } else {
                // nu s a pornit simularea sau a fost inchisa
                if (!beginSim) {
                    // nu a fost deschisa
                    eroare.put("command", comanda.getCommand());
                    eroare.put("message", "ERROR: Simulation not started. Cannot perform action");
                    eroare.put("timestamp", comanda.getTimestamp());
                    output.add(eroare);
                }
                if (endSim && !comanda.getCommand().equals("endSimulation")) {
                    // s a terminat... papa
                    eroare.put("command", comanda.getCommand());
                    eroare.put("message", "ERROR: Simulation not started. Cannot perform action");
                    eroare.put("timestamp", comanda.getTimestamp());
                    output.add(eroare);
                }
                // aici mi se incarca jucaria si nu pot inchide simularea
                if (isCharging && comanda.getCommand().equals("endSimulation")) {
                    // vreau sa inchid si amicu se incarca
                    eroare.put("command", comanda.getCommand());
                    eroare.put("message", "ERROR: Robot still charging. Cannot perform action");
                    eroare.put("timestamp", comanda.getTimestamp());
                    output.add(eroare);
                }
                if (isCharging) {
                    // orice comanda am primit nu o pot exec
                    // robotul e la incarcat
                    eroare.put("command", comanda.getCommand());
                    eroare.put("message", "ERROR: Robot still charging. Cannot perform action");
                    eroare.put("timestamp", comanda.getTimestamp());
                    output.add(eroare);

                }
            }
        }
        File outputFile = new File(outputPath);
        outputFile.getParentFile().mkdirs();
        WRITER.writeValue(outputFile, output);
    }
}
