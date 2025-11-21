package Entities;

import com.fasterxml.jackson.databind.node.ObjectNode;

public abstract class Air extends Entity {
    private String type;
    private double humidity;
    private double temperature;
    private double oxygenLevel;
    private double airQuality;

    public Air(String type, String name, double mass, double humidity,
               double temperature, double oxygenLevel) {
        super(name, mass);
        this.type = type;
        this.humidity = humidity;
        this.temperature = temperature;
        this.oxygenLevel = oxygenLevel;

        // cum fac la alea specifice??? -> abstract maria si cu alte clase
    }

    // calc scor calitate aer
    public abstract double airQualityCalc();
    // toxicitate
    public abstract double calculateToxicity();
    // daca aerul e toxic pentru robotel
    public abstract boolean isToxic();

    public abstract void setWeather(String fenomen, double value);

    public abstract void extraDisp(ObjectNode airInfo, boolean wheaterChange);

    // asta e la toti
    public String getAirQualityString() {
        double airQualDone = airQualityCalc();
        // norm + rotunjire
        if (airQualDone >= 70)
            return "good";
        if (airQualDone >= 40)
            return "moderate";

        return "poor";
    }


    public String getType() { return type; }
    public double getHumidity() { return humidity; }
    public double getTemperature() { return temperature; }
    public double getOxygenLevel() { return oxygenLevel; }
    public double getAirQuality() { return airQualityCalc(); }

    public void setType(String type) { this.type = type; }
    public void setHumidity(double humidity) { this.humidity = humidity; }
    public void setTemperature(double temperature) { this.temperature = temperature; }
    public void setOxygenLevel(double oxygenLevel) { this.oxygenLevel = oxygenLevel; }
    public void setAirQuality(double airQuality) { this.airQuality = airQuality; }
}
