package Entities;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class ForestSoil extends Soil {
    // toate de la soil si ce mai e acolo
    private double leafLitter;

    public ForestSoil(String type, String name, double mass, double nitrogen, double waterRetention,
                      double soilpH, double organicMatter, double leafLitter) {
        super(type, name, mass, nitrogen, waterRetention, soilpH, organicMatter);
        this.leafLitter = leafLitter;
    }

    // set + get pentru LeafLitter
    public void setLeafLitter(double leafLitter) { this.leafLitter = leafLitter; }
    public double getLeafLitter() { return leafLitter; }

        // calc calit sol
    @Override
    public double soilQualityCalc() {
        double soilQualBrute = (this.getNitrogen() * 1.2) + (this.getOrganicMatter() * 2.0) + (this.getWaterRetention() * 1.5) + (this.getLeafLitter() * 0.3);
        return Math.round(Math.max(0, Math.min(100, soilQualBrute)) * 100.0) / 100.0;
    }

    // Probabilitatea de a bloca TerraBot
    @Override
    public double blockProbability() {
        return (this.getWaterRetention() * 0.6 + this.leafLitter * 0.4) / 80.0 * 100.0;
    }

    @Override
    public void extraDisp(ObjectNode soilInfo) {
        soilInfo.put("leafLitter", getLeafLitter());
    }

    @Override
    public String toString() {
        return (name != null ? name : "-");
    }

}
