package entities;

import com.fasterxml.jackson.databind.node.ObjectNode;
import constants.Const;

public final class TropicalAir extends Air {
    private double co2Level;
    private double maxScore = Const.MAX_SCORE_TROP_AIR;
    private double rainfall;
    private boolean rainfallCheck = false;

    public TropicalAir(final String type, final String name, final double mass,
           final double humidity, final double temperature, final double oxygenLevel,
           final double co2Level) {
        super(type, name, mass, humidity, temperature, oxygenLevel);
        this.co2Level = co2Level;
    }

    @Override
    public void setWeather(final String fenomen, final double value) {
        if (fenomen.equals("rainfall")) {
            rainfallCheck = true;
            rainfall = value;
        }
    }

    @Override
    public double airQualityCalc() {
        double airQualBrute = (this.getOxygenLevel() * Const.UN_DOI) + (this.getHumidity()
                * Const.O_JUMATE) - (this.co2Level * Const.O_ZEC_ZECIME);
        airQualBrute = Math.max(0, Math.min(Const.O_SUTA_DBL, airQualBrute));
        if (rainfallCheck) {
            // mai adaug ceva
            airQualBrute += rainfall * Const.O_TREIME;
        }
        return Math.round(airQualBrute * Const.O_SUTA_DBL) / Const.O_SUTA_DBL;
    }

    @Override
    public double calculateToxicity() {
        double toxicityAQ = Const.O_SUTA_DBL * (1 - airQualityCalc() / maxScore);
        // trebuie si aici normalizat Doamne fereste
        toxicityAQ = Math.max(0, Math.min(Const.O_SUTA_DBL, toxicityAQ));
        return Math.round(toxicityAQ * Const.O_SUTA_DBL) / Const.O_SUTA_DBL;
    }

    @Override
    public boolean isToxic() {
        return calculateToxicity() > (Const.ZERO_OPT * maxScore);
    }

    // getter + setter
    public void setCo2Level(final double co2Level) {
        this.co2Level = co2Level;
    }
    public double getCo2Level() {
        return co2Level;
    }

    @Override
    public void extraDisp(final ObjectNode airInfo, final boolean wheaterChange) {
        airInfo.put("co2Level", Math.round(co2Level * Const.O_SUTA_DBL) / Const.O_SUTA_DBL);
    }

    @Override
    public String toString() {
        return (name != null ? name : "-");
    }

}
