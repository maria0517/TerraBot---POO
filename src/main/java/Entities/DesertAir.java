package Entities;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class DesertAir extends Air{
	private double dustParticles;
	private double maxScore = 65;
    private boolean desertStormCheck = false;

	public DesertAir(String type, String name, double mass, double humidity,
						double temperature, double oxygenLevel, double dustParticles) {
		super(type, name, mass, humidity, temperature, oxygenLevel);
		this.dustParticles = dustParticles;
	}

    @Override
    public void setWeather(String fenomen, double value) {
        // aici imi trebuie doar fenomenul
        if (fenomen.equals("desertStorm")) {
            desertStormCheck = !desertStormCheck;
        }
    }

    public boolean getDesertStormCheck() {
        return desertStormCheck;
    }

    @Override
	public double airQualityCalc() {
		double airQualBrute = (this.getOxygenLevel() * 2) - (dustParticles * 0.2) - (this.getTemperature() * 0.3);
        airQualBrute = Math.max(0, Math.min(100, airQualBrute));
        if (desertStormCheck) {
            airQualBrute -= (desertStormCheck ? 30 : 0);
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
	public void setDustParticles(double dustParticles) { this.dustParticles = dustParticles; }
	public double getDustParticles() { return dustParticles; }

    @Override
    public void extraDisp(ObjectNode airInfo, boolean wheaterChange) {
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
