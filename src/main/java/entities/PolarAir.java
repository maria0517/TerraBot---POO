package entities;

import com.fasterxml.jackson.databind.node.ObjectNode;
import constants.Const;

public final class PolarAir extends Air {
    private double iceCrystalConcentration;
    private double maxScore = Const.MAX_SCORE_POL_AIR;
    private double windSpeed;
    private boolean polarStormCheck = false;

    public PolarAir(final String type, final String name, final double mass, final double humidity,
        final double temperature, final double oxygenLevel, final double iceCrystalConcentration) {
        super(type, name, mass, humidity, temperature, oxygenLevel);
        this.iceCrystalConcentration = iceCrystalConcentration;
    }

    @Override
    public void setWeather(final String fenomen, final double value) {
        if (fenomen.equals("polarStorm")) {
            polarStormCheck = !polarStormCheck;
            this.windSpeed = value;
        }
    }

    @Override
    public double airQualityCalc() {
        double airQualBrute = (this.getOxygenLevel() * Const.UN_DOI) + (Const.O_SUTA_DBL
           - Math.abs(this.getTemperature())) - (iceCrystalConcentration * Const.O_JUM_ZECIME);
        airQualBrute = Math.max(0, Math.min(Const.O_SUTA_DBL, airQualBrute));
        if (polarStormCheck) {
            airQualBrute -= windSpeed * Const.DOUA_ZECIMI;
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
    public void setIceCrystalConcentration(final double iceCrystalConcentration) {
        this.iceCrystalConcentration = iceCrystalConcentration;
    }
    public double getIceCrystalConcentration() {
        return iceCrystalConcentration;
    }

    @Override
    public void extraDisp(final ObjectNode airInfo, final boolean wheaterChange) {
        if (wheaterChange) {
            airInfo.put("polarStorm", windSpeed);
        } else {
            airInfo.put("iceCrystalConcentration", getIceCrystalConcentration());
        }
    }

    @Override
    public String toString() {
        return (name != null ? name : "-");
    }

}
