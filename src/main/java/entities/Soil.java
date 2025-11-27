package entities;

import com.fasterxml.jackson.databind.node.ObjectNode;
import constants.Const;

/**
 * entitatea sol de tip abstract din care derivez subtipuri
 */
public abstract class Soil extends Entity {
    // campurile de la Entity + restul
    private String type;
    private double nitrogen;
    private double waterRetention;
    private double soilpH;
    private double organicMatter;
    private double soilQuality;

    // constructorul de baza ?? se mai pune
    public Soil(final String type, final String name, final double mass, final double nitrogen,
           final double waterRetention, final double soilpH, final double organicMatter) {
        super(name, mass);
        this.type = type;
        this.nitrogen = nitrogen;
        this.waterRetention = waterRetention;
        this.soilpH = soilpH;
        this.organicMatter = organicMatter;
    }

    // met abstract, urmeaza sa le completez;  fiecare tip de sol are implem lui
    /**
     aici am metoda de calc calitate sol
     */
    public abstract double soilQualityCalc();
    /**
     robot attack
     */
    public abstract double blockProbability();
    /**
      functie pentru afisare
     */
    public abstract void extraDisp(ObjectNode soilInfo);

    // asta ramane comuna tuturor

    /**
     * afisare calitate sol
     */
    public String soilQualityString() {
        double soilQualDone = soilQualityCalc();
        // acum doar scriem ce e bun si ce nu e
        if (soilQualDone >= Const.GOOD_QUAL) {
            return "good";
        }
        if (soilQualDone >= Const.MEDIUM_QUAL) {
            return "moderate";
        }
        return "poor";
    }

    /**
     *  getter Nitrogen
     */
    public double getNitrogen() {
        return nitrogen;
    }

    /**
     * getter waterRet
     */
    public double getWaterRetention() {
        return waterRetention;
    }
    /**
     * getter pH
     */
    public double getSoilpH() {
        return soilpH;
    }
    /**
     * getter materie organica
     */
    public double getOrganicMatter() {
        return organicMatter;
    }
    /**
     * getter soilType
     */
    public String getType() {
        return type;
    }
    /**
     *
     * getter soilQual
     */
    public double getSoilQuality() {
        return this.soilQualityCalc();
    }

    /**
     * @param nitrogen
     */
    public void setNitrogen(final double nitrogen) {
        this.nitrogen = nitrogen;
    }
    /**
     * @param waterRetention
     */
    public void setWaterRetention(final double waterRetention) {
        this.waterRetention = waterRetention;
    }
    /**
     *
     * @param soilpH
     */
    public void setSoilpH(final double soilpH) {
        this.soilpH = soilpH;
    }
    /**
     *
     * @param organicMatter
     */
    public void setOrganicMatter(final double organicMatter) {
        this.organicMatter = organicMatter;
    }
    /**
     *
     * @param type
     */
    public void setType(final String type) {
        this.type = type;
    }
    /**
     * @param soilQuality
     */
    public void setSoilQuality(final double soilQuality) {
        this.soilQuality = soilQuality;
    }
}
