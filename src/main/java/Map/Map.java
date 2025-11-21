package Map;

import Entities.Animal;
import Entities.TropicalAir;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Map {
    // aici matricea de celule
    private Cell[][] mapaEfec;
    private int width;
    private int height;
    // pentru robot
    private int energyPoints;

    public Map(int width, int height, int energyPoints) {
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
    // modifica de cand sunt puse

    public int getEnergyPoints() { return energyPoints; }
    public void setEnergyPoints(int energyPoints) { this.energyPoints = energyPoints; }

    public int getWidth() {
        return width;
    }

    public Cell getCell(int x, int y) {
        return mapaEfec[x][y];
    }

    public boolean verifCell(int x, int y) {
        // tu ti pisicii tai de egallll!!!!!!!!!!
        if (x < 0 || x > width - 1 || y < 0 || y > height - 1)
            return false;
        return true;
    }

    public ArrayNode getMapInfo() {
        ObjectMapper localMapper = new ObjectMapper();
        ArrayNode mapInfo = localMapper.createArrayNode();

        // incep parcurgerea
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // introduc pentru cate o celula
                ObjectNode cellInfo = localMapper.createObjectNode();
                Cell cell = mapaEfec[x][y];

                // Section array [x, y]
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

                // adaug ce am facut mai bine
                mapInfo.add(cellInfo);
            }
        }
        return mapInfo;
    }

    public void setWeatherCheck(String weatherCheck, double value) {
        // pentru fiecare celula verif daca are aer de tip X
        // si il setez pe opusul sau (treu / false)
        for (int i = 0; i <= width - 1; i++) {
            for (int j = 0; j <= height - 1; j++) {
                // merg din celula in celula si verific daca am
                if (weatherCheck.equals("rainfall")) {
                    // caut de tropialAir
                    if (mapaEfec[i][j].getAir().getType().equals("TropicalAir")) {
                        // am celula care este de tip asa
                        mapaEfec[i][j].getAir().setWeather("rainfall", value);
                    }
                }
                if (weatherCheck.equals("polarStorm")) {
                    // polarAir
                    if  (mapaEfec[i][j].getAir().getType().equals("PolarAir")) {
                        mapaEfec[i][j].getAir().setWeather("polarStorm", value);
                    }
                }
                if (weatherCheck.equals("newSeason")) {
                    // temperat
                    if (mapaEfec[i][j].getAir().getType().equals("TemperateAir"))
                        mapaEfec[i][j].getAir().setWeather("newSeason", value);
                }
                if (weatherCheck.equals("desertStorm")) {
                    // desertaciune
                    if (mapaEfec[i][j].getAir().getType().equals("DesertAir")) {
                        mapaEfec[i][j].getAir().setWeather("desertStorm", value);
                    }
                }
                if (weatherCheck.equals("peopleHiking")) {
                    // munte
                    if (mapaEfec[i][j].getAir().getType().equals("PeopleHiking")) {
                        mapaEfec[i][j].getAir().setWeather("peopleHiking", value);
                    }
                }
            }
        }
    }

    // le separ in interactiuni de 1 timestamp si de 2 timestamp
    public void interc1time(int timestampCurent) {
        // parcurg si vad unde am interactiuni
        for (int i = 0; i <= width - 1; i++) {
            for (int j = 0; j <= height - 1; j++) {
                // Soil -> Plant
                // Plant -> Air
                // vad daca este scanata plantuta si verific daca a trecut o iter de cand
                // am scanat o
                if (mapaEfec[i][j].getPlant() != null && mapaEfec[i][j].getScannedPlant() &&
                mapaEfec[i][j].getStartInterPlant() + 1 <= timestampCurent) {
                    // am voie sa stric chestii))
                    // System.out.println("sunt si eu aici, poate sunt bine" + timestampCurent);
                    // cresc mai intai planta
                    mapaEfec[i][j].getPlant().grow();
                    // l am rotunjit ca facea urat la afisare
                    double O2New = Math.round((mapaEfec[i][j].getPlant().getTotalOxygen() + mapaEfec[i][j].getAir().getOxygenLevel()) * 100.0) / 100.0;
                    // acum trebuie sa mod calitatea aerului
                    mapaEfec[i][j].getAir().setOxygenLevel(O2New);
                    // si cam asta e ipotetic
                }
                // Water -> Plant
                if (mapaEfec[i][j].getPlant() != null && mapaEfec[i][j].getScannedPlant() &&
                        mapaEfec[i][j].getStartInterPlant() + 1 <= timestampCurent &&
                    mapaEfec[i][j].getWater() != null && mapaEfec[i][j].getScannedWater() &&
                    mapaEfec[i][j].getStartInterWater() + 1 <= timestampCurent) {

                }
                // Aer -> Animal
                if (mapaEfec[i][j].getAnimal() != null && mapaEfec[i][j].getScannedAnimal() &&
                        mapaEfec[i][j].getStartInterAnimal() + 1 <= timestampCurent) {
                    // verific sa vad daca nu cumva e toxic aerul pentru animal

                }
                // Animal -> Soil
                if (mapaEfec[i][j].getAnimal() != null && mapaEfec[i][j].getScannedAnimal() &&
                        mapaEfec[i][j].getStartInterAnimal() + 1 <= timestampCurent) {
                    // daca animalul este well-fed, produce ingrasamant
                    if (mapaEfec[i][j].getAnimal().getState().equals("well-fed")) {
                        // cresc organic matter la sol
                        double newOM = mapaEfec[i][j].getSoil().getOrganicMatter();
                        newOM += 0.2;
                        newOM = Math.round(newOM * 100.0) / 100.0;
                        mapaEfec[i][j].getSoil().setOrganicMatter(newOM);
                    }
                }
            }
        }
    }

    public void interc2time(int timestampCurent) {
        for (int i = 0; i <= width - 1; i++) {
            for (int j = 0; j <= height - 1; j++) {
                // Apa -> Aer
                // Apa -> Sol
                // Atentie: aici trebuie la cate 2 iteratii -> fac cu restul
                if (mapaEfec[i][j].getWater() != null && mapaEfec[i][j].getScannedWater() &&
                        mapaEfec[i][j].getStartInterWater() % 2 == timestampCurent % 2) {
                    // acum trebuie sa vedem ce se modifica
                    // +0.1 la waterRetention sol
                    double newWaterRet = mapaEfec[i][j].getSoil().getWaterRetention();
                    newWaterRet += 0.1;
                    newWaterRet = Math.round(newWaterRet * 100.0) / 100.0;
                    mapaEfec[i][j].getSoil().setWaterRetention(newWaterRet);
                    // +0.1 la humidity aer
                    double newHumid = mapaEfec[i][j].getAir().getHumidity();
                    newHumid += 0.1;
                    newHumid = Math.round(newHumid * 100.0) / 100.0;
                    mapaEfec[i][j].getAir().setHumidity(newHumid);
                }
                // mutare animal
                if (mapaEfec[i][j].getAnimal() != null && mapaEfec[i][j].getScannedAnimal() &&
                mapaEfec[i][j].getStartInterAnimal() % 2 == timestampCurent % 2 && mapaEfec[i][j].getAnimal().isMutareOk()) {
                    // aici trebuie sa l mut si sa l fac sa manance
                    int[] newCoords = mapaEfec[i][j].getAnimal().move(this, i, j);
                    System.out.println("animalul x: " + mapaEfec[i][j].getAnimal().getName() +
                            " trebuie mutat de la " + i + " " + j + " la " + newCoords[0] + " " + newCoords[1] +
                            " cand l am miscat: " + timestampCurent);
                    // hai sa mut animalul
                    // trebuie date atributele lui noi celule
                    Animal animal = mapaEfec[i][j].getAnimal();
                    mapaEfec[i][j].setAnimal(null);
                    mapaEfec[newCoords[0]][newCoords[1]].setAnimal(animal);
                    mapaEfec[newCoords[0]][newCoords[1]].setScannedAnimal(true);
                    mapaEfec[i][j].setScannedWater(false);
                    mapaEfec[newCoords[0]][newCoords[1]].setStartInterAnimal(mapaEfec[i][j].getStartInterAnimal());
                    mapaEfec[i][j].setStartInterAnimal(0);
                    // il setez ca nu il mai pot muta pe perioada acestui timestamp
                    mapaEfec[newCoords[0]][newCoords[1]].getAnimal().setMutareOk(false);
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
