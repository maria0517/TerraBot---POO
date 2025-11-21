package Entities;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class TropicalAir extends Air {
	private double co2Level;
	private double maxScore = 82;
    private double rainfall;
    private boolean rainfallCheck = false;

	public TropicalAir(String type, String name, double mass, double humidity,
					   double temperature, double oxygenLevel, double co2Level) {
		super(type, name, mass, humidity, temperature, oxygenLevel);
		this.co2Level = co2Level;
	}


    @Override
    public void setWeather(String fenomen, double value) {
        if (fenomen.equals("rainfall")) {
            rainfallCheck = true;
            rainfall = value;
        }
    }

    @Override
	public double airQualityCalc() {
		double airQualBrute = (this.getOxygenLevel() * 2) + (this.getHumidity() * 0.5) - (this.co2Level * 0.01);
        airQualBrute = Math.max(0, Math.min(100, airQualBrute));
        if (rainfallCheck) {
            // mai adaug ceva
            airQualBrute += rainfall * 0.3;
        }
		return Math.round(airQualBrute * 100.0) / 100.0;
	}

	@Override
	public double calculateToxicity() {
		double toxicityAQ = 100 * (1 - airQualityCalc() / maxScore);
        // trebuie si aici normalizat Doamne fereste
        toxicityAQ = Math.max(0, Math.min(100, toxicityAQ));
		return Math.round(toxicityAQ * 100.0) / 100.0;
	}

	@Override
	public boolean isToxic() {
		return calculateToxicity() > (0.8 * maxScore);
	}

	// getter + setter
	public void setCo2Level(double co2Level) { this.co2Level = co2Level; }
	public double getCo2Level() { return co2Level; }

    @Override
    public void extraDisp(ObjectNode airInfo, boolean wheaterChange) {
        if (wheaterChange) {
            airInfo.put("co2Level", "tropic air la meteo inca nu stiu ce pun aici");
        } else {
            airInfo.put("co2Level", co2Level);
        }
    }

    @Override
    public String toString() {
        return (name != null ? name : "-");
    }

}
