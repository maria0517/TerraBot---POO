package map;

import constants.Const;
import entities.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.TerritorySectionParamsInput;


public final class MapA {
    // aici matricea de celule
    private Cell[][] mapaEfec;
    private int width;
    private int height;
    // pentru robot
    private int energyPoints;

    public MapA(final int width, final int height, final int energyPoints) {
        this.width = width;
        this.height = height;
        this.energyPoints = energyPoints;
        this.mapaEfec = new Cell[width][height];

        // init mapa in constructor
        for (int i = 0; i <= width - 1; i++) {
            for (int j = 0; j <= height - 1; j++) {
                this.mapaEfec[i][j] = new Cell(i, j);
            }
        }
    }

    // getteri si setteri pentru orice (de fapt fara width si height ca alea nu se mai
    // modifica de cand sunt puse)

    public int getEnergyPoints() {
        return energyPoints;
    }
    public void setEnergyPoints(final int energyPoints) {
        this.energyPoints = energyPoints;
    }

    public int getWidth() {
        return width;
    }

    /**
     * @param x
     * @param y
     * @return
     */
    public Cell getCell(final int x, final int y) {
        return mapaEfec[x][y];
    }

    /**
     * @param x
     * @param y
     * @return
     */
    public boolean verifCell(final int x, final int y) {
        // verificare coord
        if (x < 0 || x > width - 1 || y < 0 || y > height - 1) {
            return false;
        }
        return true;
    }

