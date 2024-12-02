package Pret;

public class Pret {
    private double fraisDossierBancaire;
    private double fraisNotaireCredit;
    private int nombreAnnees;
    private double tauxAnnuel;

    /**
     * Calcule le restant dû, non couvert par les mensualités.
     * @param montantEmprunt
     * @return restantDu
     */
    public double calculRestantDu(double montantEmprunt){
        double restantDu = this.calculTotalProjet(montantEmprunt) - 
                           this.calculMensualites(montantEmprunt) * 
                           this.getMensualites();
        return Math.round(restantDu * 100.0) / 100.0;
    }

    /**
     * Calcule le montant total du projet.
     * @param montantEmprunt
     * @return montantTotal
     */
    public double calculTotalProjet(double montantEmprunt){
        double montantTotal = montantEmprunt + 
                              this.fraisDossierBancaire + 
                              this.fraisNotaireCredit + 
                              this.calculTotalInterets(montantEmprunt);
        return Math.round(montantTotal * 100.0) / 100.0;
    }

    /**
     * Calcule le montant total des intérêts payés sur l'emprunt.
     * @return montantInterets
     */
    public double calculTotalInterets(double montantEmprunt){
        double montantInterets = this.calculMensualites(montantEmprunt) * 
                                 this.getMensualites() - 
                                 montantEmprunt;
        return Math.round(montantInterets * 100.0) / 100.0;
    }

    /**
     * Calcule le montant par mensualité suivant le montant emprunté,
     * le nombre de mensualités et le taux annuel.
     * @param montantEmprunt Montant de l'emprunt.
     * @return double montantMensualite
     */
    public double calculMensualites(double montantEmprunt){
        double tauxPeriodique = this.calculTauxPeriodique();
        int mensualites = this.getMensualites();
        double mensualite = (montantEmprunt * 
                             tauxPeriodique * 
                             Math.pow(1 + tauxPeriodique , mensualites)) / 
                            (Math.pow(1 + tauxPeriodique, mensualites) - 1);
        return Math.round(mensualite * 100.0) / 100.0;
    }

    /**
     * Calcule le taux périodique (par mois).
     * @return double tauxPeriodique
     */
    private double calculTauxPeriodique(){
        double tauxPeriodique = Math.pow((1 + this.tauxAnnuel), (1.0f / 12.0f)) - 1;
        return tauxPeriodique;
    }

    /**
     * Renvoie le nombre de mensualités.
     * @return int mensualite
     */
    private int getMensualites(){
        return nombreAnnees * 12;
    }

    public double getFraisDossierBancaire() {
        return fraisDossierBancaire;
    }
    public void setFraisDossierBancaire(double fraisDossierBancaire) {
        this.fraisDossierBancaire = fraisDossierBancaire;
    }
    public double getFraisNotaireCredit() {
        return fraisNotaireCredit;
    }
    public void setFraisNotaireCredit(double fraisNotaireCredit) {
        this.fraisNotaireCredit = fraisNotaireCredit;
    }
    public int getNombreAnnees() {
        return nombreAnnees;
    }
    public void setNombreAnnees(int nombreAnnees) {
        this.nombreAnnees = nombreAnnees;
    }
    public double getTauxAnnuel() {
        return tauxAnnuel;
    }

    public void setTauxAnnuel(double tauxAnnuel) {
        this.tauxAnnuel = tauxAnnuel;
    }
}
