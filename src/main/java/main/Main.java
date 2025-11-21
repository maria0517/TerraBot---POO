package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CommandInput;
import fileio.InputLoader;
import fileio.SimulationInput;

import Map.*;
import Entities.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
    public static void action(final String inputPath,
                              final String outputPath) throws IOException {

        InputLoader inputLoader = new InputLoader(inputPath);
        ArrayNode output = MAPPER.createArrayNode();

        ArrayList<SimulationInput> allSims = inputLoader.getSimulations();

        // parcurg fiecare simulare
        for (SimulationInput simulare : allSims) {
            // acum pentru fiecare simulare fac ceva
            // preiau dim si creez doar mapa nepopulata
            String[] dims = simulare.getTerritoryDim().split("x");
            int width = Integer.parseInt(dims[0]);
            int height = Integer.parseInt(dims[1]);
            int energyPoints = simulare.getEnergyPoints();

            Map mapaCurenta = new Map(width, height, energyPoints);

            // acum am mapa, trebuie sa o populez acum
            var parametri = simulare.getTerritorySectionParams();

            if (parametri.getAir() != null) {
                for (var airInput : parametri.getAir()) {
                    if ("TropicalAir".equals(airInput.getType())) {
                        for (var section : airInput.getSections()) {
                            int x = section.getX();
                            int y = section.getY();

                            // incep sa creez aerul
                            TropicalAir air1 = new TropicalAir(airInput.getType(), airInput.getName(), airInput.getMass(),
                                    airInput.getHumidity(), airInput.getTemperature(), airInput.getOxygenLevel(), airInput.getCo2Level());
                            mapaCurenta.getCell(x, y).setAir(air1);
                        }
                    } else if ("PolarAir".equals(airInput.getType())) {
                        for (var section : airInput.getSections()) {
                            int x = section.getX();
                            int y = section.getY();

                            // incep sa creez aerul
                            PolarAir air2 = new PolarAir(airInput.getType(), airInput.getName(), airInput.getMass(),
                                    airInput.getHumidity(), airInput.getTemperature(),
                                    airInput.getOxygenLevel(), airInput.getIceCrystalConcentration());
                            mapaCurenta.getCell(x, y).setAir(air2);
                        }
                    } else if ("TemperateAir".equals(airInput.getType())) {
                        for (var section : airInput.getSections()) {
                            int x = section.getX();
                            int y = section.getY();

                            // incep sa creez aerul
                            TemperateAir air3 = new TemperateAir(airInput.getType(), airInput.getName(), airInput.getMass(),
                                    airInput.getHumidity(), airInput.getTemperature(), airInput.getOxygenLevel(), airInput.getPollenLevel());
                            mapaCurenta.getCell(x, y).setAir(air3);
                        }
                    } else if ("DesertAir".equals(airInput.getType())) {
                        for (var section : airInput.getSections()) {
                            int x = section.getX();
                            int y = section.getY();

                            // incep sa creez aerul
                            DesertAir air4 = new DesertAir(airInput.getType(), airInput.getName(), airInput.getMass(),
                                    airInput.getHumidity(), airInput.getTemperature(), airInput.getOxygenLevel(), airInput.getDustParticles());
                            mapaCurenta.getCell(x, y).setAir(air4);
                        }
                    } else {
                        for (var section : airInput.getSections()) {
                            int x = section.getX();
                            int y = section.getY();

                            // incep sa creez aerul
                            MountainAir air5 = new MountainAir(airInput.getType(), airInput.getName(), airInput.getMass(),
                                    airInput.getHumidity(), airInput.getTemperature(), airInput.getOxygenLevel(), airInput.getAltitude());
                            mapaCurenta.getCell(x, y).setAir(air5);
                        }
                    }
                }
            }

            if (parametri.getSoil() != null) {
                for (var soilInput : parametri.getSoil()) {
                    // pentru forest
                    if ("ForestSoil".equals(soilInput.getType())) {
                        for (var section : soilInput.getSections()) {
                            int x = section.getX();
                            int y = section.getY();
                            // creez ForestSoil cu toate prop
                            ForestSoil solul1 = new ForestSoil( soilInput.getType(), soilInput.getName(), soilInput.getMass(),
                                    soilInput.getNitrogen(), soilInput.getWaterRetention(), soilInput.getSoilpH(),
                                    soilInput.getOrganicMatter(), soilInput.getLeafLitter());

                            // acum pun in celula
                            mapaCurenta.getCell(x, y).setSoil(solul1);
                        }
                    } else if ("DesertSoil".equals(soilInput.getType())) {
                        for (var section : soilInput.getSections()) {
                            int x = section.getX();
                            int y = section.getY();
                            // creez ForestSoil cu toate prop
                            DesertSoil solul3 = new DesertSoil(soilInput.getType(), soilInput.getName(), soilInput.getMass(),
                                    soilInput.getNitrogen(), soilInput.getWaterRetention(), soilInput.getSoilpH(),
                                    soilInput.getOrganicMatter(), soilInput.getSalinity());

                            // acum pun in celula
                            mapaCurenta.getCell(x, y).setSoil(solul3);
                        }
                    } else if ("TundraSoil".equals(soilInput.getType())) {
                        for (var section : soilInput.getSections()) {
                            int x = section.getX();
                            int y = section.getY();
                            // creez ForestSoil cu toate prop
                            TundraSoil solul4 = new TundraSoil( soilInput.getType(), soilInput.getName(), soilInput.getMass(),
                                    soilInput.getNitrogen(), soilInput.getWaterRetention(), soilInput.getSoilpH(),
                                    soilInput.getOrganicMatter(), soilInput.getPermafrostDepth());

                            // acum pun in celula
                            mapaCurenta.getCell(x, y).setSoil(solul4);
                        }
                    } else if ("SwampSoil".equals(soilInput.getType())) {
                        for (var section : soilInput.getSections()) {
                            int x = section.getX();
                            int y = section.getY();
                            // creez ForestSoil cu toate prop
                            SwampSoil solul4 = new SwampSoil( soilInput.getType(), soilInput.getName(), soilInput.getMass(),
                                    soilInput.getNitrogen(), soilInput.getWaterRetention(), soilInput.getSoilpH(),
                                    soilInput.getOrganicMatter(), soilInput.getWaterLogging());

                            // acum pun in celula
                            mapaCurenta.getCell(x, y).setSoil(solul4);
                        }
                    } else {
                        for (var section : soilInput.getSections()) {
                            int x = section.getX();
                            int y = section.getY();
                            // creez ForestSoil cu toate prop
                            GrasslandSoil solul5 = new GrasslandSoil(soilInput.getType(), soilInput.getName(), soilInput.getMass(),
                                    soilInput.getNitrogen(), soilInput.getWaterRetention(), soilInput.getSoilpH(),
                                    soilInput.getOrganicMatter(), soilInput.getRootDensity());

                            // acum pun in celula
                            mapaCurenta.getCell(x, y).setSoil(solul5);
                        }
                    }
                }
            }

            if (parametri.getWater() != null) {
                for (var waterInput : parametri.getWater()) {
                    for (var section : waterInput.getSections()) {
                        int x = section.getX();
                        int y = section.getY();

                        // fac obiectul
                        Water apaaa = new Water(waterInput.getType(), waterInput.getName(), waterInput.getMass(),
                                waterInput.getSalinity(), waterInput.getPH(), waterInput.getPurity(),
                                waterInput.getTurbidity(), waterInput.getContaminantIndex(), waterInput.isFrozen());

                        // il atribui
                        mapaCurenta.getCell(x, y).setWater(apaaa);
                    }
                }
            }

            if (parametri.getPlants() != null) {
                for (var plantInput : parametri.getPlants()) {
                    if ("FloweringPlants".equals(plantInput.getType())) {
                        for (var section : plantInput.getSections()) {
                            int x = section.getX();
                            int y = section.getY();

                            FloweringPlants planta1 = new FloweringPlants(plantInput.getType(), plantInput.getName(), plantInput.getMass(),
                                    "young");
                            mapaCurenta.getCell(x, y).setPlant(planta1);
                        }
                    } else if ("GymnospermsPlants".equals(plantInput.getType())) {
                        for (var section : plantInput.getSections()) {
                            int x = section.getX();
                            int y = section.getY();

                            GymnospermsPlants planta2 = new GymnospermsPlants(plantInput.getType(), plantInput.getName(), plantInput.getMass(),
                                    "young");
                            mapaCurenta.getCell(x, y).setPlant(planta2);
                        }
                    } else if ("Ferns".equals(plantInput.getType())) {
                        for (var section : plantInput.getSections()) {
                            int x = section.getX();
                            int y = section.getY();

                            Ferns planta3 = new Ferns(plantInput.getType(), plantInput.getName(), plantInput.getMass(),
                                    "young");
                            mapaCurenta.getCell(x, y).setPlant(planta3);
                        }
                    } else if ("Mosses".equals(plantInput.getType())) {
                        for (var section : plantInput.getSections()) {
                            int x = section.getX();
                            int y = section.getY();

                            Mosses planta4 = new Mosses(plantInput.getType(), plantInput.getName(), plantInput.getMass(),
                                    "young");
                            mapaCurenta.getCell(x, y).setPlant(planta4);
                        }
                    } else {
                        // algele
                        for (var section : plantInput.getSections()) {
                            int x = section.getX();
                            int y = section.getY();

                            Algae planta5 = new Algae(plantInput.getType(), plantInput.getName(), plantInput.getMass(),
                                    "young");
                            mapaCurenta.getCell(x, y).setPlant(planta5);
                        }
                    }
                }
            }

            if (parametri.getAnimals() != null) {
                for (var animalInput : parametri.getAnimals()) {
                    if ("Carnivores".equals(animalInput.getType())) {
                        for (var section : animalInput.getSections()) {
                            int x = section.getX();
                            int y = section.getY();

                            Carnivores animalut1 = new Carnivores(animalInput.getType(), animalInput.getName(), animalInput.getMass(),
                                    "hungry");
                            mapaCurenta.getCell(x, y).setAnimal(animalut1);
                        }
                    } else if ("Herbivores".equals(animalInput.getType())) {
                        for (var section : animalInput.getSections()) {
                            int x = section.getX();
                            int y = section.getY();

                            Herbivores animalut2 = new Herbivores(animalInput.getType(), animalInput.getName(), animalInput.getMass(),
                                    "hungry");
                            mapaCurenta.getCell(x, y).setAnimal(animalut2);
                        }
                    } else if ("Detritivores".equals(animalInput.getType())){
                        for (var section : animalInput.getSections()) {
                            int x = section.getX();
                            int y = section.getY();

                            Detritivores animalut3 = new Detritivores(animalInput.getType(), animalInput.getName(), animalInput.getMass(),
                                    "hungry");
                            mapaCurenta.getCell(x, y).setAnimal(animalut3);
                        }
                    } else if ("Omnivores".equals(animalInput.getType())) {
                        for (var section : animalInput.getSections()) {
                            int x = section.getX();
                            int y = section.getY();

                            Omnivores animalut4 = new Omnivores(animalInput.getType(), animalInput.getName(), animalInput.getMass(),
                                    "hungry");
                            mapaCurenta.getCell(x, y).setAnimal(animalut4);
                        }
                    } else {
                        // paraziitiiii
                        for (var section : animalInput.getSections()) {
                            int x = section.getX();
                            int y = section.getY();

                            Parasites animalut5= new Parasites(animalInput.getType(), animalInput.getName(), animalInput.getMass(),
                                    "hungry");
                            mapaCurenta.getCell(x, y).setAnimal(animalut5);
                        }
                    }
                }
            }

            // dupa toata aia eu ipotetic am toata harta populata

            // acum incep sa vad comenzile

            ArrayList<CommandInput> allComs = inputLoader.getCommands();
            // int timestamp = 1; no more needed
            int timeLoadStart = 1;
            int timeToRecharge = 0;
            // momentan amicu nu e la incarcat
            boolean isCharging = false;
            boolean beginSim = false;
            boolean endSim = false;
            // il pun in coltul din stanga jos
            Robot amicu = new Robot(0, 0, energyPoints);

            int timeWeatherChange = 0;
            String weatherType = null;
            // asta o sa ma ajute la afisare
            boolean weatherChange = false;

            // zona de debug maxim

            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    System.out.println("Celula (" + i + ", " + j + "):\n" + mapaCurenta.getCell(i, j) + "\n------------------------");
                }
            }

            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    System.out.println("Prob la celula " + i + " " + j);
                    if (mapaCurenta.getCell(i, j).getAir() != null) {
                        System.out.println(mapaCurenta.getCell(i, j).getAir().calculateToxicity());
                    }
                    if (mapaCurenta.getCell(i, j).getSoil() != null) {
                        System.out.println(mapaCurenta.getCell(i, j).getSoil().blockProbability());
                    }
                    if (mapaCurenta.getCell(i, j).getPlant() != null) {
                        System.out.println(mapaCurenta.getCell(i, j).getPlant().getAttackProb());
                    }
                    if  (mapaCurenta.getCell(i, j).getWater() != null) {
                        System.out.println(mapaCurenta.getCell(i, j).getWater().waterQualityCalc());
                    }
                    if  (mapaCurenta.getCell(i, j).getAnimal() != null) {
                        System.out.println(mapaCurenta.getCell(i, j).getAnimal().attackProbability());
                    }

                }
            }

            // sfarsit zona debug masiv

            for (CommandInput comanda : allComs) {
                // acum cate o comanda pe rand
                if (comanda.getCommand().equals("startSimulation")) {
                    // a inceput simularea
                    if (beginSim) {
                        // Eroare - simularea a început deja
                        ObjectNode eroare = MAPPER.createObjectNode();
                        eroare.putArray("ERROR: Simulation already started. Cannot perform action");
                        output.add(eroare);
                        // trec peste ))
                        continue;
                    }
                    beginSim = true;
                    ObjectNode eBine = MAPPER.createObjectNode();
                    eBine.put("command", "startSimulation");
                    eBine.put("message","Simulation has started.");
                    eBine.put("timestamp",comanda.getTimestamp());
                    output.add(eBine);
                }
                if (comanda.getCommand().equals("endSimulation") && !isCharging) {
                    // incheie sim
                    // o pot inchide doar daca robotul nu e
                    // la incarcat, altfel sare in aer
                    if (!beginSim & !isCharging) {
                        // s a inchis ceva ce nu s a deschis))
                        ObjectNode eroare = MAPPER.createObjectNode();
                        eroare.putArray("ERROR: Simulation not started. Cannot perform action");
                        output.add(eroare);
                        // trec peste ))
                        continue;
                    }
                    endSim = true;
                    ObjectNode eBine = MAPPER.createObjectNode();
                    eBine.put("command", "endSimulation");
                    eBine.put("message","Simulation has ended.");
                    eBine.put("timestamp",comanda.getTimestamp());
                    output.add(eBine);
                }
                // am depasit sau am ajuns la finalul incarcarii -> de acum
                // se poate folosi
                if (timeLoadStart + timeToRecharge <= comanda.getTimestamp()) {
                    isCharging = false;
                }
                // pentru schimbari meteo
                // cand dispare fenomenul meteo???
                if (timeWeatherChange != 0 && timeWeatherChange + 1 <= comanda.getTimestamp()) {
                    // le fac cu formula default scorurile la aer
                    // doar pentru 2 iteratii tin conditiile meteo
                    // mai intai "debifez true de la weathercond"
                    mapaCurenta.setWeatherCheck(weatherType, 0);
                    // recalc la toata lumea -> de fiecare cand fol aer calculez efectiv
                    // mapaCurenta.recalcAir(weatherType);
                }
                // preiau comenzi daca nu a inceput o simulare
                // si daca robotelul nu este la incarcat
                if (beginSim && !endSim && !isCharging) {

                    // aici trebuie sa pun cele care se mod independent de orice
                    mapaCurenta.interc1time(comanda.getTimestamp());
                    mapaCurenta.interc2time(comanda.getTimestamp()); // asta doar la apa =)

                    // aici sunt in timpul simularii
                    if (comanda.getCommand().equals("printEnvConditions")) {
                        // patratica curenta -> data de coor robotel

                        ObjectNode result = MAPPER.createObjectNode();
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
                        ObjectNode result = MAPPER.createObjectNode();
                        result.put("command", "printMap");
                        // obt info pentru toata harta
                        ArrayNode fullMapInfo = mapaCurenta.getMapInfo();
                        result.set("output", fullMapInfo);
                        result.put("timestamp", comanda.getTimestamp());
                        output.add(result);
                    }
                    // urmeaza aici move_robot
                    if (comanda.getCommand().equals("moveRobot")) {
                        ObjectNode result = MAPPER.createObjectNode();
                        result.put("command", "moveRobot");
                        // vad returneaza fie ca s a mutat fie ca nu
                        String mutare_robot = amicu.moveRobot(mapaCurenta);
                        result.put("message", mutare_robot);
                        result.put("timestamp", comanda.getTimestamp());
                        output.add(result);
                    }
                    if (comanda.getCommand().equals("getEnergyStatus")) {
                        ObjectNode result = MAPPER.createObjectNode();
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

                        ObjectNode result = MAPPER.createObjectNode();
                        result.put("command", "rechargeBattery");
                        result.put("message", "Robot battery is charging.");
                        result.put("timestamp", comanda.getTimestamp());
                        // dupa ce il afisez ii fac ++
                        // timestamp = timestamp + timeToRecharge - 1; // increm la final de for comenzi
                        // de cand a inceput sa se incarce amicu
                        timeLoadStart = comanda.getTimestamp();
                        output.add(result);
                    }
                    if (comanda.getCommand().equals("changeWeatherConditions")) {
                        // sa vad ce fac si aici -> nu sunt toate completate; le fac pe parcurs
                        // vremea pe care o citesc afecteaza intreaga matrice
                        weatherType = comanda.getType();
                        weatherChange = true;
                        // pentru a si atribui
                        double value = 0;

                        // trebuie sa citesc ce val am (poate fi pentru mountain, rainfall, temperate)
                        if (comanda.getType().equals("peopleHiking"))
                            value = comanda.getNumberOfHikers();

                        if (comanda.getType().equals("rainfall"))
                            value = comanda.getRainfall();

                        if (comanda.getType().equals("polarStorm"))
                            value = comanda.getWindSpeed();

                        if (comanda.getType().equals("newSeason")) {
                            String season = comanda.getSeason();
                            if (season.equalsIgnoreCase("Spring"))
                                value = 1;
                            if (season.equalsIgnoreCase("Summer"))
                                value = 2;
                            if (season.equalsIgnoreCase("Fall"))
                                value = 3;
                            if (season.equalsIgnoreCase("Winter"))
                                value = 4;
                        }

                        // imi setez evenimentul meteo
                        // pentru 2 iteratii orice fac stiu ca am
                        // fenomen pe astea
                        mapaCurenta.setWeatherCheck(weatherType, value);

                        timeWeatherChange = comanda.getTimestamp();

                        ObjectNode result = MAPPER.createObjectNode();
                        result.put("command", "changeWeatherConditions");
                        result.put("message", "The weather has changed.");
                        result.put("timestamp", comanda.getTimestamp());
                        output.add(result);
                    }
                    if (comanda.getCommand().equals("scanObject")) {
                        // preiau atributele
                        if (amicu.getEnergyPoints() - 7 < 0) {
                            // nu am puncte sa mai scanez -> eroare mare

                        } else {
                            String color = comanda.getColor();
                            String smell = comanda.getSmell();
                            String sound = comanda.getSound();
                            ObjectNode result = MAPPER.createObjectNode();

                            if (color.equals("none") && smell.equals("none") && sound.equals("none")) {
                                // am apa trebuie cautata si daca da salvata + marcata ca si scanata
                                if (mapaCurenta.getCell(amicu.getX(), amicu.getY()).getWater() == null) {
                                    // nu e apa
                                    ObjectNode eroare = MAPPER.createObjectNode();
                                    eroare.put("command", comanda.getCommand());
                                    eroare.put("message", "ERROR: Object not found. Cannot perform action");
                                    eroare.put("timestamp", comanda.getTimestamp());
                                    output.add(eroare);
                                } else {
                                    // apa exista
                                    Entity apa_scan = mapaCurenta.getCell(amicu.getX(), amicu.getY()).getWater();
                                    amicu.addScanObj(apa_scan);
                                    amicu.setEnergyPoints(amicu.getEnergyPoints() - 7);
                                    // salvez cand am "constientizat" apa
                                    mapaCurenta.getCell(amicu.getX(), amicu.getY()).setStartInterWater(comanda.getTimestamp());
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
                                // trebuie sa vad daca exista pe patratica robotului asta, daca nu eroare
                                if (mapaCurenta.getCell(amicu.getX(), amicu.getY()).getPlant() == null) {
                                    // nu am plantuta, e o mica problema
                                    ObjectNode eroare = MAPPER.createObjectNode();
                                    eroare.put("command", comanda.getCommand());
                                    eroare.put("message", "ERROR: Object not found. Cannot perform action");
                                    eroare.put("timestamp", comanda.getTimestamp());
                                    output.add(eroare);
                                } else {
                                    // planta exista, trebuie adaugata in inventarul robotelului
                                    Entity plantuta_scan = mapaCurenta.getCell(amicu.getX(), amicu.getY()).getPlant();
                                    amicu.addScanObj(plantuta_scan);
                                    amicu.setEnergyPoints(amicu.getEnergyPoints() - 7);
                                    // salvez cand a inceput plantuta respectiva sa "traiasca"
                                    mapaCurenta.getCell(amicu.getX(), amicu.getY()).setStartInterPlant(comanda.getTimestamp());
                                    // o pun si pe harta pentru interactiuni
                                    mapaCurenta.getCell(amicu.getX(), amicu.getY()).setScannedPlant(true);
                                    result.put("command", "scanObject");
                                    result.put("message", "The scanned object is a plant.");
                                    result.put("timestamp", comanda.getTimestamp());
                                    output.add(result);
                                }
                            }
                            if (!color.equals("none") && !smell.equals("none") && !sound.equals("none")) {
                                // animal, not yet
                                if (mapaCurenta.getCell(amicu.getX(), amicu.getY()).getAnimal() == null) {
                                    // alta data
                                    ObjectNode eroare = MAPPER.createObjectNode();
                                    eroare.put("command", comanda.getCommand());
                                    eroare.put("message", "ERROR: Object not found. Cannot perform action");
                                    eroare.put("timestamp", comanda.getTimestamp());
                                    output.add(eroare);
                                } else {
                                    // animal exista, trebuie adaugata in inventarul robotelului
                                    Entity animal_scan = mapaCurenta.getCell(amicu.getX(), amicu.getY()).getAnimal();
                                    amicu.addScanObj(animal_scan);
                                    amicu.setEnergyPoints(amicu.getEnergyPoints() - 7);
                                    // salvez cand a inceput plantuta respectiva sa "traiasca"
                                    mapaCurenta.getCell(amicu.getX(), amicu.getY()).setStartInterAnimal(comanda.getTimestamp());
                                    // o pun si pe harta pentru interactiuni
                                    mapaCurenta.getCell(amicu.getX(), amicu.getY()).setScannedAnimal(true);
                                    result.put("command", "scanObject");
                                    result.put("message", "The scanned object is an animal.");
                                    result.put("timestamp", comanda.getTimestamp());
                                    output.add(result);
                                }
                            }
                        }
                    }
                } else {
                    // nu s a pornit simularea sau a fost inchisa
                    if (!beginSim) {
                        // nu a fost deschisa
                        ObjectNode eroare = MAPPER.createObjectNode();
                        eroare.put("command", comanda.getCommand());
                        eroare.put("message", "ERROR: Simulation not started. Cannot perform action");
                        eroare.put("timestamp", comanda.getTimestamp());
                        output.add(eroare);
                    }
                    if (endSim && !comanda.getCommand().equals("endSimulation")) {
                        // s a terminat... papa
                        ObjectNode eroare = MAPPER.createObjectNode();
                        eroare.put("command", comanda.getCommand());
                        eroare.put("message", "ERROR: Simulation not started. Cannot perform action");
                        eroare.put("timestamp", comanda.getTimestamp());
                        output.add(eroare);
                    }
                    // aici mi se incarca jucaria si nu pot inchide simularea
                    if (isCharging && comanda.getCommand().equals("endSimulation")) {
                        // vreau sa inchid si amicu se incarca
                        // error
                        ObjectNode eroare = MAPPER.createObjectNode();
                        eroare.put("command", comanda.getCommand());
                        eroare.put("message", "ERROR: Robot still charging. Cannot perform action");
                        eroare.put("timestamp", comanda.getTimestamp());
                        output.add(eroare);
                    }
                    if (isCharging) {
                        // orice com am primit nu o pot exec
                        // robotul e la incarcat
                        // daca n am noroc se va afisa eroare
                        ObjectNode eroare = MAPPER.createObjectNode();
                        eroare.put("command", comanda.getCommand());
                        eroare.put("message", "ERROR: Robot still charging. Cannot perform action");
                        eroare.put("timestamp", comanda.getTimestamp());
                        output.add(eroare);

                    }
                }
                System.out.println("DUpa timestamp: " + comanda.getTimestamp() + " robotu are: " + amicu.getEnergyPoints());
                // timestamp++;
            }
            /*
             * TODO Implement your function here
             *
             * How to add output to the output array?
             * There are multiple ways to do this, here is one example:
             *
             *
             * ObjectNode objectNode = MAPPER.createObjectNode();
             * objectNode.put("field_name", "field_value");
             *
             * ArrayNode arrayNode = MAPPER.createArrayNode();
             * arrayNode.add(objectNode);
             *
             * output.add(arrayNode);
             * output.add(objectNode);
             *
             */

            File outputFile = new File(outputPath);
            outputFile.getParentFile().mkdirs();
            WRITER.writeValue(outputFile, output);
        }
    }
}