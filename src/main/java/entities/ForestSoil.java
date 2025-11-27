package entities;

import com.fasterxml.jackson.databind.node.ObjectNode;
import constants.Const;

public final class ForestSoil extends Soil {
    // toate de la soil si ce mai e acolo
    private double leafLitter;

    public ForestSoil(final String type, final String name, final double mass,
           final double nitrogen, final double waterRetention, final double soilpH,
           final double organicMatter, final double leafLitter) {
        super(type, name, mass, nitrogen, waterRetention, soilpH, organicMatter);
        this.leafLitter = leafLitter;
    }

    // set + get pentru LeafLitter
    public void setLeafLitter(final double leafLitter) {
        this.leafLitter = leafLitter;
    }
    public double getLeafLitter() {
        return leafLitter;
    }

    // calc calit sol
    @Override
    public double soilQualityCalc() {
        double soilQualBrute = (this.getNitrogen() * Const.UNU_DOI)
           + (this.getOrganicMatter() * Const.UN_DOI) + (this.getWaterRetention()
           * Const.UNU_JUMATE) + (this.getLeafLitter() * Const.O_TREIME);
        return Math.round(Math.max(0, Math.min(Const.O_SUTA_DBL, soilQualBrute))
                * Const.O_SUTA_DBL) / Const.O_SUTA_DBL;
    }

    // probabilitatea de a bloca robotu
    @Override
    public double blockProbability() {
        return (this.getWaterRetention() * Const.ZERO_SASE + this.leafLitter * Const.ZERO_PATRU)
                / Const.OPT_ZECI * Const.O_SUTA_DBL;
    }

    @Override
    public void extraDisp(final ObjectNode soilInfo) {
        soilInfo.put("leafLitter", getLeafLitter());
    }

    @Override
    public String toString() {
        return (name != null ? name : "-");
    }

}
