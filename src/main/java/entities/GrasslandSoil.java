package entities;

import com.fasterxml.jackson.databind.node.ObjectNode;
import constants.Const;

public final class GrasslandSoil extends Soil {
    // campuri specifice
    private double rootDensity;

    public GrasslandSoil(final String type, final String name, final double mass,
            final double nitrogen, final double waterRetention, final double soilpH,
            final double organicMatter, final double rootDensity) {
        super(type, name, mass, nitrogen, waterRetention, soilpH, organicMatter);
        this.rootDensity = rootDensity;
    }

    public void setRootDensity(final double rootDensity) {
        this.rootDensity = rootDensity;
    }
    public double getRootDensity() {
        return rootDensity;
    }

    @Override
    public double soilQualityCalc() {
        double soilQualBrute = (this.getNitrogen() * Const.UNU_TREI) + (this.getOrganicMatter()
                * Const.UNU_JUMATE) + (this.getRootDensity() * Const.ZERO_OPT);
        return Math.round(Math.max(0, Math.min(Const.O_SUTA_DBL, soilQualBrute))
                * Const.O_SUTA_DBL) / Const.O_SUTA_DBL;
    }

    @Override
    public void extraDisp(final ObjectNode soilInfo) {
        soilInfo.put("rootDensity", rootDensity);
    }

    @Override
    public double blockProbability() {
        return ((Const.JUM_SUTA - this.rootDensity) + this.getWaterRetention() * Const.O_JUMATE)
             / Const.SAPTE_CINCI * Const.O_SUTA_DBL;
    }

    @Override
    public String toString() {
        return (name != null ? name : "-");
    }

}
