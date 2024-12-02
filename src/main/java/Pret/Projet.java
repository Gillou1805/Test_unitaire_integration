package Pret;

public class Projet {
    /*
     * Cette classe couvre l'ensemble des m�thodes n�cessaires au calcul d'un pret hypothecaire.
     */
    private int revenuCadastral;
    private double prixHabitation;
    private double fraisNotaireAchat;
    private double fraisTransformation; // Aka travaux de r�novation   
   


    /**
     * Calcule le total � emprunter � la banque pour le projet.
     * @return double emprunt
     */
    public double calculResteAEmprunter(){
        return this.calculTotalProjetAchat() -
            this.calculApportMinimal();
    }

    /**
     * Calcule l'apport minimal n�cessaire pour le projet d'achat.
     * @return doube apport minimal
     */
    public double calculApportMinimal() {
        // Vérification des entrées pour éviter les valeurs négatives ou nulles
        if (this.prixHabitation <= 0) {
            throw new IllegalArgumentException("Le prix de l'habitation doit être strictement positif.");
        }
        if (this.fraisTransformation < 0) {
            throw new IllegalArgumentException("Les frais de transformation ne peuvent pas être négatifs.");
        }
        if (this.fraisNotaireAchat < 0) {
            throw new IllegalArgumentException("Les frais de notaire ne peuvent pas être négatifs.");
        }

        // Calcul de l'apport minimal
        double tvaFraisTransformation = this.calculTVAFraisTransformation();
        double droitEnregistrement = this.calculDroitEnregistrement();

        // Apport minimal : 0.1 * (prixHabitation + fraisTransformation + TVA) + droitEnregistrement + fraisNotaireAchat
        double apportMinimal = 0.1 * (this.prixHabitation + this.fraisTransformation + tvaFraisTransformation)
                + droitEnregistrement
                + this.fraisNotaireAchat;

        // Arrondir à 2 décimales
        return Math.round(apportMinimal * 100.0) / 100.0;
    }



    /**
     * Calcule le co�t total du projet d'Achat
     * @return double total
     */
    public double calculTotalProjetAchat() {
        // Vérifier que les valeurs de base sont valides
        if (this.prixHabitation < 0 || this.fraisNotaireAchat < 0 || this.fraisTransformation < 0) {
            throw new IllegalArgumentException("Le coût total doit être strictement positif");
        }

        // Calcul du coût total
        double total = this.prixHabitation +
            this.fraisNotaireAchat +
            this.calculDroitEnregistrement() +
            this.fraisTransformation +
            this.calculTVAFraisTransformation();

        // Vérifier si le total est strictement positif
        if (total <= 0) {
            throw new IllegalArgumentException("Le coût total doit être strictement positif");
       }

        return total;
    }



    /**
     * Calcule le montant du droit d'enregistrement.
     * @return double droit d'enregistrement
     */
    public double calculDroitEnregistrement() {
        // Vérification des entrées
        if (this.revenuCadastral <= 0) {
            throw new IllegalArgumentException("Le revenu cadastral doit être strictement positif et non nul.");
        }
        if (this.prixHabitation <= 0) {
            throw new IllegalArgumentException("Le prix de l'habitation doit être strictement positif et non nul.");
        }

        // Définition du taux
        float taux = 0.125f;
        if (this.revenuCadastral <= 745) {
            taux = 0.06f;
        }

        // Calcul du droit d'enregistrement
        double droit = taux * (this.prixHabitation - this.calculAbattement());

        // Retour avec arrondi à 2 décimales
        return Math.round(droit * 100.0) / 100.0;
    }



    /**
     * Calcule l'abattement fiscal possible. 
     * @return double abattement
     */
    public double calculAbattement() {
        double abattement;
        if (this.prixHabitation == 0) return 0.00; // Nouveau cas ajouté
        if (this.prixHabitation < 350_000) {
            abattement = 40_000;
        } else if (this.prixHabitation >= 500_000) {
            abattement = 20_000;
        } else {
            abattement = 40_000 - (20_000 * (this.prixHabitation - 350_000) / (500_000 - 350_000));
        }
        // Arrondir à 2 décimales
        return Math.round(abattement * 100.0) / 100.0;
    }


    /**
     * Calcule la tva sur les frais de transformation
     * @return doube TVA
     */
    public double calculTVAFraisTransformation() {
        // Vérification de la validité de la valeur
        if (this.fraisTransformation < 0) {
            throw new IllegalArgumentException("Les frais de transformation ne peuvent pas être négatifs.");
        }

        // Calcul de la TVA
        double tva = 0.06 * this.fraisTransformation;

        // Arrondi au centième
        return Math.round(tva * 100.0) / 100.0;
    }



    public int getRevenuCadastral() {
        return revenuCadastral;
    }

    public void setRevenuCadastral(int revenuCadastral) {
        this.revenuCadastral = revenuCadastral;
    }

    public double getPrixHabitation() {
        return prixHabitation;
    }

    public void setPrixHabitation(double prixHabitation) {
        this.prixHabitation = prixHabitation;
    }

    public double getFraisNotaireAchat() {
        return fraisNotaireAchat;
    }

    public void setFraisNotaireAchat(double fraisNotaireAchat) {
        this.fraisNotaireAchat = fraisNotaireAchat;
    }

    public double getFraisTransformation() {
        return fraisTransformation;
    }

    public void setFraisTransformation(double fraisTransformation) {
        this.fraisTransformation = fraisTransformation;
    }

}
