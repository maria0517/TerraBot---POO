package entities;

import constants.Const;

public final class Water extends Entity {
    // celelelalte de la entititate; cu super
    private String type;
    private double salinity;
    private double pH;
    private double purity;
    private double turbidity;
    private double contaminantIndex;
    private boolean isFrozen;

    public Water(final String type, final String name, final double mass, final double salinity,
           final double pH, final double purity, final double turbidity,
           final double contaminantIndex, final boolean isFrozen) {
        super(name, mass);
        this.type = type;
        this.salinity = salinity;
        this.pH = pH;
        this.purity = purity;
        this.turbidity = turbidity;
        this.contaminantIndex = contaminantIndex;
        this.isFrozen = isFrozen;
    }

    /**
     * calculare calitate apa
     */
    public double waterQualityCalc() {
        double purityScore = this.purity / Const.O_SUTA_DBL;
        double pHScore = 1 - Math.abs(pH - Const.SAPTE_PCT_CINCI) / Const.SAPTE_PCT_CINCI;
        double saliScore = 1 - (salinity / Const.TREI_SUTE_CINCI_ZECI);
        double turbidScore = 1 - (turbidity / Const.O_SUTA_DBL);
        double contaScore = 1 - (contaminantIndex / Const.O_SUTA_DBL);
        double frozenScore = 1;
        if (isFrozen) {
            frozenScore = 0;
        }
        return (Const.O_TREIME * purityScore + Const.DOUA_ZECIMI * pHScore + Const.O_ZEC_CINCI
           * saliScore + Const.O_ZECIME * turbidScore + Const.O_ZEC_CINCI * contaScore
           + Const.DOUA_ZECIMI * frozenScore) * Const.O_SUTA_DBL;
    }

    /**
     * @return string cu calitatea apei
     */
    public String waterQuality() {
        double waterQual = waterQualityCalc();
        // am calitatea apei -> acum spun in ce categorie este
        if (waterQual > Const.GOOD_QUAL) {
            return "good";
        } else if (waterQual > Const.MEDIUM_QUAL) {
            return "moderate";
        } else {
            return "poor";
        }
    }

    // getteri setteri pentru toti
    public String getType() {
        return type;
    }
    public double getTurbidity() {
        return turbidity;
    }
    public double getContaminantIndex() {
        return contaminantIndex;
    }

    /**
     * @return pH
     */
    public double getpH() {
        return pH;
    }
    public double getSalinity() {
        return salinity;
    }
    public double getPurity() {
        return purity;
    }
    public boolean isFrozen() {
        return isFrozen;
    }

    public void setType(final String type) {
        this.type = type;
    }
    public void setContaminantIndex(final double contaminantIndex) {
        this.contaminantIndex = contaminantIndex;
    }
    /**
     *
     * @param pH
     */
    public void setpH(final double pH) {
        this.pH = pH;
    }
    public void setPurity(final double purity) {
        this.purity = purity;
    }
    public void setSalinity(final double salinity) {
        this.salinity = salinity;
    }
    public void setTurbidity(final double turbidity) {
        this.turbidity = turbidity;
    }

    @Override
    public String toString() {
        return (name != null ? name : "-");
    }
}