    /**
     * populare mapa
     */
    public void populMap(final TerritorySectionParamsInput territorySectionParams) {
        var parametri = territorySectionParams;

        if (parametri.getAir() != null) {
            for (var airInput : parametri.getAir()) {
                if ("TropicalAir".equals(airInput.getType())) {
                    for (var section : airInput.getSections()) {
                        int x = section.getX();
                        int y = section.getY();

                        // incep sa creez aerul
                        TropicalAir air1 = new TropicalAir(airInput.getType(),
                                airInput.getName(), airInput.getMass(), airInput.getHumidity(),
                                airInput.getTemperature(), airInput.getOxygenLevel(),
                                airInput.getCo2Level());
                        this.getCell(x, y).setAir(air1);
                    }
                } else if ("PolarAir".equals(airInput.getType())) {
                    for (var section : airInput.getSections()) {
                        int x = section.getX();
                        int y = section.getY();

                        PolarAir air2 = new PolarAir(airInput.getType(),
                                airInput.getName(), airInput.getMass(), airInput.getHumidity(),
                                airInput.getTemperature(), airInput.getOxygenLevel(),
                                airInput.getIceCrystalConcentration());
                        this.getCell(x, y).setAir(air2);
                    }
                } else if ("TemperateAir".equals(airInput.getType())) {
                    for (var section : airInput.getSections()) {
                        int x = section.getX();
                        int y = section.getY();

                        TemperateAir air3 = new TemperateAir(airInput.getType(),
                                airInput.getName(), airInput.getMass(), airInput.getHumidity(),
                                airInput.getTemperature(), airInput.getOxygenLevel(),
                                airInput.getPollenLevel());
                        this.getCell(x, y).setAir(air3);
                    }
                } else if ("DesertAir".equals(airInput.getType())) {
                    for (var section : airInput.getSections()) {
                        int x = section.getX();
                        int y = section.getY();

                        DesertAir air4 = new DesertAir(airInput.getType(),
                                airInput.getName(), airInput.getMass(), airInput.getHumidity(),
                                airInput.getTemperature(), airInput.getOxygenLevel(),
                                airInput.getDustParticles());
                        this.getCell(x, y).setAir(air4);
                    }
                } else {
                    for (var section : airInput.getSections()) {
                        int x = section.getX();
                        int y = section.getY();

                        // incep sa creez aerul
                        MountainAir air5 = new MountainAir(airInput.getType(),
                                airInput.getName(), airInput.getMass(), airInput.getHumidity(),
                                airInput.getTemperature(), airInput.getOxygenLevel(),
                                airInput.getAltitude());
                        this.getCell(x, y).setAir(air5);
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
                        ForestSoil solul1 = new ForestSoil(soilInput.getType(),
                                soilInput.getName(), soilInput.getMass(), soilInput.getNitrogen(),
                                soilInput.getWaterRetention(), soilInput.getSoilpH(),
                                soilInput.getOrganicMatter(), soilInput.getLeafLitter());

                        // acum pun in celula
                        this.getCell(x, y).setSoil(solul1);
                    }
                } else if ("DesertSoil".equals(soilInput.getType())) {
                    for (var section : soilInput.getSections()) {
                        int x = section.getX();
                        int y = section.getY();

                       DesertSoil solul3 = new DesertSoil(soilInput.getType(), soilInput.getName(),
                       soilInput.getMass(), soilInput.getNitrogen(), soilInput.getWaterRetention(),
                     soilInput.getSoilpH(), soilInput.getOrganicMatter(), soilInput.getSalinity());

                        this.getCell(x, y).setSoil(solul3);
                    }
                } else if ("TundraSoil".equals(soilInput.getType())) {
                    for (var section : soilInput.getSections()) {
                        int x = section.getX();
                        int y = section.getY();
                        // creez TundraSoil
                      TundraSoil solul4 = new TundraSoil(soilInput.getType(), soilInput.getName(),
                       soilInput.getMass(), soilInput.getNitrogen(), soilInput.getWaterRetention(),
                       soilInput.getSoilpH(), soilInput.getOrganicMatter(),
                              soilInput.getPermafrostDepth());

                        this.getCell(x, y).setSoil(solul4);
                    }
                } else if ("SwampSoil".equals(soilInput.getType())) {
                    for (var section : soilInput.getSections()) {
                        int x = section.getX();
                        int y = section.getY();
                        // creez SwampSoil cu toate prop
                        SwampSoil solul4 = new SwampSoil(soilInput.getType(), soilInput.getName(),
                       soilInput.getMass(), soilInput.getNitrogen(), soilInput.getWaterRetention(),
                       soilInput.getSoilpH(), soilInput.getOrganicMatter(),
                                soilInput.getWaterLogging());

                        // acum pun in celula
                        this.getCell(x, y).setSoil(solul4);
                    }
                } else {
                    for (var section : soilInput.getSections()) {
                        int x = section.getX();
                        int y = section.getY();
                        GrasslandSoil solul5 = new GrasslandSoil(soilInput.getType(),
                        soilInput.getName(), soilInput.getMass(), soilInput.getNitrogen(),
                        soilInput.getWaterRetention(), soilInput.getSoilpH(),
                                soilInput.getOrganicMatter(), soilInput.getRootDensity());

                        this.getCell(x, y).setSoil(solul5);
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
                    Water apaaa = new Water(waterInput.getType(), waterInput.getName(),
                    waterInput.getMass(), waterInput.getPurity(), waterInput.getSalinity(),
                    waterInput.getTurbidity(), waterInput.getContaminantIndex(),
                            waterInput.getPH(), waterInput.isFrozen());

                    // il atribui
                    this.getCell(x, y).setWater(apaaa);
                }
            }
        }
        if (parametri.getPlants() != null) {
            for (var plantInput : parametri.getPlants()) {
                if ("FloweringPlants".equals(plantInput.getType())) {
                    for (var section : plantInput.getSections()) {
                        int x = section.getX();
                        int y = section.getY();

                        FloweringPlants planta1 = new FloweringPlants(plantInput.getType(),
                          plantInput.getName(), plantInput.getMass(), "young");
                        this.getCell(x, y).setPlant(planta1);
                    }
                } else if ("GymnospermsPlants".equals(plantInput.getType())) {
                    for (var section : plantInput.getSections()) {
                        int x = section.getX();
                        int y = section.getY();

                        GymnospermsPlants planta2 = new GymnospermsPlants(plantInput.getType(),
                          plantInput.getName(), plantInput.getMass(), "young");
                        this.getCell(x, y).setPlant(planta2);
                    }
                } else if ("Ferns".equals(plantInput.getType())) {
                    for (var section : plantInput.getSections()) {
                        int x = section.getX();
                        int y = section.getY();

                        Ferns planta3 = new Ferns(plantInput.getType(), plantInput.getName(),
                            plantInput.getMass(), "young");
                        this.getCell(x, y).setPlant(planta3);
                    }
                } else if ("Mosses".equals(plantInput.getType())) {
                    for (var section : plantInput.getSections()) {
                        int x = section.getX();
                        int y = section.getY();

                        Mosses planta4 = new Mosses(plantInput.getType(), plantInput.getName(),
                            plantInput.getMass(), "young");
                        this.getCell(x, y).setPlant(planta4);
                    }
                } else {
                    // algele
                    for (var section : plantInput.getSections()) {
                        int x = section.getX();
                        int y = section.getY();

                        Algae planta5 = new Algae(plantInput.getType(), plantInput.getName(),
                            plantInput.getMass(), "young");
                        this.getCell(x, y).setPlant(planta5);
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

                        Carnivores animalut1 = new Carnivores(animalInput.getType(),
                        animalInput.getName(), animalInput.getMass(), "hungry");
                        this.getCell(x, y).setAnimal(animalut1);
                    }
                } else if ("Herbivores".equals(animalInput.getType())) {
                    for (var section : animalInput.getSections()) {
                        int x = section.getX();
                        int y = section.getY();

                        Herbivores animalut2 = new Herbivores(animalInput.getType(),
                        animalInput.getName(), animalInput.getMass(), "hungry");
                        this.getCell(x, y).setAnimal(animalut2);
                    }
                } else if ("Detritivores".equals(animalInput.getType())) {
                    for (var section : animalInput.getSections()) {
                        int x = section.getX();
                        int y = section.getY();

                        Detritivores animalut3 = new Detritivores(animalInput.getType(),
                        animalInput.getName(), animalInput.getMass(), "hungry");
                        this.getCell(x, y).setAnimal(animalut3);
                    }
                } else if ("Omnivores".equals(animalInput.getType())) {
                    for (var section : animalInput.getSections()) {
                        int x = section.getX();
                        int y = section.getY();

                        Omnivores animalut4 = new Omnivores(animalInput.getType(),
                        animalInput.getName(), animalInput.getMass(), "hungry");
                        this.getCell(x, y).setAnimal(animalut4);
                    }
                } else {
                    // paraziitiiii
                    for (var section : animalInput.getSections()) {
                        int x = section.getX();
                        int y = section.getY();

                        Parasites animalut5 = new Parasites(animalInput.getType(),
                        animalInput.getName(), animalInput.getMass(), "hungry");
                        this.getCell(x, y).setAnimal(animalut5);
                    }
                }
            }
        }
    }

    /**
     *
     * @return infoMap
     */
    public ArrayNode getMapInfo() {
        ObjectMapper localMapper = new ObjectMapper();
        ArrayNode mapInfo = localMapper.createArrayNode();

        // incep parcurgerea
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // introduc pentru cate o celula
                ObjectNode cellInfo = localMapper.createObjectNode();
                Cell cell = mapaEfec[x][y];

                // section array [x, y]
                ArrayNode section = localMapper.createArrayNode();
                section.add(x);
                section.add(y);
                cellInfo.set("section", section);

                // apel pentru nr de entitati
                int nrEntities = cell.calcEntites();
                cellInfo.put("totalNrOfObjects", nrEntities);

                // air qual si soil qual
                cellInfo.put("airQuality", cell.getAir().getAirQualityString());
                cellInfo.put("soilQuality", cell.getSoil().soilQualityString());

                // adaug ce am facut
                mapInfo.add(cellInfo);
            }
        }
        return mapInfo;
    }

    /**
     * @param weatherCheck
     * @param value
     * @return
     */
    public boolean setWeatherCheck(final String weatherCheck, final double value) {
        // pentru fiecare celula verif daca are aer de tip X
        // si il setez pe opusul sau (treu / false)
        boolean gasitAer = false;
        for (int i = 0; i <= width - 1; i++) {
            for (int j = 0; j <= height - 1; j++) {
                // merg din celula in celula si verific daca am
                if (weatherCheck.equals("rainfall")) {
                    // caut de tropialAir
                    if (mapaEfec[i][j].getAir().getType().equals("TropicalAir")) {
                        // am celula care este de tip asa
                        mapaEfec[i][j].getAir().setWeather("rainfall", value);
                        // am gasit -> schimbare valida
                        gasitAer = true;
                    }
                }
                if (weatherCheck.equals("polarStorm")) {
                    // polarAir
                    if  (mapaEfec[i][j].getAir().getType().equals("PolarAir")) {
                        mapaEfec[i][j].getAir().setWeather("polarStorm", value);
                        gasitAer = true;
                    }
                }
                if (weatherCheck.equals("newSeason")) {
                    // temperat
                    if (mapaEfec[i][j].getAir().getType().equals("TemperateAir")) {
                        mapaEfec[i][j].getAir().setWeather("newSeason", value);
                        gasitAer = true;
                    }
                }
                if (weatherCheck.equals("desertStorm")) {
                    // desertaciune
                    if (mapaEfec[i][j].getAir().getType().equals("DesertAir")) {
                        mapaEfec[i][j].getAir().setWeather("desertStorm", value);
                        gasitAer = true;
                    }
                }
                if (weatherCheck.equals("peopleHiking")) {
                    // munte
                    if (mapaEfec[i][j].getAir().getType().equals("MountainAir")) {
                        mapaEfec[i][j].getAir().setWeather("peopleHiking", value);
                        gasitAer = true;
                    }
                }
            }
        }
        return gasitAer;
    }

    /**
     * le separ in interactiuni de 1 timestamp si de 2 timestamp
     */
    public void interc1time(final int timestampCurent) {
        // parcurg si vad unde am interactiuni
        for (int i = 0; i <= width - 1; i++) {
            for (int j = 0; j <= height - 1; j++) {
                // Aer -> Animal
                if (mapaEfec[i][j].getAnimal() != null && mapaEfec[i][j].getScannedAnimal()
                        && mapaEfec[i][j].getStartInterAnimal() + 1 <= timestampCurent) {
                    // verific sa vad daca nu cumva e toxic aerul pentru animal
                    if (mapaEfec[i][j].getAir().isToxic()) {
                        // aerul e toxic pentru animal -> devine sick
                        mapaEfec[i][j].getAnimal().setState("sick");
                    }
                }
                // Plant -> Air + Soil -> Plant
                // vad daca este scanata plantuta si verific daca a trecut o iter de cand
                // am scanat o aici fac si cu crescutul; e doi in unu
                if (mapaEfec[i][j].getPlant() != null && mapaEfec[i][j].getScannedPlant()
                        && mapaEfec[i][j].getStartInterPlant() + 1 <= timestampCurent) {
                    // cresc mai intai planta
                    mapaEfec[i][j].getPlant().grow();
                    // verific daca nu s a dus
                    if (mapaEfec[i][j].getPlant().getStatus().equals("dead")) {
                        // adio plantuta =((
                        mapaEfec[i][j].setPlant(null);
                        mapaEfec[i][j].setScannedPlant(false);
                        mapaEfec[i][j].setStartInterPlant(0);
                    } else {
                        // e ok si merg mai departe
                        // l am rotunjit ca facea urat la afisare
                        double o2New = Math.round((mapaEfec[i][j].getPlant().getTotalOxygen()
                            + mapaEfec[i][j].getAir().getOxygenLevel()) * Const.O_SUTA_DBL)
                                / Const.O_SUTA_DBL;
                        // acum trebuie sa mod calitatea aerului
                        mapaEfec[i][j].getAir().setOxygenLevel(o2New);
                    }

                }
                // Water -> Plant
                if (mapaEfec[i][j].getPlant() != null && mapaEfec[i][j].getScannedPlant()
                        && mapaEfec[i][j].getStartInterPlant() + 1 <= timestampCurent
                        && mapaEfec[i][j].getWater() != null && mapaEfec[i][j].getScannedWater()
                        && mapaEfec[i][j].getStartInterWater() + 1 <= timestampCurent) {
                    // aici trebuie sa cresc planta cu 0.2
                    mapaEfec[i][j].getPlant().grow();
                    if (mapaEfec[i][j].getPlant().getStatus().equals("dead")) {
                        // adio plantuta =((
                        mapaEfec[i][j].setPlant(null);
                        mapaEfec[i][j].setScannedPlant(false);
                        mapaEfec[i][j].setStartInterPlant(0);
                    }
                }
                // Animal -> Soil
                if (mapaEfec[i][j].getAnimal() != null && mapaEfec[i][j].getScannedAnimal()
                        && mapaEfec[i][j].getStartInterAnimal() + 1 <= timestampCurent) {
                    // daca animalul este well-fed, produce ingrasamant
                    if (mapaEfec[i][j].getAnimal().getState().equals("well-fed")) {
                        // cresc organic matter la sol
                        double newOM = mapaEfec[i][j].getSoil().getOrganicMatter();
                        if (mapaEfec[i][j].getAnimal().getFoodType() == 2) {
                            // a mancat si plnta si apa
                            newOM += Const.ZERO_OPT;
                        } else {
                            newOM += Const.O_JUMATE;
                        }
                        newOM = Math.round(newOM * Const.O_SUTA_DBL) / Const.O_SUTA_DBL;
                        mapaEfec[i][j].getSoil().setOrganicMatter(newOM);
                    }
                }
            }
        }
    }

    /**
     *
     * interactiuni la 2 timestamp (mostly apa)
     */
    public void interc2time(final int timestampCurent) {
        for (int i = 0; i <= width - 1; i++) {
            for (int j = 0; j <= height - 1; j++) {
                // Apa -> Aer
                // Apa -> Sol
                // atentie: aici trebuie la cate 2 iteratii -> fac cu restul
                if (mapaEfec[i][j].getWater() != null && mapaEfec[i][j].getScannedWater()
                        && mapaEfec[i][j].getStartInterWater() % 2 == timestampCurent % 2) {
                    // acum trebuie sa vad ce se modifica
                    // +0.1 la waterRetention sol
                    double newWaterRet = mapaEfec[i][j].getSoil().getWaterRetention();
                    newWaterRet += Const.O_ZECIME;
                    newWaterRet = Math.round(newWaterRet * Const.O_SUTA_DBL) / Const.O_SUTA_DBL;
                    mapaEfec[i][j].getSoil().setWaterRetention(newWaterRet);
                    // +0.1 la humidity aer
                    double newHumid = mapaEfec[i][j].getAir().getHumidity();
                    newHumid += Const.O_ZECIME;
                    newHumid = Math.round(newHumid * Const.O_SUTA_DBL) / Const.O_SUTA_DBL;
                    mapaEfec[i][j].getAir().setHumidity(newHumid);
                }
            }
        }
    }

    /**
     * separat pentru animale
     * hranire + miscare
     */
    public void intercAnimal(final int timestampCurent) {
        for (int i = 0; i <= width - 1; i++) {
            for (int j = 0; j <= height - 1; j++) {
                // momentan nu a baut apa
                int waterDrank = 0;
                // mutare animal
                if (mapaEfec[i][j].getAnimal() != null && mapaEfec[i][j].getScannedAnimal()
                        && mapaEfec[i][j].getStartInterAnimal() % 2 == timestampCurent % 2
                        && mapaEfec[i][j].getAnimal().isMutareOk()) {
                    // aici trebuie sa l mut si sa l fac sa manance
                    int[] newCoords = mapaEfec[i][j].getAnimal().move(this, i, j);
                    int preyEaten = 0;

                    // acum am unde trebuie sa l mut, luat dupa celelalte criterii
                    // trebuie sa incep sa verific care e treaba cu patratica pe care
                    // ma duc sa vad daca am animal de tip carnivor / parazit
                    if (mapaEfec[i][j].getAnimal().getType().equals("Carnivores")
                            || mapaEfec[i][j].getAnimal().getType().equals("Parasites")) {
                        // trebuie sa vad daca am prada
                        if (mapaEfec[newCoords[0]][newCoords[1]].getAnimal() != null) {
                            // pe celula pe care vreau sa ma duc am un animal
                            Animal prey = mapaEfec[newCoords[0]][newCoords[1]].getAnimal();
                            preyEaten = 1;
                            // iau masa lu amicu ala + il setez ca a mancat
                            mapaEfec[i][j].getAnimal().setMass(prey.getMass()
                                 + mapaEfec[i][j].getAnimal().getMass());
                            mapaEfec[i][j].getAnimal().setState("well-fed");
                            mapaEfec[newCoords[0]][newCoords[1]].setAnimal(null);
                        }
                        // daca nu exista se trateaza normal
                    }
                    // hai sa mut animalul
                    // trebuie date atributele lui noi celule
                    Animal animal = mapaEfec[i][j].getAnimal();
                    mapaEfec[i][j].setAnimal(null);
                    mapaEfec[newCoords[0]][newCoords[1]].setAnimal(animal);
                    mapaEfec[newCoords[0]][newCoords[1]].setScannedAnimal(true);
                    mapaEfec[newCoords[0]][newCoords[1]].setStartInterAnimal(
                            mapaEfec[i][j].getStartInterAnimal());
                    mapaEfec[i][j].setStartInterAnimal(0);
                    mapaEfec[i][j].setScannedAnimal(false);
                    // il setez ca nu il mai pot muta pe perioada acestui timestamp
                    mapaEfec[newCoords[0]][newCoords[1]].getAnimal().setMutareOk(false);

                    // trebuie sa si manance
                    // nu mai trec pe aici decat doar daca nu a mancat
                    if (preyEaten == 0) {
                        // toate animalele devin la fel; ma pun frumos sa vad ce am
                        // in celula ca sa stiu ce pot manca
                        if (mapaEfec[newCoords[0]][newCoords[1]].getPlant() != null
                            && mapaEfec[newCoords[0]][newCoords[1]].getScannedPlant()
                            && mapaEfec[newCoords[0]][newCoords[1]].getWater() != null
                           && mapaEfec[newCoords[0]][newCoords[1]].getScannedWater()) {
                            // am si apa si planta
                            waterDrank = 1;
                            // calculez cat are de baut
                            double waterToDrink = Math.min(mapaEfec[newCoords[0]][newCoords[1]]
                                 .getAnimal().getMass() * Const.WATERINTAKE,
                                    mapaEfec[newCoords[0]][newCoords[1]].getWater().getMass());
                            mapaEfec[newCoords[0]][newCoords[1]].getWater().setMass(
                                mapaEfec[newCoords[0]][newCoords[1]].getWater().getMass()
                                            - waterToDrink);
                            // verific daca mai am sau nu apa
                            if (mapaEfec[newCoords[0]][newCoords[1]].getWater().getMass() <= 0) {
                                // trebuie stearsa apa
                                mapaEfec[newCoords[0]][newCoords[1]].setWater(null);
                                mapaEfec[newCoords[0]][newCoords[1]].setScannedWater(false);
                                mapaEfec[newCoords[0]][newCoords[1]].setStartInterWater(0);
                            }
                            // pun noua masa
                            mapaEfec[newCoords[0]][newCoords[1]].getAnimal().setMass(
                                    mapaEfec[newCoords[0]][newCoords[1]].getPlant().getMass()
                                            + waterToDrink);
                            mapaEfec[newCoords[0]][newCoords[1]].getAnimal().setFoodType(2);
                            // oricum ar fi la final omor plantuta
                            // omor plantuta
                            mapaEfec[newCoords[0]][newCoords[1]].setPlant(null);
                            mapaEfec[newCoords[0]][newCoords[1]].setScannedPlant(false);
                            mapaEfec[newCoords[0]][newCoords[1]].setStartInterPlant(0);
                        } else {
                            // nu prea inteleg dc vrea sa verific daca exista animalul, da lasa asa
                            // dubla verificare n a omorat pe nimeni inca
                            if (mapaEfec[newCoords[0]][newCoords[1]].getPlant() != null
                                    && mapaEfec[newCoords[0]][newCoords[1]].getScannedPlant()
                                    && mapaEfec[newCoords[0]][newCoords[1]].getAnimal() != null) {
                                // am doar plantuta
                                // dc sa verific asta nene
                                mapaEfec[newCoords[0]][newCoords[1]].getAnimal().setMass(
                                        mapaEfec[newCoords[0]][newCoords[1]].getPlant().getMass()
                                   + mapaEfec[newCoords[0]][newCoords[1]].getAnimal().getMass());
                                mapaEfec[newCoords[0]][newCoords[1]].getAnimal().setFoodType(1);
                                // oricum ar fi la final omor plantuta
                                // omor plantuta
                                mapaEfec[newCoords[0]][newCoords[1]].setPlant(null);
                                mapaEfec[newCoords[0]][newCoords[1]].setScannedPlant(false);
                                mapaEfec[newCoords[0]][newCoords[1]].setStartInterPlant(0);
                            }
                        }
                    }

                }
                // asta va fi pentru apa indiferent de ploaie vant, asta o fac mereu
                // Animal -> Water 2.0
                if (mapaEfec[i][j].getAnimal() != null && mapaEfec[i][j].getScannedAnimal()
                        && mapaEfec[i][j].getWater() != null && mapaEfec[i][j].getScannedWater()
                        && waterDrank == 0) {
                    // cum am si la cazul planta + apa
                    double waterToDrink = Math.min(mapaEfec[i][j].getAnimal().getMass()
                           * Const.WATERINTAKE, mapaEfec[i][j].getWater().getMass());
                    // pun noua masa
                    mapaEfec[i][j].getAnimal().setMass(mapaEfec[i][j].getAnimal().getMass()
                            + waterToDrink);
                    // sigur trebuie sa scad din masa apei
                    mapaEfec[i][j].getWater().setMass(mapaEfec[i][j].getWater().getMass()
                            - waterToDrink);
                    // verific daca nu s a terminat apa
                    if (mapaEfec[i][j].getWater().getMass() <= 0) {
                        // trebuie stearsa apa
                        mapaEfec[i][j].setWater(null);
                        mapaEfec[i][j].setScannedWater(false);
                        mapaEfec[i][j].setStartInterWater(0);
                    }
                    mapaEfec[i][j].getAnimal().setFoodType(1);
                }
            }
        }
        for  (int i = 0; i <= width - 1; i++) {
            for (int j = 0; j <= height - 1; j++) {
                // aici debifez toate animalele
                if (mapaEfec[i][j].getAnimal() != null) {
                    mapaEfec[i][j].getAnimal().setMutareOk(true);
                }
            }
        }
    }
}
