package Entities;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class MountainAir extends Air {
	private double altitude;
	private double maxScore = 78;
    private int numberOfHikers;
    private boolean peopleHiking = false;

	public MountainAir(String type, String name, double mass, double humidity,
					   double temperature, double oxygenLevel, double altitude) {
		super(type, name, mass, humidity, temperature, oxygenLevel);
		this.altitude = altitude;
	}

    public int getNumberOfHikers() {
        return numberOfHikers;
    }

    @Override
    public void setWeather(String fenomen, double value) {
        if (fenomen.equals("PeopleHiking")) {
            peopleHiking = true;
            numberOfHikers = (int) value;
        }
    }

    @Override
	public double airQualityCalc() {
		double oxygenFactor = this.getOxygenLevel() - (this.altitude / 1000.0 * 0.5);
		double airQualBrute = (oxygenFactor * 2.0) + (this.getHumidity() * 0.6);
        airQualBrute = Math.max(0, Math.min(100, airQualBrute));
        if (peopleHiking) {
            airQualBrute -= numberOfHikers * 0.1;
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

	// Getter și setter corectat
	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}

	public double getAltitude() {
		return altitude;
	}

    @Override
    public void extraDisp(ObjectNode airInfo, boolean wheaterChange) {
        if (wheaterChange) {
            airInfo.put("peopleHiking", "inca nu stiu ce pun aici la mountAIR cand am meteo");
        } else {
            airInfo.put("altitude", altitude);
        }
    }

    @Override
    public String toString() {
        return (name != null ? name : "-");
    }

}