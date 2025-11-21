package Entities;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class SwampSoil extends Soil {
    // specifica
    private double waterLogging;

    public SwampSoil(String type, String name, double mass, double nitrogen, double waterRetention,
                     double soilpH, double organicMatter, double waterLogging) {
        super(type, name, mass, nitrogen, waterRetention, soilpH, organicMatter);
        this.waterLogging = waterLogging;
    }

    public void setWaterLogging(double waterLogging) { this.waterLogging = waterLogging; }
    public double getWaterLogging() { return waterLogging; }

    @Override
    public double soilQualityCalc() {
        double soilQualBrute = (this.getNitrogen() * 1.1) + (this.getOrganicMatter() * 2.2) - (this.getWaterLogging() * 5);
        // normalizare + rotunj
        return Math.round(Math.max(0, Math.min(100, soilQualBrute)) * 100.0) / 100.0;
    }

    @Override
    public double blockProbability() {
        return this.waterLogging * 10;
    }

    @Override
    public void extraDisp(ObjectNode soilInfo) {
        soilInfo.put("waterLogging", getWaterLogging());
    }

    @Override
    public String toString() {
        return (name != null ? name : "-");
    }

}