package entities;

import com.fasterxml.jackson.databind.node.ObjectNode;
import constants.Const;

public final class DesertSoil extends Soil {
    private double salinity;

    public DesertSoil(final String type, final String name, final double mass,
               final double nitrogen, final double waterRetention, final double soilpH,
               final double organicMatter, final double salinity) {
        super(type, name, mass, nitrogen, waterRetention, soilpH, organicMatter);
        this.salinity = salinity;
    }

    public void setSalinity(final double salinity) {
        this.salinity = salinity;
    }
    public double getSalinity() {
        return salinity;
    }

    @Override
    public double soilQualityCalc() {
        double soilQualBrute =  (this.getNitrogen() * Const.O_JUMATE) + (this.getWaterRetention()
              * Const.O_TREIME) - (this.getSalinity() * Const.UN_DOI);
        return Math.round(Math.max(0, Math.min(Const.O_SUTA_DBL, soilQualBrute))
              * Const.O_SUTA_DBL) / Const.O_SUTA_DBL;
    }

    @Override
    public double blockProbability() {
        return (Const.O_SUTA_DBL - this.getWaterRetention()
                + this.salinity) / Const.O_SUTA_DBL * Const.O_SUTA_DBL;
    }

    @Override
    public void extraDisp(final ObjectNode soilInfo) {
        soilInfo.put("salinity", salinity);
    }

    @Override
    public String toString() {
        return (name != null ? name : "-");
    }

}
