package entities;

import com.fasterxml.jackson.databind.node.ObjectNode;
import constants.Const;

public final class SwampSoil extends Soil {
    // specifica
    private double waterLogging;

    public SwampSoil(final String type, final String name, final double mass,
           final double nitrogen, final double waterRetention, final double soilpH,
           final double organicMatter, final double waterLogging) {
        super(type, name, mass, nitrogen, waterRetention, soilpH, organicMatter);
        this.waterLogging = waterLogging;
    }

    public void setWaterLogging(final double waterLogging) {
        this.waterLogging = waterLogging;
    }
    public double getWaterLogging() {
        return waterLogging;
    }

    @Override
    public double soilQualityCalc() {
        double soilQualBrute = (this.getNitrogen() * Const.UNU_UNU) + (this.getOrganicMatter()
                * Const.DOI_DOI) - (this.getWaterLogging() * Const.UN_CINCI);
        // normalizare + rotunj
        return Math.round(Math.max(0, Math.min(Const.O_SUTA_DBL, soilQualBrute))
                * Const.O_SUTA_DBL) / Const.O_SUTA_DBL;
    }

    @Override
    public double blockProbability() {
        return this.waterLogging * Const.UN_ZECE;
    }

    @Override
    public void extraDisp(final ObjectNode soilInfo) {
        soilInfo.put("waterLogging", getWaterLogging());
    }

    @Override
    public String toString() {
        return (name != null ? name : "-");
    }

}
