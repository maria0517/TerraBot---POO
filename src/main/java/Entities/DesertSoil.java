package Entities;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class DesertSoil extends Soil {
    private double salinity;

    public DesertSoil(String type, String name, double mass, double nitrogen, double waterRetention,
                      double soilpH, double organicMatter, double salinity) {
        super(type, name, mass, nitrogen, waterRetention, soilpH, organicMatter);
        this.salinity = salinity;
    }

    public void setSalinity(double salinity) { this.salinity = salinity; }
    public double getSalinity() { return salinity; }

    @Override
    public double soilQualityCalc() {
        double soilQualBrute =  (this.getNitrogen() * 0.5) + (this.getWaterRetention() * 0.3) - (this.getSalinity() * 2);
        return Math.round(Math.max(0, Math.min(100, soilQualBrute)) * 100.0) / 100.0;
    }

    @Override
    public double blockProbability() {
        return (100 - this.getWaterRetention() + this.salinity) / 100 * 100;
    }

    @Override
    public void extraDisp(ObjectNode soilInfo) {
        soilInfo.put("salinity", salinity);
    }

    @Override
    public String toString() {
        return (name != null ? name : "-");
    }

}