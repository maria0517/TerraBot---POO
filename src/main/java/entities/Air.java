package entities;

import com.fasterxml.jackson.databind.node.ObjectNode;
import constants.Const;

public abstract class Air extends Entity {
    private String type;
    private double humidity;
    private double temperature;
    private double oxygenLevel;
    private double airQuality;

    public Air(final String type, final String name, final double mass, final double humidity,
               final double temperature, final double oxygenLevel) {
        super(name, mass);
        this.type = type;
        this.humidity = humidity;
        this.temperature = temperature;
        this.oxygenLevel = oxygenLevel;
    }

    /**
     * @return calc scor calitate aer
     */
    public abstract double airQualityCalc();
    /**
     * @return toxicity
     */
    public abstract double calculateToxicity();
    /**
     * @return daca e toxic sau nu pentru robotel
     */
    public abstract boolean isToxic();

    /**
     * @param fenomen
     * @param value
     */
    public abstract void setWeather(String fenomen, double value);

    /**
     * pentru afisare
     */
    public abstract void extraDisp(ObjectNode airInfo, boolean wheaterChange);

    /**
     * comun pentru toti ca sa dau mesaj cu calitate aer
     */
    public String getAirQualityString() {
        double airQualDone = airQualityCalc();
        // norm + rotunjire
        if (airQualDone >= Const.GOOD_QUAL) {
            return "good";
        }
        if (airQualDone >= Const.MEDIUM_QUAL) {
            return "moderate";
        }
        return "poor";
    }

    /**
     *
     * @return type
     */
    public String getType() {
        return type;
    }
    /**
     *
     * @return humid
     */
    public double getHumidity() {
        return humidity;
    }
    /**
     * @return temp
     */
    public double getTemperature() {
        return temperature;
    }
    /**
     * @return oxygenLev
     */
    public double getOxygenLevel() {
        return oxygenLevel;
    }
    /**
     *
     * @return airQual
     */
    public double getAirQuality() {
        return airQualityCalc();
    }
    /**
     *
     * @param type
     */
    public void setType(final String type) {
        this.type = type;
    }
    /**
     *
     * @param humidity
     */
    public void setHumidity(final double humidity) {
        this.humidity = humidity;
    }
    /**
     *
     * @param temperature
     */
    public void setTemperature(final double temperature) {
        this.temperature = temperature;
    }
    /**
     * @param oxygenLevel
     */
    public void setOxygenLevel(final double oxygenLevel) {
        this.oxygenLevel = oxygenLevel;
    }
    /**
     * @param airQuality
     */
    public void setAirQuality(final double airQuality) {
        this.airQuality = airQuality;
    }
}
