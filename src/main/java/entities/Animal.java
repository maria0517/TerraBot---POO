package entities;

import map.MapA;

public abstract class Animal extends Entity {
    // ce e de la Entity
    // ce mai are ea
    private String type; // de care e
    private String state;

    private boolean mutareOk = true;
    private int foodType = 0;

    // constructorul animalului
    public Animal(final String type, final String name, final double mass, final String state) {
        super(name, mass);
        this.type = type;
        this.state = state;
    }

    /**
     * @return tipAnimal
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(final String type) {
        this.type = type;
    }

    /**
     * @return coordNoi animal
     */
    // asta e generala pentru toata lumea
    // vreau sa returnez noua pozitie pe care ar avea o noul animal
    public int[] move(final MapA mapaCurenta, final int x, final int y) {
        // actually, pe asta o apelez pentru un animal scanat
        // fac eu astea din map
        // aici o sa am un animal caruia ii voi gasi patratica pe care vrea sa se mute
        // mutarea efectiva e altceva
        double maxQualWater = -1;
        int xNew = 0;
        int yNew = 0;
        // TREBUIE SA FIE SCANATE
        if (mapaCurenta.verifCell(x, y + 1) && mapaCurenta.getCell(x, y + 1).getPlant() != null
                && mapaCurenta.getCell(x, y + 1).getWater() != null && mapaCurenta.getCell(x,
                y + 1).getScannedPlant() && mapaCurenta.getCell(x, y + 1).getScannedWater()) {
            // incep si eu cu dreapta
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
                && mapaCurenta.getCell(x + 1, y).getWater() != null && mapaCurenta.getCell(x + 1,
                y).getScannedWater() && mapaCurenta.getCell(x + 1, y).getScannedPlant()) { // jos
            double apaScor = mapaCurenta.getCell(x + 1, y).getWater().waterQualityCalc();
            if (apaScor > maxQualWater) {
                maxQualWater = apaScor;
                xNew = x + 1;
                yNew = y;
            }
        }
        if (mapaCurenta.verifCell(x, y - 1) && mapaCurenta.getCell(x, y - 1).getPlant() != null
                && mapaCurenta.getCell(x, y - 1).getWater() != null && mapaCurenta.getCell(x,
                y - 1).getScannedWater() && mapaCurenta.getCell(x, y - 1).getScannedPlant()) {
            // stnaga
            double apaScor = mapaCurenta.getCell(x, y - 1).getWater().waterQualityCalc();
            if (apaScor > maxQualWater) {
                maxQualWater = apaScor;
                xNew = x;
                yNew = y - 1;
            }
        }
        if (mapaCurenta.verifCell(x - 1, y) && mapaCurenta.getCell(x - 1, y).getPlant() != null
                && mapaCurenta.getCell(x - 1, y).getWater() != null && mapaCurenta.getCell(x - 1,
                y).getScannedWater() && mapaCurenta.getCell(x - 1, y).getScannedPlant()) {
            // si aici sus
            double apaScor = mapaCurenta.getCell(x - 1, y).getWater().waterQualityCalc();
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
                    && mapaCurenta.getCell(x, y + 1).getScannedPlant()) {
                return new int[]{x, y + 1};
            }
            if (mapaCurenta.verifCell(x + 1, y) && mapaCurenta.getCell(x + 1, y).getPlant() != null
                    && mapaCurenta.getCell(x + 1, y).getScannedPlant()) {
                return new int[]{x + 1, y};
            }
            if (mapaCurenta.verifCell(x, y - 1) && mapaCurenta.getCell(x, y - 1).getPlant() != null
                    && mapaCurenta.getCell(x, y - 1).getScannedPlant()) {
                return new int[]{x, y - 1};
            }
            if (mapaCurenta.verifCell(x - 1, y) && mapaCurenta.getCell(x - 1, y).getPlant() != null
                    && mapaCurenta.getCell(x - 1, y).getScannedPlant()) {
                return new int[]{x - 1, y};
            }
            //  daca am ajuns pana aici inseamna ca nu e nicio planta
            // ma duc pe apa ; am tot maxQualWater -1

            if (mapaCurenta.verifCell(x, y + 1) && mapaCurenta.getCell(x, y + 1).getWater() != null
                    && mapaCurenta.getCell(x, y + 1).getScannedWater()) {
                double apaScor =  mapaCurenta.getCell(x, y + 1).getWater().waterQualityCalc();
                if (apaScor > maxQualWater) {
                    maxQualWater = apaScor;
                    xNew = x;
                    yNew = y + 1;
                }
            }
            if (mapaCurenta.verifCell(x, y - 1) && mapaCurenta.getCell(x, y - 1).getWater() != null
                    && mapaCurenta.getCell(x, y - 1).getScannedWater()) {
                double apaScor =  mapaCurenta.getCell(x, y - 1).getWater().waterQualityCalc();
                if (apaScor > maxQualWater) {
                    maxQualWater = apaScor;
                    xNew = x;
                    yNew = y - 1;
                }
            }
            if (mapaCurenta.verifCell(x - 1, y) && mapaCurenta.getCell(x - 1, y).getWater() != null
                    && mapaCurenta.getCell(x - 1, y).getScannedWater()) {
                double apaScor =  mapaCurenta.getCell(x - 1, y).getWater().waterQualityCalc();
                if (apaScor > maxQualWater) {
                    maxQualWater = apaScor;
                    xNew = x - 1;
                    yNew = y;
                }
            }
            if (mapaCurenta.verifCell(x + 1, y) && mapaCurenta.getCell(x + 1, y).getWater() != null
                    && mapaCurenta.getCell(x + 1, y).getScannedWater()) {
                double apaScor =  mapaCurenta.getCell(x + 1, y).getWater().waterQualityCalc();
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
                if (mapaCurenta.verifCell(x - 1, y)) {
                    return new int[]{x - 1, y};
                }
                if (mapaCurenta.verifCell(x, y + 1)) {
                    return new int[]{x, y + 1};
                }
                if (mapaCurenta.verifCell(x + 1, y)) {
                    return new int[]{x + 1, y};
                }
                if (mapaCurenta.verifCell(x, y - 1)) {
                    return new int[]{x, y - 1};
                }
            }
        }
        // n ajune aici niciodata
        return new int[]{-1, -1};
    }

    // asta depinde de animal

    /**
     * @return attackProb
     */
    public abstract double attackProbability();

    /**
     * @return masa
     */
    @Override
    public double getMass() {
        return super.getMass();
    }

    /**
     *
     * @return state
     */
    public String getState() {
        return state;
    }

    /**
     *
     * @param state
     */
    public void setState(final String state) {
        this.state = state;
    }

    /**
     *
     * @return ok la mutare
     */
    public boolean isMutareOk() {
        return mutareOk;
    }

    /**
     * @param mutareOk
     */
    public void setMutareOk(final boolean mutareOk) {
        this.mutareOk = mutareOk;
    }

    /**
     * @param foodType
     */
    public void setFoodType(final int foodType) {
        this.foodType = foodType;
    }

    /**
     *
     * @return tip hrana
     */
    public int getFoodType() {
        return foodType;
    }
}
