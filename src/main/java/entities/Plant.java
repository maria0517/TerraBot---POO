package entities;

import constants.Const;

public abstract class Plant extends Entity {
    // cele mostenite le ia automat
    // acum ce mai are ea
    private String type;
    // old, mature, young
    private String status;
    private double maturityOxigenRate = Const.DOUA_ZECIMI;
    // creste +0.2 pe la fiecare iter; toate pleaca de mici
    private double growthLevel = 0;

    // constructorul competent
    public Plant(final String type, final String name, final double mass, final String status) {
        // acum setez tot si incep sa calculez ce a ramas
        // this.name = name; nu merge asa -> trebuie cu super
        // this.mass = mass;
        super(name, mass);
        this.type = type;
        this.status = status;
    }

    // getteri si setteri pentru nume si masa sunt
    // deja in entities, nu ne mai trebuie
    /**
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param type
     */
    public void setType(final String type) {
        this.type = type;
    }

    /**
     * @param status
     */
    public void setStatus(final String status) {
        this.status = status;
    }

    // metoda de calculare varsta plantuta

    /**
     * @return nimic ca face in interior))
     */
    public void grow() {
        growthLevel += Const.DOUA_ZECIMI;
        if (growthLevel >= 1 && status.equals("young")) {
            // o fac mai mare
            status = "mature";
            maturityOxigenRate = Const.ZERO_SAPTE;
        } else if (growthLevel >= 2 && status.equals("mature")) {
            // si mai mare
            status = "old";
            maturityOxigenRate = Const.ZERO_PATRU;
        } else if (growthLevel >= Const.UN_TREI && status.equals("old")) {
            // a murit amica
            status = "dead";
            maturityOxigenRate = 0.0;
        }
        growthLevel = Math.round(growthLevel * Const.O_SUTA_DBL) / Const.O_SUTA_DBL;
    }

    /**
     * @return O2
     */
    public double getTotalOxygen() {
        return getOxygenFromPlant() + this.maturityOxigenRate;
    }

    /**
     * @return O2 de la planta
     */
    public abstract double getOxygenFromPlant();
    /**
     * @return probAttack
     */
    public abstract double getAttackProb();
}
