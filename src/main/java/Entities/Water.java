package Entities;

public class Water extends Entity {
    // celelelalte de la entititate; cu super
    private String type;
    private double salinity;
    private double pH;
    private double purity;
    private double turbidity;
    private double contaminantIndex;
    private boolean isFrozen;

    public Water(String type, String name, double mass, double salinity, double pH, double purity,
                 double turbidity, double contaminantIndex, boolean isFrozen) {
        super(name, mass);
        this.type = type;
        this.salinity = salinity;
        this.pH = pH;
        this.purity = purity;
        this.turbidity = turbidity;
        this.contaminantIndex = contaminantIndex;
        this.isFrozen = isFrozen;
    }

    // calculare calitate apa
    public double waterQualityCalc() {
        double purity_score = this.purity / 100;
        double pH_score = 1 - Math.abs(pH - 7.5) / 7.5;
        double salinity_score = 1 - (salinity / 350);
        double turbidity_score = 1 - (turbidity / 100);
        double contaminant_score = 1 - (contaminantIndex / 100);
        double frozen_score = 1;
        if (isFrozen)
            frozen_score = 0;
        return (0.3 * purity_score + 0.2 * pH_score + 0.15 * salinity_score
                + 0.1 * turbidity_score + 0.15 * contaminant_score + 0.2 * frozen_score) * 100;
    }

    public String waterQuality() {
        double water_quality = waterQualityCalc();
        // am calitatea apei -> acum spun in ce categorie este
        if (water_quality > 70)
            return "good";
        else if (water_quality > 40)
            return "moderate";
        else
            return "poor";
    }

    // intrebare pentru frozen trebuie oare setter + getter
    public String getType() { return type; }
    public double getTurbidity() { return turbidity; }
    public double getContaminantIndex() { return contaminantIndex; }
    public double getpH() { return pH; }
    public double getSalinity() { return salinity; }
    public double getPurity() { return purity; }
    public boolean isFrozen() { return isFrozen; }

    public void setType(String type) { this.type = type; }
    public void setContaminantIndex(double contaminantIndex) { this.contaminantIndex = contaminantIndex; }
    public void setpH(double pH) { this.pH = pH; }
    public void setPurity(double purity) { this.purity = purity; }
    public void setSalinity(double salinity) { this.salinity = salinity; }
    public void setTurbidity(double turbidity) { this.turbidity = turbidity; }
    public void setFrozen(boolean frozen) { this.isFrozen = frozen; }
}
