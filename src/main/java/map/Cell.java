package map;

// import entitati
import constants.Const;
import entities.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;


public final class Cell {
    // o celula din mapa -> aici pun ce se poate
    private int x;
    private int y;

    private Soil soil;
    private Plant plant;
    private Animal animal;
    private Water water;
    private Air air;

    private boolean scannedPlant = false;
    // momentan nu am start
    private int startInterPlant = 0;

    private boolean scannedWater = false;
    private int startInterWater = 0;

    private boolean scannedAnimal = false;
    private int startInterAnimal = 0;

    public Cell(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    /**
     *
     * @return nr entitati total
     */
    public int calcEntites() {
        int nrEntities = 0;
        if (plant != null) {
            nrEntities++;
        }
        if (animal != null) {
            nrEntities++;
        }
        if (water != null) {
            nrEntities++;
        }
        return nrEntities;
    }

    /**
     * @return nr entitati care influent robotelul
     */
    public int calcAllEntities() {
        int nrEntities = 0;
        if (plant != null) {
            nrEntities++;
        }
        if (animal != null) {
            nrEntities++;
        }
        if (soil != null) {
            nrEntities++;
        }
        if (air != null) {
            nrEntities++;
        }
        return nrEntities;
    }

    /**
     * calculare scor
     */
    public double calculateScore() {
        double score = 0;
        if (soil != null) {
            score = score + soil.blockProbability();
        }
        if (plant != null) {
            score = score + plant.getAttackProb();
        }
        if (animal != null) {
            score = score + animal.attackProbability();
        }
        if (air != null) {
            score = score + air.calculateToxicity();
        }
        return score;
    }

    /**
     * metoda afisare env info
     */
    public ObjectNode getEnvironmentInfo(final boolean weatherChange) {
        // fac mapper local ca ala din main e privat
        ObjectMapper localMapper = new ObjectMapper();
        ObjectNode cellInfo = localMapper.createObjectNode();

        // Soil
        if (this.soil != null) {
            ObjectNode soilInfo = localMapper.createObjectNode();
            soilInfo.put("type", this.soil.getType());
            soilInfo.put("name", this.soil.getName());
            soilInfo.put("mass", this.soil.getMass());
            soilInfo.put("nitrogen", this.soil.getNitrogen());
            soilInfo.put("waterRetention", this.soil.getWaterRetention());
            soilInfo.put("soilpH", this.soil.getSoilpH());
            soilInfo.put("organicMatter", this.soil.getOrganicMatter());
            soilInfo.put("soilQuality", this.soil.getSoilQuality());

            this.soil.extraDisp(soilInfo);

            cellInfo.set("soil", soilInfo);
        }

        // planta
        if (this.plant != null) {
            ObjectNode plantInfo = localMapper.createObjectNode();
            plantInfo.put("type", this.plant.getType());
            plantInfo.put("name", this.plant.getName());
            plantInfo.put("mass", this.plant.getMass());
            cellInfo.set("plants", plantInfo);
        }

        // animale
        if (this.animal != null) {
            ObjectNode animalInfo = localMapper.createObjectNode();
            animalInfo.put("type", this.animal.getType());
            animalInfo.put("name", this.animal.getName());
            animalInfo.put("mass", this.animal.getMass());
            cellInfo.set("animals", animalInfo);
        }

        // apa
        if (this.water != null) {
            ObjectNode waterInfo = localMapper.createObjectNode();
            waterInfo.put("type", this.water.getType());
            waterInfo.put("name", this.water.getName());
            waterInfo.put("mass", this.water.getMass());
            cellInfo.set("water", waterInfo);
        }

        // aeru
        if (this.air != null) {
            ObjectNode airInfo = localMapper.createObjectNode();
            airInfo.put("type", this.air.getType());
            airInfo.put("name", this.air.getName());
            airInfo.put("mass", this.air.getMass());
            airInfo.put("humidity", this.air.getHumidity());
            airInfo.put("temperature", this.air.getTemperature());
            airInfo.put("oxygenLevel", this.air.getOxygenLevel());
            airInfo.put("airQuality", this.air.getAirQuality());

            this.air.extraDisp(airInfo, weatherChange);
            // pun tot
            cellInfo.set("air", airInfo);
        }
        return cellInfo;
    }

    /**
     * functie pentru aplicare improv
     */
    public void applyImprov(final String improvmentType) {
        // aplic imbunatatiri
        if (improvmentType.equals("plantVegetation")) {
            // cresc nivelul de oxigen
            this.air.setOxygenLevel(this.air.getOxygenLevel() + Const.O_TREIME);
        }
        if (improvmentType.equals("fertilizeSoil")) {
            this.soil.setOrganicMatter(this.soil.getOrganicMatter() + Const.O_TREIME);
        }
        if (improvmentType.equals("increaseHumidity")
                || improvmentType.equals("increaseMoisture")) {
            // la apa -> amodific dupa improv
            if (improvmentType.equals("increaseHumidity") && this.air != null) {
                this.air.setHumidity(this.air.getHumidity() + Const.DOUA_ZECIMI);
            }
            if (improvmentType.equals("increaseMoisture") && this.soil != null) {
                this.soil.setWaterRetention(this.soil.getWaterRetention() + Const.DOUA_ZECIMI);
            }
        }
    }

    // getteri si setteri pentru entitati

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public Soil getSoil() {
        return soil;
    }
    public Plant getPlant() {
        return plant;
    }
    public Animal getAnimal() {
        return animal;
    }
    public Water getWater() {
        return water;
    }
    public Air getAir() {
        return air;
    }

    public void setX(final int x) {
        this.x = x;
    }
    public void setY(final int y) {
        this.y = y;
    }
    public void setSoil(final Soil soil) {
        this.soil = soil;
    }
    public void setPlant(final Plant plant) {
        this.plant = plant;
    }
    public void setAnimal(final Animal animal) {
        this.animal = animal;
    }
    public void setWater(final Water water) {
        this.water = water;
    }
    public void setAir(final Air air) {
        this.air = air;
    }

    public void setScannedPlant(final boolean scannedPlant) {
        this.scannedPlant = scannedPlant;
    }

    public boolean getScannedPlant() {
        return this.scannedPlant;
    }

    public void setStartInterPlant(final int timeStart) {
        this.startInterPlant = timeStart;
    }

    public int getStartInterPlant() {
        return startInterPlant;
    }

    public boolean getScannedWater() {
        return this.scannedWater;
    }

    public void setScannedWater(final boolean scannedWater) {
        this.scannedWater = scannedWater;
    }

    public int getStartInterWater() {
        return startInterWater;
    }

    public void setStartInterWater(final int startInterWater) {
        this.startInterWater = startInterWater;
    }

    public boolean getScannedAnimal() {
        return this.scannedAnimal;
    }

    public void setScannedAnimal(final boolean scannedAnimal) {
        this.scannedAnimal = scannedAnimal;
    }

    public int getStartInterAnimal() {
        return startInterAnimal;
    }

    public void setStartInterAnimal(final int startInterAnimal) {
        this.startInterAnimal = startInterAnimal;
    }

    // pentru debug
    @Override
    public String toString() {
        return "Cell:\n" +
                "  Plant : " + plant + " daca e scan: " + getScannedPlant() + "\n" +
                "  Animal: " + animal + "daca e scan "+ getScannedAnimal() + "\n" +
                "  Soil  : " + soil + "\n" +
                "  Water : " + water + " daca e scan "+ getScannedAnimal() + "\n" +
                "  Air   : " + air;
    }

}
