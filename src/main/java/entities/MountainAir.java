package entities;

import com.fasterxml.jackson.databind.node.ObjectNode;
import constants.Const;

public final class MountainAir extends Air {
    private double altitude;
    private double maxScore = Const.MAX_SCORE_MOUNT_AIR;
    private int numberOfHikers;
    private boolean peopleHiking = false;

    public MountainAir(final String type, final String name, final double mass,
           final double humidity, final double temperature,
           final double oxygenLevel, final double altitude) {
        super(type, name, mass, humidity, temperature, oxygenLevel);
        this.altitude = altitude;
    }

    public int getNumberOfHikers() {
        return numberOfHikers;
    }

    @Override
    public void setWeather(final String fenomen, final double value) {
        if (fenomen.equals("PeopleHiking")) {
            peopleHiking = true;
            numberOfHikers = (int) value;
        }
    }

    @Override
    public double airQualityCalc() {
        double oxygenFactor = this.getOxygenLevel() - (this.altitude / Const.O_MIE
                * Const.O_JUMATE);
        double airQualBrute = (oxygenFactor * Const.UN_DOI) + (this.getHumidity()
                * Const.ZERO_SASE);
        airQualBrute = Math.max(0, Math.min(Const.O_SUTA_DBL, airQualBrute));
        if (peopleHiking) {
            airQualBrute -= numberOfHikers * Const.O_ZECIME;
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

    // Getter și setter corectat
    public void setAltitude(final double altitude) {
        this.altitude = altitude;
    }

    public double getAltitude() {
        return altitude;
    }

    @Override
    public void extraDisp(final ObjectNode airInfo, final boolean wheaterChange) {
        airInfo.put("altitude", altitude);
    }

    @Override
    public String toString() {
        return (name != null ? name : "-");
    }

}
