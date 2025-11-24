package Map;

// import entitati
import Entities.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;


public class Cell {
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

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int calcEntites() {
        int nrEntities = 0;
        if (plant != null)
            nrEntities++;
        if (animal != null)
            nrEntities++;
        if (water != null)
            nrEntities++;
        return nrEntities;
    }

    public int calcAllEntities() {
        int nrEntities = 0;
        if (plant != null)
            nrEntities++;
        if (animal != null)
            nrEntities++;
        if (soil != null)
            nrEntities++;
        if (air != null)
            nrEntities++;
        return nrEntities;
    }

    public double calculateScore() {
        double score = 0;
        if (soil != null)
            score = score + soil.blockProbability();
        if (plant != null)
            score = score + plant.getAttackProb();
        if (animal != null)
            score = score + animal.attackProbability();
        if (air != null) {
            score = score + air.calculateToxicity();
        }
        return score;
    }

    public ObjectNode getEnvironmentInfo(boolean weatherChange) {
        // fac mapper local ca ala din main e private (railor)
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

    public void applyImprov(String improvmentType, Entity elemToAdd, String type) {
        // Aplică îmbunătățirile corespunzătoare
        if (improvmentType.equals("plantVegetation")) {
            // pentru planta -> o adaug in celula
            //  this.plant = (Plant) elemToAdd;
            // this.plant.setType(type);

            // cresc nivelul de oxigen
            this.air.setOxygenLevel(this.air.getOxygenLevel() + 0.3);
        }
        if (improvmentType.equals("fertilizeSoil")) {
            // aici la animal; eu efectiv il trantesc aici??
            // ma mai gandesc
            // asta se face sigur
            this.soil.setOrganicMatter(this.soil.getOrganicMatter() + 0.3);
        }
        if (improvmentType.equals("increaseHumidity") || improvmentType.equals("increaseMoisture")) {
            // la apa -> adaug apa si modific ce e de mod
            // this.water = (Water) elemToAdd;
            // this.water.setType(type);
            if (improvmentType.equals("increaseHumidity") && this.air != null)
                this.air.setHumidity(this.air.getHumidity() + 0.2);
            if (improvmentType.equals("increaseMoisture") && this.soil != null)
                this.soil.setWaterRetention(this.soil.getWaterRetention() + 0.2);
        }
    }

    // getteri si setteri pentru entitati

    public int getX() { return x; }
    public int getY() { return y; }
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

    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public void setSoil(Soil soil) {
        this.soil = soil;
    }
    public void setPlant(Plant plant) {
        this.plant = plant;
    }
    public void setAnimal(Animal animal) {
        this.animal = animal;
    }
    public void setWater(Water water) {
        this.water = water;
    }
    public void setAir(Air air) {
        this.air = air;
    }

    public void setScannedPlant(boolean scannedPlant) {
        this.scannedPlant = scannedPlant;
    }

    public boolean getScannedPlant() {
        return this.scannedPlant;
    }

    public void setStartInterPlant(int timeStart) {
        this.startInterPlant = timeStart;
    }

    public int getStartInterPlant() {
        return startInterPlant;
    }

    public boolean getScannedWater() {
        return this.scannedWater;
    }

    public void setScannedWater(boolean scannedWater) {
        this.scannedWater = scannedWater;
    }

    public int getStartInterWater() {
        return startInterWater;
    }

    public void setStartInterWater(int startInterWater) {
        this.startInterWater = startInterWater;
    }

    public boolean getScannedAnimal() {
        return this.scannedAnimal;
    }

    public void setScannedAnimal(boolean scannedAnimal) {
        this.scannedAnimal = scannedAnimal;
    }

    public int getStartInterAnimal() {
        return startInterAnimal;
    }

    public void setStartInterAnimal(int startInterAnimal) {
        this.startInterAnimal = startInterAnimal;
    }

    // pentru debug
    @Override
    public String toString() {
        return "Cell:\n" +
                "  Plant : " + plant + "\n" +
                "  Animal: " + animal + "\n" +
                "  Soil  : " + soil + "\n" +
                "  Water : " + water + "\n" +
                "  Air   : " + air;
    }

}
