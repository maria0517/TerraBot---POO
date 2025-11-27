package entities;

import com.fasterxml.jackson.databind.node.ObjectNode;
import constants.Const;

public final class DesertAir extends Air {
    private double dustParticles;
    private double maxScore = Const.MAX_SCORE_DES_AIR;
    private boolean desertStormCheck = false;

    public DesertAir(final String type, final String name, final double mass, final double humidity,
            final double temperature, final double oxygenLevel, final double dustParticles) {
        super(type, name, mass, humidity, temperature, oxygenLevel);
        this.dustParticles = dustParticles;
    }

    @Override
    public void setWeather(final String fenomen, final double value) {
        // aici imi trebuie doar fenomenul
        if (fenomen.equals("desertStorm")) {
            desertStormCheck = !desertStormCheck;
        }
    }

    @Override
    public double airQualityCalc() {
        double airQualBrute = (this.getOxygenLevel() * Const.UN_DOI) - (dustParticles
                * Const.DOUA_ZECIMI) - (this.getTemperature() * Const.O_TREIME);
        airQualBrute = Math.max(0, Math.min(Const.O_SUTA_DBL, airQualBrute));
        if (desertStormCheck) {
            airQualBrute -= (desertStormCheck ? Const.TREI_ZECI : 0);
        }
        return Math.round(airQualBrute * Const.O_SUTA_DBL) / Const.O_SUTA_DBL;
    }

    @Override
    public double calculateToxicity() {
        double toxicityAQ = Const.O_SUTA_DBL * (1 - airQualityCalc() / maxScore);
        toxicityAQ = Math.max(0, Math.min(Const.O_SUTA_DBL, toxicityAQ));
        return Math.round(toxicityAQ * Const.O_SUTA_DBL) / Const.O_SUTA_DBL;
    }


    @Override
    public boolean isToxic() {
        return calculateToxicity() > (Const.ZERO_OPT * maxScore);
    }

    // getter + setter
    public void setDustParticles(final double dustParticles) {
        this.dustParticles = dustParticles;
    }
    public double getDustParticles() {
        return dustParticles;
    }

    @Override
    public void extraDisp(final ObjectNode airInfo, final boolean wheaterChange) {
        if (wheaterChange) {
            airInfo.put("desertStorm", desertStormCheck);
        } else {
            airInfo.put("dustParticles", getDustParticles());
        }
    }

    @Override
    public String toString() {
        return (name != null ? name : "-");
    }

}
