package Entities;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class GrasslandSoil extends Soil {
	// campuri specifice
	private double rootDensity;

	public GrasslandSoil(String type, String name, double mass, double nitrogen, double waterRetention,
						 double soilpH, double organicMatter, double rootDensity) {
        super(type, name, mass, nitrogen, waterRetention, soilpH, organicMatter);
		this.rootDensity = rootDensity;
	}

	public void setRootDensity(double rootDensity) { this.rootDensity = rootDensity; }
    public double getRootDensity() { return rootDensity; }

	@Override
	public double soilQualityCalc() {
		double soilQualBrute = (this.getNitrogen() * 1.3) + (this.getOrganicMatter() * 1.5) + (this.getRootDensity() * 0.8);
		return Math.round(Math.max(0, Math.min(100, soilQualBrute)) * 100.0) / 100.0;
	}

    @Override
    public void extraDisp(ObjectNode soilInfo) {
        soilInfo.put("rootDensity", rootDensity);
    }

	@Override
	public double blockProbability() {
		return ((50 - this.rootDensity) + this.getWaterRetention() * 0.5) / 75 * 100;
	}

    @Override
    public String toString() {
        return (name != null ? name : "-");
    }

}