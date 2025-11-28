package entities;

import com.fasterxml.jackson.databind.node.ObjectNode;
import constants.Const;

public final class TemperateAir extends Air {
    private double pollenLevel;
    private double maxScore = Const.MAX_SCORE_TEMP_AIR;
    private boolean newSeasoncheck = false;
    private String season;

    public TemperateAir(final String type, final String name, final double mass,
             final double humidity, final double temperature,
             final double oxygenLevel, final double pollenLevel) {
        super(type, name, mass, humidity, temperature, oxygenLevel);
        this.pollenLevel = pollenLevel;
    }

    // setez prostia
    @Override
    public void setWeather(final String fenomen, final double value) {
        if (fenomen.equals("newSeason")) {
            newSeasoncheck = !newSeasoncheck;
            // pt a determina sezonul
            int valueInt = (int) value;
            switch (valueInt) {
                case 1:
                    season = "Spring";
                    break;
                case 2:
                    season = "Summer";
                    break;
                case Const.UN_TREI:
                    season = "Fall";
                    break;
                case Const.UN_PATRU:
                    season = "Winter";
                    break;
                default:
                    season = null;
                    break;
            }
        }
    }

    @Override
    public double airQualityCalc() {
        double airQualBrute = (this.getOxygenLevel() * Const.UN_DOI)
                + (this.getHumidity() * Const.ZERO_SAPTE) - (pollenLevel * Const.O_ZECIME);
        airQualBrute = Math.max(0, Math.min(Const.O_SUTA_DBL, airQualBrute));
        if (newSeasoncheck && season != null) {
            // adaug
            double seasonPenalty = season.equalsIgnoreCase("Spring") ? Const.FOR_SPRING : 0;
            airQualBrute = airQualBrute - seasonPenalty;
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
    public void setPollenLevel(final double pollenLevel) {
        this.pollenLevel = pollenLevel;
    }

    public double getPollenLevel() {
        return pollenLevel;
    }

    @Override
    public void extraDisp(final ObjectNode airInfo, final boolean wheaterChange) {
        airInfo.put("pollenLevel", pollenLevel);
    }

    @Override
    public String toString() {
        return (name != null ? name : "-");
    }


}
