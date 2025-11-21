package Entities;

import com.fasterxml.jackson.databind.node.ObjectNode;

public abstract class Soil extends Entity {
    // campurile de la Entity + restul
    private String type;
    private double nitrogen;
    private double waterRetention;
    private double soilpH;
    private double organicMatter;
    private double soilQuality;

    // constructorul de baza ?? se mai pune
    public Soil(String type, String name, double mass, double nitrogen, double waterRetention,
                double soilpH, double organicMatter) {
        super(name, mass);
        this.type = type;
        this.nitrogen = nitrogen;
        this.waterRetention = waterRetention;
        this.soilpH = soilpH;
        this.organicMatter = organicMatter;
    }

    // met abstract, urmeaza sa le completez;  fiecare tip de sol are implem lui
    public abstract double soilQualityCalc();
    public abstract double blockProbability();
    public abstract void extraDisp(ObjectNode soilInfo);

    // asta ramane comuna tuturor
    public String soilQualityString() {
        double soilQualDone = soilQualityCalc();
        // acum doar scriem ce e bun si ce nu e
        if (soilQualDone >= 70)
            return "good";
        if (soilQualDone >= 40)
            return "moderate";

        return "poor";
    }


    // getteri si setteri comuni pentru toate solurile
    public double getNitrogen() { return nitrogen; }
    public double getWaterRetention() { return waterRetention; }
    public double getSoilpH() { return soilpH; }
    public double getOrganicMatter() { return organicMatter; }
    public String getType() { return type; }
    public double getSoilQuality() { return this.soilQualityCalc(); }

    public void setNitrogen(double nitrogen) { this.nitrogen = nitrogen; }
    public void setWaterRetention(double waterRetention) { this.waterRetention = waterRetention; }
    public void setSoilpH(double soilpH) { this.soilpH = soilpH; }
    public void setOrganicMatter(double organicMatter) { this.organicMatter = organicMatter; }
    public void setType(String type) { this.type = type; }
    public void setSoilQuality(double soilQuality) { this.soilQuality = soilQuality; }
}
