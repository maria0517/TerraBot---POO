package Entities;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class PolarAir extends Air {
	private double iceCrystalConcentration;
	private double maxScore = 142;
    private double windSpeed;
    private boolean polarStormCheck = false;

	public PolarAir(String type, String name, double mass,  double humidity,
					   double temperature, double oxygenLevel, double iceCrystalConcentration) {
		super(type, name, mass, humidity, temperature, oxygenLevel);
		this.iceCrystalConcentration = iceCrystalConcentration;
	}

    @Override
    public void setWeather(String fenomen, double value) {
        if (fenomen.equals("polarStorm")) {
            polarStormCheck = !polarStormCheck;
            this.windSpeed = value;
        }
    }

    @Override
	public double airQualityCalc() {
		double airQualBrute = (this.getOxygenLevel() * 2) + (100 - Math.abs(this.getTemperature()))
				- (iceCrystalConcentration * 0.05);
        airQualBrute = Math.max(0, Math.min(100, airQualBrute));
        if (polarStormCheck) {
            airQualBrute -= windSpeed * 0.2;
        }
		return Math.round(airQualBrute * 100.0) / 100.0;
	}

	@Override
	public double calculateToxicity() {
		double toxicityAQ = 100 * (1 - airQualityCalc() / maxScore);
        toxicityAQ = Math.max(0, Math.min(100, toxicityAQ));
		return Math.round(toxicityAQ * 100.0) / 100.0;
	}

	@Override
	public boolean isToxic() {
		return calculateToxicity() > (0.8 * maxScore);
	}

	// getter + setter
	public void setIceCrystalConcentration(double co2Level) { this.iceCrystalConcentration = iceCrystalConcentration; }
	public double getIceCrystalConcentration() { return iceCrystalConcentration; }

    @Override
    public void extraDisp(ObjectNode airInfo, boolean wheaterChange) {
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
