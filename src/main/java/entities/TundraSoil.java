package entities;

import com.fasterxml.jackson.databind.node.ObjectNode;
import constants.Const;

public final class TundraSoil extends Soil {
    private double permafrostDepth;

    public TundraSoil(final String type, final String name, final double mass,
           final double nitrogen, final double waterRetention, final double soilpH,
           final double organicMatter, final double permafrostDepth) {
        super(type, name, mass, nitrogen, waterRetention, soilpH, organicMatter);
        this.permafrostDepth = permafrostDepth;
    }

    public void setPermafrostDepth(final double permafrostDepth) {
        this.permafrostDepth = permafrostDepth;
    }
    public double getPermafrostDepth() {
        return permafrostDepth;
    }

    @Override
    public double soilQualityCalc() {
        double soilQualBrute = (this.getNitrogen() * Const.ZERO_SAPTE) + (this.getOrganicMatter()
              * Const.O_JUMATE) - (this.getPermafrostDepth() * Const.UNU_JUMATE);
        // normalizare + rotunj
        return Math.round(Math.max(0, Math.min(Const.O_SUTA_DBL, soilQualBrute)) * Const.O_SUTA_DBL)
                / Const.O_SUTA_DBL;
    }

    @Override
    public double blockProbability() {
        return (Const.JUM_SUTA - this.permafrostDepth) / Const.JUM_SUTA * Const.O_SUTA_DBL;
    }

    @Override
    public void extraDisp(final ObjectNode soilInfo) {
        soilInfo.put("permafrostDepth", permafrostDepth);
    }

    @Override
    public String toString() {
        return (name != null ? name : "-");
    }
}
