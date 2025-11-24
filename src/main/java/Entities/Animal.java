package Entities;

import Map.*;

public abstract class Animal extends Entity {
    // ce e de la Entity
    // ce mai are ea
    private String type; // de care e
    private String state;
    private double intakeRate = 0.08;

    private boolean mutareOk = true;
    private int foodType = 0;

    // constructorul animalului
    public Animal(String type, String name, double mass, String state) {
        super(name, mass);
        this.type = type;
        this.state = state;
    }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    // unde trebuie puse eat si move
//    public void eats(Animal animal) {
//        // trebuie sa verific de care e
//        if (animal.type.equals("Carnivore") || animal.type.equals("Parasite")) {
//            // verific daca exista animal pe patratica
//            // daca este cresc masa animalului
//            // modific solul -> vedem cum facem asta
//        } else {
//            // ierbivor
//        }
//    }

    // asta e generala pentru toata lumea
    // vreau sa returnez noua pozitie pe care ar avea o noul animal
    public int[] move(MapA mapaCurenta, int x, int y) {
        // actually, pe asta o apelez pentru un animal scanat
        // fac eu astea din map
        // aici o sa am un animal caruia ii voi gasi patratica pe care vrea sa se mute;
        // mutarea efectiva e altceva
        // asa, acum sa vad ce am
        double maxQualWater = -1;
        int xNew = 0;
        int yNew = 0;
        // TREBUIE SA FIE SCANATE
        if (mapaCurenta.verifCell(x, y + 1) && mapaCurenta.getCell(x, y + 1).getPlant() != null
                && mapaCurenta.getCell(x, y + 1).getWater() != null && mapaCurenta.getCell(x, y + 1).getScannedPlant()
                && mapaCurenta.getCell(x, y + 1).getScannedWater()) { // incep si eu cu dreapta
            // am si apa si planta undeva
            // vad daca e maxim
            double apaScor = mapaCurenta.getCell(x, y + 1).getWater().waterQualityCalc();
            if (apaScor > maxQualWater) {
                maxQualWater = apaScor;
                xNew = x;
                yNew = y + 1;
            }
        }
        if (mapaCurenta.verifCell(x + 1, y) && mapaCurenta.getCell(x + 1, y).getPlant() != null
                && mapaCurenta.getCell(x + 1, y).getWater() != null && mapaCurenta.getCell(x + 1, y).getScannedWater()
                && mapaCurenta.getCell(x + 1, y).getScannedPlant()) { // jos
            // am si apa si planta undeva
            // vad daca e maxim
            double apaScor = mapaCurenta.getCell(x + 1,y).getWater().waterQualityCalc();
            if (apaScor > maxQualWater) {
                maxQualWater = apaScor;
                xNew = x + 1;
                yNew = y;
            }
        }
        if (mapaCurenta.verifCell(x, y - 1) && mapaCurenta.getCell(x, y - 1).getPlant() != null
                && mapaCurenta.getCell(x, y - 1).getWater() != null && mapaCurenta.getCell(x, y -1).getScannedWater()
                && mapaCurenta.getCell(x, y - 1).getScannedPlant()) { // stnaga
            // am si apa si planta undeva
            // vad daca e maxim
            double apaScor = mapaCurenta.getCell(x,y - 1).getWater().waterQualityCalc();
            if (apaScor > maxQualWater) {
                maxQualWater = apaScor;
                xNew = x;
                yNew = y - 1;
            }
        }
        if (mapaCurenta.verifCell(x - 1, y) && mapaCurenta.getCell(x - 1, y).getPlant() != null
                && mapaCurenta.getCell(x - 1, y).getWater() != null && mapaCurenta.getCell(x - 1, y).getScannedWater()
                && mapaCurenta.getCell(x - 1, y).getScannedPlant()) { // si aici sus
            // am si apa si planta undeva
            // vad daca e maxim
            double apaScor = mapaCurenta.getCell(x - 1,y).getWater().waterQualityCalc();
            if (apaScor > maxQualWater) {
                maxQualWater = apaScor;
                xNew = x - 1;
                yNew = y;
            }
        }
        // ipotetic acum am scorul
        if (maxQualWater != -1) {
            // am gasit una -> gata
            return new int[]{xNew, yNew};
        } else {
            // n am gasit, vad unde este doar planta ; tot logica de la robot
            if (mapaCurenta.verifCell(x, y + 1) && mapaCurenta.getCell(x, y + 1).getPlant() != null
                    && mapaCurenta.getCell(x, y + 1).getScannedPlant())
                return new int[]{x, y + 1};

            if (mapaCurenta.verifCell(x + 1, y) && mapaCurenta.getCell(x + 1, y).getPlant() != null
                    && mapaCurenta.getCell(x + 1, y).getScannedPlant())
                return new int[]{x + 1, y};

            if (mapaCurenta.verifCell(x, y - 1) && mapaCurenta.getCell(x, y - 1).getPlant() != null &&
                    mapaCurenta.getCell(x, y - 1).getScannedPlant())
                return new int[]{x, y - 1};

            if (mapaCurenta.verifCell(x - 1, y) && mapaCurenta.getCell(x - 1, y).getPlant() != null
                    && mapaCurenta.getCell(x - 1, y).getScannedPlant())
                return new int[]{x - 1, y};

            //  daca am ajuns pana aici inseamna ca nu e nicio planta
            // ma duc pe apa ; am tot maxQualWater -1

            if (mapaCurenta.verifCell(x, y + 1) && mapaCurenta.getCell(x, y + 1).getWater() != null &&
                    mapaCurenta.getCell(x, y + 1).getScannedWater()) {
                double apaScor =  mapaCurenta.getCell(x,y + 1).getWater().waterQualityCalc();
                if (apaScor > maxQualWater) {
                    maxQualWater = apaScor;
                    xNew = x;
                    yNew = y + 1;
                }
            }
            if (mapaCurenta.verifCell(x, y - 1) && mapaCurenta.getCell(x, y - 1).getWater() != null &&
                    mapaCurenta.getCell(x, y - 1).getScannedWater()) {
                double apaScor =  mapaCurenta.getCell(x,y - 1).getWater().waterQualityCalc();
                if (apaScor > maxQualWater) {
                    maxQualWater = apaScor;
                    xNew = x;
                    yNew = y - 1;
                }
            }
            if (mapaCurenta.verifCell(x - 1, y) && mapaCurenta.getCell(x - 1, y).getWater() != null &&
                    mapaCurenta.getCell(x - 1, y).getScannedWater()) {
                double apaScor =  mapaCurenta.getCell(x - 1,y).getWater().waterQualityCalc();
                if (apaScor > maxQualWater) {
                    maxQualWater = apaScor;
                    xNew = x - 1;
                    yNew = y;
                }
            }
            if (mapaCurenta.verifCell(x + 1, y) && mapaCurenta.getCell(x + 1, y).getWater() != null &&
                    mapaCurenta.getCell(x + 1, y).getScannedWater()) {
                double apaScor =  mapaCurenta.getCell(x + 1,y).getWater().waterQualityCalc();
                if (apaScor > maxQualWater) {
                    maxQualWater = apaScor;
                    xNew = x + 1;
                    yNew = y;
                }
            }
            if (maxQualWater != -1) {
                // am si eu o celula
                return new int[]{xNew, yNew};
            } else {
                // ajung sa le caut pe bucatele ca la robot
                if (mapaCurenta.verifCell(x, y + 1))
                    return new int[]{x, y + 1};
                if (mapaCurenta.verifCell(x + 1, y))
                    return new int[]{x + 1, y};
                if (mapaCurenta.verifCell(x, y - 1))
                    return new int[]{x, y - 1};
                if (mapaCurenta.verifCell(x - 1, y))
                    return new int[]{x - 1, y};
            }
        }
        // n ajune aici niciodata
        return new int[]{-1, -1};
    }

    // asta depinde de animal
    public abstract double attackProbability();

    // public abstract void eat();

    @Override
    public double getMass() {
        return super.getMass();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean isMutareOk() {
        return mutareOk;
    }

    public void setMutareOk(boolean mutareOk) {
        this.mutareOk = mutareOk;
    }

    public void setFoodType(int foodType) { this.foodType = foodType; }

    public int getFoodType() { return foodType; }
}
