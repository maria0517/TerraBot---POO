package Entities;

public abstract class Plant extends Entity {
    // cele mostenite le ia automat
    // acum ce mai are ea
    private String type;
    // old, mature, young
    private String status;
    private double maturity_oxigen_rate = 0.2;
    // creste +0.2 pe la fiecare iter; toate pleaca de mici
    private double growthLevel = 0;
    // private double scanned;

    // constructorul competent
    public Plant(String type, String name, double mass, String status) {
        // acum setez tot si incep sa calculez ce a ramas
        // this.name = name; nu merge asa -> trebuie cu super
        // this.mass = mass;
        super(name, mass);
        this.type = type;
        this.status = status;
    }

    // getteri si setteri pentru nume si masa sunt
    // deja in entities, nu ne mai trebuie

    public String getType() { return type; }
    public String getStatus() { return status; }

    public void setType(String type) { this.type = type; }
    public void setStatus(String status) { this.status = status; }

    // metoda de calculare varsta plantuta
    public void grow() {
        growthLevel += 0.2;
        if (growthLevel >= 1 && status.equals("young")) {
            // o fac mai mare
            status = "mature";
            maturity_oxigen_rate = 0.7;
        } else if (growthLevel >= 2 && status.equals("mature")) {
            // si mai mare
            status = "old";
            maturity_oxigen_rate = 0.4;
        } else if (growthLevel >= 3 && status.equals("old")) {
            // a murit amica
            status = "dead";
            maturity_oxigen_rate = 0.0;
        }
        growthLevel = Math.round(growthLevel * 100.0) / 100.0;
    }

    public double getGrowthLevel() {
        return growthLevel;
    }

    public double getTotalOxygen() {
        return getOxygenFromPlant() + this.maturity_oxigen_rate;
    }

    public abstract double getOxygenFromPlant();
    public abstract double getAttackProb();
}
