package Entities;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class TemperateAir extends Air {
	private double pollenLevel;
	private double maxScore = 84;
    private boolean newSeasoncheck = false;
    private String season;

	public TemperateAir(String type, String name, double mass, double humidity,
					double temperature, double oxygenLevel, double pollenLevel) {
		super(type, name, mass, humidity, temperature, oxygenLevel);
		this.pollenLevel = pollenLevel;
	}

    // setez prostia
    @Override
    public void setWeather(String fenomen, double value) {
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
                case 3:
                    season = "Fall";
                    break;
                case 4:
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
		double airQualBrute = (this.getOxygenLevel() * 2) + (this.getHumidity() * 0.7) - (pollenLevel * 0.1);
        airQualBrute = Math.max(0, Math.min(100, airQualBrute));
        if (newSeasoncheck) {
            // adaug
            double seasonPenalty = season.equalsIgnoreCase("Spring") ? 15 : 0;
            airQualBrute = airQualBrute - seasonPenalty;
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
	public void setPollenLevel(double pollenLevel) { this.pollenLevel = pollenLevel; }
	public double getPollenLevel() { return pollenLevel; }

    @Override
    public void extraDisp(ObjectNode airInfo, boolean wheaterChange) {
        if (wheaterChange) {
            airInfo.put("season", "temper air la meteo don t know");
        } else {
            airInfo.put("pollenLevel", pollenLevel);
        }
    }

    @Override
    public String toString() {
        return (name != null ? name : "-");
    }


}
