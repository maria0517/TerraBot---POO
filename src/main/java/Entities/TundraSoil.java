package Entities;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class TundraSoil extends Soil {
    private double permafrostDepth;

    public TundraSoil(String type, String name, double mass, double nitrogen, double waterRetention,
                    double soilpH, double organicMatter, double permafrostDepth) {
        super(type, name, mass, nitrogen, waterRetention, soilpH, organicMatter);
        this.permafrostDepth = permafrostDepth;
    }

    public void setPermafrostDepth(double permafrostDepth) { this.permafrostDepth = permafrostDepth; }
    public double getPermafrostDepth() { return permafrostDepth; }

    @Override
    public double soilQualityCalc() {
        double soilQualBrute = (this.getNitrogen() * 0.7) + (this.getOrganicMatter() * 0.5) - (this.getPermafrostDepth() * 1.5);
        // normalizare + rotunj
        return Math.round(Math.max(0, Math.min(100, soilQualBrute)) * 100.0) / 100.0;
    }

    @Override
    public double blockProbability() {
        return (50 - this.permafrostDepth) / 50 * 100;
    }

    @Override
    public void extraDisp(ObjectNode soilInfo) {
        soilInfo.put("permafrostDepth", permafrostDepth);
    }

    @Override
    public String toString() {
        return (name != null ? name : "-");
    }

}